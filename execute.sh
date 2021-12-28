#!/bin/bash
npx kill-port 8080
java -jar ./target/SmartHome-*-SNAPSHOT.jar &
sleep 10
xdg-open http://localhost:8080/swagger-ui.html &
