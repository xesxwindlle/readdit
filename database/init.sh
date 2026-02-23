#!/bin/bash
# ==========================================================
# Script: initialize.sh
# Purpose: Initialize database with schema, data, and images
# ==========================================================

# ----------------------
# MySQL credentials
# ----------------------
MYSQL_USER="root"
MYSQL_PASS="yv10989197"
DB_NAME="readdit"

# ----------------------
# Step 1: Create schema
# ----------------------
echo "Step 1: Creating schema..."
mysql -u"$MYSQL_USER" -p"$MYSQL_PASS" < "$(dirname "$0")/schema.sql"

# ----------------------
# Step 2: Insert data (without images)
# ----------------------
echo "Step 2: Inserting data..."
mysql -u"$MYSQL_USER" -p"$MYSQL_PASS" < "$(dirname "$0")/data.sql"

# ----------------------
# Step 3: Load images
# ----------------------
echo "Step 3: Loading images..."
bash "$(dirname "$0")/load_images.sh"

# ----------------------
# Done
# ----------------------
echo ""
echo "Database initialization complete!"

