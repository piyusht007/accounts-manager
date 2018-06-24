package com.example.account.management.accountmanager.model;

public class Account {

    private String number;
    private String customerId;
    private Double balance;

    public Account(final String number,
                   final String customerId,
                   final Double balance) {
        this.number = number;
        this.customerId = customerId;
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(final Double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(final Object o) {
        if( this == o ) {
            return true;
        }
        if( o == null || getClass() != o.getClass() ) {
            return false;
        }

        final Account account = (Account) o;

        if( number != null ? !number.equals(account.number) : account.number != null ) {
            return false;
        }
        return customerId != null ? customerId.equals(account.customerId) : account.customerId == null;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + ( customerId != null ? customerId.hashCode() : 0 );
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("number=").append(number);
        sb.append(", customerId=").append(customerId);
        sb.append(", balance=").append(balance);
        sb.append('}');
        return sb.toString();
    }
}
