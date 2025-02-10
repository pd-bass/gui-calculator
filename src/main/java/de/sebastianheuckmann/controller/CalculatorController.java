package de.sebastianheuckmann.controller;
import de.sebastianheuckmann.model.CalculatorModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
public class CalculatorController {

    private Operation currentOperation;
    private Double numberA = 0.0;
    private CalculatorModel model = new CalculatorModel();
    private boolean noValueEntered = true;
    @FXML
    private TextField display;
    @FXML
    private Button btnAdd, btnSubtract, btnMultiply, btnDivide, btnPower;

    @FXML
    private void initialize() {
        // Assign event handlers directly to operator buttons
        display.setText("0");
        btnAdd.setOnAction(e -> handleOperator(Operation.ADD));
        btnSubtract.setOnAction(e -> handleOperator(Operation.SUBTRACT));
        btnMultiply.setOnAction(e -> handleOperator(Operation.MULTIPLY));
        btnDivide.setOnAction(e -> handleOperator(Operation.DIVIDE));
        btnPower.setOnAction(e -> handleOperator(Operation.POWER));
    }

    @FXML
    private void handleAC(){
        display.clear();
        display.setText("0");
        numberA = 0.0;
        noValueEntered = true;
    }
    @FXML
    private void handle0(){
        if (!display.getText().equals("0")){
            display.appendText("0");
        }
    }

    private void updateDisplay(double result){
        if (result % 1 == 0) {
            display.setText(String.format("%d", (int) result));
        }
        else {
            display.setText(String.valueOf(result));
        }
    }
    @FXML
    private void handleDigit(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        if (!noValueEntered){
            display.appendText(clickedButton.getText());
        } else {
            display.clear();
            display.setText(clickedButton.getText());
            noValueEntered = false;
        }
    }

    @FXML
    private void handleDecimal() {
        if (!display.getText().contains(".")) {
            display.appendText(".");
        }
    }

    @FXML
    private void handleOperator(Operation operation) {
        currentOperation = operation;
        numberA = Double.parseDouble(display.getText());
        noValueEntered = true;
    }
    @FXML
    private void handleSwap() {
        updateDisplay(-Double.parseDouble(display.getText()));
    }
    @FXML
    private void handleEquals() {
        if (currentOperation != null) {
            double numberB =  Double.parseDouble(display.getText()); // store second Number
            double result = 0;
            switch (currentOperation) {
                case ADD -> result = model.add(numberA, numberB);
                case SUBTRACT -> result = model.subtract(numberA, numberB);
                case MULTIPLY -> result = model.multiply(numberA, numberB);
                case DIVIDE -> result = model.divide(numberA, numberB);
                case POWER -> result = model.power(numberA, numberB);
            }
            updateDisplay(result);
            numberA = result;
            noValueEntered = true;
            currentOperation = null;
        }
    }
}
