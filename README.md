# tenera-weather-forecast

# Build Project Using Maven

generate executable packages (jar) for the project.


```bash
mvn clean package
```

## Create Docker Image
We can deploy our applications as containers using docker images. 

Create docker image using Dockerfile


```docker
docker build -t web-docker-image .
```

## Deploy Application Using Docker Compose 

```docker-compose 
docker-compose up -d 
```
Now the application is running on docker and will be available on
http://localhost:9090/

## REST API

The REST API to the Weather forecast service are described below.

View current weather
-
####Request
`GET /current?location=Berlin`
```
curl -i -H 'Accept:application/json' http://localhost:9090/current?location=Berlin
```
####Response
```
{
    "temp": 15.27,
    "pressure": 1011,
    "umbrella": false
}
```

View weather query history
-
####Request
`GET /history?location=Berlin`
```
curl -i -H 'Accept:application/json' http://localhost:9090/history?location=Berlin
```
####Response
```
{
    "avg_temp": 15.27,
    "avg_pressure": 1011,
    "history": [
        {
            "temp": 15.27,
            "pressure": 1011,
            "umbrella": false
        }
    ]
}
```

### Further enhancements

- we can enable Spring boot actuator to enable production ready features like 
    * Endpoints to monitor and interact with the application
    * configure log levels for logger  
- Exception handling can be improved 
- Bean level Validations can be added
 