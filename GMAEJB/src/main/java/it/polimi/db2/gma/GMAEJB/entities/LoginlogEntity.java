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

    @Column(name = "LogTime", nullable = true)
    private Timestamp logTime;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private UserEntity user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getLogTime() {
        return logTime;
    }

    public void setLogTime(Timestamp logTime) {
        this.logTime = logTime;
    }
}
