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
- Java 21
- Spring Boot 3
- Spring Security (JWT)
- PostgreSQL
- Flyway (Datenbank-Migrationen)
- Springdoc OpenAPI (Swagger)
- JUnit 5

---

## 🚀 Getting Started

### Voraussetzungen
- Java 21
- PostgreSQL (lokal laufend)
- Optional: Postman oder ähnliches Tool zum Testen der API

### Konfiguration
Das Projekt verwendet Umgebungsvariablen bzw. `application.yml` für:
- PostgreSQL-Verbindung
- JWT-Secret und Security-Parameter

> Hinweis:  
> Flyway-Migrationen werden **automatisch beim Start** der Anwendung ausgeführt.

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

## 🖼️ Demo / Screenshots
> Screenshots oder eine Demo-GIF können hier später ergänzt werden.
