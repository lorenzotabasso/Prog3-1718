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
import java.util.Date;
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
import org.w3c.dom.*;


public class Email extends Observable{
    private UUID IDEmail;
    private Account sender;
    private Account receiver;
    private String argument;
    private String text;
    private Calendar dateOfSending;

    /**
     * Constructor of Email Object
     * @param sender: the account of the sender
     * @param receiver: the account of the reciver
     * @param argument: the thread of the conversation
     * @param text: the text of the message
     */
    public Email(Account sender, Account receiver, String argument, String text){
        this.IDEmail = UUID.randomUUID();
        this.sender = sender;
        this.receiver = receiver;
        this.argument = argument;
        this.text = text;
        this.dateOfSending = Calendar.getInstance();
    }

    // GETTERS ---------------------------------------------------------------------------------------------------------

    /**
     * getter for IDEmail parameter
     * @return the ID of the Email
     */
    public String getIDEmail() {
        return IDEmail.toString();
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
     * getter for argument parameter
     * @return the argument of the Email
     */
    public String getArgument() {
        return argument;
    }

    /**
     * getter for text parameter
     * @return the text (body) of the Email
     */
    public String getText() {
        return text;
    }

    /**
     * getter for dateOfSending parameter
     * @return the date in which the Email is send
     */
    public Calendar getDateOfSending() {
        return dateOfSending;
    }

    // SETTERS ---------------------------------------------------------------------------------------------------------

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
     * Setter for the argument parameter
     * @param newArgument: the new argument for the email
     */
    public void setArgument(String newArgument) {
        this.argument = newArgument;
    }

    /**
     * Setter for the text parameter
     * @param newText: the new text (body) for the email
     */
    public void setText(String newText) {
        this.text = newText;
    }

    /**
     * Setter for the dateOfSending parameter
     * @param newDate: the new argument for the email
     */
    public void setDate(Calendar newDate) {
        this.dateOfSending = newDate;
    }


    // OTHER -----------------------------------------------------------------------------------------------------------

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
     * Method for parsing an XML file
     * @param pathOfXML: the path of the XML file to be read
     */
    private void readXML(String pathOfXML){
        try {

            File fXmlFile = new File(pathOfXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("email");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                // System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("Email ID : " + eElement.getElementsByTagName("IDEmail").item(0).getTextContent());
                    System.out.println("Sender : " + eElement.getElementsByTagName("sender").item(0).getTextContent());
                    System.out.println("Receiver : " + eElement.getElementsByTagName("receiver").item(0).getTextContent());
                    System.out.println("Argument : " + eElement.getElementsByTagName("argument").item(0).getTextContent());
                    System.out.println("Text : " + eElement.getElementsByTagName("text").item(0).getTextContent());
                    System.out.println("Date of sending : " + eElement.getElementsByTagName("date").item(0).getTextContent());

                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    } // end readXML

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
            IDEmail.appendChild(doc.createTextNode(toConvert.getIDEmail()));
            rootElement.appendChild(IDEmail);

            // Sender elements
            Element sender = doc.createElement("sender");
            sender.appendChild(doc.createTextNode(toConvert.getSender()));
            rootElement.appendChild(sender);

            // Reciver elements
            Element receiver = doc.createElement("receiver");
            receiver.appendChild(doc.createTextNode(toConvert.getReceiver()));
            rootElement.appendChild(receiver);

            // Argument elements
            Element argument = doc.createElement("argument");
            argument.appendChild(doc.createTextNode(toConvert.getArgument()));
            rootElement.appendChild(argument);

            // Text elements
            Element text = doc.createElement("text");
            text.appendChild(doc.createTextNode(toConvert.getText()));
            rootElement.appendChild(text);

            // Date of Sending elements
            Element dateOfSending = doc.createElement("date");
            dateOfSending.appendChild(doc.createTextNode(toConvert.getDateOfSending().toString()));
            rootElement.appendChild(dateOfSending);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("data/emails/email" + toConvert.getIDEmail() + ".xml"));

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
