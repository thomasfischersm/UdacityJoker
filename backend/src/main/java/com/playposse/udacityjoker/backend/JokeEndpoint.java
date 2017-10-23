/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.playposse.udacityjoker.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.playposse.udacityjoker.jokelibrary.JokeRepository;

import java.util.logging.Logger;

/**
 * An endpoint that serves jokes.
 */
@Api(
        name = "jokeEndpoint",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.udacityjoker.playposse.com",
                ownerName = "backend.udacityjoker.playposse.com",
                packagePath = ""
        )
)
public class JokeEndpoint {

    private static final Logger log = Logger.getLogger(JokeEndpoint.class.getName());

    /**
     * Returns a random joke.
     */
    @ApiMethod(name = "getRandomJoke")
    public JokeBean getRandomJoke() {
        log.info("Serving a joke! What a joke!");
        String joke = JokeRepository.getJoke();
        return new JokeBean(joke);
    }
}
