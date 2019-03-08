package com.example.SA.Service;

import com.example.SA.Algorithms.ExcelExtract.Question;
import com.example.SA.Algorithms.descriptionGenerator.BasicGenerator;
import com.example.SA.Algorithms.descriptionGenerator.Intercouse;
import com.example.SA.Algorithms.descriptionGenerator.Intervals;
import com.example.SA.domain.Servey.Survey;
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
public class DescriptionGenerator {
    private File result;
    private Survey survey;
    private static final Random rnd = new Random();
    private final Templates templates = new Templates(new File("C:\\Users\\alexr\\IdeaProjects\\Bricks"));

    public DescriptionGenerator(Survey surveyToAnalyze, String pathToResult) throws IOException {
        survey = surveyToAnalyze;

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

            TableExcel tableToAnalyze = survey.getTable();
            ArrayList<Question> questions = tableToAnalyze.getQuestions();
            writer.write(generateQuestionComparison(questions.get(7), questions.get(9)));
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    private String generateIntroduction() throws IOException {
        String result = "В ходе настоящего исследования был проведен опрос " + survey.getRespondersName().get(0) + " по теме  : \"" + survey.getHeader() + "\".\n";
        return result + String.format("Данное анкетировано состояло из %d вопросов. " +
                        "Участие в анкетировании приняло %d респондентов.\n",
                survey.getTable().getQuestions().size(),
                survey.getTable().getResponders().size());
    }

    /**
     * описание вопроса по Формуле Стёрджеса
     *
     * @param question вопрос на анализ
     * @return описание вопроса по Формуле Стёрджеса
     */
    private String Sturges(Question question) {
        Intervals ints = new Intervals(question, survey.getTable().getResponders().size());
        return ints.toString();
    }

    private String generateSturges() {
        StringBuilder result = new StringBuilder();

        survey.getTable()
                .getQuestions()
                .stream()
                .filter(q -> q.isQuantitative)
                .forEach(q -> result.append(Sturges(q)));

        return result.toString();
    }

    private void writeFullDescription(FileWriter writer) throws IOException {
        if (survey.getImportantQuestions() != null) {
            for (Question question : survey.getImportantQuestions()) {
                writer.write(describeAll(question));
            }
        }
    }

    private void writeMinMax(FileWriter writer, double sensitivity) throws IOException {
        for (Question question : survey.getTable().getQuestions()) {
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


            for (int i = answers.size() - 2; i < answers.size() - offset; ++i) {
                AbstractMap.SimpleEntry<String, Double> entry = answers.get(i);
                sBuilder.append(String.format(templates.getNext(Templates.Type.MIDDLE), entry.getValue() * 100, entry.getKey()));
                sBuilder.append(" ");
            }
            sBuilder.append(". ").append(System.lineSeparator());

            AbstractMap.SimpleEntry<String, Double> lowest = answers.get(offset);
            s = templates.getNext(Templates.Type.MIN);
            sBuilder.append(String.format(s, lowest.getValue() * 100, question.description, lowest.getKey()));

            for (int i = offset - 1; i >= 0; --i) {
                AbstractMap.SimpleEntry<String, Double> entry = answers.get(i);
                sBuilder.append(String.format(templates.getNext(Templates.Type.MIDDLE), entry.getValue() * 100, entry.getKey()));
                sBuilder.append(" ");
            }
            sBuilder.append(". ").append(System.lineSeparator());
        }
        return sBuilder.toString();
    }


    private String generateQuestionComparison(Question question1, Question question2) {
        return Intercouse.doIntercourse(question1, question2);
    }
}
