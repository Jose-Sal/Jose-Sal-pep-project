package Service;
import DAO.AccountDAO;
import Model.Account;
import Model.Message;

import java.util.List;
public class AccountService {
    private AccountDAO accountDAO;

    // create empty parameter constructor 
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    //Constructor for account service when DAO is provided
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    
    //registering a new account
    public Account registerAccount(Account account) throws Exception{
        return accountDAO.insertAccount(account);
    }
    public List<Account> AllAccount() throws Exception{
        List<Account> accountList = accountDAO.getAllAccounts();
        return accountList;
    }
    //user by id
    public Account findUserById(int id) throws Exception{
        return accountDAO.getUserId(id);
    }
    //loging in to account 
    public Account logIn(Account account)throws Exception{
        return accountDAO.userLogin(account.getUsername(), account.getPassword());
    }
}
