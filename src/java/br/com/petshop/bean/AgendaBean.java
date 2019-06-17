package br.com.petshop.bean;

import br.com.petshop.dao.DAO;
import br.com.petshop.model.Agenda;
import br.com.petshop.model.Animal;
import br.com.petshop.model.Cliente;
import br.com.petshop.model.FormaDePagamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class AgendaBean implements Serializable {

    private Agenda agenda = new Agenda();
    private DAO<Agenda> agendaDao = new DAO<>(Agenda.class);
    private Agenda agendaSelecionada;

    private List<Cliente> clientes = new ArrayList<Cliente>();
    private DAO<Cliente> daoCliente = new DAO<>(Cliente.class);
    private List<Animal> animais = new ArrayList<Animal>();
    private final DAO<Animal> ANIMALDAO = new DAO<>(Animal.class);

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

    public List<Cliente> completaNomeCliente(String query) {
        this.clientes = daoCliente.listaTodos();
        List<Cliente> nomes = new ArrayList<Cliente>();
        for (Cliente c : this.clientes) {
            if (c.getNome().startsWith(query)) {
                nomes.add(c);
            }
        }
        return nomes;
    }

    public List<Animal> animalPorProprietario(Cliente cliente) {
        this.animais = ANIMALDAO.listaTodos();
        List<Animal> animaisCliente = new ArrayList<Animal>();
        for (Animal a : this.animais) {
            if (a.getProprietario().equals(cliente)) {
                animaisCliente.add(a);
            }
        }
        return animaisCliente;
    }
}
