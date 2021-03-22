package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "UserEntity.checkCredentials", query = "SELECT u FROM UserEntity u WHERE u.username = :username AND u.password = :password"),
        @NamedQuery(name = "UserEntity.findByUsername", query = "SELECT u FROM UserEntity u WHERE u.username = :username"),
        @NamedQuery(name = "UserEntity.findByEmail", query = "SELECT u FROM UserEntity u WHERE u.email = :email"),
        @NamedQuery(name = "UserEntity.getLeaderboardByDate", query = "SELECT NEW it.polimi.db2.gma.GMAEJB.utils.LeaderboardRow(u.username, e.points) " +
                "FROM UserEntity u INNER JOIN u.entries e INNER JOIN e.questionnaire q WHERE q.date = :date AND e.isSubmitted = 1 ORDER BY e.points DESC"),
        @NamedQuery(name = "UserEntity.getEntriesUserInfo", query = "SELECT NEW it.polimi.db2.gma.GMAEJB.utils.UserInfo(u.id, u.username)" +
                "FROM UserEntity u INNER JOIN u.entries e INNER JOIN e.questionnaire q WHERE q.id = :id AND e.isSubmitted = :submitted")
})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;

    @Column(name = "Username", nullable = false, length = 45)
    private String username;

    @Column(name = "Password", nullable = false, length = 45)
    private String password;

    @Column(name = "Email", nullable = false, length = 90)
    private String email;

    @Column(name = "Points", nullable = true)
    private Integer points;

    @Column(name = "IsBlocked", nullable = true)
    private Byte isBlocked;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<ReviewEntity> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<LoginlogEntity> loginlogs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, orphanRemoval = true)
    private List<EntryEntity> entries = new ArrayList<>();

    public UserEntity(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Byte getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Byte isBlocked) {
        this.isBlocked = isBlocked;
    }

    public List<EntryEntity> getEntries() {
        return entries;
    }

    public void addEntry(EntryEntity entry) {
        getEntries().add(entry);
        entry.setUser(this);
    }
}
