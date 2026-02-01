# TrackCore
Sistema SaaS de Gestão de Ativos Corporativos e Rastreabilidade Patrimonial.
## Tecnologias
- Java 21 / Spring Boot 3.5.10
- Python 3.13
- PostgreSQL 16
- Docker
- Power BI
## Estrutura do Projeto
```
TrackCore/
├── backend-java/          # API REST Spring Boot
├── scripts-python/        # Scripts de automação
│   ├── calculate_depreciation.py
│   └── import_invoices.py
├── database/              # Scripts SQL
│   ├── create_tables.sql
│   └── insert_test_data.sql
├── docs/                  # Documentação
├── docker-compose.yml
└── README.md
```
## Como Executar
### Pré-requisitos
- Docker Desktop instalado
- Java 21
- Python 3.x
### 1. Clonar o repositório
```bash
git clone <URL_DO_REPOSITORIO>
cd TrackCore
```
### 2. Rodar com Docker
```bash
docker-compose up -d
```
### 3. Acessar a API
```
http://localhost:8080/api/assets
http://localhost:8080/api/users
http://localhost:8080/api/locations
http://localhost:8080/api/custody-history
```
### 4. Rodar scripts Python
```bash
cd scripts-python
python -m venv venv
venv\Scripts\activate
pip install psycopg2-binary pandas openpyxl python-decouple
python calculate_depreciation.py
python import_invoices.py example_invoice.csv
```
## Endpoints da API
### Assets
- GET /api/assets - Listar todos os ativos
- GET /api/assets/{id} - Buscar ativo por ID
- GET /api/assets/status/{status} - Buscar por status
- GET /api/assets/category/{category} - Buscar por categoria
- GET /api/assets/available - Listar ativos disponíveis
- POST /api/assets - Criar ativo
- PUT /api/assets/{id} - Atualizar ativo
- POST /api/assets/{id}/checkout - Atribuir ativo
- POST /api/assets/{id}/checkin - Devolver ativo
- DELETE /api/assets/{id} - Deletar ativo
### Users
- GET /api/users - Listar todos os usuários
- GET /api/users/{id} - Buscar usuário por ID
- GET /api/users/email/{email} - Buscar por email
- GET /api/users/department/{department} - Buscar por departamento
- GET /api/users/active - Listar usuários ativos
- POST /api/users - Criar usuário
- PUT /api/users/{id} - Atualizar usuário
- PATCH /api/users/{id}/activate - Ativar usuário
- PATCH /api/users/{id}/deactivate - Desativar usuário
- DELETE /api/users/{id} - Deletar usuário
### Locations
- GET /api/locations - Listar todas as localizações
- GET /api/locations/{id} - Buscar localização por ID
- GET /api/locations/branch/{branch} - Buscar por filial
- POST /api/locations - Criar localização
- PUT /api/locations/{id} - Atualizar localização
- DELETE /api/locations/{id} - Deletar localização
### Custody History
- GET /api/custody-history - Listar todo o histórico
- GET /api/custody-history/{id} - Buscar por ID
- GET /api/custody-history/asset/{assetId} - Histórico de um ativo
- GET /api/custody-history/user/{userId} - Histórico de um usuário
- GET /api/custody-history/asset/{assetId}/active - Custódia ativa de um ativo
- GET /api/custody-history/user/{userId}/active - Custódias ativas de um usuário
## Decisões Técnicas
- PostgreSQL foi escolhido pela integridade referencial e suporte nativo a JSONB para o audit log
- Spring Boot foi usado por ser o framework mais maduro e utilizado no ecossistema Java
- Python foi usado para automação por sua facilidade com processamento de dados
- Docker foi usado para garantir que o ambiente de desenvolvimento seja consistente 