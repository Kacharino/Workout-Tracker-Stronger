# 🏋️‍♂️ Stronger – Workout Tracker (Backend MVP)

**Stronger** ist ein Spring-Boot-Backend zur Verwaltung und Analyse von Workouts.  
Benutzer können Workouts anlegen, Übungen mit Sätzen (Wiederholungen + Gewicht) tracken und ihren Trainingsfortschritt auswerten.

Das Projekt ist als **MVP (Minimum Viable Product)** konzipiert und bildet die Grundlage für eine spätere Web-App mit UI.

---

## 🎯 MVP-Ziel

Ziel von **Stronger** ist ein sauberes, vorzeigbares Backend, mit dem Nutzer:

- sich registrieren und einloggen können (JWT)
- Workouts erfassen können
- Übungen inkl. Sätzen (Reps + optionales Gewicht) tracken können
- ihre Trainingsdaten strukturiert abrufen können
- einfache Fortschrittsauswertungen erhalten (z. B. Bestleistung pro Übung)

Der Fokus liegt auf einer klaren REST-API, sauberer Architektur und Erweiterbarkeit für zukünftige Frontend-Clients.

---

## ✨ Features (MVP)

### Übungen
- Seeded Exercise-Katalog
- Abruf aller verfügbaren Übungen

### Workouts
- Workouts für den eingeloggten Benutzer anlegen
- Workout-Einträge (Übung innerhalb eines Workouts) hinzufügen
- Sätze pro Übung erfassen:
  - Wiederholungen (**Pflicht**)
  - Gewicht (**optional**)
  - Automatische Reihenfolge der Sätze (`setNumber`)
- Abruf aller Einträge inkl. Sätze pro Workout

### Progress / Analytics
- Bestes Gewicht pro Übung (pro Benutzer)
- Nur gültige Gewichte (`weight != null`) werden berücksichtigt

---

## 🛠️ Tech Stack

![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![JUnit5](https://img.shields.io/badge/JUnit_5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

---

## 🚀 Getting Started

### Voraussetzungen
- Java 21
- PostgreSQL (lokal) **oder** Docker (für PostgreSQL via Docker Compose)
- Optional: Postman oder ähnliches Tool zum Testen der API

### Konfiguration
Das Projekt verwendet Umgebungsvariablen bzw. `application.yml` für:
- PostgreSQL-Verbindung
- JWT-Secret und Security-Parameter

> Hinweis:  
> Flyway-Migrationen werden **automatisch beim Start** der Anwendung ausgeführt.

### PostgreSQL via Docker Compose (empfohlen)

Für ein schnelles Setup kannst du PostgreSQL per Docker Compose starten:

1. Lege im Projekt-Root eine Datei `docker-compose.yml` an (siehe Issue #8).
2. Starte die Datenbank:

```bash
docker compose up -d
```

3. Die Datenbank läuft danach standardmäßig unter:
- Host: `localhost`
- Port: `5432`

> Hinweis: Stelle sicher, dass deine `spring.datasource.*` Einstellungen auf diese DB zeigen.

### Starten der Anwendung
- Über IntelliJ: Spring-Boot-Main-Klasse starten  
- Oder über Maven

Die API ist danach erreichbar unter:
```
http://localhost:8081
```

---

## 🔐 Authentifizierung

Die API ist durch JWT geschützt.

Vorgehen:
1. Registrieren / Einloggen
2. JWT aus der Response kopieren
3. In jedem Request mitsenden:

```
Authorization: Bearer <JWT_TOKEN>
```

---

## 📡 API Quickstart (Beispiele)

### Übungen abrufen
```
GET /exercises
```

---

### Workout anlegen
```
POST /workouts
```

Beispiel-Request:
```json
{
  "workoutName": "Push Day",
  "date": "2026-01-09",
  "duration": "00:52:18"
}
```

---

### Workout-Eintrag + Sätze hinzufügen
```
POST /workouts/{workoutId}/entries
```

```json
{
  "exerciseId": 1,
  "sets": [
    { "reps": 9, "weight": 32.5 },
    { "reps": 8, "weight": 35.0 },
    { "reps": 6, "weight": 37.5 }
  ]
}
```

Hinweise:
- `reps` ist verpflichtend (> 0)
- `weight` ist optional (`null` möglich, z. B. Bodyweight)
- `setNumber` wird automatisch vergeben (1..n)

---

### Workout-Einträge abrufen
```
GET /workouts/{workoutId}/entries
```

---

### Progress: Bestes Gewicht pro Übung
```
GET /progress/best-weight-per-exercise
```

Gibt pro Übung das **höchste jemals getrackte Gewicht** des eingeloggten Benutzers zurück.

---

## 🗄️ Datenbank & Migrationen
- Migrationen befinden sich unter:
  ```
  src/main/resources/db/migration
  ```
- Flyway verwaltet Schema-Änderungen automatisch

---

## 🧭 Roadmap
Geplante Erweiterungen (siehe GitHub Issues):
- Docker Compose Setup (App + PostgreSQL)
- CORS-Konfiguration für zukünftige Web-UI
- Automatisierte Tests für zentrale Endpoints
- Code-Refactorings (Naming, Auth-/Ownership-Checks)
- Erweiterte Progress-Features (Volumen, Verlauf, PRs)

---

## 💡 Was ich gelernt habe

Stronger war mein erstes wirklich ernsthaftes Solo-Backend-Projekt – 
hier habe ich viele Konzepte zum ersten Mal selbst angewendet:

- **Spring Boot & Java** – tiefes Verständnis für den Application Context, 
  Dependency Injection und saubere Projektstruktur
- **MVC-Architektur** – klare Trennung von Controller, Service und Repository, 
  eigene DTOs und Exceptions
- **Spring Security & JWT** – Authentifizierung selbst implementiert und verstanden
- **PostgreSQL & Flyway** – Datenbankdesign und automatische Migrationsverwaltung
- **JUnit 5** – Unit Tests selbst geschrieben, nicht nur kopiert
- **Maven** – Build-Tool Grundlagen, Dependency Management
- **Postman & REST APIs** – API-Endpunkte testen und mit JSON-Objekten arbeiten
- **Docker Compose** – Deployment-Setup für die Datenbank
- **Clean Code** – viel Wert auf Architektur und Lesbarkeit gelegt

Stronger war kein Uni-Projekt sondern ein bewusstes Lernprojekt – 
ich wollte wissen wie ein echtes Backend funktioniert, von Grund auf.

---
