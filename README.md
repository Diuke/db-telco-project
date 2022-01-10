Cosas adicionales por hacer para que corra bien el proyecto:

- Usar la maquina virtual de ubuntu (ahi estan los archivos de las librerias adicionales)
- Agregar en Servers -> Tomcat v9.0 -> tomee.xml :
```
<Resource id="TelcoAppDB" type="DataSource">
	JdbcDriver com.mysql.cj.jdbc.Driver
	JdbcUrl jdbc:mysql://localhost:3306/telco_app_db
	UserName root
	Password ian
</Resource>
```

Esto para que reconozca la base de datos

- Tambien agrego un dump de la base de datos.
