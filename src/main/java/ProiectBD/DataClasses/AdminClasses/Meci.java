package ProiectBD.DataClasses.AdminClasses;

import java.math.BigDecimal;
import java.util.Random;

//Clasa care reprezinta o inregistrare in tabela Meciuri
public class Meci {

    protected String id_meci;
    protected String echipa1_id;
    protected String echipa2_id;
    protected String data_meci;
    protected Integer scor_e1;
    protected Integer scor_e2;
    protected String acasa;

    public Meci(String id_meci, String echipa1_id, String echipa2_id, String data_meci, Integer scor_e1, Integer scor_e2, String acasa) {
        this.echipa1_id = echipa1_id;
        this.echipa2_id = echipa2_id;
        this.data_meci = data_meci;
        this.scor_e1 = scor_e1;
        this.scor_e2 = scor_e2;
        this.acasa = acasa;
        this.id_meci = id_meci;
    }

    public Meci(String echipa1_id, String echipa2_id, String data_meci, Integer scor_e1, Integer scor_e2, String acasa) {
        this.echipa1_id = echipa1_id;
        this.echipa2_id = echipa2_id;
        this.data_meci = data_meci;
        this.scor_e1 = scor_e1;
        this.scor_e2 = scor_e2;
        this.acasa = acasa;
        this.id_meci = genID();
    }

    public Meci(String id_meci, String echipa1_id, String echipa2_id, String data_meci, BigDecimal scor_e1, BigDecimal scor_e2, String acasa) {
        this.echipa1_id = echipa1_id;
        this.echipa2_id = echipa2_id;
        this.data_meci = data_meci;
        this.scor_e1 = scor_e1 == null ? null : scor_e1.intValue();
        this.scor_e2 = scor_e2 == null ? null : scor_e2.intValue();
        this.acasa = acasa;
        this.id_meci = id_meci;
    }

    public String getEchipa1_id() {
        return echipa1_id;
    }

    public String getEchipa2_id() {
        return echipa2_id;
    }

    public String getData_meci() {
        return data_meci;
    }

    public Integer getScor_e1() {
        return scor_e1;
    }

    public Integer getScor_e2() {
        return scor_e2;
    }

    public String getAcasa() {
        return acasa;
    }

    public String getId_meci() {
        return id_meci;
    }

    private String genID(){
        return  "" + echipa1_id.substring(0,2).toLowerCase() + echipa2_id.substring(0,2).toLowerCase() + (new Random()).nextInt(100);
    }
}
