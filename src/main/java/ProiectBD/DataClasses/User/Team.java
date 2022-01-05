package ProiectBD.DataClasses.User;

public class Team {

    private int no;
    private String name;
    private String country;
    private int points;
    private int wins;
    private int draws;
    private int defeats;
    private int nr_games;
    private double avg;

    public Team(int no, String name, String country, int points, int wins, int draws, int defeats, int nr_games, int goalavg) {
        this.no = no;
        this.name = name;
        this.country = country;
        this.points = points;
        this.wins = wins;
        this.draws = draws;
        this.defeats = defeats;
        this.avg = goalavg;
        this.nr_games = nr_games;
    }

    public int getNr_games() {
        return nr_games;
    }

    public int getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getPoints() {
        return points;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getDefeats() {
        return defeats;
    }

    public double getAvg() {
        return avg;
    }
}
