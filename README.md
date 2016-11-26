# tinyservlet: simplest java servlet like system written in plain Java with support for cookie, sessoin, session timeout support and jsp scriptlet

run the following command from your command prompt:

mvn clean && mvn package && mvn exec:java@jspTranslatorRunner && mvn exec:java@tinyServerRunner


TinyServer is running on localhost:9999 and ready to serve requests. Now you can see the login link. you can access login page by
clicking the url. you need to enter whatever username and password you would like in order to see the gretting page.
A garbage collector for session is included which runs in every t seconds and can be confiured using application.properties.
Hope that someone will find it useful.

TODO:

jsp template language support.
