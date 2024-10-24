package DAO;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;
import Model.Account;
public class AccountDAO {

    //when retreiving accounts
    public List<Account> getAllAccounts() throws Exception{
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();

        //SQL statement
        String sql = "select * from account";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password") );
            accounts.add(account);
        }        
        return accounts;
    }

    //When posting a new Account
    //this will be considered user registration 
    // create insert for the account table
    //insert method will have a parameter with a account object
    public Account insertAccount(Account account) throws Exception{
        //create connection to the database
        Connection connection =ConnectionUtil.getConnection();
        // sql statement
        String sql = "insert into account(username, password) values(?,?)";
        //create a prepared statement in order to insert data into our data table
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        //write a prepared statment sets 
        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());
        //execute the prepared statement
        preparedStatement.executeUpdate();

        ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
        if(pkeyResultSet.next()){
            int generatedAccountID = (int) pkeyResultSet.getLong(1);
            return new Account(generatedAccountID, account.getUsername(),account.getPassword());
        }
        return null;
    }

    //for logging in the user using the username and password
    public Account userLogin(String username, String password) throws Exception{
        Connection connection = ConnectionUtil.getConnection();
        //for now we will return the account information using select all where the username matches along with the password
        String sql = "select * from account where username = ? and password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        //enter the user prepared input
        preparedStatement.setString(1,username);
        preparedStatement.setString(2, password);
        //execute 
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Account accountAcc = new Account(
            rs.getInt("account_id"),
            rs.getString("username"),
            rs.getString("password"));
            return accountAcc;
        }
        return null;
    }
}
