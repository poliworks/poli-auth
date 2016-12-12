FROM java:8

WORKDIR /app
ADD target/poli-auth-0.1.0-SNAPSHOT-standalone.jar /app/poli-auth.jar
ADD resources/ /app/resources
EXPOSE 3000
CMD ["java", "-jar", "/app/poli-auth.jar"]
