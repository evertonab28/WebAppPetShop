package br.com.petshop.bean;

import br.com.petshop.dao.DAO;
import br.com.petshop.model.Agenda;
import br.com.petshop.model.ItemServico;
import br.com.petshop.model.Servico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

@ManagedBean
@SessionScoped
//@ViewScoped
public class ItemServicoBean implements Serializable {

    private Servico servico;
    private final DAO<Servico> SERVICODAO = new DAO<>(Servico.class);
    private List<ItemServico> itens = new ArrayList<ItemServico>();
    //private List<Servico> servicos = new ArrayList<Servico>();
    private Integer servicoId;
    private ItemServico itemServico = new ItemServico();

    public ItemServico getItemServico() {
        return itemServico;
    }

    public void setItemServico(ItemServico itemServico) {
        this.itemServico = itemServico;
    }

    public Integer getServicoId() {
        return servicoId;
    }

    public void setServicoId(Integer servicoId) {
        this.servicoId = servicoId;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public List<ItemServico> getItensServico() {
        return itens;
    }

    public void setItens(List<ItemServico> itens) {
        this.itens = itens;
    }

    public void handleSelectItemServico(SelectEvent event) {
        Servico s = (Servico) event.getObject();
    }

    public List<Servico> getServicos() {
        return new DAO(Servico.class).listaTodos();
    }

    public List<ItemServico> getItem() {
        return new DAO(ItemServico.class).listaTodos();
    }

    
    //NAO SERÁ UTILIZADO
//    public void addItem() {
//        ItemServico itemServico = new ItemServico();
//        Servico servico = new DAO<Servico>(Servico.class).porId(this.servicoId);
//        itemServico.setServico(servico);
//        //itemServico.setAgenda(agenda);
//        //itemServico.setValor(servico.getValor());
//
//        itens.add(itemServico);
//    }

    public void handleSelectServico(SelectEvent event) {
        Servico s = (Servico) event.getObject();
    }

}
