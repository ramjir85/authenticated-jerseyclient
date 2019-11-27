# authenticated-jerseyclient
Springboot plug and play component for authenticated jersey calls

## Where to use? 

General use case with major REST call services is to make authenticated calls. Most of the time it is call to OpenAM or IDM or even LDAP 
services which authenticate and provide the user with a Baerer Token. 

> Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.......

This library works as a boiler plate code for all such REST calls that needs to be fetching such tokens and making the REST API calls to
the actual service.

## Benefits of using this?

- [ ] Extending services do not need to write boiler plate code for fetching the auth token.
- [ ] Easy plug and play with Springboot applications. Can be used as library for other applications.
- [ ] Configurable authentication end point.
- [ ] Uses Spring Caching and Eviction. Improves the performance as dont have to fetch auth token for each call. 
- [ ] Easily extendable with POST, GET and PUT methods. Also supports custom methods.
- [ ] Configurable TLS version.
- [ ] Easy to scale as this is built is microservice architecture can be containerized easily.

## How to use it? 

 Spring/Springboot Applications
   - Download the authenticated-jerseyclient()
   - Build the project with gradle
     ```
      ./gradlew clean build
     
     ```
   - In your application that needs to us this, add the below section in application.yml or application.properties
     ```
     restClient:
        authUrl: 'AUTHENTICATION_URL'
        appId: 'USER_NAME'
        secret: 'SECRET'
        tlsVersion: 'TLSv1'
     ```
    - Application (Main class) in the service include the below piece of code
      ```
      ..........................
      ..........................
      @SpringBootApplication
      **@Import(AuthenticatedRestClientApplication.class)**
      public class SnmpadaptorApplication {
      ..........................
      ..........................
      ..........................
      ..........................
      }
      ```
     


