package model;

import javafx.collections.ObservableArray;

import java.util.LinkedList;
import java.util.Observable;
import java.util.UUID;

// MODEL (una parte)

public class Account extends Observable{
    private UUID IDAccount;
    private String name;
    private String surname;
    private String email;

    public Account(String email){
        this.IDAccount = UUID.randomUUID();
        this.email = email;
    }

    public String getNome(){
        return name;
    }

    public String getCognome(){
        return surname;
    }

    public String getEmail(){
        return email;
    }

    public void setNome(String nome){
        this.name = nome;
        setChanged();
        notifyObservers();
    }

    public void setCognome(String cognome){
        this.surname = cognome;
        setChanged();
        notifyObservers();
    }

    public void setData(String nome, String cognome){
        setNome(nome);
        setCognome(cognome);
    }

    @Override
    public String toString() {
        return "Nome: " + this.name + ", Cognome: " + this.surname;
    }
}
