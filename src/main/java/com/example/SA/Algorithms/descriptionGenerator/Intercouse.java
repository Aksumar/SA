package com.example.SA.Algorithms.descriptionGenerator;

import com.example.SA.Algorithms.ExcelExtract.Question;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Intercouse {
    public static String doIntercourse(Question milkAtSummer, Question milkAtWinter) {
        ArrayList<String> answers1 = new ArrayList<>();
        ArrayList<String> answers2 = new ArrayList<>();

        for (SimpleEntry<String, Double> ans : milkAtSummer.getAnswers())
            answers1.add(ans.getKey());

        for (SimpleEntry<String, Double> ans : milkAtWinter.getAnswers())
            answers2.add(ans.getKey());

        Set<String> similar = new HashSet<>();

        int avgSize = (answers1.size() + answers2.size()) / 2;
        if (avgSize == 0) {
            return "Не получается сравнить вопросы " + milkAtSummer.description + " и " + milkAtWinter.description;
        }

        for (String s : answers1) {
            if (answers2.contains(s))
                similar.add(s);
        }

        for (String s : answers2) {
            if (answers1.contains(s))
                similar.add(s);
        }

        int differences = avgSize - similar.size();
        if (differences / ((float) avgSize) > 0.1f) {
            return "Не получается сравнить вопросы " + milkAtSummer.description + " и " + milkAtWinter.description;
        }

        String indent = " ";
        StringBuilder sb = new StringBuilder();
        for (SimpleEntry<String, Double> s : milkAtSummer.getAnswers()) {
            if (similar.contains(s.getKey())) {
                sb.append(indent).append("ответ ").append(s.getKey()).append(" на вопрос \"").append(milkAtSummer.description).append("\" составляет ").append(s.getValue() * 100).append("% голосов, что на ");

                SimpleEntry<String, Double> mathchingQuestion = milkAtWinter.getAnswers().stream().filter(x -> x.getKey().equals(s.getKey())).findAny().get();

                double part1 = s.getValue(),
                        part2 = mathchingQuestion.getValue();

                double difference = Math.abs(part1 - part2);
                sb.append(difference * 100).append("% ");

                if (part1 > part2) {
                    sb.append("больше чем ответ ");
                } else if (part1 < part2)
                    sb.append("меньше чем ответ ");
                else sb.append("сопадает с ответом ");

                sb.append(mathchingQuestion.getKey()).append(" на вопрос \"").append(milkAtWinter.description).append("\", составивший ").append(mathchingQuestion.getValue() * 100).append("% голосов.\r\n");

            }
        }
        return sb.toString();
    }

}