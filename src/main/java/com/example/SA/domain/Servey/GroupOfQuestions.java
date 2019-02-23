package com.example.SA.domain.Servey;

import java.util.List;

/**
 * Объект предсталяет собой набор вопросов, который пользователь решил объединить в одну тематику.
 */
public class GroupOfQuestions {
    String nameOdGroup;
    List<Integer> numberOfQuesions;

    public GroupOfQuestions(String nameOdGroup, List<Integer> numberOfQuesions) {
        this.nameOdGroup = nameOdGroup;
        this.numberOfQuesions = numberOfQuesions;
    }

    public String getNameOdGroup() {
        return nameOdGroup;
    }

    public void setNameOdGroup(String nameOdGroup) {
        this.nameOdGroup = nameOdGroup;
    }

    public List<Integer> getNumberOfQuesions() {
        return numberOfQuesions;
    }

    public void setNumberOfQuesions(List<Integer> numberOfQuesions) {
        this.numberOfQuesions = numberOfQuesions;
    }
}
