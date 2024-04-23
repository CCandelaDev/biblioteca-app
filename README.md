# Biblioteca App

Esta es una aplicación de gestión de una biblioteca desarrollada en Java utilizando Hibernate, para interactuar con la base de datos.


## Requisitos previos

 - Java JDK (versión 8 o superior)
 - Maven
 - MySQL (u otro compatible con Hibernate)


## Configuración

 1. Clona el repositorio de GitHub (Opcional)

 	git clone https://github.com/CCandelaDev/biblioteca-app.git

 2. Importa el proyecto en tu IDE como un proyecto Maven.

 3. Configuración de Hibernate:

	En el directorio src/main/resources, se encuentra el archivo **hibernate.cfg.xml**

	Asegúrate de que los parámetros de conexión a la base de datos estén configurados correctamente:
	
	- **hibernate.connection.url:** La URL de conexión a la base de datos.
	- **hibernate.connection.username:** Nombre de usuario de la base de datos.
	- **hibernate.connection.password:** Contraseña de la base de datos.

	Si estás utilizando una base de datos diferente a MySQL, asegúrate de cambiar
	 el dialecto Hibernate correspondiente en la propiedad hibernate.dialect

	Crea una base de datos con el nombre `biblioteca`. Puedes hacerlo utilizando el cliente MySQL o 
	cualquier herramienta de gestión de bases de datos que prefieras.



## Compilación y Ejecución
	
	1. Abre una terminal y navega hasta el directorio raíz del proyecto.

	2. Ejecuta el siguiente comando para compilar y generar el archivo JAR:
		
		`mvn clean package`
	
	3. Una vez tenemos el archivo JAR, ejecuta la aplicación con el comando:

		`java -jar target/biblioteca-app.jar`	



## Uso

Al ejecutar la aplicación, se mostrará un menú con varias opciones:

1. Gestión de Libros
2. Gestión de Lectores
3. Gestión de préstamos
4. Consultas
5. Salir



### Gestion de libros:

Esta opción te permite realizar diversas operaciones relacionadas con la gestión de libros en la biblioteca.
	
	Insertar libro: Permite añadir un nuevo libro a la base de datos. 
	Deberás proporcionar el título del libro, el nombre del autor y el año de publicación.

	Listar libros: Muestra todos los libros almacenados en la base de datos, 
	incluyendo su título, autor y año de publicación.

	Actualizar libro: Permite actualizar la información de un libro existente en la base de datos. 
	Debes proporcionar el ID del libro que deseas actualizar, así como los nuevos datos del libro, como el título, autor y año de publicación.

	Borrar libro: Permite eliminar un libro de la base de datos. Debes proporcionar el ID del libro que deseas eliminar.	

 	Volver al menú pricipal: Permite regresar al menú principal de la aplicación.


### Gestión de Lectores:

	Esta opción te permite gestionar los lectores de la biblioteca:

	Insertar lector: Permite agregar un nuevo lector a la base de datos. Debes proporcionar el nombre, apellido y correo electrónico del lector.

	Listar lectores: Muestra todos los lectores almacenados en la base de datos, incluyendo su nombre, apellido y correo electrónico.

	Actualizar lector: Permite actualizar la información de un lector existente en la base de datos. Debes proporcionar el ID del lector que quieres actualizar, 
	así como los nuevos datos del lector, como el nombre, apellido y correo electrónico.

	Borrar lector: Permite eliminar un lector de la base de datos. Debes proporcionar el ID del lector que deseas eliminar.

 	Volver al menú pricipal: Permite regresar al menú principal de la aplicación.


### Gestión de Prestamos:

	Esta opción te permite gestionar los préstamos de libros en la biblioteca:

	Asignar libro a lector: Permite asignar un libro a un lector, registrando el préstamo en la base de datos. Debes proporcionar el ID del libro y el ID del lector.

	Registrar devolución: Permite registrar la devolución de un libro por parte de un lector, actualizando el estado del préstamo en la base de datos. 
	Debes proporcionar el ID del préstamo que deseas registrar como devuelto.

 	Volver al menú pricipal: Permite regresar al menú principal de la aplicación.


### Consultas:

	Esta opción te permite realizar consultas específicas sobre préstamos y libros:

	Libros prestados a un lector: Muestra los libros actualmente prestados a un lector específico. Debes proporcionar el ID del lector para realizar la consulta.

	Libros disponibles para préstamo: Muestra los libros que están disponibles para préstamo en la biblioteca.

	Historial de préstamos por lector: Muestra el historial de préstamos de un lector específico. Debes proporcionar el ID del lector para realizar la consulta.

 	Volver al menú pricipal: Permite regresar al menú principal de la aplicación.

	Sigue las instrucciones en pantalla para interactuar con la aplicación y selecciona la opción deseada en el menú principal para 
	realizar las operaciones correspondiente.


## Cerrar la aplicación

	Para salir de la aplicación, selecciona la opción correspondiente en el menú principal.


## Prueba untaria para el método insertarLibro en GestorLibros (sólo de este método)

 	Este método se encarga de insertar un nuevo libro en la base de datos de la biblioteca.

	Para realizar esta prueba, necesitamos una instancia de `GestorLibros` y una sesión de Hibernate. 
	En este caso, utilizamos la sesión de Hibernate que proporciona la clase `HibernateUtil`. 
	Además, simulamos la entrada del usuario con los detalles del libro utilizando `System.setIn()`.


### Ejecutar la prueba

	1. Se crea una transacción en la sesión de Hibernate.
	2. Se llama al método `insertarLibro()` de `GestorLibros`.
	3. Confirmamos la transacción para guardar los cambios en la base de datos.


### Verificación de resultados

	Después de insertar el libro, verificamos que se haya guardado correctamente en la base de datos:
	- Verifico que el libro no sea nulo.
	- Compruebo que el título, autor y año de publicación del libro coincidan con los valores que espero.

 El fuente de la prueba unitaria se encuentra en el archivo **GestorLibrosTest.java**, en el directorio **src\main\java\gestion** del proyecto.

