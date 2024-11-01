package DAO;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;

import org.h2.engine.Database;

import Model.Account;
import Model.Message;
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
    //get user by username
    public Boolean doesUsernameExist(String username){
        //sql
        String sql = "select COUNT(*) from account where username=?";
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt(1) >0;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        
    }
    //get user by ID
    public Account getUserId(int id) throws Exception{
        Connection connection = ConnectionUtil.getConnection();
        //sql
        String sql = "select * from account where account_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Account user = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password") );
            return user;
        }
        return null;
    }
    //does user exist but instead output should be bool
    public static Boolean doesUserExist(int id) throws Exception{
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT COUNT(*) FROM account where account_id=?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                return rs.getInt(1) > 0;
                
            }
        } catch (SQLException e) {
            return false;
        }
    }

    //When posting a new Account
    //this will be considered user registration 
    // create insert for the account table
    //insert method will have a parameter with a account object
    public Account insertAccount(Account account){
        // sql statement
        String sql = "insert into account(username, password) values(?,?)";
        //check if the username is already in the datatable
        
        //create connection to the database
        //create a prepared statement in order to insert data into our data table
        if(doesUsernameExist(account.getUsername().trim()) ==true){
            return null;
        }
        try {
            Connection connection =ConnectionUtil.getConnection();PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
        } catch (Exception e) {
            // TODO: handle exception
            return null;
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
            if(Objects.equals(password, accountAcc.getPassword())){
                return accountAcc;
            }
        }
        return null;
    }
}
