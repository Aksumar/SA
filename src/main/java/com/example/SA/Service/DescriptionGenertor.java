package com.example.SA.Service;

import com.example.SA.domain.Servey.Intervals;
import com.example.SA.domain.Servey.Servey;
import com.example.SA.domain.Servey.TableExcel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DescriptionGenertor {
    File result;
    Servey servey;
    String pathToResult;

    public DescriptionGenertor(Servey serveyToAnalise, String pathToResult) throws IOException {
        servey = serveyToAnalise;
        TableExcel tableToAnalize = servey.getTableToAnalize();
        this.pathToResult = pathToResult;

        result = new File(pathToResult);
        if (result.createNewFile())
            System.out.println("Результирующий файл успешно создан");
        else
            System.out.println("Результирующий файл не создан и все плохо");
    }

    public File generateDescription() {
        try (FileWriter writer = new FileWriter(result.getAbsolutePath(), true)) {

            //ЗДЕСЬ ДОЛЖНЫ ВЫЗЫВАТЬСЯ НУЖНЫЕ МЕТОДЫ
            writer.write(generateIntroduction());
            writer.write(generateSturges());

            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    private String generateIntroduction() {
        return String.format("Данное анкетировано состояло из %d вопросов. " +
                        "Участие в анкетировании приняло %d респондентов.\n",
                servey.getTableToAnalize().getQuestions().size(),
                servey.getTableToAnalize().getResponders().size());
    }

    /**
     * писание воппроса по Формуле Стёрджеса
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
}
