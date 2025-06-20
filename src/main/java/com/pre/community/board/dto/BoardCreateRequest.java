package com.pre.community.board.dto;

public class BoardCreateRequest {
    private String title;
    private String author;
    private String password;
    private String content;

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
} 