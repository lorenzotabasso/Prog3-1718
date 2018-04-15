package model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Client {

    private ObservableList<Email> inbox = FXCollections.observableArrayList();
    private ObservableList<Email> outbox = FXCollections.observableArrayList();
    private ObservableList<Email> draft = FXCollections.observableArrayList();

    private Account user;

    private SimpleStringProperty status = new SimpleStringProperty();

    private String serverAddress;
    private int serverPort;

    /**
     * Costructor of client object, It initialize all the client data
     *
     * @param userName first name of the user, used to initialize a new Account Object
     * @param userSurname surname of the user, used to initialize a new Account Object
     * @param userEmail email of the user, used to initialize a new Account Object
     * @param serverAddress the address of the server
     * @param serverPort the server's port which is listening to client calls
     */
    public Client(String userName, String userSurname, String userEmail, String serverAddress, int serverPort){
        this.user = new Account(userName, userSurname, userEmail);
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    // GETTERS ---------------------------------------------------------------------------------------------------------

    /**
     * Returns the ObservableList of all the e-mail in the inbox
     *
     * @return the ObservableList inbox
     */
    public ObservableList<Email> getInbox() {
        return inbox;
    }

    /**
     * Returns the ObservableList of all the e-mail not in the inbox
     *
     * @return the ObservableList outbox
     */
    public ObservableList<Email> getOutbox() {
        return outbox;
    }

    /**
     * Returns the ObservableList of all the e-mail saved as draft
     *
     * @return the ObservableList draft
     */
    public ObservableList<Email> getDraft() {
        return draft;
    }

    /**
     * Returns the Account object corresponding to the user of the client
     *
     * @return the Account of the user
     */
    public Account getUser() {
        return user;
    }

    /**
     * Returns the status displayed in the status bar
     *
     * @return the status
     */
    public String getStatus() {
        return status.get();
    }

    /**
     * Returns the status object displayed in the status bar
     *
     * @return the SimpleStringProperty status object
     */
    public SimpleStringProperty statusProperty() {
        return status;
    }

    /**
     * Returns the server address
     *
     * @return the server address
     */
    public String getServerAddress() {
        return serverAddress;
    }

    /**
     * Returns the server port
     *
     * @return the server port
     */
    public int getServerPort() {
        return serverPort;
    }

    // SETTERS ---------------------------------------------------------------------------------------------------------

    /**
     * Setter for the inbox parameter
     *
     * @param inbox: the new inbox object
     */
    public void setInbox(ObservableList<Email> inbox) {
        this.inbox = inbox;
    }

    /**
     * Setter for the outbox parameter
     *
     * @param outbox: the new outbox parameter
     */
    public void setOutbox(ObservableList<Email> outbox) {
        this.outbox = outbox;
    }

    /**
     * Setter for the draft parameter
     *
     * @param draft: the new draft parameter
     */
    public void setDraft(ObservableList<Email> draft) {
        this.draft = draft;
    }

    /**
     * Setter for the user parameter
     *
     * @param user: the new user of the client
     */
    public void setUser(Account user) {
        this.user = user;
    }

    /**
     * Setter for the status parameter
     *
     * @param status: the new status to be displayed
     */
    public void setStatus(String status) {
        this.status.set(status);
    }

    /**
     * It changes the status message in the status bar
     *
     * @param msg: the new message to be displayed
     */
    public void setStatusProperty(String msg) {
        Platform.runLater(() -> status.setValue(msg)); // it avoid thread exception
    }

    /**
     * Setter for the serverAddress parameter
     *
     * @param serverAddress: the new serverAddress
     */
    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    /**
     * Setter for the serverPort parameter
     *
     * @param serverPort: the new serverPort
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    // UTILITY ---------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Client{" +
                "inbox=" + inbox +
                ", outbox=" + outbox +
                ", draft=" + draft +
                ", user=" + user +
                ", status=" + status +
                ", serverAddress='" + serverAddress + '\'' +
                ", serverPort=" + serverPort +
                '}';
    }

    // OTHER METHODS ---------------------------------------------------------------------------------------------------

    // It writes a txt file containing all the data of the email passed through the field "mess" ;

    /**
     * It writes a txt file containing all the data of the email passed through the "mess" parameter, and it updates
     * the GUI, showing the new message in the right box (in/out-box or drafts)
     *
     * @param mess: the Email object to write on file
     * @param flag: the flag associated to the email: i (for inbox), o (for outbox) and d (for draft)
     *            the flag of each email defines in which folder the email just created must be saved in.
     */
    public synchronized void writeFile(Email mess, String flag){

        if(flag.equals()){

        }

        try {
            FileOutputStream fileOut = null;

            if (flag.equals('i')){
                fileOut = new FileOutputStream("/Volumes/HDD/Lorenzo/Unito/3 Anno/Prog3/Progetto/prog3-project-1718/src/data/emails/inbox/email" + mess.getIdEmail() +".txt");
            }
            else if (flag.equals('o')){
                fileOut = new FileOutputStream("/Volumes/HDD/Lorenzo/Unito/3 Anno/Prog3/Progetto/prog3-project-1718/src/data/emails/outbox/email" + mess.getIdEmail() +".txt");
            }
            else if (flag.equals('d')){
                fileOut = new FileOutputStream("/Volumes/HDD/Lorenzo/Unito/3 Anno/Prog3/Progetto/prog3-project-1718/src/data/emails/drafts/email" + mess.getIdEmail() +".txt");
            }

            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(mess);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + fileOut.toString());
        } catch (IOException i) {
            i.printStackTrace();
        }
    }


} // end client class
