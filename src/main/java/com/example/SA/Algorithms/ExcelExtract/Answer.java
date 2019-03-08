package com.example.SA.Algorithms.ExcelExtract;

public class Answer {
    Responder responder;
    Question question;
    String content;

    public double popularity;   //0 to 1

    public Responder getResponder() {
        return responder;
    }

    public Question getQuestion() {
        return question;
    }

    public String getContent() {
        return content;
    }

    public Answer(String answer, Question ask) {
        content = answer;
        question = ask;
    }

    public Answer(String answer, Responder author) {
        content = answer;
        responder = author;
    }

    public Answer(String answer, Responder author, Question ask) {
        responder = author;
        content = answer;
        question = ask;
    }
}