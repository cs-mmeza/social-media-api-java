package Service;

import java.util.List;

// import DAO.AccountDAOImpl;
import DAO.MessageDAOImpl;
import Model.Account;
import Model.Message;

public class MessageService {
    private MessageDAOImpl messageDAO;
    private AccountService accountService;

    public MessageService(){
        messageDAO = new MessageDAOImpl();
        accountService = new AccountService();
    }

    public MessageService(MessageDAOImpl messageDAO, AccountService accountService){
        this.messageDAO = messageDAO;
        this.accountService = accountService;
    }

    //3. process creation of new message if 
    //message_text is not blank,
    //is not over 255 characters,
    //posted_by refers to a real, existing use
    public Message addMessage(Message message){
        // Validate the message
        if (message.getMessage_text() == null || message.getMessage_text().isBlank() || message.getMessage_text().length() > 255) {
            throw new IllegalArgumentException("Invalid message text");
        }
        // Check if user exists
        Account user = accountService.getAccountById(message.getPosted_by());
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        return messageDAO.addMessage(message);
    }

    //4. retrieve all message
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }
    
    //5. retrieve all message by message_id
    public Message getMessageById(int msgId){
        return messageDAO.getMessageById(msgId);
    }

    //6.  detelete a message by message_id if exist
    public Message deleteMessageById(int msgId){
        return messageDAO.deleteMessageById(msgId);
    }
    //7. update message by message_id if
    // message_id already exist
    // new message_text is not blank
    // new message_text is less than 255 characters
    public Message updateMessageById(int msgId, String messageText) throws IllegalArgumentException {
        if (messageText == null || messageText.isBlank() || messageText.length() > 255) {
            throw new IllegalArgumentException("Invalid message text");
        }
        Message message = new Message();
        message.setMessage_text(messageText);
        
        Message updatedMessage = messageDAO.updateMessageById(msgId, message);
        if (updatedMessage == null) {
            throw new IllegalArgumentException("Message not found");
        }
        return updatedMessage;
    }

    //8. get all messages by account_id
    public List<Message> getAllMessagesByUserId(int userId){
        return messageDAO.getAllMessagesByUserId(userId);
    }
}
