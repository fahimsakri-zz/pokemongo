package co.go.pokemon.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.go.pokemon.R;
import co.go.pokemon.model.Pokemon;

/**
 * Created by fahim on 7/15/16.
 */

public class PokemonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Pokemon> pokemonList;
    Context context;

    public PokemonListAdapter(Context context, List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
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

    class ItemHolder extends RecyclerView.ViewHolder {

        public AppCompatImageView icon;
        public TextView name;
        public TextView rank;

        public ItemHolder(View itemView, Context context) {
            super(itemView);
            icon = (AppCompatImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.name);
            rank = (TextView) itemView.findViewById(R.id.rank);

        }
    }
}
