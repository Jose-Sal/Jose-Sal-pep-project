package DAO;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;
import Model.Message;
public class MessageDAO {
    //creating a new message should be using a insert into into our Message sql table 
    public Message creatMessage(Message message) throws Exception{
        Connection connection = ConnectionUtil.getConnection();
        //write our sql statement for inserting a new message in our table
        String str = "insert into message";
        return null;
    }
}
