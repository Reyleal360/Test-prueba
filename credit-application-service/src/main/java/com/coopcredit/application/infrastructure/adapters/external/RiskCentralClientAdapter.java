package com.coopcredit.application.infrastructure.adapters.external;

import com.coopcredit.application.domain.exception.ExternalServiceException;
import com.coopcredit.application.domain.model.RiskEvaluation;
import com.coopcredit.application.domain.ports.out.RiskEvaluationServicePort;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class RiskCentralClientAdapter implements RiskEvaluationServicePort {

    private final WebClient riskCentralWebClient;

    @Override
    public RiskEvaluation evaluateRisk(String documentNumber, BigDecimal requestedAmount, BigDecimal monthlySalary) {
        try {
            RiskEvaluationRequest request = RiskEvaluationRequest.builder()
                    .documentNumber(documentNumber)
                    .requestedAmount(requestedAmount)
                    .monthlySalary(monthlySalary)
                    .build();

            log.info("Calling Risk Central for document: {}", documentNumber);

            RiskEvaluationResponse response = riskCentralWebClient.post()
                    .uri("/api/risk-evaluation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(RiskEvaluationResponse.class)
                    .block(); // We block here as our domain logic is synchronous for now

            if (response == null) {
                throw new ExternalServiceException("Received null response from Risk Central");
            }

            return RiskEvaluation.builder()
                    .riskScore(response.getRiskScore())
                    // Mapping string to enum, assuming names match. Robustness could be added here.
                    .riskLevel(com.coopcredit.application.domain.model.enums.RiskLevel.valueOf(response.getRiskLevel()))
                    .evaluationDate(response.getEvaluationDate())
                    .externalReference(response.getExternalReference())
                    .recommendation(response.getRecommendation())
                    .build();

        } catch (WebClientResponseException e) {
            log.error("Error calling Risk Central: {}", e.getMessage());
            throw new ExternalServiceException("Error calling Risk Central", e);
        } catch (Exception e) {
            log.error("Unexpected error calling Risk Central", e);
            throw new ExternalServiceException("Unexpected error during risk evaluation", e);
        }
    }

    @Data
    @Builder
    private static class RiskEvaluationRequest {
        private String documentNumber;
        private BigDecimal requestedAmount;
        private BigDecimal monthlySalary;
    }

    @Data
    private static class RiskEvaluationResponse {
        private String documentNumber;
        private Integer riskScore;
        private String riskLevel;
        private LocalDateTime evaluationDate;
        private String externalReference;
        private String recommendation;
    }
}
