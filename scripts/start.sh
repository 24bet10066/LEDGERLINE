#!/bin/sh
set -eu
mvn -q clean package
docker compose up -d
echo "LEDGERLINE: http://localhost:8080/"
