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

import java.util.Calendar;
import java.util.Observable;
import java.util.UUID;

// MODEL (una parte)

public class Email extends Observable{
    private UUID IDEmail;
    private Account mittente;
    private Account destinatario;
    private String argomento;
    private String testo;
    private Calendar dataSpedizione;

    public Email(Account mittente, Account destinatario, String argomento, String testo){
        this.IDEmail = UUID.randomUUID();
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.argomento = argomento;
        this.testo = testo;
        this.dataSpedizione = Calendar.getInstance();
    }

    public Email getEmail () {
        return this;
    }

    public Email scriviEmail(Account destinatario, String argomento, String testo) {
        Email messaggio = new Email (this.mittente, destinatario, argomento, testo);
        return messaggio;
    }

    public void rimuoviEmail(Email daRimuovere) {

    }

}
