JavaFX Calculator
==================

A simple calculator built with JavaFX, supporting basic arithmetic operations and a clean UI.
Also my very first project. Feel free to share your ideas and criticism.

**Features**

Basic arithmetic operations: Addition (+), Subtraction (-), Multiplication (*), and Division (/)


Decimal number support

Keyboard input for digits and operations

"AC" (All Clear) button to reset calculations

Sign swap functionality (+/-)

Flip UI mode for a fun interaction at all times where the answer is 5138008

**Requirements**

Java 11 or higher

JavaFX SDK (if not using a bundled JDK with JavaFX)

**Installation**

Clone the repository:

git clone https://github.com/pd-bass/gui-calculator.git
cd calculator

Compile and run the application:

mvn clean javafx:run

Or, if running manually:

javac -cp "path/to/javafx-sdk/lib/*" -d bin src/main/java/de/sebastianheuckmann/controller/App.java
java -cp "path/to/javafx-sdk/lib/*:bin" de.sebastianheuckmann.controller.CalculatorController

**Usage**

Click buttons or use the keyboard to input numbers and operations.

Press EQUALS/ENTER to evaluate an expression.

Use ESC/AC to reset calculations.

Press 'F' to toggle the UI flip effect.

**Credits**

For the best effect of the UI-flip, a 7 segment font is recommended.
However, because of licensing issues (no commercial use of the font), the font is not included.
Look up styles.css for tips how to implement a good font. If you like the artist's work, consider to donate.

**License**

This project is licensed under the MIT License. See LICENSE for details.

