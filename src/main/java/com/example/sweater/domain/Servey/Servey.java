package com.example.sweater.domain.Servey;

import com.example.sweater.domain.User.User;
import org.apache.poi.util.Internal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * Объект самой анкеты
 */
public class Servey {
    User userUploader;

    String pathToResult;
    /**
     * Объект-таблицы, пришедший на анализ.
     */
    private TableExcel tableToAnalize;

    int respondersAmount;
    /**
     * В данном лсите хранятся номера важных для пользователя вопросов.
     * По данным вопросам будет запущен полный анализ
     */
    private List<Integer> importantQuestions;

    private List<GroupOfQuestions> groupOfQuestions;

    private List<Term> terms;

    public Servey(String pathToExcelFile, User user) throws IOException {
        tableToAnalize = new TableExcel(pathToExcelFile, user);

        pathToResult = "result" + user.getUsername().toUpperCase() + ".txt";
        tableToAnalize = new TableExcel(pathToExcelFile, user);

        respondersAmount = tableToAnalize.getResponders().size();
        File result = new File(pathToResult);
        if (result.createNewFile())
            System.out.println("Результирующий файл успешно создан");
        else
            System.out.println("Результирующий файл не создан и все плохо");


        generateIntroduction();
        for (int i = 0; i < tableToAnalize.getQuestions().size(); ++i)
            if (tableToAnalize.getQuestions().get(i).isQuantitative)
                basicAnswerAnalyser(i);

    }

    public synchronized void generateIntroduction() {
        try (FileWriter writer = new FileWriter(pathToResult, true)) {
            String text = String.format("Данное анкетировано состояло из %d вопросов. " +
                            "Участие в анкетировании приняло %d респондентов.\n",
                    tableToAnalize.getQuestions().size(), respondersAmount);
            writer.write(text);

            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Описательный анализ происходящего
     */
    public synchronized void basicAnswerAnalyser(int questionNumber) {
        try (FileWriter writer = new FileWriter(pathToResult, true)) {
            Intervals ints = new Intervals(tableToAnalize.getQuestions().get(questionNumber), respondersAmount);
            writer.write(ints.toString());
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
