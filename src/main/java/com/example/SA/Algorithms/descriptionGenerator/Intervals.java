package com.example.SA.Algorithms.descriptionGenerator;

import com.example.SA.Algorithms.ExcelExtract.Question;

import java.util.*;
import java.util.Map.Entry;

public class Intervals {
    Question qToAnalyse;
    int amountOfIntervals;
    double stepOfInterval;
    List<OneInterval> histogram;
    int respondersAmount;

    public Intervals(Question question, int respondersAmount) {
        qToAnalyse = question;

        TreeMap<Double, Integer> doubleIntegerTreeMap = new TreeMap<>();
        Iterator<Entry<String, Integer>> itr = question.getResponses().entrySet().iterator();

        for (int i = 0; i < qToAnalyse.getResponses().size(); ++i) {
            Map.Entry<String, Integer> answer = itr.next();
            doubleIntegerTreeMap.put(Double.parseDouble(answer.getKey()), answer.getValue());
        }

        this.respondersAmount = respondersAmount;

        //пределяем шаг описания по формуле Стейджерса
        amountOfIntervals = 1 + (int) (3.322 * Math.floor(Math.log10(question.getCountResponse())));
        double minValue = doubleIntegerTreeMap.firstKey();
        double maxValue = doubleIntegerTreeMap.lastKey();

        //количество значений в шаге
        stepOfInterval = (int) Math.ceil((maxValue - minValue) / amountOfIntervals);

        //коллекция интервалов
        histogram = new ArrayList<OneInterval>();

        Double minValueBefore = minValue;
        Iterator<Entry<Double, Integer>> it = doubleIntegerTreeMap.entrySet().iterator();
        Map.Entry<Double, Integer> answer = it.next();
        for (int i = 0; i < amountOfIntervals; ++i) {
            OneInterval currentInterval = new OneInterval();
            currentInterval.minValue = minValueBefore;
            currentInterval.maxValue = minValueBefore + stepOfInterval - 1;


            while (answer.getKey() <= currentInterval.maxValue) {
                currentInterval.amountAnswers += answer.getValue();
                if (!it.hasNext())
                    break;
                answer = it.next();
            }

            minValueBefore = currentInterval.maxValue + 1;
            histogram.add(currentInterval);
        }

    }

    @Override
    public String toString() {
        String result = String.format("При следующем описании вопроса <%s>\n была использована формула Стейджерса с шагом %.1f\n" +
                " и кол-вом интервалов %d.\n", qToAnalyse.description, stepOfInterval, amountOfIntervals);
        for (int i = 0; i < amountOfIntervals; ++i)
            result += histogram.get(i).toString(i, respondersAmount);
        OneInterval maxInterval = histogram.stream().max(Comparator.comparing(interval -> interval.amountAnswers)).get();
        OneInterval minInterval = histogram.stream().min(Comparator.comparing(interval -> interval.amountAnswers)).get();
        result += String.format("Таким образом, больше всего значений - %d -  лежат в промежутке : (%.1f - %.1f).\n",
                maxInterval.amountAnswers, maxInterval.minValue, maxInterval.maxValue) +
                String.format("А меньше всего значений - %d -  лежат в промежутке : (%.1f - %.1f).\n",
                        minInterval.amountAnswers, minInterval.minValue, minInterval.maxValue);
        return result;
    }
}

class OneInterval {
    double maxValue;
    double minValue;
    int amountAnswers = 0;

    String toString(int numberOfInterval, int inTotal) {
        return String.format("\tВ Интервал № %d : (%.1f - %.1f) входят %d ответов c частотой попадания в интервал %.3f.\n",
                numberOfInterval, minValue, maxValue, amountAnswers, (double) amountAnswers / inTotal);

    }
}
