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

package io.getlime.security.powerauth.app.tppengine.repository;

import io.getlime.security.powerauth.app.tppengine.repository.model.entity.UserConsentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface used for storing approved and removing rejected consents
 * of a user and TPP app.
 *
 * @author Petr Dvorak, petr@wultra.com
 */
@Repository
public interface UserConsentRepository extends CrudRepository<UserConsentEntity, Long> {

    @Query("SELECT uc FROM UserConsentEntity uc WHERE uc.userId = :userId AND uc.clientId = :clientId AND uc.consentId = :consentId")
    Optional<UserConsentEntity> findConsentStatus(@Param("userId") String userId, @Param("consentId") String consentId, @Param("clientId") String clientId);

    @Query("SELECT uc FROM UserConsentEntity uc WHERE uc.userId = :userId")
    List<UserConsentEntity> findAllConsentsGivenByUser(@Param("userId") String userId);

    @Query("SELECT uc FROM UserConsentEntity uc WHERE uc.userId = :userId AND uc.clientId = :clientId")
    List<UserConsentEntity> findConsentsGivenByUserToApp(@Param("userId") String userId, @Param("clientId") String clientId);

}
