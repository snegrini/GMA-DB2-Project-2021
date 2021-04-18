package it.polimi.db2.gma.GMAEJB.services;

import it.polimi.db2.gma.GMAEJB.entities.ProductEntity;
import it.polimi.db2.gma.GMAEJB.entities.QuestionEntity;
import it.polimi.db2.gma.GMAEJB.entities.QuestionnaireEntity;
import it.polimi.db2.gma.GMAEJB.exceptions.BadProductException;
import it.polimi.db2.gma.GMAEJB.exceptions.BadQuestionnaireException;
import it.polimi.db2.gma.GMAEJB.utils.QuestionnaireInfo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Stateless
public class QuestionnaireService {
    @PersistenceContext(unitName = "GMAEJB")
    private EntityManager em;

    public QuestionnaireEntity findQuestionnaireByDate(LocalDate localDate) {
        return em.createNamedQuery("QuestionnaireEntity.findByDate", QuestionnaireEntity.class)
                .setParameter("date", Date.valueOf(localDate))
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public void addNewQuestionnaire(LocalDate localDate, int productId, List<String> strQuestions) throws BadProductException, BadQuestionnaireException {
        ProductEntity product = em.find(ProductEntity.class, productId);

        if (product == null) {
            throw new BadProductException("Product not found.");
        }

        // Check that there is no other questionnaire in the selected date.
        List<QuestionnaireEntity> entities = em.createNamedQuery("QuestionnaireEntity.findByDate", QuestionnaireEntity.class)
                .setParameter("date", Date.valueOf(localDate))
                .getResultList();

        if (!entities.isEmpty()) {
            throw new BadQuestionnaireException("Questionnaire already registered for the selected date.");
        }

        QuestionnaireEntity questionnaire = new QuestionnaireEntity(Date.valueOf(localDate), product);

        Collections.reverse(strQuestions);
        // Build new QuestionEntity objects and add them to the questionnaire.
        for (String strQuestion : strQuestions) {
            QuestionEntity question = new QuestionEntity(strQuestion);
            questionnaire.addQuestion(question);
        }

        product.addQuestionnaire(questionnaire);
        em.persist(product);
    }

    public void deleteQuestionnaire(int questionnaireId) throws BadQuestionnaireException {
        QuestionnaireEntity questionnaire = em.find(QuestionnaireEntity.class, questionnaireId);

        if (questionnaire == null) {
            throw new BadQuestionnaireException("Questionnaire not found.");
        }

        ProductEntity product = questionnaire.getProduct();

        if (questionnaire.getDate().toLocalDate().compareTo(LocalDate.now()) >= 0) {
            throw new BadQuestionnaireException("Cannot delete the selected questionnaire.");
        }

        // Points are removed by using a trigger.

        product.removeQuestionnaire(questionnaire);
        em.remove(questionnaire);
    }

    public List<QuestionEntity> findAllQuestionsByQuestionnaire(int questionnaireId) {
        return em.createNamedQuery("QuestionEntity.findAllByQuestionnaire", QuestionEntity.class)
                .setParameter("questionnaireId", questionnaireId)
                .getResultList();
    }

    public List<QuestionnaireInfo> getQuestionnairesInfos() {
        return em.createNamedQuery("QuestionnaireEntity.getQuestionnairesInfos", QuestionnaireInfo.class)
                .getResultList();
    }

    public List<QuestionnaireEntity> findQuestionnairesUntil(LocalDate localDate) {
        return em.createNamedQuery("QuestionnaireEntity.findAllUntilDate", QuestionnaireEntity.class)
                .setParameter("date", Date.valueOf(localDate))
                .setHint("javax.persistence.cache.storeMode", "REFRESH") // Pull fresh data to bypass the cache and see the correct list.
                .getResultList();
    }
}
