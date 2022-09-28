# Bank-account-kata

### A SpringBoot application offering Rest Endpoints to carry out operations on your account


## This application offers 3 Endpoints allowing:
1. Make a deposit operation
 ```
 POST /api/bank-operation/deposit
 ```
1.1. if the amount > 0, clientId not blank and the client exist

Request body :

```json
{
"amount": 1000,
"clientId": "ayman.aboueloula"
}
```

Response : (Http status 200)
  ```json
  {
   "amount": 1000,
   "operation": "DEPOSIT",
   "operationDate": "2022-09-28T11:14:55.369869Z"
}
   ```
1.2. if the amount < 0 or the clientId is blank

Response:(Http status 400)

```json
  {
   "amount": "amount must be > 0",
   "clientId": "client id must be not null"
}
   ```

1.3. if the client doesn't exist:

Response: (Http status 404)
```
  Client does not exist
```


2. Make a withdrawal

 ```
 POST /api/bank-operation/withdrawal
 ```
2.1. if the amount > 0, clientId not blank and the client exist

Request body :

```json
{
"amount": 1000,
"clientId": "ayman.aboueloula"
}
```

Response : (Http status 200)
  ```json
  {
   "amount": -1000,
   "operation": "WITHDRAWAL",
   "operationDate": "2022-09-28T11:14:55.369869Z"
}
   ```

2.2. if balance is insufficient:

Response : (Http status 400)

```
insufficient balance
```

3. Check history of operations:

   ```
   GET /api/bank-operation/history/{clientId}
   ```

Response :(Http status 200)

   ```json
   {
   "operations": [
      {
         "amount": 1000,
         "operation": "DEPOSIT",
         "operationDate": "2022-09-28T11:14:55.369869Z"
      },
      {
         "amount": 1006,
         "operation": "DEPOSIT",
         "operationDate": "2022-09-28T11:29:32.980151800Z"
      },
      {
         "amount": -1000,
         "operation": "WITHDRAWAL",
         "operationDate": "2022-09-28T11:29:44.824651300Z"
      }
   ],
   "balance": 1006
}
   ```


## Application Endpoints could be accessed using OAS3 swagger Ui integrated into the Application:
http://localhost:8080/swagger-ui/index.html#/bank-account-controller once the application is launched.



## Testing:
The application is tested using Junit5, mockito and cucumber.
1. Unit tests mainly for the services, adapters and controllers
2. Integration test(cucumber tests) for use case scenarios, I used the prod database, to avoid mocking a database that is already mocked.
3. Test coverage greater than 91%

## Technical specifications:
1. We used a hexagonal architecture.
2. I preferred to store only the transaction history, to ensure that the deposit and withdrawal operations are transactional, and to calculate for each deposit or historical request transaction the new balance, which will increase the execution time, but in real situation using database, we could use two repositories, operationHistory(already implemented) and the account repository, and to annotate the services with @Transactional

## Application launch
```
mvn clean install
```
start the spring boot application via intellij(or via cmd line)