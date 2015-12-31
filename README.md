# HMRC4J - A Java Library for the HMRC API

> DISCLAIMER : This Library is still a work-in-progress, and is still subject to further testing. It is not yet production-ready.

This library is provides a java-wrapper for HMRC's growing collection of APIs. For more information on what APIs are available, how to register as a developer and start using this library, take a look at the [HMRC Developer Hub](https://developer.service.hmrc.gov.uk/api-documentation). 

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

A user-restricted endpoint requires an OAuth 2.0 `access_token`. To get one, you need your user (taxpayer, organisation or agent) to authorize your application. This is done by opening a web browser and requesting the following URL:

`https://api.service.hmrc.gov.uk/oauth/authorize?response_type=code&client_id=[YOUR-CLIENT-ID]&scope=[REQUESTED-SCOPE]&state=[STATE]&redirect_uri=[YOUR-REDIRECT-URI]`

You user will be first prompted to log in to HMRC using their Government Gateway credentials, and then asked to authorize your application for the requested permissions.

Once the user has granted your application permission, HMRC will issue a 302 redirect to the provided `redirect_uri`. This redirect will contain a `code` query parameter.

If your application subclasses 'AbstractAuthorizeRedirectServlet' for servicing the redirect, hmrc4j will exchange the authorization code for an OAuth 2.0 token. This token can be persisted for each user according to your TokenStore implementation.

Your application should provide an instance of your Token Store, where HMRC4J can access and update your User's access token as required.

    final HmrcCredentials hmrcCredentials = new HmrcCredentials(CLIENT_ID, CLIENT_SECRET, SERVER_TOKEN);

    final Hmrc hmrc = HmrcFactory.createForUserRestrictedAccess(hmrcCredentials, tokenStore);

    final String message = hmrc.getAPI(HelloWorldAPI.class).sayHelloUser();

    Assert.assertEquals("Hello User", message);

### Sample Application

For an example of how to use HMRC4J, take a look at the `hmrc4j-sample` module. To run the sample application, clone the repository then run:

    > mvn clean install spring-boot:run
    
Once the application has started, open [http://localhost:8080/hmrc4j/sample](http://localhost:8080/hmrc4j/sample)`.