import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.HashMap;

public class CalculatorTest {

    Calculator calculator = new Calculator(new HashMap<Currency, BigDecimal>(){{
        put(Currency.getInstance("USD"), new BigDecimal("1.0005"));
        put(Currency.getInstance("JPY"), new BigDecimal("139.04"));
        put(Currency.getInstance("BGN"), new BigDecimal("1.9558"));
    }});

    @Test
    public void check_if_calculate_of_not_inserted_currency_returns_null(){
        Assert.assertNull(calculator.calculate(Currency.getInstance("PLN"),BigDecimal.valueOf(987.54)));
    }

    @Test
    public void check_if_calculate_of_not_inserted_currency_throws_nullPointerException(){
        Assert.assertThrows(NullPointerException.class, (ThrowingRunnable) calculator.calculate(Currency.getInstance("PLN"),BigDecimal.valueOf(987.54)));
    }

    @Test
    public void check_if_calculate_returns_correct_answer_rounded_to_2_decimals_halfDown(){
        Currency currency = Currency.getInstance("USD");
        BigDecimal bigDecimal = BigDecimal.valueOf(87.65);
        Assert.assertEquals(calculator.calculate(currency,bigDecimal),
                bigDecimal.multiply(calculator.getExchangeRate().get(currency)).setScale(2, RoundingMode.HALF_DOWN));
    }

    @Test
    public void check_if_calculate_of_negative_value_returns_null(){
        Assert.assertNull(calculator.calculate(Currency.getInstance("JPY"),BigDecimal.valueOf(-56.64)));
    }

    @Test
    public void check_if_calculate_of_negative_value_throws_runtimeException(){
        Assert.assertThrows(RuntimeException.class, (ThrowingRunnable) calculator.calculate(Currency.getInstance("JPY"),BigDecimal.valueOf(-56.64)));
    }
}
