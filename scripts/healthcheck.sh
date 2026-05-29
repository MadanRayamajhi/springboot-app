#!/bin/bash
# Health check script for monitoring

MAX_RETRIES=30
RETRY_INTERVAL=5
URL="http://localhost:8080/health"

for i in $(seq 1 $MAX_RETRIES); do
    HTTP_CODE=$(curl -s -o /dev/null -w "%{http_code}" $URL)
    if [ $HTTP_CODE -eq 200 ]; then
        echo "Health check passed!"
        exit 0
    fi
    echo "Attempt $i/$MAX_RETRIES: Health check failed, retrying in $RETRY_INTERVAL seconds..."
    sleep $RETRY_INTERVAL
done

echo "Health check failed after $MAX_RETRIES attempts"
exit 1