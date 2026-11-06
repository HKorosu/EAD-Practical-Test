package com.fptaptech.sis_springboot.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class GradeCalculator {

    private static final BigDecimal SCORE1_WEIGHT = new BigDecimal("0.3");
    private static final BigDecimal SCORE2_WEIGHT = new BigDecimal("0.7");

    private GradeCalculator() {
    }

    public static BigDecimal weightedScore(BigDecimal score1, BigDecimal score2) {
        return score1.multiply(SCORE1_WEIGHT)
                .add(score2.multiply(SCORE2_WEIGHT))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public static String grade(BigDecimal weightedScore) {
        if (weightedScore.compareTo(new BigDecimal("8.0")) >= 0) {
            return "A";
        }
        if (weightedScore.compareTo(new BigDecimal("6.0")) >= 0) {
            return "B";
        }
        if (weightedScore.compareTo(new BigDecimal("4.0")) >= 0) {
            return "D";
        }
        return "F";
    }
}
