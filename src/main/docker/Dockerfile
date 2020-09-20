FROM jboss/wildfly:20.0.1.Final
LABEL maintainer="jorgelojam@gmail.com"

ENV WILDFLY_USER jloja
ENV WILDFLY_PASS JlojaPassword

RUN echo "Adding WildFly administrator"
RUN $JBOSS_HOME/bin/add-user.sh -u $WILDFLY_USER -p $WILDFLY_PASS --silent

COPY maven/${project.build.finalName}.${project.packaging} ${JBOSS_HOME}/standalone/deployments/
EXPOSE 8080
EXPOSE 9990
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-microprofile.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]