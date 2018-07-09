package client.model;

import java.util.Observable;
import java.util.UUID;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 * @author Antonio Guarino
 */

public class Account extends Observable {
    private UUID IDAccount;
    private String name;
    private String surname;
    private String userEmail;

    // COSTRUCTORS -----------------------------------------------------------------------------------------------------

    public Account(String email){
        this.IDAccount = UUID.randomUUID();
        this.name = "";
        this.surname = "";
        this.userEmail = email;
    }

    public Account(String name, String surname, String email){
        this.IDAccount = UUID.randomUUID();
        this.name = name;
        this.surname = surname;
        this.userEmail = email;
    }

    // GETTERS ---------------------------------------------------------------------------------------------------------

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public String getUserEmail(){
        return userEmail;
    }

    // SETTERS ---------------------------------------------------------------------------------------------------------

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

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        setChanged();
        notifyObservers();
    }

    // UTILITY ---------------------------------------------------------------------------------------------------------

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
