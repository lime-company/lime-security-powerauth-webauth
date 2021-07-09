# Basic Definitions

This chapter describes basic definitions of terminology used during the Web Flow authentication and authorization process.

The terminology described below is used widely in documentation, Web Flow database model and Web Flow source code. Understanding of most of these terms is required when deploying Web Flow.

## General Terminology

### Authentication

Authentication is the process of verifying the identity of a user.

Web Flow authentication step identifies the user and stores the user identity as parameter `user ID` for the current operation.

_A typical example of authentication is signing in using a login form with username and password. Another example is signing in by specifying the username and verifying user password together with an OTP code delivered by SMS._ 

### Authorization

Authorization is the process of verifying permission granted by an authority.

Web Flow authorization step verifies that the user grants a permission to perform current operation.

_A typical example of authorization is verifying that user grants a permission to process a payment using PIN code/fingerprint/SMS code on a mobile device owned by the user._

### Federated authentication and authorization

Federated authentication and authorization allows user to become authenticated and authorize the operation in one system while accessing another system.

_Web Flow is used for authentication and authorization in order to secure another otherwise unprotected system._

### Authentication and authorization process

Authentication and authorization process in Web Flow is a process which includes one or more authentication steps. Given the security requirements of the operation different authentication and/or authorization steps are required.

_Example of a single step process (login):_
- _User authentication using a login form with username and password (non-SCA, see below)._
- _User authentication using username, password, and an OTP code (SCA, see below)._

_Example of a process with multiple steps (payment authorization):_
- _User authentication using a login form with username and password._
- _Review of payment details by the user with choice of next authorization step._
- _(option 1) Authorization of payment using SMS message with one time password (OTP) rewritten to a web form._
- _(option 2) Authorization of payment using fingerprint in application running on user mobile device (online mobile token authorization)._
- _(option 3) Authorization of payment using QR code with offline signature rewritten to a web form (offline mobile token authorization)._

In the example above, the authentication and authorization process is completed once all required steps succeed.  In case any of the steps fails, the whole process fails and operation is not authorized.

We also refer to this process as **authentication flow**, hence the name **Web Flow**.

### PSD2

On October 8, 2015, the European Parliament adopted the European Commission proposal to create safer and more innovative European payments (PSD2, Directive (EU) 2015/2366). 
The new rules aim to better protect consumers when they pay online, promote the development and use of innovative online and mobile payments such as through open banking, 
and make cross-border European payment services safer.

An important element of PSD2 is the requirement for strong customer authentication on the majority of electronic payments.

### Strong Customer Authentication (SCA)

Strong customer authentication (SCA) is a requirement of the EU Revised Directive on Payment Services (PSD2) on payment service providers within the European Economic Area. 
The SCA requirement comes into force from 14 September 2019. The requirement ensures that electronic payments are performed with multi-factor authentication, 
to increase the security of electronic payments. Physical card transactions already commonly have what could be termed strong customer authentication in the EU (Chip and PIN), 
but this has not generally been true for Internet transactions across the EU prior to the implementation of the requirement.

## Web Flow and Next Step Terminology

### Operation

A new operation is created in the Next Step application with every authentication process in Web Flow. The operation is mapped 1:1 to the OAuth 2.0 dance.

There are two possible outcomes of an operation:
- **Operation succeeds** - the HTTP session becomes authenticated and the user is redirected to the original application with a successful result
- **Operation fails** - the HTTP session is not authenticated, an error is displayed and the user is redirected to the original application with an error

The operation succeeds only when all required authentication/authorization steps are successfully completed within operation timeout.

The operation may fail due to different reasons, such as:
- An authentication/authorization step fails (e.g. signature is not valid).
- Maximum number of attempts is reached causing authentication method to fail.
- Operation times out.
- User cancels the operation.
- User refreshes the browser, navigates to another URL or closes the browser window. 

### Operation ID

Each operation is identified by a unique operation ID, which is typically stored in [UUID format](https://en.wikipedia.org/wiki/Universally_unique_identifier). The operation ID is used during the authentication process to access status of the operation and update the operation in Next Step Server.

### Operation name

When deploying Web Flow you can define various operation names, which identify the type of operation.

Examples:
- `login`
- `authorize_payment`
- `login_sca`
- `authorize_payment_sca`

There need to be different authentication/authorization steps defined for each operation name.

### Operation status

Operation status is the most current status of the operation. The operation status is continuously updated as user proceeds through different authentication/authorization steps. Operation detail is another term used for operation status.

### Authentication result

Authentication result is one of the following:
- `CONTINUE` - additional authentication steps need to be performed
- `FAILED` - authentication has failed
- `DONE` - authentication has succeeded

### Authentication step

Operation consists of multiple authentication/authorization steps. These steps are executed sequentially until there are no more steps to execute or the operation fails.

### Authentication step result

Result of the authentication step is one of the following:
- `CONFIRMED` - the authentication step has succeeded
- `CANCELED` - user canceled the authentication step
- `AUTH_FAILED` - the authentication step has failed, however the user can retry the step
- `AUTH_METHOD_FAILED` - the authentication method has failed and this step cannot be retried

### Operation data

Arbitrary data which is stored together with the operation. Web Flow does not try to interpret this data and only stores it in the database.

_For example a payment operation contains following data (as string value):_

```pre
"{\"amount\":100,\"currency\":\"CZK\",\"account\":\"238400856/0300\",\"note\":\"Utility Bill Payment - 05/2019\",\"dueDate\":\"2019-06-29\"}"
```

Since Web Flow version 0.20.0 the [suggested format of operation data is specified](./Off-line-Signatures-QR-Code.md#operation-data) to allow interpretation of data by Mobile token.

### Operation form data

Structured data which is used when displaying operation details. The structure of form data is documented [in Next Step documentation](./Next-Step-Server-REST-API-Reference.md#operation-formdata).

_For example a payment operation contains following form data:_

- _title: Confirm Payment_
- _greeting: Hello, please confirm following payment_
- _summary: Hello, please confirm payment 100 CZK to account 238400856/0300._
- _amount: 100 CZK_
- _account: 238400856/0300_
- _due date: 2019-06-29_
- _note: Utility Bill Payment - 05/2019_

### Operation form field attribute

A structured type of form field in operation form data:
- `AMOUNT`
- `NOTE`
- `BANK_ACCOUNT_CHOICE`
- `KEY_VALUE`
- `HEADING`
- `PARTY_INFO`

See [Next Step documentation](./Next-Step-Server-REST-API-Reference.md#operation-formdata).

### Operation history

Whenever operation progresses to the next step, previous status of operation is stored in operation history.

### Operation review

Operation review is a special non-SCA authentication step which handles review of operation form data and next authentication method choice. This step is executed after user is authenticated, and the next step is an authorization step.

### Organization

Organizations separate users into different segments, such as `RETAIL`, `SME` and so on. The organization ID is an identifier used to specify organization selected by the user in the first step of user authentication.
Each organization may use different user identifiers and authenticate against different systems. Such functionality is handled in the Data Adapter implementation.

### Authentication method

Each step has an associated authentication method which performs either authentication or authorization during the operation.

See chapter [Configuring Next Step](Configuring-Next-Step.md) for more details.

### Authentication method choice

When there are multiple choices available for the next authentication method the user is provided with a choice of the next authentication method. The chosen authentication method is stored for the current operation step.

### Authentication method downgrade

It is possible to downgrade an authentication method, e.g., when the user has a mobile token application configured, but for some reason prefers to use a one time password for authentication. In this case, the authentication method is downgraded from the `POWERAUTH_TOKEN` authentication method to the `SMS_KEY` authentication method.

### Authentication instrument

The user has a choice of using different authentication instruments (SMS key, mobile token, hardware token, etc.). The chosen authentication instrument influences how authentication / authorization is performed.

### Next step of an operation

Each operation consists of multiple steps. The next step of the operation is decided based on following inputs:
- Authentication step result of the current step.
- Next step server configuration (definition of next steps).
- Available authentication methods (some methods may be disabled or temporarily unavailable).
- Status of the operation (operation may time out, authentication method may fail because of too many attempts, etc.).

### Next Step -- step definitions

Before starting Next Step Server the next step definition has to be defined for all operation names. All steps are defined in database table `ns_step_definition`.

See chapter [Configuring Next Step](Configuring-Next-Step.md) for more details.

### Next Step -- user preferences

Next Step user preferences store configuration for different authentication methods, for instance an activation ID configured for the mobile token application.

### Next Step -- application

Next Step applications are applications which are authenticated against Next Step, such as an internet banking application, or an online shop application.

### Next Step -- user identity

Next Step user identity stores information about the user including the user identifier (parameter `user ID`) which uniquely identifies the user. Additional user information is stored with the user identity, such as credentials, one time passwords, contact information, user aliases for external systems, and user roles. The user identity can be managed by the Next Step application, or it can be managed externally, in this case Data Adapter proxy is enabled so that all user identity queries are performed using Data Adapter.

### Next Step -- authentication

When the user identity is managed by the Next Step application, Next Step provides RESTful services for authenticating users using credentials, one time passwords, or a combination of these two methods. Authentication results are stored in the Next Step database.

### Next Step -- credential hashing

Next Step application hashes the user credentials using the Argon2 hashing algorithm. The credential verification is performed by comparing the hash of the credential with the stored hash. The hashing algorithm parameters can be changed and in this case the credential hash is recreated with new parameters during the next user authentication and stored in the database.

### Next Step -- database record encryption

The database record encryption can be enabled for credentials. A symmetric encryption key must be configured, once it is configured and credential record encryption in database is enabled, the credentials are encrypted by Next Step. This functionality can be used together with credential hashing. When an encryption configuration change is detected, the credential record is encrypted once again using new parameters during the next user authentication and encrypted credential value is stored in the database.

### Next Step - end-to-end encryption

Credentials can be optionally encrypted when received via a RESTful API request or sent via a RESTful API response. A symmetric encryption key must be configured, once it is configured and end-to-end encryption is enabled, all credentials are sent and received in encrypted form.

### Next Step -- credential policy

Next Step credential policy describes requirements on stored credentials, such as required length, password generation rules, password validation rules, expiration time, password history check, etc. The credential policy can be referenced using credential policy name.

### Next Step -- credential definition

Next Step credential definition binds the Next Step application, credential policy, and hashing configuration. It configures credential parameters such as database record encryption, end-to-end encryption and Data Adapter proxy mode. The credential definition can be referenced using credential definition name.

### Next Step -- one time password policy

Next Step one time password policy describes requirements on stored one time passwords, such as required length, number of failed attempts, expiration time, etc. The one time password policy can be referenced using one time password policy name.

### Next Step -- one time password definition

Next Step one time password definition binds the Next Step application and the one time password policy. It configures one time password parameters such as database record encryption and Data Adapter proxy mode. The one time password definition can be referenced using one time password definition name.

### PowerAuth operations

Next Step operations may be complex and consist of multiple smaller indiviual operations. For instance, the PSD2 payment operation consists of two operations: login and payment approval. When these smaller operations are authenticated using PowerAuth protocol, each such operation can be stored in PowerAuth server. Next Step and Web Flow manage the PowerAuth operations when this functionality is enabled and update their status based on authentication result. Operation template must be configured in PowerAuth server for this use case.

_Note: PowerAuth operations use `operation type` instead of `operation name` to identify the type of the operation._

### Authorization failure count

A counter of failures during an authentication step.

### Maximum authorization failure count

Maximum number of allowed failures during an authentication step.

The maximum number of allowed failures is defined in two areas:
- Configuration per authentication method, actual number of remaining attempts is decremented with each failure.
- Each authentication method may provide number of remaining attempts based on information from remote backend.

The effective number of remaining attempts is the lower of the two above mentioned values.

### HTTP session

The HTTP session is used in Web Flow in following ways:
- A client may create an operation with operation data before the OAuth 2.0 authentication is started and store assigned operationId in HTTP session in the `operationId` attribute. This attribute is picked when authentication is started and Web Flow continues an already existing operation. In case the `operationId` attribute is not found, Web Flow creates a new login operation with default operation data.
- During the authentication process, the `PENDING_AUTH_OBJECT` attribute stored in HTTP session is updated with OAuth 2.0 `UserOperationAuthentication` token which contains the most current state of authentication.
- When the authentication process is successfully completed, the HTTP session becomes authenticated with the OAuth 2.0 `UserOperationAuthentication` token.
- When the authentication process fails, the `PENDING_AUTH_OBJECT` attribute is removed from HTTP session. The HTTP session does not become authenticated.

The HTTP session is also used for storing temporary data during operation.

### Web Flow operation to session mapping

Each operation has a related HTTP session which is stored in database table `wf_operation_session` which maps operations to HTTP sessions. The reason for this mapping is to prevent concurrent execution of operations within same HTTP session.

The main impact of verifying presence of operations within HTTP session is that a new operation cancels previous operation started within same HTTP session.

### Web socket session

Each operation which includes a PowerAuth mobile token authorization step has a Web Socket session which is open during this step. The Web Socket communication is used for nearly immediate response in the web browser when an operation is confirmed or canceled in the Mobile Token application.

### Resource localization

Web Flow contains message resources which can be localized to different languages.

### Resource translation

Web Flow supports translation of resources which contain references to values of operation form data. This process is called resource translation.

### Anti-fraud system integration

Anti-fraud System (AFS) integration is available for Web Flow. Web Flow triggers AFS actions during login
and approval steps both when the step is initialized and when step authentication is performed.
The AFS integration allows authentication step-down (e.g. using 1 factor instead of 2 factors or even no factors at all). 
The AFS is also informed about completed, timed out and interrupted operations. 
The communication with AFS is handled in Data Adapter. 