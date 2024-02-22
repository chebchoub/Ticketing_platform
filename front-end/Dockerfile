# Étape de construction de l'application Angular
FROM node:latest as builder

WORKDIR /app

COPY . .

RUN npm install
RUN npm run build --prod

# Étape de construction de l'image Nginx
FROM nginx:alpine

# Copier les fichiers construits de l'étape précédente dans le répertoire de l'hôte Nginx
COPY --from=builder /app/dist/* /usr/share/nginx/html/

# Copier la configuration nginx personnalisée
COPY src/nginx/etc/conf.d/default.conf /etc/nginx/conf.d/default.conf