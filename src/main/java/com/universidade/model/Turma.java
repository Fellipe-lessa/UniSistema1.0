package com.universidade.model;

public class Turma {

    private Integer id;
    private String codigo;
    private String descricao;
    private Integer materiaId;
    private String materiaNome;

    public Turma() {}

    public Turma(Integer id, String codigo, String descricao, Integer materiaId) {
        this.id = id;
        this.codigo = codigo;
        this.descricao = descricao;
        this.materiaId = materiaId;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getMateriaId() { return materiaId; }
    public void setMateriaId(Integer materiaId) { this.materiaId = materiaId; }

    public String getMateriaNome() { return materiaNome; }
    public void setMateriaNome(String materiaNome) { this.materiaNome = materiaNome; }
}
