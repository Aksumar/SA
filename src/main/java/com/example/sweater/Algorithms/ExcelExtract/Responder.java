package com.example.sweater.Algorithms.ExcelExtract;

import java.util.ArrayList;

public class Responder {
    ArrayList<String> answers = new ArrayList<>();

    public boolean answeredThis(String answer) {
        for (String ans : answers)
            if (ans.equals(answer))
                return true;

        return false;
    }

    @Override
    public boolean equals(Object o)
    {
        Responder r = (Responder) o;
        String[] rAns = ((String[])r.answers.toArray());
        String[] tAns = ((String[])answers.toArray());

        if (rAns.length != tAns.length) return false;

        for (int i = 0; i < tAns.length; i++)
            if(!tAns[i].equals(rAns[i]))
                return false;

        return true;
    }
}
