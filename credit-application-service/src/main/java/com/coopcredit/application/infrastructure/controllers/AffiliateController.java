package com.coopcredit.application.infrastructure.controllers;

import com.coopcredit.application.domain.model.Affiliate;
import com.coopcredit.application.domain.ports.in.affiliates.GetAffiliateUseCase;
import com.coopcredit.application.domain.ports.in.affiliates.ListAffiliatesUseCase;
import com.coopcredit.application.domain.ports.in.affiliates.RegisterAffiliateUseCase;
import com.coopcredit.application.domain.ports.in.affiliates.UpdateAffiliateUseCase;
import com.coopcredit.application.infrastructure.web.dto.affiliates.RegisterAffiliateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/affiliates")
@RequiredArgsConstructor
public class AffiliateController {

    private final RegisterAffiliateUseCase registerAffiliateUseCase;
    private final GetAffiliateUseCase getAffiliateUseCase;
    private final ListAffiliatesUseCase listAffiliatesUseCase;
    private final UpdateAffiliateUseCase updateAffiliateUseCase;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    public ResponseEntity<Affiliate> register(@RequestBody RegisterAffiliateRequest request) {
        Affiliate affiliate = Affiliate.builder()
                .documentNumber(request.getDocumentNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .monthlySalary(request.getMonthlySalary())
                .seniority(request.getSeniority())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(registerAffiliateUseCase.register(affiliate));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    public ResponseEntity<List<Affiliate>> listAll() {
        return ResponseEntity.ok(listAffiliatesUseCase.listAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')") // Affiliates would need simplified context to see self
    public ResponseEntity<Affiliate> getById(@PathVariable Long id) {
        return ResponseEntity.ok(getAffiliateUseCase.getById(id));
    }
}
