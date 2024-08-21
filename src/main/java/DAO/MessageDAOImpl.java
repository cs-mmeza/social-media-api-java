package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAOImpl implements MessageDAO{
    //3
    @Override
    public Message addMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            int affectedRows = ps.executeUpdate();
        if (affectedRows == 0) { // check for changes
            throw new SQLException("Creating message failed, no rows affected.");
        }
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            message.setMessage_id(rs.getInt(1));
        } else {
            throw new SQLException("Creating message failed");
        }
        return message;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //4
    @Override
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return messages;
    }
    
    //5
    @Override
    public Message getMessageById(int msgId){
    Connection connection = ConnectionUtil.getConnection();
    try {
        String sql = "SELECT * FROM message WHERE message_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, msgId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Message message = new Message(
                rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")
            );
            return message;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } 
    return null;
    }
    
    //6
    @Override
    public Message deleteMessageById(int msgId) {
        // fetch message to check if exists and to get details before removing it
        Message message = getMessageById(msgId);

        if (message != null) {
            Connection connection = ConnectionUtil.getConnection();
            try {
                connection = ConnectionUtil.getConnection();
                String sql = "DELETE FROM message WHERE message_id = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, msgId);
                int affectedRows = ps.executeUpdate();
                if (affectedRows == 0) {
                    return null;  // If no rows were affected, return null
                }
                return message;  // Return the fetched message details
            } catch (SQLException e) {
                e.printStackTrace();
            } 
        }
        return null; // If the message does not exist, return null
    }

    //7. update message by message_id
    @Override
    public Message updateMessageById(int msgId, Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            // Perform the update if the message exists
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, message.getMessage_text());
            ps.setInt(2, msgId);

            int checkInsert = ps.executeUpdate(); 

            if(checkInsert == 0){
                return null;
            }
            // fetch  message to get the updated data
            return getMessageById(msgId); // Reuse the existing method to fetch updated message 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //8
    @Override
    public List<Message> getAllMessagesByUserId(int userId) {
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return messages;
    }
}
