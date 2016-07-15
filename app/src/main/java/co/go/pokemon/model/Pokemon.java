package co.go.pokemon.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fahim on 7/15/16.
 */

public class Pokemon implements Serializable {
    private String img_src;

    private String total;

    private String defense;

    private String hp;

    private String rank;

    private String title;

    private String speed;

    private String sp_atk;

    private String attack;

    private List<String> type;

    private String sp_def;

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getSp_atk() {
        return sp_atk;
    }

    public void setSp_atk(String sp_atk) {
        this.sp_atk = sp_atk;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getSp_def() {
        return sp_def;
    }

    public void setSp_def(String sp_def) {
        this.sp_def = sp_def;
    }

    @Override
    public String toString() {
        return "ClassPojo [img_src = " + img_src + ", total = " + total + ", defense = " + defense + ", hp = " + hp + ", rank = " + rank + ", title = " + title + ", speed = " + speed + ", sp_atk = " + sp_atk + ", attack = " + attack + ", type = " + type + ", sp_def = " + sp_def + "]";
    }
}
