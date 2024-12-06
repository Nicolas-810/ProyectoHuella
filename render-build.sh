#!/bin/bash
# Configurar JAVA_HOME
export JAVA_HOME=/opt/render/java-17
export PATH=$JAVA_HOME/bin:$PATH

# Ejecutar el comando de construcción con Maven Wrapper
./mvnw clean package
