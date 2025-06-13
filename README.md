# Backend - Gestione Turni Dipendenti

## Descrizione
Backend dell'applicazione per la gestione dei turni. Gestisce autenticazione, gestione utenti, assegnazione dei turni e calcolo ore/stipendio. Espone API REST utilizzate dal frontend.

## Tecnologie utilizzate
- Java
- Spring Boot
- Gradle (con `gradlew`)
- Visual Studio Code

## Installazione e avvio
Per avviare il backend in locale:

```bash
cd backend
./gradlew bootRun
```

## Installazione Database PostgresSQL 17

su PGAdmin4, creare un database HRFlow su porta 5432, cambiare utente e password nel dockerfile in base alle proprie credenziali, fare restore del database il dump_custom_database.sql oppure copia incollare backup_database.sql nel psql tool di PGAdmin4


## Avvio con Docker
necessario docker desktop
```bash
cd backend
docker-compose up --build

```
## Funzionalità principali
- Login e autenticazione
- Gestione utenti (dipendenti e manager)
- Assegnazione/rimozione turni
- Calcolo ore lavorate e stipendio mensile

## Autori
- Pietro Nardini – Backend Developer
- Tommaso Amà – Backend Developer
