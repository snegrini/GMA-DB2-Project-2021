package it.polimi.db2.gma.GMAEJB.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "questionnaire")
@NamedQueries({
        @NamedQuery(name = "QuestionnaireEntity.findAll", query = "SELECT q FROM QuestionnaireEntity q"),
        @NamedQuery(name = "QuestionnaireEntity.findQuestionnaireByDate", query = "SELECT q FROM QuestionnaireEntity q WHERE q.date = :date"),
        @NamedQuery(name = "QuestionnaireEntity.getQuestionnairesInfos", query = "SELECT NEW it.polimi.db2.gma.GMAEJB.utils.QuestionnaireInfo(q.id, q.date, p.name) FROM QuestionnaireEntity q INNER JOIN q.product p ORDER BY q.date DESC"),
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

    @OneToMany(mappedBy = "questionnaire")
    private List<QuestionEntity> questions;

    @OneToMany(mappedBy = "questionnaire")
    private List<EntryEntity> entries;

    public QuestionnaireEntity(Date date, ProductEntity product, List<QuestionEntity> questions) {
        this.date = date;
        this.product = product;
        this.questions = questions;
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

    public List<EntryEntity> getEntries() {
        return entries;
    }
}
