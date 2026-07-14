# 🧪 Automated Testing Study
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-78A641?style=for-the-badge&logo=mockito&logoColor=white)
![TestContainers](https://img.shields.io/badge/TestContainers-1A567E?style=for-the-badge&logo=testcontainers&logoColor=white)
![REST Assured](https://img.shields.io/badge/REST%20Assured-3E863D?style=for-the-badge&logo=cucumber&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)


API REST em **Java + Spring Boot** para gerenciamento de pessoas (**Person**), construída com foco total em **cobertura de testes automatizados**  unitários e de integração usando **JUnit 5**, **Mockito**, **TDD**, **Spring Boot Test**, **TestContainers** e **REST Assured**.

Além do código da aplicação, este repositório documenta meu aprendizado sobre **por que** e **como** testar aplicações Java de forma profissional, cobrindo desde testes simples de unidade até testes de integração completos com banco de dados real via Docker.

---

## 📌 Sobre o projeto

A aplicação é uma **API REST de CRUD de pessoas**, desenvolvida em **Spring Boot**, e serve como base prática para aplicar testes em cada uma das camadas de uma aplicação real: **repository**, **service** e **controller**.

### O que a API faz

O recurso principal é o `Person`, uma entidade com os campos `firstName`, `lastName`, `address`, `gender` e `email`, persistida em um banco de dados **MySQL**. A API expõe os seguintes endpoints (`/person`):

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/person` | Lista todas as pessoas cadastradas |
| `GET` | `/person/{id}` | Busca uma pessoa pelo ID |
| `POST` | `/person` | Cria uma nova pessoa |
| `PUT` | `/person` | Atualiza os dados de uma pessoa existente |
| `DELETE` | `/person/{id}` | Remove uma pessoa pelo ID |


### Por que cada camada é testada separadamente

O grande objetivo do projeto é mostrar, na prática, como testar uma aplicação Spring Boot **camada por camada**, sem misturar responsabilidades:

- **`PersonRepositoryTest`** → testa apenas a persistência (queries, JPQL, regras do JPA), usando `@DataJpaTest`.
- **`PersonServiceTest`** → testa as regras de negócio da `PersonServices` de forma isolada, mockando o `PersonRepository` com Mockito (sem subir banco de dados nem contexto do Spring).
- **`PersonControllerTest`** → testa a camada web (endpoints, status HTTP, serialização JSON), mockando a camada de serviço com `@MockBean` e `@WebMvcTest`.
- **`PersonControllerIntegrationTest`** → teste de integração de ponta a ponta, subindo a aplicação completa e um banco **MySQL real via TestContainers**, fazendo requisições HTTP reais com **REST Assured** contra os endpoints.
- **`SwaggerIntegrationTest`** → garante que a documentação OpenAPI/Swagger da API continua acessível e funcional.

Essa separação deixa clara, na prática, a diferença entre **teste unitário** (rápido, isolado, sem dependências externas) e **teste de integração** (mais lento, valida o sistema como um todo, incluindo banco de dados real).

Estrutura principal do repositório:

```
automated-testing-study/
└── automated-tests-with-java/
    ├── src/main/java/br/com/automated_tests_with_java/
    │   ├── model/Person.java                   # Entidade JPA
    │   ├── repository/PersonRepository.java     # Consultas JPQL customizadas
    │   ├── service/PersonServices.java          # Regras de negócio
    │   ├── controller/PersonController.java     # Endpoints REST
    │   ├── handler/                             # Tratamento global de exceções
    │   └── config/OpenAPIConfig.java            # Configuração do Swagger
    └── src/test/java/br/com/automated_tests_with_java/
        ├── repository/PersonRepositoryTest.java
        ├── service/PersonServiceTest.java
        ├── controller/PersonControllerTest.java
        └── integrationtests/
            ├── controller/PersonControllerIntegrationTest.java
            ├── swagger/SwaggerIntegrationTest.java
            └── testcontainers/AbstractIntegrationTest.java
```

---

## 🧠 Conceitos estudados

### 1. JUnit 5

O **JUnit** é o framework padrão para escrever e executar testes em Java. A versão 5 trouxe uma arquitetura modular (Jupiter, Vintage e Platform) e diversas melhorias em relação ao JUnit 4.

Principais pontos estudados:

- **Assertions**: métodos usados para validar se o resultado de um teste é o esperado.
  - `assertEquals` / `assertNotEquals` → compara valores.
  - `assertNull` / `assertNotNull` → verifica se um objeto é nulo ou não.
  - `assertSame` / `assertNotSame` → compara se duas referências apontam para o **mesmo objeto** na memória (diferente de `equals`, que compara valor).
  - `assertTrue` / `assertFalse` → valida expressões booleanas.
- **Validação de exceções**: uso de `assertThrows` para garantir que um método lança a exceção esperada em cenários de erro.
- **Timeouts**: uso de `assertTimeout` para garantir que um trecho de código não ultrapasse um tempo de execução determinado.
- **Ordem de execução dos testes**: uso de `@TestMethodOrder` e `@Order` para definir a sequência em que os testes devem rodar (útil quando há dependência entre eles, embora o ideal seja sempre buscar testes independentes).
- **Ciclo de vida dos testes**: anotações como `@BeforeEach`, `@AfterEach`, `@BeforeAll` e `@AfterAll` para preparar e limpar o ambiente de teste.

### 2. TDD (Test Driven Development)

TDD é uma técnica de desenvolvimento onde o teste é escrito **antes** do código de produção. O ciclo se resume em três passos:

1. **Red** → escreve-se um teste que falha, pois a funcionalidade ainda não existe.
2. **Green** → escreve-se o código mínimo necessário para o teste passar.
3. **Refactor** → refatora-se o código mantendo os testes passando.

Esse ciclo ajuda a garantir cobertura de testes desde o início, reduz retrabalho e força um design mais simples e testável do código.

### 3. Mockito

O **Mockito** é o framework de mocks mais usado no ecossistema Java. Ele permite simular ("mockar") dependências externas para isolar a unidade que está sendo testada.

Principais conceitos:

- **Mock**: um objeto "falso" que simula o comportamento de uma dependência real (ex: um repositório de banco de dados), sem executar a lógica real.
- **Stub**: configuração do comportamento de um mock, definindo o que ele deve retornar quando um método é chamado (`when(...).thenReturn(...)`).
- **Verify**: usado para confirmar se um determinado método foi chamado, quantas vezes e com quais parâmetros (`verify(mock, times(1)).metodo()`).
- **Annotations do Mockito**: `@Mock`, `@InjectMocks` e `@ExtendWith(MockitoExtension.class)`, que automatizam a criação e injeção dos mocks nas classes testadas.

A combinação **JUnit + Mockito** permite testar uma classe de forma isolada, sem depender de banco de dados, APIs externas ou outros serviços.

### 4. Testes no Spring Boot

O Spring Boot possui um módulo próprio de testes (`spring-boot-starter-test`) que já traz JUnit 5, Mockito, AssertJ e outras bibliotecas prontas para uso. As principais anotações estudadas foram:

- `@SpringBootTest` → sobe o contexto completo da aplicação, usado geralmente em testes de integração mais amplos.
- `@WebMvcTest` → sobe apenas a camada web (controllers), ideal para testar endpoints REST sem carregar toda a aplicação.
- `@DataJpaTest` → sobe apenas a camada de persistência (repositories), usando um banco em memória para testar queries e regras do JPA.
- `@MockBean` → cria um mock de um bean gerenciado pelo Spring e o injeta no contexto da aplicação.

Com essas anotações, é possível testar cada camada da aplicação de forma isolada:

| Camada | O que é testado | Ferramenta principal |
|---|---|---|
| Repository | Consultas e regras de persistência | `@DataJpaTest` |
| Service | Regras de negócio | JUnit + Mockito |
| Controller | Endpoints REST, status HTTP, payloads | `@WebMvcTest` + MockMvc |

### 5. Testes de integração com TestContainers

O **TestContainers** é uma biblioteca que permite subir containers Docker (bancos de dados, filas, etc.) durante a execução dos testes, garantindo que o teste de integração rode contra uma instância **real** do banco de dados, e não um banco em memória (como H2), que pode se comportar de forma diferente do banco usado em produção (ex: MySQL, PostgreSQL).

Vantagens estudadas:

- Ambiente de teste mais próximo da produção.
- Container é criado e destruído automaticamente a cada execução, sem deixar resíduos.
- Evita o famoso problema de "funciona no H2, mas quebra em produção".

### 6. REST Assured

O **REST Assured** é uma biblioteca usada para testar APIs REST de forma fluente e legível, validando status code, corpo da resposta, headers e muito mais, com uma sintaxe baseada em BDD (`given().when().then()`).

Combinado com o Spring Boot e o TestContainers, ele permite escrever testes de integração completos, simulando requisições reais para os endpoints da aplicação, com o banco de dados rodando em um container Docker.

### 7. BDD (Behaviour Driven Development)

Além do TDD, o treinamento também aborda a escrita de testes no estilo **BDD**, organizando os testes em torno do comportamento esperado da aplicação (dado um cenário, quando uma ação ocorre, então um resultado é esperado), o que deixa os testes mais legíveis e próximos da linguagem de negócio.

---

## 🛠️ Tecnologias utilizadas

**Aplicação:**
- **Java**
- **Spring Boot** (Web, Data JPA, Validation)
- **MySQL** (banco de dados relacional)
- **Lombok**
- **springdoc-openapi** (documentação Swagger/OpenAPI)

**Testes:**
- **JUnit 5**
- **Mockito**
- **TestContainers** (`spring-boot-testcontainers` + módulo MySQL)
- **REST Assured**

**Build:**
- **Maven**
- **Docker** (usado pelo TestContainers para subir o banco de dados durante os testes de integração)

---

## ▶️ Como executar

### Rodando a aplicação

```bash
cd automated-tests-with-java
./mvnw spring-boot:run
```

Com a aplicação no ar, a documentação da API fica disponível via Swagger UI (rota padrão exposta pelo `springdoc-openapi`).

### Rodando os testes

Como o projeto é baseado em **Maven**, basta rodar:

```bash
./mvnw test
```

> Para os testes de integração com **TestContainers** (`PersonControllerIntegrationTest`, `SwaggerIntegrationTest`), é necessário ter o **Docker** instalado e em execução na máquina, pois o container do MySQL é criado e destruído automaticamente durante a execução dos testes.

---

## 🎯 Objetivo

Além de ser uma API funcional de CRUD de pessoas, o projeto serve como referência prática de como estruturar a suíte de testes de uma aplicação Spring Boot de ponta a ponta:

- Testes unitários com JUnit e Mockito na camada de serviço;
- Testes de persistência com `@DataJpaTest` na camada de repositório;
- Testes da camada web com `@WebMvcTest` na camada de controller;
- Testes de integração completos com banco MySQL real via TestContainers;
- Testes de API com REST Assured e validação da documentação Swagger.

## FOTOS DOS TESTES
<img width="783" height="233" alt="Captura de tela 2026-07-14 175252" src="https://github.com/user-attachments/assets/f0028d6a-5d20-4757-9282-260541fbfa55" />
<img width="775" height="218" alt="Captura de tela 2026-07-14 175315" src="https://github.com/user-attachments/assets/fb48aab7-5ed6-4afc-9fff-82b2510b8e27" />


