package br.com.petshop.model;

import br.com.petshop.dao.ClienteDAO;
import br.com.petshop.dao.DAO;
import java.util.List;
import br.com.petshop.model.Cliente;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "clienteConverter")
public class ClienteConverter implements Converter {

    public List<Cliente> getClientes() {
        return new DAO(Cliente.class).listaTodos();
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {      

        Cliente teste = new Cliente();
        teste.setNome("Deu ruim");
        
        for (Cliente c : getClientes()) {
            if (c.getNome().equals(string)) {
                return c;
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
        Cliente cliente = new Cliente();
        cliente = (Cliente) o;
//        return cliente.getNome();
        return null;
    }

}
