package de.sebastianheuckmann.model;


public class CalculatorModel {
    private double result;
    private void setResult(double result){
        this.result = result;
    }
    public double getResult(){
        return result;
    }

    public double add(double a, double b){
        setResult(a + b);
        return getResult();
    }

    public double subtract(double a, double b){
        setResult(a - b);
        return getResult();
    }

    public double divide(double a, double b) throws ArithmeticException{
        if (b != 0){
            setResult(a / b);
        }
        else {
            setResult(Double.NaN);
        }
        return getResult();
    }

    public double multiply(double a, double b){
        setResult(a * b);
        return getResult();
    }

    public double power(double a, double b){
        setResult(Math.pow(a, b));
        return getResult();
    }

    public double nroot(double a, double b){
        if (b == 0){
            setResult(Double.NaN);
        }
        else if (a < 0 && b % 2 == 0){
            setResult(Double.NaN);
        }
        else {
            setResult(Math.pow(a, 1.0/b));
        }
        return getResult();
    }


}
