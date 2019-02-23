package com.example.SA.domain;

import com.example.SA.domain.User.User;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class MessageTODELETE {

    public MessageTODELETE() {
    }

    public MessageTODELETE(String text, String tag, User userAuthor) {
        this.text = text;
        this.tag = tag;
        this.author = userAuthor;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String text;
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private String fileName;

    public User getAuthor() {
        return author;
    }

    public String getAuthorName() {
        if (author == null)
            return "<---->";
        return author.getUsername();
    }


    public void setAuthor(User author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
