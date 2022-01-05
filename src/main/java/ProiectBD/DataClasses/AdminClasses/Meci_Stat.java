package ProiectBD.DataClasses.AdminClasses;

import java.math.BigDecimal;
import java.util.Random;

public class Meci_Stat {

    private String id_stat;
    private String id_meci;
    private String id_jucator;
    private Integer goluri;
    private Integer asist;
    private Integer cart;
    private String moment;

    public Meci_Stat(String id_meci, String id_jucator, Integer goluri, Integer asist, Integer cart, String moment) {
        this.id_meci = id_meci;
        this.id_jucator = id_jucator;
        this.goluri = goluri;
        this.asist = asist;
        this.cart = cart;
        this.moment = moment;
        this.id_stat = genID();
    }

    public Meci_Stat(String id_meci, String id_jucator, BigDecimal goluri, BigDecimal asist, BigDecimal cart, String moment) {
        this.id_meci = id_meci;
        this.id_jucator = id_jucator;
        this.goluri = goluri == null ? null : goluri.intValue();
        this.asist = asist == null ? null : asist.intValue();
        this.cart = cart == null ? null : cart.intValue();
        this.moment = moment;
        this.id_stat = genID();
    }

    private String genID() {
        return id_meci.substring(0,2) + id_jucator.substring(0,2) + (new Random()).nextInt(100);
    }

    public String getId_stat() {
        return id_stat;
    }

    public Meci_Stat(String id_stat, String id_meci, String id_jucator, Integer goluri, Integer asist, Integer cart, String moment) {
        this.id_stat = id_stat;
        this.id_meci = id_meci;
        this.id_jucator = id_jucator;
        this.goluri = goluri;
        this.asist = asist;
        this.cart = cart;
        this.moment = moment;
    }

    public String getId_meci() {
        return id_meci;
    }

    public String getId_jucator() {
        return id_jucator;
    }

    public Integer getGoluri() {
        return goluri;
    }

    public Integer getAsist() {
        return asist;
    }

    public Integer getCart() {
        return cart;
    }

    public String getMoment() {
        return moment;
    }
}
