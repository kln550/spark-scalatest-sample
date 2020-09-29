# scalatest-example

Simple introduction to the scalatest library.

## Running tests

Run `sbt test` to run the entire test suite.
sbt 

-Dclient_id=testUser -Dclient_secret=testSecrete -DloginUrl=http://localhost:8080/oauth2/token -DawsTokenUrl=http://localhost:8080/aws/generate_token


Add to pom.xml
--------------          
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.5.1</version>
</dependency>
<dependency>
    <groupId>org.skife.com.typesafe.config</groupId>
    <artifactId>typesafe-config</artifactId>
    <version>0.3.0</version>
</dependency>
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>1.7.1</version>
</dependency>

Copy RestClientUtil.java
------------------------
For your test

  var clientId = ""
  var clientSecret = ""
  var loginURL = ""
  var awsTokenUrl = ""
  
  
      clientId = configMap.get("client_id").fold("")(_.toString)
      clientSecret = configMap.get("client_secret").fold("")(_.toString)
      loginURL = configMap.get("loginUrl").fold("")(_.toString)
      awsTokenUrl = configMap.get("awsTokenUrl").fold("")(_.toString)
  
  