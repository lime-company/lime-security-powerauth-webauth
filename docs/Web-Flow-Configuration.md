# PowerAuth Web Flow Configuration

This chapter describes configuration of Web Flow.

See the [installation guide](./Web-Flow-Installation-Manual.md#update-application-configurations) for instructions how to update the configuration properties.

## Web Flow Server

At minimum the following configuration properties should be updated based on deployment:
- `powerauth.dataAdapter.service.url` - Data Adapter service URL
- `powerauth.nextstep.service.url` - Next Step service URL
- `powerauth.service.url` - PowerAuth service URL
- `powerauth.push.service.url` - PowerAuth Push service URL
- `powerauth.webflow.page.ext-resources.location` - path to customized resources (use file:/path/to/your/ext-resources instead of classpath for location on disk)
- database configuration - see examples below

Complete configuration file:
```properties
# Allow externalization of properties using application-ext.properties
spring.profiles.active=ext

# Data Adapter Server Service URL
powerauth.dataAdapter.service.url=http://localhost:8080/powerauth-data-adapter

# Next Step Server Service URL
powerauth.nextstep.service.url=http://localhost:8080/powerauth-nextstep

# PowerAuth Server URL
powerauth.service.url=http://localhost:8080/powerauth-java-server/rest
powerauth.service.security.clientToken=
powerauth.service.security.clientSecret=
# Whether invalid SSL certificates should be accepted
powerauth.service.ssl.acceptInvalidSslCertificate=false

# PowerAuth Push Server URL
powerauth.push.service.url=http://localhost:8080/powerauth-push-server

# Dynamic CSS stylesheet URL
powerauth.webflow.page.title=PowerAuth Web Flow
powerauth.webflow.page.ext-resources.location=classpath:/static/resources/
powerauth.webflow.page.custom-css.url=

# Database Configuration - MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/powerauth
spring.datasource.username=powerauth
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.jpa.properties.hibernate.connection.characterEncoding=utf8
spring.jpa.properties.hibernate.connection.useUnicode=true

# Database Configuration - PostgreSQL
#spring.datasource.url=jdbc:postgresql://localhost:5432/powerauth
#spring.datasource.username=powerauth
#spring.datasource.password=powerauth
#spring.datasource.driver-class-name=org.postgresql.Driver
#spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false
#spring.jpa.properties.hibernate.connection.characterEncoding=utf8
#spring.jpa.properties.hibernate.connection.useUnicode=true

# Database Configuration - Oracle
#spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/powerauth
#spring.datasource.username=powerauth
#spring.datasource.password=
#spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
# The following property speeds up Spring Boot startup
#spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false

# Application Service Configuration
powerauth.webflow.service.applicationName=powerauth-webflow
powerauth.webflow.service.applicationDisplayName=PowerAuth Web Flow Server
powerauth.webflow.service.applicationEnvironment=

# Configuration of Offline Mode
powerauth.webflow.offlineMode.available=true

# Enable or disable operations support in PowerAuth server
powerauth.webflow.pa.operations.enabled=false

# Configuration of Android Security Warning
powerauth.webflow.android.showSecurityWarning=true

# Configuration of Optional User Password Encryption
powerauth.webflow.password.protection.type=NO_PROTECTION
powerauth.webflow.password.encryption.transformation=
powerauth.webflow.password.encryption.key=

# Configuration of authentication using temporary credentials
powerauth.webflow.authentication.allowTemporaryCredentials=false

# Configuration of Delay for Resending SMS in Milliseconds
powerauth.webflow.sms.resend.delayMs=60000

# Configuration of Delay for Showing Operation Timeout Warning in Milliseconds
powerauth.webflow.timeout.warning.delayMs=60000

# Configuration of Limit for Large Consent Panel in Number of Characters
powerauth.webflow.consent.limit.enabled=false
powerauth.webflow.consent.limit.characters=750

# Anti-fraud System Configuration
powerauth.webflow.afs.enabled=false
powerauth.webflow.afs.type=THREAT_MARK
powerauth.webflow.afs.detectIpAddress=false
powerauth.webflow.afs.forceIpv4=true
powerauth.webflow.afs.tm.cookies.deviceTag=
powerauth.webflow.afs.tm.cookies.sessionSid=

# User Input Validation
powerauth.webflow.input.username.maxLength=256
powerauth.webflow.input.password.maxLength=128
powerauth.webflow.input.smsOtp.maxLength=8

# Configuration of CORS Requests for Client Certificate Verification
powerauth.webflow.security.cors.enabled=false
powerauth.webflow.security.cors.allowOrigin=https://localhost.cz

# Set JMX default domain in case JMX is enabled, otherwise the application startup fails due to clash in JMX bean names
spring.jmx.default-domain=powerauth-webflow

# Set Jackson date format
spring.jackson.date-format=yyyy-MM-dd'T'HH:mm:ssZ

# Disable open session in view to avoid startup warning of Spring boot
spring.jpa.open-in-view=false

# Enable caching of static resources
spring.resources.cache.cachecontrol.max-age=86400

# Disable swagger-ui default petstore url
springdoc.swagger-ui.disable-swagger-default-url=true

# Set the Spring application name
spring.application.name=powerauth-webflow
```

Encryption of user passwords during transport (end-to-end encryption) can be configured using following properties:
```
# Configuration of Password Encryption
powerauth.webflow.password.protection.type=PASSWORD_ENCRYPTION_AES
powerauth.webflow.password.encryption.transformation=AES/CBC/PKCS7Padding
powerauth.webflow.password.encryption.key=[Secret Base64-encoded 32-bit key, you can generate it using code below and keep it secure]
```

The configuration specifies that user password should be encrypted using AES in CBC mode with PKCS#7 padding. 
The key is encoded using Base64 encoding and its size influences the strength of the encryption cipher. 
The cipher transformation can be configured using standard Java Cipher transformation definition.
See: https://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html

The random encryption key can be generated using following code:
```java
byte[] randomBytes = new byte[32];
new SecureRandom().nextBytes(randomBytes);
String encryptionKey = BaseEncoding.base64().encode(randomBytes);
```

The symmetric key is used by both Web Flow and by the remote system which needs to decrypt the password for verification.
For information about password decryption, see: [User Password Encryption And Decryption](./Data-Adapter-REST-API-Reference.md#user-password-encryption-and-decryption)

## Next Step Server
At minimum the following configuration properties should be updated based on deployment:
- `powerauth.nextstep.operation.expirationTimeInSeconds` - operation expiration time in seconds
- `powerauth.dataAdapter.service.url` - Data Adapter service URL
- `powerauth.service.url` - PowerAuth service URL
- database configuration - see examples below

Complete configuration file:
```properties
# Allow externalization of properties using application-ext.properties
spring.profiles.active=ext

# Database Configuration - MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/powerauth
spring.datasource.username=powerauth
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.jpa.properties.hibernate.connection.characterEncoding=utf8
spring.jpa.properties.hibernate.connection.useUnicode=true

# Database Configuration - PostgreSQL
#spring.datasource.url=jdbc:postgresql://localhost:5432/powerauth
#spring.datasource.username=powerauth
#spring.datasource.password=
#spring.datasource.driver-class-name=org.postgresql.Driver
#spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false
#spring.jpa.properties.hibernate.connection.characterEncoding=utf8
#spring.jpa.properties.hibernate.connection.useUnicode=true

# Database Configuration - Oracle
#spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/powerauth
#spring.datasource.username=powerauth
#spring.datasource.password=
#spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
# The following property speeds up Spring Boot startup
#spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false

# Data Adapter Server Service URL
powerauth.dataAdapter.service.url=http://localhost:8080/powerauth-data-adapter

# PowerAuth Server URL
powerauth.service.url=http://localhost:8080/powerauth-java-server/rest
powerauth.service.security.clientToken=
powerauth.service.security.clientSecret=
# Whether invalid SSL certificates should be accepted
powerauth.service.ssl.acceptInvalidSslCertificate=false

# Operation expiration time in seconds
powerauth.nextstep.operation.expirationTimeInSeconds=300

# Use original username for a removed credential when the credential is recreated
powerauth.nextstep.identity.credential.useOriginalUsername=false

# Maximum number of attempts when generating username
powerauth.nextstep.identity.credential.generateUsernameMaxAttempts=100

# Enable or disable operations support in PowerAuth server
powerauth.nextstep.pa.operations.enabled=false

# Key used for end-to-end encryption of credentials
powerauth.nextstep.e2eEncryption.key=

# Key used for database record encryption
powerauth.nextstep.db.master.encryption.key=

# Application Service Configuration
powerauth.nextstep.service.applicationName=powerauth-nextstep
powerauth.nextstep.service.applicationDisplayName=PowerAuth Next Step Server
powerauth.nextstep.service.applicationEnvironment=

# Disable new Hibernate ID generators
spring.jpa.hibernate.use-new-id-generator-mappings=false

# Set JMX default domain in case JMX is enabled, otherwise the application startup fails due to clash in JMX bean names
spring.jmx.default-domain=powerauth-nextstep

# Set Jackson date format
spring.jackson.date-format=yyyy-MM-dd'T'HH:mm:ssZ

# Disable open session in view to avoid startup warning of Spring boot
spring.jpa.open-in-view=false

# Disable swagger-ui default petstore url
springdoc.swagger-ui.disable-swagger-default-url=true

# Set default media type for responses in REST API documentation
springdoc.default-produces-media-type=application/json

# Set the Spring application name
spring.application.name=powerauth-nextstep
```

Encryption of user passwords during transport (end-to-end encryption) can be configured using following property:
```properties
powerauth.nextstep.e2eEncryption.key=[Secret Base64-encoded 32-bit key, same as the key used in Web Flow]
```

Encryption of sensitive database records can be configured using following property:

```properties
powerauth.nextstep.db.master.encryption.key=[Secret Base64-encoded 32-bit key, you can generate it using code below and keep it secure]
```

The random encryption key can be generated using following code:
```java
byte[] randomBytes = new byte[32];
new SecureRandom().nextBytes(randomBytes);
String encryptionKey = BaseEncoding.base64().encode(randomBytes);
```

<!-- begin box warning -->
Do not use the same key for end-to-encryption and database record encryption. Store the keys securely, ideally using a vault mechanism._
<!-- end -->

## Data Adapter
At minimum the following configuration properties should be updated based on deployment:
- `powerauth.authorization.sms-otp.expiration-time-in-second` - SMS OTP operation expiration time in seconds
- `powerauth.authorization.sms-otp.max-verify-tries-per-message` - maximum number of attempts for SMS OTP verification
- database configuration - see examples below

Complete configuration file:
```properties
# Allow externalization of properties using application-ext.properties
spring.profiles.active=ext

# Database Configuration - MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/powerauth
spring.datasource.username=powerauth
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.jpa.properties.hibernate.connection.characterEncoding=utf8
spring.jpa.properties.hibernate.connection.useUnicode=true
#spring.jpa.properties.hibernate.connection.CharSet=utf8mb4

# Database Configuration - PostgreSQL
#spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
#spring.datasource.username=powerauth
#spring.datasource.password=
#spring.datasource.driver-class-name=org.postgresql.Driver
#spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false
#spring.jpa.properties.hibernate.connection.characterEncoding=utf8
#spring.jpa.properties.hibernate.connection.useUnicode=true

# Database Configuration - Oracle
#spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/powerauth
#spring.datasource.username=powerauth
#spring.datasource.password=
#spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
# The following property speeds up Spring Boot startup
#spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false

# SMS OTP expiration time in seconds
powerauth.authorization.sms-otp.expiration-time-in-seconds=300
# Maximum number of tries to verify a SMS OTP authorization code
powerauth.authorization.sms-otp.max-verify-tries-per-message=5

# Set JMX default domain in case JMX is enabled, otherwise the application startup fails due to clash in JMX bean names
spring.jmx.default-domain=powerauth-data-adapter

# Application Service Configuration
powerauth.dataAdapter.service.applicationName=powerauth-data-adapter
powerauth.dataAdapter.service.applicationDisplayName=PowerAuth Data Adapter
powerauth.dataAdapter.service.applicationEnvironment=

# Disable open session in view to avoid startup warning of Spring boot
spring.jpa.open-in-view=false

# Disable swagger-ui default petstore url
springdoc.swagger-ui.disable-swagger-default-url=true
```

## Web Flow Client
At minimum the following configuration properties should be updated based on deployment:
- `powerauth.webflow.service.url` - Web Flow service URL
- `powerauth.webflow.service.oauth.authorizeUrl` - OAuth 2.0 authorize endpoint
- `powerauth.webflow.service.oauth.tokenUrl` - OAuth 2.0 token endpoint
- `powerauth.webflow.service.oauth.clientId` - OAuth 2.0 client ID
- `powerauth.webflow.service.oauth.clientSecret` OAuth 2.0 client secret
- `powerauth.nextstep.service.url` - Next Step service URL

Complete configuration file:
```properties
# Web Flow Base URL Configuration
powerauth.webflow.service.url=http://localhost:8080/powerauth-webflow
powerauth.webflow.service.oauth.authorizeUrl=http://localhost:8080/powerauth-webflow/oauth/authorize
powerauth.webflow.service.oauth.tokenUrl=http://localhost:8080/powerauth-webflow/oauth/token
powerauth.webflow.service.oauth.clientId=democlient
powerauth.webflow.service.oauth.clientSecret=changeme

# Next Step Server Service URL
powerauth.nextstep.service.url=http://localhost:8080/powerauth-nextstep

# Whether invalid SSL certificates should be accepted
powerauth.service.ssl.acceptInvalidSslCertificate=false

# Application Service Configuration
powerauth.webflow.client.service.applicationName=powerauth-webflow-client
powerauth.webflow.client.service.applicationDisplayName=PowerAuth Web Flow Client
powerauth.webflow.client.service.applicationEnvironment=

# Set JMX default domain in case JMX is enabled, otherwise the application startup fails due to clash in JMX bean names
spring.jmx.default-domain=powerauth-webflow-client

# Set Jackson date format
spring.jackson.date-format=yyyy-MM-dd'T'HH:mm:ssZ

# Disable open session in view to avoid startup warning of Spring boot
spring.jpa.open-in-view=false

# Enable hidden method filter for DELETE method
spring.mvc.hiddenmethod.filter.enabled=true

# Disable harmless warning from Hikari Data Source during startup during H2 database driver initialization
logging.level.com.zaxxer.hikari.util.DriverDataSource=ERROR

# Enable caching of static resources
spring.resources.cache.cachecontrol.max-age=86400
```

## OAuth 2.0 Client Configuration

The OAuth 2.0 client requires following configuration:
- `client_id` - an identifier of the OAuth 2.0 client, choose the name so that it describes the client purpose
- `client_secret` - secret used for authentication of the OAuth 2.0 client, use BCrypt to encrypt the secret
- `scope` - OAuth 2.0 scopes supported by this client
- `authorized_grant_types` - OAuth 2.0 grant types, use `authorization_code` for typical deployment
- `web_server_redirect_url` - comma separated list of all redirect URLs after completion of OAuth 2.0 protocol
- `additional_information` - additional information for this client, use `{}` for no additional information
- `autoapprove` - use `true` value because consent page is displayed by Web Flow separately and is not handled by Spring OAuth 2.0 support

```sql
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, web_server_redirect_uri, additional_information, autoapprove)
VALUES ('democlient', '$2a$12$MkYsT5igDXSDgRwyDVz1B.93h8F81E4GZJd/spy/1vhjM4CJgeed.', 'profile,aisp,pisp', 'authorization_code', 'http://localhost:8080/powerauth-webflow-client/connect/demo', '{}', 'true');
```

Note: bcrypt('changeme', 12) => '$2a$12$MkYsT5igDXSDgRwyDVz1B.93h8F81E4GZJd/spy/1vhjM4CJgeed.'

You can use [htpasswd](https://httpd.apache.org/docs/2.4/programs/htpasswd.html) from Apache HTTP server to generate bcrypt hashes.

## AFS configuration

Anti-fraud system integration needs to be configured in table `ns_operation_config`.

Following parameters are configured for each AFS configuration:
- `afs_config_id` - unique identifier of AFS configuration which is configured in `ns_operation_config` table to link operation with AFS configuration
- `js_snippet_url` - URL which should be used for executing JavaScript code from anti-fraud system
- `parameters` - reserved for future use, specify value `{}`

AFS also needs to be configured using application properties:
- `powerauth.webflow.afs.enabled` - enables AFS integration
- `powerauth.webflow.afs.type` - AFS product type (e.g. `THREAT_MARK`)
- `powerauth.webflow.afs.detectIpAddress` - whether Web Flow should attempt to detect client IP addresses
- `powerauth.webflow.afs.forceIpv4` - whether only IPv4 addresses should be used when detecting client IP addresses
- `powerauth.webflow.afs.tm.cookies.deviceTag` - name of Threat Mark `deviceTag` cookie 
- `powerauth.webflow.afs.tm.cookies.sessionSid` - name of Threat Mark `sessionSid` cookie

## Authentication methods and next step definitions

Authentication methods and next step definitions need to be configured during Web Flow deployment.

See chapter [Configuring Next Step](Configuring-Next-Step.md) for details.

## Mobile token configuration

Mobile token needs to be enabled and configured in case it should be available for Web Flow. 

See chapter [Mobile Token Configuration](./Mobile-Token-Configuration.md) for details.
