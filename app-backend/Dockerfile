FROM adoptopenjdk/openjdk11
WORKDIR /usr/app
MAINTAINER Subhajit
COPY . .
RUN ./mvnw clean install 
RUN mv /usr/app/target/blogging-app*.jar /usr/app/target/blogging-app.jar
ENTRYPOINT ["java","-jar","/usr/app/target/blogging-app.jar"]