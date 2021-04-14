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

import io.getlime.security.powerauth.lib.nextstep.model.entity.enumeration.CredentialAuthenticationMode;
import io.getlime.security.powerauth.lib.nextstep.model.enumeration.AuthMethod;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Request object used for authenticating using a credential.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
@Data
public class CredentialAuthenticationRequest {

    @NotBlank
    @Size(min = 2, max = 256)
    private String credentialName;
    @NotBlank
    @Size(min = 1, max = 256)
    private String userId;
    @NotBlank
    @Size(min = 1, max = 256)
    private String credentialValue;
    // Null value is allowed, defaults to MATCH_EXACT
    private CredentialAuthenticationMode authenticationMode;
    private List<Integer> credentialPositionsToVerify;
    @Size(min = 1, max = 256)
    private String operationId;
    private boolean updateOperation;
    // Authentication method is required only in case multiple methods are defined in Next Steps
    private AuthMethod authMethod;

}
