package com.example.sweater.Algorithms.descriptionGenerator;

import com.example.sweater.Algorithms.ExcelExtract.Question;
import com.example.sweater.Algorithms.ExcelExtract.Responder;

import java.util.ArrayList;

public class Group{
    public ArrayList<Responder> people = new ArrayList<>();
    public String name;
    public Group(String name, ArrayList<Responder> responders){
        this.name = name;
        people.addAll(responders);
    }

    public String speak (Question q){
        StringBuilder output = new StringBuilder("На вопрос \"");
        output.append(q.description);
        output.append("\" группа" + this.name);
        output.append("ЦЕ ЗАТЫЧКО\n");


        return output.toString();
    }
}
