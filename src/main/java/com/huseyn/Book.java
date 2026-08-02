package com.huseyn;

import java.util.Random;

public class Book {
    private String id;
    private String title;
    private String author;
    private String genre;
    private int publishedYear;
    private int quantity;
    private int available;
    Book(){}
    public Book(String id, String title, String author, String genre, int publishedYear, int quantity) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setGenre(genre);
        setPublishedYear(publishedYear);
        setQuantity(quantity);
        setAvailable(quantity);
    }
    public Book(String id, String title, String author, String genre, int publishedYear, int quantity,int available) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setGenre(genre);
        setPublishedYear(publishedYear);
        setQuantity(quantity);
        setAvailable(available);
    }

    public Book(String title, String author, String genre, int publishedYear, int quantity) {
        setId();
        setTitle(title);
        setAuthor(author);
        setGenre(genre);
        setPublishedYear(publishedYear);
        setQuantity(quantity);
        setAvailable(quantity);
    }
    private void setId() {
        Random random = new Random();
        this.id = String.valueOf(100000 + random.nextInt(900000));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", publishedYear=" + publishedYear +
                ", quantity=" + quantity +
                ", available=" + available +
                '}';
    }
}
