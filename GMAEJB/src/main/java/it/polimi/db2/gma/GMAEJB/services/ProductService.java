package it.polimi.db2.gma.GMAEJB.services;

import it.polimi.db2.gma.GMAEJB.entities.ProductEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.List;

@Stateless
public class ProductService {
    @PersistenceContext(unitName = "GMAEJB")
    private EntityManager em;

    public List<ProductEntity> findAllProducts() {
        return em.createNamedQuery("ProductEntity.findAll", ProductEntity.class)
                .getResultList();
    }

    public ProductEntity findProductByDay(Date date) {
        return em.createNamedQuery("ProductEntity.findProductByDay", ProductEntity.class)
                .setParameter("date", date)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}
