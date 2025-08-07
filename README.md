## API de gestão de colaboradores SCCON - Spring Boot
API REST para gerenciar dados dos colaboradores da SCCON.
Pela API é possível criar, alterar, atualizar e deletar os dados dos colaboradores,
assim como calcular a idade e projeção de salário dos mesmos.

## Tecnologias Utilizadas
  - Java 21
  - Spring Boot 3.5.4
       - Spring Data JPA
       - Spring Validation
       - Spring Web
  - H2 Database
  - Lombok
  - OpenAPI
  - Flyway

- # Swagger com a documentação dos endpoints
  [Swagger da API REST](http://localhost:8080/swagger-ui/index.html)

## Configuração para Execução
## Pré-requisitos
- Java 21
- Maven

## Como Executar
- Clone o repositório:
  `git clone https://github.com/msvari/sccon-person.git`
  `cd sccon-person`

- Build o projeto:
  `maven clean install -f pom.xml`

- Executar diretamente pela IDE de preferência (Intellij, Eclipse, etc.)
- Clicar com botão direito na classe ExApplication.java e selecionar 'Run ExApplication.main()'

## Acesse o H2 Console no browser:

- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:exdb
- Usuário: sa
- Senha: 123456

