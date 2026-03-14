# ---- Stage 1: Build ----
FROM node:22-alpine AS build

WORKDIR /app

# Copy dependency files first (Docker cache optimization)
COPY package*.json ./
RUN npm install 

# Copy source and build
COPY . .

# Pass the backend URL at build time so Vite can embed it
# But ARG VITE_API_BASE_URL shouldn't have default value
# during production environment, because this default value 
# will be baked into the image, and users will always send
# requests to their own port
ARG VITE_API_BASE_URL=http://localhost:8080
ENV VITE_API_BASE_URL=$VITE_API_BASE_URL

RUN npm run build


# ---- Stage 2: Serve ----
FROM nginx:alpine

# Copy built files from stage 1 into Nginx's web root 
COPY --from=build /app/dist /usr/share/nginx/html

# Nginx config needed for Vue Router (client-side routing)
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80