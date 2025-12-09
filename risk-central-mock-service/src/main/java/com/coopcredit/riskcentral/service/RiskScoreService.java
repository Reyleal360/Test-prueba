package com.coopcredit.riskcentral.service;

import com.coopcredit.riskcentral.dto.RiskEvaluationRequest;
import com.coopcredit.riskcentral.dto.RiskEvaluationResponse;
import com.coopcredit.riskcentral.util.ScoreGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RiskScoreService {

    public RiskEvaluationResponse evaluate(RiskEvaluationRequest request) {
        int score = ScoreGenerator.generateScore(request.getDocumentNumber(), request.getRequestedAmount());

        String riskLevel;
        String recommendation;

        if (score < 300) {
            riskLevel = "CRITICAL";
            recommendation = "Auto-rejected due to critical risk score.";
        } else if (score <= 500) {
            riskLevel = "HIGH";
            recommendation = "Requires manual review. High risk detected.";
        } else if (score <= 700) {
            riskLevel = "MEDIUM";
            recommendation = "Approved with conditions.";
        } else {
            riskLevel = "LOW";
            recommendation = "Approved for credit.";
        }

        return RiskEvaluationResponse.builder()
                .documentNumber(request.getDocumentNumber())
                .riskScore(score)
                .riskLevel(riskLevel)
                .evaluationDate(LocalDateTime.now())
                .externalReference("RSK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .recommendation(recommendation)
                .build();
    }
}
