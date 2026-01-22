# Sistema de Gerenciamento de Loja (Java JDBC)

Este é um projeto de estudo desenvolvido para aplicar conceitos fundamentais de Java e manipulação de banco de dados sem o uso de frameworks ORM (como Hibernate), focando na compreensão da estrutura "under the hood" (por baixo dos panos).

## 🚀 Sobre o Projeto
O sistema é uma aplicação console (CLI) que permite o gerenciamento completo de uma loja, incluindo:
- **Clientes:** Cadastro, listagem, atualização e remoção (CRUD).
- **Produtos:** Cadastro e controle de estoque.
- **Pedidos:** Realização de vendas com cálculo automático de subtotal e atualização no banco de dados.

O objetivo principal foi implementar uma **Arquitetura em Camadas (Layered Architecture)** para separar as responsabilidades de interface, regras de negócio e persistência de dados.

## 🛠 Tecnologias Utilizadas
- **Java 21**
- **JDBC (Java Database Connectivity)**
- **MySQL** (Banco de dados relacional)
- **Padrão DAO/Repository** (Para acesso a dados)

## 📂 Arquitetura do Projeto
O projeto segue uma divisão clara de responsabilidades:
- `store.domain`: Classes POJO que representam as entidades do banco (Customers, Products, Orders).
- `store.repository`: Camada responsável pela comunicação direta com o banco de dados (SQL).
- `store.services`: Camada de regras de negócio e validações antes da persistência.
- `store.main`: Interface do usuário via terminal.

## ⚙️ Como rodar o projeto

### Pré-requisitos
- Java JDK 21 instalado.
- MySQL Server rodando.
- Criar o banco de dados `store`.

### Configuração do Banco de Dados
Execute o seguinte script SQL para criar as tabelas necessárias:

```sql
CREATE DATABASE store;
USE store;

CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    cpf VARCHAR(20)
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    price DECIMAL(10,2),
    quantity INT
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    total_amount DECIMAL(10,2),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    unit_price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
