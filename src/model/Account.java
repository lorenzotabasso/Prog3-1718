package model;

// needed for Account
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

public class Account extends Observable{
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
        return "IDAccount: " + this.getIDAccount() + ", Name: " + this.getName() + ", Surname: " + this.getSurname()+ ", Email: " + this.getEmail();
    }

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

            NodeList nList = doc.getElementsByTagName("account");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                // System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("Account ID : " + eElement.getElementsByTagName("IDAccount").item(0).getTextContent());
                    System.out.println("Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println("Surname : " + eElement.getElementsByTagName("surname").item(0).getTextContent());
                    System.out.println("Email : " + eElement.getElementsByTagName("email").item(0).getTextContent());

                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    } // end readXML

    /**
     * It converts an Email object into a XML file.
     * @param toConvert: the Email object to convert
     */
    private void writeXML(Account toConvert) {

        // src: https://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder account = docFactory.newDocumentBuilder();

            // root element, Account
            Document doc = account.newDocument();
            Element rootElement = doc.createElement("account");
            doc.appendChild(rootElement);

            // IDAccount element
            Element IDAccount = doc.createElement("IDAccount");
            IDAccount.appendChild(doc.createTextNode(toConvert.getIDAccount()));
            rootElement.appendChild(IDAccount);

            // Name element
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(toConvert.getName()));
            rootElement.appendChild(name);

            // Surname element
            Element surname = doc.createElement("surname");
            surname.appendChild(doc.createTextNode(toConvert.getSurname()));
            rootElement.appendChild(surname);

            // Email element
            Element email = doc.createElement("email");
            email.appendChild(doc.createTextNode(toConvert.getEmail()));
            rootElement.appendChild(email);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("data/accounts/account" + toConvert.getIDAccount() + ".xml"));

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
} // end Account Class
