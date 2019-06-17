package br.com.petshop.bean;

import br.com.petshop.dao.DAO;
import br.com.petshop.model.Agenda;
import br.com.petshop.model.Animal;
import br.com.petshop.model.ItemServico;
import br.com.petshop.model.Servico;
import br.com.petshop.service.FacesMessages;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;

@ManagedBean
@ViewScoped
public class AgendaBeanOLD {

    private Agenda agenda;
    private final DAO<Agenda> AGENDADAO = new DAO<>(Agenda.class);
    private Integer animalId;
    private Integer servicoId;
    private Integer itemServicoId;
    private FacesMessages messages = new FacesMessages();
    private Agenda agendaSelecionada;
    private Servico servico;
    private ItemServico itemServico;

    public Agenda getAgendaSelecionada() {
        return agendaSelecionada;
    }

    public void setAgendaSelecionada() {
        this.agendaSelecionada = agendaSelecionada;
    }

    public void prepararSalvar() {
        agenda = new Agenda();
    }

    public void salvar() {
        Integer id = this.agenda.getId();
        String operacao = "";
        Animal animal = new DAO<Animal>(Animal.class).porId(this.animalId);
        this.agenda.setAnimal(animal);
        this.itemServico.setServico(servico);
        this.itemServico.setAgenda(agenda);

        if (id == null) {
            AGENDADAO.salvar(this.agenda);
            operacao = "salva";
        } else {
            AGENDADAO.alterar(this.agenda);
            operacao = "alterada";
        }

        messages.info("Agenda " + operacao + " com sucesso");
        PrimeFaces.current().ajax().update(Arrays.asList("frm:msgs", "frm:agenda-tabela"));

    }

    public void excluir() {
        AGENDADAO.excluir(this.agendaSelecionada);
        this.agendaSelecionada = null;

        getAgendas();
        messages.info("Agenda excluida com Sucesso!");
    }

    public List<Agenda> getAgendas() {
        return new DAO(Agenda.class).listaTodos();
    }

    public List<Animal> getAnimais() {
        return new DAO(Animal.class).listaTodos();
    }

    public List<Servico> getServicos() {
        return new DAO(Servico.class).listaTodos();
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public Integer getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Integer animalId) {
        this.animalId = animalId;
    }

    public Integer getServicoId() {
        return servicoId;
    }

    public void setServicoId(Integer servicoId) {
        this.servicoId = servicoId;
    }

}
