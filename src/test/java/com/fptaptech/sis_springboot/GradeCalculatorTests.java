package com.fptaptech.sis_springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fptaptech.sis_springboot.service.GradeCalculator;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class GradeCalculatorTests {

    @Test
    void calculatesWeightedScoreAndGrade() {
        BigDecimal weightedScore = GradeCalculator.weightedScore(
                new BigDecimal("8.5"), new BigDecimal("7.0"));

        assertEquals(new BigDecimal("7.45"), weightedScore);
        assertEquals("B", GradeCalculator.grade(weightedScore));
    }

    @Test
    void assignsGradesAtEveryBoundary() {
        assertEquals("A", GradeCalculator.grade(new BigDecimal("8.00")));
        assertEquals("B", GradeCalculator.grade(new BigDecimal("6.00")));
        assertEquals("D", GradeCalculator.grade(new BigDecimal("4.00")));
        assertEquals("F", GradeCalculator.grade(new BigDecimal("3.99")));
    }
}
