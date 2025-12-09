package com.coopcredit.application.application.usecases.applications;

import com.coopcredit.application.domain.exception.BusinessRuleException;
import com.coopcredit.application.domain.exception.NotFoundException;
import com.coopcredit.application.domain.model.Affiliate;
import com.coopcredit.application.domain.model.CreditApplication;
import com.coopcredit.application.domain.model.RiskEvaluation;
import com.coopcredit.application.domain.model.enums.AffiliateStatus;
import com.coopcredit.application.domain.model.enums.ApplicationStatus;
import com.coopcredit.application.domain.model.enums.RiskLevel;
import com.coopcredit.application.domain.ports.out.AffiliateRepositoryPort;
import com.coopcredit.application.domain.ports.out.CreditApplicationRepositoryPort;
import com.coopcredit.application.domain.ports.out.RiskEvaluationRepositoryPort;
import com.coopcredit.application.domain.ports.out.RiskEvaluationServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Register Credit Application Use Case Tests")
class RegisterCreditApplicationUseCaseImplTest {

        @Mock
        private AffiliateRepositoryPort affiliateRepositoryPort;

        @Mock
        private CreditApplicationRepositoryPort applicationRepositoryPort;

        @Mock
        private RiskEvaluationRepositoryPort riskEvaluationRepositoryPort;

        @Mock
        private RiskEvaluationServicePort riskEvaluationServicePort;

        @InjectMocks
        private RegisterCreditApplicationUseCaseImpl useCase;

        private Affiliate validAffiliate;
        private CreditApplication validApplication;
        private RiskEvaluation lowRiskEvaluation;

        @BeforeEach
        void setUp() {
                validAffiliate = Affiliate.builder()
                                .id(1L)
                                .documentNumber("1234567890")
                                .firstName("John")
                                .lastName("Doe")
                                .email("john.doe@example.com")
                                .phoneNumber("3001234567")
                                .monthlySalary(new BigDecimal("5000000"))
                                .status(AffiliateStatus.ACTIVE)
                                .seniority(12)
                                .build();

                validApplication = CreditApplication.builder()
                                .requestedAmount(new BigDecimal("10000000"))
                                .termMonths(24)
                                .purpose("Home improvement")
                                .build();

                lowRiskEvaluation = RiskEvaluation.builder()
                                .riskScore(750)
                                .riskLevel(RiskLevel.LOW)
                                .build();
        }

        @Test
        @DisplayName("Should successfully register credit application with valid data")
        void shouldRegisterCreditApplicationSuccessfully() {
                // Arrange
                when(affiliateRepositoryPort.findById(anyLong())).thenReturn(Optional.of(validAffiliate));
                when(applicationRepositoryPort.save(any(CreditApplication.class)))
                                .thenAnswer(invocation -> {
                                        CreditApplication app = invocation.getArgument(0);
                                        app.setId(1L);
                                        return app;
                                });
                when(riskEvaluationServicePort.evaluateRisk(anyString(), any(BigDecimal.class), any(BigDecimal.class)))
                                .thenReturn(lowRiskEvaluation);
                when(riskEvaluationRepositoryPort.save(any(RiskEvaluation.class)))
                                .thenReturn(lowRiskEvaluation);

                // Act
                CreditApplication result = useCase.register(1L, validApplication);

                // Assert
                assertThat(result).isNotNull();
                assertThat(result.getStatus()).isEqualTo(ApplicationStatus.APPROVED);
                assertThat(result.getAffiliate()).isEqualTo(validAffiliate);
                assertThat(result.getMonthlyQuota()).isNotNull();
                assertThat(result.getDebtToIncomeRatio()).isNotNull();

                verify(affiliateRepositoryPort).findById(1L);
                verify(applicationRepositoryPort, times(2)).save(any(CreditApplication.class));
                verify(riskEvaluationServicePort).evaluateRisk(anyString(), any(BigDecimal.class),
                                any(BigDecimal.class));
                verify(riskEvaluationRepositoryPort).save(any(RiskEvaluation.class));
        }

        @Test
        @DisplayName("Should reject application when affiliate is not found")
        void shouldRejectWhenAffiliateNotFound() {
                // Arrange
                when(affiliateRepositoryPort.findById(anyLong())).thenReturn(Optional.empty());

                // Act & Assert
                assertThatThrownBy(() -> useCase.register(1L, validApplication))
                                .isInstanceOf(NotFoundException.class)
                                .hasMessage("Affiliate not found");

                verify(affiliateRepositoryPort).findById(1L);
                verify(applicationRepositoryPort, never()).save(any());
        }

        @Test
        @DisplayName("Should reject application when affiliate is not active")
        void shouldRejectWhenAffiliateNotActive() {
                // Arrange
                validAffiliate.setStatus(AffiliateStatus.INACTIVE);
                when(affiliateRepositoryPort.findById(anyLong())).thenReturn(Optional.of(validAffiliate));

                // Act & Assert
                assertThatThrownBy(() -> useCase.register(1L, validApplication))
                                .isInstanceOf(BusinessRuleException.class)
                                .hasMessage("Affiliate is not active");

                verify(affiliateRepositoryPort).findById(1L);
                verify(applicationRepositoryPort, never()).save(any());
        }

        @Test
        @DisplayName("Should reject application when affiliate seniority is less than 6 months")
        void shouldRejectWhenInsufficientSeniority() {
                // Arrange
                validAffiliate.setSeniority(3);
                when(affiliateRepositoryPort.findById(anyLong())).thenReturn(Optional.of(validAffiliate));

                // Act & Assert
                assertThatThrownBy(() -> useCase.register(1L, validApplication))
                                .isInstanceOf(BusinessRuleException.class)
                                .hasMessage("Affiliate seniority must be at least 6 months");

                verify(affiliateRepositoryPort).findById(1L);
                verify(applicationRepositoryPort, never()).save(any());
        }

        @Test
        @DisplayName("Should reject application when requested amount exceeds 10x salary")
        void shouldRejectWhenAmountExceedsSalaryLimit() {
                // Arrange
                validApplication.setRequestedAmount(new BigDecimal("60000000")); // 12x salary
                when(affiliateRepositoryPort.findById(anyLong())).thenReturn(Optional.of(validAffiliate));

                // Act & Assert
                assertThatThrownBy(() -> useCase.register(1L, validApplication))
                                .isInstanceOf(BusinessRuleException.class)
                                .hasMessage("Requested amount exceeds 10 times the monthly salary");

                verify(affiliateRepositoryPort).findById(1L);
                verify(applicationRepositoryPort, never()).save(any());
        }

        @Test
        @DisplayName("Should reject application when term is less than 6 months")
        void shouldRejectWhenTermTooShort() {
                // Arrange
                validApplication.setTermMonths(3);
                when(affiliateRepositoryPort.findById(anyLong())).thenReturn(Optional.of(validAffiliate));

                // Act & Assert
                assertThatThrownBy(() -> useCase.register(1L, validApplication))
                                .isInstanceOf(BusinessRuleException.class)
                                .hasMessage("Term must be between 6 and 60 months");

                verify(affiliateRepositoryPort).findById(1L);
                verify(applicationRepositoryPort, never()).save(any());
        }

        @Test
        @DisplayName("Should reject application when term exceeds 60 months")
        void shouldRejectWhenTermTooLong() {
                // Arrange
                validApplication.setTermMonths(72);
                when(affiliateRepositoryPort.findById(anyLong())).thenReturn(Optional.of(validAffiliate));

                // Act & Assert
                assertThatThrownBy(() -> useCase.register(1L, validApplication))
                                .isInstanceOf(BusinessRuleException.class)
                                .hasMessage("Term must be between 6 and 60 months");

                verify(affiliateRepositoryPort).findById(1L);
                verify(applicationRepositoryPort, never()).save(any());
        }

        @Test
        @DisplayName("Should reject application when debt-to-income ratio exceeds 40%")
        void shouldRejectWhenDebtToIncomeRatioTooHigh() {
                // Arrange
                validApplication.setRequestedAmount(new BigDecimal("30000000"));
                validApplication.setTermMonths(12);
                when(affiliateRepositoryPort.findById(anyLong())).thenReturn(Optional.of(validAffiliate));

                // Act & Assert
                assertThatThrownBy(() -> useCase.register(1L, validApplication))
                                .isInstanceOf(BusinessRuleException.class)
                                .hasMessage("Debt to income ratio exceeds 40%");

                verify(affiliateRepositoryPort).findById(1L);
                verify(applicationRepositoryPort, never()).save(any());
        }

        @Test
        @DisplayName("Should set status to IN_REVIEW for medium-high risk (score 300-500)")
        void shouldSetInReviewForHighRisk() {
                // Arrange
                RiskEvaluation highRiskEvaluation = RiskEvaluation.builder()
                                .riskScore(450)
                                .riskLevel(RiskLevel.HIGH)
                                .build();

                when(affiliateRepositoryPort.findById(anyLong())).thenReturn(Optional.of(validAffiliate));
                when(applicationRepositoryPort.save(any(CreditApplication.class)))
                                .thenAnswer(invocation -> {
                                        CreditApplication app = invocation.getArgument(0);
                                        app.setId(1L);
                                        return app;
                                });
                when(riskEvaluationServicePort.evaluateRisk(anyString(), any(BigDecimal.class), any(BigDecimal.class)))
                                .thenReturn(highRiskEvaluation);
                when(riskEvaluationRepositoryPort.save(any(RiskEvaluation.class)))
                                .thenReturn(highRiskEvaluation);

                // Act
                CreditApplication result = useCase.register(1L, validApplication);

                // Assert
                assertThat(result.getStatus()).isEqualTo(ApplicationStatus.IN_REVIEW);
        }

        @Test
        @DisplayName("Should approve with reduced amount for medium risk (score 501-700)")
        void shouldApproveWithConditionsForMediumRisk() {
                // Arrange
                RiskEvaluation mediumRiskEvaluation = RiskEvaluation.builder()
                                .riskScore(650)
                                .riskLevel(RiskLevel.MEDIUM)
                                .build();

                when(affiliateRepositoryPort.findById(anyLong())).thenReturn(Optional.of(validAffiliate));
                when(applicationRepositoryPort.save(any(CreditApplication.class)))
                                .thenAnswer(invocation -> {
                                        CreditApplication app = invocation.getArgument(0);
                                        app.setId(1L);
                                        return app;
                                });
                when(riskEvaluationServicePort.evaluateRisk(anyString(), any(BigDecimal.class), any(BigDecimal.class)))
                                .thenReturn(mediumRiskEvaluation);
                when(riskEvaluationRepositoryPort.save(any(RiskEvaluation.class)))
                                .thenReturn(mediumRiskEvaluation);

                // Act
                CreditApplication result = useCase.register(1L, validApplication);

                // Assert
                assertThat(result.getStatus()).isEqualTo(ApplicationStatus.APPROVED);
                assertThat(result.getApprovalAmount())
                                .isEqualTo(validApplication.getRequestedAmount().multiply(new BigDecimal("0.8")));
        }
}
