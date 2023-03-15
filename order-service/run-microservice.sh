#!/bin/bash

mvn clean package -Dmaven.test.skip=true

export OTEL_TRACES_EXPORTER=otlp
export OTEL_METRICS_EXPORTER=otlp
export OTEL_EXPORTER_OTLP_ENDPOINT=http://localhost:5556
export OTEL_RESOURCE_ATTRIBUTES=service.name=order-service,service.version=1.0
export OTEL_EXPORTER_OTLP_PROTOCOL=http/protobuf # grpc

#java -javaagent:../opentelemetry-javaagent.jar -javaagent:./newrelic.jar  -jar target/orders.jar
#java -javaagent:./newrelic.jar  -jar target/orders.jar
java -javaagent:../opentelemetry-javaagent.jar  -jar target/orders.jar
