package com.example.SA.domain.Servey;

import com.example.SA.domain.User.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Объект самой анкеты. Помимо объекта таблицы (TableExcel) в ней хранятся дополнительные поля : номера важных вопросов для
 * пользователя, термины, которые он ввел ( TODO пока не реализовано )
 * Объединенные в группы вопросы и т.д.
 */
public class Servey {

    User userUploader;

    String pathToResult;
    /**
     * Объект-таблицы, пришедший на анализ.
     */
    private TableExcel tableToAnalize;

    /**
     * В данном лсите хранятся номера важных для пользователя вопросов.
     * По данным вопросам будет запущен полный анализ
     */
    private List<Integer> importantQuestions;

    private List<Term> terms;

    public Servey(String pathToExcelUploadedFile, User user) throws IOException {
        tableToAnalize = new TableExcel(pathToExcelUploadedFile, user);
        userUploader = user;
        pathToResult = "result" + user.getUsername().toUpperCase() + ".txt";
    }


    public TableExcel getTableToAnalize() {
        return tableToAnalize;
    }

    public String getPathToResult() {
        return pathToResult;
    }

}
