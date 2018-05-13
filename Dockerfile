FROM openjdk:8
COPY build/libs/common-interests-network-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "common-interests-network-0.0.1-SNAPSHOT.jar"]
