package br.com.petshop.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class ConnectionFactory {

    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("frameworks");

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }

        public static void closeEntityManager(EntityManager em) {
        try {
            em.close();
        } catch (PersistenceException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
