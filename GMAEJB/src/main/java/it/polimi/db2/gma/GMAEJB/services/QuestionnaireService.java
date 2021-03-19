package it.polimi.db2.gma.GMAEJB.services;

import it.polimi.db2.gma.GMAEJB.entities.QuestionnaireEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;

@Stateless
public class QuestionnaireService {
    @PersistenceContext(unitName = "GMAEJB")
    private EntityManager em;

    public QuestionnaireEntity findQuestionnaireByDate(Date date) {
        return em.createNamedQuery("QuestionnaireEntity.findQuestionnaireByDate", QuestionnaireEntity.class)
                .setParameter("date", date)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}
