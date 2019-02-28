package com.example.SA.Service;

import java.util.Random;

public class Templates {
    public enum Type {
        ALL,
        MIDDLE,
        MAX,
        MIN
    }

    private static final Random rnd = new Random();

    private Templates() {
    }

    public static String getNext(Type type) {
        if (type == Type.MAX) {
            return fillGaps(MAX_TEMP, type);
        } else if (type == Type.MIN) {
            return fillGaps(MIN_TEMP, type);
        } else if (type == Type.MIDDLE) {
            return fillGaps(Middle.getNext(), type);
        } else if (type == Type.ALL) {
            return fillGaps(DescAll.getNext(), type);
        }
        // Error
        return "";
    }

    private static void replace(StringBuilder sb, String from, String to) {
        int index = sb.indexOf(from);
        if (index != -1) {
            sb.replace(index, index + from.length(), to);
        }
    }

    private static String fillGaps(String s, Type type) {
        StringBuilder sb = new StringBuilder(s);
        if (type == Type.MAX) {
            replace(sb, "#rate", HighRate.getNext());

        } else if (type == Type.MIN) {
            replace(sb, "#rate", LowRate.getNext());
        }
        replace(sb, "#actors", Actors.getNext());
        replace(sb, "#action", Actions.getNext());
        return sb.toString();
    }

    // Wrappers for lazy loading
    static class Actions {
        static final String[] values = {"ответили", "указали", "отметили", "выбрали", "проголосовали за"};

        static String getNext() {
            return values[rnd.nextInt(values.length)];
        }
    }

    static class Actors {
        static final String[] values = {"респондентов", "опрошенных", "принявших участие в опросе"};

        static String getNext() {
            return values[rnd.nextInt(values.length)];
        }
    }

    static class LowRate {
        static final String[] values = {"Только", "Всего лишь"};

        static String getNext() {
            return values[rnd.nextInt(values.length)];
        }
    }

    static class HighRate {
        static final String[] values = {"Большинство", "Наибольшее количество", "Больше всего"};

        static String getNext() {
            return values[rnd.nextInt(values.length)];
        }
    }

    static class DescAll {
        static final String[] values = {"%.1f #actor на вопрос \"%s\" #action \"%s\""};

        static String getNext() {
            return values[rnd.nextInt(values.length)];
        }
    }

    static class Middle {
        static final String[] values = {", %.1f%% #action \"%s\""};

        static String getNext() {
            return values[0];
//            return values[rnd.nextInt(values.length)];
        }
    }

    private static final String MIN_TEMP = "#rate %.1f%% #actors на вопрос \"%s\" #action \"%s\"";
    private static final String MAX_TEMP = "#rate #actors (%.1f%%) на вопрос \"%s\" #action \"%s\"";
}
