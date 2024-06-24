# System Login Practice

System Login ทำขึ้นเพื่อศึกษา Spring boot, Restful, Database, Kafka และการทำงานของระบบ

## Structure Modules

* login (server backend, producer)
* email (consumer)
* common ทำหน้าที่เป็นตัวกลางเพื่อแชร์ข้อมูล email ให้กับ kafka

**หลักการทำงานคร่าวๆ** เมื่อ user ทำการ register เข้ามา server จะทำการบันทึกข้อมูลลง database ในขณะเดียวกัน server
ซึ่งเป็น producer จะทำการส่งข้อมูลที่เกี่ยวกับ email ไปให้กับทาง kafka และผู้รับ consumer ที่ทำหน้าที่ส่ง email
จะทำการส่ง email แทน และเมื่อ user ทำการยืนยันอีเมลเรียบร้อยแล้ว จึงจะสามารถเข้าสู่ระบบได้
## Features

* Java 17
* Spring Boot 3
* Rest API
* Json
* PostgreSQL
* Kafka
* Passay
* JWT
* Lombok
* Mapstruct

---

## Structure backend

    └── src/main/java/com/example

        /login
        ├── LoginApplication.java
        |
        ├── business
        |   ├── EmailBusiness.java
        |   └── UserBusiness.java
        |
        ├── config
        |   ├── token
        |   │   └── TokenFilter.java
        │   │
        |   ├── AppConfig.java
        |   ├── KafkaConfig.java
        |   └── SecurityConfig.java
        |
        ├── controller
        |   ├── api
        |   │   ├── TestApi.java
        |   │   └── UserController.java
        │   │
        |   └── request
        |       ├── ActivateRequest.java
        |       ├── ActivateResponse.java
        |       ├── ResendActivateEmailRequest.java
        |       ├── UserLoginRequest.java
        |       ├── UserLoginResponse.java
        |       ├── UserRegisterRequest.java
        |       └── UserRegisterResponse.java
        |
        ├── entityModel
        |   ├── BaseModel.java
        |   └── User.java
        |
        ├── exception
        |   ├── BaseException.java
        |   ├── EmailException.java
        |   ├── ExceptionAdvice.java
        |   ├── UserException.java
        |   └── ValidateException.java
        |
        ├── mapper
        |   └── UserMapper.java
        |
        ├── repository
        |   └── UserRepository.java
        |
        ├── service
        |   ├── TokenService.java
        |   ├── TokenServiceImp.java
        |   ├── UserService.java
        |   └── UserServiceImp.java
        |
        ├── util
        |   └── SecurityUtil.java

    └── src/main

        /resources
            ├── email
            |   └── email-activate-user.html
            |
            ├── static
            |   └── images
            |   └── SequenceDiagram
            |
            └── application.yml
---

## ตัวอย่าง Rest APIs

---
### Auth

| Method | Url                           | Sample Valid <br/>Request Body | Example of Invalid <br/>Response Body | Example of Invalid <br/>Response Body |
|:------:|-------------------------------|:------------------------------:|:-------------------------------------:|:-------------------------------------:|
|  POST  | /auth/register                |              JSON              |                 JSON                  |                 JSON                  |
|  POST  | /auth/login                   |              JSON              |                 JSON                  |                 JSON                  |
|  POST  | /auth/activate                |              JSON              |                 JSON                  |                 JSON                  |
|  POST  | /auth/resend-activation-email |              JSON              |              HttpStatus               |                 JSON                  |
|  GET   | /auth/refresh-token           |                                |                 JSON                  |                                       |


## Sequence Diagram

---
#### Sign Up -> /auth/register
![register](https://github.com/Toei56/system-login-backend/blob/40d34ed2cf641e94cccd6610cb8e22a0600d553a/src/main/resources/static/Sequence%20Diagram/Register.png)
#### Log In -> /auth/login
![login](https://github.com/Toei56/system-login-backend/blob/40d34ed2cf641e94cccd6610cb8e22a0600d553a/src/main/resources/static/Sequence%20Diagram/Login.png)
#### Activate -> /auth/activate
![activate](https://github.com/Toei56/system-login-backend/blob/40d34ed2cf641e94cccd6610cb8e22a0600d553a/src/main/resources/static/Sequence%20Diagram/Activate.png)
#### Resend Activation Email -> /auth/resend-activation-email
![resend-activation-email](https://github.com/Toei56/system-login-backend/blob/40d34ed2cf641e94cccd6610cb8e22a0600d553a/src/main/resources/static/Sequence%20Diagram/ResendActivateEmail.png)
#### Refresh token -> /auth/refresh-token
![refresh-token](https://github.com/Toei56/system-login-backend/blob/40d34ed2cf641e94cccd6610cb8e22a0600d553a/src/main/resources/static/Sequence%20Diagram/RefreshTonken.png)
