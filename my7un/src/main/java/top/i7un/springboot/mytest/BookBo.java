package top.i7un.springboot.mytest;

import java.io.Serializable;

public class BookBo implements Serializable {

    private static final long serialVersionUID = 587389645684120455L;

    private String name;
    private String author;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BookBo() {
    }

    public BookBo(String name, String author) {
        this.name = name;
        this.author = author;
    }

    @Override
    public String toString() {
        return "BookBo{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}