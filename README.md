# DVDRentalServer
*This is a Movie Rental System for a local movie store. It consists of two sides, client and server running as seperate projects
*This part of th project is one part of a Movie rental System (DVDRentalClient + DVDRentalServer = Movie Rental System). 
*This is the Server side of the project which listens to client requests.
*When the server gets request from the client, itperforms what the client requested by interacting with the Database. The Database returns
 the request to the server which then sends back a response to the Client. 

====================================================================================================================
################ You need to Open both RentalServer and Rental Client as seperate Intellij projects ################
====================================================================================================================

Connecting Client with Server:
To create communication between Client and Server, certain drivers are needed

Required Drivers:
*rs2xml.jar
*ucanaccess-4.0.4
*commons-lang-2.6
*commons-logging-1.1.3
*hsqldb
*jackcess-2.1.11

First things first: 
*In order to execute this project, you first have to run the server (Run on the 'Server' file) 
*"waiting for client" will be displayed by the console 

The server consists of
*The data in the tables is read from "Customers", "Rentals" and "Movies" SER files
* An Access Database with DB tables
