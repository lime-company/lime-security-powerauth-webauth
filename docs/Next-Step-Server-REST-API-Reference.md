# NextStep Server RESTful API Reference

<!-- template api -->

PowerAuth Web Flow communicates with the Next Step Server via a REST API to resolve the next step in the authentication process. This chapter defines the REST API implemented by Next Step Server and consumed by the Web Flow Server during authentication. The REST API can be also used by other components.

The Next Step API can list available authentication methods and enable/disable authentication methods per user. Authentication method configuration can be updated - for instance the activation ID of registered user device is set for the Mobile Token authentication method.

The Next Step API is also used by other components involved in the authentication process (e.g. Mobile Token which uses the API indirectly through Web Flow or a party initiating a new operation). The API can be used to query operation details, create an operation, update an operation (move it to the next step) as well as update operation form data and set chosen authentication method as the user progresses in the authentication and authorization process.

Following topics are covered in this chapter:
- [Status codes and error handling](#status-codes-and-error-handling)
- [Service status](#service-status)
- [Authentication methods API](#authentication-methods-api)
  - [Create an authentication method](#create-an-authentication-method)
  - [List authentication methods](#list-authentication-methods)
  - [List authentication methods for given user](#list-authentication-methods-for-given-user)
  - [List authentication methods enabled for given user](#list-authentication-methods-enabled-for-given-user)
  - [Enable an authentication method for given user](#enable-an-authentication-method-for-given-user)
  - [Disable an authentication method for given user](#disable-an-authentication-method-for-given-user)
  - [Delete an authentication method](#delete-an-authentication-method)
- [Operations API](#operations-api)
  - [Operation form data](#operation-formdata)
  - [Create an operation](#create-an-operation)
  - [Update an operation](#update-an-operation)
  - [Operation detail](#operation-detail)
  - [List pending operations](#list-pending-operations)
  - [Lookup operations by external transaction ID](#lookup-operations-by-external-transaction-id)
  - [Update operation form data](#update-operation-formdata)
  - [Update application context for an operation](#update-application-context-for-an-operation)  
  - [Update user for an operation](#update-user-for-an-operation)
  - [Set chosen authentication method](#set-chosen-authentication-method)
  - [Update mobile token status for an operation](#update-mobile-token-status-for-an-operation)
  - [Get mobile token configuration](#get-mobile-token-configuration)
  - [Store result of an AFS action](#store-result-of-an-afs-action)
  - [List operation configurations](#list-operation-configurations)
  - [Create an operation configuration](#create-an-operation-configuration)
  - [Get operation configuration detail](#get-operation-configuration-detail)
  - [Delete an operation configuration](#delete-an-operation-configuration)
  - [Create an operation and authentication method configuration](#create-an-operation-and-authentication-method-configuration)
  - [Get an operation and authentication method configuration detail](#get-an-operation-and-authentication-method-configuration-detail)
  - [Delete an operation and authentication method configuration detail](#delete-an-operation-and-authentication-method-configuration)
- [Organizations API](#organizations-api)
  - [Create an organization](#create-an-organization)
  - [List organizations](#list-organizations)
  - [Organization detail](#organization-detail)
  - [Delete an organization](#delete-an-organization)
- [Step definitions API](#step-definitions-api)
  - [Create a step definition](#create-a-step-definition)
  - [Delete a step definition](#delete-a-step-definition)
- [Applications API](#applications-api)
  - [Create an application](#create-an-application)
  - [List applications](#list-applications)
  - [Update an application](#update-an-application)
  - [Delete an application](#delete-an-application)
- [Roles API](#roles-api)
  - [Create a role](#create-a-role)
  - [List roles](#list-roles)
  - [Delete a role](#delete-a-role)  
- [Credential policies API](#credential-policies-api)
  - [Create a credential policy](#create-a-credential-policy)
  - [List credential policies](#list-credential-policies)
  - [Update a credential policy](#update-a-credential-policy)
  - [Delete a credential policy](#delete-a-credential-policy)
- [Credential definitions API](#credential-definitions-api)
  - [Create a credential definition](#create-a-credential-definition)
  - [List credential definitions](#list-credential-definitions)
  - [Update a credential definition](#update-a-credential-definition)
  - [Delete a credential definition](#delete-a-credential-definition)
- [OTP policies API](#otp-policies-api)
  - [Create an OTP policy](#create-an-otp-policy)
  - [List OTP policies](#list-otp-policies)
  - [Update an OTP policy](#update-an-otp-policy)
  - [Delete an OTP policy](#delete-an-otp-policy)
- [OTP definitions API](#otp-definitions-api)
  - [Create an OTP definition](#create-an-otp-definition)
  - [List OTP definitions](#list-otp-definitions)
  - [Update an OTP definition](#update-an-otp-definition)
  - [Delete an OTP definition](#delete-an-otp-definition)  
- [Hashing configurations API](#hashing-configurations-api)
  - [Create a hashing configuration](#create-a-hashing-configuration)
  - [List hashing configurations](#list-hashing-configurations)
  - [Update a hashing configuration](#update-a-hashing-configuration)
  - [Delete a hashing configuration](#delete-a-hashing-configuration)
- [User identities API](#user-identities-api)
  - [Create a user identity](#create-a-user-identity)
  - [Get user identity detail](#get-user-identity-detail)  
  - [Update a user identity](#update-a-user-identity)
  - [Update multiple user identities](#update-multiple-user-identities)
  - [Lookup a user identity](#lookup-a-user-identity)
  - [Lookup user identities](#lookup-user-identities)
  - [Block a user identity](#block-a-user-identity)
  - [Unblock a user identity](#unblock-a-user-identity)
  - [Delete a user identity](#delete-a-user-identity)
  - [Create a user contact](#create-a-user-contact)
  - [List user contacts](#list-user-contacts)
  - [Update a user contact](#update-a-user-contact)
  - [Delete a user contact](#delete-a-user-contact)
  - [Create a user alias](#create-a-user-alias)
  - [List user aliases](#list-user-aliases)
  - [Update a user alias](#update-a-user-alias)
  - [Delete a user alias](#delete-a-user-alias)
  - [Assign a role to user identity](#assign-a-role-to-user-identity)
  - [Remove a role from user identity](#remove-a-role-from-user-identity)
  - [Get user credential list](#get-user-credential-list)
  - [Get user authentication list](#get-user-authentication-list)
- [Credentials API](#credentials-api)
  - [Create a credential](#create-a-credential)
  - [Update a credential](#update-a-credential)
  - [Validate a credential](#validate-a-credential)
  - [Reset a credential](#reset-a-credential)
  - [Block a credential](#block-a-credential)
  - [Unblock a credential](#unblock-a-credential)
  - [Delete a credential](#delete-a-credential)
- [Credential counters API](#credential-counters-api)
  - [Update a credential counter](#update-a-credential-counter)
  - [Reset all soft failed attempt counters](#reset-all-soft-failed-attempt-counters)
- [OTP API](#otp-api)
  - [Create an OTP](#create-an-otp)
  - [Send an OTP](#create-and-send-an-otp)
  - [Get OTP list](#get-otp-list)
  - [Get OTP detail](#get-otp-detail)
  - [Delete an OTP](#delete-an-otp)
- [Authentication API](#authentication-api)
  - [Authenticate using an OTP](#authenticate-using-an-otp)
  - [Authenticate using a credential](#authenticate-using-a-credential)
  - [Authenticate using a credential and OTP](#authenticate-using-a-credential-and-otp)
  
You can access the generated REST API documentation in deployed Next Step application:

```
http[s]://[host]:[port]/powerauth-nextstep/swagger-ui.html
```

## Status codes and error handling

PowerAuth Web Flow Server uses a unified format for error response body, accompanied with an appropriate HTTP status code. Besides the HTTP error codes that application server may return regardless of server application (such as 404 when resource is not found or 503 when server is down).

The list of error status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - REST API call succeeded |
| 500  | Server error - details in the message |

All error responses that are produced by the Next Step Server have following body:

```json
{
    "status": "ERROR",
    "responseObject": {
        "code": "ERROR_CODE",
        "message": "ERROR_MESSAGE_I18N_KEY"
    }
}
```

## Service API

<!-- begin api GET /api/service/status -->
### Service Status

Get a system status response, with basic information about the running application.

<!-- begin remove -->
#### Service status - parameters
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
    "status" : "OK",
    "responseObject": {
        "applicationName" : "powerauth-nextstep",
        "applicationDisplayName" : "PowerAuth Next Step Server",
        "applicationEnvironment" : "",
        "version" : "0.22.0",
        "buildTime" : "2019-06-11T09:34:52Z",
        "timestamp" : "2019-06-14T14:54:14Z"
    }  
}
```

- `applicationName` - Application name.
- `applicationDisplayName` - Application display name.
- `applicationEnvironment` - Application environment.
- `version` - Version of Next Step.
- `buildTime` - Timestamp when powerauth-nextstep.war file was created.
- `timestamp` - Response timestamp.

<!-- end -->

## Authentication Methods API

<!-- begin api POST /auth-method -->
### Create an Authentication Method

Create an authentication method in Next Step server.

<!-- begin remove -->
#### Create an authentication method - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/auth-method</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `AUTH_METHOD_ALREADY_EXISTS` - authentication method already exists |
| 500  | Server errors - unexpected error occurred |

#### Create an authentication method - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "authMethod": "INIT",
    "orderNumber": 1,
    "checkUserPrefs": false,
    "userPrefsColumn": 0,
    "userPrefsDefault": false,
    "checkAuthFails": false,
    "maxAuthFails": 0,
    "hasUserInterface": false,
    "hasMobileToken": false,
    "displayNameKey": null
  }
}
```

#### Create an authentication method - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "authMethod": "INIT",
    "orderNumber": 1,
    "checkUserPrefs": false,
    "userPrefsColumn": 0,
    "userPrefsDefault": false,
    "checkAuthFails": false,
    "maxAuthFails": 0,
    "hasUserInterface": false,
    "hasMobileToken": false,
    "displayNameKey": null
  }
}
```
<!-- end -->

<!-- begin api GET /auth-method -->
### List Authentication Methods

List all authentication methods supported by the server.

This method has a `POST /auth-method/list` alternative.

<!-- begin remove -->
#### List authentication methods - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/auth-method</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/auth-method/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 500  | Server error - unexpected error occurred |

#### List authentication methods - request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
  }
}
```

#### List authentication methods - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "authMethods": [
      {
        "authMethod": "INIT",
        "hasUserInterface": false,
        "displayNameKey": null,
        "hasMobileToken": false
      },
      {
        "authMethod": "USER_ID_ASSIGN",
        "hasUserInterface": false,
        "displayNameKey": null,
        "hasMobileToken": false
      },
      {
        "authMethod": "USERNAME_PASSWORD_AUTH",
        "hasUserInterface": true,
        "displayNameKey": "method.usernamePassword",
        "hasMobileToken": false
      },
      {
        "authMethod": "SHOW_OPERATION_DETAIL",
        "hasUserInterface": true,
        "displayNameKey": "method.showOperationDetail",
        "hasMobileToken": false
      },
      {
        "authMethod": "POWERAUTH_TOKEN",
        "hasUserInterface": true,
        "displayNameKey": "method.powerauthToken",
        "hasMobileToken": true
      },
      {
        "authMethod": "SMS_KEY",
        "hasUserInterface": true,
        "displayNameKey": "method.smsKey",
        "hasMobileToken": false
      },
      {
        "authMethod": "CONSENT",
        "hasUserInterface": true,
        "displayNameKey": "method.consent",
        "hasMobileToken": false
      },
      {
        "authMethod": "LOGIN_SCA",
        "hasUserInterface": true,
        "displayNameKey": "method.loginSca",
        "hasMobileToken": true
      },
      {
        "authMethod": "APPROVAL_SCA",
        "hasUserInterface": true,
        "displayNameKey": "method.approvalSca",
        "hasMobileToken": true
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api GET /user/auth-method/enabled -->
### List Authentication Methods Enabled for Given User

List enabled authentication methods for given user.

This method has a `POST /user/auth-method/enabled/list` alternative.

<!-- begin remove -->
#### List authentication methods enabled for given user - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/auth-method/enabled</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/auth-method/enabled/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 500  | Server error - unexpected error occurred |

#### List authentication methods enabled for given user - request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "operationName": "auth_token_otp"
  }
}
```

#### List authentication methods for given user - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "12345678",
    "userIdentityStatus": "ACTIVE",
    "operationName": "auth_token_otp",
    "enabledAuthMethods": [ "POWERAUTH_TOKEN", "SMS_KEY" ]
  }
}
```

<!-- end -->

<!-- begin api GET /user/auth-method -->
### List Authentication Methods for Given User

List all authentication methods for given user.

This method has a `POST /user/auth-method/list` alternative.

<!-- begin remove -->
#### List authentication methods for given user - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/auth-method</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/auth-method/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 500  | Server error - unexpected error occurred |

#### List authentication methods for given user - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678"
  }
}
```

#### List authentication methods for given user - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userAuthMethods": [
      {
        "userId": "12345678",
        "authMethod": "INIT",
        "hasUserInterface": false,
        "displayNameKey": null,
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "USER_ID_ASSIGN",
        "hasUserInterface": false,
        "displayNameKey": null,
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "USERNAME_PASSWORD_AUTH",
        "hasUserInterface": true,
        "displayNameKey": "method.usernamePassword",
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "SHOW_OPERATION_DETAIL",
        "hasUserInterface": true,
        "displayNameKey": "method.showOperationDetail",
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "POWERAUTH_TOKEN",
        "hasUserInterface": true,
        "displayNameKey": "method.powerauthToken",
        "hasMobileToken": true,
        "config": {
          "activationId": "1629d4c7-6b17-41e3-bce1-e184e94921d2"
        }
      },
      {
        "userId": "12345678",
        "authMethod": "SMS_KEY",
        "hasUserInterface": true,
        "displayNameKey": "method.smsKey",
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "CONSENT",
        "hasUserInterface": true,
        "displayNameKey": "method.consent",
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "LOGIN_SCA",
        "hasUserInterface": true,
        "displayNameKey": "method.loginSca",
        "hasMobileToken": true,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "APPROVAL_SCA",
        "hasUserInterface": true,
        "displayNameKey": "method.approvalSca",
        "hasMobileToken": true,
        "config": null
      }
    ]
  }
}
```

<!-- end -->

<!-- begin api POST /user/auth-method -->
### Enable an Authentication Method for Given User

Enable an authentication method for given user and lists all authentication methods enabled for given user after the authentication method has been enabled.

<!-- begin remove -->
#### Enable an authentication method for given user - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/auth-method</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 500  | Server error - unexpected error occurred |

#### Enable an authentication method for given user - request

The request contains three parameters:
* userId - identification of the user
* authMethod - name of the authentication method
* config - configuration of the authentication method

Currently the only supported configuration is in the POWERAUTH_TOKEN method and it contains activationId, as seen on the sample request below.

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "authMethod": "POWERAUTH_TOKEN",
    "config": {
      "activationId": "26c94bf8-f594-4bd8-9c51-93449926b644"
    }
  }
}
```

For other authentication methods use the following configuration:
```
{
  "requestObject": {
    "userId": "12345678",
    "authMethod": "SMS_KEY",
    "config": null
  }
}
```

#### Enable an authentication method for given user - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userAuthMethods": [
      {
        "userId": "12345678",
        "authMethod": "INIT",
        "hasUserInterface": false,
        "displayNameKey": null,
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "USER_ID_ASSIGN",
        "hasUserInterface": false,
        "displayNameKey": null,
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "USERNAME_PASSWORD_AUTH",
        "hasUserInterface": true,
        "displayNameKey": "method.usernamePassword",
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "SHOW_OPERATION_DETAIL",
        "hasUserInterface": true,
        "displayNameKey": "method.showOperationDetail",
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "POWERAUTH_TOKEN",
        "hasUserInterface": true,
        "displayNameKey": "method.powerauthToken",
        "hasMobileToken": true,
        "config": {
          "activationId": "26c94bf8-f594-4bd8-9c51-93449926b644"
        }
      },
      {
        "userId": "12345678",
        "authMethod": "SMS_KEY",
        "hasUserInterface": true,
        "displayNameKey": "method.smsKey",
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "CONSENT",
        "hasUserInterface": true,
        "displayNameKey": "method.consent",
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "LOGIN_SCA",
        "hasUserInterface": true,
        "displayNameKey": "method.loginSca",
        "hasMobileToken": true,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "APPROVAL_SCA",
        "hasUserInterface": true,
        "displayNameKey": "method.approvalSca",
        "hasMobileToken": true,
        "config": null
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api DELETE /user/auth-method -->
### Disable an Authentication Method for Given User

Disable an authentication method for given user and lists all authentication methods enabled for given user after the authentication method has been disabled.

This method has a `POST /user/auth-method/delete` alternative.

<!-- begin remove -->
#### Disable an authentication method for given user - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/auth-method</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/auth-method/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 500  | Server error - unexpected error occurred |

#### Disable an authentication method for given user - request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "authMethod": "POWERAUTH_TOKEN"
  }
}
```

#### Disable an authentication method for given user - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userAuthMethods": [
      {
        "userId": "12345678",
        "authMethod": "INIT",
        "hasUserInterface": false,
        "displayNameKey": null,
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "USER_ID_ASSIGN",
        "hasUserInterface": false,
        "displayNameKey": null,
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "USERNAME_PASSWORD_AUTH",
        "hasUserInterface": true,
        "displayNameKey": "method.usernamePassword",
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "SHOW_OPERATION_DETAIL",
        "hasUserInterface": true,
        "displayNameKey": "method.showOperationDetail",
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "SMS_KEY",
        "hasUserInterface": true,
        "displayNameKey": "method.smsKey",
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "CONSENT",
        "hasUserInterface": true,
        "displayNameKey": "method.consent",
        "hasMobileToken": false,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "LOGIN_SCA",
        "hasUserInterface": true,
        "displayNameKey": "method.loginSca",
        "hasMobileToken": true,
        "config": null
      },
      {
        "userId": "12345678",
        "authMethod": "APPROVAL_SCA",
        "hasUserInterface": true,
        "displayNameKey": "method.approvalSca",
        "hasMobileToken": true,
        "config": null
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api DELETE /auth-method -->
### Delete an Authentication Method

Delete an authentication method. Use only when the authentication method which is to be deleted has no usages.

This method has a `POST /auth-method/delete` alternative.

<!-- begin remove -->
#### Delete an authentication method - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/auth-method</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/auth-method/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `AUTH_METHOD_NOT_FOUND` - authentication method was not found |
| 400  | `DELETE_NOT_ALLOWED` - authentication method removal is not allowed |
| 500  | Server error - unexpected error occurred |

#### Delete an authentication method - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "authMethod": "OTP_CODE"
  }
}
```

#### Delete an authentication method - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "authMethod": "OTP_CODE"
  }
}
```
<!-- end -->

## Operations API

Operation detail contains following data:
* **operationId** - unique ID of the operation, it is either set while creating an operation or it is generated (field is required, value is optional, for generated operation use null as value)
* **operationName** - name of the operations based on the purpose of the operation - different steps are defined for each operation name (required)
* **userId** - ID of user in case the user has been already authorized (optional)
* **organizationId** - ID of organization in case the user has been already authorized (optional)
* **result** - result of the last authentication step: CONTINUE, FAILED or DONE (required)
* **timestampCreated** - timestamp when operation was created (required)
* **timestampExpires** - timestamp when operation expires (required)
* **operationData** - arbitrary string which contains data related to this operation, this data is not used during authorization and authentication (required). Since Web Flow version 0.20.0 the [structure of operation data is specified](./Off-line-Signatures-QR-Code.md#operation-data) for easier interpretation of data in Mobile token.
* **steps** - next steps for the operation (required)
* **history** - operation history with completed authentication steps (required)
* **afsActions** - AFS actions executed for the operation (optional)
* **formData** - data displayed by the UI as well as data gathered from the user responses (required, discussed in details below)
* **chosenAuthMethod** - authentication method chosen in current authentication step (optional)
* **remainingAttempts** - remaining attempts for current authentication step (optional)
* **applicationContext** - application context with information about application which triggered the operation, used when generating the consent form (optional)
* **expired** - whether operation was expired at the time of generating response (optional)

Example of complete operation detail:

```json
{
  "status": "OK",
  "responseObject": {
    "operationId": "b7ecf869-2ebb-44bf-ae0e-0963e9d6d46f",
    "operationName": "authorize_payment_sca",
    "userId": "12345678",
    "organizationId": "RETAIL",
    "accountStatus": "ACTIVE",
    "result": "CONTINUE",
    "timestampCreated": "2019-11-01T15:35:37+0000",
    "timestampExpires": "2019-11-01T15:41:16+0000",
    "operationData": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
    "steps": [
      {
        "authMethod": "CONSENT",
        "params": []
      }
    ],
    "history": [
      {
        "authMethod": "INIT",
        "authResult": "CONTINUE",
        "requestAuthStepResult": "CONFIRMED"
      },
      {
        "authMethod": "LOGIN_SCA",
        "authResult": "CONTINUE",
        "requestAuthStepResult": "CONFIRMED"
      },
      {
        "authMethod": "APPROVAL_SCA",
        "authResult": "CONTINUE",
        "requestAuthStepResult": "CONFIRMED"
      }
    ],
    "afsActions": [
      {
        "action": "LOGIN_INIT",
        "stepIndex": 1,
        "afsLabel": "2FA",
        "afsResponseApplied": false,
        "requestExtras": {},
        "responseExtras": {}
      },
      {
        "action": "LOGIN_AUTH",
        "stepIndex": 1,
        "afsLabel": "2FA",
        "afsResponseApplied": false,
        "requestExtras": {},
        "responseExtras": {}
      },
      {
        "action": "APPROVAL_INIT",
        "stepIndex": 1,
        "afsLabel": "1FA",
        "afsResponseApplied": true,
        "requestExtras": {},
        "responseExtras": {}
      },
      {
        "action": "APPROVAL_AUTH",
        "stepIndex": 1,
        "afsLabel": "2FA",
        "afsResponseApplied": false,
        "requestExtras": {},
        "responseExtras": {}
      }
    ],
    "formData": {
      "title": {
        "id": "operation.title",
        "message": null
      },
      "greeting": {
        "id": "operation.greeting",
        "message": null
      },
      "summary": {
        "id": "operation.summary",
        "message": null
      },
      "config": [],
      "banners": [],
      "parameters": [
        {
          "type": "AMOUNT",
          "id": "operation.amount",
          "label": null,
          "valueFormatType": "AMOUNT",
          "formattedValues": {},
          "amount": 100,
          "currency": "CZK",
          "currencyId": "operation.currency"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.account",
          "label": null,
          "valueFormatType": "ACCOUNT",
          "formattedValues": {},
          "value": "238400856/0300"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.dueDate",
          "label": null,
          "valueFormatType": "DATE",
          "formattedValues": {},
          "value": "2019-06-29"
        },
        {
          "type": "NOTE",
          "id": "operation.note",
          "label": null,
          "valueFormatType": "TEXT",
          "formattedValues": {},
          "note": "Utility Bill Payment - 05/2019"
        }
      ],
      "dynamicDataLoaded": false,
      "userInput": {
        "smsFallback.enabled": "true",
        "operation.bankAccountChoice": "CZ4012340000000012345678",
        "operation.bankAccountChoice.disabled": "true"
      }
    },
    "chosenAuthMethod": "CONSENT",
    "remainingAttempts": 5,
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
    },
    "expired": false
  }
}
```

### Operation formData

Operations contain formData which is a generic structure for storing input and output data for the operation.

The formData contains following sections:
* **static data** - this data is set when the operation is created (required)
* **dynamic data** - this data is added as the operation progresses (optional)
* **user input** - this data contains gathered inputs from the user as the authentication and authorization progresses (optional)

The **static part of formData** contains data related to the operation known when operation is initiated. For instance in case of a payment, the static data contains information about the payment such as title, amount, currency, target account and message to display to the user in the following structure:

```json
{
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
          }
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
          "type": "HEADING",
          "id": "operation.heading",
          "label": null,
          "valueFormatType": "LOCALIZED_TEXT",
          "formattedValues": {
            "value": "Confirm Payment"
          },            
          "value": "operation.title"
        }
      ],
      "dynamicDataLoaded": false,
      "userInput": { 
      }
   }
}
```

The usage of static formData:
* **title** - displayed as title on the page with operation details
  * field is required
  * id is the localization key
  * value is the localized text displayed on the page
* **greeting** - displayed as a greeting message in the web application without operation details
  * field is required
  * id is the localization key
  * value is the localized text displayed on the page
* **summary** - displayed as a summary message in the push message sent to mobile device
  * field is required
  * id is the localization key
  * value is the localized text displayed in the push message
* **config** - configures individual form fields (e.g. default values, enabled/disabled state, etc.)
  * field is required, however the config list can be empty
* **banners** - banners which can be displayed above form
* **parameters** - operation parameters which are displayed on the page with operation details
  * field is required, however the parameter list can be empty

Following parameter types are available:
* **AMOUNT** - contains information about amount in this operation including currency
  * field is optional
  * id is used both for field identification as well as the localization key  
  * label is the displayed localized text
  * valueFormatType specifies the format type
  * formattedValue is the formatted value based on format type
  * amount is displayed next to the label
  * currency is displayed next to the amount
  * currencyId is used internally for localization
* **NOTE** - contains text message related to the operation
  * field is optional
  * id is used both for field identification as well as the localization key  
  * label is the displayed localized text
  * valueFormatType specifies the format type
  * formattedValue is the formatted value based on format type
  * note is the text message displayed next to the label
* **KEY_VALUE**
  * field is optional
  * id is used both for field identification as well as the localization key  
  * label is the displayed localized text
  * valueFormatType specifies the format type
  * formattedValue is the formatted value based on format type
  * value is the text displayed next to the label
* **HEADING**
  * field is optional
  * id is used both for field identification as well as the localization key  
  * label is ignored
  * value contains heading text
  * valueFormatType specifies the format type
  * formattedValue is the formatted heading text based on format type

The **dynamic part of formData** contains additional data which is loaded once the user is authenticated. For instance in case of a payment, the dynamic data can contain choice of bank accounts available for the user with their balances:

```json
{
  "formData": {
    "parameters": [
      {
        "type": "BANK_ACCOUNT_CHOICE",
        "id": "operation.bankAccountChoice",
        "label": "From Your Account",
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
            "balance": 158121.1,
            "currency": "CZK",
            "usableForPayment": false,
            "unusableForPaymentReason": null
          },
          {
            "number": "44444444/1111",
            "accountId": "CZ4011110000000044444444",
            "name": "Spořící účet v EUR",
            "balance": 1.9,
            "currency": "EUR",
            "usableForPayment": false,
            "unusableForPaymentReason": "Low account balance"
          }
        ],
        "enabled": true,
        "defaultValue": "CZ4012340000000012345678"
      }
    ]
  }
}
```
Following parameter types are available:
* **BANK_ACCOUNT_CHOICE**
  * field is optional
  * id is used both for field identification as well as the localization key
  * label is the displayed localized text
  * bankAccounts list is required when BANK_ACCOUNT_CHOICE parameter is specified, however it can be empty

Bank account details:
  * **number** - required, account number in human readable format
  * **name** - required, account name
  * **balance** - required, account balance
  * **currency** - required, account currency
  * **usableForPayment** - required, whether account can be used for payment, in case value is false, unusableForPaymentReason is displayed
  * **unusableForPaymentReason** - optional when usableForPayment = false, otherwise it is required, field explains reason why account is unusable for payment

When dynamic form data is loaded, the formData structure contains following data:

```json
{
  "formData": {
    "dynamicDataLoaded": true
  }
}
```

Dynamic formData may not be loaded because it is required only for specific steps such as operation review. In this case the value is:
```json
{
  "formData": {
    "dynamicDataLoaded": false
  }
}
```

The form fields can be configured in the **config** section as follows:
```json
{
  "formData": {
    "config" : [ {
      "id" : "operation.bankAccountChoice",
      "enabled" : false,
      "defaultValue" : "CZ4043210000000087654321"
    } ]
  }
}
```

Each configuration item contains following fields:
  * **id** - id is used for field identification, same as id used in parameters
  * **enabled** - whether the field is enabled or disabled (default value = true)
  * **defaultValue** - default value of the field (default value = null)

The formData uses userInput JSON structure while **gathering input from the user** as the operation progresses:
```json
{
  "formData": {
    "userInput": {
      "operation.bankAccountChoice": "CZ4012340000000012345678",
      "operation.bankAccountChoice.disabled": "true"
    }
  }
}
```

The userInput part of formData is optional - empty value of userInput is:
```json
{
  "formData": {
    "userInput": {
    }
  }
}
```

Chosen authentication method for current step is stored in formData in case it is available:
```json
{
  "formData": {
    "chosenAuthMethod": "POWERAUTH_TOKEN"
  }
}
```

Null value is used when authentication method has not been chosen for current step:
```json
{
  "formData": {
    "chosenAuthMethod": null
  }
}
```

<!-- begin api POST /operation -->
### Create an Operation

Create an operation in Next Step server.

Documentation for operation data is available [in a separate document](https://developers.wultra.com/docs/2019.05/powerauth-webflow/Operation-Data).

<!-- begin remove -->
#### Create an operation - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OPERATION_ALREADY_EXISTS` - operation with specified identifier already exists |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `ORGANIZATION_NOT_FOUND` - organization specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Create an operation - request (AISP)

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationName": "login",
    "operationData": "A2",
    "externalTransactionId": "1234567890",
    "formData": {
      "title": {
        "id": "login.title"
      },
      "greeting": {
        "id": "login.greeting"
      },
      "summary": {
        "id": "login.summary"
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
```

#### Create an operation - request (PISP)

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationName": "authorize_payment",
    "operationId": null,
    "organizationId": null,
    "externalTransactionId": "1234567890",
    "operationData": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
    "params": [],
    "formData": {
      "title": {
        "id": "operation.title",
        "value": null
      },
      "greeting": {
        "id": "operation.greeting",
        "value": null
      },
      "summary": {
        "id": "operation.summary",
        "value": null
      },
      "config": [],
      "parameters": [
        {
          "type": "AMOUNT",
          "id": "operation.amount",
          "label": null,
          "valueFormatType": "AMOUNT",
          "formattedValues": {},
          "amount": 100,
          "currency": "CZK",
          "currencyId": "operation.currency"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.account",
          "label": null,
          "valueFormatType": "ACCOUNT",
          "formattedValues": {},
          "value": "238400856/0300"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.dueDate",
          "label": null,
          "valueFormatType": "DATE",
          "formattedValues": {},
          "value": "2019-06-29"
        },
        {
          "type": "NOTE",
          "id": "operation.note",
          "label": null,
          "valueFormatType": "TEXT",
          "formattedValues": {},
          "note": "Utility Bill Payment - 05/2019"
        }
      ]
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
```

#### Create an operation - response (AISP)

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`
    
```json
{
  "status": "OK",
  "responseObject": {
    "operationId": "ec039314-7560-470a-b226-116c712e8fb3",
    "operationName": "login",
    "organizationId": null,
    "externalTransactionId": "1234567890",
    "result": "CONTINUE",
    "resultDescription": null,
    "timestampCreated": "2019-07-30T12:51:28+0000",
    "timestampExpires": "2019-07-30T12:56:28+0000",
    "operationData": null,
    "steps": [
      {
        "authMethod": "USER_ID_ASSIGN",
        "params": []
      },
      {
        "authMethod": "USERNAME_PASSWORD_AUTH",
        "params": []
      }
    ],
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
      "dynamicDataLoaded": false,
      "userInput": {}
    },
    "expired": false
  }
}
```

#### Create an operation - response (PISP)

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "operationId": "f415a617-f7c0-4800-8436-f85eb075eb6f",
    "operationName": "authorize_payment",
    "organizationId": null,
    "externalTransactionId": "1234567890",
    "result": "CONTINUE",
    "resultDescription": null,
    "timestampCreated": "2019-07-30T12:52:35+0000",
    "timestampExpires": "2019-07-30T12:57:35+0000",
    "operationData": null,
    "steps": [
      {
        "authMethod": "USER_ID_ASSIGN",
        "params": []
      },
      {
        "authMethod": "USERNAME_PASSWORD_AUTH",
        "params": []
      }
    ],
    "formData": {
      "title": {
        "id": "operation.title",
        "message": null
      },
      "greeting": {
        "id": "operation.greeting",
        "message": null
      },
      "summary": {
        "id": "operation.summary",
        "message": null
      },
      "config": [],
      "banners": [],
      "parameters": [
        {
          "type": "AMOUNT",
          "id": "operation.amount",
          "label": null,
          "valueFormatType": "AMOUNT",
          "formattedValues": {},
          "amount": 100,
          "currency": "CZK",
          "currencyId": "operation.currency"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.account",
          "label": null,
          "valueFormatType": "ACCOUNT",
          "formattedValues": {},
          "value": "238400856/0300"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.dueDate",
          "label": null,
          "valueFormatType": "DATE",
          "formattedValues": {},
          "value": "2019-06-29"
        },
        {
          "type": "NOTE",
          "id": "operation.note",
          "label": null,
          "valueFormatType": "TEXT",
          "formattedValues": {},
          "note": "Utility Bill Payment - 05/2019"
        }
      ],
      "dynamicDataLoaded": false,
      "userInput": {}
    },
    "expired": false
  }
}
```
<!-- end -->

<!-- begin api PUT /operation -->
### Update an Operation

Update an operation in Next Step server.

This method has a `POST /operation/update` alternative.

<!-- begin remove -->
#### Update an operation - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `AUTH_METHOD_NOT_FOUND` - authentication method specified in request was not found |
| 400  | `OPERATION_ALREADY_FINISHED` - operation is already in `DONE` state |
| 400  | `OPERATION_ALREADY_FAILED` - operation is already in `FAILED` state |
| 400  | `OPERATION_ALREADY_CANCELED` - operation is already in `FAILED/CANCELED` state |
| 400  | `OPERATION_NOT_VALID` - operation which is being updated is not valid |
| 400  | `OPERATION_NOT_FOUND` - operation with specified identifier was not found |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `ORGANIZATION_NOT_FOUND` - organization specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Update an operation - request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationId": "4e02b39b-1ecb-440a-a942-cc27bc07d203",
    "userId": "12345678",
    "organizationId": "RETAIL",
    "authMethod": "USERNAME_PASSWORD_AUTH",
    "authStepResult": "CONFIRMED",
    "authStepResultDescription": null,
    "params": []
  }
}
```

#### Update an operation - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "operationId": "4e02b39b-1ecb-440a-a942-cc27bc07d203",
    "operationName": "authorize_payment",
    "userId": "12345678",
    "organizationId": "RETAIL",
    "externalTransactionId": "1234567890",
    "result": "CONTINUE",
    "resultDescription": null,
    "timestampCreated": "2018-06-28T12:20:28+0000",
    "timestampExpires": "2018-06-28T12:20:43+0000",
    "operationData": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
    "steps": [
      {
        "authMethod": "SMS_KEY",
        "params": []
      }
    ],
    "formData": {
      "title": {
        "id": "operation.title",
        "value": null
      },
      "greeting": {
        "id": "operation.greeting",
        "value": null
      },
      "summary": {
        "id": "operation.summary",
        "value": null
      },
      "config": [],
      "parameters": [
        {
          "type": "AMOUNT",
          "id": "operation.amount",
          "label": null,
          "valueFormatType": "AMOUNT",
          "formattedValues": {},
          "amount": 100,
          "currency": "CZK",
          "currencyId": "operation.currency"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.account",
          "label": null,
          "valueFormatType": "ACCOUNT",
          "formattedValues": {},
          "value": "238400856/0300"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.dueDate",
          "label": null,
          "valueFormatType": "DATE",
          "formattedValues": {},
          "value": "2019-06-29"
        },
        {
          "type": "NOTE",
          "id": "operation.note",
          "label": null,
          "valueFormatType": "TEXT",
          "formattedValues": {},
          "note": "Utility Bill Payment - 05/2019"
        }
      ]
    },
    "expired": false
  }
}
```
<!-- end -->

<!-- begin api GET /operation/detail -->
### Operation Detail

Retrieve detail of an operation in the Next Step server.

This method has a `POST /operation/detail` alternative.

<!-- begin remove -->
#### Operation detail - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/detail</code></td>
    </tr>
</table>

Alternative with `POST` method

<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/detail</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OPERATION_NOT_VALID` - operation is not valid |
| 400  | `OPERATION_NOT_FOUND` - operation with specified identifier was not found |
| 500  | Server error - unexpected error occurred |

#### Operation detail - request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject" : {
    "operationId" : "0861a423-ac06-4bcb-a426-2052872163d3"
  }
}
```

#### Operation detail - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "operationId": "0861a423-ac06-4bcb-a426-2052872163d3",
    "operationName": "authorize_payment_sca",
    "userId": "12345678",
    "organizationId": "RETAIL",
    "result": "CONTINUE",
    "timestampCreated": "2019-07-30T12:36:19+0000",
    "timestampExpires": "2019-07-30T12:41:40+0000",
    "operationData": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
    "steps": [
      {
        "authMethod": "LOGIN_SCA",
        "params": []
      }
    ],
    "history": [
      {
        "authMethod": "INIT",
        "authResult": "CONTINUE",
        "requestAuthStepResult": "CONFIRMED"
      }
    ],
    "formData": {
      "title": {
        "id": "operation.title",
        "message": null
      },
      "greeting": {
        "id": "operation.greeting",
        "message": null
      },
      "summary": {
        "id": "operation.summary",
        "message": null
      },
      "config": [],
      "banners": [],
      "parameters": [
        {
          "type": "AMOUNT",
          "id": "operation.amount",
          "label": null,
          "valueFormatType": "AMOUNT",
          "formattedValues": {},
          "amount": 100,
          "currency": "CZK",
          "currencyId": "operation.currency"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.account",
          "label": null,
          "valueFormatType": "ACCOUNT",
          "formattedValues": {},
          "value": "238400856/0300"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.dueDate",
          "label": null,
          "valueFormatType": "DATE",
          "formattedValues": {},
          "value": "2019-06-29"
        },
        {
          "type": "NOTE",
          "id": "operation.note",
          "label": null,
          "valueFormatType": "TEXT",
          "formattedValues": {},
          "note": "Utility Bill Payment - 05/2019"
        }
      ],
      "dynamicDataLoaded": false,
      "userInput": {
        "smsFallback.enabled": "true"
      }
    },
    "chosenAuthMethod": null,
    "remainingAttempts": 3,
    "applicationContext": {
      "id": "democlient",
      "name": "Demo application",
      "description": "Web Flow demo application",
      "originalScopes": ["pisp"],
      "extras": {
        "applicationOwner": "Wultra"
      }
    },
    "expired": false
  }
}
```
<!-- end -->

<!-- begin api GET /user/operation -->
### List Pending Operations

List pending operation for given user and authentication method.

This method has a `POST /user/operation/list` alternative.

<!-- begin remove -->
#### List pending operations - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/operation</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/operation/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 500  | Server error - unexpected error occurred |

#### List pending operations - request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject" : {
    "userId" : "12345678",
    "mobileTokenOnly" : true
  }
}
```

#### List pending operations - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": [
    {
      "operationId": "d7d9910e-b047-4352-b2b3-f1fa30d03f3a",
      "operationName": "authorize_payment_sca",
      "userId": "12345678",
      "organizationId": "RETAIL",
      "accountStatus": "ACTIVE",
      "result": "CONTINUE",
      "timestampCreated": "2019-07-30T12:57:28+0000",
      "timestampExpires": "2019-07-30T13:02:28+0000",
      "operationData": "A1*A100CZK*Q238400856/0300**D20190629*NUtility Bill Payment - 05/2019",
      "steps": [],
      "history": [
        {
          "authMethod": "INIT",
          "authResult": "CONTINUE",
          "requestAuthStepResult": "CONFIRMED"
        }
      ],
      "formData": {
        "title": {
          "id": "operation.title",
          "message": null
        },
        "greeting": {
          "id": "operation.greeting",
          "message": null
        },
        "summary": {
          "id": "operation.summary",
          "message": null
        },
        "config": [],
        "banners": [],
        "parameters": [
          {
            "type": "AMOUNT",
            "id": "operation.amount",
            "label": null,
            "valueFormatType": "AMOUNT",
            "formattedValues": {},
            "amount": 100,
            "currency": "CZK",
            "currencyId": "operation.currency"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.account",
            "label": null,
            "valueFormatType": "ACCOUNT",
            "formattedValues": {},
            "value": "238400856/0300"
          },
          {
            "type": "KEY_VALUE",
            "id": "operation.dueDate",
            "label": null,
            "valueFormatType": "DATE",
            "formattedValues": {},
            "value": "2019-06-29"
          },
          {
            "type": "NOTE",
            "id": "operation.note",
            "label": null,
            "valueFormatType": "TEXT",
            "formattedValues": {},
            "note": "Utility Bill Payment - 05/2019"
          }
        ],
        "dynamicDataLoaded": false,
        "userInput": {}
      },
      "chosenAuthMethod": "LOGIN_SCA",
      "remainingAttempts": null,
      "applicationContext": {
        "id": "democlient",
        "name": "Demo application",
        "description": "Web Flow demo application",
        "originalScopes": ["pisp"],
        "extras": {
          "applicationOwner": "Wultra"
        }
      },
      "expired": false
    }
  ]
}
```
<!-- end -->

<!-- begin api POST /operation/lookup/external -->
### Lookup Operations by External Transaction ID

Find all operations with matching external transaction ID.

<!-- begin remove -->
#### Lookup operations by external transaction ID - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/lookup/external</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 500  | Server error - unexpected error occurred |

#### Lookup operations by external transaction ID - request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject" : {
    "externalTransactionId" : "12345678"
  }
}
```

#### Lookup operations by external transaction ID - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "operations": [
      {
        "operationId": "e6d3a5e7-e291-42f7-bd46-49d9fbac4282",
        "operationName": "authorize_payment",
        "userId": null,
        "organizationId": "RETAIL",
        "accountStatus": "ACTIVE",
        "externalTransactionId": "12345678",
        "result": "CONTINUE",
        "timestampCreated": "2020-01-28T15:58:11+0000",
        "timestampExpires": "2020-01-28T16:03:11+0000",
        "operationData": "A1*A100CZK*Q238400856/0300**D20170629*NUtility Bill Payment - 05/2017",
        "steps": [],
        "history": [
          {
            "authMethod": "INIT",
            "authResult": "CONTINUE",
            "requestAuthStepResult": "CONFIRMED"
          }
        ],
        "afsActions": [],
        "formData": {
          "title": {
            "id": "operation.title",
            "message": null
          },
          "greeting": {
            "id": "operation.greeting",
            "message": null
          },
          "summary": {
            "id": "operation.summary",
            "message": null
          },
          "config": [],
          "banners": [],
          "parameters": [
            {
              "type": "AMOUNT",
              "id": "operation.amount",
              "label": null,
              "valueFormatType": "AMOUNT",
              "formattedValues": {},
              "amount": 100,
              "currency": "CZK",
              "currencyId": "operation.currency"
            },
            {
              "type": "KEY_VALUE",
              "id": "operation.account",
              "label": null,
              "valueFormatType": "ACCOUNT",
              "formattedValues": {},
              "value": "238400856/0300"
            },
            {
              "type": "KEY_VALUE",
              "id": "operation.dueDate",
              "label": null,
              "valueFormatType": "DATE",
              "formattedValues": {},
              "value": "2017-06-29"
            },
            {
              "type": "NOTE",
              "id": "operation.note",
              "label": null,
              "valueFormatType": "TEXT",
              "formattedValues": {},
              "note": "Utility Bill Payment - 05/2017"
            }
          ],
          "dynamicDataLoaded": false,
          "userInput": {}
        },
        "chosenAuthMethod": null,
        "remainingAttempts": null,
        "applicationContext": {
          "id": "democlient",
          "name": "Demo application",
          "description": "Web Flow demo application",
          "originalScopes": ["pisp"],
          "extras": {
            "applicationOwner": "Wultra"
          }
        },
        "expired": false
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api PUT /operation/formData -->
### Update Operation formData

Update operation formData for given operation. Only the userInput part of formData can be currently updated by the clients.

This method has a `POST /operation/formData/update` alternative.

<!-- begin remove -->
#### Update operation formData - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/formData</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/formData/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OPERATION_NOT_FOUND` - operation with specified identifier was not found |
| 500  | Server error - unexpected error occurred |

#### Update operation formData - request

- Headers:
    - `Content-Type: application/json`

```json
{
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
        "formattedValue": "100.00 CZK",
        "amount": 100,
        "currency": "CZK",
        "currencyId": "operation.currency"
      },
      {
        "type": "KEY_VALUE",
        "id": "operation.account",
        "label": "To Account",
        "valueFormatType": "ACCOUNT",
        "formattedValue": "238400856/0300",
        "value": "238400856/0300"
      },
      {
        "type": "KEY_VALUE",
        "id": "operation.dueDate",
        "label": "Due Date",
        "valueFormatType": "DATE",
        "formattedValue": "Jun 29, 2019",
        "value": "2019-06-29"
      },
      {
        "type": "NOTE",
        "id": "operation.note",
        "label": "Note",
        "valueFormatType": "TEXT",
        "formattedValue": "Utility Bill Payment - 05/2019",
        "note": "Utility Bill Payment - 05/2019"
      },
      {
        "type": "BANK_ACCOUNT_CHOICE",
        "id": "operation.bankAccountChoice",
        "label": "From Your Account",
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
            "balance": 158121.1,
            "currency": "CZK",
            "usableForPayment": false,
            "unusableForPaymentReason": null
          },
          {
            "number": "44444444/1111",
            "accountId": "CZ4011110000000044444444",
            "name": "Spořící účet v EUR",
            "balance": 1.9,
            "currency": "EUR",
            "usableForPayment": false,
            "unusableForPaymentReason": "Low account balance"
          }
        ],
        "enabled": true,
        "defaultValue": "CZ4012340000000012345678"
      }
    ],
    "dynamicDataLoaded": true,
    "userInput": {
      "operation.bankAccountChoice": "CZ4012340000000012345678"
    }
  }
}
```

#### Update operation formData - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status" : "OK"
}
```
<!-- end -->

<!-- begin api PUT /operation/application -->
### Update Application Context for an Operation

Update application context for an operation.

This method has a `POST /operation/application/update` alternative.

<!-- begin remove -->
#### Update application context for an operation - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/application</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/application/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OPERATION_NOT_FOUND` - operation with specified identifier was not found |
| 500  | Server error - unexpected error occurred |

#### Update application context for an operation - request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationId": "3e87f071-2f08-4341-9034-47cb5f8a3fb4",
    "applicationContext": {
      "id": "BANK_ABC_PROD",
      "name": "Bank ABC",
      "description": "Authorization for Bank ABC",
      "originalScopes": ["SCOPE_1", "SCOPE_2", "SCOPE_3"],
      "extras": {
        "applicationOwner": "BANK_ABC"
      }
    }
  }
}
```

#### Update application context for an operation - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status" : "OK"
}
```
<!-- end -->

<!-- begin api PUT /operation/user -->
### Update User for an Operation

Update user ID, organization ID and account status for an operation.

This method has a `POST /operation/user/update` alternative.

<!-- begin remove -->
#### Update user for an operation - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/user</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/user/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OPERATION_NOT_FOUND` - operation with specified identifier was not found |
| 400  | `ORGANIZATION_NOT_FOUND` - organization specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Update user for an operation - request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationId": "0a044408-aea0-433a-80cf-6371dc2a76c0",
    "userId": "12345678",
    "organizationId": "RETAIL",
    "accountStatus": "ACTIVE"
  }
}
```

#### Update user for an operation - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status" : "OK"
}
```
<!-- end -->

<!-- begin api PUT /operation/chosenAuthMethod -->
### Set Chosen Authentication Method

Set chosen authentication method for current operation step.

This method has a `POST /operation/chosenAuthMethod/update` alternative.

<!-- begin remove -->
#### Set chosen authentication method - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/chosenAuthMethod</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/chosenAuthMethod/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `OPERATION_NOT_VALID` - operation which is being updated is not valid |
| 400  | `OPERATION_NOT_FOUND` - operation with specified identifier was not found |
| 500  | Server error - unexpected error occurred |

#### Set chosen authentication method - request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationId": "3e87f071-2f08-4341-9034-47cb5f8a3fb4",
    "chosenAuthMethod": "POWERAUTH_TOKEN"
  }
}
```

#### Set chosen authentication method - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status" : "OK"
}
```

<!-- begin api PUT /operation/mobileToken/status -->
### Update Mobile Token Status for an Operation

Set whether mobile token is active for an operation.

This method has a `POST /operation/mobileToken/status/update` alternative.

<!-- begin remove -->
#### Update mobile token status for an operation - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/mobileToken/status</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/mobileToken/status/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OPERATION_NOT_VALID` - operation which is being updated is not valid |
| 400  | `OPERATION_NOT_FOUND` - operation with specified identifier was not found |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 500  | Server error - unexpected error occurred |

#### Update mobile token status for an operation - request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationId": "1ee2d165-1926-4a77-be5f-82ec26f12b97",
    "mobileTokenActive": true
  }
}
```

#### Update mobile token status for an operation - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status" : "OK"
}
```
<!-- end -->

<!-- begin api GET /operation/mobileToken/config/detail -->
### Get Mobile Token Configuration

Get whether mobile token is enabled for given user ID, operation name and authentication method.

This method has a `POST /operation/mobileToken/config/detail` alternative.

<!-- begin remove -->
#### Get mobile token configuration - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/mobileToken/config/detail</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/mobileToken/config/detail</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 500  | Server error - unexpected error occurred |

#### Get mobile token configuration - request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "12345678",
    "operationName": "login",
    "authMethod": "LOGIN_SCA"
  }
}
```

#### Get mobile token configuration - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "mobileTokenEnabled": true
  }
}
```
<!-- end -->

<!-- begin api POST /operation/afs/action -->
### Store Result of an AFS Action

Store result of an AFS action for an operation.

<!-- begin remove -->
#### Store result of an AFS action - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/afs/action</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 500  | Server error - unexpected error occurred |

#### Store result of an AFS action - request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationId": "47a74437-83f9-4567-8c9e-270bea98d9de",
    "afsAction": "APPROVAL_INIT",
    "stepIndex": 1,
    "requestAfsExtras": "{}",
    "afsResponseApplied": true,
    "afsLabel": "1FA",
    "responseAfsExtras": "{}",
    "timestampCreated": 1572618429867
  }
}
```

#### Store result of an AFS action - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status" : "OK"
}
```
<!-- end -->

<!-- begin api POST /operation/config/list -->
### List Operation Configurations

Retrieve list of operation configurations.

<!-- begin remove -->
#### List operation configurations - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/config/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 500  | Server error - unexpected error occurred |

#### List operation configurations - request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
  }
}
```

#### List operation configurations - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "operationConfigs": [
      {
        "operationName": "authorize_payment",
        "templateVersion": "A",
        "templateId": 1,
        "mobileTokenMode": "{\"type\":\"2FA\",\"variants\":[\"possession_knowledge\",\"possession_biometry\"]}"
      },
      {
        "operationName": "authorize_payment_sca",
        "templateVersion": "A",
        "templateId": 1,
        "mobileTokenMode": "{\"type\":\"2FA\",\"variants\":[\"possession_knowledge\",\"possession_biometry\"]}"
      },
      {
        "operationName": "login",
        "templateVersion": "A",
        "templateId": 2,
        "mobileTokenMode": "{\"type\":\"2FA\",\"variants\":[\"possession_knowledge\",\"possession_biometry\"]}"
      },
      {
        "operationName": "login_sca",
        "templateVersion": "A",
        "templateId": 2,
        "mobileTokenMode": "{\"type\":\"2FA\",\"variants\":[\"possession_knowledge\",\"possession_biometry\"]}"
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api POST /operation/config -->
### Create an Operation Configuration

Create an operation configuration.

<!-- begin remove -->
#### Create an operation configuration - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/config</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OPERATION_CONFIG_ALREADY_EXISTS` - operation configuration already exists |
| 500  | Server error - unexpected error occurred |

#### Create an operation configuration - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationName": "login_other",
    "templateVersion": "A",
    "templateId": 2,
    "mobileTokenEnabled": false,
    "mobileTokenMode": "{}",
    "afsEnabled": false,
    "afsConfigId": null,
    "expirationTime": 300000
  }
}
```

#### Create an operation configuration - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "operationName": "login_other",
    "templateVersion": "A",
    "templateId": 2,
    "mobileTokenEnabled": false,
    "mobileTokenMode": "{}",
    "afsEnabled": false,
    "afsConfigId": null,
    "expirationTime": 300000
  }
}
```
<!-- end -->

<!-- begin api GET /operation/config/detail -->
### Get Operation Configuration Detail

Get operation configuration detail.

This method has a `POST /operation/config/detail` alternative.

<!-- begin remove -->
#### Get operation configuration detail - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/config/detail</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/config/detail</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OPERATION_CONFIG_NOT_FOUND` - operation configuration was not found |
| 500  | Server error - unexpected error occurred |

#### Get operation configuration detail - request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationName": "login"
  }
}
```

#### Get operation configuration detail - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "operationName": "login",
    "templateVersion": "A",
    "templateId": 2,
    "mobileTokenMode": "{\"type\":\"2FA\",\"variants\":[\"possession_knowledge\",\"possession_biometry\"]}"
  }
}
```
<!-- end -->

<!-- begin api DELETE /operation/config -->
### Delete an Operation Configuration

Delete an operation configuration.

This method has a `POST /operation/config/delete` alternative.

<!-- begin remove -->
#### Delete an operation configuration - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/config</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/config/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OPERATION_CONFIG_NOT_FOUND` - operation configuration was not found |
| 400  | `DELETE_NOT_ALLOWED` - operation configuration removal is not allowed |
| 500  | Server error - unexpected error occurred |

#### Delete an operation configuration - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationName": "login",
    "authMethod": "POWERAUTH_TOKEN"
  }
}
```

#### Delete an operation configuration - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "operationName": "login"
  }
}
```
<!-- end -->

<!-- begin api POST /operation/auth-method/config -->
### Create an Operation and Authentication Method Configuration

Create a configuration for an operation and an authentication method.

<!-- begin remove -->
#### Create an operation and authentication method configuration - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/auth-method/config</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OPERATION_METHOD_CONFIG_ALREADY_EXISTS` - operation and authentication method configuration already exists |
| 400  | `OPERATION_CONFIG_NOT_FOUND` - operation configuration was not found |
| 400  | `AUTH_METHOD_NOT_FOUND` - authentication method was not found |
| 500  | Server error - unexpected error occurred |

#### Create an operation and authentication method configuration - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationName": "login_other",
    "authMethod": "LOGIN_SCA",
    "maxAuthFails": 3
  }
}
```

#### Create an operation and authentication method configuration - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "operationName": "login_other",
    "authMethod": "LOGIN_SCA",
    "maxAuthFails": 3
  }
}
```
<!-- end -->

<!-- begin api GET /operation/auth-method/config/detail -->
### Get an Operation and Authentication Method Configuration Detail

Get configuration for an operation and an authentication method.

This method has a `POST /operation/auth-method/config/detail` alternative.  

<!-- begin remove -->
#### Get an operation and authentication method configuration detail - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/auth-method/config/detail</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/auth-method/config/detail</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OPERATION_METHOD_CONFIG_NOT_FOUND` - operation and authentication method configuration was not found |
| 500  | Server error - unexpected error occurred |

#### Get an operation and authentication method configuration detail - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationName": "login_other",
    "authMethod": "LOGIN_SCA"
  }
}
```

#### Get an operation and authentication method configuration detail - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "operationName": "login_other",
    "authMethod": "LOGIN_SCA",
    "maxAuthFails": 3
  }
}
```
<!-- end -->

<!-- begin api DELETE /operation/auth-method/config -->
### Delete an Operation and Authentication Method Configuration

Delete a configuration for an operation and an authentication method.

This method has a `POST /operation/auth-method/config/delete` alternative.

<!-- begin remove -->
#### Delete an operation and authentication method configuration detail - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/auth-method/config</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OPERATION_METHOD_CONFIG_NOT_FOUND` - operation and authentication method configuration was not found |
| 500  | Server error - unexpected error occurred |

#### Delete an operation and authentication method configuration detail - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationName": "login_other",
    "authMethod": "LOGIN_SCA"
  }
}
```

#### Delete an operation and authentication method configuration detail - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "operationName": "login_other",
    "authMethod": "LOGIN_SCA"
  }
}
```
<!-- end -->

## Organizations API

<!-- begin api POST /organization -->
### Create an Organization

Create an organization.

<!-- begin remove -->
#### Create an organization - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/organization</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `ORGANIZATION_ALREADY_EXISTS` - organization with identifier specified in the request already exists |
| 500  | Server error - unexpected error occurred |

#### Create an organization - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "organizationId": "CORPORATE",
    "displayNameKey": "organization.corp",
    "orderNumber": 3,
    "default": false,
    "defaultCredentialName": "CRED_CORP",
    "defaultOtpName": "OTP_CORP"
  }
}
```

#### Create an organization - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "organizationId": "CORPORATE",
    "displayNameKey": "organization.corp",
    "orderNumber": 3,
    "default": false,
    "defaultCredentialName": "CRED_CORP",
    "defaultOtpName": "OTP_CORP"   
  }
}
```
<!-- end -->

<!-- begin api GET /organization -->
### List Organizations

List all organizations configured on the server.

This method has a `POST /organization/list` alternative.

<!-- begin remove -->
#### List organizations - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/organization</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/organization/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 500  | Server error - unexpected error occurred |

#### List organizations - request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
  }
}
```

#### List organizations - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "organizations": [
      {
        "organizationId": "RETAIL",
        "displayNameKey": "organization.retail",
        "orderNumber": 1,
        "default": true,
        "defaultCredentialName": "CRED_RETAIL",
        "defaultOtpName": "OTP_RETAIL"        
      },
      {
        "organizationId": "SME",
        "displayNameKey": "organization.sme",
        "orderNumber": 2,
        "default": false,
        "defaultCredentialName": "CRED_SME",
        "defaultOtpName": "OTP_SME"
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api GET /organization/detail -->
### Organization Detail

Get detail of an organization configured on the server.

This method has a `POST /organization/detail` alternative.

<!-- begin remove -->
#### Organization detail - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/organization/detail</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/organization/detail</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `ORGANIZATION_NOT_FOUND` - organization specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Organization detail - request

- Headers:
    - `Content-Type: application/json`

```json
{
	"requestObject": {
		"organizationId": "RETAIL"
	}
}
```

#### Organization detail - response

- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "organizationId": "RETAIL",
    "displayNameKey": "organization.retail",
    "orderNumber": 1,
    "default": true,
    "defaultCredentialName": "CRED_RETAIL",
    "defaultOtpName": "OTP_RETAIL"
  }
}
```

<!-- end -->

<!-- begin api DELETE /organization -->
### Delete an Organization

Delete an organization.

This method has a `POST /organization/delete` alternative.

<!-- begin remove -->
#### Delete an organization - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/organization</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/organization/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `ORGANIZATION_NOT_FOUND` - organization specified in the request was not found |
| 400  | `DELETE_NOT_ALLOWED` - organization removal is not allowed |
| 500  | Server error - unexpected error occurred |

#### Delete an organization - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "organizationId": "CORPORATE"
  }
}
```

#### Delete an organization - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "organizationId": "CORPORATE"
  }
}
```

<!-- end -->

## Step Definitions API

<!-- begin api POST /step/definition -->
### Create a Step definition

Create a step definition.

<!-- begin remove -->
#### Create a step definition - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/step/definition</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `STEP_DEFINITION_ALREADY_EXISTS` - step definition with identifier specified in the request already exists |
| 500  | Server error - unexpected error occurred |

#### Create a step definition - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "stepDefinitionId": 1,
    "operationName": "login",
    "operationType": "CREATE",
    "requestAuthMethod": null,
    "requestAuthStepResult": null,
    "responsePriority": 1,
    "responseAuthMethod": "USER_ID_ASSIGN",
    "responseResult": "CONTINUE"
  }
}
```

#### Create a step definition - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "stepDefinitionId": 1,
    "operationName": "login",
    "operationType": "CREATE",
    "requestAuthMethod": null,
    "requestAuthStepResult": null,
    "responsePriority": 1,
    "responseAuthMethod": "USER_ID_ASSIGN",
    "responseResult": "CONTINUE"
  }
}
```
<!-- end -->

<!-- begin api DELETE /step/definition -->
### Delete a Step Definition

Delete a step definition.

This method has a `POST /step/definition/delete` alternative.

<!-- begin remove -->
#### Delete a step definition - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/step/definition</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/step/definition/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `STEP_DEFINITION_NOT_FOUND` - step definition with identifier specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Delete a step definition - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "stepDefinitionId": 1
  }
}
```

#### Delete a step definition - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "stepDefinitionId": 1
  }
}
```

<!-- end -->

## Applications API

<!-- begin api POST /application -->
### Create an Application

Create a Next Step application.

<!-- begin remove -->
#### Create an application - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/application</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `APPLICATION_ALREADY_EXISTS` - application with name specified in the request already exists |
| 500  | Server error - unexpected error occurred |

#### Create an application - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "applicationName": "APP_1",
    "description": "Test application"
  }
}
```

#### Create an application - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "applicationName": "APP_1",
    "description": "Test application",
    "applicationStatus": "ACTIVE"
  }
}
```
<!-- end -->

<!-- begin api GET /application -->
### List Applications

List all applications configured on the server.

This method has a `POST /application/list` alternative.

<!-- begin remove -->
#### List applications - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/application</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/application/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 500  | Server error - unexpected error occurred |

#### List applications - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "includeRemoved": false
  }
}
```

#### List applications - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "applications": [
      {
        "applicationName": "APP",
        "applicationStatus": "ACTIVE",
        "description": "Sample application",
        "timestampCreated": "2021-06-07T11:42:54+0000",
        "timestampLastUpdated": null
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api PUT /application -->
### Update an Application

Update an application configured on the server.

This method has a `POST /application/update` alternative.

<!-- begin remove -->
#### Update an application - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/application</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/application/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `APPLICATION_NOT_FOUND` - application with name specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Update an application - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "applicationName": "APP_1",
    "description": "Test application updated",
    "applicationStatus": "ACTIVE"
  }
}
```

#### Update an application - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "applicationName": "APP_1",
    "description": "Test application updated",
    "applicationStatus": "ACTIVE"
  }
}
```

<!-- end -->

<!-- begin api DELETE /application -->
### Delete an Application

Delete an application.

This method has a `POST /application/delete` alternative.

<!-- begin remove -->
#### Delete an application - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/application</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/application/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `APPLICATION_NOT_FOUND` - application with name specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Delete an application - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "applicationName": "APP_1"
  }
}
```

#### Delete an application - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "applicationName": "APP_1",
    "applicationStatus": "REMOVED"
  }
}
```

<!-- end -->

## Roles API

<!-- begin api POST /role -->
### Create a Role

Create a user role.

<!-- begin remove -->
#### Create a role - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/role</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `ROLE_ALREADY_EXISTS` - role with name specified in the request already exists |
| 500  | Server error - unexpected error occurred |

#### Create a role - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "roleName": "TEST_ROLE",
    "description": "Test role"
  }
}
```

#### Create a role - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "roleName": "TEST_ROLE",
    "description": "Test role"
  }
}
```
<!-- end -->

<!-- begin api GET /role -->
### List Roles

List all user roles.

This method has a `POST /role/list` alternative.

<!-- begin remove -->
#### List roles - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/role</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/role/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 500  | Server error - unexpected error occurred |

#### List roles - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
  }
}
```

#### List roles - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "roles": [
      {
        "roleName": "TEST_ROLE",
        "description": "Test role",
        "timestampCreated": "2021-06-07T11:42:54+0000",
        "timestampLastUpdated": null
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api DELETE /role -->
### Delete a Role

Delete a user role.

This method has a `POST /role/delete` alternative.

<!-- begin remove -->
#### Delete a role - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/role</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/role/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `ROLE_NOT_FOUND` - role with name specified in the request was not found |
| 400  | `DELETE_NOT_ALLOWED` - role removal is not allowed |
| 500  | Server error - unexpected error occurred |

#### Delete a role - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "roleName": "TEST_ROLE"
  }
}
```

#### Delete a role - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "roleName": "TEST_ROLE"
  }
}
```

<!-- end -->

## Credential Policies API

<!-- begin api POST /credential/policy -->
### Create a Credential Policy

Create a credential policy.

<!-- begin remove -->
#### Create a credential policy - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/policy</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `CREDENTIAL_POLICY_ALREADY_EXISTS` - credential policy with name specified in the request already exists |
| 500  | Server error - unexpected error occurred |

#### Create a credential policy - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "credentialPolicyName": "TEST_CREDENTIAL_POLICY",
    "description": "Test policy",
    "usernameLengthMin": 8,
    "usernameLengthMax": 32,
    "usernameAllowedChars": "[a-zA-Z0-9]+",
    "credentialLengthMin": 8,
    "credentialLengthMax": 32,
    "limitSoft": 3,
    "limitHard": 5,
    "checkHistoryCount": 3,
    "rotationEnabled": false,
    "rotationDays": null,
    "temporaryCredentialExpirationTime": null,
    "usernameGenAlgorithm": "RANDOM_DIGITS",
    "usernameGenParam": {
      "length": 8
    },
    "credentialGenAlgorithm": "RANDOM_PASSWORD",
    "credentialGenParam": {
      "length": 12,
      "includeSmallLetters": true,
      "smallLettersCount": null,
      "includeCapitalLetters": true,
      "capitalLettersCount": null,
      "includeDigits": true,
      "digitsCount": null,
      "includeSpecialChars": true,
      "specialCharsCount": null
    },
    "credentialValParam": {
      "includeWhitespaceRule": true,
      "includeUsernameRule": true,
      "includeAllowedCharacterRule": false,
      "allowedChars": "",
      "includeAllowedRegexRule": false,
      "allowedRegex": ".*",
      "includeIllegalCharacterRule": false,
      "illegalChars": "",
      "includeIllegalRegexRule": false,
      "illegalRegex": "",
      "includeCharacterRule": true,
      "includeSmallLetters": true,
      "smallLettersMin": 1,
      "includeCapitalLetters": true,
      "capitalLettersMin": 1,
      "includeAlphabeticalLetters": true,
      "alphabeticalLettersMin": 2,
      "includeDigits": true,
      "digitsMin": 1,
      "includeSpecialChars": true,
      "specialCharsMin": 1
    }
  }
}
```

#### Create a credential policy - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "credentialPolicyName": "TEST_CREDENTIAL_POLICY",
    "description": "Test policy",
    "credentialPolicyStatus": "ACTIVE",
    "usernameLengthMin": 8,
    "usernameLengthMax": 32,
    "usernameAllowedPattern": null,
    "credentialLengthMin": 8,
    "credentialLengthMax": 32,
    "limitSoft": 3,
    "limitHard": 5,
    "checkHistoryCount": 3,
    "rotationEnabled": false,
    "rotationDays": null,
    "temporaryCredentialExpirationTime": null,
    "usernameGenAlgorithm": "RANDOM_DIGITS",
    "usernameGenParam": {
      "length": 8
    },
    "credentialGenAlgorithm": "RANDOM_PASSWORD",
    "credentialGenParam": {
      "length": 12,
      "includeSmallLetters": true,
      "smallLettersCount": null,
      "includeCapitalLetters": true,
      "capitalLettersCount": null,
      "includeDigits": true,
      "digitsCount": null,
      "includeSpecialChars": true,
      "specialCharsCount": null
    },
    "credentialValParam": {
      "includeWhitespaceRule": true,
      "includeUsernameRule": true,
      "includeAllowedCharacterRule": false,
      "allowedChars": "",
      "includeAllowedRegexRule": false,
      "allowedRegex": ".*",
      "includeIllegalCharacterRule": false,
      "illegalChars": "",
      "includeIllegalRegexRule": false,
      "illegalRegex": "",
      "includeCharacterRule": true,
      "includeSmallLetters": true,
      "smallLettersMin": 1,
      "includeCapitalLetters": true,
      "capitalLettersMin": 1,
      "includeAlphabeticalLetters": true,
      "alphabeticalLettersMin": 2,
      "includeDigits": true,
      "digitsMin": 1,
      "includeSpecialChars": true,
      "specialCharsMin": 1
    }
  }
}
```
<!-- end -->

<!-- begin api GET /credential/policy -->
### List Credential Policies

List all credential policies configured on the server.

This method has a `POST /credential/policy/list` alternative.

<!-- begin remove -->
#### List credential policies - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/policy</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/policy/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 500  | Server error - unexpected error occurred |

#### List credential policies - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "includeRemoved": false
  }
}
```

#### List credential policies - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "credentialPolicies": [
      {
        "credentialPolicyName": "TEST_CREDENTIAL_POLICY",
        "description": "Test policy",
        "usernameLengthMin": 8,
        "usernameLengthMax": 32,
        "usernameAllowedPattern": null,
        "credentialLengthMin": 8,
        "credentialLengthMax": 32,
        "limitSoft": 3,
        "limitHard": 5,
        "checkHistoryCount": 3,
        "rotationEnabled": false,
        "rotationDays": null,
        "temporaryCredentialExpirationTime": null,
        "usernameGenAlgorithm": "RANDOM_DIGITS",
        "usernameGenParam": {
          "length": 8
        },
        "credentialGenAlgorithm": "RANDOM_PASSWORD",
        "credentialGenParam": {
          "length": 12,
          "includeSmallLetters": true,
          "smallLettersCount": null,
          "includeCapitalLetters": true,
          "capitalLettersCount": null,
          "includeDigits": true,
          "digitsCount": null,
          "includeSpecialChars": true,
          "specialCharsCount": null
        },
        "credentialValParam": {
          "includeWhitespaceRule": true,
          "includeUsernameRule": true,
          "includeAllowedCharacterRule": false,
          "allowedChars": "",
          "includeAllowedRegexRule": false,
          "allowedRegex": ".*",
          "includeIllegalCharacterRule": false,
          "illegalChars": "",
          "includeIllegalRegexRule": false,
          "illegalRegex": "",
          "includeCharacterRule": true,
          "includeSmallLetters": true,
          "smallLettersMin": 1,
          "includeCapitalLetters": true,
          "capitalLettersMin": 1,
          "includeAlphabeticalLetters": true,
          "alphabeticalLettersMin": 2,
          "includeDigits": true,
          "digitsMin": 1,
          "includeSpecialChars": true,
          "specialCharsMin": 1
        },
        "credentialPolicyStatus": "ACTIVE",
        "timestampCreated": "2021-07-01T19:50:11+0000",
        "timestampLastUpdated": null
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api PUT /credential/policy -->
### Update a Credential Policy

Update a credential policy configured on the server.

This method has a `POST /credential/policy/update` alternative.

<!-- begin remove -->
#### Update a credential policy - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/policy</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/policy/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `CREDENTIAL_POLICY_NOT_FOUND` - credential policy with name specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Update a credential policy - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "credentialPolicyName": "CREDENTIAL_POLICY",
    "description": "Sample credential policy",
    "usernameLengthMin": 5,
    "usernameLengthMax": 20,
    "usernameAllowedPattern": "[0-9a-z]+",
    "credentialLengthMin": 8,
    "credentialLengthMax": 40,
    "limitSoft": 3,
    "limitHard": 5,
    "checkHistoryCount": 3,
    "rotationEnabled": false,
    "rotationDays": null,
    "temporaryCredentialExpirationTime": 345600,
    "usernameGenAlgorithm": "RANDOM_DIGITS",
    "usernameGenParam": {
      "length": 8
    },
    "credentialGenAlgorithm": "RANDOM_PASSWORD",
    "credentialGenParam": {
      "length": 12,
      "includeSmallLetters": true,
      "smallLettersCount": 5,
      "includeCapitalLetters": true,
      "capitalLettersCount": 5,
      "includeDigits": true,
      "digitsCount": 1,
      "includeSpecialChars": true,
      "specialCharsCount": 1
    },
    "credentialValParam": {
      "includeWhitespaceRule": true,
      "includeUsernameRule": true,
      "includeAllowedCharacterRule": false,
      "allowedChars": "",
      "includeAllowedRegexRule": false,
      "allowedRegex": ".*",
      "includeIllegalCharacterRule": false,
      "illegalChars": "",
      "includeIllegalRegexRule": false,
      "illegalRegex": "",
      "includeCharacterRule": true,
      "includeSmallLetters": true,
      "smallLettersMin": 1,
      "includeCapitalLetters": true,
      "capitalLettersMin": 1,
      "includeAlphabeticalLetters": true,
      "alphabeticalLettersMin": 2,
      "includeDigits": true,
      "digitsMin": 1,
      "includeSpecialChars": true,
      "specialCharsMin": 1
    },
    "credentialPolicyStatus": "ACTIVE"
  }
}
```

#### Update a credential policy - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "credentialPolicyName": "CREDENTIAL_POLICY",
    "description": "Sample credential policy",
    "usernameLengthMin": 5,
    "usernameLengthMax": 20,
    "usernameAllowedPattern": "[0-9a-z]+",
    "credentialLengthMin": 8,
    "credentialLengthMax": 40,
    "limitSoft": 3,
    "limitHard": 5,
    "checkHistoryCount": 3,
    "rotationEnabled": false,
    "rotationDays": null,
    "temporaryCredentialExpirationTime": 345600,
    "usernameGenAlgorithm": "RANDOM_DIGITS",
    "usernameGenParam": {
      "length": 8
    },
    "credentialGenAlgorithm": "RANDOM_PASSWORD",
    "credentialGenParam": {
      "length": 12,
      "includeSmallLetters": true,
      "smallLettersCount": 5,
      "includeCapitalLetters": true,
      "capitalLettersCount": 5,
      "includeDigits": true,
      "digitsCount": 1,
      "includeSpecialChars": true,
      "specialCharsCount": 1
    },
    "credentialValParam": {
      "includeWhitespaceRule": true,
      "includeUsernameRule": true,
      "includeAllowedCharacterRule": false,
      "allowedChars": "",
      "includeAllowedRegexRule": false,
      "allowedRegex": ".*",
      "includeIllegalCharacterRule": false,
      "illegalChars": "",
      "includeIllegalRegexRule": false,
      "illegalRegex": "",
      "includeCharacterRule": true,
      "includeSmallLetters": true,
      "smallLettersMin": 1,
      "includeCapitalLetters": true,
      "capitalLettersMin": 1,
      "includeAlphabeticalLetters": true,
      "alphabeticalLettersMin": 2,
      "includeDigits": true,
      "digitsMin": 1,
      "includeSpecialChars": true,
      "specialCharsMin": 1
    },
    "credentialPolicyStatus": "ACTIVE"
  }
}
```

<!-- end -->

<!-- begin api DELETE /credential/policy -->
### Delete a credential policy

Delete a credential policy.

This method has a `POST /credential/policy/delete` alternative.

<!-- begin remove -->
#### Delete a credential policy - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/policy</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/policy/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `CREDENTIAL_POLICY_NOT_FOUND` - credential policy with name specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Delete a credential policy - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "credentialPolicyName": "TEST_CREDENTIAL_POLICY"
  }
}
```

#### Delete a credential policy - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "credentialPolicyName": "TEST_CREDENTIAL_POLICY",
    "credentialPolicyStatus": "REMOVED"
  }
}
```

<!-- end -->

## Credential Definitions API

<!-- begin api POST /credential/definition -->
### Create a Credential Definition

Create a credential definition.

<!-- begin remove -->
#### Create a credential definition - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/definition</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `CREDENTIAL_DEFINITION_ALREADY_EXISTS` - credential definition with name specified in the request already exists |
| 400  | `APPLICATION_NOT_FOUND` - application with name specified in the request was not found |
| 400  | `HASHING_CONFIG_NOT_FOUND` - hashing configuration with name specified in the request was not found |
| 400  | `CREDENTIAL_POLICY_NOT_FOUND` - credential policy with name specified in the request was not found |
| 400  | `ORGANIZATION_NOT_FOUND` - organization with name specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Create a credential definition - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "credentialDefinitionName": "TEST_CREDENTIAL_DEFINITION",
    "applicationName": "APP",
    "organizationId": "RETAIL",
    "credentialPolicyName": "CREDENTIAL_POLICY",
    "category": "PASSWORD",
    "encryptionEnabled": true,
    "encryptionAlgorithm": "AES_HMAC",
    "hashingEnabled": true,
    "hashConfigName": "ARGON_2021",
    "e2eEncryptionEnabled": true,
    "dataAdapterProxyEnabled": false
  }
}
```

#### Create a credential definition - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "credentialDefinitionName": "TEST_CREDENTIAL_DEFINITION",
    "applicationName": "APP",
    "credentialPolicyName": "CREDENTIAL_POLICY",
    "description": null,
    "category": "PASSWORD",
    "encryptionEnabled": true,
    "encryptionAlgorithm": "AES_HMAC",
    "hashingEnabled": true,
    "hashConfigName": "ARGON_2021",
    "e2eEncryptionEnabled": true,
    "e2eEncryptionAlgorithm": null,
    "e2eEncryptionCipherTransformation": null,
    "e2eEncryptionForTemporaryCredentialEnabled": false,
    "credentialDefinitionStatus": "ACTIVE",
    "dataAdapterProxyEnabled": false
  }
}
```
<!-- end -->

<!-- begin api GET /credential/definition -->
### List Credential Definitions

List all credential definitions configured on the server.

This method has a `POST /credential/definition/list` alternative.

<!-- begin remove -->
#### List credential definitions - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/definition</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/definition/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 500  | Server error - unexpected error occurred |

#### List credential definitions - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "includeRemoved": false
  }
}
```

#### List credential definitions - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "credentialDefinitions": [
      {
        "credentialDefinitionName": "TEST_CREDENTIAL_DEFINITION",
        "applicationName": "APP",
        "organizationId": "RETAIL",
        "credentialPolicyName": "CREDENTIAL_POLICY",
        "description": null,
        "category": "PASSWORD",
        "encryptionEnabled": true,
        "encryptionAlgorithm": "AES_HMAC",
        "hashingEnabled": true,
        "hashConfigName": "ARGON_2021",
        "e2eEncryptionEnabled": true,
        "e2eEncryptionAlgorithm": null,
        "e2eEncryptionCipherTransformation": null,
        "e2eEncryptionForTemporaryCredentialEnabled": false,
        "credentialDefinitionStatus": "ACTIVE",
        "dataAdapterProxyEnabled": false,
        "timestampCreated": "2021-07-01T20:03:25+0000",
        "timestampLastUpdated": null
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api PUT /credential/definition -->
### Update a Credential Definition

Update a credential definition configured on the server.

This method has a `POST /credential/definition/update` alternative.

<!-- begin remove -->
#### Update a credential definition - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/definition</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/definition/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `CREDENTIAL_DEFINITION_NOT_FOUND` - credential definition with name specified in the request was not found |
| 400  | `APPLICATION_NOT_FOUND` - application with name specified in the request was not found |
| 400  | `HASHING_CONFIG_NOT_FOUND` - hashing configuration with name specified in the request was not found |
| 400  | `CREDENTIAL_POLICY_NOT_FOUND` - credential policy with name specified in the request was not found |
| 400  | `ORGANIZATION_NOT_FOUND` - organization with name specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Update a credential definition - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "credentialDefinitionName": "RETAIL_CREDENTIAL",
    "applicationName": "APP",
    "organizationId": "RETAIL",
    "credentialPolicyName": "CREDENTIAL_POLICY",
    "description": "Sample credential definition for retail",
    "category": "PASSWORD",
    "encryptionEnabled": true,
    "encryptionAlgorithm": "AES_HMAC",
    "hashingEnabled": true,
    "hashConfigName": "ARGON_2021",
    "e2eEncryptionEnabled": false,
    "e2eEncryptionAlgorithm": "AES",
    "e2eEncryptionCipherTransformation": "AES/CBC/PKCS7Padding",
    "credentialDefinitionStatus": "ACTIVE",
    "dataAdapterProxyEnabled": false
  }
}
```

#### Update a credential definition - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "credentialDefinitionName": "RETAIL_CREDENTIAL",
    "applicationName": "APP",
    "organizationId": "RETAIL",
    "credentialPolicyName": "CREDENTIAL_POLICY",
    "description": "Sample credential definition for retail",
    "category": "PASSWORD",
    "encryptionEnabled": true,
    "encryptionAlgorithm": "AES_HMAC",
    "hashingEnabled": true,
    "hashConfigName": "ARGON_2021",
    "e2eEncryptionEnabled": false,
    "e2eEncryptionAlgorithm": "AES",
    "e2eEncryptionCipherTransformation": "AES/CBC/PKCS7Padding",
    "e2eEncryptionForTemporaryCredentialEnabled": false,
    "credentialDefinitionStatus": "ACTIVE",
    "dataAdapterProxyEnabled": false
  }
}
```

<!-- end -->

<!-- begin api DELETE /credential/definition -->
### Delete a credential definition

Delete a credential definition.

This method has a `POST /credential/definition/delete` alternative.

<!-- begin remove -->
#### Delete a credential definition - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/definition</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/definition/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `CREDENTIAL_DEFINITION_NOT_FOUND` - credential definition with name specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Delete a credential definition - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "credentialDefinitionName": "TEST_CREDENTIAL_DEFINITION"
  }
}
```

#### Delete a credential definition - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "credentialDefinitionName": "TEST_CREDENTIAL_DEFINITION",
    "credentialDefinitionStatus": "REMOVED"
  }
}
```

<!-- end -->

## OTP Policies API

<!-- begin api POST /otp/policy -->
### Create an OTP Policy

Create an OTP policy.

<!-- begin remove -->
#### Create an OTP policy - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/policy</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `OTP_POLICY_ALREADY_EXISTS` - OTP policy with name specified in the request already exists |
| 500  | Server error - unexpected error occurred |

#### Create an OTP policy - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "otpPolicyName": "TEST_OTP_POLICY",
    "description": "Test OTP policy",
    "length": 8,
    "attemptLimit": 3,
    "expirationTime": null,
    "genAlgorithm": "OTP_DATA_DIGEST"
  }
}
```

#### Create an OTP policy - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "otpPolicyName": "TEST_OTP_POLICY",
    "description": "Test OTP policy",
    "length": 8,
    "attemptLimit": 3,
    "expirationTime": null,
    "genAlgorithm": "OTP_DATA_DIGEST",
    "genParam": {
      "groupSize": null
    },
    "otpPolicyStatus": "ACTIVE"
  }
}
```
<!-- end -->

<!-- begin api GET /otp/policy -->
### List OTP Policies

List all OTP policies configured on the server.

THis method has a `POST /otp/policy/list` alternative.

<!-- begin remove -->
#### List OTP policies - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/policy</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/policy/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 500  | Server error - unexpected error occurred |

#### List OTP policies - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "includeRemoved": false
  }
}
```

#### List OTP policies - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "otpPolicies": [
      {
        "otpPolicyName": "TEST_OTP_POLICY",
        "description": "Test OTP policy",
        "length": 8,
        "attemptLimit": 3,
        "expirationTime": null,
        "genAlgorithm": "OTP_DATA_DIGEST",
        "genParam": {
          "groupSize": null
        },
        "otpPolicyStatus": "ACTIVE",
        "timestampCreated": "2021-07-01T20:13:48+0000",
        "timestampLastUpdated": null
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api PUT /otp/policy -->
### Update an OTP Policy

Update an OTP policy configured on the server.

This method has a `POST /otp/policy/update` alternative.

<!-- begin remove -->
#### Update an OTP policy - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/policy</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/policy/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `OTP_POLICY_NOT_FOUND` - OTP policy with name specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Update an OTP policy - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "otpPolicyName": "OTP_POLICY",
    "description": "Sample OTP policy",
    "length": 8,
    "attemptLimit": 3,
    "expirationTime": 300,
    "genAlgorithm": "OTP_DATA_DIGEST",
    "genParam": {
      "groupSize": null
    },
    "otpPolicyStatus": "ACTIVE"
  }
}
```

#### Update an OTP policy - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "otpPolicyName": "OTP_POLICY",
    "description": "Sample OTP policy",
    "length": 8,
    "attemptLimit": 3,
    "expirationTime": 300,
    "genAlgorithm": "OTP_DATA_DIGEST",
    "genParam": {
      "groupSize": null
    },
    "otpPolicyStatus": "ACTIVE"
  }
}
```

<!-- end -->

<!-- begin api DELETE /otp/policy -->
### Delete an OTP policy

Delete an OTP policy.

This method has a `POST /otp/policy/delete` alternative.

<!-- begin remove -->
#### Delete an OTP policy - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/policy</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/policy/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OTP_POLICY_NOT_FOUND` - OTP policy with name specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Delete an OTP policy - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "otpPolicyName": "TEST_OTP_POLICY"
  }
}
```

#### Delete an OTP policy - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "otpPolicyName": "TEST_OTP_POLICY",
    "otpPolicyStatus": "REMOVED"
  }
}
```

<!-- end -->

## OTP Definitions API

<!-- begin api POST /otp/definition -->
### Create an OTP Definition

Create an OTP definition.

<!-- begin remove -->
#### Create an OTP definition - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/definition</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OTP_DEFINITION_ALREADY_EXISTS` - OTP definition with name specified in the request already exists |
| 400  | `APPLICATION_NOT_FOUND` - application with name specified in the request was not found |
| 400  | `OTP_POLICY_NOT_FOUND` - OTP policy with name specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Create an OTP definition - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "otpDefinitionName": "RETAIL_OTP",
    "applicationName": "RETAIL_APP",
    "otpPolicyName": "OTP_POLICY",
    "description": "Sample OTP definition for retail",
    "encryptionEnabled": false,
    "encryptionAlgorithm": null,
    "dataAdapterProxyEnabled": false,
    "otpDefinitionStatus": "ACTIVE"
  }
}
```

#### Create an OTP definition - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "otpDefinitionName": "RETAIL_OTP",
    "applicationName": "APP",
    "otpPolicyName": "OTP_POLICY",
    "description": null,
    "encryptionEnabled": false,
    "encryptionAlgorithm": null,
    "otpDefinitionStatus": "ACTIVE",
    "dataAdapterProxyEnabled": false
  }
}
```
<!-- end -->

<!-- begin api GET /otp/definition -->
### List OTP definitions

List all OTP definitions configured on the server.

This method has a `POST /otp/definition/list` alternative.

<!-- begin remove -->
#### List OTP definitions - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/definition</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/definition/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 500  | Server error - unexpected error occurred |

#### List OTP definitions - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "includeRemoved": false
  }
}
```

#### List OTP definitions - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "otpDefinitions": [
      {
        "otpDefinitionName": "RETAIL_OTP",
        "applicationName": "APP",
        "otpPolicyName": "OTP_POLICY",
        "description": "Sample OTP definition for retail",
        "encryptionEnabled": false,
        "encryptionAlgorithm": null,
        "otpDefinitionStatus": "ACTIVE",
        "dataAdapterProxyEnabled": true,
        "timestampCreated": "2021-06-07T11:42:54+0000",
        "timestampLastUpdated": null
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api PUT /otp/definition -->
### Update an OTP Definition

Update an OTP definition configured on the server.

This method has a `POST /otp/definition/update` alternative.

<!-- begin remove -->
#### Update an OTP definition - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/definition</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/definition/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OTP_DEFINITION_NOT_FOUND` - OTP definition with name specified in the request was not found |
| 400  | `APPLICATION_NOT_FOUND` - application with name specified in the request was not found |
| 400  | `OTP_POLICY_NOT_FOUND` - OTP policy with name specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Update an OTP definition - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "otpDefinitionName": "RETAIL_OTP",
    "applicationName": "APP",
    "otpPolicyName": "OTP_POLICY",
    "description": null,
    "encryptionEnabled": false,
    "encryptionAlgorithm": "AES_HMAC",
    "otpDefinitionStatus": "ACTIVE",
    "dataAdapterProxyEnabled": false
  }
}
```

#### Update an OTP definition - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "otpDefinitionName": "RETAIL_OTP",
    "applicationName": "APP",
    "otpPolicyName": "OTP_POLICY",
    "description": null,
    "encryptionEnabled": false,
    "encryptionAlgorithm": "AES_HMAC",
    "otpDefinitionStatus": "ACTIVE",
    "dataAdapterProxyEnabled": false
  }
}
```

<!-- end -->

<!-- begin api DELETE /otp/definition -->
### Delete an OTP definition

Delete an OTP definition.

This method has a `POST /otp/definition/delete` alternative.

<!-- begin remove -->
#### Delete an OTP definition - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/definition</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/definition/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OTP_DEFINITION_NOT_FOUND` - OTP definition with name specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Delete an OTP definition - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "otpDefinitionName": "RETAIL_OTP"
  }
}
```

#### Delete an OTP definition - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "otpDefinitionName": "RETAIL_OTP",
    "otpDefinitionStatus": "REMOVED"
  }
}
```

<!-- end -->

## Hashing Configurations API

<!-- begin api POST /hashconfig -->
### Create a Hashing Configuration

Create a hashing configuration.

<!-- begin remove -->
#### Create a hashing configuration - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/hashconfig</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `HASHING_CONFIG_ALREADY_EXISTS` - hashing configuration with name specified in the request already exists |
| 500  | Server error - unexpected error occurred |

#### Create a hashing configuration - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "hashConfigName": "ARGON_2021",
    "algorithm": "ARGON_2ID",
    "parameters": {
      "version": "16",
      "iterations": "3",
      "memory": "15",
      "parallelism": "16",
      "outputLength": "32"
    }
  }
}
```

#### Create a hashing configuration - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "hashConfigName": "ARGON_2021",
    "algorithm": "ARGON_2ID",
    "parameters": {
      "version": "16",
      "iterations": "3",
      "memory": "15",
      "parallelism": "16",
      "outputLength": "32"
    },
    "hashConfigStatus": "ACTIVE"
  }
}
```
<!-- end -->

<!-- begin api GET /hashconfig -->
### List Hashing Configurations

List all hashing configurations configured on the server.

This method has a `POST /hashconfig/list` alternative.

<!-- begin remove -->
#### List hashing configurations - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/hashconfig</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/hashconfig/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_CONFIGURATION` - Next Step server configuration is invalid |
| 500  | Server error - unexpected error occurred |

#### List hashing configurations - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "includeRemoved": false
  }
}
```

#### List hashing configurations - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "hashConfigs": [
      {
        "hashConfigName": "ARGON_2021",
        "algorithm": "ARGON_2ID",
        "hashConfigStatus": "ACTIVE",
        "parameters": {
          "version": "16",
          "iterations": "3",
          "memory": "15",
          "parallelism": "16",
          "outputLength": "32"
        },
        "timestampCreated": "2021-07-01T20:03:20+0000"
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api PUT /hashconfig -->
### Update a Hashing Configuration

Update a hashing configuration configured on the server.

This method has a `POST /hashconfig/update` alternative.

<!-- begin remove -->
#### Update a hashing configuration - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/hashconfig</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/hashconfig/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `HASHING_CONFIG_NOT_FOUND` - hashing configuration with name specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Update a hashing configuration - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "hashConfigName": "ARGON_2021",
    "algorithm": "ARGON_2ID",
    "parameters": {
      "version": "16",
      "iterations": "3",
      "memory": "15",
      "parallelism": "16",
      "outputLength": "32"
    },
    "hashConfigStatus": "ACTIVE"
  }
}
```

#### Update a hashing configuration - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "hashConfigName": "ARGON_2021",
    "algorithm": "ARGON_2ID",
    "parameters": {
      "version": "16",
      "iterations": "3",
      "memory": "15",
      "parallelism": "16",
      "outputLength": "32"
    },
    "hashConfigStatus": "ACTIVE"
  }
}
```

<!-- end -->

<!-- begin api DELETE /hashconfig -->
### Delete a Hashing Configuration

Delete a hashing configuration.

This method has a `POST /hashconfig/delete` alternative.

<!-- begin remove -->
#### Delete a hashing configuration - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/hashconfig</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/hashconfig/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `HASHING_CONFIG_NOT_FOUND` - hashing configuration with name specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Delete a hashing configuration - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "hashConfigName": "ARGON_2021"
  }
}
```

#### Delete a hashing configuration - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "hashConfigName": "ARGON_2021",
    "hashConfigStatus": "REMOVED"
  }
}
```

<!-- end -->

## User Identities API

<!-- begin api POST /user -->
### Create a User Identity

Create a user identity.

<!-- begin remove -->
#### Create a user identity - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `USER_IDENTITY_ALREADY_EXISTS` - user identity with identifier specified in the request already exist |
| 400  | `CREDENTIAL_DEFINITION_NOT_FOUND` - credential definition with name specified in the request was not found |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `CREDENTIAL_VALIDATION_FAILED` - credential validation failed |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Create a user identity - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "extras": {
      "key1": "value1"
    },
    "roles": [],
    "contacts": [
      {
        "contactName": "TEST_CONTACT",
        "contactType": "PHONE",
        "contactValue": "+420123456",
        "primary": true
      }
    ],
    "credentials": [
      {
        "credentialName": "RETAIL_CREDENTIAL",
        "credentialType": "PERMANENT",
        "username": "testuser"
      }
    ]
  }
}
```

#### Create a user identity - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "userIdentityStatus": "ACTIVE",
    "extras": {
      "key1": "value1"
    },
    "roles": [],
    "contacts": [
      {
        "contactName": "TEST_CONTACT",
        "contactType": "PHONE",
        "contactValue": "+420123456",
        "primary": true,
        "timestampCreated": "2021-07-02T09:47:36+0000",
        "timestampLastUpdated": null
      }
    ],
    "credentials": [
      {
        "credentialName": "RETAIL_CREDENTIAL",
        "credentialType": "PERMANENT",
        "credentialStatus": "ACTIVE",
        "username": "testuser",
        "credentialValue": "buCH<JpTw1mA",
        "credentialChangeRequired": false,
        "timestampCreated": "2021-07-02T09:47:35+0000",
        "timestampLastUpdated": null,
        "timestampBlocked": null,
        "timestampExpires": null,
        "timestampLastCredentialChange": "2021-07-02T09:47:35+0000",
        "timestampLastUsernameChange": "2021-07-02T09:47:35+0000"
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api GET /user/detail -->
### Get User Identity Detail

Get user identity detail.

This method has a `POST /user/detail` alternative.

<!-- begin remove -->
#### Get user identity detail - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/detail</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/detail</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `CREDENTIAL_DEFINITION_NOT_FOUND` - credential definition was not found |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Get user identity detail - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234"
  }
}
```

#### Get user identity detail - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "userIdentityStatus": "ACTIVE",
    "extras": {
      "key1": "value1"
    },
    "roles": [],
    "contacts": [
      {
        "contactName": "TEST_CONTACT",
        "contactType": "PHONE",
        "contactValue": "+420123456",
        "primary": true,
        "timestampCreated": "2021-07-02T09:47:36+0000",
        "timestampLastUpdated": null
      }
    ],
    "credentials": [
      {
        "credentialName": "RETAIL_CREDENTIAL",
        "credentialType": "PERMANENT",
        "credentialStatus": "ACTIVE",
        "username": "testuser2",
        "credentialChangeRequired": false,
        "timestampCreated": "2021-07-02T09:47:36+0000",
        "timestampExpires": null,
        "timestampBlocked": null,
        "timestampLastUpdated": null,
        "timestampLastCredentialChange": "2021-07-02T09:47:36+0000",
        "timestampLastUsernameChange": "2021-07-02T09:47:36+0000"
      }
    ],
    "timestampCreated": "2021-07-02T09:47:36+0000",
    "timestampLastUpdated": null
  }
}
```
<!-- end -->

<!-- begin api PUT /user -->
### Update a User Identity

Update a user identity.

This method has a `POST /user/update` alternative.

<!-- begin remove -->
#### Update a user identity - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `CREDENTIAL_DEFINITION_NOT_FOUND` - credential definition was not found |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `CREDENTIAL_VALIDATION_FAILED` - credential validation failed |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Update a user identity - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "userIdentityStatus": "ACTIVE",
    "extras": {
      "key1": "value1"
    },
    "roles": [
      "TEST_ROLE"
    ],
    "contacts": [
      {
        "contactName": "TEST_CONTACT",
        "contactType": "PHONE",
        "contactValue": "+4201234567",
        "primary": true
      }
    ],
    "credentials": [
      {
        "credentialName": "RETAIL_CREDENTIAL",
        "credentialType": "PERMANENT",
        "username": "test1234",
        "credentialValue": "S3cret.1234"
      }
    ]
  }
}
```

#### Update a user identity - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "test2",
    "userIdentityStatus": "ACTIVE",
    "extras": {
      "key1": "value1"
    },
    "roles": [
      "TEST_ROLE"
    ],
    "contacts": [
      {
        "contactName": "TEST_CONTACT",
        "contactType": "PHONE",
        "contactValue": "+4201234567",
        "primary": true,
        "timestampCreated": "2021-07-02T09:47:36+0000",
        "timestampLastUpdated": "2021-07-02T10:17:03+0000"
      }
    ],
    "credentials": [
      {
        "credentialName": "RETAIL_CREDENTIAL",
        "credentialType": "PERMANENT",
        "credentialStatus": "ACTIVE",
        "username": "test1234",
        "credentialValue": null,
        "credentialChangeRequired": false,
        "timestampCreated": "2021-07-02T09:47:36+0000",
        "timestampLastUpdated": "2021-07-02T10:17:03+0000",
        "timestampBlocked": null,
        "timestampExpires": null,
        "timestampLastCredentialChange": "2021-07-02T10:17:03+0000",
        "timestampLastUsernameChange": "2021-07-02T10:17:03+0000"
      }
    ]
  }
}
```

<!-- end -->

<!-- begin api PUT /user/multi -->
### Update Multiple User Identities

Update multiple user identities.

This method has a `POST /user/update/multi` alternative.

<!-- begin remove -->
#### Update multiple user identities - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/multi</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/update/multi</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Update multiple user identities - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userIds": [
      "user1234",
      "user5678"
    ],
    "userIdentityStatus": "ACTIVE"
  }
}
```

#### Update multiple user identities - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userIds": [
      "user1234",
      "user5678"
    ],
    "userIdentityStatus": "ACTIVE"
  }
}
```

<!-- end -->

<!-- begin api POST /user/lookup/single -->
### Lookup a User Identity

Lookup a user identity. 

The operation ID parameter is required in case Data Adapter proxy is enabled.

<!-- begin remove -->
#### Lookup a user identity - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/lookup/single</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Lookup a user identity - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "username": "77002401",
    "credentialName": "RETAIL_CREDENTIAL",
    "operationId": null
  }
}
```

#### Lookup a user identity - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "user": {
      "userId": "user9876",
      "userIdentityStatus": "ACTIVE",
      "extras": {
        "key1": "value1"
      },
      "roles": [
        "TEST_ROLE"
      ],
      "contacts": [
        {
          "contactName": "TEST_CONTACT",
          "contactType": "PHONE",
          "contactValue": "+4201234567",
          "primary": true,
          "timestampCreated": "2021-07-02T09:47:36+0000",
          "timestampLastUpdated": "2021-07-02T10:17:04+0000"
        }
      ],
      "credentials": [
        {
          "credentialName": "RETAIL_CREDENTIAL",
          "credentialType": "PERMANENT",
          "credentialStatus": "ACTIVE",
          "username": "77002401",
          "credentialChangeRequired": false,
          "timestampCreated": "2021-07-02T09:47:36+0000",
          "timestampExpires": null,
          "timestampBlocked": null,
          "timestampLastUpdated": "2021-07-02T10:17:03+0000",
          "timestampLastCredentialChange": "2021-07-02T10:17:03+0000",
          "timestampLastUsernameChange": "2021-07-02T10:17:03+0000"
        }
      ],
      "timestampCreated": "2021-07-02T09:47:36+0000",
      "timestampLastUpdated": "2021-07-02T10:17:03+0000"
    }
  }
}
```
<!-- end -->

<!-- begin api POST /user/lookup -->
### Lookup User Identities

Lookup user identities.

<!-- begin remove -->
#### Lookup user identities - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/lookup</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Lookup user identities - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userIdentityStatus": null,
    "createdStartDate": null,
    "createdEndDate": null,
    "roles": null,
    "username": "14655327",
    "credentialName": "RETAIL_CREDENTIAL",
    "credentialStatus" : null
  }
}
```

#### Lookup user identities - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "users": [
      {
        "userId": "user4321",
        "userIdentityStatus": "ACTIVE",
        "extras": {
          "key1": "value1"
        },
        "roles": [
          "TEST_ROLE"
        ],
        "contacts": [
          {
            "contactName": "TEST_CONTACT",
            "contactType": "PHONE",
            "contactValue": "+4201234567",
            "primary": true,
            "timestampCreated": "2021-07-02T09:47:36+0000",
            "timestampLastUpdated": "2021-07-02T10:17:04+0000"
          }
        ],
        "credentials": [
          {
            "credentialName": "RETAIL_CREDENTIAL",
            "credentialType": "PERMANENT",
            "credentialStatus": "ACTIVE",
            "username": "14655327",
            "credentialChangeRequired": false,
            "timestampCreated": "2021-07-02T09:47:36+0000",
            "timestampExpires": null,
            "timestampBlocked": null,
            "timestampLastUpdated": "2021-07-02T10:17:03+0000",
            "timestampLastCredentialChange": "2021-07-02T10:17:03+0000",
            "timestampLastUsernameChange": "2021-07-02T10:17:03+0000"
          }
        ],
        "timestampCreated": "2021-07-02T09:47:36+0000",
        "timestampLastUpdated": "2021-07-02T10:17:03+0000"
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api POST /user/block -->
### Block a User Identity

Block a user identity.

<!-- begin remove -->
#### Block a user identity - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/block</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `USER_IDENTITY_NOT_ACTIVE` -  user identity with identifier specified in the request is not in `ACTIVE` state |
| 500  | Server error - unexpected error occurred |

#### Block a user identity - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234"
  }
}
```

#### Block a user identity - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "userIdentityStatus": "BLOCKED"
  }
}
```
<!-- end -->

<!-- begin api POST /user/unblock -->
### Unblock a User Identity

Unblock a user identity.

<!-- begin remove -->
#### Unblock a user identity - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/unblock</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `USER_IDENTITY_NOT_BLOCKED` -  user identity with identifier specified in the request is not in `BLOCKED` state |
| 500  | Server error - unexpected error occurred |

#### Unblock a user identity - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234"
  }
}
```

#### Unblock a user identity - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "userIdentityStatus": "ACTIVE"
  }
}
```
<!-- end -->

<!-- begin api DELETE /user -->
### Delete a User Identity

Delete a user identity.

This method has a `POST /otp/policy/delete` alternative.

<!-- begin remove -->
#### Delete a user identity - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Delete a user identity - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234"
  }
}
```

#### Delete a user identity - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "userIdentityStatus": "REMOVED"
  }
}
```
<!-- end -->

<!-- begin api POST /user/contact -->
### Create a User Contact

Create a user contact.

<!-- begin remove -->
#### Create a user contact - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/contact</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `USER_CONTACT_ALREADY_EXISTS` - user contact already exists |
| 500  | Server error - unexpected error occurred |

#### Create a user contact - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "contactName": "TEST_CONTACT",
    "contactType": "PHONE",
    "contactValue": "+420602123456",
    "primary": true
  }
}
```

#### Create a user contact - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "contactName": "TEST_CONTACT",
    "contactType": "PHONE",
    "contactValue": "+420602123456",
    "primary": true
  }
}
```
<!-- end -->

<!-- begin api GET /user/contact -->
### List User Contacts

List all user contacts.

This method has a `POST /user/contact/list` alternative.

<!-- begin remove -->
#### List user contacts - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/contact</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/contact/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### List user contacts - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234"
  }
}
```

#### List user contacts - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "contacts": [
      {
        "contactName": "TEST_CONTACT",
        "contactType": "PHONE",
        "contactValue": "+4201234567",
        "primary": false,
        "timestampCreated": "2021-07-02T09:47:36+0000",
        "timestampLastUpdated": "2021-07-02T11:27:30+0000"
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api PUT /user/contact -->
### Update a User Contact

Update a user contact.

This method has a `POST /user/contact/update` alternative.

<!-- begin remove -->
#### Update a user contact - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/contact</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/contact/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `USER_CONTACT_NOT_FOUND` - user contact was not found |
| 500  | Server error - unexpected error occurred |

#### Update a user contact - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "contactName": "TEST_CONTACT",
    "contactType": "PHONE",
    "contactValue": "+420605789651",
    "primary": true
  }
}
```

#### Update a user contact - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "contactName": "TEST_CONTACT",
    "contactType": "PHONE",
    "contactValue": "+420605789651",
    "primary": true
  }
}
```

<!-- end -->

<!-- begin api DELETE /user/contact -->
### Delete a User Contact

Delete a user contact.

This method has a `POST /user/contact/delete` alternative.

<!-- begin remove -->
#### Delete a user contact - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/contact</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/contact/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `USER_CONTACT_NOT_FOUND` - user contact was not found |
| 500  | Server error - unexpected error occurred |

#### Delete a user contact - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "test1234",
    "contactName": "TEST_CONTACT",
    "contactType": "PHONE"
  }
}
```

#### Delete a user contact - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "test1234",
    "contactName": "TEST_CONTACT",
    "contactType": "PHONE"
  }
}
```
<!-- end -->

<!-- begin api POST /user/alias -->
### Create a User Alias

Create a user alias.

<!-- begin remove -->
#### Create a user alias - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/alias</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `USER_ALIAS_ALREADY_EXISTS` - user alias already exists |
| 500  | Server error - unexpected error occurred |

#### Create a user alias - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "aliasName": "TEST_ALIAS",
    "aliasValue": "SOME_ALIAS_VALUE",
    "extras": {
      "key1": "value1"
    }
  }
}
```

#### Create a user alias - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "aliasName": "TEST_ALIAS",
    "aliasValue": "SOME_ALIAS_VALUE",
    "extras": {
      "key1": "value1"
    },
    "userAliasStatus": "ACTIVE"
  }
}
```
<!-- end -->

<!-- begin api GET /user/alias -->
### List User Aliases

List all user aliases.

This method has a `POST /user/alias/list` alternative.

<!-- begin remove -->
#### List user aliases - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/alias</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/alias/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### List user aliases - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "includeRemoved": false
  }
}
```

#### List user aliases - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "aliases": [
      {
        "aliasName": "TEST_ALIAS",
        "aliasValue": "SOME_ALIAS_VALUE",
        "userAliasStatus": "ACTIVE",
        "extras": {
          "key1": "value1"
        },
        "timestampCreated": "2021-07-02T11:37:54+0000",
        "timestampLastUpdated": null
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api PUT /user/alias -->
### Update a User Alias

Update a user alias.

This method has a `POST /user/alias/update` alternative.

<!-- begin remove -->
#### Update a user alias - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/alias</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/alias/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `USER_ALIAS_NOT_FOUND` - user alias was not found |
| 500  | Server error - unexpected error occurred |

#### Update a user alias - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "aliasName": "TEST_ALIAS",
    "aliasValue": "SOME_ALIAS_VALUE",
    "extras": {
      "key1": "value"
    },
    "userAliasStatus": "ACTIVE"
  }
}
```

#### Update a user contact - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "aliasName": "TEST_ALIAS",
    "aliasValue": "SOME_ALIAS_VALUE",
    "extras": {
      "key1": "value"
    },
    "userAliasStatus": "ACTIVE"
  }
}
```
<!-- end -->

<!-- begin api DELETE /user/alias -->
### Delete a User Alias

Delete a user alias.

This method has a `POST /user/alias/delete` alternative.

<!-- begin remove -->
#### Delete a user alias - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/alias</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/alias/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `USER_ALIAS_NOT_FOUND` - user alias was not found |
| 500  | Server error - unexpected error occurred |

#### Delete a user alias - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "aliasName": "TEST_ALIAS"
  }
}
```

#### Delete a user alias - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "aliasName": "TEST_ALIAS",
    "userAliasStatus": "REMOVED"
  }
}
```
<!-- end -->

<!-- begin api POST /user/role -->
### Assign a Role to User Identity

Assign a role to user identity.

<!-- begin remove -->
#### Assign a role to user identity - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/role</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `USER_ROLE_ALREADY_ASSIGNED` - user role is already assigned |
| 500  | Server error - unexpected error occurred |

#### Assign a role to user identity - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "roleName": "TEST_ROLE"
  }
}
```

#### Assign a role to user identity 

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "roleName": "TEST_ROLE",
    "userRoleStatus": "ACTIVE"
  }
}
```
<!-- end -->

<!-- begin api DELETE /user/role -->
### Remove a Role from User Identity

Remove a user role from user identity.

This method has a `POST /user/role/delete` alternative.

<!-- begin remove -->
#### Remove a role from user identity - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/role</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/role/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `USER_ROLE_NOT_ASSIGNED` - user role is not assigned |
| 500  | Server error - unexpected error occurred |

#### Remove a role from user identity - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "roleName": "TEST_ROLE"
  }
}
```

#### Remove a role from user identity - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "roleName": "TEST_ROLE",
    "userRoleStatus": "REMOVED"
  }
}
```
<!-- end -->

<!-- begin api GET /user/credential -->
### Get User Credential List

Get user credential list.

This method has a `POST /user/credential/list` alternative.

<!-- begin remove -->
#### Get user credential list - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/credential</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/credential/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Get user credential list - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "includeRemoved": false
  }
}
```

#### Get user credential list - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "credentials": [
      {
        "credentialName": "RETAIL_CREDENTIAL",
        "credentialType": "PERMANENT",
        "credentialStatus": "ACTIVE",
        "username": "test1234",
        "credentialChangeRequired": false,
        "timestampCreated": "2021-07-02T09:47:36+0000",
        "timestampExpires": null,
        "timestampBlocked": null,
        "timestampLastUpdated": "2021-07-02T11:27:30+0000",
        "timestampLastCredentialChange": "2021-07-02T11:27:30+0000",
        "timestampLastUsernameChange": "2021-07-02T11:27:30+0000"
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api GET /user/authentication -->
### Get User Authentication List

Get user credential list.

This method has a `POST /user/authentication/list` alternative.

<!-- begin remove -->
#### Get user authentication list - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/authentication</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/authentication/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 500  | Server error - unexpected error occurred |

#### Get user authentication list - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "createdStartDate": "2021-06-24T10:24:09+0000",
    "createdEndDate": "2021-07-24T17:24:09+0000"    
  }
}
```

#### Get user authentication list - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "authentications": [
      {
        "authenticationType": "CREDENTIAL",
        "credentialName": "RETAIL_CREDENTIAL",
        "otpName": null,
        "authenticationResult": "FAILED",
        "credentialAuthenticationResult": "FAILED",
        "otpAuthenticationResult": null,
        "timestampCreated": "2021-07-02T11:56:03+0000"
      }
    ]
  }
}
```
<!-- end -->

## Credentials API

<!-- begin api POST /credential -->
### Create a Credential

Create a credential.

<!-- begin remove -->
#### Create a credential - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `CREDENTIAL_DEFINITION_NOT_FOUND` - credential definition with name specified in the request was not found |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `CREDENTIAL_VALIDATION_FAILED` - credential validation failed |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Create a credential - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "credentialName": "RETAIL_CREDENTIAL",
    "credentialType": "PERMANENT",
    "username": "username1234",
    "credentialValue": null,
    "validationMode": "VALIDATE_USERNAME_AND_CREDENTIAL",
    "credentialHistory": []
  }
}
```

#### Create a credential - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "credentialName": "RETAIL_CREDENTIAL",
    "credentialType": "PERMANENT",
    "credentialStatus": "ACTIVE",
    "username": "username1234",
    "credentialValue": "JeM1vr%GyJFh",
    "credentialChangeRequired": false
  }
}
```
<!-- end -->

<!-- begin api PUT /credential -->
### Update a Credential

Update a credential.

This method has a `POST /credential/update` alternative.

<!-- begin remove -->
#### Update a credential - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `CREDENTIAL_DEFINITION_NOT_FOUND` - credential definition with name specified in the request was not found |
| 400  | `CREDENTIAL_NOT_FOUND` - credential was not found |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `CREDENTIAL_VALIDATION_FAILED` - credential validation failed |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Update a credential - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "credentialName": "RETAIL_CREDENTIAL",
    "credentialType": "PERMANENT",
    "username": "username1234",
    "credentialStatus": "ACTIVE"
  }
}
```

#### Update a credential - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "credentialName": "RETAIL_CREDENTIAL",
    "credentialType": "PERMANENT",
    "credentialStatus": "ACTIVE",
    "username": "username1234",
    "credentialChangeRequired": false
  }
}
```
<!-- end -->

<!-- begin api POST /credential/validate -->
### Validate a Credential

Validate a credential.

<!-- begin remove -->
#### Validate a credential - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/validate</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `CREDENTIAL_DEFINITION_NOT_FOUND` - credential definition with name specified in the request was not found |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Validate a credential - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "credentialName": "RETAIL_CREDENTIAL",
    "username": "user",
    "credentialValue": "rrnVHhN2YGw",
    "validationMode": "VALIDATE_USERNAME_AND_CREDENTIAL",
    "userId": "user1234"
  }
}
```

#### Validate a credential - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "validationResult": "FAILED",
    "validationErrors": [
      "USERNAME_TOO_SHORT",
      "CREDENTIAL_INSUFFICIENT_SPECIAL"
    ]
  }
}
```
<!-- end -->

<!-- begin api POST /credential/reset -->
### Reset a Credential

Reset a credential.

<!-- begin remove -->
#### Reset a credential - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/reset</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `CREDENTIAL_DEFINITION_NOT_FOUND` - credential definition with name specified in the request was not found |
| 400  | `CREDENTIAL_NOT_FOUND` - credential was not found |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Reset a credential - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "credentialName": "RETAIL_CREDENTIAL",
    "credentialType": "PERMANENT"
  }
}
```

#### Reset a credential - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "credentialName": "RETAIL_CREDENTIAL",
    "username": "username1234",
    "credentialValue": "N4DuitRp:HUx",
    "credentialStatus": "ACTIVE"
  }
}
```
<!-- end -->

<!-- begin api POST /credential/block -->
### Block a Credential

Block a credential.

<!-- begin remove -->
#### Block a credential - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/block</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `CREDENTIAL_DEFINITION_NOT_FOUND` - credential definition with name specified in the request was not found |
| 400  | `CREDENTIAL_NOT_FOUND` - credential was not found |
| 400  | `CREDENTIAL_NOT_ACTIVE` - credential is not in `ACTIVE` state |
| 500  | Server error - unexpected error occurred |

#### Block a credential - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "credentialName": "RETAIL_CREDENTIAL"
  }
}
```

#### Block a credential - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "credentialName": "RETAIL_CREDENTIAL",
    "credentialStatus": "BLOCKED_PERMANENT"
  }
}
```
<!-- end -->

<!-- begin api POST /credential/unblock -->
### Unblock a Credential

Block a credential.

<!-- begin remove -->
#### Unblock a credential - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/unblock</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `CREDENTIAL_DEFINITION_NOT_FOUND` - credential definition with name specified in the request was not found |
| 400  | `CREDENTIAL_NOT_FOUND` - credential was not found |
| 400  | `CREDENTIAL_NOT_BLOCKED` - credential is not in `BLOCKED_PERMANENT` or `BLOCKED_TEMPORARY` state |
| 500  | Server error - unexpected error occurred |

#### Unblock a credential - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "credentialName": "RETAIL_CREDENTIAL"
  }
}
```

#### Unblock a credential - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "credentialName": "RETAIL_CREDENTIAL",
    "credentialStatus": "ACTIVE"
  }
}
```
<!-- end -->

<!-- begin api DELETE /credential -->
### Delete a Credential

Delete a credential.

This method has a `POST /credential/delete` alternative.

<!-- begin remove -->
#### Delete a credential - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `CREDENTIAL_DEFINITION_NOT_FOUND` - credential definition with name specified in the request was not found |
| 400  | `CREDENTIAL_NOT_FOUND` - credential was not found |
| 500  | Server error - unexpected error occurred |

#### Delete a credential - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "credentialName": "RETAIL_CREDENTIAL"
  }
}
```

#### Delete a credential - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "credentialName": "RETAIL_CREDENTIAL",
    "credentialStatus": "REMOVED"
  }
}
```
<!-- end -->

## Credential Counters API

<!-- begin api PUT /credential/counter -->
### Update a Credential Counter

Update a credential.

This method has a `POST /credential/counter/update` alternative.

<!-- begin remove -->
#### Update a credential counter - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/counter</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `PUT` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/counter/update</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity with identifier specified in the request was not found |
| 400  | `CREDENTIAL_DEFINITION_NOT_FOUND` - credential definition with name specified in the request was not found |
| 400  | `CREDENTIAL_NOT_FOUND` - credential was not found |
| 400  | `CREDENTIAL_NOT_ACTIVE` - credential is not in `ACTIVE` state |
| 500  | Server error - unexpected error occurred |

#### Update a credential counter - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "user1234",
    "credentialName": "RETAIL_CREDENTIAL",
    "authenticationResult": "FAILED"
  }
}
```

#### Update a credential - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "user1234",
    "credentialName": "RETAIL_CREDENTIAL",
    "credentialStatus": "ACTIVE"
  }
}
```
<!-- end -->

<!-- begin api POST /credential/counter/reset-all -->
### Reset All Soft Failed Attempt Counters

Reset all soft failed counters.

<!-- begin remove -->
#### Reset all soft failed attempt counters - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/credential/counter/reset-all</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 500  | Server error - unexpected error occurred |

#### Reset all soft failed attempt counters - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "resetMode": "RESET_ACTIVE_AND_BLOCKED_TEMPORARY"
  }
}
```

#### Reset all soft failed attempt counters - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "resetCounterCount": 1
  }
}
```
<!-- end -->

## OTP API

<!-- begin api POST /otp -->
### Create an OTP

Create an OTP.

<!-- begin remove -->
#### Create an OTP - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `OTP_DEFINITION_NOT_FOUND` - OTP definition with name specified in the request was not found |
| 400  | `CREDENTIAL_DEFINITION_NOT_FOUND` - credential definition with name specified in the request was not found |
| 400  | `OPERATION_NOT_FOUND` - operation was not found |
| 400  | `OTP_GEN_ALGORITHM_NOT_SUPPORTED` - OTP generation algorithm is not supported |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `OPERATION_ALREADY_FINISHED` - operation is already in `DONE` state |
| 400  | `OPERATION_ALREADY_FAILED` - operation is already in `FAILED` state |
| 400  | `USER_IDENTITY_NOT_ACTIVE` - user identity is not active |
| 400  | `CREDENTIAL_NOT_FOUND` - credential was not found |
| 400  | `CREDENTIAL_NOT_ACTIVE` - credential is not in `ACTIVE` state |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Create an OTP - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "test1234",
    "otpName": "RETAIL_OTP",
    "credentialName": "RETAIL_CREDENTIAL",
    "otpData": "TEST_DATA",
    "operationId": null
  }
}
```

#### Create an OTP - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "otpName": "RETAIL_OTP",
    "userId": "test1234",
    "otpId": "b498adb3-84aa-4235-8ffb-d8e9daa54145",
    "otpValue": "85092023",
    "otpStatus": "ACTIVE"
  }
}
```
<!-- end -->

<!-- begin api POST /otp/send -->
### Create And Send an OTP

Create and send an OTP via Data Adapter.

<!-- begin remove -->
#### Create and send an OTP - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/send</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `OTP_DEFINITION_NOT_FOUND` - OTP definition with name specified in the request was not found |
| 400  | `CREDENTIAL_DEFINITION_NOT_FOUND` - credential definition with name specified in the request was not found |
| 400  | `OPERATION_NOT_FOUND` - operation was not found |
| 400  | `OTP_GEN_ALGORITHM_NOT_SUPPORTED` - OTP generation algorithm is not supported |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `OPERATION_ALREADY_FINISHED` - operation is already in `DONE` state |
| 400  | `OPERATION_ALREADY_FAILED` - operation is already in `FAILED` state |
| 400  | `USER_IDENTITY_NOT_ACTIVE` - user identity is not active |
| 400  | `CREDENTIAL_NOT_FOUND` - credential was not found |
| 400  | `CREDENTIAL_NOT_ACTIVE` - credential is not in `ACTIVE` state |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Create and send an OTP - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "test1234",
    "otpName": "RETAIL_OTP",
    "credentialName": "RETAIL_CREDENTIAL",
    "otpData": "TEST_DATA",
    "operationId": null,
    "language": "en"
  }
}
```

#### Create and send an OTP - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "otpName": "RETAIL_OTP",
    "userId": "test1234",
    "otpId": "b498adb3-84aa-4235-8ffb-d8e9daa54145",
    "otpStatus": "ACTIVE",
    "delivered": true,
    "errorMessage": null
  }
}
```
<!-- end -->

<!-- begin api GET /otp -->
### Get OTP list

Get OTP list for an operation.

This method has a `POST /otp/list` alternative.

<!-- begin remove -->
#### Get OTP list - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/list</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `OPERATION_NOT_FOUND` - operation was not found |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Get OTP list - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationId": "login_1234567",
    "includeRemoved": false
  }
}
```

#### Get OTP list - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "operationId": "login_1234567",
    "otpDetails": [
      {
        "otpName": "RETAIL_OTP",
        "userId": "user1234",
        "otpId": "6bc3c99a-63fb-446a-a187-4c6a0bf0a63a",
        "operationId": "login_1234567",
        "otpData": "TEST_DATA",
        "otpValue": "82310309",
        "credentialName": "RETAIL_CREDENTIAL",
        "attemptCounter": 0,
        "failedAttemptCounter": 0,
        "remainingAttempts": 3,
        "otpStatus": "ACTIVE",
        "timestampCreated": "2021-07-02T13:02:55+0000",
        "timestampVerified": null,
        "timestampBlocked": null,
        "timestampExpires": "2021-07-02T13:07:55+0000"
      }
    ]
  }
}
```
<!-- end -->

<!-- begin api GET /otp/detail -->
### Get OTP detail

Get OTP detail.

This method has a `POST /otp/detail` alternative.

<!-- begin remove -->
#### Get OTP detail - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>GET</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/detail</code></td>
    </tr>
</table>

Alternative with `POST` method:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/detail</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `OTP_NOT_FOUND` - OTP with identifier specified in the request was not found |
| 400  | `OPERATION_NOT_FOUND` - operation was not found |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Get OTP detail - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "otpId": "6bc3c99a-63fb-446a-a187-4c6a0bf0a63a",
    "operationId": null
  }
}
```

#### Get OTP detail - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "operationId": null,
    "otpDetail": {
      "otpName": "RETAIL_OTP",
      "userId": "user1234",
      "otpId": "6bc3c99a-63fb-446a-a187-4c6a0bf0a63a",
      "operationId": "login_1234567",
      "otpData": "TEST_DATA",
      "otpValue": "82310309",
      "credentialName": "RETAIL_CREDENTIAL",
      "attemptCounter": 0,
      "failedAttemptCounter": 0,
      "remainingAttempts": 3,
      "otpStatus": "ACTIVE",
      "timestampCreated": "2021-07-02T13:02:55+0000",
      "timestampVerified": null,
      "timestampBlocked": null,
      "timestampExpires": "2021-07-02T13:07:55+0000"
    }
  }
}
```
<!-- end -->

<!-- begin api DELETE /otp -->
### Delete an OTP.

Delete an OTP.

This method has a `POST /otp/delete` alternative.

<!-- begin remove -->
#### Delete a credential - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp</code></td>
    </tr>
</table>

Alternative with `POST` method for environments which do not allow `DELETE` methods:
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/otp/delete</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `OTP_NOT_FOUND` - OTP with identifier specified in the request was not found |
| 400  | `OPERATION_NOT_FOUND` - operation was not found |
| 500  | Server error - unexpected error occurred |

#### Delete an OTP - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "otpId": "6bc3c99a-63fb-446a-a187-4c6a0bf0a63a",
    "operationId": null
  }
}
```

#### Delete an OTP - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "otpId": "6bc3c99a-63fb-446a-a187-4c6a0bf0a63a",
    "operationId": "login_1234567",
    "otpStatus": "REMOVED"
  }
}
```
<!-- end -->

## Authentication API

<!-- begin api POST /auth/otp -->
### Authenticate Using an OTP

Authenticate using a one time password.

<!-- begin remove -->
#### Authenticate using an OTP - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/auth/otp</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `AUTH_METHOD_NOT_FOUND` - authentication method was not found |
| 400  | `OPERATION_ALREADY_FINISHED` - operation is already in `DONE` state |
| 400  | `OPERATION_ALREADY_FAILED` - operation is already in `FAILED` state |
| 400  | `OPERATION_ALREADY_CANCELED` - operation is already in `FAILED/CANCELED` state |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `CREDENTIAL_NOT_FOUND` - credential was not found |
| 400  | `OPERATION_NOT_FOUND` - operation was not found |
| 400  | `OTP_NOT_FOUND` - OTP with identifier specified in the request was not found |
| 400  | `OPERATION_NOT_VALID` - operation is not valid |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Authenticate using an OTP - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "otpId": "b498adb3-84aa-4235-8ffb-d8e9daa54145",
    "operationId": null,
    "otpValue": "37325969",
    "authMethod": null,
    "updateOperation": false
  }
}
```

#### Authenticate using an OTP - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "test1234",
    "userIdentityStatus": "ACTIVE",
    "credentialStatus": "ACTIVE",
    "timestampBlocked": null,
    "otpStatus": "ACTIVE",
    "authenticationResult": "FAILED",
    "remainingAttempts": 2,
    "showRemainingAttempts": false,
    "errorMessage": null,
    "operationFailed": false
  }
}
```
<!-- end -->

<!-- begin api POST /auth/credential -->
### Authenticate Using a Credential

Authenticate using a credential.

<!-- begin remove -->
#### Authenticate using a credential - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/auth/credential</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity was not found |`
| 400  | `AUTH_METHOD_NOT_FOUND` - authentication method was not found |
| 400  | `OPERATION_ALREADY_FINISHED` - operation is already in `DONE` state |
| 400  | `OPERATION_ALREADY_FAILED` - operation is already in `FAILED` state |
| 400  | `OPERATION_ALREADY_CANCELED` - operation is already in `FAILED/CANCELED` state |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `OPERATION_NOT_FOUND` - operation was not found |
| 400  | `CREDENTIAL_NOT_FOUND` - credential was not found |
| 400  | `CREDENTIAL_DEFINITION_NOT_FOUND` - credential definition was not found |
| 400  | `OPERATION_NOT_VALID` - operation is not valid |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Authenticate using a credential - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "credentialName": "RETAIL_CREDENTIAL",
    "userId": "test1234",
    "credentialValue": "OTuGEsf<n8Ue",
    "authenticationMode": "MATCH_EXACT",
    "credentialPositionsToVerify": [],
    "operationId": null,
    "authMethod": null,
    "updateOperation": false
  }
}
```

#### Authenticate using a credential - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "test1234",
    "userIdentityStatus": "ACTIVE",
    "timestampBlocked": null,
    "credentialStatus": "ACTIVE",
    "credentialChangeRequired": false,
    "authenticationResult": "FAILED",
    "remainingAttempts": 2,
    "showRemainingAttempts": false,
    "errorMessage": null,
    "operationFailed": false
  }
}
```
<!-- end -->

<!-- begin api POST /auth/combined -->
### Authenticate Using a Credential and OTP

Authenticate using a credential and one time password.

<!-- begin remove -->
#### Authenticate using a credential and OTP - parameters
<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/auth/combined</code></td>
    </tr>
</table>
<!-- end -->

| Code | Description |
|------|-------------|
| 200  | OK response - request succeeded |
| 400  | `REQUEST_VALIDATION_FAILED` - request validation failed |
| 400  | `INVALID_REQUEST` - invalid request received |
| 400  | `USER_IDENTITY_NOT_FOUND` - user identity was not found |`
| 400  | `AUTH_METHOD_NOT_FOUND` - authentication method was not found |
| 400  | `OPERATION_ALREADY_FINISHED` - operation is already in `DONE` state |
| 400  | `OPERATION_ALREADY_FAILED` - operation is already in `FAILED` state |
| 400  | `OPERATION_ALREADY_CANCELED` - operation is already in `FAILED/CANCELED` state |
| 400  | `INVALID_CONFIGURATION` - Next Step configuration is invalid |
| 400  | `CREDENTIAL_NOT_FOUND` - credential was not found |
| 400  | `OPERATION_NOT_FOUND` - operation was not found |
| 400  | `OTP_NOT_FOUND` - OTP with identifier specified in the request was not found |
| 400  | `OPERATION_NOT_VALID` - operation is not valid |
| 400  | `ENCRYPTION_FAILED` - encryption failed |
| 500  | Server error - unexpected error occurred |

#### Authenticate using a credential and OTP - request

- Headers:
  - `Content-Type: application/json`

```json
{
  "requestObject": {
    "credentialName": "RETAIL_CREDENTIAL",
    "userId": "test1234",
    "credentialValue": ")wOI6ijUkwYI",
    "authenticationMode": "MATCH_EXACT",
    "credentialPositionsToVerify": [],
    "otpId": "b498adb3-84aa-4235-8ffb-d8e9daa54145",
    "operationId": null,
    "otpValue": "29092692",
    "authMethod": null,
    "updateOperation": false
  }
}
```

#### Authenticate using a credential and OTP - response

- Status Code: `200`
- Headers:
  - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userId": "test1234",
    "userIdentityStatus": "ACTIVE",
    "timestampBlocked": null,
    "credentialStatus": "ACTIVE",
    "credentialChangeRequired": false,
    "otpStatus": "ACTIVE",
    "authenticationResult": "FAILED",
    "credentialAuthenticationResult": "FAILED",
    "otpAuthenticationResult": "FAILED",
    "remainingAttempts": 1,
    "showRemainingAttempts": false,
    "errorMessage": null,
    "operationFailed": false
  }
}
```
<!-- end -->

<!-- end -->
