1. run this following syntax in cli

psql --username=<username> -d <database_name> -a -f <runallscript.sql>

ex :

psql --username=baseApp -d baseApp -a -f runallscript.sql

2. add this property into your server.xml (inside tag <Host>)

<Context docBase="D:/project/baseApp/fileStorage/" path="/otherResources"/>

3. now you can try to log in with user = ADMIN and pass = master

