package com.example.SA.Service;

import com.example.SA.Algorithms.ExcelExtract.Question;
import com.example.SA.Algorithms.descriptionGenerator.BasicGenerator;
import com.example.SA.Algorithms.descriptionGenerator.Intercouse;
import com.example.SA.Algorithms.descriptionGenerator.Intervals;
import com.example.SA.domain.Servey.Servey;
import com.example.SA.domain.Servey.TableExcel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Основной класс создатель описания анкетирования. Тянет за собой нужные методы
 */
public class DescriptionGenertor {
    File result;
    Servey servey;
    String pathToResult;
    BasicGenerator bg;
    boolean allAnswers;
    boolean minMax;
    boolean intervals;
    boolean compare;
    int q1;
    int q2;

    public DescriptionGenertor(Servey serveyToAnalise, String pathToResult, boolean intervals,
                               boolean minMax, boolean all, boolean compare,
                               int questionToCompare1, int questionTiCompare2) throws IOException {
        servey = serveyToAnalise;
        TableExcel tableToAnalize = servey.getTableToAnalize();
        this.pathToResult = pathToResult;

        result = new File(pathToResult);
        bg = new BasicGenerator();

        this.allAnswers = all;
        this.minMax = minMax;
        this.intervals = intervals;
        this.compare = compare;
        q1 = questionToCompare1;
        q2 = questionTiCompare2;

        if (result.createNewFile())
            System.out.println("Результирующий файл успешно создан");
        else
            System.out.println("Результирующий файл не создан и все плохо");
    }

    public File generateDescription() {
        try (FileWriter writer = new FileWriter(result.getAbsolutePath())) {

            writer.write(generateIntroduction());

            //ЗДЕСЬ ДОЛЖНЫ ВЫЗЫВАТЬСЯ НУЖНЫЕ МЕТОДЫ
            for (Question q : servey.getTableToAnalize().getQuestions()
            ) {
                if (intervals)
                    writer.write(generateSturges());
            }

            if (compare)
                writer.write(generateQuestionComparison(servey.getTableToAnalize().getQuestions().get(q1 - 1),
                        servey.getTableToAnalize().getQuestions().get(q2 - 1)));

            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    private String generateIntroduction() {
        String result = "В ходе настоящего исследования был проведен опрос " + servey.getRespondersName().get(0) + " по теме  : \"" + servey.getHeader() + "\".\n";
        return result + String.format("Данное анкетировано состояло из %d вопросов. " +
                        "Участие в анкетировании приняло %d респондентов.\n",
                servey.getTableToAnalize().getQuestions().size(),
                servey.getTableToAnalize().getResponders().size());
    }

    /**
     * описание вопроса по Формуле Стёрджеса
     *
     * @param questionNumber вопрос на анализ
     * @return описание вопроса по Формуле Стёрджеса
     */
    private String Sturges(int questionNumber) {
        Intervals ints = new Intervals(servey.getTableToAnalize().getQuestions().get(questionNumber),
                servey.getTableToAnalize().getResponders().size());
        return ints.toString();
    }

    private String generateSturges() {
        String result = "";
        for (int i = 0; i < servey.getTableToAnalize().getQuestions().size(); ++i)
            if (servey.getTableToAnalize().getQuestions().get(i).isQuantitative)
                result += Sturges(i);
        return result;
    }


    private String generateQuestionComparison(Question question1, Question question2) {
        return Intercouse.doIntercourse(question1, question2);
    }
}
