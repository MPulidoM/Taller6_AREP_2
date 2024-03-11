
# Taller 6: TALLER DE DE MODULARIZACIÓN CON VIRTUALIZACIÓN E INTRODUCCIÓN A DOCKER

En este laboratorio, se aprenderá a crear y desplegar una aplicación web en AWS EC2 utilizando Docker y un servicio REST con balanceo de cargas Round Robin. La aplicación web tendrá un cliente web y un servicio REST que recibirá mensajes y los almacenará en una base de datos MongoDB. El servicio REST implementará un algoritmo de balanceo de cargas Round Robin, delegando el procesamiento de los mensajes y el retorno de las respuestas a cada una de las instancias del servicio LogService. Al final del laboratorio, se tendrá una aplicación web funcional desplegada en AWS EC2 utilizando Docker y un servicio REST con balanceo de cargas Round Robin.

## Empezando

El proyecto contiene:

--> Tenemos en primer proyecto  [Round Robin](https://github.com/MPulidoM/Taller6_AREP_2/tree/main/RoundRobin).

- Se tiene la clase [httpRemote](https://github.com/MPulidoM/Taller6_AREP_2/blob/main/RoundRobin/src/main/java/co/edu/escuelaing/arep/HttpRemote.java) La clase implementa un cliente HTTP que realiza solicitudes GET a un servicio REST en una serie de servidores especificados, mantiene un registro del servidor actual y rota al siguiente servidor en la lista utilizando un algoritmo de balanceo de cargas Round Robin, y envía mensajes al servicio REST utilizando el método remoteLogCall().

- Se tiene la clase [RoundRobin](https://github.com/MPulidoM/Taller6_AREP_2/blob/main/RoundRobin/src/main/java/co/edu/escuelaing/arep/RoundRobin.java) La clase inicializa una aplicación web utilizando Spark, define una ruta GET en "/log" que envía mensajes al servicio REST utilizando el método HttpRemote.remoteLogCall(), y sirve archivos estáticos desde la carpeta "public". La aplicación se ejecuta en el puerto especificado en la variable de entorno "PORT" o en el puerto 4567 por defecto.

- Se tiene la carpeta de recursos donde se encuentra el [formulario Html](https://github.com/MPulidoM/Taller6_AREP_2/blob/main/RoundRobin/src/main/resources/public/formulario.html) En este el cliente hara las peticiones y vera el funcionamiento.

--> Tenemos en Segundo proyecto  [Service](https://github.com/MPulidoM/Taller6_AREP_2/tree/main/Service).

- Se tiene la clase [Service](https://github.com/MPulidoM/Taller6_AREP_2/blob/main/Service/src/main/java/co/edu/escuelaing/arep/Service.java) La clase inicializa una aplicación web utilizando Spark, define una ruta GET en "/logService" que acepta un parámetro "value", guarda el mensaje en una base de datos MongoDB, recupera los últimos 10 mensajes y devuelve una respuesta JSON con los mensajes. La aplicación se ejecuta en el puerto especificado en la variable de entorno "PORT" o en el puerto 4568 por defecto. Además, la aplicación sirve archivos estáticos desde la carpeta "public".

--> Archivos :

- [DockerFileRoundRobin](https://github.com/MPulidoM/Taller6_AREP_2/blob/main/RoundRobin/Dockerfile). Este Dockerfile construye una imagen de Docker para una aplicación Java con OpenJDK 11, copia las clases compiladas y dependencias, expone el puerto 4567 y establece el comando para ejecutar la clase co.edu.escuelaing.arep.RoundRobin.
  
- [DockerFileService](https://github.com/MPulidoM/Taller6_AREP_2/blob/main/Service/Dockerfile).Este Dockerfile construye una imagen de Docker para una aplicación Java con OpenJDK 11, copia las clases compiladas y dependencias, expone el puerto 4568 y establece el comando para ejecutar la clase co.edu.escuelaing.arep.Service.

- [DockerCompose](https://github.com/MPulidoM/Taller6_AREP_2/blob/main/docker-compose.yml) Este archivo de docker-compose.yml define un conjunto de servicios que se ejecutan en contenedores de Docker, cada uno con una imagen específica, nombre de contenedor, y puertos específicos. La versión de Docker Compose utilizada es la 3.



## Requisitos previos

[Docker](https://www.docker.com/): Proporciona un entorno de contenedorización que garantiza la consistencia y portabilidad de la aplicación.

[Maven](https://maven.apache.org/) : Con esta herramienta se creo la estructura del proyecto y se manejan las dependencias que se necesitan

[Git](https://git-scm.com/) : Se basa en un sistema de control de versiones distribuido, donde cada desarrollador tiene una copia completa del historial del proyecto.

Para asegurar una correcta instalación de Maven, es crucial confirmar que la versión del JDK de Java sea compatible. Si el JDK no está actualizado, la instalación de las versiones actuales de Maven podría fallar, generando problemas durante el uso de la herramienta.
```
java -version 
```
## Arquitectura

La arquitectura del proyecto se basa en un patrón de diseño Round Robin para el balanceo de cargas entre servidores. El diseño del proyecto se compone de dos aplicaciones web, una que actúa como cliente (RoundRobin) y otra que actúa como servicio REST (Service).

El cliente (RoundRobin) realiza solicitudes GET a un servicio REST en una serie de servidores especificados. Utiliza un algoritmo de balanceo de cargas Round Robin para mantener un registro del servidor actual y rota al siguiente servidor en la lista. El cliente envía mensajes al servicio REST utilizando el método remoteLogCall().

El servicio REST (Service) acepta solicitudes GET en "/logService" y acepta un parámetro "value". Guarda el mensaje en una base de datos MongoDB, recupera los últimos 10 mensajes y devuelve una respuesta JSON con los mensajes.

Ambas aplicaciones web sirven archivos estáticos desde la carpeta "public". La aplicación cliente se ejecuta en el puerto especificado en la variable de entorno "PORT" o en el puerto 4567 por defecto. La aplicación servicio se ejecuta en el puerto especificado en la variable de entorno "PORT" o en el puerto 4568 por defecto.

Los Dockerfiles construyen imágenes de Docker para las aplicaciones Java con OpenJDK 11, copian las clases compiladas y dependencias, exponen los puertos 4567 y 4568 y establecen los comandos para ejecutar las clases co.edu.escuelaing.arep.RoundRobin y co.edu.escuelaing.arep.Service, respectivamente.

El archivo docker-compose.yml define un conjunto de servicios que se ejecutan en contenedores de Docker, cada uno con una imagen específica, nombre de contenedor, y puertos específicos. La versión de Docker Compose utilizada es la 3.

## Diseño

El proyecto está compuesto por dos componentes principales: Round Robin y Servicio.

--> El componente Round Robin es el primer proyecto y contiene los siguientes elementos:

- Una clase llamada httpRemote que implementa un cliente HTTP que realiza solicitudes GET a un servicio REST en una lista de servidores especificados, mantiene un registro del servidor actual y rota al siguiente servidor en la lista utilizando un algoritmo de balanceo de cargas Round Robin, y envía mensajes al servicio REST utilizando el método remoteLogCall().
  
- Una clase llamada RoundRobin que inicializa una aplicación web utilizando Spark, define una ruta GET en "/log" que envía mensajes al servicio REST utilizando el método HttpRemote.remoteLogCall(), y sirve archivos estáticos desde la carpeta "public". La aplicación se ejecuta en el puerto especificado en la variable de entorno "PORT" o en el puerto 4567 por defecto.

- Una carpeta de recursos que contiene un formulario HTML que el cliente utilizará para hacer peticiones y ver el funcionamiento.
  
--> El componente Servicio es el segundo proyecto y contiene los siguientes elementos:

- Una clase llamada Service que inicializa una aplicación web utilizando Spark, define una ruta GET en "/logService" que acepta un parámetro "value", guarda el mensaje en una base de datos MongoDB, recupera los últimos 10 mensajes y devuelve una respuesta JSON con los mensajes. La aplicación se ejecuta en el puerto especificado en la variable de entorno "PORT" o en el puerto 4568 por defecto. Además, la aplicación sirve archivos estáticos desde la carpeta "public".

--> El proyecto también incluye los siguientes archivos:

- DockerFileRoundRobin: Un Dockerfile que construye una imagen de Docker para una aplicación Java con OpenJDK 11, copia las clases compiladas y dependencias, expone el puerto 4567 y establece el comando para ejecutar la clase co.edu.escuelaing.arep.RoundRobin.
- DockerFileService: Un Dockerfile que construye una imagen de Docker para una aplicación Java con OpenJDK 11, copia las clases compiladas y dependencias, expone el puerto 4568 y establece el comando para ejecutar la clase co.edu.escuelaing.arep.Service.
- docker-compose.yml: Un archivo de docker-compose.yml que define un conjunto de servicios que se ejecutan en contenedores de Docker, cada uno con una imagen específica, nombre de contenedor, y puertos específicos. La versión de Docker Compose utilizada es la 3.
  
El diseño del proyecto involucra dos componentes separados, Round Robin y Servicio, que se comunican entre sí a través de API REST. El componente Round Robin distribuye las solicitudes al componente Servicio utilizando un algoritmo de balanceo de cargas Round Robin. El proyecto está diseñado para ser escalable y flexible, con cada componente que se ejecuta en un contenedor separado y se comunica a través de una red. Los Dockerfiles y el archivo docker-compose.yml facilitan el despliegue y la gestión del proyecto.

## Instalando

Para ver el codigo fuente del proyecto e realiza lo siguiente:
```
git clone https://github.com/MPulidoM/Taller6_AREP_2.git
```
Se accede al directorio deñ proyecto:
```
cd Taller6_AREP_2
```
Consiguiente vamos a compilar cada proyecto 

```
cd RoundRobin
mvn clean install
```
```
cd Service
mvn clean install
```
Ya con lo anterior ahora se deben construir las imagenes de docker, cada uno en el directorio correpondiente:

```
docker build --tag service .
docker build --tag round .
```
- En el caso de lo de **Docker Hub**

```
docker pull mariana360/taller6bono:roundrobin
docker pull mariana360/taller6bono:service
```
Despues realizamos el ajuste de nombres para el docker compose:

```
docker tag mariana360/taller6bono:roundrobin roundrobin
docker tag mariana360/taller6bono:service service
```
Ya teniendo lo anterior (Teniendo en cuenta que se está en el directorio donde se encuentra el docker compose) se da lo siguiente:

```
docker-compose up -d
```


## Pruebas

- Local desde el IDE antes de Docker(El IDE que se uso en este caso es Intellij)
Entramos con el link
```
http://localhost:4567/formulario.html
```
![image](https://github.com/MPulidoM/Taller6_AREP_2/assets/118181543/986de287-1ba4-423b-97a0-534f0dc30d0d)
![image](https://github.com/MPulidoM/Taller6_AREP_2/assets/118181543/9dd04812-d68e-4955-9a59-57a3b1f47931)

- Local con Docker-Compose ejecutandose:
![image](https://github.com/MPulidoM/Taller6_AREP_2/assets/118181543/819fdb28-0d51-42bb-90c6-7ba6224177ef)

- AWS

--> Corriendo la maquina:

  ![image](https://github.com/MPulidoM/Taller6_AREP_2/assets/118181543/494041b1-5046-4c94-ad5c-0142f0023e22)
![image](https://github.com/MPulidoM/Taller6_AREP_2/assets/118181543/ed3f3a89-c137-4434-9acb-4393b6484e50)

--> Entrando a la pagina desde AWS

![image](https://github.com/MPulidoM/Taller6_AREP_2/assets/118181543/c579c9dd-cb9f-4d50-b270-e52ebeea245c)

![image](https://github.com/MPulidoM/Taller6_AREP_2/assets/118181543/91050a47-328f-4163-8461-1efd29516463)



## Autores

* **Mariana Pulido Moreno** - *Arep 101* - [MPulidoM](https://github.com/MPulidoM)













