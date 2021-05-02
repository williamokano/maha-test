# Maha Backend Engineer Coding Challenge
This project consists on a checkout "microservice".

Given a list of products (or SKU's - Stock-keeping units), the service will calculate the total, apply the discounts and return the price of the checkout.

## Endpoints
| Method | Path      | Description                                                                   |
|--------|-----------|-------------------------------------------------------------------------------|
| POST   | /checkout | Given a list of SKU's, calculate the total of the checkout applying discounts |

**Request example**:
`curl -X POST http://localhost:8080/checkout -H "Content-Type: application/json" --data '["001", "002", "003"]'`

**Response example**:
```json
{"price": 230}
```

### Response Codes
| ResponseCode | Description         |
|--------------|---------------------|
| 201          | Price was calculate |
| 400          | Product not found   |
| 500          | Unhandled exception |

## How to run the application
I recommend using docker. You can simply build the image using `docker build -t maha .` and then run with `docker run -p 8080:8080 maha`.

Alternatively you can use gradle `./gradlew bootRun` or even creating your own jar: `./gradlew clean build` and then `java -jar build/libs/maha-test-0.0.1-SNAPSHOT.jar`

## Running the tests
Since it's gradle project, you can run with `./gradlew clean test`