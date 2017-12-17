package br.edu.ifspsaocarlos.agenda.model;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class Contato extends RealmObject{

    @PrimaryKey
    private long id;
    @Index
    private String nome;
    private String fone;
    private String fone2;
    private String email;
    private String aniver;
    private Boolean isfavorito;


    public Contato()
    {
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getFone() {return fone;}
    public void setFone(String fone) {this.fone = fone;}
    public void setFone2(String fone2){this.fone2=fone2;}
    public String getFone2() {return fone2;}
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAniver(String aniver){this.aniver=aniver;};
    public String getAniver() {return aniver;}
    public Boolean getFavorito() {
        return isfavorito;
    }
    public void setFavorito(boolean isfavorito) {
        this.isfavorito = isfavorito;
    }

}

