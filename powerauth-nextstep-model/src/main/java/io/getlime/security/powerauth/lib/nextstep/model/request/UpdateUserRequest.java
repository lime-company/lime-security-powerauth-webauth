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
package io.getlime.security.powerauth.lib.nextstep.model.request;

import io.getlime.security.powerauth.lib.nextstep.model.entity.enumeration.ContactType;
import io.getlime.security.powerauth.lib.nextstep.model.entity.enumeration.CredentialType;
import io.getlime.security.powerauth.lib.nextstep.model.entity.enumeration.UserIdentityStatus;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Request object used for updating a user identity.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
@Data
public class UpdateUserRequest {

    @NotBlank
    @Size(min = 1, max = 256)
    private String userId;

    private UserIdentityStatus userIdentityStatus;

    private Map<String, Object> extras;

    private List<String> roles;

    @Valid
    private List<UpdatedContact> contacts;

    @Valid
    private List<UpdatedCredential> credentials;

    /**
     * Contact to update.
     */
    @Data
    public static class UpdatedContact {

        @NotBlank
        @Size(min = 2, max = 256)
        private String contactName;
        @NotNull
        private ContactType contactType;
        @NotBlank
        @Size(min = 2, max = 256)
        private String contactValue;
        private boolean primary;

    }

    /**
     * Credential to update.
     */
    @Data
    public static class UpdatedCredential {

        @NotBlank
        @Size(min = 2, max = 256)
        private String credentialName;
        @NotNull
        private CredentialType credentialType;
        @Size(min = 1, max = 256)
        private String username;
        @Size(min = 1, max = 256)
        private String credentialValue;
        private Date timestampExpires;

    }

}
