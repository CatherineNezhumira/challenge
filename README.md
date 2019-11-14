This application is running a daemon thread, in which pings server every 5 seconds. 
It logs health check rate for the last 10 entries of ping result and also logs errors such as timeout and network error.

Ping responses are interpreted in several ways :
  - Healthy (server respond with "Magnificent!") - Status = Healthy
  - Incorrect response ("Processing failed) - Status = Failure
  - Timeout (Timeout is set to 5 seconds) - Status = Failure
  - Network errors (are not included in statistics calculation, but logs with error log level)
  
Logs can be viewed in service_healthcheck.log file.
  
To build jar run:
mvn clean package

To run jar:
java -jar target/saucelabs-challenge-1.0-SNAPSHOT.jar

P.S. I'm really sorry to do THIS in ServiceHealthCheckTest:37, I had not so much time left and did'nt come up with more elegant solution :(
