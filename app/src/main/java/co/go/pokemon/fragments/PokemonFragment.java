package co.go.pokemon.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import co.go.pokemon.R;
import co.go.pokemon.model.Pokemon;

/**
 * Created by fahim on 7/15/16.
 */

public class PokemonFragment extends Fragment {

    private Pokemon pokemon;

    public static PokemonFragment newInstance(Pokemon pokemon) {
        PokemonFragment pokemonFragment = new PokemonFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("pokemon", pokemon);
        pokemonFragment.setArguments(bundle);
        return pokemonFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pokemon = (Pokemon) getArguments().getSerializable("pokemon");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pokemon_description_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(getContext())
                .load(pokemon.getImg_src())
                .fitCenter()
                .override(300, 300)
                .crossFade()
                .into((AppCompatImageView) view.findViewById(R.id.image));


        if (pokemon.getType().get(0).contains(",")) {
            ((TextView) view.findViewById(R.id.type1)).setText(pokemon.getType().get(0).split(",")[0]);
            ((TextView) view.findViewById(R.id.type2)).setText(pokemon.getType().get(0).split(",")[1]);
        } else {
            ((TextView) view.findViewById(R.id.type2)).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.type1)).setText(pokemon.getType().get(0));
        }

        ((TextView) view.findViewById(R.id.txtHpValue)).setText(pokemon.getHp());
        ((TextView) view.findViewById(R.id.txtAttackValue)).setText(pokemon.getAttack());
        ((TextView) view.findViewById(R.id.txtSpAtkValue)).setText(pokemon.getSp_atk());
        ((TextView) view.findViewById(R.id.name)).setText(pokemon.getTitle());
        ((TextView) view.findViewById(R.id.rank)).setText("Rank " + pokemon.getRank());
        ((TextView) view.findViewById(R.id.txtSpDefValue)).setText(pokemon.getSp_def());
        ((TextView) view.findViewById(R.id.txtSpeedValue)).setText(pokemon.getSpeed());
        ((TextView) view.findViewById(R.id.txtDefenceValue)).setText(pokemon.getDefense());
        ((TextView) view.findViewById(R.id.txtTotalValue)).setText(pokemon.getTotal());

    }

}
