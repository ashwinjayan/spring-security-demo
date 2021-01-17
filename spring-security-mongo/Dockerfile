FROM openjdk:11
ADD target/spring-security-mongo.jar spring-security-mongo.jar
EXPOSE 8086
ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://springboot-mongo-app:27017/userdb","-Djava.security.egd=file:/dev/./urandom", "-jar","./spring-security-mongo.jar"]