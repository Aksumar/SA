package com.example.sweater.Algorithms.descriptionGenerator;

import com.example.sweater.Algorithms.ExcelExtract.ExcelReader;
import com.example.sweater.Algorithms.ExcelExtract.Question;

import java.io.IOException;
import java.util.ArrayList;

public class QuestionGroup {
    public ArrayList<ArrayList<Question>> groupedQs = new ArrayList<>();

    public QuestionGroup(ExcelReader er, int[][] numbers) throws IOException{

        ArrayList<Question> allQuestions = er.questions();

        for (int i = 0; i < numbers.length; ++i){
            ArrayList<Question> singleGroup = new ArrayList<>(allQuestions);
            groupedQs.add(singleGroup);
        }
    }
}