package com.universidade.model;

public class Materia {

    private Integer id;
    private String codigo;
    private String nome;
    private String ementa;

    public Materia() {}

    public Materia(Integer id, String codigo, String nome, String ementa) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.ementa = ementa;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmenta() { return ementa; }
    public void setEmenta(String ementa) { this.ementa = ementa; }
}
