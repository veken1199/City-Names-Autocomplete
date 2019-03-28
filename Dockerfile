FROM openjdk:8-jdk-alpine
COPY build/libs/city-autocomplete-1.0.0.jar /var/cloud/
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/var/cloud/city-autocomplete-1.0.0.jar"]
