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

    public String getDir() {
        return Dir.getAbsolutePath();
    }

    // Wrappers for lazy loading
    public static class Actions {
        public static final String[] values = { "ответили", "указали", "отметили", "выбрали" };
    }

    public static class Actors {
        public static final String[] values = { "респондентов", "опрошенных", "принявших участие в опросе" };
    }

    public static class LowRate {
        public static final String[] values = { "только", "всего лишь", "меньше всего" };
    }

    public static class HighRate {
        public static final String[] values = { "большинство", "чаще всего", "больше всего" };
    }
}
