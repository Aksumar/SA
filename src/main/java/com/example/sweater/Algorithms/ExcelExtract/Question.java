package com.example.sweater.Algorithms.ExcelExtract;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class Question {
    ExcelReader er;
    public  String description;
    private TreeMap<String, Integer> responses = new TreeMap<>();
    private int count = 0;

    ArrayList<Answer> answers = new ArrayList<>();  //addition

    public Question(String d) {
        description = d;
    }

    public void addResponse(String desc) {
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

    Question createSingleFromMultiple(String baseQuestion){
        ArrayList<Question> questions = new ArrayList<>();
        ArrayList<Question> allQuestions = new ArrayList<>();
        try{
            allQuestions = er.questions();
        } catch (IOException e) {e.printStackTrace();}

        for (Question q : allQuestions) {
            if (q.description.contains(baseQuestion + " - "))
                questions.add(q);
        }

        Question single = new Question(baseQuestion);
        for (Question q : questions)
            for (String a : q.responses.keySet())
                single.addResponse(a);
        return single;
    }
}
