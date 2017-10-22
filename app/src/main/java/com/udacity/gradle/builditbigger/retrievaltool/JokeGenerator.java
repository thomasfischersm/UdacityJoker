package com.udacity.gradle.builditbigger.retrievaltool;

import android.util.Log;

import com.udacity.gradle.builditbigger.retrievaltool.retrofit.Joke;
import com.udacity.gradle.builditbigger.retrievaltool.retrofit.PublicJokeService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A helper class that downloads jokes from an online joke API. (icanhazdajoke.com).
 */
public class JokeGenerator {

    private static final String LOG_TAG = JokeGenerator.class.getSimpleName();

    private static final String JOKE_API_BASE_URL = "https://icanhazdadjoke.com";
    private static final int JOKE_COUNT = 1;

    public static String retrieveJokes() {
        try {
            List<String> jokes = retrieveJokes(JOKE_COUNT);
            return convertToJson(jokes);
        } catch (IOException ex) {
            Log.e(LOG_TAG, "retrieveJokes: Failed to retrieve joke.", ex);
            return null;
        } catch (JSONException ex) {
            Log.e(LOG_TAG, "retrieveJokes: Failed to generate JSON.", ex);
            return null;
        }
    }

    private static Call<Joke> prepareCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JOKE_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PublicJokeService remoteService = retrofit.create(PublicJokeService.class);
        return remoteService.get();
    }

    private static List<String> retrieveJokes(int jokeCount)
            throws IOException {

        List<String> jokes = new ArrayList<>(JOKE_COUNT);
        for (int i = 0; i < JOKE_COUNT; i++) {
            Call<Joke> jokeCall = prepareCall();
            jokes.add(retrieveJoke(jokeCall));
        }
        return jokes;
    }

    private static String retrieveJoke(Call<Joke> jokeCall) throws IOException {
        Response<Joke> jokeResponse = jokeCall.execute();

        Joke body = jokeResponse.body();
        if (body == null) {
            return null;
        }

        Log.i(LOG_TAG, "retrieveJokes: Got joke: " + body.getJoke());
        return body.getJoke();
    }

    private static String convertToJson(List<String> jokes) throws JSONException {
        JSONArray array = new JSONArray(jokes);
        JSONObject rootObject = new JSONObject();
        rootObject.put("jokes", array);
        String json = rootObject.toString();

        Log.i(LOG_TAG, "convertToJson: Converted jokes to JSON: \n" + json);
        return json;
    }
}
