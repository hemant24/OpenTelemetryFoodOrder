#!/bin/bash

mvn clean package -Dmaven.test.skip=true

export OTEL_TRACES_EXPORTER=otlp
export OTEL_METRICS_EXPORTER=otlp
export OTEL_EXPORTER_OTLP_ENDPOINT=http://localhost:5555
export OTEL_RESOURCE_ATTRIBUTES=service.name=restaurant-service,service.version=1.0

#https://opentelemetry.io/docs/instrumentation/java/automatic/agent-config/
export OTEL_INSTRUMENTATION_COMMON_DEFAULT_ENABLED=true




#export OTEL_INSTRUMENTATION_SPRING_DATA_ENABLED=true
#export OTEL_INSTRUMENTATION_SPRING_WEBMVC_ENABLED=true
#export OTEL_INSTRUMENTATION_TOMCAT_ENABLED=true
#export OTEL_INSTRUMENTATION_JAVA_HTTP_CLIENT_ENABLED=true

#export OTEL_INSTRUMENTATION_HIBERNATE_ENABLED=true
#export OTEL_INSTRUMENTATION_HTTP_URL_CONNECTION_ENABLED=true
#export OTEL_INSTRUMENTATION_OK_HTTP_ENABLED=true
#export OTEL_INSTRUMENTATION_SPRING_CORE_ENABLED=true
#export OTEL_INSTRUMENTATION_SPRING_WEB_ENABLED=true


#java -javaagent:../opentelemetry-javaagent.jar -javaagent:../newrelic.jar -jar target/restaurant.jar
#java -javaagent:./newrelic.jar  -jar target/restaurant.jar

java -javaagent:../opentelemetry-javaagent.jar  -jar target/restaurant.jar

