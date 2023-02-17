FROM node:16-alpine
ARG JAR_NAME=orders.jar
COPY . /app
WORKDIR /app
EXPOSE 9083
CMD ["node", "-r", "./tracing.js", "app.js"]