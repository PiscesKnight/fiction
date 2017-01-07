package com.example.tai_fiction.entity;

/**
 * Created by Tai on 2016/4/14.
 */
public class MyCollectEntity {
    private String authors;
    private String title;
    private String cover;
    private int bookcount;
    private String time;
    private String bookId;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getBookcount() {
        return bookcount;
    }

    public void setBookcount(int bookcount) {
        this.bookcount = bookcount;
    }

    public String getBookId(){
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
