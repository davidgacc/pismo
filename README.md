# Getting Started

### 1. Docker Build

```bash
docker build -t pismo .
```


### 2. Run the PostgreSQL Container

```bash
docker run --name postgres-db -e POSTGRES_DB=postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:latest
```

### Create some rows

Create tables in the file resources/create_tables.sql

### 3. Run Your Application Container
```bash
docker run --name pismo-app --link postgres-db -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db:5432/postgres -e SPRING_DATASOURCE_USERNAME=postgres -e SPRING_DATASOURCE_PASSWORD=postgres pismo
```