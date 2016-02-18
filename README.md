# HMRC4J - A Java Library for the HMRC API

This library provides a java-wrapper for HMRC's growing collection of APIs. For more information on what APIs are available, how to register as a developer and start using this library, take a look at the [HMRC Developer Hub](https://developer.service.hmrc.gov.uk/api-documentation). 

## Benefits

- A simple, lightweight Java interface to HMRC's RESTful APIs
- Consistently defined boilerplate API implementations (including necessary exception handling)
- Integration with HMRC's key OAuth 2.0 operations (Authorize, Token, and Revoke endpoints)
- Automatic refreshing of expired OAuth 2.0 tokens prior to use (using AspectJ)
- Encapsulation of HMRC's OAuth 2.0 token via a simple [TokenStore](https://github.com/shaneagibson/hmrc4j/blob/master/hmrc4j/src/main/java/uk/co/epsilontechnologies/hmrc4j/core/oauth20/TokenStore.java) interface
- Implicit handling of HTTP Redirection
- An extensible architecture, so you can provide your own bespoke client implementations and still receive these same benefits

## Installation

You can integrate the latest version of hmrc4j into your Maven project by including the following dependency in your pom.xml file.

       <dependency>
           <groupId>uk.co.epsilontechnologies</groupId>
           <artifactId>hmrc4j</artifactId>
           <version>1.0.13</version>
       </dependency>

## Usage

### Accessing an Unrestricted Endpoint

An unrestricted endpoint is accessible without a `server_token` or OAuth 2.0 `access_token`.

    final Hmrc hmrc = HmrcFactory.createForUnrestrictedAccess();

    final String message = hmrc.getAPI(HelloWorldAPI.class).sayHelloWorld();

    Assert.assertEquals("Hello World", message);


### Accessing an Application-restricted Endpoint

An application-restricted endpoint uses your application's `server_token`, which is available (along with your `client_id` and `client_secret`) via the HMRC API Developer Hub.

    final HmrcCredentials hmrcCredentials = new HmrcCredentials(CLIENT_ID, CLIENT_SECRET, SERVER_TOKEN);

    final Hmrc hmrc = HmrcFactory.createForApplicationRestrictedAccess(hmrcCredentials);

    final String message = hmrc.getAPI(HelloWorldAPI.class).sayHelloApplication();

    Assert.assertEquals("Hello Application", message);


### Accessing a User-restricted Endpoint

A user-restricted endpoint requires an OAuth 2.0 `access_token`. To get one, you need your user (individual, organisation or agent) to authorize your application. This is done by opening a web browser and requesting the following URL:

    https://api.service.hmrc.gov.uk/oauth/authorize?response_type=code
                &client_id=[YOUR-CLIENT-ID]
                &scope=[REQUESTED-SCOPE]
                &state=[STATE]
                &redirect_uri=[YOUR-REDIRECT-URI]

You user will be first prompted to log in to HMRC using their Government Gateway credentials, and then asked to authorize your application for the requested permissions.

Once the user has granted your application permission, HMRC will issue a 302 redirect to the provided `redirect_uri`. This redirect will contain a `code` query parameter.

If your application subclasses 'AbstractAuthorizeRedirectServlet' for servicing the redirect, hmrc4j will exchange the authorization code for an OAuth 2.0 token. This token can be persisted for each user according to your TokenStore implementation.

Your application should provide an instance of your Token Store, where HMRC4J can access and update your User's access token as required.

    final HmrcCredentials hmrcCredentials = new HmrcCredentials(CLIENT_ID, CLIENT_SECRET, SERVER_TOKEN);

    final Hmrc hmrc = HmrcFactory.createForUserRestrictedAccess(hmrcCredentials, tokenStore);

    final String message = hmrc.getAPI(HelloWorldAPI.class).sayHelloUser();

    Assert.assertEquals("Hello User", message);
 
One key benefit of using HMRC4J is that you do not need to worry about your OAuth 2.0 access_token expiry or having to refresh a token prior to use - this is all handled behind the scenes by HMRC4J's core framework.

## Sample Applications

For an example of how to use HMRC4J, take a look at the `hmrc4j-samples` module. These sample applications are for illustrative purposes only (clearly not production-standard code!). They use [Spring Boot](http://projects.spring.io/spring-boot/) as the application framework. 

To run a sample application, clone the repository, change directory to the desired sample, then run:

    > mvn spring-boot:run
    
Once the application has started, open [http://localhost:8080/hmrc4j/sample](http://localhost:8080/hmrc4j/sample) in a browser.

NOTE: you will need to create a file under `/hmrc/hmrc-samples/[sample-project]/src/main/resources/config` called `secure.properties`, and add the following values:

    hmrc.credentials.client_id=[YOUR-CLIENT-ID]
    hmrc.credentials.client_secret=[YOUR-CLIENT-SECRET]
    hmrc.credentials.server_token=[YOUR-SERVER-TOKEN]
