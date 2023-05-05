FROM amazoncorretto:11
COPY target/*.jar java-trainee.jar
ENTRYPOINT ["java", "-jar", "java-trainee.jar"]