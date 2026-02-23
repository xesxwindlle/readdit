#!/bin/bash
# ==========================================================
# Script: load_images.sh
# Purpose: Load book cover images into MySQL BLOB column
# ==========================================================

# ----------------------
# MySQL credentials
# ----------------------
MYSQL_USER="root"
MYSQL_PASS="yv10989197"
DB_NAME="readdit"

# ----------------------
# Image directory (relative to this script)
# ----------------------
IMG_DIR="$(dirname "$0")/images"

# ----------------------
# Function to load one image
# ----------------------
load_image() {
    ISBN="$1"
    FILE="$IMG_DIR/$ISBN.jpg"

    if [ -f "$FILE" ]; then
        echo "Loading $ISBN.jpg..."
        HEX=$(xxd -p "$FILE" | tr -d '\n')
        mysql -u"$MYSQL_USER" -p"$MYSQL_PASS" "$DB_NAME" -e \
            "UPDATE book SET cover_image = X'$HEX' WHERE isbn = '$ISBN';"
        echo "Done: $ISBN"
    else
        echo "File not found: $FILE"
    fi
}

# ----------------------
# Automatically get all ISBNs from images folder
# ----------------------
for IMAGE_FILE in "$IMG_DIR"/*.jpg; do
    ISBN=$(basename "$IMAGE_FILE" .jpg)
    load_image "$ISBN"
done

# ----------------------
# Verify images loaded
# ----------------------
echo ""
echo "Verifying images..."
mysql -u"$MYSQL_USER" -p"$MYSQL_PASS" "$DB_NAME" -e \
"SELECT id, isbn, title, LENGTH(cover_image) AS image_size FROM book;"
