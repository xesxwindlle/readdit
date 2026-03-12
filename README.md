# Readdit

A full-stack book review web app where users can browse books, write reviews, and submit new books for approval.

## Tech Stack

**Frontend**
- Vue 3, Vue Router, Pinia
- PrimeVue (Aura theme), Tailwind CSS v4
- vee-validate + yup (form validation)
- Vite

**Backend**
- Java 21, Spring Boot
- Spring Security (JWT authentication)
- Spring JDBC, Spring Data REST
- Gradle

**Database**
- MySQL 8

**Infrastructure**
- Docker + Docker Compose
- Nginx (reverse proxy + static file server)
- AWS EC2

## Features

- Browse books, authors, and genres
- User registration and login
- Book submissions with moderator approval workflow
- Admin panel: manage books, authors, genres, publishers, and users
- Role-based access control: `USER`, `MODERATOR`, `ADMIN`
- AI-powered book chat (via OpenRouter API)
- Book cover image upload

## Project Structure

```
readdit/
├── backend/          # Spring Boot application
├── frontend/         # Vue 3 application
├── database/         # SQL schema and seed data
├── docker-compose.yml
└── .env              # Secrets (not committed to git)
```

## Getting Started

### Prerequisites
- Docker and Docker Compose
- Java 21 (for local backend development)
- Node.js (for local frontend development)

### Environment Variables

Copy `.env.example` to `.env` and fill in the values:

```bash
cp .env.example .env
```

```
MYSQL_ROOT_PASSWORD=
SPRING_DATASOURCE_PASSWORD=
APP_JWT_SECRET=
APP_JWT_EXPIRATION=86400000
OPENROUTER_API_KEY=
```

### Run with Docker

```bash
# Build the backend JAR first
cd backend && ./gradlew build && cd ..

# Start all containers
docker compose up --build
```

The app will be available at `http://localhost`.

### Run Locally (without Docker)

**MySQL** — start your local MySQL server and make sure the `readdit` database exists.

**Backend:**
```bash
cd backend
./gradlew bootRun
```

**Frontend:**
```bash
cd frontend
npm install
npm run dev
```

Frontend runs at `http://localhost:5173`.

## API Overview

| Path | Description |
|---|---|
| `/auth/**` | Login and registration |
| `/api/data/**` | Spring Data REST (books, authors, genres, publishers) |
| `/api/chat` | AI chat endpoint |

## Deployment (AWS EC2)

1. Launch an Ubuntu EC2 instance with ports 22, 80, and 443 open
2. Install Docker on the instance
3. Clone this repository
4. Create a `.env` file with your secrets
5. Build and start:
   ```bash
   cd backend && ./gradlew build && cd ..
   docker compose up --build -d
   ```
6. Access the app at `http://<EC2-PUBLIC-IP>`

## Default Roles

| Role | Permissions |
|---|---|
| `USER` | Browse, write reviews, submit books |
| `MODERATOR` | Approve/reject book submissions |
| `ADMIN` | Full access including user management |

To promote a user to admin via MySQL:
```bash
docker exec -it readdit-mysql-1 mysql -u root -p<password> readdit -e "UPDATE users SET role = 'ADMIN' WHERE username = 'yourusername'"
```
