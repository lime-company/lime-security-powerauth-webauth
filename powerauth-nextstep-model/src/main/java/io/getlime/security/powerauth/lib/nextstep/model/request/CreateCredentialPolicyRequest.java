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

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Request object used for creating a credential policy.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
@Data
public class CreateCredentialPolicyRequest {

    @NotBlank
    @Size(min = 2, max = 256)
    private String credentialPolicyName;
    @Size(min = 2, max = 256)
    private String description;
    @Positive
    private Integer usernameLengthMin;
    @Positive
    private Integer usernameLengthMax;
    @Size(min = 1, max = 256)
    private String usernameAllowedPattern;
    @Positive
    private Integer credentialLengthMin;
    @Positive
    private Integer credentialLengthMax;
    @Positive
    private Integer limitSoft;
    @Positive
    private Integer limitHard;
    private int checkHistoryCount;
    private boolean rotationEnabled;
    @Positive
    private Integer rotationDays;
    @NotBlank
    @Size(min = 2, max = 256)
    private String usernameGenAlgorithm;
    @NotNull
    private Map<String, String> usernameGenParam = new LinkedHashMap<>();
    @NotBlank
    @Size(min = 2, max = 256)
    private String credentialGenAlgorithm;
    @NotNull
    private Map<String, String> credentialGenParam = new LinkedHashMap<>();
    @NotNull
    private Map<String, String> credentialValParam = new LinkedHashMap<>();

}

