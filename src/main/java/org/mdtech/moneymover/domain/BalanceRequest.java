package org.mdtech.moneymover.domain;

import org.mdtech.moneymover.error.Error;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BalanceRequest {

    private String account;
    private List<Error> errors;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    private BigDecimal balance;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<Error> getErrors() {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        return errors;
    }
}
