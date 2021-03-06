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
package io.getlime.security.powerauth.app.nextstep.repository.model.entity;

import io.getlime.security.powerauth.lib.nextstep.model.entity.enumeration.AuthenticationResult;
import io.getlime.security.powerauth.lib.nextstep.model.entity.enumeration.AuthenticationType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity which stores authentication attempts.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
@Entity
@Table(name = "ns_authentication")
@Data
@EqualsAndHashCode(of = {"userId", "authenticationType", "credential", "otp", "operation", "timestampCreated"})
public class AuthenticationEntity implements Serializable {

    private static final long serialVersionUID = 1598100682966462736L;

    @Id
    @Column(name = "authentication_id", nullable = false)
    private String authenticationId;

    // User identity may not be present in Next Step, foreign key reference is optional
    @Column(name = "user_id", updatable = false)
    private String userId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthenticationType authenticationType;

    @ManyToOne
    @JoinColumn(name = "credential_id", referencedColumnName = "credential_id", updatable = false)
    private CredentialEntity credential;

    @ManyToOne
    @JoinColumn(name = "otp_id", referencedColumnName = "otp_id", updatable = false)
    private OtpEntity otp;

    @ManyToOne
    @JoinColumn(name = "operation_id", referencedColumnName = "operation_id", updatable = false)
    private OperationEntity operation;

    @Column(name = "result", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthenticationResult result;

    @Column(name = "result_credential")
    @Enumerated(EnumType.STRING)
    private AuthenticationResult resultCredential;

    @Column(name = "result_otp")
    @Enumerated(EnumType.STRING)
    private AuthenticationResult resultOtp;

    @Column(name = "timestamp_created", nullable = false)
    private Date timestampCreated;

}
