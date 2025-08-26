

# MICROFRAMEWORKS WEB

En este taller se construyó un servidor web para soportar una funcionalidad similar a la de SparkJava, un microframework WEB que permite construir aplicaciones web de manera simple usando funciones lambda.

## Diseño de la aplicación

La aplicación está diseñada para cumplir con los requisitos especificados en el enunciado del taller y proporcionar una experiencia de usuario fluida y satisfactoria. A continuación, se describen los principales componentes y características de la aplicación:

- La clase `MySpark` permite el registro de servicios get y post usando funciones lambda, es responsable de manejar las solicitudes entrantes de los usuarios y coordinar las interacciones entre el cliente. Además, incluye métodos para configurar el directorio de los archivos estáticos y el tipo de respuesta del servicio, lo que permite servir contenido estático como archivos `HTML`, `CSS`, `JavaScript` e imágenes en los formatos `PNG` y `JPG`.
- La interfaz `Function` define un único método `handle`, que toma un objeto de tipo `Request` y `ResponseBuilder` para devolver una cadena de caracteres. Esta interfaz se utiliza en la clase `MySpark` para definir las funciones lambda que manejan las solicitudes entrantes de los usuarios. El método handle es responsable de procesar la solicitud y generar una respuesta adecuada.
- La clase `Request` representa una solicitud HTTP entrante y proporciona métodos para acceder a sus atributos, como la URI y el cuerpo de la solicitud. 
- La clase `APIController` realiza la conexión a OMDb API en el método `connectToMoviesAPI`, al cual se le pasa como argumento el título de la película. Si la película es encontrada, se retorna un String con los datos, de lo contrario, se establecen mecanismos para validar si la película no fue encontrada y mostrar al usuario el estado de la consulta. Utiliza una estructura de datos concurrente `ConcurrentHashMap` para almacenar en caché las consultas realizadas a la API. Esto mejora significativamente los tiempos de respuesta al evitar consultas repetidas para las mismas películas. 
- La clase `ResponseBuilder` ofrece métodos para construir las respuestas HTML que son enviadas a los usuarios. Proporciona métodos para generar respuestas HTTP con diferentes códigos de estado y tipos de contenido.
- Se agrega el directorio `resources` donde se almacenan los archivos que serán leídos por el servidor.
- Las clases `Product` y `ProductService`son agregadas para mostrar el comportamiento del servicio post.
- La clase `MyServices` contiene ejemplos que muestran cómo se desarrollarían las aplicaciones en su servidor.

## Guía de Inicio

Las siguientes instrucciones le permitirán descargar una copia y ejecutar la aplicación en su máquina local.

### Prerrequisitos

- Java versión 8 OpenJDK
- Maven
- Git

## Instalación 

1. Ubíquese sobre el directorio donde desea realizar la descarga y ejecute el siguiente comando:
   
     ```
       git clone https://github.com/juliandtrianar/AREP_2025-1.git

     ```

3. Navegue al directorio del proyecto:
   
      ```
    cd lab2

   ```

5. Ejecute el siguiente comando para compilar el código:

      ```
   mvn compile

    ```

7.  Ejecute el siguiente comando para empaquetar el proyecto:
   
      ```
    mvn package
      ``` 

9. Para iniciar el servidor, ejecute el siguiente comando:

    ```
    java -cp target/LAB2_AREP-1.0-SNAPSHOT.jar edu.escuelaing.arep.app.MyServices
    ```

10. Verifique en la linea de comandos que se imprimió el mensaje **Listo para recibir ...**
   
![alt text](image.png)





## Construyendo aplicaciones

La clase `MySpark` permite el registro de servicios get y post usando funciones lambda. Cuando se recibe una solicitud GET o POST en la ruta especificada , el servidor ejecutará la función lambda proporcionada como segundo argumento. La función lambda toma un objeto `Request` como parámetro, que representa la solicitud entrante y un objeto `ResponseBuilder` que le permite modificar el tipo de la respuesta, por ejemplo, a "application/json".

Para registrar un servicio GET o POST, utilizamos los métodos get() y post() proporcionados por la clase `MySpark`. Estos métodos toman dos argumentos: la ruta del servicio y una función lambda que define el comportamiento del servicio. Dentro de la función lambda, puedes procesar la solicitud, realizar operaciones necesarias (como procesar datos, consultar APIs externas etc.) y generar la respuesta correspondiente.


Ejemplo de una función lambda en un servicio GET:

```
// Registro de un servicio GET en la ruta '/hi'
get("/hi", (req, res) -> {
          // Obtener la ruta de la URI de la solicitud
          String path = req.getUri().getPath();
          // Generar una respuesta con la ruta
          return "El query es:" + path;
      });
```

Ejemplo de una función lambda en un servicio POST:
```
post("/products", (req, res) -> {
    // Configurar el tipo de respuesta como JSON
    res.setResponseType("application/json");
    // Crear un nuevo producto a partir de los datos en el cuerpo de la solicitud
    Product product = new Product(req.getBody());
    // Agregar el producto a la lista de productos
    productService.addProduct(product);
    // Devolver el producto como una cadena JSON
    return product.toString();
});

```

Por defecto, la ruta de acceso a los recursos estáticos es `/public`, si se requiere, se puede cambiar con el método `setLocation` de la clase `MySpark`.
Una vez que se hayan definido los servicios, puede ejecutar el servidor (método `runServer`) para comenzar a manejar solicitudes HTTP:

```
public static void main(String[] args) throws Exception {
    // servicios post y get
    MySpark.getInstance().runServer(args);
}

```

Por defecto, los servicios estarán disponibles en la ruta `http://localhost:35000/`

## Probando la Aplicación.  

### Archivos Estáticos

Puede asignar una carpeta  que sirve archivos estáticos con el método `MySpark.setLocation`, si por ejemplo, el directorio es configurado en `/public`, Un archivo /public/css/style.css está disponible como `http://{host}:{port}/css/style.css`.

![image](https://github.com/user-attachments/assets/4ee2f8cd-af81-4d05-aa7e-6322497d70f3)

Entrega archivos estáticos como páginas HTML, CSS, JS e imágenes:
![image](https://github.com/user-attachments/assets/9dd5d5f6-5ffd-42f1-8132-a86e9c11b2eb)


Si no se encuentra el archivo en el directorio especificado, se mostrará un mensaje de error:


![image](https://github.com/user-attachments/assets/1f444280-d349-49b2-a298-265dd5614326)


### GET

La clase `mySpark` ofrece el servicio get, se desarrollaron los siguientes ejemplos:

`/hi`retorna un mensaje que incluye la ruta del URI recibido en la solicitud:

![image](https://github.com/user-attachments/assets/2d9aa23d-6883-44a6-9df8-5b39303e1811)


`/users` interpreta los parámetros de la consulta del URI para mostrar un mensaje personalizado.

![image](https://github.com/user-attachments/assets/02c0ff21-4b53-4010-b7f9-faed31ef1498)


`/movies` realiza una solicitud a una API de películas utilizando el título proporcionado en los parámetros de la consulta del URI y devuelve la respuesta en formato JSON.

![image](https://github.com/user-attachments/assets/6dc55409-980e-43d8-88e1-c74b164a457a)


Este mismo servicio puede ser usado por clientes web para dar un mejor formato a la salida de la consulta:
![image](https://github.com/user-attachments/assets/387e8746-0950-4ab3-a2ba-94549289775f)


### POST

La clase `mySpark` ofrece el servicio post. Se desarrolló el siguiente ejemplo:

Se implementó un servicio sencillo para enviar al servidor solicitudes POST para la creación de productos:

![image](https://github.com/user-attachments/assets/84a9a4db-b78b-4cdd-a100-54403e53e54e)


El servidor retorna el JSON del producto creado:
![image](https://github.com/user-attachments/assets/7a0cd935-2752-475a-82d2-9b474ff683cc)



Podemos acceder a todos los productos al acceder al servicio get con ruta `/products`

![image](https://github.com/user-attachments/assets/e30c8d25-5454-4cff-b666-d58e97150a7a)


## Ejecutando las Pruebas.  

A continuación se muestra cómo ejecutar las pruebas desde la línea de comandos.

1. Navegue al directorio del proyecto con la línea de comandos.
2. Ejecute el siguiente comando:
   
```
    mvn test
 ```
3. Debe mostrarse en pantalla que las pruebas fueron exitosas.

![alt text](image-1.png)





## Construido Con:

- Maven - Administrador de dependencias

## Versión

1.0.0

## Autor

- Julián David Triana Roa


