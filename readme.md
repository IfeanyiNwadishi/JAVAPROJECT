Welcome to IAM project:
- It is an interactive console application that allows you to create,  delete,    modify, list all identities. Read to the User Guide.pdf for further help.


This application was build using JDK 1.8 

Step to run the application

Setup the database:
 - Download apache derby client
 - Create an instance of derby name it instance name =IAM with username="admin"
   and password="admin".
 - run /db-derby-10.13.1.1-bin/bin/startNetworkServer
 - Go to  iam-core/src/fr/epita/iam/config/config.properties 
    edit user value with the user name you used to create your derby schema 
    Do the same with password, the name if instance if you used your own values
 - Go to  iam-core/sql  run create_tables.sql on your instance IAM
 
 Run the application:
 - Go to iam-core/src/fr/epita/iam/launcher execute ConsoleLauncher.java
 
 Developers:
 - Go to iam_core/javadoc for documentation
 - Go to iam-core/test to run advanced test. The console application does not allows the user to add other attributes and addresses on identity but by running
 these tests you can see how to use the advanced options.
 
 The project is an open source and will be enhanced .Feel free to contribute!
   

 
 