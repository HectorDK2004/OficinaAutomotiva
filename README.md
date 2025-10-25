# Sistema para Oficina Automotiva

Sistema completo para gerenciamento de oficinas automotivas desenvolvido em Java seguindo o padrão MVC, com persistência em banco de dados MySQL.

## Funcionalidades

-  Cadastro de veículos e condutores (OO com herança)

-  Registro de serviços executados (listas + persistência com banco de dados)

-  Visualização de ordens de serviço via interface console (MVC)

-  Relatório de serviços por veículo usando estruturas de repetição e operadores

-  Persistência completa com MySQL

## Como Usar o Sistema
###Fluxo Recomendado:
- 1. Cadastrar Condutor (Menu opção 1)
- 2. Cadastrar Veículo (Menu opção 2) - Necessita condutor cadastrado
- 3. Registrar Serviço (Menu opção 3) - Necessita veículo cadastrado
- 4. Criar Ordem de Serviço (Menu opção 4) - Associa serviços a veículos
- 5. Consultar Relatórios (Menu opção 9) - Gera relatório completo

## Tecnologias

- Java 11
- MySQL 8.0
- Maven
- JSON para integração com APIs
- JDBC para persistência
- Padrão MVC (Model-View-Controller)

## Como Executar

### Pré-requisitos

- Java 11 ou superior
- MySQL 8.0 ou superior
- Maven 3.6 ou superior

### Passos para execução:

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/oficina-automotiva.git
   cd oficina-automotiva
   
2. Configure o banco de dados MySQL
Crie o banco de dados:

- CREATE DATABASE oficina_automotiva;

3. Compile e execute:
  ```bash
  java -jar target/oficina-automotiva.jar
