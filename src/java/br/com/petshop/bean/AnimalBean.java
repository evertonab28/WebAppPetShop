package br.com.petshop.bean;

import br.com.petshop.dao.DAO;
import br.com.petshop.model.Animal;
import br.com.petshop.model.Cliente;
import br.com.petshop.model.Raca;
import br.com.petshop.service.FacesMessages;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
//@RequestScoped
public class AnimalBean {

    private Animal animal;
    private final DAO<Animal> ANIMALDAO = new DAO<>(Animal.class);
    private Integer racaId;
    private Integer proprietarioId;
    private String sexo;
    private FacesMessages messages = new FacesMessages();
    private Animal animalSelecionado;

    private List<Animal> animais = new ArrayList<Animal>();
    private List<Cliente> clientes = new ArrayList<Cliente>();
    private DAO<Cliente> daoCliente = new DAO<>(Cliente.class);
    private Cliente clienteSelecionado;
    private Cliente cliente;

    public Animal getAnimalSelecionado() {
        return animalSelecionado;
    }

    public void setAnimalSelecionado(Animal animalSelecionado) {
        this.animalSelecionado = animalSelecionado;
    }

    public void prepararSalvar() {
        animal = new Animal();
    }

    public void salvar() {
        Integer id = this.animal.getId();
        String operacao = "";
        Raca raca = new DAO<Raca>(Raca.class).porId(this.racaId);
        Cliente proprietario = new DAO<Cliente>(Cliente.class).porId(this.proprietarioId);
        this.animal.setRaca(raca);
        this.animal.setProprietario(proprietario);
        if (id == null) {
            ANIMALDAO.salvar(this.animal);
            operacao = "salvo";
        } else {
            ANIMALDAO.alterar(this.animal);
            operacao = "alterado";
        }
        messages.info("Animal " + operacao + " com sucesso");
        PrimeFaces.current().ajax().update(
                Arrays.asList("frm:msgs", "frm:animal-tabela")
        );
    }

    public void excluir() {
        ANIMALDAO.excluir(this.animalSelecionado);
        this.animalSelecionado = null;

        getAnimais();
        messages.info("Animal excluido com Sucesso!");
    }

    public List<Raca> getRacas() {
        return new DAO<Raca>(Raca.class).listaTodos();
    }

    public List<Cliente> getCliente() {
        return new DAO(Cliente.class).listaTodos();
    }

    public List<Animal> getAnimais() {
        return new DAO(Animal.class).listaTodos();
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Integer getRacaId() {
        return racaId;
    }

    public void setRacaId(Integer racaId) {
        this.racaId = racaId;
    }

    public Integer getProprietarioId() {
        return proprietarioId;
    }

    public void setProprietarioId(Integer proprietarioId) {
        this.proprietarioId = proprietarioId;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
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

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }

    public void handleSelect(SelectEvent event) {
        Cliente c = (Cliente) event.getObject();
        System.out.println("SELECIONADO " + c.getCPF());
    }

}
