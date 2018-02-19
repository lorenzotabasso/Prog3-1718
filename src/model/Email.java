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

// needed for Email
import java.util.Calendar;
import java.util.Observable;
import java.util.UUID;

// needed for write XML
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

// needed for building XML
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Email extends Observable{
    private UUID IDEmail;
    private Account sender;
    private Account reciver;
    private String argument;
    private String text;
    private Calendar dateOfSending;

    /**
     * Constructor of Email Object
     * @param sender: the account of the sender
     * @param reciver: the account of the reciver
     * @param argument: the thread of the conversation
     * @param text: the text of the message
     */
    public Email(Account sender, Account reciver, String argument, String text){
        this.IDEmail = UUID.randomUUID();
        this.sender = sender;
        this.reciver = reciver;
        this.argument = argument;
        this.text = text;
        this.dateOfSending = Calendar.getInstance();
    }

    /**
     * getter for an Email  Object
     * @return the Email object in which is invoked
     */
    public Email getEmail () {
        return this;
    }

    /**
     * Writes an Email and prints all its contents into an XML file
     * @param reciver: the account of the reciver
     * @param argument: the thread of the conversation
     * @param text: the text of the message
     */
    public void writeEmail(Account reciver, String argument, String text) {
        Email message = new Email (this.sender, reciver, argument, text);
        setChanged();
        notifyObservers();
        writeXML(message);
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

    /**
     * It converts an Email object into a XML file.
     * @param toConvert: the Email object to convert
     */
    private void writeXML(Email toConvert) {

        // src: https://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder email = docFactory.newDocumentBuilder();

            // root elements, Email
            Document doc = email.newDocument();
            Element rootElement = doc.createElement("email");
            doc.appendChild(rootElement);

            // IDEmail element
            Element IDEmail = doc.createElement("IDEmail");
            rootElement.appendChild(IDEmail);

            // set attribute to IDEmail element
            IDEmail.setAttribute("id", toConvert.IDEmail.toString());


            // Sender elements
            Element sender = doc.createElement("sender");
            sender.appendChild(doc.createTextNode(toConvert.sender.getEmail()));
            rootElement.appendChild(sender);

            // Reciver elements
            Element reciver = doc.createElement("reciver");
            reciver.appendChild(doc.createTextNode(toConvert.reciver.getEmail()));
            rootElement.appendChild(reciver);

            // Argument elements
            Element argument = doc.createElement("argument");
            argument.appendChild(doc.createTextNode(toConvert.argument));
            rootElement.appendChild(argument);

            // Text elements
            Element text = doc.createElement("text");
            text.appendChild(doc.createTextNode(toConvert.text));
            rootElement.appendChild(text);

            // Date of Sending elements
            Element dateOfSending = doc.createElement("date");
            dateOfSending.appendChild(doc.createTextNode(toConvert.dateOfSending.toString()));
            rootElement.appendChild(dateOfSending);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("data/emails/email" + toConvert.IDEmail.toString() + ".xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } // end CATCH block

    } // end writeXML

} // end Email Class
