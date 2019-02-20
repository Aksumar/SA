package com.example.sweater.Algorithms.ExcelExtract;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class Question {
    public  String description;
    private TreeMap<String, Integer> responses = new TreeMap<>();
    private int count = 0;

    public Question(String d) {
        description = d;
    }

    public void addResponse(String desc) {
        int theResponsePopularity = responses.getOrDefault(desc, 0);

        responses.put(desc, theResponsePopularity + 1);
        ++count;
    }

    public ArrayList<AbstractMap.SimpleEntry<String, Double>> getAnswers() {
        ArrayList<AbstractMap.SimpleEntry<String, Double>> answerSet = new ArrayList<>();

        responses.forEach((k, v) -> answerSet.add(new AbstractMap.SimpleEntry<>(k, ((double) v) / count)));
        answerSet.sort(Comparator.comparingDouble(AbstractMap.SimpleEntry::getValue));
        return answerSet;
    }
}
