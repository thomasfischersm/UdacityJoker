package com.playposse.udacityjoker.jokelibrary;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

/**
 * A repository of jokes. The jokes are read from a json file. The json file is generated by
 * the JokeGenerator class in the Android app.
 */
public class JokeRepository {

    private static final String JOKE_FILE_NAME = "jokes.json";

    private static List<String> jokes = null;
    private static Random random = new Random();

    public static String getJoke() {
        if (jokes == null) {
            jokes = loadJokesFromFile();
        }

        return jokes.get(random.nextInt(jokes.size()));
    }

    private static List<String> loadJokesFromFile() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(JOKE_FILE_NAME);
        InputStreamReader reader = new InputStreamReader(inputStream);

        Gson gson = new Gson();
        JokeRoot jokeRoot = gson.fromJson(reader, JokeRoot.class);
        return jokeRoot.getJokes();
    }

    /**
     * A GSON root object that contains a list of jokes.
     */
    public static class JokeRoot {
        private List<String> jokes;

        public List<String> getJokes() {
            return jokes;
        }
    }
}
