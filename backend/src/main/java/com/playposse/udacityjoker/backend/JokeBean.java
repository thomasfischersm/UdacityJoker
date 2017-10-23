package com.playposse.udacityjoker.backend;

/**
 * A transport bean that encapsulates a joke.
 */
public class JokeBean {

    private String joke;

    public JokeBean(String joke) {
        this.joke = joke;
    }

    public String getJoke() {
        return joke;
    }
}