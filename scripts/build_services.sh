#!/bin/bash
function buildService() {
  MODULE_NAME=$1
  echo "Building $MODULE_NAME"

  echo "Building images..."
  mvn -f $MODULE_NAME/pom.xml dockerfile:build
  echo "Done!"
  sleep 5s
}

cd ..
mvn clean package -U
buildService eureka-server
buildService resource-service
buildService song-service
buildService resource-processor
