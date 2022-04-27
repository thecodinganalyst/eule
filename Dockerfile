FROM gradle:7.4.2-jdk11 as builder
WORKDIR /builder
ADD . /builder
RUN gradle build --stacktrace

FROM adoptopenjdk/openjdk11:alpine-jre as runner
WORKDIR /app
COPY --from=builder /builder/build/libs/eule-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "eule-0.0.1-SNAPSHOT.jar"]