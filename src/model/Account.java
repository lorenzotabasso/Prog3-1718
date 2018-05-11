package model;

import java.io.*;
import java.util.Observable;
import java.util.UUID;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 */

public class Account extends Observable implements Serializable{
    private UUID IDAccount;
    private String name;
    private String surname;
    private String email;

    /**
     * Costructor of Account Object
     * @param email: the email of the account to create
     */
    public Account(String email){
        setIDAccount();
        setEmail(email);
    }

    // GETTERS ---------------------------------------------------------------------------------------------------------

    /**
     * getter for IDAccount parameter
     * @return the ID of the account
     */
    public String getIDAccount(){
        return IDAccount.toString();
    }

    /**
     * getter for name parameter
     * @return the name of the proprietary of the account
     */
    public String getName(){
        return name;
    }

    /**
     * getter for surname parameter
     * @return the surname of the proprietary of the account
     */
    public String getSurname(){
        return surname;
    }

    /**
     * getter for email parameter
     * @return the email of the proprietary of the account
     */
    public String getEmail(){
        return email;
    }

    // SETTERS ---------------------------------------------------------------------------------------------------------

    /**
     * Setter for the IDAccount parameter
     */
    public void setIDAccount(){
        this.IDAccount = UUID.randomUUID();
        setChanged();
        notifyObservers();
    }

    /**
     * Setter for name parameter
     * @param name: the new name of the proprietary of the account
     */
    public void setName(String name){
        this.name = name;
        setChanged();
        notifyObservers();
    }

    /**
     * Setter for surname parameter
     * @param surname: the new surname of the proprietary of the account
     */
    public void setSurname(String surname){
        this.surname = surname;
        setChanged();
        notifyObservers();
    }

    /**
     * Setter for email parameter
     * @param email: the new email of the proprietary of the account
     */
    public void setEmail(String email){
        this.email = email;
        setChanged();
        notifyObservers();
    }

    // UTILITY ---------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Account{" +
                "IDAccount=" + IDAccount +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

} // end Account Class
