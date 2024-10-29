package htw.berlin.prog2.ha1;

/**
 * Eine Klasse, die das Verhalten des Online Taschenrechners imitiert, welcher auf
 * https://www.online-calculator.com/ aufgerufen werden kann (ohne die Memory-Funktionen)
 * und dessen Bildschirm bis zu zehn Ziffern plus einem Dezimaltrennzeichen darstellen kann.
 * Enthält mit Absicht noch diverse Bugs oder unvollständige Funktionen.
 */
import java.util.ArrayList;
import java.util.List;
public class Calculator {
    private String screen = "0";
    private double[] numbers = new double[10];  // Maximal 10 Zahlen können gespeichert werden
    private String[] operators = new String[9]; // Maximal 9 Operatoren (da 10 Zahlen)
    private int numberIndex = 0;
    private int operatorIndex = 0;
    private boolean newInput = true;


    /**
     * @return den aktuellen Bildschirminhalt als String
     */
    public String readScreen() {
        return screen;
    }

    /**
     * Empfängt den Wert einer gedrückten Zifferntaste. Da man nur eine Taste auf einmal
     * drücken kann muss der Wert positiv und einstellig sein und zwischen 0 und 9 liegen.
     * Führt in jedem Fall dazu, dass die gerade gedrückte Ziffer auf dem Bildschirm angezeigt
     * oder rechts an die zuvor gedrückte Ziffer angehängt angezeigt wird.
     *
     * @param digit Die Ziffer, deren Taste gedrückt wurde
     */
    public void pressDigitKey(int digit) {
        if (digit > 9 || digit < 0) throw new IllegalArgumentException();
        if (newInput) {  // Neues Ergebnis startet eine neue Eingabe
            screen = "";
            newInput = false;
        }
        if (screen.equals("0")) {
            screen = Integer.toString(digit);
        } else {
            screen += digit;
        }

    }

    /**
     * Empfängt den Befehl der C- bzw. CE-Taste (Clear bzw. Clear Entry).
     * Einmaliges Drücken der Taste löscht die zuvor eingegebenen Ziffern auf dem Bildschirm
     * so dass "0" angezeigt wird, jedoch ohne zuvor zwischengespeicherte Werte zu löschen.
     * Wird daraufhin noch einmal die Taste gedrückt, dann werden auch zwischengespeicherte
     * Werte sowie der aktuelle Operationsmodus zurückgesetzt, so dass der Rechner wieder
     * im Ursprungszustand ist.
     */
    public void pressClearKey() {
        screen = "0";
        numbers = new double[10];
        operators = new String[9];
        numberIndex = 0;
        operatorIndex = 0;
        newInput = true;
    }

    /**
     * Empfängt den Wert einer gedrückten binären Operationstaste, also eine der vier Operationen
     * Addition, Substraktion, Division, oder Multiplikation, welche zwei Operanden benötigen.
     * Beim ersten Drücken der Taste wird der Bildschirminhalt nicht verändert, sondern nur der
     * Rechner in den passenden Operationsmodus versetzt.
     * Beim zweiten Drücken nach Eingabe einer weiteren Zahl wird direkt des aktuelle Zwischenergebnis
     * auf dem Bildschirm angezeigt. Falls hierbei eine Division durch Null auftritt, wird "Error" angezeigt.
     *
     * @param operation "+" für Addition, "-" für Substraktion, "x" für Multiplikation, "/" für Division
     */
    public void pressBinaryOperationKey(String operation) {
        double currentValue = Double.parseDouble(screen);
        numbers[numberIndex++] = currentValue;
        operators[operatorIndex++] = operation;
        newInput = true;
    }



    /**
     * Empfängt den Wert einer gedrückten unären Operationstaste, also eine der drei Operationen
     * Quadratwurzel, Prozent, Inversion, welche nur einen Operanden benötigen.
     * Beim Drücken der Taste wird direkt die Operation auf den aktuellen Zahlenwert angewendet und
     * der Bildschirminhalt mit dem Ergebnis aktualisiert.
     *
     * @param operation "√" für Quadratwurzel, "%" für Prozent, "1/x" für Inversion
     */
    public void pressUnaryOperationKey(String operation) {
        double currentValue = Double.parseDouble(screen); // Der aktuelle Wert auf dem Bildschirm

        double result = switch (operation) {
            case "√" -> Math.sqrt(currentValue);
            case "%" -> currentValue / 100;
            case "1/x" -> 1 / currentValue;
            default -> throw new IllegalArgumentException("Unbekannte Operation: " + operation);
        };

        // Aktualisiert den Bildschirm mit dem Ergebnis und setzt für die nächste Eingabe zurück
        screen = Double.toString(result);
        if (screen.endsWith(".0")) screen = screen.substring(0, screen.length() - 2);
        newInput = true;
    }

    /**
     * Empfängt den Befehl der gedrückten Dezimaltrennzeichentaste, im Englischen üblicherweise "."
     * Fügt beim ersten Mal Drücken dem aktuellen Bildschirminhalt das Trennzeichen auf der rechten
     * Seite hinzu und aktualisiert den Bildschirm. Daraufhin eingegebene Zahlen werden rechts vom
     * Trennzeichen angegeben und daher als Dezimalziffern interpretiert.
     * Beim zweimaligem Drücken, oder wenn bereits ein Trennzeichen angezeigt wird, passiert nichts.
     */
    public void pressDotKey() {
        if (!screen.contains(".")) screen = screen + ".";
        newInput = false;
    }

    /**
     * Empfängt den Befehl der gedrückten Vorzeichenumkehrstaste ("+/-").
     * Zeigt der Bildschirm einen positiven Wert an, so wird ein "-" links angehängt, der Bildschirm
     * aktualisiert und die Inhalt fortan als negativ interpretiert.
     * Zeigt der Bildschirm bereits einen negativen Wert mit führendem Minus an, dann wird dieses
     * entfernt und der Inhalt fortan als positiv interpretiert.
     */
    public void pressNegativeKey() {
        screen = screen.startsWith("-") ? screen.substring(1) : "-" + screen;
    }

    /**
     * Empfängt den Befehl der gedrückten "="-Taste.
     * Wurde zuvor keine Operationstaste gedrückt, passiert nichts.
     * Wurde zuvor eine binäre Operationstaste gedrückt und zwei Operanden eingegeben, wird das
     * Ergebnis der Operation angezeigt. Falls hierbei eine Division durch Null auftritt, wird "Error" angezeigt.
     * Wird die Taste weitere Male gedrückt (ohne andere Tasten dazwischen), so wird die letzte
     * Operation (ggf. inklusive letztem Operand) erneut auf den aktuellen Bildschirminhalt angewandt
     * und das Ergebnis direkt angezeigt.
     */
    public void pressEqualsKey() {
        numbers[numberIndex] = Double.parseDouble(screen); // Letzte Zahl speichern
        calculateResult();
        newInput = true;
    }
    private void calculateResult() {
        // Erster Durchlauf: Multiplikation und Division auswerten
        double[] tempNumbers = new double[10];
        String[] tempOperators = new String[9];
        int tempIndex = 0;

        tempNumbers[0] = numbers[0];
        for (int i = 0; i < operatorIndex; i++) {
            if (operators[i].equals("x") || operators[i].equals("/")) {
                double left = tempNumbers[tempIndex];
                double right = numbers[i + 1];
                double result;

                if (operators[i].equals("x")) {
                    result = left * right;
                } else {
                    if (right == 0) {
                        screen = "Error";
                        resetCalculator();
                        return;
                    }
                    result = left / right;
                }
                tempNumbers[tempIndex] = result;
            } else {
                tempOperators[tempIndex] = operators[i];
                tempNumbers[++tempIndex] = numbers[i + 1];
            }
        }

        // Zweiter Durchlauf: Addition und Subtraktion
        double result = tempNumbers[0];
        for (int i = 0; i < tempIndex; i++) {
            if (tempOperators[i].equals("+")) {
                result += tempNumbers[i + 1];
            } else if (tempOperators[i].equals("-")) {
                result -= tempNumbers[i + 1];
            }
        }

        screen = Double.toString(result);
        if (screen.endsWith(".0")) screen = screen.substring(0, screen.length() - 2);

        resetCalculator();
    }

    private void shiftArrays(int index) {
        for (int j = index; j < numberIndex - 1; j++) {
            numbers[j] = numbers[j + 1];
            operators[j] = operators[j + 1];
        }
        numberIndex--;
        operatorIndex--;
    }
    private void resetCalculator() {
        numbers = new double[10];
        operators = new String[9];
        numberIndex = 0;
        operatorIndex = 0;
        newInput = true;
    }
    private void executeImmediateOperation() {
        double left = numbers[numberIndex - 1];
        double right = Double.parseDouble(screen);
        double result;

        if (operators[operatorIndex - 1].equals("x")) {
            result = left * right;
        } else {
            if (right == 0) {
                screen = "Error";
                resetCalculator();
                return;
            }
            result = left / right;
        }

        numbers[numberIndex - 1] = result;
        newInput = true;
    }



}