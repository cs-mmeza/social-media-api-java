package DAO;

import Model.Message;
import java.util.List;

public interface MessageDAO {
    //3. process creation of new message.
    public Message addMessage(Message message);
    //4. retrieve all message
    public List<Message> getAllMessages();
    // //5. retrieve all message by message_id
    public Message getMessageById(int msgId);
    // //6.  detelete a message by message_id
    public Message deleteMessageById(int msgId);
    // //7. update message by message_id
    public Message updateMessageById(int msgId, Message message);
    // //8. get all messages by account_id
    public List<Message> getAllMessagesByUserId(int userId);
}
