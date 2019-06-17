package br.com.petshop.webservices;

import br.com.petshop.dao.DAO;
import br.com.petshop.model.Animal;
import br.com.petshop.model.Cliente;
import br.com.petshop.model.Raca;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class ConsumirWSHTTP {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {                           
        
        ConsumirWSHTTP http = new ConsumirWSHTTP();
        Gson gson = new Gson();
        Cliente cliente = new Cliente();
        Type clienteType = new TypeToken<Cliente>() {
        }.getType();
        Type clienteTypeLista = new TypeToken<List<Cliente>>() {
        }.getType();

        Animal animal = new Animal();
        Type animalType = new TypeToken<Animal>() {
        }.getType();

        Raca raca = new Raca();
        Type racaType = new TypeToken<Raca>() {
        }.getType();

        //Consultar Cliente
        String url = "http://localhost:8080/WebAppPetShopAtualizado/webresources/petshop/cliente/list";
        String json = http.sendGet(url, "GET");
        List<Cliente> lista = new LinkedList<>();
        lista = gson.fromJson(json, clienteTypeLista);

        //listar os clientes
        for (Cliente cliente1 : lista) {
            System.out.println("CPF....:" + cliente1.getCPF());
            System.out.println("Nome...:" + cliente1.getNome());
        }

        //inserir cliente
        //se quiser alterar, tem que pegar o ID primeiro
        cliente.setId(8);
        cliente.setNome("Iron Jacksonn");
        cliente.setCPF("123.123.123.43");
        cliente.setEmail("email@do.cliente");
        cliente.setRG("111.222.444");
        cliente.setTelefone("9999-1111");
        cliente.setEndereco("Rua azul e amarela");
        String json2 = gson.toJson(cliente, clienteType);
        //para alterar apenas mudar a url para alterar
        String urlInserir = "http://localhost:8080/WebAppPetShopAtualizado/webresources/petshop/cliente/alterar";
        //String urlInserir = "http://localhost:8080/WebAppPetShopAtualizado/webresources/petshop/cliente/inserir";

        //e mudar este parâmetro para PUT
        String retorno = http.sendPost(urlInserir, json2, "PUT");
//        String retorno = http.sendPost(urlInserir, json2, "POST");
        //System.out.println(json2);
        //System.out.println("");
        System.out.println(retorno);
        System.out.println("-----------------");

        //INSERIR RACA
        raca.setRaca("Gato Tigrado");
        String jsonRaca = gson.toJson(raca, racaType);
        String urlInserirRaca = "http://localhost:8080/WebAppPetShopAtualizado/webresources/petshop/raca/inserir";
        String retornoRaca = http.sendPost(urlInserirRaca, jsonRaca, "POST");
        System.out.println("-----------------------");

                        
        DAO<Cliente> daoCli = new DAO<>(Cliente.class);
        Cliente clienteAnimal = daoCli.findById(Cliente.class, 6);
        
        DAO<Raca> daoRaca = new DAO<>(Raca.class);
        Raca racaAn = daoRaca.findById(Raca.class, 5);
        
        
        //INSERIR ANIMAL
        animal.setNome("Leia Charao Bezerra");
        animal.setIdade(1);
        animal.setPeso(1.5);
        animal.setSexo("Femea");
        animal.setProprietario(clienteAnimal);
        animal.setRaca(racaAn);
        animal.setObservacao("obs");
        animal.setUrl("http://");

        String jsonAnimal = gson.toJson(animal, animalType);
        String urlInserirAnimal = "http://localhost:8080/WebAppPetShopAtualizado/webresources/petshop/animal/inserir";
        String retornoAnimal = http.sendPost(urlInserirAnimal, jsonAnimal, "POST");

        //excluir cliente
//        String urlExcluir = "http://localhost:8080/WebAppPetShopAtualizado/webresources/petshop/cliente/excluir/12";
//        String retorno2 = http.sendGet(urlExcluir, "GET");
//        System.out.println(retorno);
    }

    // HTTP GET request
    private String sendGet(String url, String method) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();

    }

    // HTTP POST request
    private String sendPost(String url, String urlParameters, String method) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod(method);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        //String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();

    }

}
