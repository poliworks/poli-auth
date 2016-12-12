#!/usr/bin/env bash

echo "Starting to build application to prod..."
lein ring uberjar
echo "Building docker image..."
docker build . -t leoiacovini/poli-auth
echo "Done building docker image!"
echo "Now pushing to remote..."
docker push leoiacovini/poli-auth
echo "Done, image pushed to leoiacovini/poli-auth:latest"