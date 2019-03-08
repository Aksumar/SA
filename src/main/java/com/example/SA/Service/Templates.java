package com.example.SA.Service;

import java.io.*;
import java.util.*;

public class Templates {
    public enum Type {
        ALL,
        MIDDLE,
        MAX,
        MIN
    }

    ////////////////////////////////
    private static final int
        ACTIONS = 0,
        ACTORS = 1,
        LRATE = 2,
        HRATE = 3,
        DESCALL = 4,
        MIDDLE = 5;
    private TemplateBrick[] templates = new TemplateBrick[6];
    ////////////////////////////////

    private static final List<String> _brickNames =
            Arrays.asList(
                    "actions",
                    "actors",
                    "lowRate",
                    "highRate",
                    "describeAll",
                    "middle"
            );

    private Templates(File directory) {
        if (!directory.isDirectory())
            throw new IllegalArgumentException();

        File[] childFiles = directory.listFiles();
        try {
            int i = 0;
            for (File f : childFiles) {
                if (f.getName().contains(_brickNames.get(i))) {
                    templates[i] = new TemplateBrick(f);
                }
                i++;
            }
        } catch (IOException e) {

        }
    }

    public String getNext(Type type) {
        if (type == Type.MAX) {
            return fillGaps(MAX_TEMP, type);
        } else if (type == Type.MIN) {
            return fillGaps(MIN_TEMP, type);
        } else if (type == Type.MIDDLE) {
            return fillGaps(templates[MIDDLE].getNext(), type);
        } else if (type == Type.ALL) {
            return fillGaps(templates[DESCALL].getNext(), type);
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

    private String fillGaps(String s, Type type) {
        StringBuilder sb = new StringBuilder(s);
        if (type == Type.MAX) {
            replace(sb, "#rate", templates[HRATE].getNext());

        } else if (type == Type.MIN) {
            replace(sb, "#rate", templates[LRATE].getNext());
        }
        replace(sb, "#actors", templates[ACTORS].getNext());
        replace(sb, "#action", templates[ACTIONS].getNext());
        return sb.toString();
    }

    private static final String MIN_TEMP = "#rate %.1f%% #actors на вопрос \"%s\" #action \"%s\"";
    private static final String MAX_TEMP = "#rate #actors (%.1f%%) на вопрос \"%s\" #action \"%s\"";
}

//part of a template
//#: Actions : {указали, выбрали и тд} - это TemplateBrick
class TemplateBrick {
    private static final Random rnd = new Random();

    private ArrayList<String> options = new ArrayList<>();
    private ArrayList<String> optionsUnused = new ArrayList<>();

    public TemplateBrick(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        options.clear();
        optionsUnused.clear();

        String line;
        while ((line = br.readLine()) != null) {
            options.add(line);
        }

        br.close();
        fr.close();
    }

    public String getNext() {
        if (optionsUnused.isEmpty()) {
            optionsUnused = new ArrayList<>(options);
        }

        return optionsUnused.remove(rnd.nextInt(optionsUnused.size()));
    }
}
