docker build -t localhost:5005/postgres-pgvector .
docker push localhost:5005/postgres-pgvector
#docker run -e POSTGRES_PASSWORD=password localhost:5005/postgres-pgvector