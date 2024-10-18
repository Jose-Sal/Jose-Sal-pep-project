package Service;
import DAO.AccountDAO;
import Model.Account;
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
}
