#!/bin/sh
docker run -v $(pwd)/setup:/migrations --network host migrate/migrate -path=/migrations/ -database postgres://admin:password@127.0.0.1:5432/postgres?sslmode=disable down -all
docker run -v $(pwd)/setup:/migrations --network host migrate/migrate -path=/migrations/ -database postgres://admin:password@127.0.0.1:5432/postgres?sslmode=disable up

docker run -v $(pwd)/tables:/migrations --network host migrate/migrate -path=/migrations/ -database postgres://admin:password@127.0.0.1:5432/threat_dashboard?sslmode=disable up


docker run -v $(pwd)/data:/data --network host governmentpaas/psql psql postgres://admin:password@localhost:5432/threat_dashboard?sslmode=disable -f /data/seed_data.sql

