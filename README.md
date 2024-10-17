# Servicio 2: Reconocimientos Extra

Dentro del contexto de [Sistema para la Mejora del Acceso Alimentario en Contextos de Vulnerabilidad Socioeconómica
](https://docs.google.com/document/d/13niiEppxrm8LjyrxmH5Pskrc7VVuPKWSFRi3WvhsXns)

Hay empresas que decidieron brindar beneficios a los colaboradores pero desde sus propias plataformas.
Para eso vamos a implementar un servicio que recomiende colaboradores de acuerdo a los parámetros
solicitados por las distintas empresas.

La empresa solicitará una cantidad mínima de puntos requeridos, una cantidad mínima de donación de
viandas realizada en el último mes y una cantidad máxima de colaboradores a ser incorporados en la
plataforma externa. En caso de que no se llegue a la cantidad de colaboradores solicitados, se deberán
devolver únicamente los que cumplan las condiciones.

Nuestro sistema debe devolver una lista de colaboradores a ser reconocidos por otras empresas, junto con
el puntaje que obtuvo cada uno hasta el momento.

## Definición de la solución

#### Tecnologías utilizadas
- Java 21
- Spring Boot 3.3.3
+ Spring Boot Data JPA
+ Spring Boot Web
- MySQL
- H2 Database
- MapStruct
- OpenApi webmvc UI (Swagger)

## Definición de la API

Nuestra API ofrecerá un endpoint que permitirá a las empresas solicitar colaboradores a reconocer, mediante un GET a la URL `/colaboradores`.
Contara con los siguientes query params:
- `minPoints`: cantidad mínima de puntos requeridos
- `minDonations`: cantidad mínima de donación de viandas realizada en el último mes
- `page`: número de página a ser devuelto
- `limit`: cantidad máxima de colaboradores a ser devueltos a la plataforma externa
- `sort`: campo por el cual se ordenarán los colaboradores (puede ser `points,(asc|desc)` o `donations,(asc|desc)`). Desc por default.

A su vez, contamos con un endpoint que permite la carga de colaboradores desde el sistema principal, mediante un POST a la URL `/colaboradores`.

## Guía de despliegue

### Requisitos
- JDK 21
- Maven
- MySQL o alguna DB relacional equivalente.

### Pasos
1. Clonar el repositorio
2. Asegurarse de tener una instancia de la DB a la cual conectarse
3. Configurar variables de entorno. El uso de variables de entorno permite trabajar con configuraciones de forma dinámica, sin necesidad de modificar archivos, y preservando la seguridad de las credenciales.
   + Las variables de entorno necesarias, junto con un ejemplo para la conexión a una base de datos MySQL local, son las siguientes: 
   + Si se quiere ejecutar directamente desde Intellij, estas se pueden configurar en `Run > Edit Configurations > Environment variables`
     ```
       DATASOURCE_PASSWORD=root;
       DATASOURCE_URL=jdbc:mysql://localhost:3306/servicio-recomendador?createDatabaseIfNotExist=true;
       DATASOURCE_USERNAME=root;
       JPA_HIBERNATE_DDL_AUTO=update;
       ```
   + En caso de que el motor de base de datos no soporte `createDatabaseIfNotExist`, se debe crear la base de datos manualmente antes de ejecutar.

## API
La documentación de consumo de la API, una vez ejecutado el servicio, se encuentra en el path `/swagger-ui.html`. OpenAPI es una especificación para describir APIs RESTful, y Swagger es una herramienta que permite visualizar y probar APIs que cumplen con esta especificación. Usando un archivo .yaml en el que se describe la API, Swagger genera una interfaz gráfica que permite interactuar con la API de forma sencilla. A su vez, distintas bibliotecas permiten generar los DTO de la API a partir de este archivo, y viceversa. Los invitamos a que exploren esta opción para ahorrarse el trabajo de hacer los DTO a mano, esto se puede hacer en Java con el plugin de Maven `openapi-generator-maven-plugin` [https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-maven-plugin](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-maven-plugin). 

El archivo que describe a la api se encuentra en `src/main/resources/servicio-api-docs.yaml`
