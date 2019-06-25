package br.com.petshop.bean;

import br.com.petshop.dao.DAO;
import br.com.petshop.model.Agenda;
import br.com.petshop.model.Animal;
import br.com.petshop.model.Cliente;
import br.com.petshop.model.FormaDePagamento;
import br.com.petshop.model.ItemServico;
import br.com.petshop.model.Servico;
import br.com.petshop.service.FacesMessages;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;

@ManagedBean
//@SessionScoped
//@ApplicationScoped
@ViewScoped
public class AgendaBean implements Serializable {

    private FacesMessages messages = new FacesMessages();
    private Agenda agenda = new Agenda();
    private Animal animal = new Animal();
    private Servico servico = new Servico();
    private ItemServico item = new ItemServico();
    private Cliente cliente = new Cliente();
    private Agenda agendaSelecionada = new Agenda();
    private ItemServico itemServicoSelecionado = new ItemServico();

    private final DAO<Agenda> agendaDao = new DAO<>(Agenda.class);
    private final DAO<Animal> ANIMALDAO = new DAO<>(Animal.class);
    private final DAO<ItemServico> ITEMSERVICODAO = new DAO<>(ItemServico.class);
    private final DAO<Cliente> daoCliente = new DAO<>(Cliente.class);
    private final DAO<ItemServico> itemDao = new DAO<>(ItemServico.class);

    private List<Cliente> clientes = new ArrayList<Cliente>();
    private List<Animal> animais = new ArrayList<Animal>();
    private List<ItemServico> itens = new ArrayList<ItemServico>();

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public ItemServico getItem() {
        return item;
    }

    public void setItem(ItemServico itemServico) {
        this.item = itemServico;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Agenda getAgendaSelecionada() {
        return agendaSelecionada;
    }

    public void setAgendaSelecionada(Agenda agendaSelecionada) {
        this.agendaSelecionada = agendaSelecionada;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public ItemServico getItemServicoSelecionado() {
        return itemServicoSelecionado;
    }

    public void setItemServicoSelecionado(ItemServico itemServicoSelecionado) {
        this.itemServicoSelecionado = itemServicoSelecionado;
    }

    public List<Servico> getListaServicos() {
        return new DAO(Servico.class).listaTodos();
    }

    public List<ItemServico> getItensDaAgenda() {
        this.itens = ITEMSERVICODAO.listaTodos();
        List<ItemServico> lista = new ArrayList<ItemServico>();

        for (ItemServico item : this.itens) {
            if (item.getAgenda().equals(agenda)) {
                lista.add(item);
            }
        }
        return lista;
    }

    public List<Cliente> getClientes() {
        return new DAO(Cliente.class).listaTodos();
    }

    public List<Animal> getAnimalLista() {
        return new DAO(Animal.class).listaTodos();
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

    public List<Agenda> getAgendas() {
        return new DAO(Agenda.class).listaTodos();
    }

    private List<Agenda> agendasFiltradas;

    public List<Agenda> getAgendasFiltradas() {
        return agendasFiltradas;
    }

    public void setAgendasFiltradas(List<Agenda> agendasFiltradas) {
        this.agendasFiltradas = agendasFiltradas;
    }

    public void prepararSalvar() {
        agenda = new Agenda();
        cliente = new Cliente();
        animal = new Animal();
    }

    public void salvarAgenda() {
        Integer id = this.agenda.getId();
        String operacao = "";
        this.agenda.setAnimal(animal);

        if (id == null) {
            this.agenda = agendaDao.salvarComRetorno(this.agenda);
            operacao = "salva";
        } else {
            this.agenda = agendaDao.salvarComRetorno(this.agenda);
            operacao = "editada";
        }

        messages.info("Agenda " + operacao + " com sucesso!");
        PrimeFaces.current().ajax()
                .update(Arrays.asList("frm:msgs-dialog", "frm:agendamento-tabela"));
    }

    public void salvarItem() {
        Integer id = agenda.getId();
        String operacao = "";

        if (id == null) {
            operacao = "Salve a agenda primeiro!";
        } else {
            item.setAgenda(agenda);
            item.setServico(servico);
            item.setValor(servico.getValor());
            itemDao.salvar(item);
            operacao = "Servico adicionado com sucesso!";
            item = new ItemServico();
        }

        messages.info(operacao);
        PrimeFaces.current().ajax()
                .update(Arrays.asList("frm:msgs-dialog", "itensServico"));
    }

    public void removerItem() {
        itemDao.excluir(this.itemServicoSelecionado);
        itemServicoSelecionado = null;

        getItensDaAgenda();
        messages.info("Serviço removido!");
        PrimeFaces.current().ajax()
                .update(Arrays.asList("frm:msgs-dialog", "itensServico"));
    }

    public void fechar() {
        agendaSelecionada = null;
        agenda = null;
        animal = null;
        cliente = null;

        PrimeFaces.current().ajax()
                .update(Arrays.asList("frm:msgs-dialog", "frm:agendamento-tabela", "frm:toolbar"));
    }

    public void cancelarAgendamento() {
        agendaSelecionada.setStatus("Cancelado");
        agendaDao.alterar(this.agendaSelecionada);
        agendaSelecionada = null;
        String operacao = "Agendamento cancelado!";

        messages.info(operacao);
        PrimeFaces.current().ajax()
                .update(Arrays.asList("frm:msgs", "frm:agendamento-tabela", "frm:toolbar"));
    }

    public void finalizarAgendamento() {
        agendaSelecionada.setStatus("Finalizado");
        agendaDao.alterar(this.agendaSelecionada);
        agendaSelecionada = null;
        String operacao = "Agendamento finalizado!";

        messages.info(operacao);
        PrimeFaces.current().ajax()
                .update(Arrays.asList("frm:msgs", "frm:agendamento-tabela", "frm:toolbar"));
    }

    public BigDecimal totalValorServicos() {
        BigDecimal soma = getItensDaAgenda().stream().map(ItemServico::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return soma;
    }

    public FormaDePagamento[] getFormaDePagamentos() {
        return FormaDePagamento.values();
    }

    public Boolean enableTab() {
        Integer id = agenda.getId();
        if (id == null || agendaSelecionada.getId() == null) {
            return true;
        } else {
            return false;
        }
    }

//    WIZARD A PARTIR DAQUI
    private boolean skip;

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }
//WIZARD TERMINA AQUI
}
