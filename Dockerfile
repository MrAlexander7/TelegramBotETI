FROM maven:latest

WORKDIR /app

COPY . /app/

RUN mvn dependency:resolve

CMD ["mvn", "exec:java"]