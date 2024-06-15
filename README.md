# System Login Practice
System Login ทำขึ้นเพื่อศึกษา Spring boot, Restful, Database, Kafka และการทำงานของระบบ
## Structure Modules
* login (server backend, producer)
* email (consumer)
* common ทำหน้าที่เป็นตัวกลางเพื่อแชร์ข้อมูล email ให้กับ kafka

**หลักการทำงานคร่าวๆ** เมื่อ user ทำการ register เข้ามา server จะทำการบันทึกข้อมูลลง database ในขณะเดียวกัน server ซึ่งเป็น producer จะทำการส่งข้อมูลที่เกี่ยวกับ email ไปให้กับทาง kafka และผู้รับ consumer ที่ทำหน้าที่ส่ง email จะทำการส่ง email แทน และเมื่อ user ทำการยืนยันอีเมลเรียบร้อยแล้ว จึงจะสามารถเข้าสู่ระบบได้
## Features
* Java 17
* Spring Boot 3
* Rest API
* PostgreSQL
* Kafka
* Passay
* JWT
* Lombok
* Mapstruct
---
## Structure backend
    └── src ─ main ─ java ─ com ─ example

    ── login
       ├── LoginApplication.java
       |
       ├── business
       |   ├── EmailBusiness.java
       |   └── UserBusiness.java
       |
       ├── config
       |   └── token
       |   │   └── TokenFilter.java
       │   │
       |   ├── AppConfig.java
       |   ├── KafkaConfig.java
       |   └── SecurityConfig.java
       |
       ├── controller
       |   └── api
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
---
## Dependencies
PostgreSQL, JWT, Kafka, Passay, Mapstruct, Lombok, Spring JPA, Spring Security, Spring Validation, Spring Web, Spring Mail (Java Mail Sender), Spring Devtools, Spring Configuration Processor
### pom.xml
Spring JPA คือ
```
    <dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-jpa</artifactId>
	</dependency>
```
Spring Security คือ
```
    <dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-security</artifactId>
	</dependency>
    <dependency>
		<groupId>org.springframework.security</groupId>
		<artifactId>spring-security-test</artifactId>
		<scope>test</scope>
	</dependency>
```
Spring Validation ช่วยในการตรวจสอบความถูกต้องของข้อมูล
```
    <dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-validation</artifactId>
	</dependency>
```
Spring Web คือ
```
    <dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
```
Spring Mail (Java Mail Sender) คือ
```
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-mail</artifactId>
	</dependency>
```
Spring Devtools คือ
```
    <dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-devtools</artifactId>
		<scope>runtime</scope>
		<optional>true</optional>
	</dependency>
```
Spring Configuration Processor คือ
```
    <dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-configuration-processor</artifactId>
		<optional>true</optional>
	</dependency>
```
PostgreSQL (Spring PostgreSQL Driver) คือ
```
    <dependency>
		<groupId>org.postgresql</groupId>
		<artifactId>postgresql</artifactId>
		<scope>runtime</scope>
	</dependency>
```
Lombok คือ
```
    <dependency>
		<groupId>org.projectlombok</groupId>
		<artifactId>lombok</artifactId>
		<optional>true</optional>
	</dependency>
```
JWT คือ
```
    <dependency>
		<groupId>com.auth0</groupId>
		<artifactId>java-jwt</artifactId>
		<version>4.4.0</version>
	</dependency>
```
Kafka คือ
```
    <dependency>
		<groupId>org.springframework.kafka</groupId>
		<artifactId>spring-auth0</artifactId>
	</dependency>
    <dependency>
		<groupId>org.springframework.kafka</groupId>
		<artifactId>spring-kafka-test</artifactId>
	</dependency>
```
Passay คือ
```
    <dependency>
		<groupId>org.passay</groupId>
		<artifactId>passay</artifactId>
		<version>1.6.4</version>
	</dependency>
```
Mapstruct คือ
```
    <dependency>
		<groupId>org.mapstruct</groupId>
		<artifactId>mapstruct</artifactId>
		<version>${org.mapstruct.version}</version>
	</dependency>
	<dependency>
		<groupId>org.mapstruct</groupId>
		<artifactId>mapstruct-processor</artifactId>
		<version>${org.mapstruct.version}</version>
		<scope>provided</scope>
	</dependency>
```