package com.example.sweater.domain.Servey;

import com.example.sweater.domain.User.User;

import java.io.IOException;
import java.util.List;

/**
 * Объект самой анкеты
 */
public class Servey {
    User userUploader;
    /**
     * Объект-таблицы, пришедший на анализ.
     */
    private TableExcel tableToAnalize;
    /**
     * В данном лсите хранятся номера важных для пользователя вопросов.
     * По данным вопросам будет запущен полный анализ
     */
    private List<Integer> importantQuestions;

    private List<GroupOfQuestions> groupOfQuestions;

    private List<Term> terms;

    public Servey(String pathToExcelFile, User user) throws IOException {
        tableToAnalize = new TableExcel(pathToExcelFile, user);
    }

    public void GenerateResult() {
    }

}
