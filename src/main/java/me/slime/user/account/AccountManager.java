package me.slime.user.account;

import me.slime.util.SQLiteUtils;

import java.util.HashMap;

/**
 * Created by Kevloe on 08.04.2020.
 */

public class AccountManager {

    private SQLiteUtils sql = new SQLiteUtils();
    private String passwordHash = "";

    private HashMap<String, String> accounts;

    public void addAcount(String username, String password) {
        this.accounts.put(username, password);
        if(!sql.isConnected()) {
            sql.connect();
        }
        sql.createTable("accounts", "username VARCHAR(100), password VARCHAR(100), premium BOOLEAN");

    }

    public void removeAccount(String username) {
        this.accounts.remove(username);
    }

    public HashMap<String, String> getAccounts() {
        return this.accounts;
    }

    public void switchAccount(String username) {

    }

}
