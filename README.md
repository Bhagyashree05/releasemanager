# Release Manager

## Introduction

The Release Manager is a microservice application that manages and tracks the deployment versions of various services in a distributed system. It ensures that a single, overall version number (SystemVersion) is associated with the combination of service versions deployed at any given time. This helps in tracking which versions of services were running during specific events or user interactions.

## Features

- REST API for deploying services and retrieving service versions by SystemVersion.
- RabbitMQ integration to listen for deployment events from a message queue.
- In-memory H2 database for persistent storage.
- Global exception handling and logging.
- Unit and integration tests using JUnit 5 and Mockito.

## Endpoints

### POST /api/v1/deploy

Deploys a service and updates the SystemVersion if the service version changes.

**Request:**

```json
{
    "serviceName": "Service A", 
    "serviceVersion": 1 
}
```

**Response:**
```json
{
    "systemVersionNumber": 1
}
```


### GET api/v1/services?systemVersion=3

Returns a list of Services and their corresponding service version numbers deployed with the given SystemVersionNumber

**Response:**
```json
[
    {
        "serviceName": "Service A",
        "serviceVersion": 2
    },
    {
        "serviceName": "Service B",
        "serviceVersion": 1
    }
]
```


## Setting Up and Running

1. **Build the Project**:
    ```bash
    mvn clean install
    ```

2. **Run the Application**:
    ```bash
    mvn spring-boot:run
    ```

3. **Running Tests**:
    ```bash
    mvn test
    ```

## Error Handling

- **DeploymentException**: Thrown for invalid input, returns HTTP 400.
