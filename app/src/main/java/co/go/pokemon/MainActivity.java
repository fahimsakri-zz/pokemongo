package co.go.pokemon;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

import co.go.pokemon.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFireBaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, new MainFragment());
        fragmentTransaction.commit();
    }

    public FirebaseAnalytics getAnalytics() {
        if (mFireBaseAnalytics == null)
            mFireBaseAnalytics = FirebaseAnalytics.getInstance(this);
        return mFireBaseAnalytics;
    }
}
