package com.example.SA.Algorithms.ExcelExtract;

import java.util.*;

public class Question {
    public final String description;
    public boolean isQuantitative = true;

    private TreeMap<String, Integer> responses = new TreeMap<>();
    private int count = 0;

    ArrayList<Answer> answers = new ArrayList<>();  //addition

    public Question(String d) {
        description = d;
    }

    void addResponse(String desc) {
        if (isQuantitative) {
            try {
                Double.parseDouble(desc);
            } catch (Exception e) {
                isQuantitative = false;
            }
        }

        int theResponsePopularity = responses.getOrDefault(desc, 0);
        responses.put(desc, theResponsePopularity + 1);
        ++count;


        answers.add(new Answer(desc, this));   //addition
    }


    public ArrayList<AbstractMap.SimpleEntry<String, Double>> getAnswers() {
        ArrayList<AbstractMap.SimpleEntry<String, Double>> answerSet = new ArrayList<>();

        responses.forEach((k, v) -> answerSet.add(new AbstractMap.SimpleEntry<>(k, ((double) v) / count)));
        answerSet.sort(Comparator.comparingDouble(AbstractMap.SimpleEntry::getValue));
        return answerSet;
    }

    public int getCountResponse() {
        return count;
    }

    public TreeMap<String, Integer> getResponses() {
        return responses;
    }

    public void setResponses(TreeMap<String, Integer> responses) {
        this.responses = responses;
    }

}
