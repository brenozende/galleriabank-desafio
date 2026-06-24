# Desafio GalleriaBank

API REST para gerenciamento de usuarios, clientes, produtos e pedidos usando Java, Spring Boot, Spring Security, JWT, Spring Data JPA e H2. O projeto tambem possui configuracao opcional para PostgreSQL via Docker.

## Requisitos

- Java 25 ou versão compatível com o `pom.xml`

## Rodar a API com H2

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

Por padrão, a aplicação usa H2 em memória. O console do H2 fica disponível em:

```http
http://localhost:8080/h2-console
```

Dados de conexão:

- JDBC URL: `jdbc:h2:mem:galleriabank`
- Usuário: `sa`
- Senha: deixe em branco

## Rodar com PostgreSQL via Docker

Quando o Docker estiver disponível, suba o banco:

```bash
docker compose up -d
```

O PostgreSQL ficará disponível em:

- Host: `localhost`
- Porta: `5432`
- Database: `galleriabank`
- Usuario: `galleriabank`
- Senha: `galleriabank`

Depois rode a API ativando o profile `postgres`:

```bash
SPRING_PROFILES_ACTIVE=postgres ./mvnw spring-boot:run
```

No Windows:

```powershell
$env:SPRING_PROFILES_ACTIVE="postgres"
mvnw.cmd spring-boot:run
```

Tambem e possivel sobrescrever por variaveis de ambiente:

```bash
DATABASE_URL=jdbc:postgresql://localhost:5432/galleriabank
DATABASE_USERNAME=galleriabank
DATABASE_PASSWORD=galleriabank
JWT_SECRET=galleria-bank-jwt-secret-key-32-chars
```

## Autenticacao

O cadastro de usuario e publico:

```http
POST /usuarios
```

Exemplo:

```json
{
  "name": "Usuario Teste",
  "username": "usuario",
  "password": "123456"
}
```

O login retorna um token JWT:

```http
POST /auth/login
```

Exemplo:

```json
{
  "username": "usuario",
  "password": "123456"
}
```

Use o token retornado nas rotas protegidas:

```http
Authorization: Bearer <token>
```
