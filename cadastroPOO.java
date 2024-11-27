package model;

import java.io.Serializable;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.io.IOException;



public class Pessoa implements Serializable {
    private int id;
    private String nome;

     
    public Pessoa() {}

    
    public Pessoa(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

     
    public void exibir() {
        System.out.println("ID: " + id + ", Nome: " + nome);
    }
}

public class PessoaFisica extends Pessoa {
    private String cpf;
    private int idade;

    
    public PessoaFisica() {}

     
    public PessoaFisica(int id, String nome, String cpf, int idade) {
        super(id, nome);
        this.cpf = cpf;
        this.idade = idade;
    }

    // Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    // Método exibir polimórfico
    @Override
    public void exibir() {
        super.exibir();
        System.out.println("CPF: " + cpf + ", Idade: " + idade);
    }
}

public class PessoaJuridica extends Pessoa {
    private String cnpj;

     
    public PessoaJuridica() {}

     
    public PessoaJuridica(int id, String nome, String cnpj) {
        super(id, nome);
        this.cnpj = cnpj;
    }

    // Getters e Setters
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    // Método exibir polimórfico
    @Override
    public void exibir() {
        super.exibir();
        System.out.println("CNPJ: " + cnpj);
    }
}

public class PessoaFisicaRepo {
    private ArrayList<PessoaFisica> lista = new ArrayList<>();

    // Método inserir
    public void inserir(PessoaFisica pessoaFisica) {
        lista.add(pessoaFisica);
    }

    // Método alterar
    public void alterar(PessoaFisica pessoaFisica) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == pessoaFisica.getId()) {
                lista.set(i, pessoaFisica);
                return;
            }
        }
        throw new IllegalArgumentException("Pessoa física com ID " + pessoaFisica.getId() + " não encontrada.");
    }

    // Método excluir
    public void excluir(int id) {
        lista.removeIf(pessoaFisica -> pessoaFisica.getId() == id);
    }

    // Método obter (por ID)
    public PessoaFisica obter(int id) {
        Optional<PessoaFisica> pessoa = lista.stream()
                .filter(pessoaFisica -> pessoaFisica.getId() == id)
                .findFirst();
        return pessoa.orElse(null);
    }

    // Método obterTodos
    public ArrayList<PessoaFisica> obterTodos() {
        return new ArrayList<>(lista);
    }

    // Método persistir
    public void persistir(String nomeArquivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(lista);
        }
    }

    // Método recuperar
    public void recuperar(String nomeArquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            lista = (ArrayList<PessoaFisica>) ois.readObject();
        }
    }
}

public class PessoaJuridicaRepo {
    private ArrayList<PessoaJuridica> lista = new ArrayList<>();

    // Método inserir
    public void inserir(PessoaJuridica pessoaJuridica) {
        lista.add(pessoaJuridica);
    }

    // Método alterar
    public void alterar(PessoaJuridica pessoaJuridica) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == pessoaJuridica.getId()) {
                lista.set(i, pessoaJuridica);
                return;
            }
        }
        throw new IllegalArgumentException("Pessoa jurídica com ID " + pessoaJuridica.getId() + " não encontrada.");
    }

    // Método excluir
    public void excluir(int id) {
        lista.removeIf(pessoaJuridica -> pessoaJuridica.getId() == id);
    }

    // Método obter (por ID)
    public PessoaJuridica obter(int id) {
        Optional<PessoaJuridica> pessoa = lista.stream()
                .filter(pessoaJuridica -> pessoaJuridica.getId() == id)
                .findFirst();
        return pessoa.orElse(null);
    }

    // Método obterTodos
    public ArrayList<PessoaJuridica> obterTodos() {
        return new ArrayList<>(lista);
    }

    // Método persistir
    public void persistir(String nomeArquivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(lista);
        }
    }

    // Método recuperar
    public void recuperar(String nomeArquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            lista = (ArrayList<PessoaJuridica>) ois.readObject();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            // Repositório de Pessoas Físicas - repo1
            PessoaFisicaRepo repo1 = new PessoaFisicaRepo();
            
            // Adicionar duas pessoas físicas
            repo1.inserir(new PessoaFisica(1, "João Silva", "123.456.789-00", 30));
            repo1.inserir(new PessoaFisica(2, "Maria Oliveira", "987.654.321-00", 25));
            
            // Persistir os dados no arquivo
            String arquivoPessoaFisica = "pessoasFisicas.dat";
            repo1.persistir(arquivoPessoaFisica);

            // Repositório de Pessoas Físicas - repo2
            PessoaFisicaRepo repo2 = new PessoaFisicaRepo();
            
            // Recuperar os dados do arquivo
            repo2.recuperar(arquivoPessoaFisica);

            // Exibir os dados recuperados
            System.out.println("Pessoas Físicas Recuperadas:");
            for (PessoaFisica pessoa : repo2.obterTodos()) {
                pessoa.exibir();
            }

            // Repositório de Pessoas Jurídicas - repo3
            PessoaJuridicaRepo repo3 = new PessoaJuridicaRepo();

            // Adicionar duas pessoas jurídicas
            repo3.inserir(new PessoaJuridica(1, "Empresa Alpha", "12.345.678/0001-90"));
            repo3.inserir(new PessoaJuridica(2, "Empresa Beta", "98.765.432/0001-01"));

            // Persistir os dados no arquivo
            String arquivoPessoaJuridica = "pessoasJuridicas.dat";
            repo3.persistir(arquivoPessoaJuridica);

            // Repositório de Pessoas Jurídicas - repo4
            PessoaJuridicaRepo repo4 = new PessoaJuridicaRepo();
            
            // Recuperar os dados do arquivo
            repo4.recuperar(arquivoPessoaJuridica);

            // Exibir os dados recuperados
            System.out.println("\nPessoas Jurídicas Recuperadas:");
            for (PessoaJuridica pessoa : repo4.obterTodos()) {
                pessoa.exibir();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}