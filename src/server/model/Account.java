package server.model;

import java.util.Observable;
import java.util.UUID;

public class Account extends Observable {
    private UUID IDAccount;
    private String name;
    private String surname;
    private String userEmail;

    public Account(String email){
        this.IDAccount = UUID.randomUUID();
        this.userEmail = email;
    }

    public Account(String name, String surname, String email){
        this.IDAccount = UUID.randomUUID();
        this.name = name;
        this.surname = surname;
        this.userEmail = email;
    }

    public String getNome(){
        return name;
    }

    public String getCognome(){
        return surname;
    }

    public String getUserEmail(){
        return userEmail;
    }

    public void setName(String name){
        this.name = name;
        setChanged();
        notifyObservers();
    }

    public void setSurname(String surname){
        this.surname = surname;
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Account{" +
                "IDAccount=" + IDAccount +
                ", nome='" + name + '\'' +
                ", cognome='" + surname + '\'' +
                ", emailUtente='" + userEmail + '\'' +
                '}';
    }
}
