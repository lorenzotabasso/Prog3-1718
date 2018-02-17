package model;

import java.util.LinkedList;
import java.util.UUID;

// MODEL (una parte)

public class Account {
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

    public LinkedList<Email> getMessaggi(){
        LinkedList<Email> tuttiMessaggi = new LinkedList<Email>();
        return tuttiMessaggi;
    }
}
