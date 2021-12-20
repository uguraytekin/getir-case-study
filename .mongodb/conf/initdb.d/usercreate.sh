set -Eeuo pipefail

echo changing permissions

chmod 600 /auth/file.key
chown 999:999 /auth/file.key

mongo -u ${MONGO_INITDB_ROOT_USERNAME} -p ${MONGO_INITDB_ROOT_PASSWORD} <<EOF
    use ${MONGO_INITDB_DATABASE}
    db.createUser({
        user: "${MONGO_INITDB_USERNAME}",
        pwd: "${MONGO_INITDB_PASSWORD}",
        roles: [ { role: 'readWrite', db: "${MONGO_INITDB_DATABASE}" } ]
    })
EOF
