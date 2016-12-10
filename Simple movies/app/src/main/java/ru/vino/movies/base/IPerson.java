package ru.vino.movies.base;

public interface IPerson {

    static final int TYPE_CAST = 0;
    static final int TYPE_CREW = 1;

    long getId();

    String getName();

    String getRole();

    String getPhoto();

    int getType();
}
