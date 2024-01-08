package org.joel.test.springboot.app.sprigboot_test;

import org.joel.test.springboot.app.sprigboot_test.models.Account;
import org.joel.test.springboot.app.sprigboot_test.models.Bank;

import java.math.BigDecimal;

public class Data {
    public static final Account ACCOUNT_001 = new Account(1L, "Joel", new BigDecimal("1000"));
    public static final Account ACCOUNT_002 = new Account(2L, "Andrés", new BigDecimal("2000"));

    public static final Bank BANK = new Bank(1L, "El banco financiero", 0);
}
