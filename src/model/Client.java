package model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Client {

    private ObservableList<Email> inbox = FXCollections.observableArrayList();
    private ObservableList<Email> outbox = FXCollections.observableArrayList();
    private ObservableList<Email> draft = FXCollections.observableArrayList();
    private ObservableList<Email> bin = FXCollections.observableArrayList();

    private final String inboxPath = "src/data/emails/inbox/"; // TODO: controllare se il relative path è giusto.
    private final String outboxPath = "src/data/emails/outbox/";
    private final String draftsPath = "src/data/emails/drafts/";
    private final String binPath = "src/data/emails/bin/";

    private Account user;

    private SimpleStringProperty status = new SimpleStringProperty();

    private String serverAddress;
    private int serverPort;

    /**
     * Costructor of client object, It initialize all the client data
     *
     * @param userEmail email of the user, used to initialize a new Account Object
     * @param serverAddress the address of the server
     * @param serverPort the server's port which is listening to client calls
     */
    public Client(String userEmail, String serverAddress, int serverPort){
        this.user = new Account(userEmail);
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
     * Returns the ObservableList of all the e-mail in the recycle bin
     *
     * @return the ObservableList bin
     */
    public ObservableList<Email> getBin() {
        return bin;
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
    public SimpleStringProperty getStatus() {
        return status;
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
     * Setter for the bin parameter
     *
     * @param bin: the new bin parameter
     */
    public void setBin(ObservableList<Email> bin) {
        this.bin = bin;
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
                ", bin=" + bin +
                ", user=" + user +
                ", status=" + status +
                ", serverAddress='" + serverAddress + '\'' +
                ", serverPort=" + serverPort +
                '}';
    }

    // OTHER METHODS ---------------------------------------------------------------------------------------------------

    /**
     * It writes a serialized txt file containing all the data of the email passed through the "mess" parameter, and it updates
     * the GUI, showing the new message in the right box (in/out-box or drafts)
     *
     * @param mess: the Email object to write on file
     * @param location: the flag associated to the email: i (for inbox), o (for outbox) and d (for draft).
     *            the flag of each email defines in which folder the email just created must be saved in.
     */
    public synchronized void write(Email mess, String location){
        try {

            if (location.equals("i")){ // inbox
                getInbox().add(mess); // update GUI

                try (
                        OutputStream file = new FileOutputStream(inboxPath + mess.getIdEmail() + ".ser");
                        OutputStream buffer = new BufferedOutputStream(file);
                        ObjectOutput output = new ObjectOutputStream(buffer);
                ) {
                    output.writeObject(mess);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            } // end if

            else if (location.equals("o")){ // outbox
                getOutbox().add(mess); // update GUI
                try (
                        OutputStream file = new FileOutputStream(outboxPath + mess.getIdEmail() + ".ser");
                        OutputStream buffer = new BufferedOutputStream(file);
                        ObjectOutput output = new ObjectOutputStream(buffer);
                ) {
                    output.writeObject(mess);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } // end else if

            else { // location.equals("d") Default case: save to drafts
                try (
                        OutputStream file = new FileOutputStream(draftsPath + mess.getIdEmail() + ".ser");
                        OutputStream buffer = new BufferedOutputStream(file);
                        ObjectOutput output = new ObjectOutputStream(buffer);
                ) {
                    output.writeObject(mess);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } // end else

        } catch (Exception e) {
            e.printStackTrace();
        } // end main try-catch block

    } // end method

    /**
     * It removes the email from the ObservableList in which is located, it moves the email to the bin and it updates the GUI.
     * @param mess: the Email to be removed
     * @param location: the flag associated to the email: i (for inbox), o (for outbox) and d (for draft).
     *            the flag of each email defines in which folder the email just created must be saved in.
     */
    public synchronized void delete(Email mess, String location){
        if (location.equals("i")){
            getInbox().remove(mess); // update GUI
            getBin().add(mess); // put email just removed in Bin

            // moving the email file from inbox to bin
            File transotiry = new File(inboxPath + "email" + mess.getIdEmail() + ".txt");
            transotiry.renameTo(new File(binPath + mess.getIdEmail() +".txt"));
        }
        else if (location.equals("o")){
            getOutbox().remove(mess); // update GUI
            getBin().add(mess); // put email just removed in Bin

            // moving the email file from inbox to bin
            File transotiry = new File(outboxPath + "email" + mess.getIdEmail() + ".txt");
            transotiry.renameTo(new File(binPath + mess.getIdEmail() +".txt"));
        }
        else if (location.equals("d")){
            getDraft().remove(mess); // update GUI
            getBin().add(mess); // put email just removed in Bin

            // moving the email file from inbox to bin
            File transotiry = new File(draftsPath + "email" + mess.getIdEmail() + ".txt");
            transotiry.renameTo(new File(binPath + mess.getIdEmail() +".txt"));
        }

    }

    /**
     * It reads all the serialized txt files of the emails and it fill the ObservableList related to the Folder
     * in which the files are located.
     *
     * @param location the folder in which the emails are located: i (for inbox), o (for outbox), d (for draft),
     *                 and b (for bin).
     */
    public synchronized void read(String location){
        // needed to fill the observable lists
        File folder;
        File[] numberOfFiles;

        // TODO: Necessario creare oggetto Email quando una Email viene aperta?
        // per serializzre e deserializzare: http://www.javapractices.com/topic/TopicAction.do?Id=57


        switch (location) {
            case "DEBUG":

                File folder1 = new File(inboxPath);
                File[] listOfFiles1 = folder1.listFiles();

                for (int i = 0; i < listOfFiles1.length; i++) {
//                    if (listOfFiles1[i].isFile()) {
//                        System.out.println("File " + listOfFiles1[i].getName());
//                    } else if (listOfFiles1[i].isDirectory()) {
//                        System.out.println("Directory " + listOfFiles1[i].getName());
//                    }

                    //deserialize the email*.ser file
                    try (
                            InputStream file = new FileInputStream(inboxPath + listOfFiles1[i].getName());
                            InputStream buffer = new BufferedInputStream(file);
                            ObjectInput input = new ObjectInputStream(buffer);
                    ) {
                        Email recoveredEmail = (Email) input.readObject(); //deserialize the email
                        System.out.println(recoveredEmail.toString()); //display its data

                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }


//            case "i":
//                folder = new File(inboxPath);
//                numberOfFiles = folder.listFiles();
//
//                for (int i = 0; i < numberOfFiles.length; i++){
//                    System.out.println(i);
//                    if (numberOfFiles[i].isFile()) {
//                        System.out.println("File " + numberOfFiles[i].getName());
//                    }
//                    else if (numberOfFiles[i].isDirectory()){
//                        System.out.println("Directory " + numberOfFiles[i].getName());
//                    }
//                    if (numberOfFiles[i].isFile()) { // one more check for secutiry
//                        Email em;
//                        try {
//                            FileInputStream fileIn = new FileInputStream(inboxPath + '/' + numberOfFiles[i].getName());
//                            ObjectInputStream in = new ObjectInputStream(fileIn);
//
//                            em = (Email) in.readObject();
//
//                            getInbox().add(em);
//                            in.close();
//                            fileIn.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            return;
//                        } catch (ClassNotFoundException c) {
//                            System.out.println("Email class not found");
//                            c.printStackTrace();
//                            return;
//                        }
//                    } // end if
//                }// end for
//
//            case "o":
//                folder = new File(outboxPath);
//                numberOfFiles = folder.listFiles();
//
//                for (int i = 0; numberOfFiles != null && i < numberOfFiles.length; i++){
//                    if (numberOfFiles[i].isFile()) { // one more check for secutiry
//                        Email em;
//                        try {
//                            FileInputStream fileIn = new FileInputStream(outboxPath + numberOfFiles[i].getName());
//                            ObjectInputStream in = new ObjectInputStream(fileIn);
//                            em = (Email) in.readObject();
//                            getOutbox().add(em);
//                            in.close();
//                            fileIn.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            return;
//                        } catch (ClassNotFoundException c) {
//                            System.out.println("Email class not found");
//                            c.printStackTrace();
//                            return;
//                        }
//                    } // end if
//                }// end for
//
//            case "d":
//                folder = new File(draftsPath);
//                numberOfFiles = folder.listFiles();
//
//                for (int i = 0; numberOfFiles != null && i < numberOfFiles.length; i++){
//                    if (numberOfFiles[i].isFile()) { // one more check for secutiry
//                        Email em;
//                        try {
//                            FileInputStream fileIn = new FileInputStream(draftsPath + numberOfFiles[i].getName());
//                            ObjectInputStream in = new ObjectInputStream(fileIn);
//                            em = (Email) in.readObject();
//                            getDraft().add(em);
//                            in.close();
//                            fileIn.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            return;
//                        } catch (ClassNotFoundException c) {
//                            System.out.println("Email class not found");
//                            c.printStackTrace();
//                            return;
//                        }
//                    } // end if
//                }// end for
//
//            default:  // location.equals("b")
//                folder = new File(binPath);
//                numberOfFiles = folder.listFiles();
//
//                for (int i = 0; numberOfFiles != null && i < numberOfFiles.length; i++){
//                    if (numberOfFiles[i].isFile()) { // one more check for secutiry
//                        Email em;
//                        try {
//                            FileInputStream fileIn = new FileInputStream(binPath + numberOfFiles[i].getName());
//                            ObjectInputStream in = new ObjectInputStream(fileIn);
//                            em = (Email) in.readObject();
//                            getBin().add(em);
//                            in.close();
//                            fileIn.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            return;
//                        } catch (ClassNotFoundException c) {
//                            System.out.println("Email class not found");
//                            c.printStackTrace();
//                            return;
//                        }
//                    } // end if
//                }// end for
        } // end switch

    } // end read method


} // end client class
