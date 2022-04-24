# apirest-payment-provider-with-spring-boot-kafka-docker

Projeto desenvolvido para demonstrar a utilização do Kafka com Spring Boot e Docker.

# Desenho de Arquitetura da Api
<div align="center">
<img src="https://user-images.githubusercontent.com/5739362/164945727-07d727ae-01a9-4fc7-9275-a6d145e5bf8c.PNG"/>
</div>


### Utilização da aplicação:
A aplicação deve receber como entrada as seguintes informações conforme definido na especificação de cada rota descrito abaixo.:

# Endpoints disponíveis nessa apirest:

### POST: - CreatePayment
Endpoint: http://localhost:1020/api/v1/payments

##### Dados de entrada
```json
{
  "numero_pedido": "{{$guid}}",
  "descricao_pedido": "Geladeira Inox Eletrolux",
  "valor_total_pedido": 2998.00,
  "nome_cliente": "gisiona costa souza"
}
```

A aplicação deve responder com o seguinte retorno no modelo de informações:

##### Dados de saída
```json
{
  "codigo_pagamento": "dd11c68c-c6d4-4825-9b1d-d15803f237b5",
  "data_criacao_pagamento": "2022-04-23T16:30:13.738",
  "status_pagamento": "PROCESSANDO",
  "numero_pedido": "a734da73-9c0a-45a3-91dd-a0227017f9ab",
  "descricao_pedido": "Geladeira Inox Eletrolux",
  "valor_total_pedido": 2998.00,
  "nome_cliente": "gisiona costa souza"
}
```
### GET: - VerifyPayment
Endpoint: http://localhost:1020/api/v1/payments/8f000dc6-abdb-4c2c-8da4-945c3f50bcea
##### Dados de saída
```json
{
  "codigo_pagamento": "8f000dc6-abdb-4c2c-8da4-945c3f50bcea",
  "data_criacao_pagamento": "2022-04-23T16:32:37.111",
  "status_pagamento": "APROVADO",
  "numero_pedido": "869d42ce-d2a9-41cb-bd06-756464dc45f1",
  "descricao_pedido": "Geladeira Inox Eletrolux",
  "valor_total_pedido": 2998.00,
  "nome_cliente": "gisiona costa souza"
}
```
# Setup da apliacação
Instale as dependências
- Apache Maven 3.8.4 (https://maven.apache.org/download.cgi)
- Java jdk 1.8.0 (https://www.oracle.com/br/java/technologies/javase/javase8u211-later-archive-downloads.html)
- IDE de sua preferência (estou utilizando IntelliJ IDEA(https://www.jetbrains.com/pt-br/idea/))
- Docker (https://docs.docker.com/desktop/windows/install/)

# Executar o kafka em container docker
Para executar o kafka com o docker, basta acessar o diretório kafka na raiz do projeto e via bash de sua preferência digitar o comando abaixo.
- docker-compose -f kafka-docker-compose.yml up -d

Obs: Para executar o comando abaixo é preciso que tenha o docker instalado em sua maquina.

# Rode os testes
Você pode executar os testes com o comando a seguir pelo CLI:

```bash
$ mvn test
```
# Libs utilizadas no projeto
- lombok
- spring-kafka
- spring-boot-starter-web

# Container Docker
Utilizado o docker para rodar o kafka, zookeeper e kafdrop

# Credito de todo o conhecimento absorvido ao canal no youtube @BoraPraticar
- https://www.youtube.com/watch?v=Su49NMD9UiA
