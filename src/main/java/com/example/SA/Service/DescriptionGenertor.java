package com.example.SA.Service;

import com.example.SA.Algorithms.ExcelExtract.Question;
import com.example.SA.Algorithms.descriptionGenerator.BasicGenerator;
import com.example.SA.domain.Servey.Intervals;
import com.example.SA.Algorithms.descriptionGenerator.Intercouse;
import com.example.SA.Algorithms.descriptionGenerator.Intervals;
import com.example.SA.domain.Servey.Servey;
import com.example.SA.domain.Servey.TableExcel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Random;

/**
 * Основной класс создатель описания анкетирования. Тянет за собой нужные методы
 */
public class DescriptionGenertor {
    private File result;
    private Servey survey;
    private String pathToResult;
    private static final Random r = new Random();

    public DescriptionGenertor(Servey surveyToAnalyze, String pathToResult) throws IOException {
        survey = surveyToAnalyze;
        TableExcel tableToAnalyze = survey.getTableToAnalize();
        this.pathToResult = pathToResult;

        result = new File(pathToResult);
        if (result.exists()) {
            if (!result.delete())
                throw new FileSystemException("Can not delete old report " + pathToResult);
        }
        if (result.createNewFile())
            System.out.println("Результирующий файл успешно создан");
        else
            throw new FileSystemException("Can not create file " + pathToResult);
    }

    public File generateDescription() {
        try (FileWriter writer = new FileWriter(result.getAbsolutePath())) {

            // ЗДЕСЬ ДОЛЖНЫ ВЫЗЫВАТЬСЯ НУЖНЫЕ МЕТОДЫ
            writer.write(generateIntroduction());
            writer.write(generateSturges());
            writeFullDescription(writer);
            writer.write(System.lineSeparator());
            writeMinMax(writer, 0.3);

            writer.write(generateQuestionComparison(servey.getTableToAnalize().getQuestions().get(7), servey.getTableToAnalize().getQuestions().get(9)));
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    private String generateIntroduction() {
        String result = "В ходе настоящего исследования был проведен опрос " + survey.getRespondersName().get(0) + " по теме  : \"" + survey.getHeader() + "\".\n";
        return result + String.format("Данное анкетировано состояло из %d вопросов. " +
                        "Участие в анкетировании приняло %d респондентов.\n",
                survey.getTableToAnalize().getQuestions().size(),
                survey.getTableToAnalize().getResponders().size());
    }

    /**
     * описание вопроса по Формуле Стёрджеса
     *
     * @param questionNumber вопрос на анализ
     * @return описание вопроса по Формуле Стёрджеса
     */
    private String Sturges(int questionNumber) {
        Intervals ints = new Intervals(survey.getTableToAnalize().getQuestions().get(questionNumber),
                survey.getTableToAnalize().getResponders().size());
        return ints.toString();
    }

    private String generateSturges() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < survey.getTableToAnalize().getQuestions().size(); ++i)
            if (survey.getTableToAnalize().getQuestions().get(i).isQuantitative)
                result.append(Sturges(i));
        return result.toString();
    }

    private void writeFullDescription(FileWriter writer) throws IOException {
        for (Question question : survey.getTableToAnalize().getQuestions()) {
            writer.write(describeAll(question));
        }
    }

    private void writeMinMax(FileWriter writer, double sensitivity) throws IOException {
        for (Question question : survey.getTableToAnalize().getQuestions()) {
            writer.write(describeMinMax(question, sensitivity));
        }
    }

    /**
     * Полное текстовое описание вопроса анкеты
     *
     * @param question вопрос для описания
     * @return описание :-)
     */
    private String describeAll(Question question) {
        StringBuilder sBuilder = new StringBuilder();

        ArrayList<AbstractMap.SimpleEntry<String, Double>> answers = question.getAnswers();

        AbstractMap.SimpleEntry<String, Double> firstEntry = answers.get(0);
        sBuilder.append(String.format("На вопрос \"%s\" %.1f%% #actor #action %s", question.description,
                firstEntry.getValue() * 100, firstEntry.getKey()));

        replace(sBuilder, "#actor", getRandomStub(BasicGenerator.Actors.values));
        replace(sBuilder, "#action", getRandomStub(BasicGenerator.Actions.values));

        for (int i = 1; i < answers.size(); ++i) {
            AbstractMap.SimpleEntry<String, Double> entry = answers.get(i);
            sBuilder.append(String.format(", %.1f%% - %s", entry.getValue() * 100, entry.getKey()));
        }

        sBuilder.append(". ").append(System.lineSeparator());
        return sBuilder.toString();
    }

    private static void replace(StringBuilder sb, String from, String to) {
        int index = sb.indexOf(from);
        if (index != -1) {
            sb.replace(index, index + from.length(), to);
        }
    }

    /**
     * Возвращает рандомное слово из категории (low, high...)
     */
    private static String getRandomStub(String[] stubs) {
        return stubs[r.nextInt(stubs.length)];
    }

    /**
     * Делает нам мин-максы
     *
     * @param question
     * @param sensitivity
     * @return я хочу спать. сейчас 3:03 апути миня
     */
    public String describeMinMax(Question question, double sensitivity) {
        StringBuilder sBuilder = new StringBuilder();

        ArrayList<AbstractMap.SimpleEntry<String, Double>> answers = question.getAnswers();
        if (answers.size() > 0) {
            // ну это тип сдвиг. Три первых или два или сколько надо
            int offset = (int) (answers.size() * sensitivity);

            // Пример. Вроде так будет работать.
            // "На вопрос как я провел лето большинство (60%) опрошенных указали дома, 30%
            // ответили на курорте, 10% отметили на даче.";
            sBuilder.append("На вопрос #desc #high_rate (#val%) #actor #action #answer");

            AbstractMap.SimpleEntry<String, Double> highest = answers.get(answers.size() - 1);
            replace(sBuilder, "#desc", question.description);
            replace(sBuilder, "#high_rate", getRandomStub(BasicGenerator.HighRate.values));
            replace(sBuilder, "#actor", getRandomStub(BasicGenerator.Actors.values));
            replace(sBuilder, "#action", getRandomStub(BasicGenerator.Actions.values));
            replace(sBuilder, "#val", String.format("%.1f", highest.getValue() * 100));
            replace(sBuilder, "#answer", highest.getKey());

            for (int i = answers.size() - 2; i < answers.size() - offset; ++i) {
                AbstractMap.SimpleEntry<String, Double> entry = answers.get(i);
                sBuilder.append(String.format(", %.1f %s %s", entry.getValue() * 100, getRandomStub(BasicGenerator.Actions.values),
                        entry.getKey()));
            }
            sBuilder.append("." + System.lineSeparator());

            AbstractMap.SimpleEntry<String, Double> low = answers.get(offset);
            sBuilder.append("#low_rate (#val%%) #actor #action #answer");

            replace(sBuilder, "#low_rate", getRandomStub(BasicGenerator.LowRate.values));
            replace(sBuilder, "#actor", getRandomStub(BasicGenerator.Actors.values));
            replace(sBuilder, "#action", getRandomStub(BasicGenerator.Actions.values));
            replace(sBuilder, "#val", String.format("%.1f", low.getValue() * 100));
            replace(sBuilder, "#answer", low.getKey());

            for (int i = offset - 1; i >= 0; --i) {
                AbstractMap.SimpleEntry<String, Double> entry = answers.get(i);
                sBuilder.append(String.format(", %.1f %s %s", entry.getValue() * 100, getRandomStub(BasicGenerator.Actions.values),
                        entry.getKey()));
            }
            sBuilder.append(".").append(System.lineSeparator());
        }
        return sBuilder.toString();
    }


    private String generateQuestionComparison(Question question1, Question question2) {
        return Intercouse.doIntercourse(question1, question2);
    }
}
