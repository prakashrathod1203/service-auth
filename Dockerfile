FROM amazoncorretto:17-alpine
# Alternative no-shell low footprint image
# FROM gcr.io/distroless/java:17

######################################## Amazon Corretto only
RUN apk --no-cache add curl
RUN apk --no-cache add jq
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
######################################## Amazon Corretto only

ARG JAR_FILE=target/service-auth-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} service-auth.jar
ENV SPRING_PROFILES_ACTIVE local
EXPOSE 8080

# User needed to run on distroless image
# USER nonroot

ENTRYPOINT ["java","-jar", "/service-auth.jar"]
CMD ["-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}"]