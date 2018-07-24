package common;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 * @author Antonio Guarino
 */

public class Email implements Serializable{

    private static final long serialVersionUID = 42L; // needed for serialization, it avoids InvalidClassException

    // for more infos about serialVersionUID, read the following topics:
    // 1) https://stackoverflow.com/questions/7173352/java-io-invalidclassexception
    // 2) https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it

    private UUID idEmail;
    private boolean seen;
    private String sender;
    private ArrayList<String> receiver;
    private String subject;
    private String text;
    private Timestamp date;


    /**
     * Constructor of Email Object
     * @param sender: the account of the sender
     * @param receiver: the account of the reciver
     * @param subject: the thread of the conversation
     * @param text: the text of the message
     */
    public Email(String sender, ArrayList<String> receiver, String subject, String text){
        this.idEmail = UUID.randomUUID();
        this.seen = setSeen(false); // not yet read
        this.sender = sender;
        this.receiver= receiver;
        this.subject = subject;
        this.text = text;
        this.date = setDate();
    }

    // GETTERS ---------------------------------------------------------------------------------------------------------

    /**
     * Getter for idEmail parameter.
     *
     * @return the ID of the Email
     */
    public String getIdEmail() {
        return idEmail.toString();
    }

    /**
     * Getter for state parameter.
     *
     * @return the seen status of the email (true if receiver has seen the email, false otherwise)
     */
    public boolean getSeen() {
        return this.seen;
    }

    /**
     * Getter for sender parameter.
     *
     * @return the sender of the Email
     */
    public String getSender() {
        return sender;
    }

    /**
     * Getter for receiver parameter.
     *
     * @return the receiver of the Email
     */
    public ArrayList<String> getReceiver() { return receiver; }

    /**
     * Getter for subject parameter.
     *
     * @return the subject of the Email
     */
    public String getSubject() { return subject; }

    /**
     * Getter for text parameter.
     *
     * @return the text (body) of the Email
     */
    public String getText() {
        return text;
    }

    /**
     * Getter for date parameter.
     *
     * @return the date in which the Email is send
     */
    public Timestamp getDate() {
        return date;
    }

    // SETTERS ---------------------------------------------------------------------------------------------------------

    /**
     * Setter for the seen parameter.
     *
     * @param value the boolean value for the email (true if receiver has seen the email, false otherwise)
     */
    public boolean setSeen(boolean value) {
        if (this.seen == value) {
            return this.seen;
        }
        else {
            this.seen = value;
            return this.seen;
        }
    }

    /**
     * Setter for the sender parameter.
     *
     * @param newSender the new sender address
     */
    public void setSender(String newSender) {
        this.sender = newSender;
    }

    /**
     * Setter for the receiver parameter.
     *
     * @param newReceiver the new receiver address
     */
    public void setReceiver(String newReceiver) { this.receiver.add(newReceiver); }

    /**
     * Setter for the receiver parameter.
     *
     * @param newReceiver the new receiver address
     */
    public void setReceiver(ArrayList<String> newReceiver) {
        this.receiver = newReceiver;
    }

    /**
     * Setter for the subject parameter.
     *
     * @param newsubject the new subject for the email
     */
    public void setSubject(String newsubject) {
        this.subject = newsubject;
    }

    /**
     * Setter for the text parameter.
     *
     * @param newText the new text (body) for the email
     */
    public void setText(String newText) {
        this.text = newText;
    }

    /**
     * Setter for the date parameter
     */
    public Timestamp setDate() {
        Date date = new Date();
        long time = date.getTime(); //getTime() returns current time in milliseconds
        Timestamp ts = new Timestamp(time); //Passed the milliseconds to constructor of Timestamp class
        return this.date = ts;

    }

    // UTILITY ---------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Email{\n" +
                "idEmail=" + idEmail +
                ",\n sender=" + sender +
                ",\n receiver=" + receiver +
                ",\n subject='" + subject + '\'' +
                ",\n text='" + text + '\'' +
                ",\n date=" + date + '\n' +
                '}';
    }

} // end Email Class
