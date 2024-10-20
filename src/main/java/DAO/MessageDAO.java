package DAO;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;

import com.azul.crs.client.Result;

import Model.Message;
public class MessageDAO {

    //creating a new message should be using a insert into into our Message sql table 
    public Message creatMessage(Message message) throws Exception{
        Connection connection = ConnectionUtil.getConnection();
        //write our sql statement for inserting a new message in our table
        String str = "insert into message(message_id, posted_by, message_text, time_posted_epoch)";
        PreparedStatement preparedStatement = connection.prepareStatement(str);

        //prepared statement sets
        preparedStatement.setInt(1,message.getMessage_id());
        preparedStatement.setInt(2,message.getPosted_by());
        preparedStatement.setString(3, message.getMessage_text());
        preparedStatement.setLong(4, message.getTime_posted_epoch());
        preparedStatement.executeUpdate();
        
        return message;
    }

    //retrieving all messages
    public List<Message> getAllMessages() throws Exception{
        Connection connection =  ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        //SQL statement
        String sql = "select * from message";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            messages.add(message);
        }

        return messages;
    }
}
