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

**Sequence Diagram:** Sequence diagram to explain the account creation flow: src/main/resources/sequence-diagrams       


## Test the application in following steps using any rest client:

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
  "accountId": "1325248128977157752",
  "customerId": "2442828243899034888"
}
```
**2. Create a secondary account with the customer id received in above response:**

`POST http://localhost:8080/accounts-management/accounts/secondary`

**Request Payload:**
```
{
  "customerId": "2442828243899034888",
  "initialCredit": "2000"
}
```
**Response:**
```
{
  "accountId": "6038069432489713146",
  "customerId": "2442828243899034888"
}
```
**3. Fetch the account summary for above account id and customer id:** 

`GET http://localhost:8080/accounts-management/accounts/customer/2442828243899034888`

**Response:**
```
{
	"accountSummaries": [{
		"accountId": "1325248128977157752",
		"customerId": "2442828243899034888",
		"firstName": "Piyush",
		"lastName": "Tiwari",
		"balance": 2000.0,
		"transactions": [{
			"id": "b30b9c9c-3f8c-4f49-b444-3f011f1d19db",
			"date": "2018-06-25T20:19:50.5455667",
			"type": "CREDIT",
			"amount": 2000.0
		}]
	}, {
		"accountId": "6038069432489713146",
		"customerId": "2442828243899034888",
		"firstName": "Piyush",
		"lastName": "Tiwari",
		"balance": 2000.0,
		"transactions": [{
			"id": "56cf70f9-234b-4de5-8f00-2397f3b25f4f",
			"date": "2018-06-25T20:20:15.9405667",
			"type": "CREDIT",
			"amount": 2000.0
		}]
	}]
}
```









