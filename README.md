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
- Kubernetes (k3s on EC2, minikube for local)
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
├── backend/               # Spring Boot application
├── frontend/              # Vue 3 application
├── database/              # SQL schema and seed data
├── k8s/                   # Kubernetes manifests
├── docker-compose.yml
├── docker-compose.prod.yml
└── .env                   # Secrets (not committed to git)
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

## Running the App

### Option 1 — Docker Compose with source code
```bash
docker compose up -d
```
Needs: Docker, source code, SQL files.

### Option 2 — Docker Compose with Docker Hub images (no source code needed)
```bash
docker compose -f docker-compose.prod.yml up -d
```
Needs: Docker, `docker-compose.prod.yml`, SQL files.

### Option 3 — Local Kubernetes with minikube (build locally)
```bash
minikube start
eval $(minikube docker-env)
docker build -t readdit-backend ./backend
docker build --build-arg VITE_API_BASE_URL="" -t readdit-frontend ./frontend
eval $(minikube docker-env -u)
bash k8s/create-db-configmap.sh
kubectl apply -f k8s/
minikube service frontend -n readdit --url
```
Needs: Docker, minikube, source code, SQL files.
> Set `imagePullPolicy: Never` in both deployment files.

### Option 4 — Local Kubernetes with minikube (pull from Docker Hub)
```bash
minikube start
bash k8s/create-db-configmap.sh
kubectl apply -f k8s/
minikube service frontend -n readdit --url
```
Needs: Docker Hub images public + multi-arch, minikube, `k8s/` folder, SQL files.
> Set `imagePullPolicy: IfNotPresent` or `Always` in both deployment files.

### Option 5 — EC2 with k3s
```bash
# Install k3s
curl -sfL https://get.k3s.io | sh -
sudo chmod 644 /etc/rancher/k3s/k3s.yaml

# Add swap
sudo fallocate -l 2G /swapfile && sudo chmod 600 /swapfile && sudo mkswap /swapfile && sudo swapon /swapfile

# Deploy
bash k8s/create-db-configmap.sh
sudo kubectl apply -f k8s/

# Load data
sudo kubectl exec -i deploy/mysql -n readdit -- mysql -uroot -p<password> readdit < database/schema.sql
sudo kubectl exec -i deploy/mysql -n readdit -- mysql -uroot -p<password> readdit < database/data.sql
sudo kubectl exec -i deploy/mysql -n readdit -- mysql -uroot -p<password> readdit < database/images.sql
```
Needs: k3s, `k8s/` folder, SQL files, port 30080 open in EC2 Security Group.
> Set `imagePullPolicy: IfNotPresent` or `Always` in both deployment files.
> Access the app at `http://<EC2-PUBLIC-IP>:30080`

## Default Roles

| Role | Permissions |
|---|---|
| `USER` | Browse, write reviews, submit books |
| `MODERATOR` | Approve/reject book submissions |
| `ADMIN` | Full access including user management |

To promote a user to admin via Docker:
```bash
docker exec -it readdit-mysql-1 mysql -u root -p<password> readdit -e "UPDATE user SET role = 'ADMIN' WHERE username = 'yourusername';"
```

To promote a user to admin via Kubernetes:
```bash
kubectl exec -it deploy/mysql -n readdit -- mysql -uroot -p<password> readdit -e "UPDATE user SET role = 'ADMIN' WHERE username = 'yourusername';"
```
