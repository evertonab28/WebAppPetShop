package br.com.petshop.bean;

import br.com.petshop.dao.ClienteDAO;
import br.com.petshop.dao.DAO;
import br.com.petshop.dao.JPAUtil;
import br.com.petshop.model.Cliente;
import br.com.petshop.model.Servico;
import br.com.petshop.service.FacesMessages;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import org.primefaces.PrimeFaces;

@ManagedBean
@ViewScoped
public class ServicoBean implements Serializable {

    private FacesMessages messages = new FacesMessages();
    private Servico servico;
    private final DAO<Servico> SERVICODAO = new DAO<>(Servico.class);
    private Servico servicoSelecionado;

    public Servico getServicoSelecionado() {
        return servicoSelecionado;
    }

    public void setServicoSelecionado(Servico servicoSelecionado) {
        this.servicoSelecionado = servicoSelecionado;
    }

    public void prepararSalvar() {
        servico = new Servico();
    }

    public void salvar() {
        Integer id = this.servico.getId();
        String operacao = "";

        if (id == null) {
            SERVICODAO.salvar(this.servico);
            operacao = "salvo";
        } else {
            SERVICODAO.alterar(this.servico);
            operacao = "alterado";
        }
        messages.info("Serviço " + operacao + " com sucesso!");
        PrimeFaces.current().ajax().update(Arrays.asList("frm:msgs", "frm:servico-tabela"));
    }

    public void excluir() {
        SERVICODAO.excluir(this.servicoSelecionado);
        this.servicoSelecionado = null;

        getServicos();
        messages.info("Serviço excluido com sucesso!");
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public List<Servico> getServicos() {
        return new DAO(Servico.class).listaTodos();
    }
}
