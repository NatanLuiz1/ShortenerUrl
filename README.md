# ShortenerUrl
Micro serviço desenvolvido baseado em tutoriais na internet.

## Resumo
O projeto consiste em receber uma URL de qualquer tamanho e receber uma URL de tamanho menor que direciona pro mesmo site que a URL recebida.

## Tecnologias Utilizadas e requisitos
Foram utilizadas as seguintes tecnologias:
- Java
- Redis
- Spring framework
- Junity
- Maven
- Postman (para testes de rotas)

## Utilização

Para utilização e teste do código é preciso seguir as seguintes etapas:

- Realizar um git clone do projeto;

- Abrir o pacote do projeto e realizar a build com o Maven em sua IDE de preferência; (Foi utilizado o IntelliJ no desenvolvimento)

- Instalar e Configurar o redis-server em sua máquina;

- Após iniciar o projeto em sua IDE, estará disponibilizado 3 rotas que são as seguintes:

#POST /

Deve receber um body no format-json passando da seguinte forma:  {"url": "urlteste.com"}

#GET /{id}

Ao ser chamado enviando o id(da shortURL) ele retorna o Url Original

#GET /metrics{id}

Ao ser chamado enviando o id(da shortURL) retorna a quantidade de acessos que a short url teve

## Créditos

Este projeto foi desenvolvido baseado em um tutorial do Denim Mazuck e do HallenWeaver, porém, foram-se encontrados alguns bugs e atualizações devido a versão antiga do Java que foram resolvidos por mim, implementei também algumas melhorias de códido e teste de uma das classes.


