package com.coopcredit.application.infrastructure.controllers;

import com.coopcredit.application.domain.model.CreditApplication;
import com.coopcredit.application.domain.model.enums.ApplicationStatus;
import com.coopcredit.application.domain.ports.in.applications.GetCreditApplicationUseCase;
import com.coopcredit.application.domain.ports.in.applications.ListCreditApplicationsUseCase;
import com.coopcredit.application.domain.ports.in.applications.RegisterCreditApplicationUseCase;
import com.coopcredit.application.domain.ports.in.applications.UpdateApplicationStatusUseCase;
import com.coopcredit.application.infrastructure.web.dto.applications.CreateCreditApplicationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-applications")
@RequiredArgsConstructor
public class CreditApplicationController {

    private final RegisterCreditApplicationUseCase registerUseCase;
    private final GetCreditApplicationUseCase getUseCase;
    private final ListCreditApplicationsUseCase listUseCase;
    private final UpdateApplicationStatusUseCase updateStatusUseCase;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AFILIADO')")
    public ResponseEntity<CreditApplication> create(@RequestBody CreateCreditApplicationRequest request) {
        CreditApplication application = CreditApplication.builder()
                .requestedAmount(request.getRequestedAmount())
                .termMonths(request.getTermMonths())
                .purpose(request.getPurpose())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerUseCase.register(request.getAffiliateId(), application));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    public ResponseEntity<CreditApplication> getById(@PathVariable Long id) {
        return ResponseEntity.ok(getUseCase.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    public ResponseEntity<List<CreditApplication>> listAll() {
        return ResponseEntity.ok(listUseCase.listAll());
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    public ResponseEntity<CreditApplication> updateStatus(@PathVariable Long id,
            @RequestParam ApplicationStatus status) {
        return ResponseEntity.ok(updateStatusUseCase.updateStatus(id, status));
    }
}
