# DataTable (to JSON [driver MySQL]) versión JAVA
Objeto DataTable estilo c# para java.De igual forma transforma en JSON
proyecto porteado de WebAPISQL por yorb1990 solo que añadiendo DataTable,DataSet,DataRow y DataColumn
en su versión en JAVA.
trabaja perfecto para angularJS y JQuery (transformacion JSON).

## Especificación
### Generacion
* DataTable
```java
 DT.DataTable dt=new DataTable(new String[]{"nombre","mensaje"});
```
* DataSet
```java
 DT.DataSet ds =new DataSet();
```
### Asignando
* Asignacion facil para DataTable
```java
 dt.AddRow(new Object[]{"juan","hola"});
 dt.AddRow(new Object[]{"Pedro","holas"});
```
* DataSet (requiere un DataTable)
```java
 ds.Tables.add(dt);
```
### Obtencion JSON
* DataTable
```java
dt.toJSON();
```
RESULTADO
```json
[{"nombre":"juan","mensaje":"hola"},{"nombre":"Pedro","mensaje":"holas"} ]
```
* DataSet
```java
 ds.toJSON();
```
RESULTADO
```json
[[{"nombre":"juan","mensaje":"hola"},{"nombre":"Pedro","mensaje":"holas"} ]]
```
Ejemplos elaborados en netbeans con Jersey para JAVA https://jersey.github.io

## Especificación avanzada
Se puede ocupar en conjunto con una conexión SQL para extraer o insertar datos.
### MySQLController
* Generar conexión
```java
 Connection conn = null;
 Class.forName("com.mysql.jdbc.Driver").newInstance();
 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","");
```
* Crear instancia
```java
 DB.MySQLController my=new MySQLController();
```
  instancia vacia sin conexion

```java
 DB.MySQLController my=new MySQLController(conn);
```
  instancia con cierre de la conexión
  
```java
 DB.MySQLController my=new MySQLController(conn,false);
```
  instancia sin cierre de la connexión

```java
 DB.MySQLController my=new MySQLController(null);
```
  instancia con error de conexion
* Colocar conexión
```java
 my.SetConnection(conn);
```
* Ejecucion
```java
my.ExecMulti("select * from test").toJSON();
```
RESULTADO
```json
[[{"nombre":"uno"},{"nombre":"dos"},{"nombre":"tres"},{"nombre":"cuatro"},{"nombre":"cinco"} ]]
```
### Soporte dee errores
Los errores los interpreta en el estilo de DataSset es decir si hay un error retorna esto:
```json
[[{"TIPO":"CONECTAR","ERROR":"La conexión a la base de datos no es válida.","INNER":"NO CONECTADA"} ]]
```
```json
[[{"TIPO":"EJECUTAR","ERROR":"You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \u0027ERROR\u0027 at line 1","INNER":"[Ljava.lang.StackTraceElement;@6bd56abb"} ]]
```
tenga en cuenta que el error es especifico de la vercion de la base de datos, este solo retorna el error.

NOTA: Bug del controlador MySQL conderado (no puede ejecutar y retornar storage procedure que incluyan dentro de este sentencias select), es por ello que se deve de ejecutar "call SP_Stest()" en el MySQL Controller
