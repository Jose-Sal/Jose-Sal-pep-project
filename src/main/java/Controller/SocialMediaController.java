package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.AccountDAO;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService = new AccountService();
    MessageService messageService = new MessageService();

    public SocialMediaController(){}
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        //create POST endpoint for creating a new account
        app.post("/register", this::registerHandler);
        //create POST endpoint for UserLogin
        app.post("/login", this::userLogin);
        //endpoint post for messages
        app.post("/messages", this::createNewMessage);
        //GetEndpoints
        app.get("/messages", this::getAllMessages);
        //Get from message iD
        app.get("/messages/{message_id}", this::GetMessageById);
        //delete message from id
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getAllFromUser);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    //for registering account
    private void registerHandler(Context ctx) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        //convert json object of the post request into Account object
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addAccount = accountService.registerAccount(account);
        // ctx.result("this is the endpoint!");
        //conditions for http response 
        if(addAccount.getUsername()==null || addAccount.getUsername().trim().isEmpty()){
            ctx.status(400);
            
        } 
        else if (addAccount.getPassword() == null ||addAccount.getPassword().length() <= 4) {
            ctx.status(400);
        }
        // else if(accountService..AllAccount().contains(addAccount)){
        //     ctx.status(400);
        // }
        else{
            ctx.json(addAccount);
        }
        
    }
    //userlogin
    private void userLogin(Context ctx) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        //convert json object of the post request into Account object
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.logIn(account);
        ctx.json(loginAccount);
    }

    //creating a new message
    private void createNewMessage(Context ctx) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createMessage = messageService.creatMessage(message);
        ctx.json(createMessage);
    }

    private void getAllMessages(Context ctx) throws Exception{
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void GetMessageById(Context ctx)throws Exception{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        // ObjectMapper mapper = new ObjectMapper();
        // Message message = mapper.readValue(ctx.body(), Message.class);
        Message getmessage = messageService.findMessageById(id);
        if(getmessage != null){
        ctx.json(getmessage);
        }
        else{ctx.status(200);}
    }
    //delete message with id
    private void deleteMessage(Context ctx) throws Exception{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message messageToDelete  = messageService.findMessageById(id);
        if(messageToDelete != null){
            messageService.deleteMessage(id);
            ctx.json(messageToDelete);
        }else{
            ctx.status(200);
        }
    }
    //update message with id
    private void updateMessage(Context ctx)throws Exception{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message isMessageFound = messageService.findMessageById(id);
        if(message.getMessage_text().isEmpty() || isMessageFound == null || message.getMessage_text().length() > 255){
            ctx.status(400);
        }
        else{
            Message updateMessage = messageService.updateMessage(message.message_text, id);
            ctx.json(updateMessage);
        }
    }

    //get all messages from user ID
    private void getAllFromUser(Context ctx)throws Exception{
        int userID = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getallFromUserID(userID);
        ctx.json(messages);
    }
}