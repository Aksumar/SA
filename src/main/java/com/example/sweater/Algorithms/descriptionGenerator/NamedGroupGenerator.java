package com.example.sweater.Algorithms.descriptionGenerator;

import com.example.sweater.Algorithms.ExcelExtract.ExcelReader;
import com.example.sweater.Algorithms.ExcelExtract.Question;
import com.example.sweater.Algorithms.ExcelExtract.Responder;

import java.io.IOException;
import java.util.ArrayList;

public class NamedGroupGenerator {
    ExcelReader exc;
    public ArrayList<Group> groups = new ArrayList<>();

    public NamedGroupGenerator(ExcelReader er, int[][] numbers, String[] names) throws IOException{
        exc = er;

        ArrayList<Responder> allResponders = er.read();

        for (int i = 0; i < numbers.length; i++) {
            int[] groupIndexes = numbers[i];
            ArrayList<Responder> respodersToGroup = new ArrayList<>();
            //forming a group of guys
            for (Responder resp : allResponders){
                if (groupIndexes[i] < allResponders.size())
                    respodersToGroup.add(resp);
            }
            //creating a named group and putting it to arraylist
            Group g = new Group(names[i], respodersToGroup);
            groups.add(g);
        }
    }

    public ArrayList<Responder> getResponersByAnswer(String answer)throws IOException{
        ArrayList<Responder> allResps = exc.read();
        ArrayList<Responder> result = new ArrayList<>();

        for (Responder r : allResps ) {
            if (r.answers.contains(answer))
                result.add(r);
        }
        return result;
    }

    public ArrayList<Responder> getRespondersByPercentage(int from, int to) throws IOException{
        ArrayList<Question> allQuestions = exc.questions();
        ArrayList<Responder> result = new ArrayList<>();

        return null;
    }
}

