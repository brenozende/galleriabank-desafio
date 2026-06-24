# Desafio GalleriaBank

Aplicação full stack para gerenciamento de usuários, clientes, produtos e pedidos.

O projeto é composto por:

* **Backend:** Java 25, Spring Boot, Spring Security, JWT e Spring Data JPA
* **Frontend:** Angular 21
* **Banco de dados:** PostgreSQL (Docker) ou H2 em memória para desenvolvimento

## Requisitos

### Para execução via Docker (recomendado)

* Docker
* Docker Compose

### Para execução local sem Docker

* Java 25 ou versão compatível com o `pom.xml`
* Maven

---

# Executando a aplicação com Docker

A aplicação pode ser executada de forma totalmente automatizada utilizando Docker Compose.

Na raiz do projeto, execute:

```bash
docker compose up --build
```

O comando irá:

* Subir o banco PostgreSQL
* Compilar e iniciar o backend Spring Boot
* Instalar dependências e iniciar o frontend Angular

Após a inicialização:

| Serviço    | URL                   |
| ---------- | --------------------- |
| Frontend   | http://localhost:4200 |
| Backend    | http://localhost:8080 |
| PostgreSQL | localhost:5432        |

### Credenciais do PostgreSQL

| Propriedade | Valor        |
| ----------- | ------------ |
| Database    | galleriabank |
| Usuário     | galleriabank |
| Senha       | galleriabank |

---

# Executando apenas o Backend com H2

Para desenvolvimento rápido é possível executar somente a API utilizando o banco H2 em memória.

Linux/Mac:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

A aplicação ficará disponível em:

```http
http://localhost:8080
```

## Console H2

```http
http://localhost:8080/h2-console
```

Dados de conexão:

| Propriedade | Valor                    |
| ----------- | ------------------------ |
| JDBC URL    | jdbc:h2:mem:galleriabank |
| Usuário     | sa                       |
| Senha       | (em branco)              |

---

# Executando o Backend com PostgreSQL Local

Suba o banco:

```bash
docker compose up -d postgres
```

Em seguida execute a API utilizando o profile PostgreSQL.

Linux/Mac:

```bash
SPRING_PROFILES_ACTIVE=postgres ./mvnw spring-boot:run
```

Windows:

```powershell
$env:SPRING_PROFILES_ACTIVE="postgres"
mvnw.cmd spring-boot:run
```

Também é possível sobrescrever as configurações por variáveis de ambiente:

```bash
DATABASE_URL=jdbc:postgresql://localhost:5432/galleriabank
DATABASE_USERNAME=galleriabank
DATABASE_PASSWORD=galleriabank
JWT_SECRET=galleria-bank-jwt-secret-key-32-chars
```

---

# Autenticação

## Cadastro de usuário

Endpoint público:

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

## Login

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

A resposta retornará um token JWT.

Utilize o token nas rotas protegidas:

```http
Authorization: Bearer <token>
```

---

# Estrutura do Projeto

```text
.
├── backend
├── frontend
├── docker-compose.yml
└── README.md
```
