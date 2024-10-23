FROM openjdk:17-alpine AS build_image
ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew $APP_HOME/
COPY gradle $APP_HOME/gradle
RUN chmod +x gradlew
RUN ./gradlew build || return 0
COPY . .
RUN ./gradlew build -x test

FROM openjdk:17-jdk-buster
ENV ARTIFACT_NAME=operations-service-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY --from=build_image $APP_HOME/build/libs/$ARTIFACT_NAME .
ENV JAVA_OPTS="-Xms512m -Xmx1g"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar $ARTIFACT_NAME"]
EXPOSE 8080