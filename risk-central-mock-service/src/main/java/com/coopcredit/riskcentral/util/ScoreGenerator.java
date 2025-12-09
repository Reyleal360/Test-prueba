package com.coopcredit.riskcentral.util;

import java.math.BigDecimal;
import java.util.Random;

public class ScoreGenerator {

    /**
     * Generates a deterministic risk score based on document number and amount.
     * The score is between 100 and 1000.
     */
    public static int generateScore(String documentNumber, BigDecimal requestedAmount) {
        if (documentNumber == null) {
            return 100;
        }

        // Use document number hash as seed for consistency
        long seed = documentNumber.hashCode();

        // Add amount influence to allow some variance if amount changes significantly
        // We take the integer part of the amount
        if (requestedAmount != null) {
            seed += requestedAmount.longValue();
        }

        Random random = new Random(seed);

        // Generate score between 300 and 950 (as per requirements)
        return 300 + random.nextInt(651);
    }
}
