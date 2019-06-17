/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petshop.webservices;

import br.com.petshop.dao.DAO;
import br.com.petshop.model.Cliente;
import br.com.petshop.model.Animal;
import br.com.petshop.model.Raca;
import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 */
@Path("petshop")
public class Petshop {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Petshop
     */
    public Petshop() {
    }

    /**
     * Retrieves representation of an instance of
     * br.com.petshop.webservices.Petshop
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cliente/get")
    public String getCliente() {
        Cliente c = new Cliente();
        c.setNome("Jon Snow");
        c.setEndereco("Castle Black");
        Gson g = new Gson();
        return g.toJson(c);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("animal/get")
    public String getAnimal() {
        Animal a = new Animal();

        a.setNome("Leia");
        Gson g = new Gson();
        return g.toJson(a);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("animal/get/{id}")
    public String getAnimal(@PathParam("id") Integer id) {
        Animal a = new Animal();
        a.setId(id);

        DAO<Animal> dao = new DAO<>(Animal.class);
        a = dao.porId(id);

        Gson g = new Gson();
        return g.toJson(a);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cliente/get/{id}")
    public String getCliente(@PathParam("id") Integer id) {
        Cliente c = new Cliente();
        c.setId(id);

        DAO<Cliente> dao = new DAO<>(Cliente.class);
        c = dao.porId(id);

        Gson g = new Gson();
        return g.toJson(c);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("raca/list")
    public String listRacas() {
        List<Raca> listRacas;

        DAO<Raca> dao = new DAO<>(Raca.class);
        listRacas = dao.listaTodos();

        Gson g = new Gson();
        return g.toJson(listRacas);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cliente/list")
    public String listClientes() {
        List<Cliente> listClientes;

        DAO<Cliente> dao = new DAO<>(Cliente.class);
        listClientes = dao.listaTodos();

        Gson g = new Gson();
        return g.toJson(listClientes);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("animal/list")
    public String listAnimais() {
        List<Animal> listAnimais;

        DAO<Animal> dao = new DAO<>(Animal.class);
        listAnimais = dao.listaTodos();

        Gson g = new Gson();
        return g.toJson(listAnimais);
    }

    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    @Path("animal/excluir/{id}")
    public String excluirAnimal(@PathParam("id") Integer id) {
        Animal a = new Animal();
        a.setId(id);

        DAO<Animal> dao = new DAO<>(Animal.class);
        a = dao.porId(id);
        dao.excluir(a);

        return "Excluido";
    }

    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    @Path("cliente/excluir/{id}")
    public String excluirCliente(@PathParam("id") Integer id) {
        Cliente c = new Cliente();
        c.setId(id);

        DAO<Cliente> dao = new DAO<>(Cliente.class);
        c = dao.porId(id);
        dao.excluir(c);

        return "Excluido";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("raca/inserir")
    public String inserirRaca(String conteudo) {
        Gson g = new Gson();
        Raca r = (Raca) g.fromJson(conteudo, Raca.class);

        DAO<Raca> dao = new DAO<>(Raca.class);
        dao.salvar(r);

        return "Salvo!";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("animal/inserir")
    public String inserirAnimal(String conteudo) {
        Gson g = new Gson();
        Animal a = (Animal) g.fromJson(conteudo, Animal.class);

        DAO<Animal> dao = new DAO<>(Animal.class);
        dao.salvar(a);

        return "Salvo!";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("cliente/inserir")
    public String inserirCliente(String conteudo) {
        Gson g = new Gson();

        String get = "{\"master\":" + conteudo + "}";

        Cliente c = (Cliente) g.fromJson(conteudo, Cliente.class);
        //Cliente c = (Cliente) g.fromJson(get, Cliente.class);

        DAO<Cliente> dao = new DAO<>(Cliente.class);
        dao.salvar(c);

        return "Salvo!";
    }

    /**
     * PUT method for updating or creating an instance of Petshop
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("cliente/alterar")
    public void alterarCliente(String content) {
        Gson g = new Gson();
        Cliente c = (Cliente) g.fromJson(content, Cliente.class);
        DAO<Cliente> dao = new DAO<>(Cliente.class);
        dao.alterar(c);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("animal/alterar")
    public void alterar(String content) {
        Gson g = new Gson();
        Animal a = (Animal) g.fromJson(content, Animal.class);

        DAO<Animal> dao = new DAO<>(Animal.class);
        dao.alterar(a);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
