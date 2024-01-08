FROM maven:3.6.3-adoptopenjdk-11 as builder
RUN mkdir -p /app/source
COPY . /app/source
WORKDIR /app/source
RUN mvn clean install -Dmaven.test.skip=true

FROM openjdk:17-jdk-alpine as runtime
COPY --from=builder /app/source/target/address-book-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8210
ENTRYPOINT ["java","-jar","/app/app.jar"]