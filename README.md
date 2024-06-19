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
            └── application.yml
---

## ตัวอย่าง Rest APIs

---
### Auth

| Method | Url                           | Sample Valid <br/>Request Body | Example of invalid <br/>response body | Example of invalid <br/>response body |
|:------:|-------------------------------|:------------------------------:|:-------------------------------------:|:-------------------------------------:|
|  POST  | /auth/register                |              JSON              |                 JSON                  |                 JSON                  |
|  POST  | /auth/login                   |              JSON              |                 JSON                  |                 JSON                  |
|  POST  | /auth/activate                |              JSON              |                 JSON                  |                 JSON                  |
|  POST  | /auth/resend-activation-email |              JSON              |                                       |                 JSON                  |
|  GET   | /auth/refresh-token           |                                |                 JSON                  |                                       |


## ตัวอย่างเนื้อหาคำขอ JSON ที่ถูกต้อง

---
#### Sign Up -> /auth/register

ตัวอย่างเนื้อหาคำขอที่ถูกต้อง
```json
{
  "username": "Tonson",
  "email": "spxth5735@gmail.com",
  "password": "12345678",
  "phone_number": "0900000000"
}
```
ตัวอย่างเนื้อหาการตอบกลับที่ถูกต้อง
```json
{
  "username": "Tonson",
  "email": "spxth5735@gmail.com"
}
```
ตัวอย่างเนื้อหาการตอบกลับที่ไม่ถูกต้อง (ไม่ได้ใส่ username)
```json
{
  "timestamp": "2024-06-19T13:28:57.6911008",
  "status": 400,
  "error": "username : must not be empty"
}
```

#### Log In -> /auth/login

ตัวอย่างเนื้อหาคำขอที่ถูกต้อง
```json
{
  "email": "spxth5735@gmail.com",
  "password": "12345678"
}
```
ตัวอย่างเนื้อหาการตอบกลับที่ถูกต้อง
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJCYWNrZW5kU2VydmljZSIsInByaW5jaXBhbCI6MSwicm9sZSI6IlVTRVIiLCJleHAiOjE3MTg3ODIwMjZ9.084eDY-lf8-_UMNKHAQja-IxQse8VkiYPxLJ8S7yoTI"
}
```
ตัวอย่างเนื้อหาการตอบกลับที่ไม่ถูกต้อง (ใส่ email หรือ password ผิด)
```json
{
  "timestamp": "2024-06-19T13:56:22.6213411",
  "status": 404,
  "error": "user.login.fail"
}
```

#### Activate -> /auth/activate

ตัวอย่างเนื้อหาคำขอที่ถูกต้อง
```json
{
    "token": "2ovlunKx7j2BUUTXdYL3Yg6820h15J"
}
```
ตัวอย่างเนื้อหาการตอบกลับที่ถูกต้อง
```json
{
    "success": true
}
```
ตัวอย่างเนื้อหาการตอบกลับที่ไม่ถูกต้อง (token ผิด)
```json
{
    "timestamp": "2024-06-19T13:54:03.057719",
    "status": 400,
    "error": "user.activate.fail"
}
```

#### Resend Activation Email -> /auth/resend-activation-email

ตัวอย่างเนื้อหาคำขอที่ถูกต้อง
```json
{
    "email": "spxth5735@gmail.com"
}
```
ตัวอย่างเนื้อหาการตอบกลับที่ถูกต้อง
```
    StatusCode : 200 OK.
    
    ![email](https://github.com/Toei56/system-login-backend/blob/97559cd9ae5ad0b8dab56206bd91ebd12473c2d6/Screenshot%202024-06-19%20152108.png)
```
ตัวอย่างเนื้อหาการตอบกลับที่ไม่ถูกต้อง (ใส่ email ไม่ถูกต้อง)
```json
{
    "timestamp": "2024-06-19T13:43:23.1866123",
    "status": 404,
    "error": "user.resend.activation.fail"
}
```

#### Refresh token -> /auth/refresh-token

ตัวอย่างเนื้อหาการตอบกลับที่ถูกต้อง
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJCYWNrZW5kU2VydmljZSIsInByaW5jaXBhbCI6MSwicm9sZSI6IlVTRVIiLCJleHAiOjE3MTg3ODYxNjJ9.F6vezzXiyVZQzh043IqGYIREqh9vcvMGWQpRLkIdfek"
}
```
