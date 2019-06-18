package br.com.petshop.model;

import br.com.petshop.dao.ClienteDAO;
import br.com.petshop.dao.DAO;
import java.util.List;
import br.com.petshop.model.Cliente;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "itemServicoConverter")
public class ItemServicoConverter implements Converter {

    public List<Servico> getServicos() {
        return new DAO(Servico.class).listaTodos();
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {      

        Servico teste = new Servico();
        teste.setDescricao("Deu ruim");
        
        for (Servico s : getServicos()) {
            if (s.getDescricao().equals(string)) {
                return s;
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
        Servico servico = new Servico();
        servico = (Servico) o;
//        return cliente.getNome();
        return null;
    }

}
