package com.universidade.model;

import java.time.LocalDate;

public class Aluno {

    private Integer id;
    private String nome;
    private String endereco;
    private String matricula;
    private LocalDate dataIngresso;

    public Aluno() {}

    public Aluno(Integer id, String nome, String endereco, String matricula, LocalDate dataIngresso) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.matricula = matricula;
        this.dataIngresso = dataIngresso;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public LocalDate getDataIngresso() { return dataIngresso; }
    public void setDataIngresso(LocalDate dataIngresso) { this.dataIngresso = dataIngresso; }
}
