package com.coopcredit.application.infrastructure.controllers;

import com.coopcredit.application.domain.model.CreditApplication;
import com.coopcredit.application.domain.model.enums.ApplicationStatus;
import com.coopcredit.application.domain.ports.in.applications.GetCreditApplicationUseCase;
import com.coopcredit.application.domain.ports.in.applications.ListCreditApplicationsUseCase;
import com.coopcredit.application.domain.ports.in.applications.RegisterCreditApplicationUseCase;
import com.coopcredit.application.domain.ports.in.applications.UpdateApplicationStatusUseCase;
import com.coopcredit.application.infrastructure.web.dto.applications.CreateCreditApplicationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-applications")
@RequiredArgsConstructor
@Tag(name = "Credit Applications", description = "Endpoints for managing credit applications")
@SecurityRequirement(name = "bearer-jwt")
public class CreditApplicationController {

    private final RegisterCreditApplicationUseCase registerUseCase;
    private final GetCreditApplicationUseCase getUseCase;
    private final ListCreditApplicationsUseCase listUseCase;
    private final UpdateApplicationStatusUseCase updateStatusUseCase;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AFILIADO')")
    @Operation(summary = "Create a new credit application", description = "Create a new credit application for an affiliate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Credit application created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
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
    @Operation(summary = "Get credit application by ID", description = "Retrieve a specific credit application by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Credit application found"),
            @ApiResponse(responseCode = "404", description = "Credit application not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<CreditApplication> getById(@PathVariable Long id) {
        return ResponseEntity.ok(getUseCase.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    @Operation(summary = "List all credit applications", description = "Retrieve all credit applications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Credit applications retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<CreditApplication>> listAll() {
        return ResponseEntity.ok(listUseCase.listAll());
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    @Operation(summary = "Update credit application status", description = "Update the status of a credit application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Credit application not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<CreditApplication> updateStatus(@PathVariable Long id,
            @RequestParam ApplicationStatus status) {
        return ResponseEntity.ok(updateStatusUseCase.updateStatus(id, status));
    }
}
