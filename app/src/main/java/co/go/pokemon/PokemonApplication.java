package co.go.pokemon;

import android.app.Application;

/**
 * Created by fahim on 7/15/16.
 */

public class PokemonApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Fresco.initialize(this);
    }
}
