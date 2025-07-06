docker build -f Dockerfile_15 -t localhost:5001/postgres-pgvector-15 .
docker push localhost:5001/postgres-pgvector-15
#docker run -e POSTGRES_PASSWORD=password localhost:5001/postgres-pgvector-15