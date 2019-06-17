package br.com.petshop.model;

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

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {      

        Animal teste = new Animal();
        teste.setNome("Deu ruim");
        
        for (Animal a : getAnimais()) {
            if (a.getNome().equals(string)) {
                return a;
            }
        }
        return teste;
    }
//    @Override
//    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
//        ClienteDAO clienteDAO = new ClienteDAO();
//        Cliente cliente = clienteDAO.consultarPorClienteNome(string);
//        return cliente;
//    }

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
//        return cliente.getNome();
        return null;
    }

}
