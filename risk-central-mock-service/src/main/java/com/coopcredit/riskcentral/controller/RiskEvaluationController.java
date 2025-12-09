package com.coopcredit.riskcentral.controller;

import com.coopcredit.riskcentral.dto.RiskEvaluationRequest;
import com.coopcredit.riskcentral.dto.RiskEvaluationResponse;
import com.coopcredit.riskcentral.service.RiskScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/risk-evaluation")
@RequiredArgsConstructor
@Slf4j
public class RiskEvaluationController {

    private final RiskScoreService riskScoreService;

    @PostMapping
    public ResponseEntity<RiskEvaluationResponse> evaluateRisk(@RequestBody RiskEvaluationRequest request) {
        log.info("Received risk evaluation request for document: {}", request.getDocumentNumber());
        RiskEvaluationResponse response = riskScoreService.evaluate(request);
        log.info("Risk evaluation completed for document: {}, Score: {}, Level: {}",
                request.getDocumentNumber(), response.getRiskScore(), response.getRiskLevel());
        return ResponseEntity.ok(response);
    }
}
