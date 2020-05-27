FROM java:8
VOLUME /tmp
ADD app/build/libs/app-1.0-SNAPSHOT.jar app.jar
EXPOSE 2020
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
