package com.example.SA.Algorithms.descriptionGenerator;

import com.example.SA.Algorithms.ExcelExtract.Question;
import com.example.SA.Algorithms.ExcelExtract.Responder;

import java.util.AbstractMap;
import java.util.ArrayList;

public class Group{
    public ArrayList<Responder> people = new ArrayList<>();
    public String name;
    public Group(String name, ArrayList<Responder> responders){
        this.name = name;
        people.addAll(responders);
    }

    public String speak (Question q){
        ArrayList<AbstractMap.SimpleEntry<String, Double>> answers =  q.getAnswers();
        int num = answers.size();
        StringBuilder output = new StringBuilder("На вопрос \"");
        output.append(q.description);
        output.append("\" группа " + this.name);
        output.append(" чаще всего (" + answers.get(num-1).getValue()*100 + "%)" +
                " отвечала так: " + answers.get(num-1).getKey() + ".");

        output.append("Наименее популярным ответом оказался вариант \"" + answers.get(0).getKey() + "\"." +
                " Он набрал " + answers.get(0).getValue()*100 + "%.");

        output.append(" Между ними расположились остальные варинты: ");
        StringBuilder middle = new StringBuilder();
        for (int i = num-2; i > 0; i--){
            middle.append(answers.get(i).getKey() + " - " + answers.get(i).getValue()*100 + "%; ");
        }
        output.append(middle + "\n");

        return output.toString();
    }
}
