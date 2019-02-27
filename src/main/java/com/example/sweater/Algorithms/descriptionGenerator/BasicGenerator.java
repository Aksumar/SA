package com.example.sweater.Algorithms.descriptionGenerator;

import org.springframework.data.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import com.example.sweater.Algorithms.ExcelExtract.Question;

public class BasicGenerator {
    static Random r = new Random();
    File Dir;

    // structure:
    // Большинство (%) выбрало <var>
    public BasicGenerator(String filename) {
        Dir = new File(filename);
    }

    public String analyze(Pair<String, Double> answer) throws IOException {
        Boolean noMoMacros = false;
        String randomTemplate = RandomTemplate("templates");

        while (!noMoMacros) {
            noMoMacros = true;

            String[] _split = randomTemplate.split("<|>");
            randomTemplate = "";
            for (int i = 0; i < _split.length; i++) {
                if (_split[i].contains("~")) {
                    if (_split[i].contains("%")) {
                        _split[i] = answer.getFirst() + "%";
                        noMoMacros = false;
                    } else if (_split[i].contains("r")) {
                        _split[i] = answer.getFirst();
                        noMoMacros = false;
                    }
                } else {
                    if (_split[i].equals(""))
                        continue;

                    String temp;
                    try {
                        temp = RandomTemplate(_split[i]);
                        noMoMacros = false;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        temp = _split[i];
                    }
                    _split[i] = temp;
                }
                randomTemplate += _split[i];
            }
        }
        return randomTemplate;
    }

    public String RandomTemplate(final String filename) throws IOException, ArrayIndexOutOfBoundsException {
        if (!Dir.exists())
            throw new IOException("File does not exist.");
        File file = Objects.requireNonNull(Dir.listFiles((dir, name) -> name.contains(filename)))[0];

        FileInputStream inputStream = new FileInputStream(file);

        ArrayList<String> Templates = new ArrayList<String>();
        Scanner s = new Scanner(inputStream);
        String line;

        int i = -1;
        while (s.hasNext()) {
            i++;
            Templates.add("");

            line = s.nextLine();
            while (!line.equals("#")) {
                Templates.set(i, Templates.get(i) + line);

                if (!s.hasNext())
                    break;
                line = s.nextLine();
            }
        }
        s.close();
        return Templates.get(r.nextInt(Templates.size()));
    }

    public String describeAll(Question question) {
        StringBuilder sBuilder = new StringBuilder();

        ArrayList<AbstractMap.SimpleEntry<String, Double>> answers = question.getAnswers();

        AbstractMap.SimpleEntry<String, Double> firstEntry = answers.get(0);
        sBuilder.append(String.format("На вопрос \"%s\" %.1f%% %actor %action \"%s\"", question.description,
                firstEntry.getValue() * 100, firstEntry.getKey()));

        replace(sBuilder, "%actor", Actors.values[r.nextInt(Actors.length)]);
        replace(sBuilder, "%action", Actions.values[r.nextInt(Actions.length)]);

        for (int i = 1; i < answers.size(); ++i) {
            AbstractMap.SimpleEntry<String, Double> entry = answers.get(i);
            sBuilder.append(String.format(", %.1f%% - \"%s\"", entry.getValue() * 100, entry.getKey()));
        }

        sBuilder.append(". " + System.lineSeparator());
        return sBuilder.toString();
    }

    public String describeMinMax(Question question, double sensitivity) {
        StringBuilder sBuilder = new StringBuilder();

        ArrayList<AbstractMap.SimpleEntry<String, Double>> answers = question.getAnswers();
        int offset = (int) (answers.size() * sensitivity);

        for (int i = 0; i < offset; ++i) {
        }

        for (int i = answers.size() - 1; i > answers.size() - offset - 1; --i) {
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

    // Wrappers for lazy loading
    static class Actions {
        static final String[] values = { "ответили", "указали", "отметили", "выбрали" };
        static final int length = values.length;
    }

    static class Actors {
        static final String[] values = { "респондентов", "опрошенный", "принявших участие в опросе" };
        static final int length = values.length;
    }

    static class LowRate {
        static final String[] values = { "только", "всего лишь", "меньше всего" };
        static final int length = values.length;
    }

    static class HighRate {
        static final String[] values = { "большинство", "чаще всего", "больше всего" };
        static final int length = values.length;
    }
}
