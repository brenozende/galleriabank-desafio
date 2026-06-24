# Desafio Gallery Bank Frontend

Frontend Angular para o desafio Gallery Bank.

## Requisitos

- Node.js 22+
- npm 10+
- Docker e Docker Compose (opcional, para execução em container)

## Executando localmente com npm

1. Instale as dependências:

```bash
npm install
```

2. Inicie o servidor de desenvolvimento:

```bash
npm start
```

3. Acesse no navegador:

```text
http://localhost:4200
```

## Build de produção

```bash
npm run build
```

Os artefatos serão gerados na pasta `dist/`.

## Executando com Docker

Na raiz do projeto, execute:

```bash
docker compose up --build
```

A aplicação ficará disponível em:

```text
http://localhost:4200
```

Para parar os containers:

```bash
docker compose down
```

## Observações

- O frontend consome a API do backend em `http://localhost:8080` por padrão, configurada em `src/environments/environment.ts`.
- Se o backend estiver rodando em outra porta ou host, ajuste esse valor antes de iniciar o frontend.
