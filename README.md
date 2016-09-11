# SimpliLend
Challenge number 2

SimpliLendDemo is the java package for the android app.
Server_side contains the model and controller files (part of MVC architecture based framework- CodeIgnitor) which are uploaded on the server.

app-debug.apk is the unsigned apk file which can be downloaded on an android device and then installed to see thw working of the application.

To check that the data stored in the database is not plaintext,
go to http://hrrealvalue.com/sldemo/index.php/controllerauth/getdetails
this calls a function which returns all the entries in the DB in the form of a JSON object.

DB JSON dump-
```
{
  "details": [
    {
      "id": "1",
      "username": "804b33542c3172aa05608e9d079e2a31726ace6dd4c78a130707862d76fbd30c",
      "password": "a3c57abb4acde82491f5dceaa5a446d8f441581e11e924949fdaa92af6193430",
      "name": "Utkarsh Mittal"
    },
    {
      "id": "2",
      "username": "705db0603fd5431451dab1171b964b4bd575e2230f40f4c300d70df6e65f5f1c",
      "password": "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f",
      "name": "Awesome Guy"
    },
    {
      "id": "3",
      "username": "ba596ed5c84f2d6cb70a477fba788276aa3df93d55b766b019e79f108b38def8",
      "password": "e24df920078c3dd4e7e8d2442f00e5c9ab2a231bb3918d65cc50906e49ecaef4",
      "name": "arushi mittal"
    }
  ]
}
```
