docker run -d --name=mykeycloak -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=DemoAdmin -p 8888:8080 jboss/keycloak -Dkeycloak.profile.feature.upload_scripts=enabled

docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' srvkeycloak

docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' srvwildfly

export TOKEN=$(\
curl -s -L -X POST 'http://srvkeycloak:8080/auth/realms/demojwt/protocol/openid-connect/token' \
-H 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=demojwt-mp' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'scope=openid' \
--data-urlencode 'username=admin1' \
--data-urlencode 'password=password1'  | jq --raw-output '.access_token' \
 )

export TOKEN=$(\
curl -s -L -X POST 'http://srvkeycloak:8080/auth/realms/demojwt/protocol/openid-connect/token' \
-H 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=demojwt-mp' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'scope=openid' \
--data-urlencode 'username=user1' \
--data-urlencode 'password=password1'  | jq --raw-output '.access_token' \
 )

JWT=`echo $TOKEN | sed 's/[^.]*.\([^.]*\).*/\1/'`

echo $JWT | base64 -d | python -m json.tool

curl -H "Authorization: Bearer $TOKEN" http://srvwildfly:8080/demojwt/api/saludos/hola