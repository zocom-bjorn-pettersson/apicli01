# APICLI01

Det här är en API-klient som skickar API-förfrågningar (requests) till http://127.0.0.1:8080.

Med den här klienten kan du:
* Lista filmer (till /api/v1/movies/list)
* Lägga till filmer (till /api/v1/movies/create)
* Rensa listan av filmer på servern (OBS: Ej implementerat i servern - det får du testa göra själv)

# Lästips

* https://www.baeldung.com/java-http-request
* https://www.baeldung.com/httpurlconnection-post

# Maven på command line

Det här projektet skapades inte i IntelliJ, utan på command line.

```
archetype:generate -DgroupId=se.jensen.caw21.bjorn -DartifactId=apicli01 -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

Du kan sedan kompilera projektet och bygga en jar-fil med Maven:

```
mvn compile package
```

Och sedan köra programmet genom att skriva:

```
java -jar target/apicli01-1.0-SNAPSHOT.jar
```

(eller)

```
java -jar target/apicli01-1.0-SNAPSHOT-jar-with-dependencies.jar
```

För att se vilka versioner av dina dependencies som har uppdateringar:

```
mvn versions:display-dependency-updates
```
