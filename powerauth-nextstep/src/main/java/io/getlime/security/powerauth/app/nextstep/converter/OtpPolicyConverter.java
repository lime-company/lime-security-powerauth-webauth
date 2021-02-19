/*
 * Copyright 2019 Wultra s.r.o.
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
package io.getlime.security.powerauth.app.nextstep.converter;

import io.getlime.security.powerauth.app.nextstep.repository.model.entity.OtpPolicyEntity;
import io.getlime.security.powerauth.lib.nextstep.model.entity.OtpPolicyDetail;

/**
 * Converter for OTP policies.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
public class OtpPolicyConverter {

    /**
     * Convert OTP policy entity to detail.
     * @param otpPolicy OTP policy entity.
     * @return OTP policy detail.
     */
    public OtpPolicyDetail fromEntity(OtpPolicyEntity otpPolicy) {
        OtpPolicyDetail otpPolicyDetail = new OtpPolicyDetail();
        otpPolicyDetail.setOtpPolicyName(otpPolicy.getName());
        otpPolicyDetail.setDescription(otpPolicy.getDescription());
        otpPolicyDetail.setOtpPolicyStatus(otpPolicy.getStatus());
        otpPolicyDetail.setLength(otpPolicy.getLength());
        otpPolicyDetail.setAttemptLimit(otpPolicy.getAttemptLimit());
        otpPolicyDetail.setGenAlgorithm(otpPolicy.getGenAlgorithm());
        otpPolicyDetail.setExpirationTime(otpPolicy.getExpirationTime());
        otpPolicyDetail.setTimestampCreated(otpPolicy.getTimestampCreated());
        otpPolicyDetail.setTimestampLastUpdated(otpPolicy.getTimestampLastUpdated());
        return otpPolicyDetail;
    }

}