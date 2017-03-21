## Description

This project shows how to use JCache (JSR-107) and Ehcache (is a backend store) for in-memory caching.

The project is based on Spring Framework (Spring Boot) + Spring Web MVC. As a build and dependency resolution system is used Maven.

In the project implemented the service that determines code of country based on client ip-address.

The process of determining code of country is request to external web-services that are provide such service. In particular, the request is sending to `freegeoip.net` and `ip-api.com` sites. This services provide a REST api.

Communicating with external web services is done with Apache HttpComponents library.

To decrease the response time, the service is requesting all services simultaneously. The first response will be result. If an error occurred or waiting time is out, then will be returned fallback code. All of this is achieved using `RxJava`.

### Caching

Because the request to the external web-service is time-consuming operation, so processing time can be reduced by using cache.

In the project using in-memory caching. The cache entry is a pair of client ip-address and code of country. Every success result puts in the cache. So, at next invoke the service, if the result already received earlier, then it will return back immediately from cache without any interactions with service.

The cache size may be configured externally using configuration file.

### REST API

#### Get code of country by the client ip-address

* **URL**
  
  `/api/v1/country/getCountryInfo`

* **Method:**
  
  `POST`
  
*  **URL Params**

   No

* **Data Params**
  
  Content-type is `application/json`. The json object should has following fields:
  * ipAddress: [string]

* **Success Response:**
  
  * **Code:** 200 OK <br />
    **Content:**
    
    {"countryCode":"it"}, where `it` is the code of country
 
* **Error Response:**

  * If at least one of the request parameter did not pass the validation <br />
    **Code:** 400 BAD_REQUEST <br />
    **Content:**
    The content is a standard Spring Boot error output, encoded in the json format.


* **Sample call:**

```
curl -i -X POST
    -H "Content-Type: application/json;charset=UTF-8"
    -d "{\"ipAddress\":\"81.30.12.20\"}
    "http://localhost:8080/api/v1/country/getCountryInfo"
```


## Using

Because it's Spring Boot based project, so to build standalone jar run following command:

```
mvn clean package
```

The jar-file may be found in the `target` folder

Next, you can run this jar as usually.
