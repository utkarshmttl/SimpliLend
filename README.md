# SimpliLend
Challenge number 2

SimpliLendDemo is the java package for the android app.
Server_side contains the model and controller files (part of MVC architecture based framework- CodeIgnitor) which are uploaded on the server.

app-debug.apk is the unsigned apk file which can be downloaded on an android device and then installed to see thw working of the application.

To check that the data stored in the database is not plaintext,
go to http://hrrealvalue.com/sldemo/index.php/controllerauth/getdetails
this calls a function which returns all the entries in the DB in the form of a JSON object.
