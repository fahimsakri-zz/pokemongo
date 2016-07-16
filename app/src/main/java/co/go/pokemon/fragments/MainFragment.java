package co.go.pokemon.fragments;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.go.pokemon.R;
import co.go.pokemon.adapter.PokemonListAdapter;
import co.go.pokemon.model.Pokemon;

/**
 * Created by fahim on 7/15/16.
 */

public class MainFragment extends Fragment implements SearchView.OnQueryTextListener {
    private RecyclerView mRecyclerView;
    private SearchView mSearcView;
    private List<Pokemon> pokemons;
    private PokemonListAdapter mAdaper;
    private RelativeLayout offerBanner;

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
        offerBanner = (RelativeLayout) view.findViewById(R.id.offerBanner);
        offerBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                NotificationFragment pokemonFragment = new NotificationFragment();
                String backStateName = pokemonFragment.getClass().getName();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                fragmentTransaction.add(android.R.id.content, pokemonFragment, backStateName);
                fragmentTransaction.addToBackStack(backStateName);
                fragmentTransaction.commit();
            }
        });
        mSearcView = (SearchView) view.findViewById(R.id.txtSearch);
        mSearcView.setOnQueryTextListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.pokemonlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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
            pokemons = gson.fromJson(content, POK_TYPE);
            // Adding Dummy Item for Refer n Earn Card
          //  pokemons.add(0,null);
            mAdaper = new PokemonListAdapter(getContext(), pokemons);
            mRecyclerView.setAdapter(mAdaper);
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

    private List<Pokemon> filter(List<Pokemon> models, String query) {
        query = query.toLowerCase();

        final List<Pokemon> filteredModelList = new ArrayList<>();
        for (Pokemon model : models) {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Pokemon> filteredModelList = filter(pokemons, newText);
        mAdaper.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }
}
