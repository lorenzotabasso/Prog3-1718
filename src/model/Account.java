package model;

import javafx.collections.ObservableArray;

import java.util.LinkedList;
import java.util.Observable;
import java.util.UUID;

// MODEL (una parte)

public class Account extends Observable{
    private UUID IDAccount;
    private String nome;
    private String cognome;
    private String nomeUtente;

    public Account(String nomeUtente){
        this.IDAccount = UUID.randomUUID();
        this.nomeUtente = nomeUtente;
    }

    public String getNome(){
        return nome;
    }

    public String getCognome(){
        return cognome;
    }

    public String getNomeUtente(){
        return nomeUtente;
    }

    public void setNome(String nome){
        this.nome = nome;
        setChanged();
        notifyObservers();
    }

    public void setCognome(String cognome){
        this.cognome = cognome;
        setChanged();
        notifyObservers();
    }

    public void setData(String nome, String cognome){
        setNome(nome);
        setCognome(cognome);
    }

    @Override
    public String toString() {
        return "Nome: " + this.nome + ", Cognome: " + this.cognome;
    }
}
