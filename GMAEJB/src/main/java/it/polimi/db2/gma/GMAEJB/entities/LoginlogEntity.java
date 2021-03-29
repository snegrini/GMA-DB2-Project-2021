package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "loginlog")
public class LoginlogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;

    @Column(name = "LogTime")
    private Timestamp logTime;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private UserEntity user;

    public LoginlogEntity() {
    }

    public LoginlogEntity(Timestamp logTime) {
        this.logTime = logTime;
    }

    public int getId() {
        return id;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
