# tiny servlet 
### simplest java servlet like system written in plain Java just for fun! 
### supports cookie, session, session timeout, jsp scriptlet

### System requirements to run this project
1. java 8 
2. maven 3.3.1+

### How to run this project

1. clone this repository
2. Open command line in the cloned directory
3. run the following commands from command line:
    
    mvn clean && mvn package && mvn exec:java@jspTranslatorRunner
    
    mvn clean && mvn package && mvn exec:java@tinyServerRunner
4. Open localhost:8000 in the browser

#### TinyServer is running at localhost:8000 and as a result you can see three links. you can access the login page by
#### clicking the login url. you need to enter a username and password to long in to the application and see the greeting page. you can use any login credentials you want.
#### TinyServer is bundled with a sample jsp page in the jsp folder. you can create your own jsp pages. Jsp pages are translated to servlet at compile time.

####Users can register by creating a new account using a name and password
####A garbage collector for session is included which runs in every t seconds and can be confiured using application.properties.

####TODO:

####jsp template language support.
####code generation at runtime
