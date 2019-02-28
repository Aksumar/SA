package com.example.sweater.Algorithms.descriptionGenerator;

import com.example.sweater.Algorithms.ExcelExtract.Answer;
import com.example.sweater.Algorithms.ExcelExtract.Responder;
import com.example.sweater.domain.Servey.TableExcel;

import java.io.IOException;
import java.util.ArrayList;

public class NamedGroupGenerator {
    TableExcel tablo;

    public NamedGroupGenerator(TableExcel table){
        tablo = table;
    }

    public Group getGroupByNumbers(int[] numbers, String groupName){
        ArrayList<Responder> allResponders = tablo.getResponders();
        ArrayList<Responder> respondersToGroup = new ArrayList<>();

        for (int i = 0; i < numbers.length; i++){
            if (numbers[i] < allResponders.size())
                respondersToGroup.add(allResponders.get(i));
        }

        return new Group(groupName, respondersToGroup);
    }

    public Group getResponersByAnswer(String answer, String groupName){ //TODO: test
        ArrayList<Responder> allResps = tablo.getResponders();
        ArrayList<Responder> result = new ArrayList<>();

        for (Responder r : allResps ) {
            if (r.answeredThis(answer))
                result.add(r);
        }
        return new Group(groupName, result);
    }

    public ArrayList<Responder> getRespondersByPercentage(int from, int to, Answer ans){ //TODO: test
        ArrayList<Responder> allResps = tablo.getResponders();
        ArrayList<Responder> result = new ArrayList<>();

        for (Responder r : allResps) {
            if (r.objectAnswers.contains(ans))
                if ( ans.popularity*100 > from && ans.popularity*100 < to)
                    result.add(r);
        }
        return result;
    }

    public Group createMetaGroup(String[] answers, String groupName){ //TODO: test
        ArrayList<Responder> allResponders = tablo.getResponders();
        ArrayList<Responder> respondersToGroup = new ArrayList<>();

        for (Responder r : allResponders) {
            boolean okay = true;
            for (String answer : answers)
                if (!r.answeredThis(answer))
                    okay = false;

                if (okay)
                    respondersToGroup.add(r);
        }
        return new Group(groupName, respondersToGroup);
    }

    public static String tellMeAbout(Group group){
        StringBuilder result = new StringBuilder("Группа " + group.name);


        return result.toString();
    }
}

