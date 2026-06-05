package com.universidade.model;

public class Matricula {

    private Integer id;
    private Integer alunoId;
    private Integer turmaId;
    private String alunoNome;
    private String alunoMatricula;
    private String turmaCodigo;
    private String turmaDescricao;
    private String materiaNome;
    private Double nota1;
    private Double nota2;
    private Double nota3;

    public Matricula() {}

    public Double getMedia() {
        if (nota1 == null || nota2 == null || nota3 == null) return null;
        return (nota1 + nota2 + nota3) / 3.0;
    }

    public boolean isAprovado() {
        Double media = getMedia();
        return media != null && media >= 6.0;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getAlunoId() { return alunoId; }
    public void setAlunoId(Integer alunoId) { this.alunoId = alunoId; }

    public Integer getTurmaId() { return turmaId; }
    public void setTurmaId(Integer turmaId) { this.turmaId = turmaId; }

    public String getAlunoNome() { return alunoNome; }
    public void setAlunoNome(String alunoNome) { this.alunoNome = alunoNome; }

    public String getAlunoMatricula() { return alunoMatricula; }
    public void setAlunoMatricula(String alunoMatricula) { this.alunoMatricula = alunoMatricula; }

    public String getTurmaCodigo() { return turmaCodigo; }
    public void setTurmaCodigo(String turmaCodigo) { this.turmaCodigo = turmaCodigo; }

    public String getTurmaDescricao() { return turmaDescricao; }
    public void setTurmaDescricao(String turmaDescricao) { this.turmaDescricao = turmaDescricao; }

    public String getMateriaNome() { return materiaNome; }
    public void setMateriaNome(String materiaNome) { this.materiaNome = materiaNome; }

    public Double getNota1() { return nota1; }
    public void setNota1(Double nota1) { this.nota1 = nota1; }

    public Double getNota2() { return nota2; }
    public void setNota2(Double nota2) { this.nota2 = nota2; }

    public Double getNota3() { return nota3; }
    public void setNota3(Double nota3) { this.nota3 = nota3; }
}