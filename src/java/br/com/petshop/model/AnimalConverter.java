package br.com.petshop.model;

import br.com.petshop.dao.AnimalDAO;
import br.com.petshop.dao.ClienteDAO;
import br.com.petshop.dao.DAO;
import java.util.List;
import br.com.petshop.model.Cliente;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "animalConverter")
public class AnimalConverter implements Converter {

    public List<Animal> getAnimais() {
        return new DAO(Animal.class).listaTodos();
    }

//    @Override
//    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
//
//        Integer id = Integer.valueOf(string);
//
//        for (Cliente c : getClientes()) {
//            if (c.getId() == id) {
//                return c;
//            }
//        }
//        return null;
//    }
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

        AnimalDAO daoAnimal = new AnimalDAO();
        Animal animal = daoAnimal.consultarPorAnimal(string);
        return animal;
    }

//    @Override
//    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
//
//        if (o instanceof Cliente) {
//            Cliente cliente = (Cliente) o;
//            return String.valueOf(cliente.getId());
//        }
//
//        return "";
//    }
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Animal animal = new Animal();
        animal = (Animal) o;
        return animal.getNome();
    }

}
