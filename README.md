# Code assignment for Zemoga
By: Cristhian Cangrejo S

Hello guys from Zemoga, I have implemented the solution for the assignment based on the next frameworks:

- Spring Boot (JPA, Rest)
- Twitter4
- Java 11
- Swagger
- Maven

For testing I have used:

- SpringBoot test
- Mockito
- JUnit

# Run it!
As, the service is configured with spring boot, there is not dependency needed and just run as (by default 8080 port):

    mvn spring-boot:run
    
If you do not have maven, just run as:    
    
    java -jar <path/to/my/jar> --server.port=8080
    
As test suggests, it has two endpoints that you can find in [Swagger](http://127.0.0.1:8080/portfolio-web-app/swagger-ui/index.html?url=/portfolio-web-app/v3/api-docs&validatorUrl=) `http://127.0.0.1:8080/portfolio-web-app/swagger-ui/index.html?url=/portfolio-web-app/v3/api-docs&validatorUrl=` if deploy:
- GET: /v1/portfolio/{id}
- POST: /v1/portfolio/{id} There is an optional query param that can be set as v1/portfolio/1?numTweets=5

The web site is located in:

http://127.0.0.1:8080/portfolio-web-app/ 

Total time solved: 6h 
