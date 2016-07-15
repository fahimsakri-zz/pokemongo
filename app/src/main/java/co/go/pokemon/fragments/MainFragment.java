package co.go.pokemon.fragments;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import co.go.pokemon.R;
import co.go.pokemon.adapter.PokemonListAdapter;
import co.go.pokemon.model.Pokemon;

/**
 * Created by fahim on 7/15/16.
 */

public class MainFragment extends Fragment {
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.pokemonlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        AssetFileDescriptor descriptor = null;
        try {
            descriptor = getContext().getAssets().openFd("pokemon.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileReader reader = new FileReader(descriptor.getFileDescriptor());
        Type POK_TYPE = new TypeToken<List<Pokemon>>() {
        }.getType();
        Gson gson = new Gson();

        try {
            String content = loadJSONFromAsset();
            List<Pokemon> pokemons = gson.fromJson(content, POK_TYPE);
            mRecyclerView.setAdapter(new PokemonListAdapter(getContext(),pokemons));
            Log.d("lis", pokemons.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getContext().getAssets().open("pokemon.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}
