package it.polimi.db2.gma.GMAEJB.services;

import it.polimi.db2.gma.GMAEJB.entities.ProductEntity;
import it.polimi.db2.gma.GMAEJB.entities.QuestionEntity;
import it.polimi.db2.gma.GMAEJB.entities.QuestionnaireEntity;
import it.polimi.db2.gma.GMAEJB.exceptions.BadProductException;
import it.polimi.db2.gma.GMAEJB.utils.QuestionnaireInfo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class QuestionnaireService {
    @PersistenceContext(unitName = "GMAEJB")
    private EntityManager em;

    public QuestionnaireEntity findQuestionnaireById(int id) {
        return em.find(QuestionnaireEntity.class, id);
    }

    public List<QuestionnaireEntity> findAllQuestionnaires() {
        return em.createNamedQuery("QuestionnaireEntity.findAll", QuestionnaireEntity.class)
                .getResultList();
    }

    public QuestionnaireEntity findQuestionnaireByDate(Date date) {
        return em.createNamedQuery("QuestionnaireEntity.findQuestionnaireByDate", QuestionnaireEntity.class)
                .setParameter("date", date)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public QuestionnaireEntity addNewQuestionnaire(LocalDate date, int productId, List<String> strQuestions) throws BadProductException {
        ProductEntity product = em.find(ProductEntity.class, productId);

        if (product == null) {
            throw new BadProductException("Product not found.");
        }

        // TODO: remove this line, build and manage questions.
        List<QuestionEntity> questions = null;
        // <--

        QuestionnaireEntity questionnaire = new QuestionnaireEntity(Date.valueOf(date), product, questions);
        em.persist(questionnaire);
        return questionnaire;
    }

    public List<QuestionnaireInfo> getQuestionnairesInfos() {
        return em.createNamedQuery("QuestionnaireEntity.getQuestionnairesInfos", QuestionnaireInfo.class)
                .getResultList();

    }
}
