package model;

/*
ESERCIZIO N. 2 dell' 8 novembre 2017

Premessa: Questo esercizio rappresenta una porzione del progetto di laboratorio, anche se non riflette tutte le
specifiche tecniche che verranno richieste. Curate la grafica e l'architettura MVC dell'applicazione, in modo da poter
riutilizzare in seguito porzioni di codice qui sviluppate.


Si sviluppi un’applicazione java con interfaccia grafica, e basata sui pattern MVC + Observer Observable, che simuli
alcune funzionalità di un client di posta elettronica (non si consideri la parte server che gestisce le caselle di
posta elettronica degli utenti).

La casella di posta elettronica contiene una lista eventualmente vuota di messaggi e rappresenta il model
dell'applicazione. I messaggi di posta elettronica sono istanze di una classe email.Email che specifica ID, mittente,
destinatario, argomento, testo e data di spedizione del messaggio.

La vista sia una tipica finestra di client di mail (es. Thunderbird), con funzionalità ridotte a quanto serve per:
 - vedere il nome dell'account di posta elettronica (che qui assumiamo fisso per l'applicazione, che non prevede
    autenticazione da parte dell'utente)
 - vedere la lista dei messaggi memorizzati nella casella di posta. La lista sia
    ordinata per data dai messaggi più recenti ai meno recenti
 - visualizzare un messaggio della casella di posta selezionandolo dalla lista dei messaggi
 - scrivere un messaggio e inviarlo a uno o più destinatari
 - rimuovere un messaggio dalla casella di posta elettronica e vedere la lista dei messaggi aggiornata.

Si inizializzi la casella di posta elettronica con una decina di messaggi da utilizzare per provare le funzionalità
dell'applicativo. La casella non potrà ricevere nuovi messaggi in quanto non è connessa ad alcun sistema di gestione
della posta elettronica.

Per l'implementazione dell'applicazione si può utilizzare, a scelta, SWING oppure JavaFX.
 */

import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Observable;
import java.util.UUID;

public class Email extends Observable implements Serializable{
    private UUID idEmail;
    private int state;
    private Account sender;
    private Account receiver;
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
    public Email(Account sender, Account receiver, String subject, String text){
        this.idEmail = UUID.randomUUID();
        this.state = setState(2); // new
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.text = text;
        this.date = setDate();
    }

    // GETTERS ---------------------------------------------------------------------------------------------------------

    /**
     * getter for idEmail parameter
     * @return the ID of the Email
     */
    public String getIdEmail() {
        return idEmail.toString();
    }

    /**
     * getter for state parameter
     * @return the state of the email (-1 = deleted, 0 = draft, 1 = read, 2 = new)
     */
    public int getState() {
        return this.state;
    }

    /**
     * getter for sender parameter
     * @return the sender of the Email
     */
    public String getSender() {
        return sender.getEmail();
    }

    /**
     * getter for receiver parameter
     * @return the receiver of the Email
     */
    public String getReceiver() {
        return receiver.getEmail();
    }

    /**
     * getter for subject parameter
     * @return the subject of the Email
     */
    public String getSubject() {
        return subject;
    }

    /**
     * getter for text parameter
     * @return the text (body) of the Email
     */
    public String getText() {
        return text;
    }

    /**
     * getter for date parameter
     * @return the date in which the Email is send
     */
    public Timestamp getDate() {
        return date;
    }

    // SETTERS ---------------------------------------------------------------------------------------------------------

    /**
     * Setter for the state parameter
     * @param newState: the new state of the email (-1 = deleted, 0 = draft, 1 = read, 2 = new)
     */
    public int setState(int newState) {
        return this.state = newState;
    }

    /**
     * Setter for the sender parameter
     * @param newSender: the new sender address
     */
    public void setSender(Account newSender) {
        this.sender = newSender;
    }

    /**
     * Setter for the receiver parameter
     * @param newReceiver: the new receiver address
     */
    public void setReceiver(Account newReceiver) {
        this.receiver = newReceiver;
    }

    /**
     * Setter for the subject parameter
     * @param newsubject: the new subject for the email
     */
    public void setSubject(String newsubject) {
        this.subject = newsubject;
    }

    /**
     * Setter for the text parameter
     * @param newText: the new text (body) for the email
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


    // OTHER -----------------------------------------------------------------------------------------------------------

    /**
     * Writes an Email and prints all its contents into an XML file
     * @param reciver: the account of the reciver
     * @param subject: the thread of the conversation
     * @param text: the text of the message
     */
    public void writeEmail(Account reciver, String subject, String text) {
        Email message = new Email (this.sender, reciver, subject, text);
        setChanged();
        notifyObservers();
        //writeXML(message); // TODO: Redesign the conversion between Objext and XML and viceversa
        writeFile(message);
    }

    /**
     * Deletes a specific Email
     * @param toDelete: the ID of the Email to delete
     */
    public void deleteEmail(Email toDelete) {
        setChanged();
        notifyObservers();
    }

    // UTILITY ---------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Email{" +
                "idEmail=" + idEmail +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }

    /**
     * It writes a txt file containing all the data of this email
     */
    public void writeFile(){
        try {
            FileOutputStream fileOut = new FileOutputStream("/Volumes/HDD/Lorenzo/Unito/3 Anno/Prog3/Progetto/prog3-project-1718/src/data/emails/email" + this.getIdEmail() + ".txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in data/emails/...");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Overloading of writeFile(). It writes a txt file containing all the data of the email passed through the field "mess"
     * @param mess the Email object to write on file
     */
    public void writeFile(Email mess){
        try {
            FileOutputStream fileOut = new FileOutputStream("/Volumes/HDD/Lorenzo/Unito/3 Anno/Prog3/Progetto/prog3-project-1718/src/data/emails/email" + mess.getIdEmail() +".txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(mess);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in data/emails/...");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    // TODO: re-design this method
    public void readFile(Email mess) {
        Email e; // Needed for printing values
        try {
            FileInputStream fileIn = new FileInputStream("/Volumes/HDD/Lorenzo/Unito/3 Anno/Prog3/Progetto/prog3-project-1718/src/data/emails/email.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Email) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Email class not found");
            c.printStackTrace();
            return;
        }

        System.out.println("Deserialized Email...");
        System.out.println("ID: " + e.getIdEmail());
        System.out.println("Subject: " + e.getSubject());
        System.out.println("Receiver: " + e.getReceiver());
        System.out.println("Sender: " + e.getSender());
        System.out.println("Text: " + e.getText());
        System.out.println("Date: " + e.getDate());
    }


} // end Email Class
