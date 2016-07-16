package co.go.pokemon.model;

/**
 * Created by fahim on 7/16/16.
 */

public class Offers {


    private Deals[] deals;

    public Deals[] getDeals() {
        return deals;
    }

    public void setDeals(Deals[] deals) {
        this.deals = deals;
    }

    @Override
    public String toString() {
        return "ClassPojo [ deals = " + deals + "]";
    }
}
