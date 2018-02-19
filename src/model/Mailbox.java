package model;

import java.util.ArrayList;
import java.util.Observable;

/*
La casella di posta elettronica contiene una lista eventualmente vuota di messaggi e rappresenta il model
dell'applicazione. I messaggi di posta elettronica sono istanze di una classe email.Email che specifica ID, mittente,
destinatario, argomento, testo e data di spedizione del messaggio.
 */

public class Mailbox extends Observable{
    private Account accoutOf;
    private ArrayList<Email> messages;

    public Mailbox(String email) {
        this.accoutOf = new Account(email);
        this.messages = new ArrayList<Email>();
    }
}
