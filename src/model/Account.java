package model;

// needed for Account
import java.io.*;
import java.util.Observable;
import java.util.UUID;

// needed for write XML
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

// needed for building XML
import org.w3c.dom.*;

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

    public void writeFile(Account acc) {
        try {
            FileOutputStream fileOut = new FileOutputStream("/Volumes/HDD/Lorenzo/Unito/3 Anno/Prog3/Progetto/prog3-project-1718/src/data/accounts/account" + /* acc.getIDAccount() + */ ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(acc);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in data/accounts/...");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

} // end Account Class
