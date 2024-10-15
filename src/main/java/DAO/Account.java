package DAO;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;
public class Account {
    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();

        //SQL statement
        String sql = "";
        //create a prepared statement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password") );
        }
        return Account;
    }
}
