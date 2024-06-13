# System Login
---
เทคโนโลยี
Spring Boot, PostgreSQL, 
Kafka
Passay
Lombok
Mapstruct
JWT
Spring JPA
Spring Security
Spring Validation
Spring Web
Spring Mail (Java Mail Sender)
Spring Devtools
Spring Configuration Processor
---
com
  +- example
    +- login
      +- LoginApplication.java
      |
      +- business
      |  +- EmailBusiness.java
      |  +- UserBusiness.java
      |
      +- config
      |  +- token
      |    +- TokenFilter.java
      |  +- AppConfig.java
      |  +- KafkaConfig.java
      |  +- SecurityConfig.java
      |
      +- controller
      |  +- api
      |    +- TestApi.java
      |    +- UserController.java
      |  +- request
      |    +- ActivateRequest.java
      |    +- ActivateResponse.java
      |    +- ResendActivateEmailRequest.java
      |    +- UserLoginRequest.java
      |    +- UserLoginResponse.java
      |    +- UserRegisterRequest.java
      |    +- UserRegisterResponse.java
      |
      +- entityModel
      |  +- BaseModel.java
      |  +- User.java
      |
      +- exception
      |  +- BaseException.java
      |  +- EmailException.java
      |  +- ExceptionAdvice.java
      |  +- UserException.java
      |  +- ValidateException.java
      |
      +- mapper
      |  +- UserMapper.java
      |
      +- repository
      |  +- UserRepository.java
      |
      +- service
      |  +- TokenService.java
      |  +- TokenServiceImp.java
      |  +- UserService.java
      |  +- UserServiceImp.java
      |
      +- util
      |  +- SecurityUtil.java
---

