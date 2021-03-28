FROM openjdk:11.0.7-jre-slim
COPY ./target/subscriptionservice-0.0.1-SNAPSHOT.jar /usr/src/subscriptionservice-0.0.1-SNAPSHOT.jar
WORKDIR /usr/src
CMD ["java","-jar","subscriptionservice-0.0.1-SNAPSHOT.jar"]