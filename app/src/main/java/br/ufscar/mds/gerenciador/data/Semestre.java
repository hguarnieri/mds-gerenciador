package br.ufscar.mds.gerenciador.data;

/**
 * Created by gabri on 29/01/2017.
 */

public class Semestre {
    private int id;
    private String nome;
    private String ano;
    private String periodo;

    public Semestre() {
    }

    public Semestre(int id, String nome, String ano, String periodo) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.periodo = periodo;
    }

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

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
