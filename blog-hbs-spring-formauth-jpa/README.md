# simple blog with Spring

## tech stacks

* semantic UI
* handlebars template
* Spring Boot
* Spring Mvc
* Spring Security: form authentication
* Spring data jpa: access mysql
* mysql
* gravatar: generate the avatar from email
* lombok: simplify the model definition
* dozer: bean copy 

## usage

### step1: config the mysql connection string

* config location：`blog-hbs-spring-formauth-jpa/blog-hbs-formauth/src/main/resources/application.yaml`
* config item: `spring.datasource.url: jdbc:mysql://localhost:3306/blog?characterEncoding=UTF-8&useSSL=false`

### step2: run the website
```sh
git clone https://github.com/fancyyawn/blog-hbs-spring-formauth-jpa.git
cd blog-hbs-spring-formauth-jpa
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