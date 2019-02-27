package com.example.SA.domain.Servey;

import org.springframework.data.util.Pair;

import java.util.List;

/**
 * Специальные термины, который вводит пользователь для характерицации отвечающих
 */
public class Term {
    String termName;

    /**
     * На вопрос номер Integer необходимо ответить String
     */
    List<Pair<Integer, String>> spesificResponsesOnQuestions;
}
