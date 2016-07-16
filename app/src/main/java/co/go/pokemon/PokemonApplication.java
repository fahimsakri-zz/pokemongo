package co.go.pokemon;

import android.app.Application;

import com.appsflyer.AppsFlyerLib;
import com.crashlytics.android.Crashlytics;

import co.go.pokemon.common.Common;
import io.fabric.sdk.android.Fabric;

/**
 * Created by fahim on 7/15/16.
 */

public class PokemonApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        AppsFlyerLib.getInstance().setAndroidIdData(Common.getDeviceAndriod_Id(this));
        AppsFlyerLib.getInstance().startTracking(this, Common.APPSFLYER_DEV_KEY);

    }
}
