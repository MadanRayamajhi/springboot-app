#!/bin/bash
set -e

echo "Starting deployment..."
cd /home/jenkins

# Pull latest images
docker pull your-dockerhub-username/spring-boot-app:latest

# Stop and remove old containers
docker-compose -f docker-compose.prod.yml down || true

# Start new containers
docker-compose -f docker-compose.prod.yml up -d

# Wait for application to start
sleep 15

# Verify deployment
if curl -f http://localhost:8080/health; then
    echo "Deployment successful!"
else
    echo "Health check failed!"
    exit 1
fi