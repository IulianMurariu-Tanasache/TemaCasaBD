package ProiectBD.DataClasses.User;


public class MeciExtra {

    private String echipa1_name;
    private int scor1;
    private int scor2;
    private String echipa2_name;
    private String data;
    private String acasa_name;
    private String id1;
    private String id2;
    private String id_meci;

    public MeciExtra(String echipa1_name, int scor1, int scor2, String echipa2_name, String data, String acasa_name, String id1, String id2, String id_meci) {
        this.echipa1_name = echipa1_name;
        this.scor1 = scor1;
        this.scor2 = scor2;
        this.echipa2_name = echipa2_name;
        this.data = data;
        this.acasa_name = acasa_name;
        this.id1 = id1;
        this.id2 = id2;
        this.id_meci = id_meci;
    }

    public String getEchipa1_name() {
        return echipa1_name;
    }

    public String getEchipa2_name() {
        return echipa2_name;
    }

    public String getData() {
        return data;
    }

    public String getDisplay() {
        return echipa1_name + "   " + scor1 + "\n" + echipa2_name + "   " + scor2;
    }

    public String getAcasa_name() {
        return acasa_name;
    }

    public String getId1() {
        return id1;
    }

    public String getId2() {
        return id2;
    }

    public String getId_meci() {
        return id_meci;
    }
}
