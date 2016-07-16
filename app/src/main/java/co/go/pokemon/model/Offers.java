package co.go.pokemon.model;

import java.util.ArrayList;

/**
 * Created by fahim on 7/16/16.
 */

public class Offers {


    private ArrayList<Deals> deals;

    public ArrayList<Deals> getDeals() {
        return deals;
    }

    public void setDeals(ArrayList<Deals> deals) {
        this.deals = deals;
    }

    @Override
    public String toString() {
        return "ClassPojo [ deals = " + deals + "]";
    }
}
