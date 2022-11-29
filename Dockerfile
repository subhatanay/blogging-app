FROM adoptopenjdk/openjdk11
MAINTAINER Subhajit
COPY target/blogging-app*.jar blogging-app.jar
ENTRYPOINT ["java","-jar","/blogging-app.jar"]