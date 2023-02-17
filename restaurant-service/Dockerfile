FROM openjdk:11-jre-slim
ARG JAR_NAME=restaurant.jar
COPY ./target/${JAR_NAME} app.jar
COPY ./opentelemetry-javaagent.jar opentelemetry-javaagent.jar
EXPOSE 9081
CMD ["java", \
"-javaagent:./opentelemetry-javaagent.jar", \
"-jar", "app.jar"]