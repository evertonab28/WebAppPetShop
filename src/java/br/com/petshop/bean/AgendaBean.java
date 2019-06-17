package br.com.petshop.bean;

import br.com.petshop.dao.DAO;
import br.com.petshop.model.Agenda;
import br.com.petshop.model.FormaDePagamento;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class AgendaBean implements Serializable {

    private Agenda agenda = new Agenda();
    private DAO<Agenda> agendaDao = new DAO<>(Agenda.class);
    private Agenda agendaSelecionada;

    public Agenda getAgendaSelecionada() {
        return agendaSelecionada;
    }

    public void salvar() {
        this.agenda = agendaDao.salvarComRetorno(agenda);
    }

    public FormaDePagamento[] getFormaDePagamentos() {
        return FormaDePagamento.values();
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public List<Agenda> getAgendas() {
        return new DAO(Agenda.class).listaTodos();
    }

}
