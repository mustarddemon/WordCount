#Solid Fire WordCount Program

##Specs

* Built with simple http server default with Java 7+ 
* Using Mason 
* Using Jackson for json parsing and deserializing

##Configuration
* Config is handled through config.json file in src/main/resources
* Can update port server runs against as well as number of threads used in processing word counter


##To Run
* Server exists in SimpleHttpServer class with basic Main() method run Server
* Uses port 8000

##Route Info

###POST /zipfile
* Attach zip file through multipart post request

###Results
* Returns top 10 common words

