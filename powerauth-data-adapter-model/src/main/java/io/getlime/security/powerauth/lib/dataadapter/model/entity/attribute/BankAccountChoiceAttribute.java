/*
 * Copyright 2018 Wultra s.r.o.
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
package io.getlime.security.powerauth.lib.dataadapter.model.entity.attribute;

import io.getlime.security.powerauth.lib.dataadapter.model.entity.BankAccount;

import java.util.List;

/**
 * Class representing a bank account choice form field attribute.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
public class BankAccountChoiceAttribute extends Attribute {

    private List<BankAccount> bankAccounts;
    private boolean enabled;
    private String defaultValue;

    /**
     * Default constructor.
     */
    public BankAccountChoiceAttribute() {
        this.type = Type.BANK_ACCOUNT_CHOICE;
    }

    /**
     * Constructor with all details.
     * @param id ID.
     * @param label Label.
     * @param bankAccounts List of bank accounts.
     * @param enabled Whether choice is enabled.
     * @param defaultValue Default chosen value.
     */
    public BankAccountChoiceAttribute(String id, String label, List<BankAccount> bankAccounts, boolean enabled, String defaultValue) {
        this.type = Type.BANK_ACCOUNT_CHOICE;
        this.id = id;
        this.label = label;
        this.bankAccounts = bankAccounts;
        this.enabled = enabled;
        this.defaultValue = defaultValue;
    }

    /**
     * Get bank account list.
     * @return Bank account list.
     */
    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    /**
     * Set bank account list.
     * @param bankAccounts Bank account list.
     */
    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    /**
     * Whether choice is enabled.
     * @return Whether choice is enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set whether choice is enabled.
     * @param enabled Whether choice is enabled.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get default chosen value.
     * @return Default chosen value.
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Set default chosen value.
     * @param defaultValue Default chosen value.
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
