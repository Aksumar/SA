package com.example.SA.Algorithms.descriptionGenerator;

import org.hibernate.engine.jdbc.env.spi.AnsiSqlKeywords;
import org.springframework.data.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import com.example.SA.Algorithms.ExcelExtract.Question;

public class BasicGenerator {
    static Random r = new Random();
    File Dir;

    /**
     * Полное текстовое описание вопроса анкеты
     *
     * @param question вопрос для описания
     * @return описание :-)
     */
    public String describeAll(Question question) {
        StringBuilder sBuilder = new StringBuilder();

        ArrayList<AbstractMap.SimpleEntry<String, Double>> answers = question.getAnswers();

        AbstractMap.SimpleEntry<String, Double> firstEntry = answers.get(0);
        sBuilder.append(String.format("На вопрос \"%s\" %.1f%% %actor %action \"%s\"", question.description,
                firstEntry.getValue() * 100, firstEntry.getKey()));

        replace(sBuilder, "%actor", getRandomStub(Actors.values));
        replace(sBuilder, "%action", getRandomStub(Actions.values));

        for (int i = 1; i < answers.size(); ++i) {
            AbstractMap.SimpleEntry<String, Double> entry = answers.get(i);
            sBuilder.append(String.format(", %.1f%% - \"%s\"", entry.getValue() * 100, entry.getKey()));
        }

        sBuilder.append(". " + System.lineSeparator());
        return sBuilder.toString();
    }

    /**
     * Делает нам мин-максы
     *
     * @param question
     * @param sensitivity
     * @return я хочу спать. сейчас 3:03 апути миня
     */
    public String describeMinMax(Question question, double sensitivity) {
        StringBuilder sBuilder = new StringBuilder();

        ArrayList<AbstractMap.SimpleEntry<String, Double>> answers = question.getAnswers();
        if (answers.size() > 0) {
            // ну это тип сдвиг. Три первых или два или сколько надо
            int offset = (int) (answers.size() * sensitivity);

            // Пример. Вроде так будет работать.
            // "На вопрос как я провел лето большинство (60%) опрошенных указали дома, 30%
            // ответили на курорте, 20% отметили на даче.";
            sBuilder.append("На вопрос #desc #high_rate (#val%%) #actor #action #answer");

            AbstractMap.SimpleEntry<String, Double> highest = answers.get(answers.size() - 1);
            replace(sBuilder, "#desc", question.description);
            replace(sBuilder, "#high_rate", getRandomStub(HighRate.values));
            replace(sBuilder, "#actor", getRandomStub(Actors.values));
            replace(sBuilder, "#action", getRandomStub(Actions.values));
            replace(sBuilder, "#val", String.format("%.1f", highest.getValue() * 100));
            replace(sBuilder, "#answer", highest.getKey());

            for (int i = answers.size() - 2; i < answers.size() - offset; ++i) {
                AbstractMap.SimpleEntry<String, Double> entry = answers.get(i);
                sBuilder.append(String.format(", %.1f %s %s", entry.getValue() * 100, getRandomStub(Actions.values),
                        entry.getKey()));
            }
            sBuilder.append("." + System.lineSeparator());

            AbstractMap.SimpleEntry<String, Double> low = answers.get(offset);
            sBuilder.append("#low_rate (#val%%) #actor #action #answer");

            replace(sBuilder, "#low_rate", getRandomStub(LowRate.values));
            replace(sBuilder, "#actor", getRandomStub(Actors.values));
            replace(sBuilder, "#action", getRandomStub(Actions.values));
            replace(sBuilder, "#val", String.format("%.1f", low.getValue() * 100));
            replace(sBuilder, "#answer", low.getKey());

            for (int i = offset - 1; i >= 0; --i) {
                AbstractMap.SimpleEntry<String, Double> entry = answers.get(i);
                sBuilder.append(String.format(", %.1f %s %s", entry.getValue() * 100, getRandomStub(Actions.values),
                        entry.getKey()));
            }
            sBuilder.append("." + System.lineSeparator());
        }
        return sBuilder.toString();
    }

    public String getDir() {
        return Dir.getAbsolutePath();
    }

    private static StringBuilder replace(StringBuilder sb, String from, String to) {
        int index = sb.indexOf(from);
        if (index != -1) {
            sb.replace(index, index + from.length(), to);
        }
        return sb;
    }

    /**
     * Возвращает рандомное слово из категории (low, high...)
     */
    private static String getRandomStub(String[] stubs) {
        return stubs[r.nextInt(stubs.length)];
    }

    // Wrappers for lazy loading
    static class Actions {
        static final String[] values = {"ответили", "указали", "отметили", "выбрали"};
    }

    static class Actors {
        static final String[] values = {"респондентов", "опрошенных", "принявших участие в опросе"};
    }

    static class LowRate {
        static final String[] values = {"только", "всего лишь", "меньше всего"};
    }

    static class HighRate {
        static final String[] values = {"большинство", "чаще всего", "больше всего"};
    }
}
