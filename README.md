# Proyecto Java Maven - [Nombre del Proyecto]

Este proyecto es una plantilla base para comenzar a trabajar con aplicaciones Java utilizando Maven. Asegúrate de seguir las instrucciones para configurar tu entorno correctamente y entender cómo contribuir al proyecto.

## Tabla de Contenidos

- [Introducción](#introducción)
- [Requisitos](#requisitos)
- [Configuración del Entorno](#configuración-del-entorno)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Compilación y Ejecución](#compilación-y-ejecución)
- [Contribución](#contribución)
- [Licencia](#licencia)

---

## Introducción

Este es un proyecto base para aprender sobre el uso de **Java** y **Maven**. El objetivo familiarizarse con las buenas prácticas de desarrollo y gestionar dependencias utilizando Maven.

## Requisitos

Antes de comenzar, asegúrate de tener instalado lo siguiente en tu sistema:

- **Java 8+** (JDK) - Puedes descargarlo desde [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) o [OpenJDK](https://openjdk.java.net/)
- **Maven** - Puedes instalar Maven siguiendo las instrucciones en su sitio oficial: [https://maven.apache.org/install.html](https://maven.apache.org/install.html)
- **IDE recomendada:** Visual Studio Code o cualquier editor de tu preferencia con soporte para Java.

## Configuración del Entorno

### 1. Instalar Java
Asegúrate de que tienes **Java 8** o una versión superior instalada. Puedes verificarlo con el siguiente comando:

```bash
java -version
```

### 2. Instalar Maven
Una vez que tengas Java instalado, puedes proceder con la instalación de Maven. Para verificar que Maven esté instalado correctamente, ejecuta el siguiente comando:

mvn -version

### 3. Configurar el IDE
Para trabajar con este proyecto, puedes usar cualquier IDE que soporte Java, como Visual Studio Code, IntelliJ IDEA, Eclipse, etc. Si estás usando Visual Studio Code, asegúrate de instalar las siguientes extensiones:

Java Extension Pack (de Microsoft)
Maven for Java

## Estructura del proyecto

```plaintext
[NombreDelProyecto]/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── ejemplo/
│                   └── App.java
├── pom.xml
├── target/
└── README.md
```

## Compilación y ejecución

Para compilar y ejecutar el proyecto, sigue estos pasos:

Abre una terminal en la raíz del proyecto.

Ejecuta el siguiente comando para compilar el proyecto:
```bash
mvn clean install
```
Para ejecutar la aplicación, usa el siguiente comando:
```bash
mvn exec:java
```

## Contribución

Si deseas contribuir a este proyecto, por favor sigue estos pasos:
- Haz un fork del repositorio.
- Crea una nueva rama (git checkout -b feature-nueva-funcionalidad).
- Realiza tus cambios y haz commit (git commit -am 'Agregué nueva funcionalidad').
- Empuja los cambios a tu fork (git push origin feature-nueva-funcionalidad).
- Crea un pull request.

## Licencia

Este proyecto está bajo la Licencia MIT - consulta el archivo LICENSE para más detalles.
---

## Tipo de cambio (EUR / USD / GBP)

La aplicación muestra los pedidos en euros, dólares o libras, según lo que se elija en el desplegable junto al botón **Search**. Al cambiar de moneda, el pedido mostrado se actualiza automáticamente.

### Cómo se obtiene el tipo de cambio

- Clase: `com.uem.service.ExchangeRateService`.
- **Servicio externo:** API gratuita y sin clave de [Frankfurter](https://frankfurter.dev): `https://api.frankfurter.dev/v1/latest?base=EUR&symbols=USD,GBP`.
- **Cuándo:** cada vez que se ejecuta la aplicación (`Main` llama a `refresh()` antes de abrir la ventana).
- **Cómo:** `HttpClient` de Java 17 (sin dependencias extra) y Gson para leer la respuesta JSON. Timeout de 5 s.
- **Tipo de cambio no constante:** no se guarda en el código; solo hay unos valores por defecto como último recurso.

### Qué pasa si falla la API o no hay internet

1. Si la consulta funciona, los valores se guardan en `~/.orders-app/rates.properties` (carpeta del usuario).
2. Si falla, se usa **el último valor conocido** guardado en ese fichero.
3. Si nunca se ha podido consultar, se usan los valores por defecto del código (USD 1.162976, GBP 0.87).

Debajo de cada pedido, en USD o GBP, se indica el tipo usado y su origen (`live rate`, `last known rate` o `default value`).

### Otros detalles

- El icono de la ventana (`src/main/resources/app.png`) y `orders.json` se cargan desde el classpath, para que funcionen dentro del JAR de la release.
- Ejecución: `java -jar swing-app-<version>.jar` o doble clic sobre el JAR descargado de la release.