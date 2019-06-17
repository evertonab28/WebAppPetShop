package br.com.petshop.dao;

import br.com.petshop.model.Cliente;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.jboss.logging.Logger;

public class ClienteDAO implements Serializable {

    private EntityManager em;

    public ClienteDAO() {
    }

    public ClienteDAO(EntityManager em) {
        this.em = em;
    }

    public Cliente porId(Integer id) {
        return em.find(Cliente.class, id);
    }

    public List<Cliente> consultarPorNome(String nome) {
        String jpql = "from Cliente where nome like :nome";
        TypedQuery<Cliente> query
                = em.createQuery(jpql, Cliente.class);
        query.setParameter("nome", nome + "%");
        return query.getResultList();
    }

    public Cliente consultarPorClienteNome(String nome) {
        try {
            String jpql = "SELECT * from Cliente where nome like :nome";
            TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
            query.setParameter("nome", nome + "%");
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        
    }

    public void salvar(Cliente cliente) {
        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
        em.close();
    }

    public void alterar(Cliente cliente) {
        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();
        em.merge(cliente);
        em.getTransaction().commit();
        em.close();
    }

    public void excluir(Cliente cliente) {
        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(cliente));
        em.getTransaction().commit();
        em.close();
    }
}
