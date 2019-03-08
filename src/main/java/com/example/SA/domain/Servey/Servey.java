package com.example.SA.domain.Servey;

import com.example.SA.domain.User.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Объект самой анкеты. Помимо объекта таблицы (TableExcel) в ней хранятся дополнительные поля : номера важных вопросов для
 * пользователя, термины, которые он ввел ( TODO пока не реализовано )
 * Объединенные в группы вопросы и т.д.
 */
public class Servey {

    User userUploader;

    String pathToResult;

    //Основная тема опроса
    String header;

    ArrayList<String> respondersName = new ArrayList<>();

    /**
     * Объект-таблицы, пришедший на анализ.
     */
    private TableExcel tableToAnalize;

    /**
     * В данном лсите хранятся номера важных для пользователя вопросов.
     * По данным вопросам будет запущен полный анализ
     */
    private List<Integer> importantQuestions;


    public Servey(String pathToExcelUploadedFile, String header, String respType, User user) throws IOException {
        this.header = header;
        respondersName.add(respType);
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

    public ArrayList<String> getRespondersName() {
        return respondersName;
    }

    public String getHeader() {
        return header;
    }

}
