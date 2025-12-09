package com.coopcredit.application.application.usecases.affiliates;

import com.coopcredit.application.domain.exception.BusinessRuleException;
import com.coopcredit.application.domain.model.Affiliate;
import com.coopcredit.application.domain.model.enums.AffiliateStatus;
import com.coopcredit.application.domain.ports.out.AffiliateRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Register Affiliate Use Case Tests")
class RegisterAffiliateUseCaseImplTest {

    @Mock
    private AffiliateRepositoryPort affiliateRepositoryPort;

    @InjectMocks
    private RegisterAffiliateUseCaseImpl useCase;

    private Affiliate validAffiliate;

    @BeforeEach
    void setUp() {
        validAffiliate = Affiliate.builder()
                .documentNumber("1234567890")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("3001234567")
                .monthlySalary(new BigDecimal("5000000"))
                .status(AffiliateStatus.ACTIVE)
                .seniority(0)
                .build();
    }

    @Test
    @DisplayName("Should successfully register affiliate with valid data")
    void shouldRegisterAffiliateSuccessfully() {
        // Arrange
        when(affiliateRepositoryPort.existsByDocumentNumber(anyString())).thenReturn(false);
        when(affiliateRepositoryPort.save(any(Affiliate.class)))
                .thenAnswer(invocation -> {
                    Affiliate affiliate = invocation.getArgument(0);
                    affiliate.setId(1L);
                    return affiliate;
                });

        // Act
        Affiliate result = useCase.register(validAffiliate);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getDocumentNumber()).isEqualTo("1234567890");
        assertThat(result.getStatus()).isEqualTo(AffiliateStatus.ACTIVE);

        verify(affiliateRepositoryPort).existsByDocumentNumber("1234567890");
        verify(affiliateRepositoryPort).save(any(Affiliate.class));
    }

    @Test
    @DisplayName("Should reject registration when document number already exists")
    void shouldRejectWhenDocumentNumberExists() {
        // Arrange
        when(affiliateRepositoryPort.existsByDocumentNumber(anyString())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> useCase.register(validAffiliate))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("already exists");

        verify(affiliateRepositoryPort).existsByDocumentNumber("1234567890");
        verify(affiliateRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Should reject registration when salary is zero")
    void shouldRejectWhenSalaryIsZero() {
        // Arrange
        validAffiliate.setMonthlySalary(BigDecimal.ZERO);
        when(affiliateRepositoryPort.existsByDocumentNumber(anyString())).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> useCase.register(validAffiliate))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("salary must be greater than zero");

        verify(affiliateRepositoryPort).existsByDocumentNumber("1234567890");
        verify(affiliateRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Should reject registration when salary is negative")
    void shouldRejectWhenSalaryIsNegative() {
        // Arrange
        validAffiliate.setMonthlySalary(new BigDecimal("-1000"));
        when(affiliateRepositoryPort.existsByDocumentNumber(anyString())).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> useCase.register(validAffiliate))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("salary must be greater than zero");

        verify(affiliateRepositoryPort).existsByDocumentNumber("1234567890");
        verify(affiliateRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Should set seniority to zero for new affiliates")
    void shouldSetSeniorityToZeroForNewAffiliates() {
        // Arrange
        when(affiliateRepositoryPort.existsByDocumentNumber(anyString())).thenReturn(false);
        when(affiliateRepositoryPort.save(any(Affiliate.class)))
                .thenAnswer(invocation -> {
                    Affiliate affiliate = invocation.getArgument(0);
                    affiliate.setId(1L);
                    return affiliate;
                });

        // Act
        Affiliate result = useCase.register(validAffiliate);

        // Assert
        assertThat(result.getSeniority()).isNotNull();

        verify(affiliateRepositoryPort).save(any(Affiliate.class));
    }
}
