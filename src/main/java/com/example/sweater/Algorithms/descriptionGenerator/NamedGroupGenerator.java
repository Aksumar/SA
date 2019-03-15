package com.example.sweater.Algorithms.descriptionGenerator;

import com.example.sweater.Algorithms.ExcelExtract.Answer;
import com.example.sweater.Algorithms.ExcelExtract.Question;
import com.example.sweater.Algorithms.ExcelExtract.Responder;
import com.example.sweater.domain.Servey.TableExcel;
import jdk.nashorn.internal.ir.ReturnNode;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;

public class NamedGroupGenerator {
    TableExcel tablo;

    public NamedGroupGenerator(TableExcel table) {
        tablo = table;
    }

    public Group getGroupByNumbers(int[] numbers, String groupName) {
        ArrayList<Responder> allResponders = tablo.getResponders();
        ArrayList<Responder> respondersToGroup = new ArrayList<>();

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] < allResponders.size())
                respondersToGroup.add(allResponders.get(i));
        }

        return new Group(groupName, respondersToGroup);
    }

    public Group getResponersByAnswer(String answer, String groupName) { //TODO: test
        ArrayList<Responder> allResps = tablo.getResponders();
        ArrayList<Responder> result = new ArrayList<>();

        for (Responder r : allResps) {
            if (r.answeredThis(answer))
                result.add(r);
        }
        return new Group(groupName, result);
    }

    Question createSingleFromMultiple(String baseQuestion) {
        ArrayList<Question> questions = new ArrayList<>();
        ArrayList<Question> allQuestions = tablo.getQuestions();

        for (Question q : allQuestions) {
            if (q.description.contains(baseQuestion + " - "))
                questions.add(q);
        }

        Question single = new Question(baseQuestion);
        for (Question q : questions)
            for (AbstractMap.SimpleEntry<String, Double> a : q.getAnswers())
                single.addResponse(a.getKey());
        return single;
    }

    Group getGroupByQandA(int QuestionNumber, String answer, String groupName) {
        Responder[] allResps = ((Responder[]) tablo.getResponders().toArray());
        ArrayList<Responder> group = new ArrayList<>();
        Question q = ((Question[]) tablo.getQuestions().toArray())[QuestionNumber];

        ArrayList<String> answers = q.getUnsortedAnswers();

        for (int i = 0; i < answers.size(); i++)
            if (((String[]) answers.toArray())[i].equals(answer))
                group.add(allResps[i]);

        return new Group(groupName, group);
    }

    int[] getGroupNumbers(Group g){
        Responder[] allResps = ((Responder[]) tablo.getResponders().toArray());
        Responder[] groupResps = ((Responder[]) g.people.toArray());
        int N = g.people.size();
        int[] result = new int[N];

        for (int i = 0; i < N; i++)
            for (int j = 0; j < allResps.length; j++)
                if (groupResps[i].equals(allResps[j]))
                {
                    result[i] = j;
                    break;
                }

        return result;
    }


    String getGroupDescriptionBy(int questionNumber, String answer, String groupName, int questionToGroup){
        Group g = getGroupByQandA(questionNumber, answer, groupName);

        Question q = ((Question[]) tablo.getQuestions().toArray())[questionToGroup];
        return g.speak(q);
    }
}



//    public ArrayList<Responder> getRespondersByPercentage(int from, int to, Answer ans){ //TODO: test
//        ArrayList<Responder> allResps = tablo.getResponders();
//        ArrayList<Responder> result = new ArrayList<>();
//
//        for (Responder r : allResps) {
//            if (r.objectAnswers.contains(ans))
//                if ( ans.popularity*100 > from && ans.popularity*100 < to)
//                    result.add(r);
//        }
//        return result;
//    }
//
//    public Group createMetaGroup(String[] answers, String groupName){ //TODO: test
//        ArrayList<Responder> allResponders = tablo.getResponders();
//        ArrayList<Responder> respondersToGroup = new ArrayList<>();
//
//        for (Responder r : allResponders) {
//            boolean okay = true;
//            for (String answer : answers)
//                if (!r.answeredThis(answer))
//                    okay = false;
//
//                if (okay)
//                    respondersToGroup.add(r);
//        }
//        return new Group(groupName, respondersToGroup);
//    }
//
//    public static String tellMeAbout(Group group){
//        StringBuilder result = new StringBuilder("Группа " + group.name);
//
//
//        return result.toString();
//    }
//}

