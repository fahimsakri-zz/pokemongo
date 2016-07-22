package co.go.pokemon.fragments;

import android.content.Context;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import co.go.pokemon.MainActivity;
import co.go.pokemon.R;
import co.go.pokemon.adapter.PokemonListAdapter;
import co.go.pokemon.common.Common;
import co.go.pokemon.model.Pokemon;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static co.go.pokemon.common.Common.showDefaultError;

/**
 * Created by fahim on 7/15/16.
 */

public class MainFragment extends Fragment implements SearchView.OnQueryTextListener {
    private RecyclerView mRecyclerView;
    private SearchView mSearcView;
    private List<Pokemon> pokemons;
    private PokemonListAdapter mAdaper;
    private RelativeLayout offerBanner;
    private ProgressBar progressBar;

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

    private void offerListViewed(Context context) {
        Bundle bundle = new Bundle();
        ((MainActivity) context).getAnalytics().logEvent("viewed_offer", bundle);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        offerBanner = (RelativeLayout) view.findViewById(R.id.offerBanner);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        if (progressBar != null && progressBar.getVisibility() != View.VISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
        }
        offerBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offerListViewed(getActivity());
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
        mSearcView.setIconifiedByDefault(false);
        mSearcView.clearFocus();
        // mSearcView.setImeOptions();
       /* ((ViewGroup)mSearcView.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearcView.performClick();
            }
        });
       */
        mSearcView.setOnQueryTextListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.pokemonlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        /*AssetFileDescriptor descriptor = null;
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
        }*/

        OkHttpClient client = new OkHttpClient
                .Builder()
                .cache(new Cache(new File(getActivity().getApplication().getCacheDir(), "http"), 10 * 1024 * 1024))
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        request = request.newBuilder().header("Cache-Control", "public, max-age=86400").build();
                        return chain.proceed(request);
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://orbis.gofynd.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Cache cache;
        long SIZE_OF_CACHE = 10 * 1024 * 1024;
        cache = new Cache(new File("", "http"), SIZE_OF_CACHE);
        Common.PokemonService service = retrofit.create(Common.PokemonService.class);

        Call<List<Pokemon>> repos = service.listPokemons();
        repos.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                if (progressBar != null && progressBar.getVisibility() == View.VISIBLE) {
                    progressBar.setVisibility(View.GONE);
                    ((View) progressBar.getParent()).setVisibility(View.GONE);
                }
                mAdaper = new PokemonListAdapter(getContext(), response.body());
                mRecyclerView.setAdapter(mAdaper);
            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                showDefaultError(getActivity());
                Log.e("","");
            }
        });
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
