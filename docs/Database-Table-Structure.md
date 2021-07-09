# Database Table Structure

Web Flow requires a database to store data. It is tested with Oracle, MySQL and PostgreSQL. It should be easily adapted to any other SQL database which supports JDBC.

Web Flow can coexist with PowerAuth in the same database schema, or it can use a different database schema.

## Database Scripts

### MySQL
- [create_schema.sql](./sql/mysql/create_schema.sql) - DDL script for creating the database schema
- [initial_data.sql](./sql/mysql/initial_data.sql) - script with initial data
- [drop_schema.sql](./sql/mysql/drop_schema.sql) - drop schema script

### Oracle

- [create_schema.sql](./sql/oracle/create_schema.sql) - DDL script for creating the database schema
- [initial_data.sql](./sql/oracle/initial_data.sql) - script with initial data
- [drop_schema.sql](./sql/oracle/drop_schema.sql) - drop schema script

### PostgreSQL

- [create_schema.sql](./sql/postgresql/create_schema.sql) - DDL script for creating the database schema
- [initial_data.sql](./sql/postgresql/initial_data.sql) - script with initial data
- [drop_schema.sql](./sql/postgresql/drop_schema.sql) - drop schema script

## Database Tables

### Database Tables for the OAuth 2.0 protocol

- **oauth_client_details** - the table stores details about OAuth2 client applications. Every Web Flow client application should have a record in this table. See [JdbcClientDetailsService.java](https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/main/java/org/springframework/security/oauth2/provider/client/JdbcClientDetailsService.java).

- **oauth_client_token** - the table stores OAuth2 tokens for retrieval by client applications. See [JdbcClientTokenServices.java](https://docs.spring.io/spring-security/oauth/apidocs/org/springframework/security/oauth2/client/token/JdbcClientTokenServices.html).

- **oauth_access_token** - the table stores OAuth2 access tokens. See [JdbcTokenStore.java](https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/main/java/org/springframework/security/oauth2/provider/token/store/JdbcTokenStore.java).

- **oauth_refresh_token** - the table stores OAuth2 refresh tokens. See [JdbcTokenStore.java](https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/main/java/org/springframework/security/oauth2/provider/token/store/JdbcTokenStore.java).

- **oauth_code** - the table stores data for the OAuth2 authorization code grant. See [JdbcAuthorizationCodeServices.java](https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/main/java/org/springframework/security/oauth2/provider/code/JdbcAuthorizationCodeServices.java).

### Database Tables for the Next Step Server

- **ns_auth_method** - the table stores configuration of authentication methods. Data in this table needs to be loaded before Web Flow is started.

- **ns_user_prefs** - the table stores user preferences. Status of authentication methods is stored in this table per user (methods can be enabled or disabled).

- **ns_operation** - the table stores details of Web Flow operations. Only the last status is stored in this table, changes of operations are stored in table ns_operation_history.

- **ns_operation_config** - the table stores configuration of Web Flow operations including configuration of mobile templates. Data in this table needs to be loaded before Web Flow is started.

- **ns_operation_method_config** - the table stores configuration of authentication methods per operation name.

- **ns_operation_history** - the table stores all changes of operations.

- **ns_organization** - the table stores definitions of organizations.

- **ns_step_definition** - the table stores definitions of authentication/authorization steps. Data in this table needs to be loaded before Web Flow is started.

- **ns_operation_afs** - the table stores responses from AFS for operations.

- **ns_application** - the table stores Next Step applications.

- **ns_credential_policy** - the table stores credential policies.

- **ns_otp_policy** - the table stores OTP policies.

- **ns_user_identity** - the table stores Next Step user identities.

- **ns_user_contact** - the table stores contact information for user identities.

- **ns_user_identity_history** - the table stores history for user identities.

- **ns_role** - the table stores user role definitions.

- **ns_user_role** - the table stores assignment of roles to user identities.

- **ns_user_alias** - the table stores user aliases.

- **ns_hashing_config** - the table stores configuration of hashing algorithms.

- **ns_credential_definition** - the table stores definitions of credentials with reference to credential policies and applications.

- **ns_otp_definition** - the table stores definitions of one time passwords with reference to one time password policies and applications.

- **ns_credential_storage** - the table stores credential values, attempt counters and other data related to credentials.

- **ns_credential_history** - the table stores historical values of credentials.

- **ns_otp_storage** - the table stores one time password values, attempt counters and other data related to one time passwords.

- **ns_authentication** - the table stores user authentication attempts.

### Database Tables for the Data Adapter

- **da_sms_authorization** - the table stores data for SMS OTP authorization.

- **da_user_credentials** - the table stores user credentials.

### Database Tables for the Web Flow Server

- **wf_operation_session** - the table stores mapping of operations to HTTP sessions.

- **wf_afs_config** - the table stores configuration of anti-fraud system integration.

- **wf_certificate_verification** - the table stores results of TLS certificate verifications.

### Database Tables for the Third Party Provider

- **tpp_consent** - the table stores definitions of consents.

- **tpp_user_consent** - the table stores consents given by the user.

- **tpp_user_consent_history** - the table stores changes of consents given by the user.

- **tpp_detail** - the table stores information about third parties.

- **tpp_app_detail** - the table store information about third party applications.

### Database Tables for the auditing functionality

- **audit_log** - the table stores audit records.

- **audit_param** - the table stores parameters of audit records which can be used in queries.