# Configuring Next Step

During Web Flow and Next Step deployment authentication methods, organizations, operation configurations, and next step definitions need to be customized. In case Next Step is used to store user identities, additional configuration of credential and one time password policies and definitions is required. The customization is usually done using SQL, however a RESTful API is also available for each of the configuration steps.

## Configuration of Authentication Methods

Following authentication methods are available:
- `INIT` - operation initialization, executed automatically when operation is started
- `USER_ID_ASSIGN` - resolution of user identity without requiring the user to sign in
- `USERNAME_PASSWORD_AUTH` - user signs in using form based authentication by supplying username and password credentials
- `LOGIN_SCA` - login with either mobile token or SMS and password supporting strong customer authentication
- `POWERAUTH_TOKEN` - user authorizes the operation using PowerAuth mobile token
- `SMS_KEY` - user authorizes the operation using SMS message with OTP
- `APPROVAL_SCA` - operation approval with either mobile token or SMS and password supporting strong customer authentication 
- `CONSENT` - OAuth 2.0 consent form with options to approve by the user
- `OTP_CODE` - a generic OTP code authentication method which may be delivered by other channel than SMS

The following parameters can be configured:
- `auth_method` - authentication method name
- `order_number` - unique order number
- `check_user_prefs` - should authentication method check its availability using user preferences
- `user_prefs_column` - column number in which the user preferences are stored
- `user_prefs_default` - `TRUE` if authentication method is enabled by default, otherwise `FALSE`
- `check_auth_fails` - should authentication method check number of failed authentication attempts
- `max_auth_fails` - maximum number of allowed authentication attempts for this authentication method
- `has_user_interface` - `TRUE` if authentication method has user interface, otherwise `FALSE`
- `display_name_key` - localization key with authentication method name
- `has_mobile_token` - whether authentication method is compatible with mobile token

The SQL example below shows how to configure authentication methods.

Oracle:
```sql
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('INIT', 1, 0, NULL, NULL, 0, NULL, 0, 0, NULL);
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('USER_ID_ASSIGN', 2, 0, NULL, NULL, 0, NULL, 0, 0, NULL);
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('USERNAME_PASSWORD_AUTH', 3, 0, NULL, NULL, 1, 5, 1, 0, 'method.usernamePassword');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('SHOW_OPERATION_DETAIL', 4, 0, NULL, NULL, 0, NULL, 1, 0, 'method.showOperationDetail');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('POWERAUTH_TOKEN', 5, 1, 1, 0, 1, 5, 1, 1, 'method.powerauthToken');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('SMS_KEY', 6, 0, NULL, NULL, 1, 5, 1, 0, 'method.smsKey');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('CONSENT', 7, 0, NULL, NULL, 1, 5, 1, 0, 'method.consent');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('LOGIN_SCA', 8, 0, NULL, NULL, 1, 5, 1, 1, 'method.loginSca');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('APPROVAL_SCA', 9, 0, NULL, NULL, 1, 5, 1, 1, 'method.approvalSca');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('OTP_CODE', 10, 0, NULL, NULL, 1, 3, 1, 0, 'method.otpCode');

```

MySQL:
```sql
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('INIT', 1, FALSE, NULL, NULL, FALSE, NULL, FALSE, FALSE, NULL);
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('USER_ID_ASSIGN', 2, FALSE, NULL, NULL, FALSE, NULL, FALSE, FALSE, NULL);
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('USERNAME_PASSWORD_AUTH', 3, FALSE, NULL, NULL, TRUE, 5, TRUE, FALSE, 'method.usernamePassword');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('SHOW_OPERATION_DETAIL', 4, FALSE, NULL, NULL, FALSE, NULL, TRUE, FALSE, 'method.showOperationDetail');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('POWERAUTH_TOKEN', 5, TRUE, 1, FALSE, TRUE, 5, TRUE, TRUE, 'method.powerauthToken');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('SMS_KEY', 6, FALSE, NULL, NULL, TRUE, 5, TRUE, FALSE, 'method.smsKey');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('CONSENT', 7, FALSE, NULL, NULL, TRUE, 5, TRUE, FALSE, 'method.consent');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('LOGIN_SCA', 8, FALSE, NULL, NULL, TRUE, 5, TRUE, TRUE, 'method.loginSca');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('APPROVAL_SCA', 9, FALSE, NULL, NULL, TRUE, 5, TRUE, TRUE, 'method.approvalSca');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('OTP_CODE', 10, FALSE, NULL, NULL, TRUE, 3, TRUE, FALSE, 'method.otpCode');
```

PostgreSQL:
```sql
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('INIT', 1, FALSE, NULL, NULL, FALSE, NULL, FALSE, FALSE, NULL);
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('USER_ID_ASSIGN', 2, FALSE, NULL, NULL, FALSE, NULL, FALSE, FALSE, NULL);
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('USERNAME_PASSWORD_AUTH', 3, FALSE, NULL, NULL, TRUE, 5, TRUE, FALSE, 'method.usernamePassword');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('SHOW_OPERATION_DETAIL', 4, FALSE, NULL, NULL, FALSE, NULL, TRUE, FALSE, 'method.showOperationDetail');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('POWERAUTH_TOKEN', 5, TRUE, 1, FALSE, TRUE, 5, TRUE, TRUE, 'method.powerauthToken');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('SMS_KEY', 6, FALSE, NULL, NULL, TRUE, 5, TRUE, FALSE, 'method.smsKey');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('CONSENT', 7, FALSE, NULL, NULL, TRUE, 5, TRUE, FALSE, 'method.consent');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('LOGIN_SCA', 8, FALSE, NULL, NULL, TRUE, 5, TRUE, TRUE, 'method.loginSca');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('APPROVAL_SCA', 9, FALSE, NULL, NULL, TRUE, 5, TRUE, TRUE, 'method.approvalSca');
INSERT INTO ns_auth_method (auth_method, order_number, check_user_prefs, user_prefs_column, user_prefs_default, check_auth_fails, max_auth_fails, has_user_interface, has_mobile_token, display_name_key)
VALUES ('OTP_CODE', 10, FALSE, NULL, NULL, TRUE, 3, TRUE, FALSE, 'method.otpCode');
```

## Organization Configuration

Next Step requires at least one organization configured. The default configuration is following:

Oracle:
```sql
INSERT INTO ns_organization (organization_id, display_name_key, is_default, order_number) VALUES ('DEFAULT', null, 1, 1);
```

MySQL:
```sql
INSERT INTO ns_organization (organization_id, display_name_key, is_default, order_number) VALUES ('DEFAULT', null, TRUE, 1);
```

The default configuration assigns the `DEFAULT` organization to all operations. You can define multiple organizations to support
authentication for multiple segments which can have overlapping user IDs, e.g.:

Oracle:
```sql
INSERT INTO ns_organization (organization_id, display_name_key, is_default, order_number) VALUES ('RETAIL', 'organization.retail', 1, 1);
INSERT INTO ns_organization (organization_id, display_name_key, is_default, order_number) VALUES ('SME', 'organization.sme', 0, 2);
```

MySQL:
```sql
INSERT INTO ns_organization (organization_id, display_name_key, is_default, order_number) VALUES ('RETAIL', 'organization.retail', TRUE, 1);
INSERT INTO ns_organization (organization_id, display_name_key, is_default, order_number) VALUES ('SME', 'organization.sme', FALSE, 2);
```

Such configuration defines two organizations `RETAIL` and `SME`. The user sees two tabs when authenticating with localized labels
based on keys `organization.retail` and `organization.sme`. The user can switch the organization against which the authentication is performed.
The `RETAIL` organization is the default one (it is preselected in the UI). The order of displayed organizations is defined as
`RETAIL`, `SME` using the last parameter.

<!-- begin box warning -->
In case you configure multiple organizations make sure the user ID used in PowerAuth Web Flow, PowerAuth Server and PowerAuth Push Server is unique across all organizations, and it is consistent in all PowerAuth backends. You can achieve this requirement by assigning unique user IDs in different organizations during user authentication. Alternatively the uniqueness requirement can be achieved by adding a prefix to all user IDs based on the organization against which the user was authenticated (e.g. `RETAIL.12345678`).
<!-- end -->

Each organization requires following configuration:
- `organization_id` - an identifier of the organization, such as `RETAIL` or `SME`
- `display_name_key` - internationalization key for the organization used in Web Flow when displaying organization name
- `is_default` - whether the organization is the default one, set this flag to `TRUE` only for one organization
- `order_numer` - order of the organization in the Web Flow UI, starting by 1
- `default_credential_name` - default credential name is used by Web Flow when performing credential authentication using Next Step
- `default_credential_name` - default credential name is used by Web Flow when performing OTP authentication using Next Step

```sql
INSERT INTO ns_organization (organization_id, display_name_key, is_default, order_number, default_credential_name, default_otp_name) VALUES ('RETAIL', 'organization.retail', TRUE, 1, 'RETAIL_CREDENTIAL', 'RETAIL_OTP');
```

The organization can be created using REST API by calling the `POST /organization` endpoint.

## Operation Configuration

Operations need to be configured in table `ns_operation_config`.

Following parameters are configured for each operation:
- `operation_name` - unique operation name which identifies the operation
- `template_version` - template version, see documentation for [Operation Data](./Operation-Data.md)
  - currently used template version is `A`
- `template_id` - template identifier, see documentation for [Operation Data](./Operation-Data.md)
  - for approval (payment) use `1`
  - for login use `2`
- `mobile_token_enabled` - whether mobile token is enabled for this operation
- `mobile_token_mode` - configuration of mobile token factors, use only when `mobile_token_enabled` value is true
  - for 1FA use: `{"type":"1FA"}`
  - for 2FA use e.g.: `{"type":"2FA","variants":["possession_knowledge","possession_biometry"]}`
- `afs_enabled` - whether anti-fraud service integration is enabled for this operation
- `afs_config_id` - identifier of AFS configuration, use only when `afs_enabled` value is true

Sample configuration:

```sql
INSERT INTO ns_operation_config (operation_name, template_version, template_id, mobile_token_enabled, mobile_token_mode) VALUES ('login', 'A', 2, FALSE, '{"type":"2FA","variants":["possession_knowledge","possession_biometry"]}');
```

The operation configuration can be created using REST API by calling the `POST /operation/config` endpoint.

## Configuration of Next Step Applications

At least one Next Step application must be configured. Each application requires following configuration:
- `application_id` - Next Step application identifier
- `name` - application name
- `description` - description of the application
- `status` - application status: `ACTIVE` or `REMOVED`
- `timestamp_created` - timestamp when application was created

```sql
INSERT INTO ns_application (application_id, name, description, status, timestamp_created) values (1, 'APP', 'Sample application', 'ACTIVE', CURRENT_TIMESTAMP);
```

The application can be created using REST API by calling the `POST /application` endpoint.

## Configuration of Next Step Credential Policies

At least one Next Step credential policy must be configured in case credential authentication is performed. The configuration is not used when Data Adapter proxy is enabled, in this case the configuration record is prepared for potential future switch to Next Step identity once Data Adapter proxy is disabled.

The credential policy requires following configuration:

- `credential_policy_id` - credential policy identifier
- `name` - credential policy name
- `description` - description of the credential policy
- `status` - credential policy status: `ACTIVE` or `REMOVED`
- `username_length_min` - minimum length of the username, use `NULL` value for no limit
- `username_length_max` - maximum length of the username, use `NULL` value for no limit
- `username_allowed_pattern` - regular expression for checking the username pattern, use `NULL` value for no check
- `credential_length_min` - minimum length of the credential, use `NULL` value for no limit
- `credential_length_max` - maximum length of the credential, use `NULL` value for no limit
- `limit_soft` - soft limit for failed authentication attempts using credential (credential status `BLOCKED_TEMPORARY` when limit is exceeded), use `0` for no limit
- `limit_hard` - hard limit for failed authentication attempts using credential (credential status `BLOCKED_PERMANENT` when limit is exceeded), use `0` for no limit
- `check_history_count` - count of historical credential values which should be checked when changing the credential, use `0` for skipping the check
- `rotation_enabled` - whether credential rotation is enabled
- `rotation_days`- number of days for credential rotation, only specify when credential rotation is enabled
- `credential_temp_expiration` - expiration of `TEMPORARY` credentials in seconds
- `username_gen_algorithm` - name of algorithm used for generating username: `NO_USERNAME`, `RANDOM_DIGITS`, or `RANDOM_LETTERS`
- `username_gen_param` - parameters of the username generating algorithm: `length` (only applies to algorithms `RANDOM_DIGITS` and `RANDOM_LETTERS`)
- `credential_gen_algorithm` - name of algorithm used for generating credentials: `RANDOM_PASSWORD` or `RANDOM_PIN`
- `credential_gen_param` - parameters of the credential generating algorithm: `length`, `includeSmallLetters`, `smallLettersCount`, `includeCapitalLetters`, `capitalLettersCount`, `includeDigits`, `digitsCount`, `includeSpecialChars`, `specialCharsCount`
- `credential_val_param` - parameters of credential validation based on the [Passay library rules](https://www.passay.org): `includeWhitespaceRule`, `includeUsernameRule`, `includeAllowedCharacterRule`, `allowedChars`, `includeAllowedRegexRule`, `allowedRegex`, `includeIllegalCharacterRule`, `illegalChars`, `includeIllegalRegexRule`, `illegalRegex`, `includeCharacterRule`, `includeSmallLetters`, `smallLettersMin`, `includeCapitalLetters`, `capitalLettersMin`, `includeAlphabeticalLetters`, `alphabeticalLettersMin`, `includeDigits`, `digitsMin`, `includeSpecialChars`, `specialCharsMin`
- `timestamp_created` - timestamp when credential policy was created

```sql
INSERT INTO ns_credential_policy (credential_policy_id, name, description, status, username_length_min, username_length_max, username_allowed_pattern, credential_length_min, credential_length_max, limit_soft, limit_hard, check_history_count, rotation_enabled, username_gen_algorithm, username_gen_param, credential_gen_algorithm, credential_gen_param, credential_val_param, timestamp_created) values (1, 'CREDENTIAL_POLICY', 'Sample credential policy', 'ACTIVE', 8, 20, '[0-9]+', 8, 40, 3, 5, 3, 0, 'RANDOM_DIGITS', '{"length": 8}', 'RANDOM_PASSWORD', '{"length": 12, "includeSmallLetters": true, "smallLettersCount": 5, "includeCapitalLetters": true, "capitalLettersCount": 5, "includeDigits": true, "digitsCount": 1, "includeSpecialChars": true, "specialCharsCount": 1}', '{"includeWhitespaceRule": true, "includeUsernameRule": true, "includeAllowedCharacterRule": false, "allowedChars": "", "includeAllowedRegexRule": false, "allowedRegex": ".*", "includeIllegalCharacterRule": false, "illegalChars": "", "includeIllegalRegexRule": false, "illegalRegex": "", "includeCharacterRule": true, "includeSmallLetters": "true", "smallLettersMin": 1, "includeCapitalLetters": true, "capitalLettersMin": 1, "includeAlphabeticalLetters": true, "alphabeticalLettersMin": 2, "includeDigits": true, "digitsMin": 1, "includeSpecialChars": true, "specialCharsMin": 1}', CURRENT_TIMESTAMP);
```

The credential policy can be created using REST API by calling the `POST /credential/policy` endpoint.

## Configuration of Next Step OTP Policies

At least one Next Step OTP policy must be configured in case OTP authentication is performed. The configuration is not used when Data Adapter proxy is enabled, in this case the configuration record is prepared for potential future switch to Next Step identity once Data Adapter proxy is disabled.

The credential policy requires following configuration:
- `otp_policy_id` - OTP policy identifier
- `name` - OTP policy name
- `description` - description of the OTP policy
- `status` - OTP policy status: `ACTIVE` or `REMOVED`
- `length` - length of the OTP authorization code
- `attempt_limit` - limit for failed authentication attempts using OTP (OTP status `BLOCKED` when limit is exceeded), use `0` for no limit
- `expiration_time` - expiration of OTP record
- `gen_algorithm` - name of algorithm used for generating OTP authorization code: `OTP_DATA_DIGEST` or `OTP_RANDOM_DIGIT_GROUPS`
- `gen_param` - parameters of the OTP authorization code generating algorithm: `groupSize` (only applies to algorithm `OTP_RANDOM_DIGIT_GROUPS`)
- `timestamp_created` - timestamp when OTP policy was created

```sql
INSERT INTO ns_otp_policy (otp_policy_id, name, description, status, length, attempt_limit, expiration_time, gen_algorithm, gen_param, timestamp_created) values (1, 'OTP_POLICY', 'Sample OTP policy', 'ACTIVE', 8, 3, 300, 'OTP_DATA_DIGEST', '{}', CURRENT_TIMESTAMP);
```

The credential policy can be created using REST API by calling the `POST /credential/policy` endpoint.

## Configuration of Next Step Credential Definitions

At least one Next Step credential definition must be configured in case credential authentication is performed. The configuration is not used when Data Adapter proxy is enabled, in this case the configuration record is prepared for potential future switch to Next Step identity once Data Adapter proxy is disabled.

The credential definition requires following configuration:
- `credential_definition_id` - credential definition identifier
- `name` - credential definition name
- `description` - description of the credential definition
- `application_id` - application identifier
- `organization_id` - organization identifier
- `credential_policy_id` - credential policy identifier
- `category` - credential category: `PASSWORD`, `PIN`, or `OTHER`
- `data_adapter_proxy_enabled` - whether credential verification requests should be proxied via Data Adapter
- `status` - credential definition status: `ACTIVE` or `REMOVED`
- `timestamp_created` -  timestamp when credential definition was created

```sql
INSERT INTO ns_credential_definition (credential_definition_id, name, description, application_id, organization_id, credential_policy_id, category, data_adapter_proxy_enabled, status, timestamp_created) values (1, 'RETAIL_CREDENTIAL', 'Sample credential definition for retail', 1, 'RETAIL', 1, 'PASSWORD', 1, 'ACTIVE', CURRENT_TIMESTAMP);
```

## Configuration of Next Step OTP Definitions

At least one Next Step OTP definition must be configured in case OTP authentication is performed. The configuration is not used when Data Adapter proxy is enabled, in this case the configuration record is prepared for potential future switch to Next Step identity once Data Adapter proxy is disabled.

The credential definition requires following configuration:
- `otp_definition_id` - OTP definition identifier
- `name` - OTP definition name
- `description` - description of the OTP definition
- `application_id` - application identifier
- `organization_id` - organization identifier
- `otp_policy_id` - OTP policy identifier
- `data_adapter_proxy_enabled` - whether OTP verification requests should be proxied via Data Adapter
- `status` - credential definition status: `ACTIVE` or `REMOVED`
- `timestamp_created` -  timestamp when OTP definition was created

```sql
INSERT INTO ns_otp_definition (otp_definition_id, name, description, application_id, otp_policy_id, data_adapter_proxy_enabled, status, timestamp_created) values (1, 'RETAIL_OTP', 'Sample OTP definition for retail', 1, 1, 1, 'ACTIVE', CURRENT_TIMESTAMP);
```

## Configuration of Next Steps

Following steps are required for next step definition:
* Choose operation names, each operation should have a unique name
* Choose authentication methods which should be used during the operation
* Specify mapping of the next steps using SQL

The next step mapping has following inputs:
- `operation_name` - name of the operation differenciating operations (`login`, `authorize_payment`, etc.)
- `operation_type` - type of the operation
  - `CREATE` - new operation
  - `UPDATE` - existing operation
- `request_auth_method` - current authentication method
- `request_auth_step_result` - result of current authentication step

The next step mapping has following outputs:
- `response_priority` - priority of the step in case multiple next steps are returned
- `response_auth_method` - next authentication method to execute
- `response_result` - authentication result
  - `CONTINUE` - next step should be performed
  - `FAILED` - authentication has failed
  - `DONE` - authentication is complete

The SQL example below shows how to configure next step definitions.

```sql
-- login - init operation -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (1, 'login', 'CREATE', NULL, NULL, 1, 'USER_ID_ASSIGN', 'CONTINUE');
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (2, 'login', 'CREATE', NULL, NULL, 2, 'USERNAME_PASSWORD_AUTH', 'CONTINUE');

-- login - update operation - CANCELED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (3, 'login', 'UPDATE', 'INIT', 'CANCELED', 1, NULL, 'FAILED');

-- login - update operation - CONFIRMED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (4, 'login', 'UPDATE', 'USER_ID_ASSIGN', 'CONFIRMED', 1, 'CONSENT', 'CONTINUE');
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (5, 'login', 'UPDATE', 'USERNAME_PASSWORD_AUTH', 'CONFIRMED', 1, 'CONSENT', 'CONTINUE');

-- login - update operation - CANCELED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (6, 'login', 'UPDATE', 'USER_ID_ASSIGN', 'CANCELED', 1, NULL, 'FAILED');
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (7, 'login', 'UPDATE', 'USERNAME_PASSWORD_AUTH', 'CANCELED', 1, NULL, 'FAILED');

-- login - update operation - AUTH_METHOD_FAILED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (8, 'login', 'UPDATE', 'USER_ID_ASSIGN', 'AUTH_METHOD_FAILED', 1, NULL, 'FAILED');
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (9, 'login', 'UPDATE', 'USERNAME_PASSWORD_AUTH', 'AUTH_METHOD_FAILED', 1, NULL, 'FAILED');

-- login - update operation - AUTH_FAILED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (11, 'login', 'UPDATE', 'USER_ID_ASSIGN', 'AUTH_FAILED', 1, 'USER_ID_ASSIGN', 'CONTINUE');
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (12, 'login', 'UPDATE', 'USERNAME_PASSWORD_AUTH', 'AUTH_FAILED', 1, 'USERNAME_PASSWORD_AUTH', 'CONTINUE');

-- login - update operation (consent) - CONFIRMED -> DONE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (13, 'login', 'UPDATE', 'CONSENT', 'CONFIRMED', 1, NULL, 'DONE');

-- login - update operation (consent) - CANCELED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (14, 'login', 'UPDATE', 'CONSENT', 'CANCELED', 1, NULL, 'FAILED');

-- login - update operation (consent) - AUTH_METHOD_FAILED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (15, 'login', 'UPDATE', 'CONSENT', 'AUTH_METHOD_FAILED', 1, NULL, 'FAILED');

-- login - update operation (consent) - AUTH_FAILED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (16, 'login', 'UPDATE', 'CONSENT', 'AUTH_FAILED', 1, 'CONSENT', 'CONTINUE');

-- authorize_payment - init operation -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (17, 'authorize_payment', 'CREATE', NULL, NULL, 1, 'USER_ID_ASSIGN', 'CONTINUE');
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (18, 'authorize_payment', 'CREATE', NULL, NULL, 2, 'USERNAME_PASSWORD_AUTH', 'CONTINUE');

-- authorize_payment - update operation - CANCELED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (19, 'authorize_payment', 'UPDATE', 'INIT', 'CANCELED', 1, NULL, 'FAILED');

-- authorize_payment - update operation (login) - CONFIRMED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (20, 'authorize_payment', 'UPDATE', 'USER_ID_ASSIGN', 'CONFIRMED', 1, 'POWERAUTH_TOKEN', 'CONTINUE');
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (21, 'authorize_payment', 'UPDATE', 'USER_ID_ASSIGN', 'CONFIRMED', 2, 'SMS_KEY', 'CONTINUE');

INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (22, 'authorize_payment', 'UPDATE', 'USERNAME_PASSWORD_AUTH', 'CONFIRMED', 1, 'POWERAUTH_TOKEN', 'CONTINUE');
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (23, 'authorize_payment', 'UPDATE', 'USERNAME_PASSWORD_AUTH', 'CONFIRMED', 2, 'SMS_KEY', 'CONTINUE');

-- authorize_payment - update operation (login) - CANCELED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (24, 'authorize_payment', 'UPDATE', 'USER_ID_ASSIGN', 'CANCELED', 1, NULL, 'FAILED');
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (25, 'authorize_payment', 'UPDATE', 'USERNAME_PASSWORD_AUTH', 'CANCELED', 1, NULL, 'FAILED');

-- authorize_payment - update operation (login) - AUTH_METHOD_FAILED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (26, 'authorize_payment', 'UPDATE', 'USER_ID_ASSIGN', 'AUTH_METHOD_FAILED', 1, NULL, 'FAILED');
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (27, 'authorize_payment', 'UPDATE', 'USERNAME_PASSWORD_AUTH', 'AUTH_METHOD_FAILED', 1, NULL, 'FAILED');

-- authorize_payment - update operation (login) - AUTH_FAILED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (28, 'authorize_payment', 'UPDATE', 'USER_ID_ASSIGN', 'AUTH_FAILED', 1, 'USER_ID_ASSIGN', 'CONTINUE');
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (29, 'authorize_payment', 'UPDATE', 'USERNAME_PASSWORD_AUTH', 'AUTH_FAILED', 1, 'USERNAME_PASSWORD_AUTH', 'CONTINUE');

-- authorize_payment - update operation (authorize using mobile token) - CONFIRMED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (30, 'authorize_payment', 'UPDATE', 'POWERAUTH_TOKEN', 'CONFIRMED', 1, 'CONSENT', 'CONTINUE');

-- authorize_payment - update operation (authorize using mobile token) - CANCELED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (31, 'authorize_payment', 'UPDATE', 'POWERAUTH_TOKEN', 'CANCELED', 1, NULL, 'FAILED');

-- authorize_payment - update operation (authorize using mobile token) - AUTH_METHOD_FAILED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (32, 'authorize_payment', 'UPDATE', 'POWERAUTH_TOKEN', 'AUTH_METHOD_FAILED', 1, NULL, 'FAILED');

-- authorize_payment - update operation (authorize using mobile token) - AUTH_FAILED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (33, 'authorize_payment', 'UPDATE', 'POWERAUTH_TOKEN', 'AUTH_FAILED', 1, 'POWERAUTH_TOKEN', 'CONTINUE');

-- authorize_payment - update operation (authorize using sms key) - CONFIRMED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (34, 'authorize_payment', 'UPDATE', 'SMS_KEY', 'CONFIRMED', 1, 'CONSENT', 'CONTINUE');

-- authorize_payment - update operation (authorize using sms key) - CANCELED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (35, 'authorize_payment', 'UPDATE', 'SMS_KEY', 'CANCELED', 1, NULL, 'FAILED');

-- authorize_payment - update operation (authorize using sms key) - AUTH_METHOD_FAILED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (36, 'authorize_payment', 'UPDATE', 'SMS_KEY', 'AUTH_METHOD_FAILED', 1, NULL, 'FAILED');

-- authorize_payment - update operation (authorize using sms key) - AUTH_FAILED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (37, 'authorize_payment', 'UPDATE', 'SMS_KEY', 'AUTH_FAILED', 1, 'SMS_KEY', 'CONTINUE');

-- authorize_payment - update operation (consent) - CONFIRMED -> DONE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (38, 'authorize_payment', 'UPDATE', 'CONSENT', 'CONFIRMED', 1, NULL, 'DONE');

-- authorize_payment - update operation (consent) - CANCELED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (39, 'authorize_payment', 'UPDATE', 'CONSENT', 'CANCELED', 1, NULL, 'FAILED');

-- authorize_payment - update operation (consent) - AUTH_METHOD_FAILED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (40, 'authorize_payment', 'UPDATE', 'CONSENT', 'AUTH_METHOD_FAILED', 1, NULL, 'FAILED');

-- authorize_payment - update operation (consent) - AUTH_FAILED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (41, 'authorize_payment', 'UPDATE', 'CONSENT', 'AUTH_FAILED', 1, 'CONSENT', 'CONTINUE');

-- login_sca - init operation -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (42, 'login_sca', 'CREATE', NULL, NULL, 1, 'LOGIN_SCA', 'CONTINUE');

-- login_sca - update operation - CANCELED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (43, 'login_sca', 'UPDATE', 'INIT', 'CANCELED', 1, 'INIT', 'FAILED');

-- login_sca - update operation (login) - CONFIRMED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (44, 'login_sca', 'UPDATE', 'LOGIN_SCA', 'CONFIRMED', 1, 'CONSENT', 'CONTINUE');

-- login_sca - update operation (login) - CANCELED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (45, 'login_sca', 'UPDATE', 'LOGIN_SCA', 'CANCELED', 1, NULL, 'FAILED');

-- login_sca - update operation (login) - AUTH_METHOD_FAILED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (46, 'login_sca', 'UPDATE', 'LOGIN_SCA', 'AUTH_METHOD_FAILED', 1, NULL, 'FAILED');

-- login_sca - update operation (login) - AUTH_FAILED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (47, 'login_sca', 'UPDATE', 'LOGIN_SCA', 'AUTH_FAILED', 1, 'LOGIN_SCA', 'CONTINUE');

-- login_sca - update operation (consent) - CONFIRMED -> DONE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (48, 'login_sca', 'UPDATE', 'CONSENT', 'CONFIRMED', 1, NULL, 'DONE');

-- login_sca - update operation (consent) - CANCELED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (49, 'login_sca', 'UPDATE', 'CONSENT', 'CANCELED', 1, NULL, 'FAILED');

-- login_sca - update operation (consent) - AUTH_METHOD_FAILED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (50, 'login_sca', 'UPDATE', 'CONSENT', 'AUTH_METHOD_FAILED', 1, NULL, 'FAILED');

-- login_sca - update operation (consent) - AUTH_FAILED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (51, 'login_sca', 'UPDATE', 'CONSENT', 'AUTH_FAILED', 1, 'CONSENT', 'CONTINUE');

-- authorize_payment_sca - init operation -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (52, 'authorize_payment_sca', 'CREATE', NULL, NULL, 1, 'LOGIN_SCA', 'CONTINUE');

-- authorize_payment_sca - update operation - CANCELED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (53, 'authorize_payment_sca', 'UPDATE', 'INIT', 'CANCELED', 1, 'INIT', 'FAILED');

-- authorize_payment_sca - update operation (login) - CONFIRMED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (54, 'authorize_payment_sca', 'UPDATE', 'LOGIN_SCA', 'CONFIRMED', 1, 'APPROVAL_SCA', 'CONTINUE');

-- authorize_payment_sca - update operation (login) - CANCELED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (55, 'authorize_payment_sca', 'UPDATE', 'LOGIN_SCA', 'CANCELED', 1, NULL, 'FAILED');

-- authorize_payment_sca - update operation (login) - AUTH_METHOD_FAILED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (56, 'authorize_payment_sca', 'UPDATE', 'LOGIN_SCA', 'AUTH_METHOD_FAILED', 1, NULL, 'FAILED');

-- authorize_payment_sca - update operation (login) - AUTH_FAILED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (57, 'authorize_payment_sca', 'UPDATE', 'LOGIN_SCA', 'AUTH_FAILED', 1, 'LOGIN_SCA', 'CONTINUE');

-- authorize_payment_sca - update operation (approval) - CONFIRMED -> DONE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (58, 'authorize_payment_sca', 'UPDATE', 'APPROVAL_SCA', 'CONFIRMED', 1, 'CONSENT', 'CONTINUE');

-- authorize_payment_sca - update operation (approval) - CANCELED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (59, 'authorize_payment_sca', 'UPDATE', 'APPROVAL_SCA', 'CANCELED', 1, NULL, 'FAILED');

-- authorize_payment_sca - update operation (approval) - AUTH_METHOD_FAILED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (60, 'authorize_payment_sca', 'UPDATE', 'APPROVAL_SCA', 'AUTH_METHOD_FAILED', 1, NULL, 'FAILED');

-- authorize_payment_sca - update operation (approval) - AUTH_FAILED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (61, 'authorize_payment_sca', 'UPDATE', 'APPROVAL_SCA', 'AUTH_FAILED', 1, 'APPROVAL_SCA', 'CONTINUE');

-- authorize_payment_sca - update operation (consent) - CONFIRMED -> DONE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (62, 'authorize_payment_sca', 'UPDATE', 'CONSENT', 'CONFIRMED', 1, NULL, 'DONE');

-- authorize_payment_sca - update operation (consent) - CANCELED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (63, 'authorize_payment_sca', 'UPDATE', 'CONSENT', 'CANCELED', 1, NULL, 'FAILED');

-- authorize_payment_sca - update operation (consent) - AUTH_METHOD_FAILED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (64, 'authorize_payment_sca', 'UPDATE', 'CONSENT', 'AUTH_METHOD_FAILED', 1, NULL, 'FAILED');

-- authorize_payment_sca - update operation (consent) - AUTH_FAILED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (65, 'authorize_payment_sca', 'UPDATE', 'CONSENT', 'AUTH_FAILED', 1, 'CONSENT', 'CONTINUE');

-- authorize_payment_sca - init operation -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (66, 'authorize_payment_sca', 'CREATE', null, null, 1, 'USER_ID_ASSIGN', 'CONTINUE');

-- authorize_payment_sca - update operation (user ID assignment) - CONFIRMED -> CONTINUE
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (67, 'authorize_payment_sca', 'UPDATE', 'USER_ID_ASSIGN', 'CONFIRMED', 1, 'APPROVAL_SCA', 'CONTINUE');

-- authorize_payment_sca - update operation (user ID assignment) - CANCELED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (68, 'authorize_payment_sca', 'UPDATE', 'USER_ID_ASSIGN', 'CANCELED', 1, null, 'FAILED');

-- authorize_payment_sca - update operation (user ID assignment) - AUTH_METHOD_FAILED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (69, 'authorize_payment_sca', 'UPDATE', 'USER_ID_ASSIGN', 'AUTH_METHOD_FAILED', 1, null, 'FAILED');

-- authorize_payment_sca - update operation (user ID assignment) - AUTH_FAILED -> FAILED
INSERT INTO ns_step_definition (step_definition_id, operation_name, operation_type, request_auth_method, request_auth_step_result, response_priority, response_auth_method, response_result)
VALUES (70, 'authorize_payment_sca', 'UPDATE', 'USER_ID_ASSIGN', 'AUTH_FAILED', 1, null, 'FAILED');
```
