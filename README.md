# Istruzioni per l'utilizzo del software realizzato

1. **Effettuare il fork del repository:**
   - Accedi al repository su GitHub e fai clic sul pulsante "Fork" in alto a destra per creare una copia del repository nel tuo account GitHub.

2. **Importare il progetto in Eclipse:**
   - Copia la URI del repository appena forkato.
   - In Eclipse, seleziona "File" -> "Import" -> "Git" -> "Projects from Git".
   - Incolla la URI e segui le istruzioni per importare il progetto.

3. **Importare ed eseguire il file macchine.sql:**
   - Trova il file `macchine.sql` nella cartella database del progetto.
   - Esegui il file nel tuo DBMS per creare il database necessario.

4. **Configurare la password del DBMS:**
   - All'interno della classe `DBConnect`, trova il metodo `setPassword`.
   - Inserisci la password del tuo DBMS per stabilire la connessione al database.

5. **Eseguire la classe Main:**
   - Trova e esegui la classe `Main` del progetto per avviare l'applicazione.

**Video dimostrativo:**
- [YouTube](https://youtu.be/hZkOR1db05c)
