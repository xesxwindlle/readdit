#!/bin/bash
# Run this from the project root BEFORE applying the other manifests.
# It creates the MySQL init ConfigMap from your SQL files.
#
# Usage: bash k8s/create-db-configmap.sh

set -e

# Check if namespace exists first
kubectl get namespace readdit > /dev/null 2>&1 || kubectl apply -f k8s/namespace.yaml

# Delete existing ConfigMap if re-running
kubectl delete configmap mysql-init-scripts -n readdit 2>/dev/null || true

kubectl create configmap mysql-init-scripts \
  --from-file=1_schema.sql=database/schema.sql \
  --from-file=2_data.sql=database/data.sql \
  --from-file=3_images.sql=database/images.sql \
  -n readdit

echo "ConfigMap mysql-init-scripts created successfully."
echo "Note: If images.sql is larger than ~1MB this will fail."
echo "In that case, load images manually after the cluster is up:"
echo "  kubectl exec -it deploy/mysql -n readdit -- mysql -uroot -p readdit < database/images.sql"
