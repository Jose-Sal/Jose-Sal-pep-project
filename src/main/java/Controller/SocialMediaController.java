package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.AccountDAO;
import Model.Account;
import Service.AccountService;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService = new AccountService();

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
}