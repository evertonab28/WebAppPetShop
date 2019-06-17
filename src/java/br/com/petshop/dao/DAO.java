package br.com.petshop.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public class DAO<T> {

    private final static EntityManager MANAGER = ConnectionFactory.getEntityManager();
    //private JPAUtil factory = new JPAUtil();  
//    private EntityManagerFactory emf
//            = Persistence.createEntityManagerFactory("frameworks");
    private final Class<T> classe;

    public DAO(Class<T> classe) {
        this.classe = classe;
    }

    public void salvar(T t) {
        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
        em.close();
    }

    public T salvarComRetorno(T t) {
        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();
        T instancia = em.merge(t);
        em.getTransaction().commit();
        em.close();
        return instancia;
    }

    public void alterar(T t) {
        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();
        em.merge(t);
        em.getTransaction().commit();
        em.close();
    }

    public void excluir(T t) {
        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(t));
        em.getTransaction().commit();
        em.close();
    }

    public List<T> listaTodos() {
        //EntityManager em = factory.getEntityManager();
        EntityManager em = new JPAUtil().getEntityManager();
        CriteriaQuery<T> query = em.getCriteriaBuilder().
                createQuery(classe);
        query.select(query.from(classe));

        List<T> lista = em.createQuery(query).getResultList();
        em.close();
        return lista;
    }

    public T findById(Class<T> clazz, Integer id) {
        return MANAGER.find(clazz, id);
    }

    public T porId(Integer id) {
        EntityManager em = new JPAUtil().getEntityManager();
        return em.find(this.classe, id);
    }

    public <T> List<T> findByForeignKey(Class<T> clazz, Integer idFK, String nomeColuna) {
        String tabela = clazz.getSimpleName();
        String jpql = "from " + tabela + " where " + nomeColuna + " like :idFK";
        Query query = MANAGER.createQuery(jpql, clazz);
        query.setParameter("idFK", idFK + "%");
        return (List<T>) query.getResultList();
    }
        
}
