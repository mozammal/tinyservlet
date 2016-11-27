# tinyservlet: simplest java servlet like system written in plain Java with support for cookie, sessoin, session timeout support and jsp scriptlet

System requirements: java 8 and maven 3.3.1+

run the following command from your command prompt:

mvn clean && mvn package && mvn exec:java@jspTranslatorRunner && mvn exec:java@tinyServerRunner


TinyServer is running on localhost:9999 and ready to serve requests;as a result you can see three links. you can access the login page by
clicking the url. you need to enter a username and a password in order to see the gretting page, and you can use any login credentials.
TinyServer is bundled with sample jsp pages in jsp folder, and you can create your own jsp pages. Jsp pages are translated into servlet at compile time.

A garbage collector for session is included which runs in every t seconds and can be confiured using application.properties.
Hope that someone will find it useful.

TODO:

jsp template language support.
