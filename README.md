# Riacecí-Application

*Sistema de Autenticação e Gerenciamento de Pedidos.*

## Descrição

A aplicação é um sistema que gerencia pedidos, produtos e clientes. 
Também fornece recursos para autenticação e autorização de usuários.

## Funcionalidades

1. #### Autenticação de Usuários:
   - Registro de novos usuários.
   - Login de usuários existentes gerando um token JWT para autenticação subsequente.
   
2. #### Gestão de Clientes:
    - Criação, leitura, atualização e exclusão de clientes.
   
3. #### Gestão de Produtos:
    - Criação, leitura, atualização e exclusão de produtos.

4. #### Gestão de Pedidos:
    - Os clientes podem criar novos pedidos.
    - Pedidos podem conter múltiplos itens.

## Uso dos Endpoints

1. **AuthenticationController**: Trata de questões de autenticação.
    - Exemplo de endpoint:
      ```json lines
      POST "/auth/register" Registra um novo usuário.
      {
        "login": "exampleLogin",
        "password": "examplePassword",
        "role": "ADMIN"
      }
      ```
      ```json
      POST "/auth/login": Autentica o usuário.
      {
        "login": "exampleLogin",
        "password": "examplePassword"
      }
      ```

2. **Clientes:**
    - Exemplo de endpoint:
      ```json
      POST "/customer/new-customer": Cria um novo cliente.
      {
        "name": "string",
        "cnpj": "stringstringst",
        "email": "string",
        "password": "string",
        "customerType": "Supermarket"
      }
      ```
      ```json
      GET "/customer/find-customers": Retorna todos os clientes.
      ```
      ```json
      GET "/customer/find-by-cnpj/{cnpj}": Retorna um cliente com base em seu CNPJ.
      ```
      ```json
      GET "/customer/find-by-name/{name}": Retorna um cliente com base em seu nome.
      ```
      ```json
      DELETE "/customer/delete-customer/id/{id}": Exclui um cliente com base em seu ID.
      ```
      ```json
      PUT "/customer/update-customer/id/{id}": Atualiza os dados de um cliente.
      {
        "name": "Cailan",
        "cnpj": "74186204000103",
        "email": "cailan@gmail.com",
        "password": "123456",
        "customerType": "Supermarket"
      }
      ```

- **Produtos:**
    - Exemplo de endpoint:
      ```json
      POST "/products/new-product": Cria um novo produto.
      {
        "name": "string",
        "description": "string",
        "price": 0,
        "image": "string"
      }
      ```
      ```json
      GET "/products/find-products": Retorna todos os produtos.
      ```
      ```json
      DELETE "/products/delete-product/id/{id}": Exclui um produto com base em seu ID.
      ```
      ```json
      PUT "/products/update-product/id/{id}": Atualiza os dados de um produto.
      {
        "name": "Pizza Prestígio",
        "description": "Pizza chocolate feito com prestígio Nestlé®",
        "price": 32.90,
        "image": "https://riaceci.s3.sa-east-1.amazonaws.com/apresentacao_comercial_riaceci/pizza_prestigio.png"
      }

      ```

- **Pedidos:**
    - Exemplo de endpoint:
      ```json
      POST "/products/new-product": Cria um novo produto.
      {
        "customerId": 1,
        "products": [
          {
          "id": 1,
          "quantity": 10
          }
        ]
      }
      ```
      ```json
      GET "/orders/find-order/id/{id}": Retorna um pedido com base em seu ID.
      ```
      ```json
      GET "/orders/find-order-by-cnpj/{cnpj}": Retorna pedidos com base no CNPJ do cliente.
      ```

## Como Rodar a Aplicação

1. Clone o repositório para sua máquina local.
2. Abra o projeto usando IntelliJ ou Eclipse.
3. Garanta que todas as dependências especificadas no `build.gradle` estão devidamente instaladas.
4. Configure as variáveis de ambiente ou propriedades conforme definido no arquivo `application.yaml`.
5. Conecte o banco de dados.
6. Execute a aplicação a partir da classe principal.
7. Acesse os endpoints via Postman, [Swagger](http://localhost:8080/swagger-ui/index.html#) ou qualquer outra ferramenta de teste de API.

## Como Conectar no Banco de Dados

1. Abra um gerenciador de banco de dados como o DataGrip.
2. Abra o Database Explorer
   <p align="center">
<img src="https://riaceci.s3.sa-east-1.amazonaws.com/README/database-explorer.png" alt="Database Explorer"></p>
3. Escolha a opção "Data Source from URL"
   <p align="center">
<img src="https://riaceci.s3.sa-east-1.amazonaws.com/README/data-source-from-url.png" alt="Database Explorer"></p>
4. Coloque esta URL "jdbc:mysql://riaceci.cmzh6h3jlyws.sa-east-1.rds.amazonaws.com:3306" e escolha o Driver MySQL.
   <p align="center">
<img src="https://riaceci.s3.sa-east-1.amazonaws.com/README/url_driver.png" alt="Database Explorer"></p>
5. Na próxima tela, preencha o User e Password com as credencias que estão no [application.yaml](src%2Fmain%2Fresources%2Fapplication.yaml)
    -    username: cailangrott
    -    password: Caica122014
   <p align="center">
<img src="https://riaceci.s3.sa-east-1.amazonaws.com/README/data-sources-driver.png" alt="Database Explorer"></p>
6. Seguindo esses passos, a conexão com o banco de dados estará feito.
   <p align="center">
<img src="https://riaceci.s3.sa-east-1.amazonaws.com/README/database-online.png" alt="Database Explorer"></p>


## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot:** Framework para facilitar o desenvolvimento de aplicações stand-alone, production-grade com Java.
- **Spring Data JPA:** Para persistência de dados e operações CRUD.
- **Spring Security:** Para autenticação e autorização.
- **JWT (JSON Web Tokens):** Para autenticação e geração de tokens.
- **Hibernate:** ORM para mapeamento objeto-relacional.
- **Lombok:** Biblioteca Java que ajuda a reduzir a verbosidade do código.
- **Gradle:** Ferramenta de automação de compilação.
