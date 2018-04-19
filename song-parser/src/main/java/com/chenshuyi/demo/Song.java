package com.chenshuyi.demo;

/**
 * Service Interface 提供接口
 * @author chenyr
 * @date 2018.04.19
 */
public class Song {

    private String format;

    private String name;

    private String author;

    private long time;

    public Song() {
    }

    public Song(String author, String format, String name, long time) {
        this.author = author;
        this.format = format;
        this.name = name;
        this.time = time;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
