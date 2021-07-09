# Mobile Token Configuration

Mobile Token application requires configuration in order to set an active activation for the `POWERAUTH_TOKEN` authentication method for given user. Mobile token configuration should be performed when user activates the mobile token (e.g. in internet banking). When the user disables the mobile token, the activation should be unconfigured, too.

Web Flow uses the configured activation when sending push notifications and when authorizing operations using Mobile Token. When activation is not configured for the user, the `POWERAUTH_TOKEN` authentication method is not available and the user needs to use an alternative method for authorization of the operation (e.g. `SMS_KEY`).

See the [Next Step REST API documentation](./Next-Step-Server-REST-API-Reference.md#enable-an-authentication-method-for-given-user) which describes this configuration step.

## Enabling Mobile Token

The mobile token needs to be enabled using following configuration parameters in the Next Step application:

- Mobile token needs to be enabled in table `ns_operation_config` using the `mobile_token_enabled` column. This parameter configures whether 
mobile token is enabled for given operation. Mobile token can be also enabled for an operation using the `POST /operation/config` call and the current status is queried using `POST /operation/config/detail` call.
- Mobile token needs to be enabled in table `ns_auth_method` using the `has_mobile_token` column. This parameter configures whether authentication method supports mobile token. Mobile token can be also enabled for an authentication method using the `POST /auth-method` call and the current status is queried using `POST /auth-method/list` call.
It should not be necessary to change the default settings, but it is possible to change on authentication method level whether mobile token is enabled.
- Mobile token needs to be enabled in table `ns_user_prefs`. The `ns_auth_method.user_prefs_column` parameter specifies using which column is the mobile token enabled.
Furthermore the activation ID for mobile needs to be configured, as explained above. Both enabling mobile token in user preferences and setting the activation
ID is typically done by calling `POST /user/auth-method`.