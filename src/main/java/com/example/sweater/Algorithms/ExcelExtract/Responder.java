package com.example.sweater.Algorithms.ExcelExtract;

import java.util.ArrayList;

public class Responder {
//    public ArrayList<String> answers = new ArrayList<>();

    public boolean answeredThis(String answer){
        for (Answer a : objectAnswers) {
            if (a.getContent().equals(answer))
                return true;
        }
        return false;
    }


    public ArrayList<Answer> objectAnswers = new ArrayList<>();
}
