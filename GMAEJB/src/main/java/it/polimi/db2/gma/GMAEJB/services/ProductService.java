package it.polimi.db2.gma.GMAEJB.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProductService {
    @PersistenceContext(unitName = "GMAEJB")
    private EntityManager em;


}
