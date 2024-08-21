package DAO;

import Model.Account;



public interface AccountDAO {
    //1. add user registration 
    public Account insertAccount(Account account);

    public Account getAccountByUsername(String username);
    
    public Account getAccountById(int id);
}