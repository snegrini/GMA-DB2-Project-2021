package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.*;

@Entity
@Table(name = "offensiveword")
public class OffensivewordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;

    @Column(name = "Word", nullable = false, length = 45)
    private String word;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
