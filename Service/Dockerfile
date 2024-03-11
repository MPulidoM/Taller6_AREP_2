FROM openjdk:11

WORKDIR /usrapp/bin

COPY /target/classes /usrapp/bin/classes
COPY /target/dependency /usrapp/bin/dependency

EXPOSE 4568

CMD ["java", "-cp", "./classes:./dependency/*", "co.edu.escuelaing.arep.Service"]