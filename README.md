# tinyservlet

run the follwing commands from your coomamnd prompt:
mvn clean package
mvn exec:java

TinyServer is now running on localhost:9999 adn ready to serve requests. Now you can see the login link. you can access login page by clicking
the login url. you need to enter any username and password to see the gretting page. A garbage collector for session is included which runs in every 
t seconds and can be confiured using application.properties. Hope that someone will find it useful.

TODO:
tiny jsp support with class loader. 
