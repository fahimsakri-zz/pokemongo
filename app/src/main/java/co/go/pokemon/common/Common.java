package co.go.pokemon.common;

import android.content.Context;
import android.graphics.Point;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import co.go.pokemon.model.Offers;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by fahim on 7/16/16.
 */

public class Common {

    public static final String APPSFLYER_DEV_KEY = "zD9EQSqJtNMADxmqC3AiNQ";


    public interface OfferService {
        @GET("api/v2/web/inventory/get-sale/")
        Call<Offers> listOffers();
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int DIME_TYPE_WIDTH = 1;
    public static int DIME_TYPE_HEIGHT = 2;
    public static int deviceWidth = 0;
    private static int deviceHeight = 0;

    public static int getDeviceSize(int type, Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        deviceWidth = size.x;
        deviceHeight = size.y;
        if (type == DIME_TYPE_WIDTH) {
            return deviceWidth;
        } else {
            return deviceHeight;
        }
    }

    public static int getDeviceWidth(Context context) {
        if (deviceWidth > 0)
            return deviceWidth;
        return getDeviceSize(DIME_TYPE_WIDTH, context);
    }

    public static int getDeviceHeight(Context context) {
        if (deviceHeight > 0)
            return deviceHeight;
        return getDeviceSize(DIME_TYPE_HEIGHT, context);
    }

    public static String getDeviceAndriod_Id(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

}
