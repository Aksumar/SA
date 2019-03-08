package com.example.SA.Service;

import com.example.SA.Algorithms.ExcelExtract.Question;
import com.example.SA.Algorithms.descriptionGenerator.Intercouse;
import com.example.SA.Algorithms.descriptionGenerator.Intervals;
import com.example.SA.domain.Servey.Servey;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Основной класс создатель описания анкетирования. Тянет за собой нужные методы
 */
public class DescriptionGenerator {
    private File result;
    private Servey survey;
    private String pathToResult;
    private boolean allAnswers;
    private boolean minMax;
    private boolean intervals;
    private boolean compare;
    private int q1;
    private int q2;

    private final Templates templates = new Templates(new File("C:\\Users\\alexr\\IdeaProjects\\Bricks"));

    public DescriptionGenerator(Servey serveyToAnalise, String pathToResult, boolean intervals,
                                boolean minMax, boolean all, boolean compare) throws IOException {
        this(serveyToAnalise, pathToResult, intervals, minMax, all, compare, -1, -1);
    }

    public DescriptionGenerator(Servey surveyToAnalyze, String pathToResult, boolean intervals,
                                boolean minMax, boolean all, boolean compare,
                                int questionToCompare1, int questionTiCompare2) throws IOException {
        survey = surveyToAnalyze;
        this.pathToResult = pathToResult;

        result = new File(pathToResult);

        this.allAnswers = all;
        this.minMax = minMax;
        this.intervals = intervals;
        this.compare = compare;
        q1 = questionToCompare1;
        q2 = questionTiCompare2;

        result = new File(pathToResult);
        if (result.exists()) {
            if (!result.delete())
                System.out.println("Не удалось удалить старый файл отчета");
        }
        if (result.createNewFile())
            System.out.println("Результирующий файл успешно создан");
        else
            throw new FileSystemException("Can not create file " + pathToResult);
    }

    public File generateDescription() {
        try (FileWriter writer = new FileWriter(result.getAbsolutePath())) {
            writer.write(generateIntroduction());
            for (Question question : survey.getTableToAnalize().getQuestions()) {
                if (allAnswers) {
                    writeFullDescription(writer, question);
                }
                if (minMax) {
                    writeMinMax(writer, question, 0.2);
                }
                if (intervals)
                    writer.write(generateSturges(question));
            }

            if (compare)
                writer.write(generateQuestionComparison(survey.getTableToAnalize().getQuestions().get(q1 - 1),
                        survey.getTableToAnalize().getQuestions().get(q2 - 1)));

            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    private String generateIntroduction() {
        String result = "В ходе настоящего исследования был проведен опрос " + survey.getRespondersName().get(0) + " по теме \"" + survey.getHeader() + "\".\n";
        return result + String.format("Данное анкетировано состояло из %d вопросов. " +
                        "Участие в анкетировании приняло %d респондентов.\n",
                survey.getTableToAnalize().getQuestions().size(),
                survey.getTableToAnalize().getResponders().size());
    }

    /**
     * описание вопроса по Формуле Стёрджеса
     *
     * @param question вопрос на анализ
     * @return описание вопроса по Формуле Стёрджеса
     */
    private String Sturges(Question question) {
        Intervals ints = new Intervals(question, survey.getTableToAnalize().getResponders().size());
        return ints.toString();
    }

    private String generateSturges(Question question) {
        return Sturges(question);
    }


    private String generateQuestionComparison(Question question1, Question question2) {
        return Intercouse.doIntercourse(question1, question2);
    }

    private void writeFullDescription(FileWriter writer, Question question) throws IOException {
        writer.write(describeAll(question));
        writer.write(System.lineSeparator());
    }

    private void writeMinMax(FileWriter writer, Question question, double sensitivity) throws IOException {
        writer.write(describeMinMax(question, sensitivity));
        writer.write(System.lineSeparator());
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
        sBuilder.append(String.format(templates.getNext(Templates.Type.ALL), firstEntry.getValue() * 100, question.description, firstEntry.getKey()));

        for (int i = 1; i < answers.size(); ++i) {
            AbstractMap.SimpleEntry<String, Double> entry = answers.get(i);
            sBuilder.append(String.format(templates.getNext(Templates.Type.MIDDLE), entry.getValue() * 100, entry.getKey()));
        }

        sBuilder.append(". ").append(System.lineSeparator());
        return sBuilder.toString();
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
            AbstractMap.SimpleEntry<String, Double> highest = answers.get(answers.size() - 1);
            String s = templates.getNext(Templates.Type.MAX);
            sBuilder.append(String.format(s, highest.getValue() * 100, question.description, highest.getKey()));

            Consumer<AbstractMap.SimpleEntry<String, Double>> generateMiddle = entry -> {
                sBuilder.append(String.format(templates.getNext(Templates.Type.MIDDLE), entry.getValue() * 100, entry.getKey()));
                sBuilder.append(" ");
            };

            for (int i = answers.size() - 2; i < answers.size() - offset; ++i) {
                generateMiddle.accept(answers.get(i));
            }
            sBuilder.append(". ").append(System.lineSeparator());

            AbstractMap.SimpleEntry<String, Double> lowest = answers.get(offset);
            s = templates.getNext(Templates.Type.MIN);
            sBuilder.append(String.format(s, lowest.getValue() * 100, question.description, lowest.getKey()));

            for (int i = offset - 1; i >= 0; --i) {
                generateMiddle.accept(answers.get(i));
            }
            sBuilder.append(". ").append(System.lineSeparator());
        }
        return sBuilder.toString();
    }

}
