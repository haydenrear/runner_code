docker build -f Dockerfile_15 -t localhost:5005/postgres-pgvector-15 .
docker push localhost:5005/postgres-pgvector-15
#docker run -e POSTGRES_PASSWORD=password localhost:5005/postgres-pgvector-15