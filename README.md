# apirest-payment-publisher-subscriber-with-spring-webflux-reactive

Projeto demo para testar alta performance de request response de forma assíncrona com spring webflux reactive.

# Desenho de Arquitetura da Api
<div align="center">
<img src="https://user-images.githubusercontent.com/5739362/164986078-9c158b48-86e7-4c4d-a9a9-dc0b402e858c.PNG"/>
</div>


### Utilização da aplicação:
A aplicação deve receber como entrada as seguintes informações conforme definido na especificação de cada rota descrito abaixo.:

# Endpoints disponíveis nessa apirest:

### POST: - Create Payment
Endpoint: http://localhost:7806/api/v1/payments/create-payment

##### Dados de entrada
```json
{
  "user_id": "{{$guid}}",
  "valor": 2998.00
}
```

A aplicação deve responder com o seguinte retorno no modelo de informações:

##### Dados de saída
```json
{
  "payment_id": "f4270cfa-ceef-4227-a82d-9554e24fbfd6",
  "valor": 20.00,
  "data": "2022-04-24T13:27:50.688",
  "user_id": "d321462a-7039-4174-849f-80a5c24fcf6e",
  "status_payment": "PENDING"
}
```
### GET: - Verify All Payments
Endpoint: http://localhost:7806/api/v1/payments
##### Dados de saída
```json
"9822213e-0db3-4f09-a0de-10bba7ffbe82"
```

### GET: - Verify Payments Users
Endpoint: http://localhost:7806/api/v1/payments/users?ids=2ff7c889-1c2e-425b-b333-9672fda5e712,9822213e-0db3-4f09-a0de-10bba7ffbe82
##### Dados de saída
```json
[
  {
    "payment_id": "c10cb539-153a-4c2c-8a65-ceda9a4f3803",
    "valor": 20.00,
    "data": "2022-04-24T13:34:29.997",
    "user_id": "9822213e-0db3-4f09-a0de-10bba7ffbe82",
    "status_payment": "APPROVED"
  },
  {
    "payment_id": "5facdb0a-0a58-4c35-82fe-88a8ed02f6de",
    "valor": 20.00,
    "data": "2022-04-24T13:35:45.99",
    "user_id": "2ff7c889-1c2e-425b-b333-9672fda5e712",
    "status_payment": "APPROVED"
  }
]
```

# Setup da apliacação
Instale as dependências
- Apache Maven 3.8.4 (https://maven.apache.org/download.cgi)
- Java jdk 1.8.0 (https://www.oracle.com/br/java/technologies/javase/javase8u211-later-archive-downloads.html)
- IDE de sua preferência (estou utilizando IntelliJ IDEA(https://www.jetbrains.com/pt-br/idea/))

# Rode os testes
Você pode executar os testes com o comando a seguir pelo CLI:

```bash
$ mvn test
```
# Libs utilizadas no projeto
- lombok
- spring-boot-starter-webflux

# Credito de todo o conhecimento absorvido ao canal no youtube @Full Cycle
- https://www.youtube.com/watch?v=7DgcCNn9mA8
