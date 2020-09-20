# Ejemplo de integracion simple de microprofile jwt auth con keycloak

Para correr el ejemplo se debe contruirlo por medio del siguiente comando:

```bash
mvn clean package docker:build
```

Para arrancar los contenedores del keycloak y de la aplicacion se debe ejecutar lo siguiente:

```bash
docker-compose up
```

Para determinas las direcciones IP de los contenedores se debe consultar de la siguiente manera:

```bash
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' srvkeycloak
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' srvwildfly
```

Con estas direcciones se debe registrar en la maquina fisica en el archivo host para no tener problemas con validaciones de origenes en las cabeceras HTTP

Se debe acceder al servidor keycloak por medio de la siguiente URL

http://srvkeycloak:8080

Con las credenciales de administrador se debe crear un realm importando el archivo demojwt-realm.json y dentro de este realm se debe crear usuario y asignar a lo roles definidos

Para emitir un token se debe autentificar con alguno de los usuarios creados, por ejemplo para un rol de administrador

```bash
export TOKEN=$(\
curl -s -L -X POST 'http://srvkeycloak:8080/auth/realms/demojwt/protocol/openid-connect/token' \
-H 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=demojwt-mp' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'scope=openid' \
--data-urlencode 'username=admin1' \
--data-urlencode 'password=password1'  | jq --raw-output '.access_token' \
 )
```

Para un rol de usuario normal

```bash
export TOKEN=$(\
curl -s -L -X POST 'http://srvkeycloak:8080/auth/realms/demojwt/protocol/openid-connect/token' \
-H 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=demojwt-mp' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'scope=openid' \
--data-urlencode 'username=user1' \
--data-urlencode 'password=password1'  | jq --raw-output '.access_token' \
 )
```

En el caso de que se requiere ver el contenido del JWT se puede ejecutar lo siguiente:

```bash
JWT=`echo $TOKEN | sed 's/[^.]*.\([^.]*\).*/\1/'`
echo $JWT | base64 -d | python -m json.tool
```

Una vez que se tiene el token se puede consumir el servicio utilizando el JWT generado, como se muestra a continuacion

```bash
curl -H "Authorization: Bearer $TOKEN" http://srvwildfly:8080/demojwt/api/saludos/hola
```