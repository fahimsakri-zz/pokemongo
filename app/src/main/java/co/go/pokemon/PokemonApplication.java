package co.go.pokemon;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by fahim on 7/15/16.
 */

public class PokemonApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        // Fresco.initialize(this);
    }
}
