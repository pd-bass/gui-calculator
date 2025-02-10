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

    public double divide(double a, double b){
        if (b != 0){
            setResult(a / b);
        }
        else {
            System.out.println("Error! Division by Zero");
            setResult(0); // setting result to 0 for safety
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
            System.out.println("Error! Root cannot be negative!");
            setResult(0);
        }
        else if (a < 0 && b % 2 == 0){
            System.out.println("Error! Even root of a negative number is not defined in real numbers!");
            setResult(0);
        }
        else {
            setResult(Math.pow(a, 1.0/b));
        }
        return getResult();
    }


}
