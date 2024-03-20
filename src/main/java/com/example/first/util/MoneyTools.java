package com.example.first.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoneyTools {
    public static Map<String, BigDecimal> integerMap = new HashMap<>();
    static {
        integerMap.put("PHP", new BigDecimal("300"));
        integerMap.put("IDR", new BigDecimal("78000"));
        integerMap.put("USD", new BigDecimal("5"));
    }
    public static void main(String[] args) {
        System.out.println(isBigMoney("CAD", new BigDecimal("39.99")));
    }

    static Boolean method(String currency, Function<String, BigDecimal> one, Function<BigDecimal, Boolean> two) {
        return one.andThen(two).apply(currency);
    }

    // 判断所传入金额货币是否是大金额
    public static boolean isBigMoney(String currency, BigDecimal money) {
        return method(currency,s -> integerMap.getOrDefault(s, BigDecimal.valueOf(1)), integer -> integer.compareTo(money) < 0);
    }

    // 正则匹配分离金额和货币，0.99USD
    public static String[] splitAmountCurrency(String str) {
        Pattern pattern = Pattern.compile("([0-9]+(\\.[0-9]+)?)([A-Za-z]+)");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            String amount = matcher.group(1);   // 获取金额
            String currency = matcher.group(2); // 获取货币符号
            return new String[]{amount, currency};
        }
        return new String[]{str, ""};
    }

}
