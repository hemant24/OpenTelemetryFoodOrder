FROM openjdk:11-jre-slim
ARG JAR_NAME=orders.jar
COPY ./target/${JAR_NAME} app.jar
COPY ./opentelemetry-javaagent.jar opentelemetry-javaagent.jar
EXPOSE 8080
CMD ["java", \
"-javaagent:./opentelemetry-javaagent.jar", \
"-jar", "app.jar"]