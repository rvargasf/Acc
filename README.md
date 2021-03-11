# Automation Practice
## Projeto de automação de testes


Tecnologias empregadas:
- [Selenium] - Automates browsers. That's it!
- [Junit] - JUnit is a simple framework to write repeatable tests
- [Maven] - Maven is based around the central concept of a build lifecycle
- [RestAssured] - Testing and validating REST services in Java


## Pré-requisitos
1. Apache Maven

## Execução


1. Faça o download do projeto em um diretório local

2. Acesse o diretório via terminal
   ```sh
   cd dir
   ```
3. Execute o comando
   ```sh
   npm test
   ```

4. Testes implementados

1 -  /produtos
- Validar inclusão de produto SEM token de autorização válido [Esperado status code: 401]
- Validar inclusão de produto COM token de autorização válido [Esperado status code: 201]
- Validar inclusão de produto duplicado [Esperado status code: 400]
- Validar dados obtidos de produto específico previamente cadastrado [Esperado status code: 200 | body idêntico]
- Listar todos os produtos cadastrados [Esperado status code: 200]
- Excluir produto previamente cadastrado [Esperado status code: 200]
- Validar Json Schema da requisição /produtos [Esperada compatibilidade com o .json] 

2 -  /usuarios
- Validar inclusão de novo usuário com token de autorização válido [Esperado status code: 201]
- Validar inclusão de usuário duplicado (e-mail) [Esperado status code: 400]
- Validar dados obtidos de usuário específico previamente cadastrado [Esperado status code: 200 | body idêntico]
- Listar todos os usuários cadastrados [Esperado status code: 200]
- Validar Json Schema da requisição /usuarios [Esperada compatibilidade com o .json] 
