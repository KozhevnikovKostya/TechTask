## Bank
An application to work with the account

## Task

Написать приложение(Rest API), которое позволяет одновременно(параллельно) проводить операции по снятию денег/пополнению одного и того же счета.

Операции должны осуществляться с помощью http, состояние счетов хранить в БД, вести журнал операций в БД.

Написать тесты, которые позволят проверить данное приложение.

#### Technologies
+ Java 11 OpenJDK
+ Spring Boot 2.4.4
+ Gradle 6.3 + 
+ MySQL 5.x

#### Installation
1\. Install MySQL DB and create a new schema with name 'bank'

2\. Clone project:
```
https://github.com/KozhevnikovKostya/TechTask.git
```
#### Build
You can build the application with by running:
```
./gradlew clean build
```

#### Run
You can launch the application with by running:
```
./gradlew bootRun
java -jar crm-application.jar  --spring.datasource.username={login} --spring.datasource
.password={password} --jwt.secret={secret_key} --spring.datasource.jdbc-url={url}
```

##### Parameters
The parameters that you must specify to run the application:
+ spring.datasource.username - username for connection to DB;
+ spring.datasource.password - password for connection to DB;
+ spring.datasource.jdbc-url - url for connection to DB;
+ jwt.secret - the key used for signing and validation Client JWT tokens.