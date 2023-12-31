# Customer Contacts Application

Java back-end to process customer contacts with emails and phones. Test task from Unibell company 
(Ufa, Russia)

To start application you need to have Java 17 Runtime virtual machine installed on your system, download 
customer-contacts-0.2.jar [link](https://github.com/CatOgre70/customer-contacts/blob/5550710efd6b2aea1f48880b7c40c20afb7ae46a/customer-contacts-0.2.jar) in current folder, and execute following command:

    java -jar customer-contacts-0.2.jar

Please don't forget to add following path variables:

spring.datasource.username=YOUR_DATABASE_ADMIN_USER_NAME  
spring.datasource.password=YOUR_DATABASE_PASSWORD  
spring.datasource.url=YOUR_DATABASE_URL (like jdbc:postgresql://DB_SERVER_IP_ADDRESS:5432/DATABASE_NAME)

# API end-points and Open API (Swagger) Documentation

Swagger-UI is available by address <http://YOU_APPLICATION_SERVER_IP_ADDRESS:8080/swagger-ui/index.html#/>

    /customer - CRUD API end-points to work with customer records
    /phones - CRUD API end-points to work with phones
    /emails - CRUD API end-points to work with emails

Detailed end points documentation is in Open API file [customer-contacts-v0.2.yaml](https://github.com/CatOgre70/customer-contacts/blob/master/customer-contacts-v0.2.yaml) 

# Working application

You can view API end points and try to test working application here:   
http://46.188.28.196:8080/swagger-ui/index.html#/

# Authors

[Vasily Demin](https://github.com/CatOgre70)