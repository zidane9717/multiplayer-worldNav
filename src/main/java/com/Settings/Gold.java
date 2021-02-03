package com.Settings;

import java.math.BigDecimal;

public enum Gold{
    FIFTY(new BigDecimal("50.0")),
    TWENTY_FIVE(new BigDecimal("25.0")),
    ZERO(new BigDecimal("0")),
    HUNDRED(new BigDecimal("100"));

    private final BigDecimal value;

    Gold(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue(){
        return this.value;
    }

}
