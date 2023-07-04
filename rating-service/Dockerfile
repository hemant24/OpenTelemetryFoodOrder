FROM node:16-alpine
COPY . /app
WORKDIR /app
EXPOSE 9083
CMD ["node", "-r", "./tracing.js", "app.js"]