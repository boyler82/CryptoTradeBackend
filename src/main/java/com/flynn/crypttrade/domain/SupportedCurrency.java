package com.flynn.crypttrade.domain;

public enum SupportedCurrency {
    USD, PLN, EUR, GBP;

    public static boolean isSupported(String currency) {
        for (SupportedCurrency c : values()) {
            if (c.name().equalsIgnoreCase(currency)) {
                return true;
            }
        }
        return false;
    }
}
