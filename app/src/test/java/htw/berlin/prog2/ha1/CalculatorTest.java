package htw.berlin.prog2.ha1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Retro calculator")
class CalculatorTest {

    @Test
    @DisplayName("should display result after adding two positive multi-digit numbers")
    void testPositiveAddition() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(2);
        calc.pressDigitKey(0);
        calc.pressBinaryOperationKey("+");
        calc.pressDigitKey(2);
        calc.pressDigitKey(0);
        calc.pressEqualsKey();

        String expected = "40";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should display result after getting the square root of two")
    void testSquareRoot() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(2);
        calc.pressUnaryOperationKey("√");

        String expected = "1.4142135623730951";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should display error when dividing by zero")
    void testDivisionByZero() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(7);
        calc.pressBinaryOperationKey("/");
        calc.pressDigitKey(0);
        calc.pressEqualsKey();

        String expected = "Error";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should display error when drawing the square root of a negative number")
    void testSquareRootOfNegative() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(7);
        calc.pressNegativeKey();
        calc.pressUnaryOperationKey("√");

        String expected = "Error";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should not allow multiple decimal dots")
    void testMultipleDecimalDots() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(1);
        calc.pressDotKey();
        calc.pressDigitKey(7);
        calc.pressDotKey();
        calc.pressDigitKey(8);

        String expected = "1.78";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }


    //TODO hier weitere Tests erstellen
    @Test
    @DisplayName("should ad a decimal dot")
    void testDecimalDot() {
        Calculator calc = new Calculator();
        calc.pressDigitKey(1);
        calc.pressDotKey();
        calc.pressDigitKey(7);
        String expected = "1.7";
        String actual = calc.readScreen();

        assertEquals(expected, actual);

    }
    @Test
    @DisplayName("schould display result after multiplication of two positive multi-digit numbers")
    void testMultiplication() {
        Calculator calc = new Calculator();
        calc.pressDigitKey(2);
        calc.pressDigitKey(0);
        calc.pressBinaryOperationKey("*");
        calc.pressDigitKey(2);
        calc.pressDigitKey(0);
        calc.pressEqualsKey();
        String expected = "400";
        String actual = calc.readScreen();
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("should display result after modulo of two numbers")
    void testModulo() {
        Calculator calc = new Calculator();
        calc.pressDigitKey(2);
        calc.pressDigitKey(0);
        calc.pressBinaryOperationKey("%");
        calc.pressDigitKey(2);
        calc.pressDigitKey(0);
        calc.pressEqualsKey();
        String expected = "0";
        String actual = calc.readScreen();
        assertEquals(expected, actual);

    }
   @Test
   @DisplayName("schould display result after addition of 3 or more numbers")
   void testAdditionOf3Numbers() {
        Calculator calc = new Calculator();
        calc.pressDigitKey(2);
        calc.pressBinaryOperationKey("+");
        calc.pressDigitKey(5);
        calc.pressBinaryOperationKey("+");
        calc.pressDigitKey(5);
        calc.pressBinaryOperationKey("+");
        calc.pressDigitKey(4);


        calc.pressEqualsKey();
        String expected = "16";
        String actual = calc.readScreen();
        assertEquals(expected, actual);

   }
   @Test
   @DisplayName("schould display result after multiplication and addition of 3 numbers")
   void testMultiplicationAndAdditionOf3Numbers() {
       Calculator calc = new Calculator();
       calc.pressDigitKey(5);
       calc.pressBinaryOperationKey("x");
       calc.pressDigitKey(5);
       calc.pressBinaryOperationKey("+");
       calc.pressDigitKey(5);
       calc.pressEqualsKey();
       String expected = "30";
       String actual = calc.readScreen();
       assertEquals(expected, actual);
   }



    @Test
    @DisplayName("should display result after addition and multiplication of 3 numbers")
    void testAdditionAndMultiplicationOf3Numbers() {
        Calculator calc = new Calculator();
        calc.pressDigitKey(5);
        calc.pressBinaryOperationKey("+");
        calc.pressDigitKey(5);
        calc.pressBinaryOperationKey("x");
        calc.pressDigitKey(5);
        calc.pressEqualsKey();  // Führe Berechnung aus und zeige das Ergebnis an
        String expected = "30"; // Erwartetes Ergebnis von (5 + 5) * 3 = 20
        String actual = calc.readScreen();
        assertEquals(expected, actual);
    }




}


