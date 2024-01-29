

# gestion-tasa-cambio
API - GESTION DE TASAS DE CAMBIO

### Stack Tecnológico 
* Java 17
* SpringBoot 3.1.2
* Maven 3.9.0


## Configuración del ambiente 

### 1. Documentación de la API (Swagger)

La especificación del servicio a través de la siguiente ruta:[http://localhost:9090/swagger-ui/index.html](http://localhost:9090/swagger-ui/index.html)

###  MEDIANTE DOCKER COMPOSE (integrado con la BD MySql)

Desde la ruta del proyecto, una vez construido el jar, ejecutar:

```shell
docker-compose -f docker-compose.yml up
```

Si todo esta bien debería poder acceder al contenedor desde el
navegador con [http://localhost:9090/swagger-ui/index.html](http://localhost:9090/swagger-ui/index.html)


### ACCESO A LA BD MYSQL
Para el ejercicio se utiliza la BD MySql.
Para configurar la BD MySql, se debe editar el archivo **application.yml** como en el siguiente ejemplo: 
```shell
base-datos:
  mysql:
    user: root
    password: desa
    urljdbc: jdbc:mysql://localhost:3306/dbtasas
```
NOTA: En el contenedor Docker, la variable **localhost**  debe ser cambiada por la IP que asigna el contenedor a la instancia **mysql**

La conexion se realiza desde un cliente SQL
```shell
jdbc:mysql://localhost:5506/mysql
root/desa
```
Se debe correr el script de creacion de BD y Tablas
```shell
/gestion-tasa-cambio/assets/sql/DB_Mysql_TasaCambio.sql
```


### CONTENEDOR USANDO DOCKER MANUALMENTE
--
Para construir el contenedor, se debe construir la imagen en Docker.

```bash
docker build . -t gestion-tasa-cambio
```

A continuación se muestra como construir la imagen utilizando el nombre raíz del proyecto como nombre de la imagen.

```bash
docker build -f Dockerfile -t gestion-tasa-cambio --network host .
```

--
Para levantar el contenedor

```bash
docker run -it --rm --name gestion-tasa-cambio -p 9090:9090 -d gestion-tasa-cambio --network=host
```
Para comprobar que el contenedor levanto correctamente

```bash
docker ps -a
```

Esto listara los contenedores corriendo en el equipo, si todo esta bien debería poder acceder al contenedor desde su 
navegador con [http://localhost:9090/swagger-ui/index.html](http://localhost:9090/swagger-ui/index.html)

-- 
Ver logs del contenedor:
```shell
docker logs gestion-tasa-cambio
```

--
Para acceder a través de TTY al contenedor:
```shell
docker exec -it gestion-tasa-cambio /bin/sh
```
### DOCUMENTACION DEL SERVICIO

[DEFINICIONES_DE_ARQUITECTURA_v1.docx](assets/DEFINICIONES_DE_ARQUITECTURA_v1.docx)
