package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questionnaire")
@NamedQueries({
        @NamedQuery(name = "QuestionnaireEntity.findByDate", query = "SELECT q FROM QuestionnaireEntity q WHERE q.date = :date"),
})
public class QuestionnaireEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;

    @Column(name = "Date", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "ProductId")
    private ProductEntity product;

    @OneToMany(mappedBy = "questionnaire", cascade = { CascadeType.PERSIST })
    private List<QuestionEntity> questions = new ArrayList<>();

    @OneToMany(mappedBy = "questionnaire")
    private List<EntryEntity> entries;

    public QuestionnaireEntity(Date date, ProductEntity product) {
        this.date = date;
        this.product = product;
    }

    public QuestionnaireEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public List<EntryEntity> getEntries() {
        return entries;
    }

    public void addQuestion(QuestionEntity question) {
        getQuestions().add(question);
        question.setQuestionnaire(this);
    }

    public void removeQuestion(QuestionEntity question) {
        getQuestions().remove(question);
    }
}
