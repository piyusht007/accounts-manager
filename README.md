# accounts-manager
An account management solution. Its a spring boot application.

**Testing:**

JUnit tests along with Cucumber (Integration Tests) has been used to test the application scenarios.

**Running the application:**

Use following maven command to start the application:

`mvn spring-boot:run` (Execute the command on the parent folder i.e. **accounts-manager**)

**Running integration tests:**

Use following maven command to run integration tests:

`mvn test -P cucumber-integration-tests`

We can see the test reports on following location: 
**cucumber/test-reports/index.html**

**Sequence Diagram:** Sequence diagram to explain the account creation flow: src/main/resources/sequence_diagrams       


**Test the application using in following steps using any rest client:**

**1. Create a primary account:**

`POST http://localhost:8080/accounts-management/accounts`

**Request Payload:**
```
{
  "firstName":"Piyush",
  "lastName":"Tiwari",
  "initialCredit": "2000"
}
```
**Response:**
```
{
  "accountId": "6590657441575742343",
  "customerId": "4129870677315811375"
}
```
**2. Create a secondary account with the customer id received in above response:**

`POST http://localhost:8080/accounts-management/accounts/secondary`

**Request Payload:**
```
{
  "customerId": "4129870677315811375",
  "initialCredit": "2000"
}
```
**Response:**
```
{
  "accountId": "6094785286911000670",
  "customerId": "4129870677315811375"
}
```
**3. Fetch the account summary for above account id and customer id:** 

`GET http://localhost:8080/accounts-management/accounts/6094785286911000670/customer/4129870677315811375`

**Response:**
```
{
	  "accountSummaries": [{
	  "accountId": "6094785286911000670",
	  "customerId": "4129870677315811375",
	  "firstName": "Piyush",
	  "lastName": "Tiwari",
	  "balance": 2000.0,
	  "transactions": [{
		    "id": "48f97e76-18f2-44c6-84c0-8a6f186feb71",
		    "date": "2018-06-25T18:38:46.8015667",
		    "type": "CREDIT",
		    "amount": 2000.0
	   }]
	  }]
}
```









