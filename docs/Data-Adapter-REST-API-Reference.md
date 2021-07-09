# Data Adapter RESTful API Reference

<!-- template api -->

PowerAuth Web Flow server and Next Step server communicate with the Data Adapter via a REST API. This chapter defines the REST API implemented by Data Adapter and consumed by the Web Flow Server.

Following topics are covered in this chapter:
- [Status codes and error handling](#status-codes-and-error-handling)
- [Service status](#service-status)
- [User lookup](#user-lookup)
- [User authentication](#user-authentication)
- [User information](#user-information)
- [Initialize an authentication method](#initialize-an-authentication-method)
- [Decorate form data](#decorate-form-data)
- [Form data change notification](#formdata-change-notification)
- [Operation change notification](#operation-change-notification)
- [Create implicit login operation](#create-implicit-login-operation)
- [Operation mapping](#operation-mapping)
- [Generate and send authorization SMS](#generate-and-send-sms-authorization-code)
- [Send authorization SMS](#send-sms-authorization-code)
- [Verify authorization SMS code](#verify-authorization-sms-code)
- [Verify authorization SMS code and password](#verify-authorization-sms-code-and-password)
- [Verify a client TLS certificate](#verify-a-client-tls-certificate)
- [Initialize OAuth 2.0 consent form](#initialize-oauth-20-consent-form)
- [Create OAuth 2.0 consent form](#create-oauth-20-consent-form)
- [Validate OAuth 2.0 consent form](#validate-oauth-20-consent-form)
- [Save OAuth 2.0 consent form](#save-oauth-20-consent-form)
- [Execute an AFS action](#execute-an-afs-action)

You can access the generated REST API documentation in deployed Data Adapter:

```
http[s]://[host]:[port]/powerauth-data-adapter/swagger-ui.html
```

## Status Codes and Error Handling

PowerAuth compliant Data Adapter uses a unified format for error response body, accompanied with an appropriate HTTP status code. Besides the HTTP error codes that application server may return regardless of server application (such as 404 when resource is not found or 503 when server is down).

All error responses that are produced by the Data Adapter should have following body:

```json
{
    "status": "ERROR",
    "responseObject": {
        "code": "ERROR_CODE",
        "message": "ERROR_MESSAGE_I18N_KEY"
    }
}
```

Expected error messages are explained in details in individual sections.

## Service API

<!-- begin api GET /push/service/status -->
### Service Status

Get a system status response, with basic information about the running application.

<!-- begin remove -->
### Service status - parameters
<table>
<tr>
<td>Method</td>
<td><code>GET</code></td>
</tr>
<tr>
<td>Resource URI</td>
<td>/api/service/status</td>
</tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - system status successfully retrieved |
| 404  | Not found - application is not running |
| 500  | Server errors - unexpected server error |

#### Service status - response

```json
{
  "status": "OK",
  "responseObject": {
    "applicationName": "powerauth-data-adapter",
    "applicationDisplayName": "PowerAuth Data Adapter",
    "applicationEnvironment": "ENV1",
    "version": "0.22.0",
    "buildTime": "2019-06-21T12:49:22.959+0000",
    "timestamp": "2019-06-21T13:05:49.740+0000"
  }
}
```

- `applicationName` - Application name.
- `applicationDisplayName` - Application display name.
- `applicationEnvironment` - Application environment.
- `version` - Version of Data Adapter.
- `buildTime` - Time when the powerauth-data-adapter.war file was built.
- `timestamp` - Response timestamp.
<!-- end -->

## User API

<!-- begin api POST /api/auth/user/lookup -->
### User Lookup

Perform a lookup of user account. The specified username is converted into a user ID. The username and user ID values 
may be identical, however in typical deployments they are different. Client TLS certificate can also be used for user lookup.

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/auth/user/lookup</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes during authentication:

| Code | Description |
|------|-------------|
| 200  | OK response - user details were successfully retrieved |
| 400  | `INPUT_INVALID` - username has invalid format |
| 400  | `USER_NOT_FOUND` - user account does not exist |
| 400  | `REMOTE_ERROR` - communication with remote system failed |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### User lookup - request

```json
{
  "requestObject": {
    "username": "user1234",
    "organizationId": "RETAIL",
    "clientCertificate": "--- [CLIENT_CERTIFICATE] ---",
    "operationContext": {
      "id": "7662c638-9dc9-484c-a119-145b3685e623",
      "name": "authorize_payment",
      "data": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "formData": {
        "title": {
          "id": "operation.title",
          "message": "Confirm Payment"
        },
        "greeting": {
          "id": "operation.greeting",
          "message": "Hello,\nplease confirm following payment:"
        },
        "summary": {
          "id": "operation.summary",
          "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": "Amount",
            "valueFormatType": "AMOUNT",
            "formattedValues": {
              "amount": "100.00",
              "currency": "CZK"
            },
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": "To Account",
            "valueFormatType": "ACCOUNT",
            "formattedValues": {
              "value": "238400856/0300"
            },
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": "Due Date",
            "valueFormatType": "DATE",
            "formattedValues": {
              "value": "Jun 29, 2019"
            },
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": "Note",
            "valueFormatType": "TEXT",
            "formattedValues": {
              "value": "Utility Bill Payment - 05/2019"
            },
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "userInput": {}
      },
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": ["pisp"], 
        "extras": {
          "applicationOwner": "Wultra"
        }
      }
    }
  }
}
```

#### User lookup response - user account exists

```json
{
  "status": "OK",
  "responseObject": {
    "id": "12345678",
    "givenName": "John",
    "familyName": "Doe",
    "organizationId": "RETAIL",
    "accountStatus": "ACTIVE",
	"extras": {}  
  }
}
```

#### User lookup response - user account exists, however the account is not active (e.g. blocked)

```json
{
  "status": "OK",
  "responseObject": {
    "id": "12345678",
    "givenName": "John",
    "familyName": "Doe",
    "organizationId": "RETAIL",
    "accountStatus": "NOT_ACTIVE"
  }
}
```

#### User lookup response - user account does not exist

```json
{
  "status": "ERROR",
  "responseObject": {
    "code": "USER_NOT_FOUND",
    "message": "login.userNotFound",
    "validationErrors": null,
    "remainingAttempts": null
  }
}
```

Note that in SCA steps the implementations should hide this error, because the error could reveal which usernames are valid 
and which are not.

#### User lookup response - invalid input

```json
{
  "status": "ERROR",
  "responseObject": {
    "code": "INPUT_INVALID",
    "message": "login.username.empty",
    "validationErrors": [
      "login.username.empty.objectRequest.requestObject.username",
      "login.username.empty.requestObject.username",
      "login.username.empty.username",
      "login.username.empty.java.lang.String",
      "login.username.empty"
    ],
    "remainingAttempts": null
  }
}
```
<!-- end -->

<!-- begin api POST /api/auth/user/authenticate -->
### User Authentication

Perform an authentication operation with user ID and password. This method is not SCA compliant.

#### User authentication - parameters
<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/auth/user/authenticate</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes during authentication:

| Code | Description |
|------|-------------|
| 200  | OK response - user was successfully authenticated |
| 400  | `INPUT_INVALID` - username and/or password has invalid format, unsupported authentication type |
| 400  | `REMOTE_ERROR` - communication with remote system failed |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### User authentication - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "organizationId": "RETAIL",
    "password": "s3cret",
    "authenticationContext": {
      "passwordProtection": "NO_PROTECTION",
      "cipherTransformation": "",
      "smsAuthorizationResult": null
    },
    "operationContext": {
      "id": "447fbd89-6f46-46da-a573-ade4f3409c94",
      "name": "authorize_payment",
      "data": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "formData": {
        "title": {
          "id": "operation.title",
          "message": "Confirm Payment"
        },
        "greeting": {
          "id": "operation.greeting",
          "message": "Hello,\nplease confirm following payment:"
        },
        "summary": {
          "id": "operation.summary",
          "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": "Amount",
            "valueFormatType": "AMOUNT",
            "formattedValues": {
              "amount": "100.00",
              "currency": "CZK"
            },
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": "To Account",
            "valueFormatType": "ACCOUNT",
            "formattedValues": {
              "value": "238400856/0300"
            },
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": "Due Date",
            "valueFormatType": "DATE",
            "formattedValues": {
              "value": "Jun 29, 2019"
            },
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": "Note",
            "valueFormatType": "TEXT",
            "formattedValues": {
              "value": "Utility Bill Payment - 05/2019"
            },
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "userInput": {}
      },
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": ["pisp"], 
        "extras": {
          "applicationOwner": "Wultra"
        }
      }
    }
  }
}
```

#### User password encryption and decryption

The `NO_PROTECTION` password protection type is used for plain text password in request (sent by default). 

The password can be optionally encrypted. In this case the `passwordProtection` parameter provides information about
type of password encryption and the `cipherTransformation` parameter contains information about used cipher.

It is expected that the remote system which handles password verification decrypts the password and it is not required to decrypt 
the password in the Data Adapter itself. The following code sample decrypts the encrypted password for `PASSWORD_ENCRYPTION_AES` authentication type:

```java

// Get encryption key from the configuration and extract cipher transformation
// and encrypted password from the request.
String secretKeyBase64 = configuration.getSecretKey();
String cipherTransformation = request.getCipherTransformation();
String encryptedPassword = request.getPassword();

String originalPassword = decryptPassword(secretKeyBase64, cipherTransformation, encryptedpassword);

// ...

// Decryption method (missing the 'throws' clause).
private String decryptPassword(String secretKeyBase64, String cipherTransformation, String encryptedPassword) {
    // Read secret key from configuration
    byte[] secretKeyBytes = BaseEncoding.base64().decode(secretKeyBase64);
    SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "AES");

    // Extract IV and encrypted password and convert them to bytes
    String[] parts = encryptedPassword.split(":");
    if (parts.length != 2) {
        throw new IllegalArgumentException("Invalid request");
    }
    String ivBase64 = parts[0];
    byte[] ivBytes = BaseEncoding.base64().decode(ivBase64);
    String encryptedPasswordBase64 = parts[1];
    byte[] encryptedPasswordBytes = BaseEncoding.base64().decode(encryptedPasswordBase64);

    // Decrypt password using specified cipher transformation, extracted IV and encrypted password bytes
    Cipher cipher = Cipher.getInstance(cipherTransformation);
    cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(ivBytes));
    byte[] decryptedPasswordBytes = cipher.doFinal(encryptedPasswordBytes);
    return new String(decryptedPasswordBytes, StandardCharsets.UTF_8);
}

// ...

// Verify that decrypted password matches expected password
```

#### User authentication response - authentication succeeded

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "authenticationResult": "SUCCEEDED",
    "errorMessage": null,
    "remainingAttempts": null,
    "showRemainingAttempts": false,
    "accountStatus": "ACTIVE"
  }
}
```

#### User authentication response - authentication failed

This message should be sent when the Data Adapter receives a correct message, however the username and password combination is invalid.

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "authenticationResult": "FAILED",
    "errorMessage": "login.authenticationFailed",
    "remainingAttempts": null,
    "showRemainingAttempts": false,
    "accountStatus": "ACTIVE"
  }
}
```

#### User authentication response - input validation errors

This error should be returned when username or password format is invalid - either it contains unsupported characters or it is empty or too long. This error is also used when authentication type is not supported.

The sample response below is returned when password is empty. 

- Status Code: `400`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "ERROR",
  "responseObject": {
    "code": "INPUT_INVALID",
    "message": "login.password.empty",
    "validationErrors": [
      "login.password.empty.objectRequest.requestObject.password",
      "login.password.empty.requestObject.password",
      "login.password.empty.password",
      "login.password.empty.java.lang.String",
      "login.password.empty"
    ],
    "remainingAttempts": null
  }
}
```

For more information, see classes `AuthenticationRequestValidator` and `DefaultExceptionResolver`.

#### User authentication response - internal error

This error should be used for all unexpected errors.

- Status Code: `500`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "ERROR",
  "responseObject": {
    "code": "ERROR_GENERIC",
    "message": "Exception occurred at ...",
    "validationErrors": null,
    "remainingAttempts": null
  }
}
```
<!-- end -->

<!-- begin api POST /api/auth/user/info -->
### User Information

Fetch user details based on user ID.

### User information - parameters
<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/auth/user/info</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - user details have been successfully retrieved |
| 400  | `INPUT_INVALID` - request validation errors |
| 400  | `USER_NOT_FOUND` - user account does not exist |
| 400  | `REMOTE_ERROR` - communication with remote system failed |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### User information - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "organizationId": "RETAIL"
  }
}
```

#### User information response - user info successfully retrieved

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "id": "12345678",
    "givenName": "John",
    "familyName": "Doe",
    "organizationId": "RETAIL",
    "accountStatus": "ACTIVE"
  }
}
```
<!-- end -->

## Authentication Method API

<!-- begin api POST /api/auth/method/init -->
### Initialize an Authentication Method

Initialize an authentication method and set its parameters, e.g. client certificate configuration.

#### Initialize an authentication method - parameters

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/auth/method/init</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - user details have been successfully retrieved |
| 400  | `INPUT_INVALID` - request validation errors |
| 400  | `OPERATION_CONTEXT_INVALID` - invalid operation context |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### Initialize an authentication method - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": null,
    "organizationId": null,
    "authMethod": "LOGIN_SCA",
    "operationContext": {
      "id": "fd598c95-c7a4-4d66-9cf6-0262b8a71693",
      "name": "login_sca",
      "data": "A2",
      "externalTransactionId": null,
      "formData": {
        "title": {
          "id": "login.title",
          "message": "Login"
        },
        "greeting": {
          "id": "login.greeting",
          "message": "Hello,\nplease sign in."
        },
        "summary": {
          "id": "login.summary",
          "message": "Please confirm the login attempt."
        },
        "config": [],
        "banners": [],
        "parameters": [],
        "userInput": {}
      },
      "applicationContext": {
        "id": "democlient",
        "name": "democlient",
        "description": "App with client ID: democlient",
        "originalScopes": [
          "aisp"
        ],
        "extras": {}
      }
    }
  }
}
```

#### Initialize an authentication method - response - authentication method was initialized

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "certificateAuthenticationMode": "NOT_AVAILABLE",
    "certificateVerificationUrl": null
  }
}
```
<!-- end -->

## Operation API

<!-- begin api POST /api/operation/formdata/decorate -->
### Decorate Form Data

Retrieve form data and decorate it (optional).

#### Decorate form data - parameters

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/operation/formdata/decorate</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - form data was successfully decorated |
| 400  | `INPUT_INVALID` - request validation errors |
| 400  | `USER_NOT_FOUND` - user account does not exist |
| 400  | `REMOTE_ERROR` - communication with remote system failed |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### Decorate form data - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "organizationId": "RETAIL",
    "authMethod": "LOGIN_SCA",
	"operationContext": {
      "id": "371526cc-5dee-414e-8418-5ee1c5ef2d67",
      "name": "authorize_payment",
      "data": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "formData": {
        "title": {
          "id": "operation.title",
          "message": "Confirm Payment"
        },
        "greeting": {
          "id": "operation.greeting",
          "message": "Hello,\nplease confirm following payment:"
        },
        "summary": {
          "id": "operation.summary",
          "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": "Amount",
            "valueFormatType": "AMOUNT",
            "formattedValues": {
              "amount": "100.00",
              "currency": "CZK"
            },
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": "To Account",
            "valueFormatType": "ACCOUNT",
            "formattedValues": {
              "value": "238400856/0300"
            },
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": "Due Date",
            "valueFormatType": "DATE",
            "formattedValues": {
              "value": "Jun 29, 2019"
            },
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": "Note",
            "valueFormatType": "TEXT",
            "formattedValues": {
              "value": "Utility Bill Payment - 05/2019"
            },
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "userInput": {
        }
      },
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": ["pisp"], 
        "extras": {
          "applicationOwner": "Wultra"
        }
      }
    }
  }
}
```

#### Decorate form data - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "formData": {
      "title": {
        "id": "operation.title",
        "message": "Confirm Payment"
      },
      "greeting": {
        "id": "operation.greeting",
        "message": "Hello,\nplease confirm following payment:"
      },
      "summary": {
        "id": "operation.summary",
        "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
      },
      "config": [],
      "banners": [],
      "parameters": [
        {
          "type": "AMOUNT",
          "id": "operation.amount",
          "label": "Amount",
          "valueFormatType": "AMOUNT",
          "formattedValues": {
            "amount": "100.00",
            "currency": "CZK"
          },
          "amount": 100,
          "currency": "CZK",
          "currencyId": "operation.currency"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.account",
          "label": "To Account",
          "valueFormatType": "ACCOUNT",
          "formattedValues": {
            "value": "238400856/0300"
          },
          "value": "238400856/0300"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.dueDate",
          "label": "Due Date",
          "valueFormatType": "DATE",
          "formattedValues": {
            "value": "Jun 29, 2019"
          },
          "value": "2019-06-29"
        },
        {
          "type": "NOTE",
          "id": "operation.note",
          "label": "Note",
          "valueFormatType": "TEXT",
          "formattedValues": {
            "value": "Utility Bill Payment - 05/2019"
          },
          "note": "Utility Bill Payment - 05/2019"
        },
        {
          "type": "BANK_ACCOUNT_CHOICE",
          "id": "operation.bankAccountChoice",
          "label": null,
          "bankAccounts": [
            {
              "number": "12345678/1234",
              "accountId": "CZ4012340000000012345678",
              "name": "Běžný účet v CZK",
              "balance": 24394.52,
              "currency": "CZK",
              "usableForPayment": false,
              "unusableForPaymentReason": null
            },
            {
              "number": "87654321/4321",
              "accountId": "CZ4043210000000087654321",
              "name": "Spořící účet v CZK",
              "balance": 158121.10,
              "currency": "CZK",
              "usableForPayment": false,
              "unusableForPaymentReason": null
            },
            {
              "number": "44444444/1111",
              "accountId": "CZ4011110000000044444444",
              "name": "Spořící účet v EUR",
              "balance": 1.90,
              "currency": "EUR",
              "usableForPayment": false,
              "unusableForPaymentReason": "Low account balance"
            }
          ],
          "enabled": true,
          "defaultValue": "CZ4012340000000012345678"
        }
      ],
      "userInput": {
      }
    }
  }
}
```
<!-- end -->

<!-- begin api POST /api/operation/formdata/change -->
### FormData Change Notification

Notification of Data Adapter about formData change.

#### FormData change notification - parameters

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/operation/formdata/change</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - notification was successfully received |
| 400  | `INPUT_INVALID` - request validation errors |
| 400  | `REMOTE_ERROR` - communication with remote system failed |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### FormData change notification - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "organizationId": "RETAIL",
    "operationContext": {
      "id": "371526cc-5dee-414e-8418-5ee1c5ef2d67",
      "name": "authorize_payment",
      "data": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "formData": {
        "title": {
          "id": "operation.title",
          "message": "Confirm Payment"
        },
        "greeting": {
          "id": "operation.greeting",
          "message": "Hello,\nplease confirm following payment:"
        },
        "summary": {
          "id": "operation.summary",
          "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": "Amount",
            "valueFormatType": "AMOUNT",
            "formattedValues": {
              "amount": "100.00",
              "currency": "CZK"
            },
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": "To Account",
            "valueFormatType": "ACCOUNT",
            "formattedValues": {
              "value": "238400856/0300"
            },
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": "Due Date",
            "valueFormatType": "DATE",
            "formattedValues": {
              "value": "Jun 29, 2019"
            },
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": "Note",
            "valueFormatType": "TEXT",
            "formattedValues": {
              "value": "Utility Bill Payment - 05/2019"
            },
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "userInput": {
          "operation.bankAccountChoice": "CZ4012340000000012345678"
        }
      },
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": ["pisp"],
        "extras": {
          "applicationOwner": "Wultra"
        }
      }
    },
    "formDataChange": {
      "type": "BANK_ACCOUNT_CHOICE",
      "bankAccountId": "CZ4012340000000012345678"
    }
  }
}
```

#### FormData change notification - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK"
}
```
<!-- end -->

<!-- begin api POST /api/operation/change -->
### Operation Change Notification

Notification of Data Adapter about operation change.

#### Operation change notification - parameters

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/operation/change</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - notification was successfully received |
| 400  | `INPUT_INVALID` - request validation errors |
| 400  | `REMOTE_ERROR` - communication with remote system failed |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### Operation change notification - request

Possible operation changes are: `DONE`, `CANCELED` and `FAILED`.

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "organizationId": "RETAIL",
    "operationContext": {
      "id": "c02c5ea8-d4da-4499-9a27-aa4ded70921b",
      "name": "authorize_payment",
      "data": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "formData": {
        "title": {
          "id": "operation.title",
          "message": "Confirm Payment"
        },
        "greeting": {
          "id": "operation.greeting",
          "message": "Hello,\nplease confirm following payment:"
        },
        "summary": {
          "id": "operation.summary",
          "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": "Amount",
            "valueFormatType": "AMOUNT",
            "formattedValues": {
              "amount": "100.00",
              "currency": "CZK"
            },
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": "To Account",
            "valueFormatType": "ACCOUNT",
            "formattedValues": {
              "value": "238400856/0300"
            },
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": "Due Date",
            "valueFormatType": "DATE",
            "formattedValues": {
              "value": "Jun 29, 2019"
            },
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": "Note",
            "valueFormatType": "TEXT",
            "formattedValues": {
              "value": "Utility Bill Payment - 05/2019"
            },
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "userInput": {
          "operation.bankAccountChoice": "CZ4012340000000012345678",
          "operation.bankAccountChoice.disabled": "true"
        }
      },
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": ["pisp"], 
        "extras": {
          "applicationOwner": "Wultra"
        }
      }
    },
    "operationChange": "DONE"
  }
}
```

#### Operation change notification - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK"
}
```
<!-- end -->

<!-- begin api POST /api/operation/create -->
### Create Implicit Login Operation

Create an implicit login operation when accessing Web Flow without a previously created operation.

#### Create implicit login operation - parameters

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/operation/create</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - notification was successfully received |
| 400  | `INPUT_INVALID` - request validation errors |
| 400  | `OPERATION_CONTEXT_INVALID` - invalid operation context |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### Create implicit login operation - request

Possible operation changes are: `DONE`, `CANCELED` and `FAILED`.

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "clientId": "democlient",
    "scopes": [
      "aisp"
    ]
  }
}
```

#### Create implicit login operation - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "name": "login_sca",
    "formData": {
      "title": {
        "id": "login.title",
        "message": null
      },
      "greeting": {
        "id": "login.greeting",
        "message": null
      },
      "summary": {
        "id": "login.summary",
        "message": null
      },
      "config": [],
      "banners": [],
      "parameters": [],
      "userInput": {}
    },
    "applicationContext": {
      "id": "democlient",
      "name": "democlient",
      "description": "App with client ID: democlient",
      "originalScopes": [
        "aisp"
      ],
      "extras": {}
    }
  }
}
```
<!-- end -->

<!-- begin api POST /api/operation/mapping -->
### Operation Mapping

Mapping of complex Next Step operations into individual operations. PowerAuth operation template can be configured using the `templateName` parameter in response.

#### Operation mapping - parameters

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/operation/mapping</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - notification was successfully received |
| 400  | `INPUT_INVALID` - request validation errors |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### Operation mapping - request

Possible operation changes are: `DONE`, `CANCELED` and `FAILED`.

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "organizationId": "RETAIL",
    "authMethod": "LOGIN_SCA",
    "operationContext": {
      "id": "c02c5ea8-d4da-4499-9a27-aa4ded70921b",
      "name": "authorize_payment",
      "data": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "formData": {
        "title": {
          "id": "operation.title",
          "message": "Confirm Payment"
        },
        "greeting": {
          "id": "operation.greeting",
          "message": "Hello,\nplease confirm following payment:"
        },
        "summary": {
          "id": "operation.summary",
          "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": "Amount",
            "valueFormatType": "AMOUNT",
            "formattedValues": {
              "amount": "100.00",
              "currency": "CZK"
            },
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": "To Account",
            "valueFormatType": "ACCOUNT",
            "formattedValues": {
              "value": "238400856/0300"
            },
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": "Due Date",
            "valueFormatType": "DATE",
            "formattedValues": {
              "value": "Jun 29, 2019"
            },
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": "Note",
            "valueFormatType": "TEXT",
            "formattedValues": {
              "value": "Utility Bill Payment - 05/2019"
            },
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "userInput": {
          "operation.bankAccountChoice": "CZ4012340000000012345678",
          "operation.bankAccountChoice.disabled": "true"
        }
      },
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": ["pisp"], 
        "extras": {
          "applicationOwner": "Wultra"
        }
      }
    }
  }
}
```

#### Operation mapping - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "templateName": "login",
    "operationName": "login",
    "operationData": "A2",
    "formData": {
      "title": {
        "id": "login.title",
        "message": null
      },
      "greeting": {
        "id": "login.greeting",
        "message": null
      },
      "summary": {
        "id": "login.summary",
        "message": null
      },
      "config": [],
      "banners": [],
      "parameters": [],
      "userInput": {}
    }
  }
}
```
<!-- end -->

## Authentication API

<!-- begin api POST /api/auth/sms/create -->
### Generate and Send SMS Authorization Code

Generate an OTP authorization code, store it in the database and send a SMS message to the user.

#### Generate and send SMS authorization code - parameters

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/auth/sms/create</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - SMS message has been successfully created |
| 400  | `INPUT_INVALID` - request validation errors |
| 400  | `OPERATION_CONTEXT_INVALID` - invalid operation context |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### Generate and send SMS authorization code - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "organizationId": "RETAIL",
    "accountStatus": "ACTIVE",
	"authMethod": "LOGIN_SCA",
    "operationContext": {
      "id": "371526cc-5dee-414e-8418-5ee1c5ef2d67",
      "name": "authorize_payment",
      "data": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "formData": {
        "title": {
          "id": "operation.title",
          "message": "Confirm Payment"
        },
        "greeting": {
          "id": "operation.greeting",
          "message": "Hello,\nplease confirm following payment:"
        },
        "summary": {
          "id": "operation.summary",
          "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": "Amount",
            "valueFormatType": "AMOUNT",
            "formattedValues": {
              "amount": "100.00",
              "currency": "CZK"
            },
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": "To Account",
            "valueFormatType": "ACCOUNT",
            "formattedValues": {
              "value": "238400856/0300"
            },
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": "Due Date",
            "valueFormatType": "DATE",
            "formattedValues": {
              "value": "Jun 29, 2019"
            },
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": "Note",
            "valueFormatType": "TEXT",
            "formattedValues": {
              "value": "Utility Bill Payment - 05/2019"
            },
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "userInput": {
          "operation.bankAccountChoice": "CZ4012340000000012345678",
          "operation.bankAccountChoice.disabled": "true"
        }
      },
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": ["pisp"], 
        "extras": {
          "applicationOwner": "Wultra"
        }
      }
    },
    "lang": "en",
    "resend": false
  }
}
```

#### Generate and send SMS authorization code response - SMS has been successfully created and sent

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "messageId": "d056ec42-2349-43a1-9af4-cc502d924f76",
    "smsDeliveryResult": "SUCCEEDED",
    "errorMessage": null
  }
}
```

#### Generate and send SMS authorization code response - SMS could not be sent

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`
  
```json
{
  "status": "OK",
  "responseObject": {
    "messageId": "d056ec42-2349-43a1-9af4-cc502d924f76",
    "smsDeliveryResult": "FAILED",
    "errorMessage": null
  }
}
```
<!-- end -->

<!-- begin api POST /api/auth/sms/send -->
### Send SMS Authorization Code

Send an SMS message with previously generated OTP authorization code to the user.

#### Send SMS authorization code - parameters

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/auth/sms/send</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - SMS message has been successfully created |
| 400  | `INPUT_INVALID` - request validation errors |
| 400  | `OPERATION_CONTEXT_INVALID` - invalid operation context |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### Send SMS authorization code - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "organizationId": "RETAIL",
    "accountStatus": "ACTIVE",
    "operationContext": {
      "id": "371526cc-5dee-414e-8418-5ee1c5ef2d67",
      "name": "authorize_payment",
      "data": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "formData": {
        "title": {
          "id": "operation.title",
          "message": "Confirm Payment"
        },
        "greeting": {
          "id": "operation.greeting",
          "message": "Hello,\nplease confirm following payment:"
        },
        "summary": {
          "id": "operation.summary",
          "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": "Amount",
            "valueFormatType": "AMOUNT",
            "formattedValues": {
              "amount": "100.00",
              "currency": "CZK"
            },
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": "To Account",
            "valueFormatType": "ACCOUNT",
            "formattedValues": {
              "value": "238400856/0300"
            },
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": "Due Date",
            "valueFormatType": "DATE",
            "formattedValues": {
              "value": "Jun 29, 2019"
            },
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": "Note",
            "valueFormatType": "TEXT",
            "formattedValues": {
              "value": "Utility Bill Payment - 05/2019"
            },
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "userInput": {
          "operation.bankAccountChoice": "CZ4012340000000012345678",
          "operation.bankAccountChoice.disabled": "true"
        }
      },
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": ["pisp"], 
        "extras": {
          "applicationOwner": "Wultra"
        }
      }
    },
  "messageId": "e82318ea-d8c6-11eb-b8bc-0242ac130003",
  "authorizationCode": "54741284",
  "lang": "en",
  "resend": false
  }
}
```

#### Send SMS authorization code response - SMS has been successfully sent

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "messageId": "e82318ea-d8c6-11eb-b8bc-0242ac130003",
    "smsDeliveryResult": "SUCCEEDED",
    "errorMessage": null
  }
}
```

#### Send SMS authorization code response - SMS could not be sent

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "messageId": "d056ec42-2349-43a1-9af4-cc502d924f76",
    "smsDeliveryResult": "FAILED",
    "errorMessage": null
  }
}
```
<!-- end -->

<!-- begin api POST /api/auth/sms/verify -->
### Verify Authorization SMS Code

Verify an authorization OTP code from a SMS message.

### Verify authorization SMS code - parameters

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/auth/sms/verify</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - SMS authorization code has been successfully verified |
| 400  | `INPUT_INVALID` -   request validation errors |
| 400  | `OPERATION_CONTEXT_INVALID` - invalid operation context |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### Verify authorization SMS code - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "organizationId": "RETAIL",
    "accountStatus": "ACTIVE",
    "messageId": "617178ab-f315-4223-a602-9d4893b4f99f",
    "authorizationCode": "77038183",
    "operationContext": {
      "id": "371526cc-5dee-414e-8418-5ee1c5ef2d67",
      "name": "authorize_payment",
      "data": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "formData": {
        "title": {
          "id": "operation.title",
          "message": "Confirm Payment"
        },
        "greeting": {
          "id": "operation.greeting",
          "message": "Hello,\nplease confirm following payment:"
        },
        "summary": {
          "id": "operation.summary",
          "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": "Amount",
            "valueFormatType": "AMOUNT",
            "formattedValues": {
              "amount": "100.00",
              "currency": "CZK"
            },
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": "To Account",
            "valueFormatType": "ACCOUNT",
            "formattedValues": {
              "value": "238400856/0300"
            },
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": "Due Date",
            "valueFormatType": "DATE",
            "formattedValues": {
              "value": "Jun 29, 2019"
            },
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": "Note",
            "valueFormatType": "TEXT",
            "formattedValues": {
              "value": "Utility Bill Payment - 05/2019"
            },
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "userInput": {
          "operation.bankAccountChoice": "CZ4012340000000012345678",
          "operation.bankAccountChoice.disabled": "true"
        }
      },
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": ["pisp"], 
        "extras": {
          "applicationOwner": "Wultra"
        }
      }
    }
  }
}
```

#### Verify authorization SMS code response - SMS authorization code has been successfully verified

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "smsAuthorizationResult": "SUCCEEDED",
    "errorMessage": null,
    "remainingAttempts": null,
    "showRemainingAttempts": false
  }
}
```

#### Verify authorization SMS code response - SMS authorization code verification failed

```json
{
  "status": "OK",
  "responseObject": {
    "smsAuthorizationResult": "FAILED",
    "errorMessage": "smsAuthorization.failed",
    "remainingAttempts": 4,
    "showRemainingAttempts": false
  }
}
```
<!-- end -->

<!-- begin api POST /api/auth/sms/password/verify -->
### Verify Authorization SMS Code and Password

Verify an authorization OTP code from a SMS message together with user password.

#### Verify authorization SMS code and password - parameters

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/auth/sms/password/verify</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - SMS authorization code has been successfully verified |
| 400  | `INPUT_INVALID` -   request validation errors |
| 400  | `OPERATION_CONTEXT_INVALID` - invalid operation context |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

The password can be optionally encrypted. In this case the `passwordProtection` parameter provides information about
type of encryption and the `cipherTransformation` parameter contains information about used cipher.

See chapter [User Password Encryption and Decryption](./Data-Adapter-REST-API-Reference.md#user-password-encryption-and-decryption) for additional details.

#### Verify authorization SMS code and password - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "organizationId": "RETAIL",
    "accountStatus": "ACTIVE",
    "password": "s3cret",
    "authenticationContext": {
      "passwordProtection": "NO_PROTECTION",
      "cipherTransformation": "",
      "smsAuthorizationResult": null
    },
    "messageId": "617178ab-f315-4223-a602-9d4893b4f99f",
    "authorizationCode": "77038183",
    "operationContext": {
      "id": "371526cc-5dee-414e-8418-5ee1c5ef2d67",
      "name": "authorize_payment",
      "data": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "formData": {
        "title": {
          "id": "operation.title",
          "message": "Confirm Payment"
        },
        "greeting": {
          "id": "operation.greeting",
          "message": "Hello,\nplease confirm following payment:"
        },
        "summary": {
          "id": "operation.summary",
          "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": "Amount",
            "valueFormatType": "AMOUNT",
            "formattedValues": {
              "amount": "100.00",
              "currency": "CZK"
            },
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": "To Account",
            "valueFormatType": "ACCOUNT",
            "formattedValues": {
              "value": "238400856/0300"
            },
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": "Due Date",
            "valueFormatType": "DATE",
            "formattedValues": {
              "value": "Jun 29, 2019"
            },
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": "Note",
            "valueFormatType": "TEXT",
            "formattedValues": {
              "value": "Utility Bill Payment - 05/2019"
            },
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "userInput": {
          "operation.bankAccountChoice": "CZ4012340000000012345678",
          "operation.bankAccountChoice.disabled": "true"
        }
      },
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": ["pisp"], 
        "extras": {
          "applicationOwner": "Wultra"
        }
      }
    }
  }
}
```

#### Verify authorization SMS code and password response - SMS authorization code and password have been successfully verified

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "smsAuthorizationResult": "SUCCEEDED",
    "userAuthenticationResult": "SUCCEEDED",
    "errorMessage": null,
    "remainingAttempts": null,
    "showRemainingAttempts": false
  }
}
```

#### Verify authorization SMS code and password response - SMS authorization code and/or password verification failed

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`
  
```json
{
  "status": "OK",
  "responseObject": {
    "smsAuthorizationResult": "FAILED",
    "userAuthenticationResult": "FAILED",
    "errorMessage": "login.authenticationFailed",
    "remainingAttempts": null,
    "showRemainingAttempts": false
  }
}
```
<!-- end -->

<!-- begin api POST /api/auth/certificate/verify -->
### Verify a Client TLS Certificate

Verify a client TLS certificate for user authentication.

#### Verify a client TLS certificate - parameters

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/auth/certificate/verify</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - SMS authorization code has been successfully verified |
| 400  | `INPUT_INVALID` -   request validation errors |
| 400  | `OPERATION_CONTEXT_INVALID` - invalid operation context |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### Verify a client TLS certificate - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
  "clientCertificate": "--- [CLIENT_TLS_CERTIFICATE] ---",
  "authMethod": "LOGIN_SCA",
    "userId": "12345678",
    "organizationId": "RETAIL",
    "accountStatus": "ACTIVE",
    "operationContext": {
      "id": "371526cc-5dee-414e-8418-5ee1c5ef2d67",
      "name": "authorize_payment",
      "data": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "formData": {
        "title": {
          "id": "operation.title",
          "message": "Confirm Payment"
        },
        "greeting": {
          "id": "operation.greeting",
          "message": "Hello,\nplease confirm following payment:"
        },
        "summary": {
          "id": "operation.summary",
          "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": "Amount",
            "valueFormatType": "AMOUNT",
            "formattedValues": {
              "amount": "100.00",
              "currency": "CZK"
            },
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": "To Account",
            "valueFormatType": "ACCOUNT",
            "formattedValues": {
              "value": "238400856/0300"
            },
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": "Due Date",
            "valueFormatType": "DATE",
            "formattedValues": {
              "value": "Jun 29, 2019"
            },
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": "Note",
            "valueFormatType": "TEXT",
            "formattedValues": {
              "value": "Utility Bill Payment - 05/2019"
            },
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "userInput": {
          "operation.bankAccountChoice": "CZ4012340000000012345678",
          "operation.bankAccountChoice.disabled": "true"
        }
      },
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": ["pisp"], 
        "extras": {
          "applicationOwner": "Wultra"
        }
      }
    }
  }
}
```

#### Verify a client TLS certificate response - client TLS certificate has been verified, verification result is sent in response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "certificateVerificationResult": "SUCCEEDED",
    "errorMessage": null,
    "remainingAttempts": null,
    "showRemainingAttempts": false, 
  "accountStatus": "ACTIVE"
  }
}
```

In case the client TLS certificate verification fails, the parameter `certificateVerificationResult` value is set to `FAILED`.
<!-- end -->

## Consent API

<!-- begin api POST /api/auth/consent/init -->
### Initialize OAuth 2.0 Consent Form

Initialize the OAuth 2.0 consent form and get information whether consent form should be displayed.

#### Initialize OAuth 2.0 consent form - parameters

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/auth/consent/init</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - consent form has been successfully initalized |
| 400  | `INPUT_INVALID` - request validation errors |
| 400  | `OPERATION_CONTEXT_INVALID` - invalid operation context |
| 400  | `REMOTE_ERROR` - communication with remote system failed |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### Initialize OAuth 2.0 consent form - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "organizationId": "RETAIL",
    "operationContext": {
      "id": "7d92fce2-c1f2-4d5b-b522-61da0749fdf7",
      "name": "authorize_payment",
      "data": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "formData": {
        "title": {
          "id": "operation.title",
          "message": "Confirm Payment"
        },
        "greeting": {
          "id": "operation.greeting",
          "message": "Hello,\nplease confirm following payment:"
        },
        "summary": {
          "id": "operation.summary",
          "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": "Amount",
            "valueFormatType": "AMOUNT",
            "formattedValues": {
              "amount": "100.00",
              "currency": "CZK"
            },
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": "To Account",
            "valueFormatType": "ACCOUNT",
            "formattedValues": {
              "value": "238400856/0300"
            },
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": "Due Date",
            "valueFormatType": "DATE",
            "formattedValues": {
              "value": "Jun 29, 2019"
            },
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": "Note",
            "valueFormatType": "TEXT",
            "formattedValues": {
              "value": "Utility Bill Payment - 05/2019"
            },
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "userInput": {
          "operation.bankAccountChoice": "CZ4012340000000012345678",
          "operation.bankAccountChoice.disabled": "true"
        }
      },
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": ["pisp"], 
        "extras": {
          "applicationOwner": "Wultra"
        }
      }
    }
  }
}
```

#### Initialize OAuth 2.0 consent form response - consent form has been successfully initialized

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "shouldDisplayConsentForm": true
  }
}
```
<!-- end -->

<!-- begin api POST /api/auth/consent/create -->
### Create OAuth 2.0 Consent Form

Create the OAuth 2.0 consent form and obtain form options.

#### Create OAuth 2.0 consent form - parameters

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/auth/consent/create</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - consent form has been successfully created |
| 400  | `INPUT_INVALID` - request validation errors |
| 400  | `OPERATION_CONTEXT_INVALID` - invalid operation context |
| 400  | `REMOTE_ERROR` - communication with remote system failed |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### Create OAuth 2.0 consent form - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "organizationId": "RETAIL",
    "operationContext": {
      "id": "7d92fce2-c1f2-4d5b-b522-61da0749fdf7",
      "name": "authorize_payment",
      "data": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "formData": {
        "title": {
          "id": "operation.title",
          "message": "Confirm Payment"
        },
        "greeting": {
          "id": "operation.greeting",
          "message": "Hello,\nplease confirm following payment:"
        },
        "summary": {
          "id": "operation.summary",
          "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": "Amount",
            "valueFormatType": "AMOUNT",
            "formattedValues": {
              "amount": "100.00",
              "currency": "CZK"
            },
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": "To Account",
            "valueFormatType": "ACCOUNT",
            "formattedValues": {
              "value": "238400856/0300"
            },
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": "Due Date",
            "valueFormatType": "DATE",
            "formattedValues": {
              "value": "Jun 29, 2019"
            },
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": "Note",
            "valueFormatType": "TEXT",
            "formattedValues": {
              "value": "Utility Bill Payment - 05/2019"
            },
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "userInput": {
          "operation.bankAccountChoice": "CZ4012340000000012345678",
          "operation.bankAccountChoice.disabled": "true"
        }
      },
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": ["pisp"], 
        "extras": {
          "applicationOwner": "Wultra"
        }
      }
    },
    "lang": "en"
  }
}
```

#### Create OAuth 2.0 consent form response - consent form has been successfully created

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "consentHtml": "I consent that I have initiated this payment request and give consent to complete the operation.",
    "options": [
      {
        "id": "CONSENT_INIT",
        "descriptionHtml": "I consent that I have initiated this payment operation.",
        "required": true,
        "defaultValue": "NOT_CHECKED",
        "value": null
      },
      {
        "id": "CONSENT_PAYMENT",
        "descriptionHtml": "I give consent to complete this payment operation.",
        "required": true,
        "defaultValue": "NOT_CHECKED",
        "value": null
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api POST /api/auth/consent/validate -->
### Validate OAuth 2.0 Consent Form

Validate submitted options in the OAuth 2.0 consent form.

#### Validate OAuth 2.0 consent form - parameters

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/auth/consent/validate</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - consent form has been successfully validated (however it may contain validation errors) |
| 400  | `INPUT_INVALID` - request validation errors |
| 400  | `OPERATION_CONTEXT_INVALID` - invalid operation context |
| 400  | `CONSENT_DATA_INVALID` - invalid consent data |
| 400  | `REMOTE_ERROR` - communication with remote system failed |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### Validate consent form - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "organizationId": "RETAIL",
    "operationContext": {
      "id": "7d92fce2-c1f2-4d5b-b522-61da0749fdf7",
      "name": "authorize_payment",
      "data": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "formData": {
        "title": {
          "id": "operation.title",
          "message": "Confirm Payment"
        },
        "greeting": {
          "id": "operation.greeting",
          "message": "Hello,\nplease confirm following payment:"
        },
        "summary": {
          "id": "operation.summary",
          "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": "Amount",
            "valueFormatType": "AMOUNT",
            "formattedValues": {
              "amount": "100.00",
              "currency": "CZK"
            },
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": "To Account",
            "valueFormatType": "ACCOUNT",
            "formattedValues": {
              "value": "238400856/0300"
            },
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": "Due Date",
            "valueFormatType": "DATE",
            "formattedValues": {
              "value": "Jun 29, 2019"
            },
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": "Note",
            "valueFormatType": "TEXT",
            "formattedValues": {
              "value": "Utility Bill Payment - 05/2019"
            },
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "userInput": {
          "operation.bankAccountChoice": "CZ4012340000000012345678",
          "operation.bankAccountChoice.disabled": "true"
        }
      },
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": ["pisp"], 
        "extras": {
          "applicationOwner": "Wultra"
        }
      }
    },
    "lang": "en",
    "options": [
      {
        "id": "CONSENT_INIT",
        "descriptionHtml": "I consent that I have initiated this payment operation.",
        "required": true,
        "defaultValue": "NOT_CHECKED",
        "value": null
      },
      {
        "id": "CONSENT_PAYMENT",
        "descriptionHtml": "I give consent to complete this payment operation.",
        "required": true,
        "defaultValue": "NOT_CHECKED",
        "value": null
      }
    ]
  }
}
```

#### Validate consent form response - consent form has been successfully validated

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "consentValidationPassed": true,
    "validationErrorMessage": null,
    "optionValidationResults": []
  }
}
```

#### Validate consent form response - consent form options validation failed

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "consentValidationPassed": false,
    "validationErrorMessage": "Please fill in the whole consent form.",
    "optionValidationResults": [
      {
        "id": "CONSENT_INIT",
        "validationPassed": false,
        "errorMessage": "Confirm this option to complete the operation."
      },
      {
        "id": "CONSENT_PAYMENT",
        "validationPassed": false,
        "errorMessage": "Confirm this option to complete the operation."
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api POST /api/auth/consent/save -->
### Save OAuth 2.0 Consent Form

Save the OAuth 2.0 consent form options.

#### Save OAuth 2.0 consent form - parameters

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/auth/consent/save</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - consent form has been successfully saved |
| 400  | `INPUT_INVALID` - request validation errors |
| 400  | `OPERATION_CONTEXT_INVALID` - invalid operation context |
| 400  | `CONSENT_DATA_INVALID` - invalid consent data |
| 400  | `REMOTE_ERROR` - communication with remote system failed |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### Save OAuth 2.0 consent form - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "organizationId": "RETAIL",
    "operationContext": {
      "id": "4a04667b-8a1a-46af-813c-cf71ffcde478",
      "name": "authorize_payment",
      "data": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "formData": {
        "title": {
          "id": "operation.title",
          "message": "Confirm Payment"
        },
        "greeting": {
          "id": "operation.greeting",
          "message": "Hello,\nplease confirm following payment:"
        },
        "summary": {
          "id": "operation.summary",
          "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": "Amount",
            "valueFormatType": "AMOUNT",
            "formattedValues": {
              "amount": "100.00",
              "currency": "CZK"
            },
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": "To Account",
            "valueFormatType": "ACCOUNT",
            "formattedValues": {
              "value": "238400856/0300"
            },
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": "Due Date",
            "valueFormatType": "DATE",
            "formattedValues": {
              "value": "Jun 29, 2019"
            },
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": "Note",
            "valueFormatType": "TEXT",
            "formattedValues": {
              "value": "Utility Bill Payment - 05/2019"
            },
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "userInput": {
          "operation.bankAccountChoice": "CZ4012340000000012345678",
          "operation.bankAccountChoice.disabled": "true"
        }
      },
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": ["pisp"], 
        "extras": {
          "applicationOwner": "Wultra"
        }
      }
    },
    "options": [
      {
        "id": "CONSENT_INIT",
        "descriptionHtml": "I consent that I have initiated this payment operation.",
        "required": true,
        "defaultValue": "NOT_CHECKED",
        "value": "CHECKED"
      },
      {
        "id": "CONSENT_PAYMENT",
        "descriptionHtml": "I give consent to complete this payment operation.",
        "required": true,
        "defaultValue": "NOT_CHECKED",
        "value": "CHECKED"
      }
    ]
  }
}
```

#### Save OAuth 2.0 consent form response - consent form has been successfully saved

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "saveSucceeded": true
  }
}
```

#### Save OAuth 2.0 consent form response - the processing was correct however consent form could not be saved at this time

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "saveSucceeded": false
  }
}
```
<!-- end -->

## AFS API

<!-- begin api POST /api/afs/action/execute -->
### Execute an AFS Action

Execute an AFS action using the remote anti-fraud system.

#### Execute an AFS action - parameters

<!-- begin remove -->
<table>
  <tr>
    <td>Method</td>
    <td><code>POST</code></td>
  </tr>
  <tr>
    <td>Resource URI</td>
    <td><code>/api/afs/action/execute</code></td>
  </tr>
</table>
<!-- end -->

The list of expected status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - AFS action was successfully executed |
| 400  | `OPERATION_CONTEXT_INVALID` - invalid operation context |
| 400  | `REMOTE_ERROR` - communication with remote system failed |
| 500  | Server errors - provide error details in the message, this is only for unexpected errors |

#### Execute an AFS action - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "organizationId": "RETAIL",
    "operationContext": {
      "id": "47a74437-83f9-4567-8c9e-270bea98d9de",
      "name": "authorize_payment",
      "data": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "formData": {
        "title": {
          "id": "operation.title",
          "message": "Confirm Payment"
        },
        "greeting": {
          "id": "operation.greeting",
          "message": "Hello,\nplease confirm following payment:"
        },
        "summary": {
          "id": "operation.summary",
          "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": "Amount",
            "valueFormatType": "AMOUNT",
            "formattedValues": {
              "amount": "100.00",
              "currency": "CZK"
            },
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": "To Account",
            "valueFormatType": "ACCOUNT",
            "formattedValues": {
              "value": "238400856/0300"
            },
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": "Due Date",
            "valueFormatType": "DATE",
            "formattedValues": {
              "value": "Jun 29, 2019"
            },
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": "Note",
            "valueFormatType": "TEXT",
            "formattedValues": {
              "value": "Utility Bill Payment - 05/2019"
            },
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "userInput": {
          "smsFallback.enabled": "true",
          "operation.bankAccountChoice": "CZ4012340000000012345678",
          "operation.bankAccountChoice.disabled": "true"
        }
      },
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": [
          "pisp"
        ],
        "extras": {
          "applicationOwner": "Wultra"
        }
      }
    },
    "afsRequestParameters": {
      "afsType": "THREAT_MARK",
      "afsAction": "APPROVAL_INIT",
      "clientIpAddress": "",
      "stepIndex": 1,
      "username": null,
      "authInstruments": [],
      "authStepResult": null,
      "operationTerminationReason": null
    },
    "extras": {
      "cookie1": "123xyz",
      "cookie2": "abc67890"
    }
  }
}
```

#### Execute an AFS action response - AFS action was successfully executed

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "afsResponseApplied": true,
    "afsLabel": "1FA",
    "authStepOptions": {
      "smsOtpRequired": true,
      "passwordRequired": false
    },
    "extras": {}
  }
}
```
<!-- end -->

<!-- end -->
