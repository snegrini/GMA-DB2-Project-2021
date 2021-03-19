package it.polimi.db2.gma.GMAEJB.utils;

public class LeaderboardRow {
    final private String username;
    final private int points;

    public LeaderboardRow(String username, int points) {
        this.username = username;
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }
}
