package com.example.first.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardUtil {

    public enum CardEnum {

        Visa("Visa"), Master("Mastercard"), JCB("JCB");

        public String code;

        CardEnum(String code) {
            this.code = code;
        }
    }

    //信用卡正则识别
    //1、Visa:^4\\d{15}$
    //2、MasterCard:^(5[1-5][\\d]{2}|222[1-9]|22[3-9][\\d]|2[3-6][\\d]{2}|27[0-1][\\d]|2720)\\d{12}$
    //3、JCB:^35(28|29|([3-8][\\d]))\\d{12}$
    //4、Maestro:^(50|(5[6-9])|(6[\\d]))\\d{10,17}$
    //5、AE:^3[47]\\d{13}$
    //6、aura:^5\\d{18}$
    //7、diners:^36\\d{12}|3(?:0[0-5]|[68][0-9])[0-9]{11}$
    //8、discover:^6(?:011|5\\d{2})\\d{12}$
    //9、hipercard:^(?:606282|637095|637568)\\d{10}|38\\d{14,17}$
    //10、elo:^(?:50670[7-8]|506715|50671[7-9]|50672[0-1]|50672[4-9]|50673[0-3]|506739|50674[0-8]|50675[0-3]|50677[4-8]|50900[0-9]|50901[3-9]|50902[0-9]|50903[1-9]|50904[0-9]|50905[0-9]|50906[0-4]|50906[6-9]|50907[0-2]|50907[4-5]|636368|636297|504175|438935|40117[8-9]|45763[1-2]|457393|431274|50907[6-9]|50908[0-9]|627780)[0-9]*$
    private static final Map<String, String> CARD_MAP = new HashMap<>();

    static {
        CARD_MAP.put(CardEnum.Visa.code, "^4\\d{15}$");
        CARD_MAP.put(CardEnum.Master.code, "^(5[1-5][\\d]{2}|222[1-9]|22[3-9][\\d]|2[3-6][\\d]{2}|27[0-1][\\d]|2720)\\d{12}$)");
        CARD_MAP.put(CardEnum.JCB.code, "^35(28|29|([3-8][\\d]))\\d{12}$");
    }

    public static String recognizeType(String cardNumber) {
        for (Map.Entry<String, String> entry : CARD_MAP.entrySet()) {
            Matcher matcher = Pattern.compile(entry.getValue()).matcher(cardNumber);
            if (matcher.find()) {
                return entry.getKey();
            }
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(recognizeType("4242424242424242"));
    }
}
