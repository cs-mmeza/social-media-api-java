package Service;

import Model.Account;
import DAO.AccountDAOImpl;

public class AccountService {
    private AccountDAOImpl accountDAO;

    public AccountService(){
        accountDAO = new AccountDAOImpl();
    }

    public AccountService(AccountDAOImpl accountDAO){
        this.accountDAO = accountDAO;
    }
    public Account loginAccount(Account account) {
        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())){
            return existingAccount;
        } 
        else if (account == null || account.getUsername() == null || account.getPassword() == null){
            throw new IllegalArgumentException("Invalid username or password");
        } else {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }
    //1. The registration will be successful if:
    //  * The username is not blank, 
    //  * The password is at least 4 characters long,
    //  * an Account with that username does not already exist.
    public Account registerAccount(Account account){
        if (account.getUsername() == null || account.getUsername().isBlank()){
            throw new IllegalArgumentException("Invalid username: The username cannot be blank");
        }
        else if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Invalid Password: Password must be at least 4 characters long");
        }
        else if (accountDAO.getAccountByUsername(account.getUsername()) != null) {
            throw new IllegalArgumentException("An account with that username already exists");
        } else {
            return accountDAO.insertAccount(account);
        }
    }
    public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }
}
