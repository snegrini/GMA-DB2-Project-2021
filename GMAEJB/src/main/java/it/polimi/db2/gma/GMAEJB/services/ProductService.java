package it.polimi.db2.gma.GMAEJB.services;

import it.polimi.db2.gma.GMAEJB.entities.ProductEntity;
import it.polimi.db2.gma.GMAEJB.entities.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProductService {
    @PersistenceContext(unitName = "GMAEJB")
    private EntityManager em;



}
