/*
 * Copyright 2021 Wultra s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getlime.security.powerauth.app.nextstep.controller;

import io.getlime.core.rest.model.base.request.ObjectRequest;
import io.getlime.core.rest.model.base.response.ObjectResponse;
import io.getlime.security.powerauth.app.nextstep.service.*;
import io.getlime.security.powerauth.lib.nextstep.model.exception.*;
import io.getlime.security.powerauth.lib.nextstep.model.request.*;
import io.getlime.security.powerauth.lib.nextstep.model.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST controller for user identities.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserIdentityService userIdentityService;
    private final UserIdentityLookupService userIdentityLookupService;
    private final UserRoleService userRoleService;
    private final UserContactService userContactService;
    private final UserAliasService userAliasService;
    private final CredentialService credentialService;
    private final AuthenticationService authenticationService;

    /**
     * REST controller constructor.
     * @param userIdentityService User identity service.
     * @param userIdentityLookupService User identity lookup service.
     * @param userRoleService User role service.
     * @param userContactService User contact service.
     * @param userAliasService User alias service.
     * @param credentialService Credential service.
     * @param authenticationService Authentication service.
     */
    @Autowired
    public UserController(UserIdentityService userIdentityService, UserIdentityLookupService userIdentityLookupService, UserRoleService userRoleService, UserContactService userContactService, UserAliasService userAliasService, CredentialService credentialService, AuthenticationService authenticationService) {
        this.userIdentityService = userIdentityService;
        this.userIdentityLookupService = userIdentityLookupService;
        this.userRoleService = userRoleService;
        this.userContactService = userContactService;
        this.userAliasService = userAliasService;
        this.credentialService = credentialService;
        this.authenticationService = authenticationService;
    }

    /**
     * Create a user identity.
     * @param request Create user request.
     * @return Create user response.
     * @throws UserAlreadyExistsException Thrown when user already exists.
     * @throws InvalidRequestException Thrown when request is invalid.
     * @throws CredentialDefinitionNotFoundException Thrown when credential definition is not found.
     * @throws InvalidConfigurationException Thrown when configuration is invalid.
     * @throws CredentialValidationFailedException Thrown when credential validation fails.
     * @throws EncryptionException Thrown when encryption or decryption fails.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ObjectResponse<CreateUserResponse> createUser(@Valid @RequestBody ObjectRequest<CreateUserRequest> request) throws UserAlreadyExistsException, InvalidRequestException, CredentialDefinitionNotFoundException, InvalidConfigurationException, CredentialValidationFailedException, EncryptionException {
        logger.info("Received createUser request, user ID: {}", request.getRequestObject().getUserId());
        CreateUserResponse response = userIdentityService.createUserIdentity(request.getRequestObject());
        logger.info("The createUser request succeeded, user ID: {}", request.getRequestObject().getUserId());
        return new ObjectResponse<>(response);
    }

    /**
     * Update a user identity via PUT method.
     * @param request Update user request.
     * @return Update user response.
     * @throws UserNotFoundException Throw when user identity is not found.
     * @throws InvalidRequestException Thrown when request is invalid.
     * @throws CredentialDefinitionNotFoundException Thrown when credential definition is not found.
     * @throws InvalidConfigurationException Thrown when configuration is invalid.
     * @throws CredentialValidationFailedException Thrown when credential validation is failed.s
     * @throws EncryptionException Thrown when encryption or decryption fails.
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ObjectResponse<UpdateUserResponse> updateUser(@Valid @RequestBody ObjectRequest<UpdateUserRequest> request) throws UserNotFoundException, InvalidRequestException, CredentialDefinitionNotFoundException, InvalidConfigurationException, CredentialValidationFailedException, EncryptionException {
        logger.info("Received updateUser request, user ID: {}", request.getRequestObject().getUserId());
        UpdateUserResponse response = userIdentityService.updateUserIdentity(request.getRequestObject());
        logger.info("The updateUser request succeeded, user ID: {}", request.getRequestObject().getUserId());
        return new ObjectResponse<>(response);
    }

    /**
     * Update a user identity via POST method.
     * @param request Update user request.
     * @return Update user response.
     * @throws UserNotFoundException Throw when user identity is not found.
     * @throws InvalidRequestException Thrown when request is invalid.
     * @throws CredentialDefinitionNotFoundException Thrown when credential definition is not found.
     * @throws InvalidConfigurationException Thrown when configuration is invalid.
     * @throws CredentialValidationFailedException Thrown when credential validation is failed.s
     * @throws EncryptionException Thrown when encryption or decryption fails.
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ObjectResponse<UpdateUserResponse> updateUserPost(@Valid @RequestBody ObjectRequest<UpdateUserRequest> request) throws UserNotFoundException, InvalidRequestException, CredentialDefinitionNotFoundException, InvalidConfigurationException, CredentialValidationFailedException, EncryptionException {
        logger.info("Received updateUserPost request, user ID: {}", request.getRequestObject().getUserId());
        UpdateUserResponse response = userIdentityService.updateUserIdentity(request.getRequestObject());
        logger.info("The updateUserPost request succeeded, user ID: {}", request.getRequestObject().getUserId());
        return new ObjectResponse<>(response);
    }

    /**
     * Get user identity detail.
     * @param request Get user detail request.
     * @return Get user detail response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws InvalidRequestException Thrown when request is invalid.
     * @throws InvalidConfigurationException Thrown when Next Step configuration is invalid.
     * @throws EncryptionException Thrown when decryption fails.
     */
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public ObjectResponse<GetUserDetailResponse> getUserDetail(@Valid @RequestBody ObjectRequest<GetUserDetailRequest> request) throws UserNotFoundException, InvalidRequestException, InvalidConfigurationException, EncryptionException {
        logger.debug("Received getUserDetail request, user ID: {}", request.getRequestObject().getUserId());
        GetUserDetailResponse response = userIdentityService.getUserDetail(request.getRequestObject());
        logger.debug("The getUserDetail request succeeded, user ID: {}", request.getRequestObject().getUserId());
        return new ObjectResponse<>(response);
    }

    /**
     * Lookup a user identity.
     * @param request Lookup user request.
     * @return Lookup user response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws InvalidRequestException Thrown when request is invalid.
     * @throws InvalidConfigurationException Thrown when Next Step configuration is invalid.
     * @throws EncryptionException Thrown when decryption fails.
     */
    @RequestMapping(value = "lookup", method = RequestMethod.POST)
    public ObjectResponse<LookupUsersResponse> lookupUser(@Valid @RequestBody ObjectRequest<LookupUsersRequest> request) throws UserNotFoundException, InvalidRequestException, InvalidConfigurationException, EncryptionException {
        logger.info("Received lookupUsers request");
        LookupUsersResponse response = userIdentityLookupService.lookupUsers(request.getRequestObject());
        logger.info("The lookupUsers request succeeded");
        return new ObjectResponse<>(response);
    }

    /**
     * Lookup a single user identity.
     * @param request Lookup user request.
     * @return Lookup user response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws InvalidRequestException Thrown when request is invalid.
     * @throws InvalidConfigurationException Thrown when Next Step configuration is invalid.
     * @throws OperationNotFoundException Thrown when operation is not found.
     * @throws EncryptionException Thrown when decryption fails.
     */
    @RequestMapping(value = "lookup/single", method = RequestMethod.POST)
    public ObjectResponse<LookupUserResponse> lookupSingleUser(@Valid @RequestBody ObjectRequest<LookupUserRequest> request) throws UserNotFoundException, InvalidRequestException, InvalidConfigurationException, OperationNotFoundException, EncryptionException {
        logger.info("Received lookupSingleUser request, username: {}, credential name: {}", request.getRequestObject().getUsername(), request.getRequestObject().getCredentialName());
        LookupUserResponse response = userIdentityLookupService.lookupUser(request.getRequestObject());
        logger.info("The lookupSingleUser request, username: {}, credential name: {}, user ID: {}", request.getRequestObject().getUsername(), request.getRequestObject().getCredentialName(), response.getUser().getUserId());
        return new ObjectResponse<>(response);
    }

    /**
     * Update multiple user identity status via PUT method.
     * @param request Update users request.
     * @return Update users response.
     * @throws UserNotFoundException Thrown when no user identity is found.
     */
    @RequestMapping(value = "multi", method = RequestMethod.PUT)
    public ObjectResponse<UpdateUsersResponse> updateMultipleUsers(@Valid @RequestBody ObjectRequest<UpdateUsersRequest> request) throws UserNotFoundException {
        logger.info("Received updateMultipleUsers request");
        UpdateUsersResponse response = userIdentityService.updateUsers(request.getRequestObject());
        logger.info("The updateMultipleUsers request succeeded");
        return new ObjectResponse<>(response);
    }

    /**
     * Update multiple user identity status via POST method.
     * @param request Update users request.
     * @return Update users response.
     * @throws UserNotFoundException Thrown when no user identity is found.
     */
    @RequestMapping(value = "update/multi", method = RequestMethod.POST)
    public ObjectResponse<UpdateUsersResponse> updateMultipleUsersPost(@Valid @RequestBody ObjectRequest<UpdateUsersRequest> request) throws UserNotFoundException {
        logger.info("Received updateMultipleUsersPost request");
        UpdateUsersResponse response = userIdentityService.updateUsers(request.getRequestObject());
        logger.info("The updateMultipleUsersPost request succeeded");
        return new ObjectResponse<>(response);
    }

    /**
     * Assign a role to a user identity.
     * @param request Add user role request.
     * @return Add user role response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws InvalidRequestException Thrown when request is not found.
     * @throws UserRoleAlreadyAssignedException Thrown when user role is already assigned.
     */
    @RequestMapping(value = "role", method = RequestMethod.POST)
    public ObjectResponse<AddUserRoleResponse> addRole(@Valid @RequestBody ObjectRequest<AddUserRoleRequest> request) throws UserNotFoundException, InvalidRequestException, UserRoleAlreadyAssignedException {
        logger.info("Received addRole request, user ID: {}, role name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getRoleName());
        AddUserRoleResponse response = userRoleService.addUserRole(request.getRequestObject());
        logger.info("The addRole request succeeded, user ID: {}, role name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getRoleName());
        return new ObjectResponse<>(response);
    }

    /**
     * Remove a user role from a user identity.
     * @param request Remove user role request.
     * @return Remove user role response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws UserRoleNotAssignedException Thrown when user role is not assigned.
     * @throws InvalidRequestException Thrown when request is invalid.
     */
    @RequestMapping(value = "role/remove", method = RequestMethod.POST)
    public ObjectResponse<RemoveUserRoleResponse> removeRole(@Valid @RequestBody ObjectRequest<RemoveUserRoleRequest> request) throws UserNotFoundException, UserRoleNotAssignedException, InvalidRequestException {
        logger.info("Received removeRole request, user ID: {}, role name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getRoleName());
        RemoveUserRoleResponse response = userRoleService.removeUserRole(request.getRequestObject());
        logger.info("The removeRole request succeeded, user ID: {}, role name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getRoleName());
        return new ObjectResponse<>(response);
    }

    /**
     * Create a user contact.
     * @param request Create user contact request.
     * @return Create user contact response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws UserContactAlreadyExistsException Thrown when user contact already exists.
     */
    @RequestMapping(value = "contact", method = RequestMethod.POST)
    public ObjectResponse<CreateUserContactResponse> createUserContact(@Valid @RequestBody ObjectRequest<CreateUserContactRequest> request) throws UserNotFoundException, UserContactAlreadyExistsException {
        logger.info("Received createUserContact request, user ID: {}, contact name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getContactName());
        CreateUserContactResponse response = userContactService.createUserContact(request.getRequestObject());
        logger.info("The createUserContact request succeeded, user ID: {}, contact name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getContactName());
        return new ObjectResponse<>(response);
    }

    /**
     * Get list of contacts for a user identity.
     * @param request Get user contact list request.
     * @return Get user contact list response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     */
    @RequestMapping(value = "contact/list", method = RequestMethod.POST)
    public ObjectResponse<GetUserContactListResponse> getUserContactList(@Valid @RequestBody ObjectRequest<GetUserContactListRequest> request) throws UserNotFoundException {
        logger.info("Received getUserContactList request, user ID: {}", request.getRequestObject().getUserId());
        GetUserContactListResponse response = userContactService.getUserContactList(request.getRequestObject());
        logger.info("The getUserContactList request succeeded, user ID: {}, contact list size: {}", request.getRequestObject().getUserId(), response.getContacts().size());
        return new ObjectResponse<>(response);
    }

    /**
     * Update a user contact via PUT method.
     * @param request Update user contact request.
     * @return Update user contact response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws UserContactNotFoundException Thrown when user contact is not found.
     */
    @RequestMapping(value = "contact", method = RequestMethod.PUT)
    public ObjectResponse<UpdateUserContactResponse> updateUserContact(@Valid @RequestBody ObjectRequest<UpdateUserContactRequest> request) throws UserNotFoundException, UserContactNotFoundException {
        logger.info("Received updateUserContact request, user ID: {}, contact name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getContactName());
        UpdateUserContactResponse response = userContactService.updateUserContact(request.getRequestObject());
        logger.info("The updateUserContact request succeeded, user ID: {}, contact name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getContactName());
        return new ObjectResponse<>(response);
    }

    /**
     * Update a user contact via POST method.
     * @param request Update user contact request.
     * @return Update user contact response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws UserContactNotFoundException Thrown when user contact is not found.
     */
    @RequestMapping(value = "contact/update", method = RequestMethod.POST)
    public ObjectResponse<UpdateUserContactResponse> updateUserContactPost(@Valid @RequestBody ObjectRequest<UpdateUserContactRequest> request) throws UserNotFoundException, UserContactNotFoundException {
        logger.info("Received updateUserContactPost request, user ID: {}, contact name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getContactName());
        UpdateUserContactResponse response = userContactService.updateUserContact(request.getRequestObject());
        logger.info("The updateUserContactPost request succeeded, user ID: {}, contact name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getContactName());
        return new ObjectResponse<>(response);
    }

    /**
     * Delete a user contact.
     * @param request Delete user contact request.
     * @return Delete user contact response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws UserContactNotFoundException Thrown when user contact is not found.
     */
    @RequestMapping(value = "contact/delete", method = RequestMethod.POST)
    public ObjectResponse<DeleteUserContactResponse> deleteUserContact(@Valid @RequestBody ObjectRequest<DeleteUserContactRequest> request) throws UserNotFoundException, UserContactNotFoundException {
        logger.info("Received deleteUserContact request, user ID: {}, contact name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getContactName());
        DeleteUserContactResponse response = userContactService.deleteUserContact(request.getRequestObject());
        logger.info("The deleteUserContact request succeeded, user ID: {}, contact name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getContactName());
        return new ObjectResponse<>(response);
    }

    /**
     * Create a user alias.
     * @param request Create user alias request.
     * @return Create user alias response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws UserAliasAlreadyExistsException Thrown when user alias already exists.
     * @throws InvalidRequestException Thrown when request is invalid.
     */
    @RequestMapping(value = "alias", method = RequestMethod.POST)
    public ObjectResponse<CreateUserAliasResponse> createUserAlias(@Valid @RequestBody ObjectRequest<CreateUserAliasRequest> request) throws UserNotFoundException, UserAliasAlreadyExistsException, InvalidRequestException {
        logger.info("Received createUserAlias request, user ID: {}, alias name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getAliasName());
        CreateUserAliasResponse response = userAliasService.createUserAlias(request.getRequestObject());
        logger.info("The createUserAlias request succeeded, user ID: {}, alias name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getAliasName());
        return new ObjectResponse<>(response);
    }

    /**
     * Get alias list for a user identity.
     * @param request Get user alias list request.
     * @return Get user alias list response.
     * @throws InvalidRequestException Thrown when request is invalid.
     * @throws UserNotFoundException Thrown when user identity is not found.
     */
    @RequestMapping(value = "alias/list", method = RequestMethod.POST)
    public ObjectResponse<GetUserAliasListResponse> getUserAliasList(@Valid @RequestBody ObjectRequest<GetUserAliasListRequest> request) throws InvalidRequestException, UserNotFoundException {
        logger.info("Received getUserAliasList request, user ID: {}", request.getRequestObject().getUserId());
        GetUserAliasListResponse response = userAliasService.getUserAliasList(request.getRequestObject());
        logger.info("The getUserAliasList request succeeded, user ID: {}, alias list size: {}", request.getRequestObject().getUserId(), response.getAliases().size());
        return new ObjectResponse<>(response);
    }

    /**
     * Update a user alias via PUT method.
     * @param request Update user alias request.
     * @return Update user alias response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws UserAliasNotFoundException Thrown when user alias is not found.
     * @throws InvalidRequestException Thrown when request is invalid.
     */
    @RequestMapping(value = "alias", method = RequestMethod.PUT)
    public ObjectResponse<UpdateUserAliasResponse> updateUserAlias(@Valid @RequestBody ObjectRequest<UpdateUserAliasRequest> request) throws UserNotFoundException, UserAliasNotFoundException, InvalidRequestException {
        logger.info("Received updateUserAlias request, user ID: {}, alias name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getAliasName());
        UpdateUserAliasResponse response = userAliasService.updateUserAlias(request.getRequestObject());
        logger.info("The updateUserAlias request succeeded, user ID: {}, alias name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getAliasName());
        return new ObjectResponse<>(response);
    }

    /**
     * Update a user alias via POST method.
     * @param request Update user alias request.
     * @return Update user alias response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws UserAliasNotFoundException Thrown when user alias is not found.
     * @throws InvalidRequestException Thrown when request is invalid.
     */
    @RequestMapping(value = "alias/update", method = RequestMethod.POST)
    public ObjectResponse<UpdateUserAliasResponse> updateUserAliasPost(@Valid @RequestBody ObjectRequest<UpdateUserAliasRequest> request) throws UserNotFoundException, UserAliasNotFoundException, InvalidRequestException {
        logger.info("Received updateUserAliasPost request, user ID: {}, alias name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getAliasName());
        UpdateUserAliasResponse response = userAliasService.updateUserAlias(request.getRequestObject());
        logger.info("The updateUserAliasPost request succeeded, user ID: {}, alias name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getAliasName());
        return new ObjectResponse<>(response);
    }

    /**
     * Delete a user alias.
     * @param request Delete user alias request.
     * @return Delete user alias response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws UserAliasNotFoundException Thrown when user alias isnot found.
     */
    @RequestMapping(value = "alias/delete", method = RequestMethod.POST)
    public ObjectResponse<DeleteUserAliasResponse> deleteUserAlias(@Valid @RequestBody ObjectRequest<DeleteUserAliasRequest> request) throws UserNotFoundException, UserAliasNotFoundException {
        logger.info("Received deleteUserAlias request, user ID: {}, alias name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getAliasName());
        DeleteUserAliasResponse response = userAliasService.deleteUserAlias(request.getRequestObject());
        logger.info("The deleteUserAlias request succeeded, user ID: {}, alias name: {}", request.getRequestObject().getUserId(), request.getRequestObject().getAliasName());
        return new ObjectResponse<>(response);
    }

    /**
     * Get credential list for a user identity.
     * @param request Get user credential list request.
     * @return Get user credential list response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws InvalidConfigurationException Thrown when Next Step configuration is invalid.
     * @throws EncryptionException Thrown when decryption fails.
     */
    @RequestMapping(value = "credential/list", method = RequestMethod.POST)
    public ObjectResponse<GetUserCredentialListResponse> getUserCredentialList(@Valid @RequestBody ObjectRequest<GetUserCredentialListRequest> request) throws UserNotFoundException, InvalidConfigurationException, EncryptionException {
        logger.info("Received getUserCredentialList request, user ID: {}", request.getRequestObject().getUserId());
        GetUserCredentialListResponse response = credentialService.getCredentialList(request.getRequestObject());
        logger.info("The getUserCredentialList request succeeded, user ID: {}, credential list size: {}", request.getRequestObject().getUserId(), response.getCredentials().size());
        return new ObjectResponse<>(response);
    }

    /**
     * Get authentication list for a user identity.
     * @param request Get user authentication list request.
     * @return Get user authentication list response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     */
    @RequestMapping(value = "authentication/list", method = RequestMethod.POST)
    public ObjectResponse<GetUserAuthenticationListResponse> getUserAuthenticationList(@Valid @RequestBody ObjectRequest<GetUserAuthenticationListRequest> request) throws UserNotFoundException {
        logger.info("Received getUserAuthenticationList request, user ID: {}", request.getRequestObject().getUserId());
        GetUserAuthenticationListResponse response = authenticationService.getUserAuthenticationList(request.getRequestObject());
        logger.info("The getUserAuthenticationList request succeeded, user ID: {}, authentication list size: {}", request.getRequestObject().getUserId(), response.getAuthentications().size());
        return new ObjectResponse<>(response);
    }

    /**
     * Delete a user identity.
     * @param request Delete user identity request.
     * @return Delete user identity response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ObjectResponse<DeleteUserResponse> deleteUser(@Valid @RequestBody ObjectRequest<DeleteUserRequest> request) throws UserNotFoundException {
        logger.info("Received deleteUser request, user ID: {}", request.getRequestObject().getUserId());
        DeleteUserResponse response = userIdentityService.deleteUser(request.getRequestObject());
        logger.info("The deleteUser request succeeded, user ID: {}", request.getRequestObject().getUserId());
        return new ObjectResponse<>(response);
    }

    /**
     * Block a user identity.
     * @param request Block user identity request.
     * @return Block user identity response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws UserNotActiveException Thrown when user identity is not active.
     */
    @RequestMapping(value = "block", method = RequestMethod.POST)
    public ObjectResponse<BlockUserResponse> blockUser(@Valid @RequestBody ObjectRequest<BlockUserRequest> request) throws UserNotFoundException, UserNotActiveException {
        logger.info("Received blockUser request, user ID: {}", request.getRequestObject().getUserId());
        BlockUserResponse response = userIdentityService.blockUser(request.getRequestObject());
        logger.info("The blockUser request succeeded, user ID: {}", request.getRequestObject().getUserId());
        return new ObjectResponse<>(response);
    }

    /**
     * Unblock a user identity.
     * @param request Unblock user identity request.
     * @return Unblock user identity response.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws UserNotBlockedException Thrown when user identity is not blocked.
     */
    @RequestMapping(value = "unblock", method = RequestMethod.POST)
    public ObjectResponse<UnblockUserResponse> unblockUser(@Valid @RequestBody ObjectRequest<UnblockUserRequest> request) throws UserNotFoundException, UserNotBlockedException {
        logger.info("Received unblockUser request, user ID: {}", request.getRequestObject().getUserId());
        UnblockUserResponse response = userIdentityService.unblockUser(request.getRequestObject());
        logger.info("The unblockUser request succeeded, user ID: {}", request.getRequestObject().getUserId());
        return new ObjectResponse<>(response);
    }

}
