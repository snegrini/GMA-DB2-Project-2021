package it.polimi.db2.gma.GMAEJB;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "loginlog")
public class LoginlogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;

    @Column(name = "UserId", nullable = true)
    private Integer userId;

    @Column(name = "LogTime", nullable = true)
    private Timestamp logTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getLogTime() {
        return logTime;
    }

    public void setLogTime(Timestamp logTime) {
        this.logTime = logTime;
    }
}
