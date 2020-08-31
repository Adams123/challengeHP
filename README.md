# Desafio Dextra: Potter API

Backend realizado para o desafio dextra

# Inicialização
## Docker
Para inicializar a aplicação em docker, use:
>docker pull adamssilva/hp_app:firsttry
>docker-compose up

A aplicação por padrão está em 0.0.0.0:3000. Um arquivo .env está disponível, contendo uma chave de autenticação da Potter API. Caso queira, pode substituir pela sua.

## Compilando o código
Requisitos:
 - maven
 - base postgres
 - docker (para testes, opcional)
Para compilar a API, basta executar 
> mvn install
> mvn spring-boot:run

Caso docker não esteja disponível, use
>mvn install -DskipTests

# A API
A API pode ser consultada acessando **/swagger-ui.html**.
Principais endpoints disponíveis:

 - GET /api/characters
 - GET /api/characters/{characterId}
 - POST /api/characters
 - PUT /api/characters
 - DELETE /api/characters/{characterId}
 - GET /api/houses
 - GET /api/houses/{houseId}
 - GET /api/spells
 - GET /api/spells/{spellId}
 - GET /api/sortingHat

# Packages principais

 - Spring boot
 - PostgresDB
 - OpenAPI
 - DBRider com Testcontainers e DBUnit
 - JUnit 5
 - FeignClient
