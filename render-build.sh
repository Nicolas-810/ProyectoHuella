#!/bin/bash
export JAVA_HOME=/opt/render/java-17
export PATH=$JAVA_HOME/bin:$PATH
./mvnw clean package
