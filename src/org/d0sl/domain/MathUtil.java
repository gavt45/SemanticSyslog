package org.d0sl.domain;

@DomainModel(name = "Math")
public class MathUtil {
    @DomainFunction(name = "sqrt")
    public static double sqrt(double value) {
        return Math.sqrt(value);
    }

    @DomainFunction(name = "sin")
    public static double sin(double value) {
        double res = Math.sin(Math.toRadians(value));
        return Math.round(res);
    }

    @DomainFunction(name = "cos")
    public static double cos(double value) {
        double res = Math.cos(Math.toRadians(value));
        return res;
    }

    @DomainFunction(name = "pow")
    public static double pow(double a, double b) {
        return Math.pow(a, b);
    }
}
