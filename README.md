# 🏋️‍♂️ Workout Tracker: Stronger

Ein leichtgewichtiges Workout-Tracking-Backend auf Basis von **Spring Boot**, entwickelt für Sportler, die ihre Trainingseinheiten effizient verwalten möchten.

## 🎯 MVP-Ziel 

Ziel ist eine vorzeigbare Web-Demo von **Stronger**, bei der Nutzer:

- sich registrieren und einloggen können (JWT)
- Trainingseinheiten erfassen können (Übungen + Sätze mit Wiederholungen/Gewicht)
- ihre Trainingshistorie ansehen können
- eine einfache Fortschrittsübersicht erhalten (z.B. Bestleistung pro Übung)

Der Fokus liegt auf einer sauberen REST-API, klarer Architektur und einem stabilen Funktionsumfang, der sich später für eine mobile App (iOS) wiederverwenden lässt.

### 🔧 Features
> Dieses Projekt befindet sich noch aktiv in Entwicklung. Weitere Features und Verbesserungen folgen kontinuierlich.
- CRUD-Funktionen für Workouts & User
- Sichere Authentifizierung mittels JWT
- Klare API-Struktur mit Swagger-UI
- Saubere Layer-Architektur (Controller, Service, Repository)

Der Fokus liegt auf Einfachheit, Geschwindigkeit und sauberem Code.

## 🗺️ Roadmap – Geplante Features
Diese Features sind für zukünftige Versionen vorgesehen:

- Neue Entity **Exercise** (Übung) mit eigener Beziehung zu Workouts 
- Workout-Vorlagen & Favoriten
- Fortschritts-Tracking (Gewicht, Wiederholungen, Volumen pro Übung)
- Zeitbasierte Statistiken (Trainingshäufigkeit pro Woche/Monat)  
- Erweiterte User-Profile (Avatar, Ziele, Einstellungen)
- Docker-Support für einfaches Deployment
- Refresh Token Mechanismus zur sicheren Verlängerung der Sessions  
- Bereitstellung einer REST-API für zukünftige mobile/Frontend-Apps

### 🛠️ Tech Stack
- Java 21 + Maven
- Spring Boot 3 
- Spring Security (JWT)
- PostgreSQL + Flyway
- Springdoc OpenAPI (Swagger)
- JUnit 5
