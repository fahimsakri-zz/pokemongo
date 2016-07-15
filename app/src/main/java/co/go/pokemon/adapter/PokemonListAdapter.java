package co.go.pokemon.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.go.pokemon.MainActivity;
import co.go.pokemon.R;
import co.go.pokemon.fragments.PokemonFragment;
import co.go.pokemon.model.Pokemon;

/**
 * Created by fahim on 7/15/16.
 */

public class PokemonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Pokemon> pokemonList;
    Context context;

    public PokemonListAdapter(Context context, List<Pokemon> pokemonList) {
        this.pokemonList = new ArrayList<>(pokemonList);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pokemons_item_layout, parent, false);
        return new ItemHolder(view, context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ((ItemHolder) holder).name.setText(pokemonList.get(position).getTitle());
            ((ItemHolder) holder).rank.setText(pokemonList.get(position).getRank());
        }
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public Pokemon removeItem(int position) {
        final Pokemon model = pokemonList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Pokemon model) {
        pokemonList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Pokemon model = pokemonList.remove(fromPosition);
        pokemonList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<Pokemon> filteredModelList) {
        applyAndAnimateRemovals(filteredModelList);
        applyAndAnimateAdditions(filteredModelList);
        applyAndAnimateMovedItems(filteredModelList);
    }

    private void applyAndAnimateMovedItems(List<Pokemon> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Pokemon model = newModels.get(toPosition);
            final int fromPosition = pokemonList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    private void applyAndAnimateRemovals(List<Pokemon> newModels) {
        for (int i = pokemonList.size() - 1; i >= 0; i--) {
            final Pokemon model = pokemonList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }


    private void applyAndAnimateAdditions(List<Pokemon> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Pokemon model = newModels.get(i);
            if (!pokemonList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Context context;
        public AppCompatImageView icon;
        public TextView name;
        public TextView rank;
        public RelativeLayout itemContainer;

        public ItemHolder(View itemView, Context context) {
            super(itemView);
            icon = (AppCompatImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.name);
            rank = (TextView) itemView.findViewById(R.id.rank);
            itemContainer = (RelativeLayout) itemView.findViewById(R.id.item_container);
            itemContainer.setOnClickListener(this);
            this.context = context;

        }

        private void pokemonViewed(Context context,Pokemon pokemon){
            Bundle bundle = new Bundle();
            bundle.putString("name",pokemon.getTitle());
            bundle.putString("source","Feed");
            ((MainActivity)context).getAnalytics().logEvent("Viewed Pokemon",bundle);
        }
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.item_container) {
                Pokemon pokemon =pokemonList.get(getAdapterPosition());
                pokemonViewed(context,pokemon);
                PokemonFragment pokemonFragment = PokemonFragment.newInstance(pokemonList.get(getAdapterPosition()));
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                String backStateName = pokemonFragment.getClass().getName();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                fragmentTransaction.add(android.R.id.content, pokemonFragment, backStateName);
                fragmentTransaction.addToBackStack(backStateName);
                fragmentTransaction.commit();
            }
        }
    }
}
