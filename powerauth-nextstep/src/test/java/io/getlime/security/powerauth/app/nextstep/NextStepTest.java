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
package io.getlime.security.powerauth.app.nextstep;

import com.wultra.core.audit.base.database.DatabaseAudit;
import io.getlime.security.powerauth.app.nextstep.configuration.NextStepClientFactory;
import io.getlime.security.powerauth.app.nextstep.configuration.NextStepTestConfiguration;
import io.getlime.security.powerauth.lib.nextstep.client.NextStepClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Next Step tests.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
@Sql(scripts = "/db_schema.sql")
public class NextStepTest implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    protected NextStepClient nextStepClient;

    @Autowired
    protected NextStepClientFactory nextStepClientFactory;

    @Autowired
    protected NextStepTestConfiguration nextStepTestConfiguration;

    @LocalServerPort
    protected int port;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext1) throws BeansException {
        NextStepTest.applicationContext = applicationContext1;
    }

    @AfterAll
    public static void cleanup() {
        // Flush audit data to database before the test application and H2 database are terminated
        applicationContext.getBean(DatabaseAudit.class).flush();
    }

    @Test
    public void testContextLoads() {
        assertTrue(port > 1024);
    }

}
