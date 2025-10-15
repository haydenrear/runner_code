to build and run...

```shell
docker build --build-arg="PG_MAJOR=15" -t postgres-pgvector . 
docker rm postgres-pgvector || true && docker run -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 --name=postgres-pgvector postgres-pgvector
```

then you can check to see if the vector has been created

```shell
docker exec -it postgres-pgvector psql -U postgres
\dt
select * from public.items;
```

this should print

```
 id | embedding 
----+-----------
  1 | [1,2,3]
  2 | [4,5,6]
  3 | [7,8,9]
(3 rows)
```