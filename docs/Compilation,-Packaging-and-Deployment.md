# Compilation, Packaging and Deployment

Web Flow uses Maven for compilation and packaging. Java 11 or newer should be used to compile Web Flow.

Web Flow can be deployed to any Java web container (such as Tomcat) using war artifacts produced during compilation.

## Dependencies

In order to build Web Flow using Maven, following PowerAuth dependencies need to be satisfied:

* [powerauth-server](https://github.com/wultra/powerauth-server) - dependency **powerauth-rest-client-spring**
* [powerauth-push-server](https://github.com/wultra/powerauth-push-server) - dependency **powerauth-push-client**
* [powerauth-restful-integration](https://github.com/wultra/powerauth-restful-integration) - dependency **powerauth-restful-security-spring**
* [powerauth-crypto](https://github.com/wultra/powerauth-crypto) - dependency **powerauth-java-crypto**
* [lime-java-core](https://github.com/wultra/lime-java-core) - dependency **rest-model-base**

These projects should be installed into local Maven repository using the "install" goal before building Web Flow. This step may not be necessary in case these packages are already published in the public repository in their required versions.

Other dependencies are resolved automatically by Maven by downloading packages from public Maven repositories.

## Building Web Flow

Web Flow can be built using [the parent pom.xml file](../pom.xml) of project [powerauth-webflow](https://github.com/wultra/powerauth-webflow#docucheck-keep-link). Use the "package" goal to generate the war artifacts. The war files will be built in target subfolders of project folders. In case the compilation fails, check for missing PowerAuth dependencies to be installed.

## Deployment

Once you create a war package using steps above and set up database for Web Flow, you can deploy the war files to any Java web container, such as Tomcat or any Java EE server. You can remove the version from the war file for a nicer target URL in the container (e.g. rename powerauth-webflow-0.0.xx-SNAPSHOT.war to powerauth-webflow.war). After deployment, the client application frontend should be available at http[s]://host:port/powerauth-webflow-client.

The following war files need to be deployed for a fully functional demo of Web Flow:
* `powerauth-webflow.war` - the main Web Flow application
* `powerauth-nextstep.war` - the Next Step service
* `powerauth-data-adapter.war` - a Data Adapter for communication with client backends
* `powerauth-webflow-client.war` - demo client application

The whole installation process is described in the [Web Flow Installation Manual](./Web-Flow-Installation-Manual.md).

In case you want to use the Third Party Provider engine functionality, deploy the `powerauth-tpp-engine.war` file, too.

## Testing Web Flow

You can test the web flow demo application by navigating to: http://localhost:8080/powerauth-webflow-client

* Use the "Login" action to test the user authentication. The Data Adapter sample project uses "test" as password for any username.
* Use the "Authorization" action to test operation authorization. In order to test this action you will need to create an operation and obtain its operationId (using [Next Step REST API](./Next-Step-Server-REST-API-Reference.md#create-an-operation)).
