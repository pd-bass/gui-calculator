package de.sebastianheuckmann.controller;
import de.sebastianheuckmann.calculator.App;
import de.sebastianheuckmann.model.CalculatorModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculatorController {

    private final CalculatorModel model = new CalculatorModel();
    private final List<Double> numbers = new ArrayList<>();
    private final List<Operation> operations = new ArrayList<>();
    private final List<Double> numberStorage = new ArrayList<>(); // needed for concatination of precedences
    private final List<Operation> operationStorage = new ArrayList<>(); // needed for concatination of precedences

    private Operation lastOperation;
    private double lastNumber;

    private static final char DECIMAL_SEPARATOR = DecimalFormatSymbols.getInstance().getDecimalSeparator();
    private static final String ERROR_MESSAGE = "ERROR";
    private boolean isNewInput = true;
    private boolean calculatorFlipped = false;
    private boolean equalPressed = false;
    @FXML
    private TextField display;
    @FXML
    private Button btnAdd, btnSubtract, btnMultiply, btnDivide, btnEquals, btnAC, btnSwap;
    /*@FXML
    private Button btnPowSq, btnPowCu, btnPowY;*/
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
        // btnPowY.setOnAction(e -> handleOperator(Operation.POWER));

        btnDecimal.setText(String.valueOf(DECIMAL_SEPARATOR)); // set Text in decimal-button according to Locale
        Platform.runLater(() -> {
            rootPane.requestFocus();
            rootPane.setOnKeyPressed(this :: handleKeyPress);
        });
    }
    // ========================================
    // ============ UI - METHODS ==============
    // ========================================
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
            case S -> switchToScientific();
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
        isNewInput = true;
        equalPressed = false;
        btnAC.setText("AC");
        operations.clear();
        numbers.clear();
        operationStorage.clear();
        numberStorage.clear();
    }
    @FXML
    private void handleDigit(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        btnAC.setText("C");
        if (!isNewInput){
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
            isNewInput = false;
        }
    }

    @FXML
    private void handleOperator(Operation operation) {
        double currentNumber = getDisplayAsDouble();

        if (!isNewInput) {
            numbers.add(currentNumber); // Store number before operator
            System.out.println("Number '" + currentNumber + "' added");
            System.out.println("Operation-list: " + operations);
            isNewInput = true;
        }
        if (!operations.isEmpty()) {
            // Handle operator precedence before adding new operation
            if (operation.getPrecedence() <= operations.get(operations.size() - 1).getPrecedence()) {
                System.out.println("current Precedence: " + operation.getPrecedence());
                System.out.println("last Precedence: " + operations.get(operations.size() -1).getPrecedence());
                if (operation.getPrecedence() < operations.get(operations.size() - 1).getPrecedence()){
                    addStorageToLists();
                }
                handleEquals(); // Compute lower precedence first
            }
            else {
                numberStorage.add(numbers.get(0));
                numbers.remove(0);
                operationStorage.add(operations.get(0));
                operations.remove(0);
            }
        }
        operations.add(operation);

        equalPressed = false;
    }

    @FXML
    private void handleSwap() {
        updateDisplay(-getDisplayAsDouble());
        numbers.removeLast();
        numbers.add(getDisplayAsDouble());
    }
    @FXML
    private void handleEquals() {
        if (numbers.isEmpty()) {
            return;
        }

        // Store last entered number
        if (!isNewInput) {
            lastNumber = getDisplayAsDouble(); // if needed for a repeated calculation
            numbers.add(lastNumber);
            addStorageToLists();
        }
        if (!equalPressed){
            // Compute in PEMDAS order
            lastOperation = operations.getLast();
            applyOperatorPrecedence(Operation.POWER);
            applyOperatorPrecedence(Operation.MULTIPLY, Operation.DIVIDE);
            applyOperatorPrecedence(Operation.ADD, Operation.SUBTRACT);
        } else {
            numbers.add(lastNumber);
            operations.add(lastOperation);
            applyOperatorPrecedence(lastOperation);
        }

        // Final result should be in numbers[0]
        double result = numbers.get(0);
        System.out.println("Result: " + result);
        updateDisplay(result);

        // Reset for next calculation
        numbers.clear();
        operations.clear();
        numbers.add(result);
        isNewInput = true;
        equalPressed = true;
    }
    private void applyOperatorPrecedence(Operation... targetOps) {
        int i = 0;
        while (i < operations.size()) {
            Operation op = operations.get(i);
            if (Arrays.asList(targetOps).contains(op)) {
                double a = numbers.get(i);
                double b = numbers.get(i + 1);
                double result = calculate(op, a, b);

                // Replace `a op b` with `result`
                numbers.set(i, result);
                numbers.remove(i + 1);
                operations.remove(i);
            } else {
                i++; // Move to next operation
            }
        }
    }

    @FXML
    private void handleBackspace() {
        String displayContent = display.getText();
        if (displayContent.length() > 1) {
            display.setText(displayContent.substring(0, displayContent.length() - 1));
        } else {
            display.setText("0"); // Reset to default
            isNewInput = true;
        }
    }
    @FXML
    private void inPercent() {
        double value = Double.parseDouble(display.getText());
        updateDisplay(value/100);
    }

    // ======= SCIENTIFIC CALCULATOR METHODS =========

    /*@FXML
    private void handlePower(ActionEvent event){
        Button clickedButton = (Button) event.getSource();
        switch (clickedButton.getId()) {
            case "btnPowSq" -> updateDisplay(model.power(1,2));
            case "btnPowCu" -> updateDisplay(model.power(1,3));
        }
    }*/


    // ======  Util-Methods  ======

    private String replaceDecimal(String value){
        return value.replace(DECIMAL_SEPARATOR, '.'); //make sure that calculations always use '.' as Decimal
    }
    private void updateDisplay(double result){
        if (result % 1 == 0) {
            display.setText(String.format("%d", (int) result));
        }
        else {
            String truncatedDisplay = String.format("%.6f", result);
            while (true){
                if (truncatedDisplay.charAt(truncatedDisplay.length()-1) == '0'){
                    truncatedDisplay = truncatedDisplay.substring(0,truncatedDisplay.length()-1);
                }
                else {
                    break;
                }
            }
            display.setText(truncatedDisplay);
        }
    }

    private void addStorageToLists() {
        numbers.addAll(0, numberStorage);
        operations.addAll(0, operationStorage);
        numberStorage.clear();
        operationStorage.clear();
    }

    private Double getDisplayAsDouble() {
        return Double.parseDouble(replaceDecimal(display.getText()));
    }

    private double calculate(Operation op, double a, double b) {
        return switch (op) {
            case ADD -> model.add(a, b);
            case SUBTRACT -> model.subtract(a, b);
            case MULTIPLY -> model.multiply(a, b);
            case DIVIDE -> model.divide(a, b);
            case POWER -> model.power(a, b);
            default -> throw new IllegalArgumentException("Invalid operation");
        };
    }

    private void switchToScientific(){
        App.switchScene("/de/sebastianheuckmann/view/scientificCalculator.fxml");
    }
}