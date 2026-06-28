#!/bin/sh
sed -i "s|http://localhost:8080|${API_URL}|g" /app/config/config.json
exec java -jar app.jar