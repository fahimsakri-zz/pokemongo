package co.go.pokemon.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.go.pokemon.R;
import co.go.pokemon.adapter.OfferListAdapter;
import co.go.pokemon.common.Common;
import co.go.pokemon.itemdecoration.BrandCollectionDecoration;
import co.go.pokemon.model.Offers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fahim on 7/15/16.
 */

public class NotificationFragment extends Fragment {


    private RecyclerView mRecyclerview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerview = (RecyclerView) view.findViewById(R.id.mRecyclerView);
        mRecyclerview.addItemDecoration(new BrandCollectionDecoration((int) getContext().getResources().getDimension(R.dimen.margin)));
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://orbis.gofynd.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Common.OfferService service = retrofit.create(Common.OfferService.class);

        Call<Offers> repos = service.listOffers();
        repos.enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(Call<Offers> call, Response<Offers> response) {
                Offers offers = response.body();
                offers.getDeals().add(0, null);
                OfferListAdapter offerListAdapter = new OfferListAdapter(getContext(), offers);
                mRecyclerview.setAdapter(offerListAdapter);
              /*  if(response.body()!=null){

                    Log.d("response",response.body().toString());
                }else{
                    Log.d("response",response.body().toString());
                }
                Log.d("response",response.body().toString());*/
            }

            @Override
            public void onFailure(Call<Offers> call, Throwable t) {

            }
        });


    }
}
