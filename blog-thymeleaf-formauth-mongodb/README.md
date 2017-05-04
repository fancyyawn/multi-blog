# simple blog with Spring

## tech stacks

* semantic UI
* thymeleaf template
* Spring Boot
* Spring Mvc
* Spring Security: form authentication
* Spring data mongodb: access mongodb
* mongodb
* gravatar: generate the avatar from email
* lombok: simplify the model definition
* dozer: bean copy 

## usage

### step1: config the mongodb database

* config location：`blog-thymeleaf-formauth-mongodb/blog-thymeleaf-formauth/src/main/resources/application.yaml`
* config item: `spring.data.mongodb.database: blog`

### step2: run the website
```sh
git clone https://github.com/fancyyawn/blog-thymeleaf-formauth-mongodb.git
cd blog-thymeleaf-formauth-mongodb
mvn spring-boot:run
```
## tips

### how to generate the spring boot project by Spring CLI

```sh
spring init --dependencies web,data-jpa blog/ 
```

### how to simplify model definition by lombok
```java
@Data
@Accessors(chain = true) 
public class User implements Serializable {
    private static final long serialVersionUID = -1575177945061174211L;

    private Long id;

    private String username;

    private String password;
    
    public static User mock(){
        return new User().setId(1L).setUsername('zhacker').setPassword('123456');
    }
}
```