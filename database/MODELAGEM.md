# MODELAGEM DO BANCO DE DADOS - TRACKCORE

## Objetivo
Sistema de gestão de ativos corporativos com rastreabilidade completa.

## TABELAS DO SISTEMA

### 1. assets (Ativos)
Armazena todos os bens da empresa.

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | SERIAL PRIMARY KEY | ID único do ativo |
| name | VARCHAR(200) | Nome/descrição do ativo |
| category | VARCHAR(100) | Categoria (notebook, monitor, veículo) |
| purchase_value | DECIMAL(10,2) | Valor de compra |
| purchase_date | DATE | Data de aquisição |
| current_value | DECIMAL(10,2) | Valor atual (após depreciação) |
| status | VARCHAR(50) | em_uso, disponivel, manutencao, baixado |
| location_id | INTEGER | FK para locations |
| created_at | TIMESTAMP | Data de criação do registro |
| updated_at | TIMESTAMP | Última atualização |

### 2. users (Usuários/Colaboradores)
Pessoas que podem ter ativos sob sua custódia.

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | SERIAL PRIMARY KEY | ID único do usuário |
| name | VARCHAR(200) | Nome completo |
| email | VARCHAR(200) UNIQUE | Email corporativo |
| department | VARCHAR(100) | Departamento (TI, RH, Vendas) |
| active | BOOLEAN | Se está ativo na empresa |
| created_at | TIMESTAMP | Data de criação |

### 3. custody_history (Histórico de Custódia)
Rastreabilidade completa de movimentações.

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | SERIAL PRIMARY KEY | ID único do registro |
| asset_id | INTEGER | FK para assets |
| user_id | INTEGER | FK para users |
| start_date | TIMESTAMP | Quando o usuário pegou o ativo |
| end_date | TIMESTAMP | Quando devolveu (NULL = ainda tem) |
| notes | TEXT | Observações da movimentação |

### 4. locations (Localizações Físicas)
Onde os ativos ficam guardados.

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | SERIAL PRIMARY KEY | ID único |
| branch | VARCHAR(100) | Filial/Unidade |
| floor | VARCHAR(50) | Andar |
| room | VARCHAR(50) | Sala/Setor |

### 5. audit_log (Log de Auditoria)
Registra todas as alterações no sistema.

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | SERIAL PRIMARY KEY | ID único |
| table_name | VARCHAR(100) | Qual tabela foi alterada |
| record_id | INTEGER | ID do registro alterado |
| action | VARCHAR(50) | INSERT, UPDATE, DELETE |
| old_values | JSONB | Valores antigos |
| new_values | JSONB | Valores novos |
| changed_by | VARCHAR(200) | Usuário que fez a mudança |
| changed_at | TIMESTAMP | Quando foi alterado |

## RELACIONAMENTOS

- Um asset pertence a uma location
- Um asset pode ter vários registros em custody_history
- Um user pode ter vários registros em custody_history
- custody_history conecta assets e users (quem tem o quê)

## REGRAS DE NEGÓCIO

1. Um ativo só pode estar com UM usuário por vez
2. Quando um ativo é tran