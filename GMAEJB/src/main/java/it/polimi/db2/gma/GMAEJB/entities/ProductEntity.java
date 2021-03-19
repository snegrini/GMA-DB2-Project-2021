package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@NamedQueries({
        @NamedQuery(name = "ProductEntity.findAll", query = "SELECT p FROM ProductEntity p"),
        @NamedQuery(name = "ProductEntity.findByDate", query = "SELECT p FROM ProductEntity p INNER JOIN p.questionnaires q WHERE q.date = :date"),
})
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;

    @Column(name = "Name", nullable = false, length = 45)
    private String name;

    @Column(name = "Image", nullable = false, length = 45)
    private String image;

    @OneToMany(mappedBy = "product")
    private List<ReviewEntity> reviews;

    @OneToMany(mappedBy = "product")
    private List<QuestionnaireEntity> questionnaires;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<ReviewEntity> getReviews() {
        return reviews;
    }
}
