City Of Chicago Stats Tracker

This system is guaranteed to work on Linux distros and Max OSX. It has been developed primarly on Ubunut and Max OSX. No support for Windows.

Prerequisites:

  1. Install postgres (https://www.digitalocean.com/community/tutorials/how-to-install-and-use-postgresql-on-ubuntu-14-04)
  2. Install mongo (http://docs.mongodb.org/manual/tutorial/install-mongodb-on-ubuntu/)
  3. Import sql
            psql -h <hostname> -d city_of_chicago_stats_app -U username -f rest/src/main/resources/sql/city_of_chicago_stats_app.sql
  4. Import the mongo db collections located within the mongo_raw_data/import.txt file
  5. To build and run all tests input the follwoing command from the root directory:
            $ mvn clean test
  6. To run the middleware:
            $ cd rest
            $ mvn clean jetty:run

API Breakdown:

  * curl -k -d "username=<useremail>&password=<user_password>" https://middleware.chicagostatstracker.com:8443/socrata_chicago_data_access/authenticate
  * curl -k -H "X-Auth-Token: <useremail>:<time_stamp>:<md5_signature>" https://middleware.chicagostatstracker.com:8443/socrata_chicago_data_access/authenticate
  * curl -k -H "X-Auth-Token: <useremail>:<time_stamp>:<md5_signature>" https://middleware.chicagostatstracker.com:8443/socrata_chicago_data_access/user
  * curl -k -H "X-Auth-Token: <useremail>:<time_stamp>:<md5_signature>" https://middleware.chicagostatstracker.com:8443/socrata_chicago_data_access/mongo/findOne/crimes
  * curl -k -H "X-Auth-Token: <useremail>:<time_stamp>:<md5_signature>" https://middleware.chicagostatstracker.com:8443/socrata_chicago_data_access/mongo/findTop50/crimes
  * curl -k -H "X-Auth-Token: <useremail>:<time_stamp>:<md5_signature>" https://middleware.chicagostatstracker.com:8443/socrata_chicago_data_access/mongo/findAllByParams/crimes?params=%7B%22Beat%22:511%7D
  
Examples:
    curl -k -d "username=peter_pan@gmail.com&password=mun1ak0t!" https://middleware.chicagostatstracker.com:8443/socrata_chicago_data_access/authenticate

    Result:

    {"token":"peter_pan@gmail.com:1426455701633:da663433200dbfa89d2ac55a70ecf504"}

    curl -k -H "X-Auth-Token: peter_pan@gmail.com:1426455701633:da663433200dbfa89d2ac55a70ecf504" https://middleware.chicagostatstracker.com:8443/socrata_chicago_data_access/authenticate

    Result:

    {"name":"peter_pan@gmail.com","roles":{"ROLE_USER":true}}

    curl -k -H "X-Auth-Token: peter_pan@gmail.com:1426455701633:da663433200dbfa89d2ac55a70ecf504" https://middleware.chicagostatstracker.com:8443/socrata_chicago_data_access/users

    Result:

        <html>
            <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Error 403 Access is denied</title>
            </head>
            <body><h2>HTTP ERROR 403</h2>
            <p>Problem accessing /socrata_chicago_data_access/users. Reason:
            <pre>    Access is denied</pre></p><hr><i><small>Powered by Jetty://</small></i><hr/>

            </body>
        </html>

    curl -k -H "X-Auth-Token: peter_pan@gmail.com:1426455701633:da663433200dbfa89d2ac55a70ecf504" https://middleware.chicagostatstracker.com:8443/socrata_chicago_data_access/mongo/findTop50/crimes

    [ { "ID" : 9969168 , "Case Number" : "HY158467" , "Date" : "02/20/2015 11:56:00 PM" , "Block" : "054XX W HADDON AVE" , "IUCR" : 1310 , "Primary Type" : "CRIMINAL DAMAGE" , "Description" : "TO PROPERTY" , "Location Description" : "RESIDENCE" , "Arrest" : "false" , "Domestic" : "false" , "Beat" : 1524 , "District" : 15 , "Ward" : 37 , "Community Area" : 25 , "FBI Code" : 14 , "X Coordinate" : 1139870 , "Y Coordinate" : 1907092 , "Year" : 2015 , "Updated On" : "02/27/2015 12:41:00 PM" , "Latitude" : 41.901162519 , "Longitude" : -87.761691621 , "Location" : "(41.901162519, -87.761691621)"} , { "ID" : 9969180 , "Case Number" : "HY158497" , "Date" : "02/20/2015 11:55:00 PM" , "Block" : "007XX N WELLS ST" , "IUCR" : 890 , "Primary Type" : "THEFT" , "Description" : "FROM BUILDING" , "Location Description" : "BAR OR TAVERN" , "Arrest" : "false" , "Domestic" : "false" , "Beat" : 1831 , "District" : 18 , "Ward" : 42 , "Community Area" : 8 , "FBI Code" : 6 , "X Coordinate" : 1174562 , "Y Coordinate" : 1905290 , "Year" : 2015 , "Updated On" : "02/27/2015 12:41:00 PM" , "Latitude" : 41.895512696 , "Longitude" : -87.634319131 , "Location" : "(41.895512696, -87.634319131)"} , { "ID" : 9969211 , "Case Number" : "HY158493" , "Date" : "02/20/2015 11:46:00 PM" , "Block" : "047XX S WALLACE ST" , "IUCR" : 3710 , "Primary Type" : "INTERFERENCE WITH PUBLIC OFFICER" , "Description" : "RESIST/OBSTRUCT/DISARM OFFICER" , "Location Description" : "SIDEWALK" , "Arrest" : "true" , "Domestic" : "false" , "Beat" : 935 , "District" : 9 , "Ward" : 11 , "Community Area" : 61 , "FBI Code" : 24 , "X Coordinate" : 1173045 , "Y Coordinate" : 1873701 , "Year" : 2015 , "Updated On" : "02/27/2015 12:41:00 PM" , "Latitude" : 41.808863794 , "Longitude" : -87.640826308 , "Location" : "(41.808863794, -87.640826308)"}, ...]
  
    curl -k -d "username=admin@chicagostatstracker.com&password=mun1ak0t!" https://middleware.chicagostatstracker.com:8443/socrata_chicago_data_access/authenticate

    Result:
  
    {"token":"admin@chicagostatstracker.com:1426456250606:5bf8e4d47dd500667d8f8a8166ac4f41"}

    curl -k -H "X-Auth-Token: admin@chicagostatstracker.com:1426456250606:5bf8e4d47dd500667d8f8a8166ac4f41" https://middleware.chicagostatstracker.com:8443/socrata_chicago_data_access/users

    Result:

    [{"id":1,"uuid":"7ce06e3f-4e75-4d7d-8b89-6cb1e279f8a7","password":"9005848241c320b36126bfbe0e788bf480bfed428e4cacb94788935b0e5fa63a24ae4dc1d9a49b6d","first_name":"root","last_name":"user","email":"admin@chicagostatstracker.com","enabled":true,"created_date":1423705217273},{"id":2,"uuid":"6396d58e-b83c-45e9-aacc-6dd133d6fd4a","password":"dfa0b002619f0bb2d9e8baf41008ce985c842e261f3a47d3ed90b09759e6b034c11a35df09ef85ea","first_name":"Tom","last_name":"Mitic","email":"tomislavmitic2012@u.northwestern.edu","enabled":true,"created_date":1424662168762},{"id":3,"uuid":"1173689d-bd0f-4ea8-a3fe-a0c747f75051","password":"aa8de1823ec287bcb86d70b19266495cd4785269b2fb4c519a3591530bd2bbcb2227b6886c30ad75","first_name":"Miffles","last_name":"McGoo","email":"miffles@gmail.com","enabled":true,"created_date":1426450171970},{"id":7,"uuid":"67653e10-bc83-4306-9074-567dcc596031","password":"b9db46ccdba399b5ab301e708e71b7fd013a082b10edde755503069a701b5f623abbeecc1cabe9b4","first_name":"Peter","last_name":"Pan","email":"peter_pan@gmail.com","enabled":true,"created_date":1426451816847}]

    Adding New Users:

    $ cd account_creator
    $ mvn exec:java -Dexec.args="Peter Pan peter_pan@gmail.com mun1ak0t! true ROLE_USER"

Get Front end running:

To create an ssl key at the command prompt type the following:

  1. openssl genrsa -out key.pem
  2. openssl req -new -key key.pem -out csr.pem
  3. openssl x509 -req -days 9999 -in csr.pem -signkey key.pem -out cert.pem
  4. rm csr.pem

Enter the following into your /etc/hosts file

  127.0.0.1 chicagostatstracker.com

To start the node server with javascript expanded

  1. npm install
  2. sudo node server.js expanded

IMPORTANT (BEFORE YOU DO ANY OF THE ABOVE)

I highly recommend using nvm to run node, follow the following instructions:

  1. curl https://raw.githubusercontent.com/creationix/nvm/v0.23.2/install.sh | bash
  2. nvm install 0.11.16
  3. in .nvm/nvm.sh add the following lines:

	alias node='$NVM_BIN/node'  
	alias npm='$NVM_BIN/npm'  

  4. in .bashrc add the following lines of code:

	export NVM_DIR="/home/tomislav/.nvm"  
	[ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"  
	. ~/.nvm/nvm.sh  
	alias sudo='sudo '
