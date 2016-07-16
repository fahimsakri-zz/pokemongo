package co.go.pokemon.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;

import co.go.pokemon.MainActivity;
import co.go.pokemon.R;
import co.go.pokemon.common.Common;
import co.go.pokemon.model.Deals;
import co.go.pokemon.model.Offers;
import co.go.pokemon.model.Pokemon;

/**
 * Created by fahim on 7/15/16.
 */

public class OfferListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Offers offers;
    Context context;

    public OfferListAdapter(Context context, Offers offers) {
        this.offers = offers;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offer_item_layout, parent, false);
        return new ItemHolder(view, context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            int extraSpaces = 8 + 8;
            int cardWidth = Common.getDeviceWidth(context) - Common.dpToPx(context, extraSpaces);
            Deals currentFeedItemData = offers.getDeals()[position];
            String[] ratioValues;
            ratioValues = currentFeedItemData.getBanner_image().getAspect_ratio().split(":");
            float imageRatio = Float.parseFloat(ratioValues[1]) / Float.parseFloat(ratioValues[0]);
            ((ItemHolder) holder).itemContainer.getLayoutParams().height = (int) (cardWidth * imageRatio);
            FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams((int) cardWidth, FrameLayout.LayoutParams.MATCH_PARENT);
            ((ItemHolder) holder).offer_image.setLayoutParams(frameLayoutParams);
            (((ItemHolder) holder).offer_image).setAdjustViewBounds(true);
            Glide.with(context)
                    .load(currentFeedItemData.getBanner_image().getUrl())
                    .centerCrop()
                    .crossFade()
                    .into(((ItemHolder) holder).offer_image);

            //((OfferHolder) holder).offerImage.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
            // ((ItemHolder) holder).name.setText(pokemonList.get(position).getTitle());
            //  ((ItemHolder) holder).rank.setText(pokemonList.get(position).getRank());
        }
    }

    @Override
    public int getItemCount() {
        return offers.getDeals().length;
    }


    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Context context;
        public AppCompatImageView offer_image;
        public FrameLayout itemContainer;

        public ItemHolder(View itemView, Context context) {
            super(itemView);
            offer_image = (AppCompatImageView) itemView.findViewById(R.id.offer_image);
            itemContainer = (FrameLayout) itemView.findViewById(R.id.item_container);
            itemContainer.setOnClickListener(this);
            this.context = context;

        }

        private void pokemonViewed(Context context, Pokemon pokemon) {
            Bundle bundle = new Bundle();
            bundle.putString("name", pokemon.getTitle());
            bundle.putString("source", "Feed");
            ((MainActivity) context).getAnalytics().logEvent("Viewed Pokemon", bundle);
        }

        @Override
        public void onClick(View view) {
           /* if (view.getId() == R.id.item_container) {
                Pokemon pokemon = pokemonList.get(getAdapterPosition());
                pokemonViewed(context, pokemon);
                PokemonFragment pokemonFragment = PokemonFragment.newInstance(pokemonList.get(getAdapterPosition()));
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                String backStateName = pokemonFragment.getClass().getName();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                fragmentTransaction.add(android.R.id.content, pokemonFragment, backStateName);
                fragmentTransaction.addToBackStack(backStateName);
                fragmentTransaction.commit();
            }*/
        }
    }
}
