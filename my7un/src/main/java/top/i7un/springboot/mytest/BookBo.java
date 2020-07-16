package top.i7un.springboot.mytest;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookBo bookBo = (BookBo) o;
        return Objects.equals(name, bookBo.name) &&
                Objects.equals(author, bookBo.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author);
    }
}