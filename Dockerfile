FROM mysql:8

ARG JAR_FILE=target/central-auth-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} central-auth.jar
ENV SPRING_PROFILES_ACTIVE dev
EXPOSE 8080

ENTRYPOINT ["java","-jar", "/central-auth.jar"]
CMD ["-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}"]