# TALLER 1: TALLER DISEÑO Y ESTRUCTURACIÓN DE APLICACIONES DISTRIBUIDAS EN INTERNET

En este taller se exploró la arquitectura de las aplicaciones distribuidas, específicamente la de un servidor web y el protocolo HTTP.   El servidor entrega a los clientes web una aplicación que  permite consultar información de películas. Esta información es obtenida de OMDb API. Todos los archivos para la construcción de la aplicación son leídos por el servidor del disco local y retornados en un formato adecuado.


### Prerequisitos

Para elaborar este proyecto se requirio de : 


Maven: Apache Maven es una herramienta que maneja el ciclo de vida del programa.



Git: Es un sistema de control de versiones distribuido (VCS).



Java 19: Java es un lenguaje de programación de propósito general orientado a objetos, portátil y muy versátil.



### Instalación

1. Ubíquese sobre el directorio donde desea realizar la descarga y ejecute el siguiente comando:

```
   git clone https://github.com/juliandtrianar/AREP_2025-2.git

```
2. Navegue al directorio del proyecto:

```
	 cd lab1
```

3. Ejecute el siguiente comando para compilar el código:

```
    mvn compile
```
5.  Ejecute el siguiente comando para empaquetar el proyecto:
   
```
mvn package
```
6. Para iniciar el servidor, ejecute el siguiente comando:

```
 java -cp target/LAB1_AREP-1.0-SNAPSHOT.jar edu.escuelaing.arep.app.HttpServer
```
---
7. Verifique en la linea de comanos que se imprimió el mensaje **Listo para recibir ...**

## Probando la Aplicación.  

Una vez muestra en la línea de comandos el mensaje **Listo para recibir ...**, se puede ingresar a la aplicación en cualquier navegador con la siguiente URL:
```
       http://localhost:35000/index.html
```

Debería ver en pantalla lo siguiente:

![image](https://github.com/user-attachments/assets/ade5225c-01f7-454c-9c10-ad841f82eae5)

---
### Corriendo test

Ejecutamos el comando

	mvn test

---
<img width="1461" height="376" alt="image" src="https://github.com/user-attachments/assets/087a46d2-12eb-44d9-8726-be912d8283c0" />



## Construido con

- Maven - Administrador de dependencias

## Autor
* **[Julián David Triana Roa](https://www.linkedin.com/in/juli%C3%A1n-david-triana-roa-7712a4227/)**  - [juliandtrianar](https://github.com/juliandtrianar)
