#ulisation de l'image OpenJDK 18
FROM openjdk:18
# Copie du fichier JAR de l'application dans le conteneur
ADD target/backend.jar backend.jar
# Commande pour exécuter l'application Spring Boot
ENTRYPOINT ["java","-jar","backend.jar"]