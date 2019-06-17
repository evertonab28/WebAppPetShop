/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petshop.dao;

import br.com.petshop.model.Animal;
import java.io.Serializable;
import javax.persistence.EntityManager;

/**
 *
 * @author Tom
 */
public class AnimalDAO implements Serializable {

    private EntityManager em;

    public AnimalDAO() {
    }

    public AnimalDAO(EntityManager em) {
        this.em = em;
    }

    public Animal consultarPorAnimal(String nome) {
        Animal animal = (Animal) em.createQuery("SELECT c FROM animal a WHERE a.nome LIKE :nome")
                .setParameter("nome", nome).getSingleResult();
        return animal;
    }

}
