import com.sun.security.auth.callback.TextCallbackHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Calculator {

    private Map<Currency, BigDecimal> exchangeRate;

    public Calculator(Map<Currency, BigDecimal> exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal calculate(Currency currency, BigDecimal bigDecimal){
        return bigDecimal.multiply(exchangeRate.get(currency)).setScale(2, RoundingMode.HALF_DOWN);
    }


    public Map<Currency, BigDecimal> getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Map<Currency, BigDecimal> exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calculator that = (Calculator) o;
        return Objects.equals(exchangeRate, that.exchangeRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeRate);
    }

    @Override
    public String toString() {
        return "Calculator{" +
                "exchangeRate=" + exchangeRate +
                '}';
    }
}
