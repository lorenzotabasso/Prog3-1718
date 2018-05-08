# Progetto Prog3 - 2017/2018

## Testo
Si sviluppi un’applicazione java che implementi un servizio di posta elettronica
organizzato con un mail server che gestisce le caselle di posta elettronica degli utenti
e i mail client necessari per permettere agli utenti di accedere alle proprie caselle di
posta. Si assuma di avere 3 utenti di posta elettronica che comunicano tra loro.

1. Il mail server gestisce una lista di caselle di posta elettronica. Il mail server ha
    un’interfaccia grafica sulla quale viene visualizzato il log delle azioni effettuate
    dai mail clients e degli eventi che occorrono durante l’interazione tra i client e il
    server. Per esempio: apertura/chiusura di una connessione tra mail client e server,
    invio di messaggi da parte di un client, ricezione di messaggi da parte di un client,
    errori nella consegna di messaggi, eliminazione di messaggi, etc. (tutte le tipologie
    di azioni permesse dai client – NON fare log di eventi locali al client come per
    esempio il fatto che ha schiacciato un bottone, aperto una finestra o simili in
    quanto non sono di pertinenza del server). Il mail server deve gestire la persistenza
    delle code dei messaggi, in modo che i messaggi delle mailbox non vengano persi
    quando si spegne il server.
2. Una casella di posta elettronica contiene:
    1. Nome dell’account di mail associato alla casella postale (es.
       giorgio@mia.mail.com).
    2. Lista eventualmente vuota di messaggi. I messaggi di posta elettronica
       sono istanze di una classe Email che specifica ID, mittente, destinatario,
       argomento, testo e data di spedizione del messaggio.
3. Il mail client, associato ad un particolare account di posta elettronica, ha
    un’interfaccia grafica così caratterizzata:
    1. L’interfaccia permette di:
          1. creare e inviare un messaggio a uno o più destinatari
          2. leggere i messaggi della casella di posta
          3. rispondere a un messaggio ricevuto, in Reply (al mittente del
             destinatario) e/o in Reply-all (al mittente e a tutti i destinatari del
             messaggio ricevuto). Nella risposta si copi il testo del messaggio
             ricevuto.
          4. girare (forward) un messaggio a uno o più account di posta
             elettronica (copiando il testo del messaggio originale).
          5. rimuovere un messaggio dalla casella di posta.
    2. L’interfaccia mostra sempre la lista aggiornata dei messaggi in casella e,
          quando arriva un nuovo messaggio, notifica l’utente attraverso una finestra
          di dialogo che mostra mittente e il titolo del messaggio.
    3. **NB**: per semplicità si associno i mail client agli utenti a priori: non si
          richiede che il mail client offra le funzionalità di registrazione di un
          account di posta. Inoltre, un mail client è associato ad una sola casella di
          posta elettronica e la sua interfaccia non richiede autenticazione da parte
          dell’utente.

## Requisiti tecnici:
1. L’applicazione deve essere sviluppata in Java e basata su **architettura MVC**, con
    Controller + viste e Model, seguendo il pattern Observer Observable. Si noti che
    non deve esserci comunicazione diretta tra viste e model: ogni tipo di comunicazione 
    tra questi due livelli deve essere mediato dal controller o supportata dal pattern 
    Observer Observable.
2. L’applicazione deve permettere all’utente di correggere eventuali input errati (per
    es., in caso di inserimento di indirizzi di posta elettronica non esistenti, il server
    deve inviare messaggio di errore al client che ha inviato il messaggio).
3. L’applicazione deve **parallelizzare le attività** che non necessitano di esecuzione
    sequenziale e gestire gli eventuali problemi di accesso a risorse in mutua
    esclusione. In particolare, i client e il server di mail devono essere applicazioni
    distinte e la creazione/gestione dei messaggi deve avvenire in parallelo alla
    ricezione di altri messaggi.
4. L’applicazione deve essere **distribuita** (i mail client e il server devono stare tutti
    su JVM distinte) attraverso l’uso di RMI (o Socket java).
5. Non si utilizzino database per salvare i messaggi di posta elettronica. La
    persistenza delle mail potrà essere gestita scrivendo su file.
6. È necessario utilizzare i Thread Java in almeno una funzionalità dell’applicativo.


## Requisiti dell’interfaccia utente:
L’interfaccia utente deve essere:
1. Comprensibile (**trasparenza**). In particolare, a fronte di errori, deve
       segnalare il problema all’utente.
2. Ragionevolmente efficiente per permettere all’utente di eseguire le
       operazioni con un numero minimo di click e di inserimenti di dati.
3. Deve essere implementata utilizzando il linguaggio Java e in particolare
       SWING o JavaFX (FXML), e Thread java.


### Note:
1. Si raccomanda di prestare molta attenzione alla progettazione dell’applicazione
    per facilitare il parallelismo nell’esecuzione delle istruzioni e la distribuzione su
    JVM diverse.
2. *Si ricorda che il progetto può essere svolto in gruppo (max 3 persone) o
    individualmente. Se lo si svolge in gruppo la discussione deve essere fatta
    dall’intero gruppo in soluzione unica. La discussione potrà essere fatta nelle date
    di appello orale del corso oppure su appuntamento, concordando la data con il
    docente via email. Si può discutere il progetto prima o dopo aver sostenuto la
    prova scritta. Il voto finale deve essere registrato entro fine febbraio 2019, data
    oltre la quale non è possibile mantere i voti parziali. Leggere il regolamento
    d’esame sulla pagina web del corso per dettagli.*

