package ProiectBD.DataClasses.User;

public class Player {

    private String name;
    private String team;
    private String pos;
    private String nat;
    private int age;
    private int goals;
    private int assists;
    private int yellows;
    private int reds;

    public Player(String name, String team, String pos, String nat, int age, int goals, int assists, int yellows, int reds) {
        this.name = name;
        this.team = team;
        this.pos = pos;
        this.nat = nat;
        this.age = age;
        this.goals = goals;
        this.assists = assists;
        this.yellows = yellows;
        this.reds = reds;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public String getPos() {
        return pos;
    }

    public String getNat() {
        return nat;
    }

    public int getAge() {
        return age;
    }

    public int getGoals() {
        return goals;
    }

    public int getAssists() {
        return assists;
    }

    public int getYellows() {
        return yellows;
    }

    public int getReds() {
        return reds;
    }
}
