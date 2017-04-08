/*
 * Copyright 2017 Lime - HighTech Solutions s.r.o.
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
package io.getlime.security.powerauth.app.credentialServer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Roman Strobl
 */
public class AuthenticationRequest {

    @JsonProperty
    private String username;
    @JsonProperty
    private String password;
    @JsonProperty
    private AuthenticationMethod authMethod;
    @JsonProperty
    private RequestType requestType;


    private ObjectMapper mapper = new ObjectMapper();

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password, AuthenticationMethod authMethod, RequestType requestType) {
        this.username = username;
        this.password = password;
        this.authMethod = authMethod;
        this.requestType = requestType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AuthenticationMethod getAuthMethod() {
        return authMethod;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    private String toJson() {
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public String toString() {
        return toJson();
    }
}
