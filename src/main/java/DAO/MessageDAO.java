package DAO;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;

// import com.azul.crs.client.Result;

import Model.Message;
public class MessageDAO {

    //creating a new message should be using a insert into into our Message sql table 
    public Message creatMessage(Message message) throws Exception{
        Connection connection = ConnectionUtil.getConnection();
        //write our sql statement for inserting a new message in our table
        String str = "insert into message(posted_by, message_text, time_posted_epoch) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(str, Statement.RETURN_GENERATED_KEYS);

        //prepared statement sets
        // preparedStatement.setInt(1,message.getMessage_id());
        preparedStatement.setInt(1,message.getPosted_by());
        preparedStatement.setString(2, message.getMessage_text());
        preparedStatement.setLong(3, message.getTime_posted_epoch());
        preparedStatement.executeUpdate();
        ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
        if(pkeyResultSet.next()){
            int generatedMessageID = (int) pkeyResultSet.getLong(1);
            return new Message(generatedMessageID, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
        }
        return null;
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

    //find message by its ID
    public Message getMessageByID(int id) throws Exception{
        Connection connection = ConnectionUtil.getConnection();
        //sql
        String sql = "select * from message where message_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            return message;
        }
        return null;
    }
    
    public void deleteMessage(int id)throws Exception{
        Connection connection = ConnectionUtil.getConnection();
        //sql
        String sql = "DELETE from message where message_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        // ResultSet rs = preparedStatement.executeQuery();
        // while(rs.next()){
        //     Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
        //     return message;
        // }
        // return null;
    }
}
