FROM amazoncorretto:21.0.4-alpine3.18

WORKDIR /app

COPY target/ecommerce-crupiers-1.0.0-jar-with-dependencies.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
