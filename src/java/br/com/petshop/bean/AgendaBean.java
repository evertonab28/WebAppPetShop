package br.com.petshop.bean;

import br.com.petshop.dao.DAO;
import br.com.petshop.model.Agenda;
import br.com.petshop.model.Animal;
import br.com.petshop.model.Cliente;
import br.com.petshop.model.FormaDePagamento;
import br.com.petshop.model.ItemServico;
import br.com.petshop.model.Servico;
import br.com.petshop.service.FacesMessages;
import static com.sun.org.apache.xml.internal.serializer.utils.Utils.messages;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.PrimeFaces;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

@ManagedBean
//@SessionScoped
@ApplicationScoped
//@ViewScoped
public class AgendaBean implements Serializable {

    private FacesMessages messages = new FacesMessages();
    private Agenda agenda = new Agenda();
    private DAO<Agenda> agendaDao = new DAO<>(Agenda.class);
    private Agenda agendaSelecionada;

    private List<Cliente> clientes = new ArrayList<Cliente>();
    //private List<Servico> servicos = new ArrayList<Servico>();
    private DAO<Cliente> daoCliente = new DAO<>(Cliente.class);
    private List<Animal> animais = new ArrayList<Animal>();
    private List<ItemServico> itens = new ArrayList<ItemServico>();
    private final DAO<Animal> ANIMALDAO = new DAO<>(Animal.class);
    private final DAO<Servico> SERVICODAO = new DAO<>(Servico.class);
    private final DAO<ItemServico> ITEMSERVICODAO = new DAO<>(ItemServico.class);
    private Cliente clienteSelecionado;
    private Animal animalSelecionado;
    private Servico servico = new Servico();
    private ItemServico item = new ItemServico();
    private DAO<ItemServico> itemDao = new DAO<>(ItemServico.class);

    private Integer animalId;

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public ItemServico getItem() {
        return item;
    }

    public List<Servico> getListaServicos() {
        return new DAO(Servico.class).listaTodos();
    }

    public void setItem(ItemServico itemServico) {
        this.item = itemServico;
    }

    public Integer getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Integer animalId) {
        this.animalId = animalId;
    }

    public Animal getAnimalSelecionado() {
        return animalSelecionado;
    }

    public void setAnimalSelecionado(Animal animalSelecionado) {
        this.animalSelecionado = animalSelecionado;
    }

    public Agenda getAgendaSelecionada() {
        return agendaSelecionada;
    }

    public void setAgendaSelecionada(Agenda agendaSelecionada) {
        this.agendaSelecionada = agendaSelecionada;
    }

    public void prepararSalvar() {
        agenda = new Agenda();
    }

    public void salvarAgenda() {
        Integer id = this.agenda.getId();
        String operacao = "";

        this.agenda.setAnimal(animalSelecionado);
        this.agenda.setAnimal(animalSelecionado);

        if (id == null) {
            this.agenda = agendaDao.salvarComRetorno(this.agenda);
            operacao = "salva";
        } else{
            this.agenda = agendaDao.salvarComRetorno(this.agenda);
            operacao = "editada";
        }

        messages.info("Agenda " + operacao + " com sucesso!");
        PrimeFaces.current().ajax()
                .update(Arrays.asList("frm:msgs-dialog"));
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
            operacao = "Item adicionado com sucesso!";

            item = new ItemServico();
        }

        messages.info(operacao);
        PrimeFaces.current().ajax()
                .update(Arrays.asList("frm:msgs-dialog", "itensServico"));
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

    public BigDecimal total() {
        BigDecimal soma = getItensDaAgenda().stream().map(ItemServico::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return soma;
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

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }

    public List<Cliente> getCliente() {
        return new DAO(Cliente.class).listaTodos();
    }

    public List<Animal> getAnimalLista() {
        return new DAO(Animal.class).listaTodos();
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

    public void handleSelectCliente(SelectEvent event) {
        Cliente c = (Cliente) event.getObject();
        System.out.println("Objeto");
        System.out.println("SELECIONADO " + c);
        System.out.println("----");
        System.out.println("Cliente Selecionado");
        System.out.println(clienteSelecionado);

    }

    public void umEvento(SelectEvent e) {
        Animal a = (Animal) e.getObject();
        System.out.println("---------");
        System.out.println(a);
        System.out.println(animalSelecionado.getNome());
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
