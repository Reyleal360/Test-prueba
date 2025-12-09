package com.coopcredit.application.domain.model;

import com.coopcredit.application.domain.model.enums.AffiliateStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Affiliate {
    private Long id;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private BigDecimal monthlySalary;
    private Integer seniority;
    private AffiliateStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
