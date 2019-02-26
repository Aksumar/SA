package com.example.sweater.Algorithms.ExcelExtract;

import java.util.ArrayList;

public class Transfer {
    public ArrayList<Question> transfer(ArrayList<Responder> responders, ArrayList<Question> questions) {
        for (int k = 0; k < questions.size(); ++k) {
            for (Responder responder : responders) {
//                questions.get(k).addResponse(responder.answers.get(k));
                questions.get(k).addResponse(responder.objectAnswers.get(k).content);
            }
        }
        return questions;
    }
}