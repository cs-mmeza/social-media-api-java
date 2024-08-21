package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService  accountService;
    MessageService messageService;
    ObjectMapper mapper;
    
    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
        this.mapper = new ObjectMapper();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        //1 Account handlers
        app.post("/register", this::registerAccountHandler);
        //2
        app.post("/login", this::loginAccountHandler);
        
        //3 Message handlers
        app.post("/messages", this::addMessageHandler);
        //4
        app.get("/messages", this::getAllMessagesHandler);
        //5.
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        //6
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        //7
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        //8
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
    //1 Account handlers
    private void registerAccountHandler(Context context) throws JsonProcessingException{
        Account account = mapper.readValue(context.body(), Account.class);
        try {
            Account createdAccount = accountService.registerAccount(account);
            context.json(createdAccount).status(200);
        } catch (IllegalArgumentException e) {
            context.status(400).result(""); 
        }

    }
    //2
    private void loginAccountHandler(Context context) throws JsonProcessingException{
        Account account = mapper.readValue(context.body(), Account.class);
        try {
            Account createdAccount = accountService.loginAccount(account);
            context.json(createdAccount).status(200);
        } catch (IllegalArgumentException e) {
            context.status(401).result(""); 
        }
    }
    
    //3 Message handlers
    private void addMessageHandler(Context context) throws JsonProcessingException{
        Message message = mapper.readValue(context.body(), Message.class);
        try {
            Message createdMessage = messageService.addMessage(message);
            context.json(createdMessage).status(200);
        } catch (IllegalArgumentException e) {
            context.status(400).result("");
        }
    }
    //4
    private void getAllMessagesHandler(Context context){
        List<Message> messages = messageService.getAllMessages();
        context.json(messages).status(200);
        
    }
    
    //5
    private void getMessageByIdHandler(Context context){
        int msgId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(msgId);
        if (message != null) {
            context.json(message).status(200);
        } else {
            context.status(200).result("");
        }
    }
    //6
    private void deleteMessageByIdHandler(Context context){
        int msgId = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(msgId);
        if (deletedMessage != null) {
            context.json(deletedMessage).status(200);
        } else {
            context.result("").status(200);
        }
    }
    //7
    private void updateMessageByIdHandler(Context context) {
        try {
            int msgId = Integer.parseInt(context.pathParam("message_id"));
            
            // Parse only the message_text from the request body
            JsonNode jsonNode = mapper.readTree(context.body());
            String messageText = jsonNode.get("message_text").asText();
            
            Message updatedMessage = messageService.updateMessageById(msgId, messageText);
            context.json(mapper.writeValueAsString(updatedMessage));
            context.status(200);
        } catch (IllegalArgumentException e) {
            context.status(400);
            context.result(""); // Empty response body for 400 status
        } catch (Exception e) {
            context.status(400);
            context.result(""); // Empty response body for any other exception
        }
    }
    //8
    private void getAllMessagesByUserIdHandler(Context context) {
        int userId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByUserId(userId);
        context.json(messages).status(200);
    }
}