package com.example.sweater.domain.Servey;

import java.util.List;

public class Servey {
    /**
     * Объект-таблицы, пришедший на анализ.
     */
    TableExcel tableToAnalize;
    /**
     * В данном лсите хранятся номера важных для пользователя вопросов.
     * По данным вопросам будет запущен полный анализ
     */
    List<Integer> importantQuestions;

    List<GroupOfQuestions> groupOfQuestions;

    public void GenerateResult(){}

}
