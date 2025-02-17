package de.sebastianheuckmann.controller;
import de.sebastianheuckmann.model.CalculatorModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.text.DecimalFormatSymbols;

public class CalculatorController {

    private CalculatorModel model = new CalculatorModel();
    private Operation currentOperation = null;
    private Double numberA = 0.0;
    private Double numberB;

    private static final char DECIMAL_SEPARATOR = DecimalFormatSymbols.getInstance().getDecimalSeparator();
    private static final String ERROR_MESSAGE = "ERROR";
    private boolean isNewInput = true;
    private boolean calculatorFlipped = false;
    private boolean equalPressed = false;
    @FXML
    private TextField display;
    @FXML
    private Button btnAdd, btnSubtract, btnMultiply, btnDivide, btnPower, btnEquals, btnAC, btnSwap;
    @FXML
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnDecimal;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private void initialize() {
        // Assign event handlers directly to operator buttons
        display.setText("0");
        btnAdd.setOnAction(e -> handleOperator(Operation.ADD));
        btnSubtract.setOnAction(e -> handleOperator(Operation.SUBTRACT));
        btnMultiply.setOnAction(e -> handleOperator(Operation.MULTIPLY));
        btnDivide.setOnAction(e -> handleOperator(Operation.DIVIDE));
        btnPower.setOnAction(e -> handleOperator(Operation.POWER));

        btnDecimal.setText(String.valueOf(DECIMAL_SEPARATOR)); // set Text in decimal-button according to Locale
        Platform.runLater(() -> {
            rootPane.requestFocus();
            rootPane.setOnKeyPressed(this :: handleKeyPress);
        });
    }

    @FXML
    private void handleKeyPress( KeyEvent event) {
        System.out.println("Key pressed " + event.getCode());
        switch (event.getCode()) {
            case DIGIT0, NUMPAD0 -> btn0.fire();
            case DIGIT1, NUMPAD1 -> btn1.fire();
            case DIGIT2, NUMPAD2 -> btn2.fire();
            case DIGIT3, NUMPAD3 -> btn3.fire();
            case DIGIT4, NUMPAD4 -> btn4.fire();
            case DIGIT5, NUMPAD5 -> btn5.fire();
            case DIGIT6, NUMPAD6 -> btn6.fire();
            case DIGIT7, NUMPAD7 -> btn7.fire();
            case DIGIT8, NUMPAD8 -> btn8.fire();
            case DIGIT9, NUMPAD9 -> btn9.fire();
            case ADD, PLUS -> btnAdd.fire();
            case SUBTRACT, MINUS -> btnSubtract.fire();
            case MULTIPLY -> btnMultiply.fire();
            case DIVIDE, SLASH -> btnDivide.fire();
            case ENTER, EQUALS -> btnEquals.fire();
            case ESCAPE -> btnAC.fire();
            case PERIOD, DECIMAL, COMMA -> handleDecimal();
            case M -> btnSwap.fire();
            case F -> flipCalculator();
            case BACK_SPACE -> handleBackspace();
        }
    }

    @FXML
    private void flipCalculator(){
        calculatorFlipped = !calculatorFlipped;
        rootPane.setRotate(calculatorFlipped ? 180 : 0);
    }
    @FXML
    private void handleAC(){
        display.clear();
        display.setText("0");
        numberA = 0.0;
        numberB = 0.0;
        isNewInput = true;
        currentOperation = null;
        equalPressed = false;
        btnAC.setText("AC");
    }
    /*@FXML
    private void handle0(){
        if (!isNewInput){
            display.appendText("0");
        }
        else {
            display.setText("0");
            isNewInput = false;
        }
    }*/

    @FXML
    private void handleDigit(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        btnAC.setText("C");
        if (!isNewInput ){
            display.appendText(clickedButton.getText());
        } else {
            display.clear();
            display.setText(clickedButton.getText());
            isNewInput = false;
        }
    }

    @FXML
    private void handleDecimal() {
        if (!display.getText().contains(String.valueOf(DECIMAL_SEPARATOR))) {
            display.appendText(String.valueOf(DECIMAL_SEPARATOR));
        }
    }

    @FXML
    private void handleOperator(Operation operation) {
        if (currentOperation != null && !equalPressed){
            handleEquals();
        }
        currentOperation = operation;
        numberA = Double.parseDouble(display.getText());
        isNewInput = true;
    }
    @FXML
    private void handleSwap() {
        updateDisplay(-Double.parseDouble(display.getText()));
    }
    @FXML
    private void handleEquals() {
        if (currentOperation != null) {
            if (!isNewInput){
                numberB =  Double.parseDouble(display.getText()); // store second Number (only when new Input was typed)
            }
            double result = calculateResult();
            if (Double.isNaN(result)){
                display.setText(ERROR_MESSAGE);
                numberA = 0.0;
            }
            else {
                updateDisplay(result);
                numberA = result;
            }
            isNewInput = true;
            equalPressed = true;
        }
    }

    private double calculateResult(){
        double calculatedResult;
        switch (currentOperation) {
            case ADD -> calculatedResult = model.add(numberA, numberB);
            case SUBTRACT -> calculatedResult = model.subtract(numberA, numberB);
            case MULTIPLY -> calculatedResult = model.multiply(numberA, numberB);
            case DIVIDE -> calculatedResult = model.divide(numberA, numberB);
            case POWER -> calculatedResult = model.power(numberA, numberB);
            default -> throw new IllegalArgumentException("Invalid operation");
        }
        return calculatedResult;
    }
    private void handleBackspace() {
        String text = display.getText();
        if (text.length() > 1) {
            display.setText(text.substring(0, text.length() - 1));
        } else {
            display.setText("0"); // Reset to default
            isNewInput = true;
        }
    }


    // ======  Util-Methods  ======

    private void updateDisplay(double result){
        if (result % 1 == 0) {
            display.setText(String.format("%d", (int) result));
        }
        else {
            display.setText(String.format("%.6f", result));
        }
    }
}