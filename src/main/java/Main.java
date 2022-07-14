import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        Calculator calculator = new Calculator(new Parser("src/main/resources/eurofxref-daily.xml").getExchangeRateMap());
        Scanner scanner = new Scanner(System.in);
        ArrayList<Currency> currencies = new ArrayList<>(calculator.getExchangeRate().keySet());

        System.out.println("Type 'q' to quit");
        System.out.println("List of available currencies:");
        currencies.forEach(v -> System.out.print(v + " "));
        System.out.println();

        while (!currencies.isEmpty()) {

            System.out.println("Insert currency: ");
            String currency = scanner.next().toUpperCase();
            if (currencies.stream().map(Currency::toString).collect(Collectors.toList()).contains(currency)) {
                System.out.println("Insert value: ");
                if (scanner.hasNextBigDecimal()) {
                    BigDecimal value = scanner.nextBigDecimal().setScale(2, RoundingMode.HALF_DOWN);
                    System.out.println(value + " EUR = " + calculator.calculate(Currency.getInstance(currency), value) + " " + currency);
                } else {
                    if (scanner.next().matches("[qQ]")) break;
                    System.out.println("Invalid value");
                }
            } else {
                if (currency.equals("Q")) break;
                System.out.println("Invalid currency");
            }

        }
        scanner.close();
    }

}
