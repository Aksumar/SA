package com.example.sweater.domain.Servey;

import java.util.List;

/**
 * Объект предсталяет собой набор вопросов, который пользователь решил объединить в одну тематику.
 */
public class GroupOfQuestions {
    String nameOdGroup;
    List<Integer> numberOfQuestions;

    public GroupOfQuestions(String nameOdGroup, List<Integer> numberOfQuesions) {
        this.nameOdGroup = nameOdGroup;
        this.numberOfQuestions = numberOfQuesions;
    }

    public String getNameOdGroup() {
        return nameOdGroup;
    }

    public void setNameOdGroup(String nameOdGroup) {
        this.nameOdGroup = nameOdGroup;
    }

    public List<Integer> getNumberOfQuesions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuesions(List<Integer> numberOfQuesions) {
        this.numberOfQuestions = numberOfQuesions;
    }
}
