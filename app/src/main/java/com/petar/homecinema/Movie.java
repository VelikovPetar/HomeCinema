package com.petar.homecinema;

/**
 * Created by petar on 6/4/16.
 */
public class Movie {
    public String title;
    public String actors;
    public String director;
    public String genre;
    public int box;

    public Movie(String title, String actors, String director, String genre, int box) {
        this.box = box;
        this.genre = genre;
        this.director = director;
        this.actors = actors;
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%s\t%s\t%d\n", title, actors, director, genre, box);
    }
}
