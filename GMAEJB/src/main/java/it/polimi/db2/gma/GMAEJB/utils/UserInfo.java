package it.polimi.db2.gma.GMAEJB.utils;

public class UserInfo {
    int id;
    String username;

    public UserInfo(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
