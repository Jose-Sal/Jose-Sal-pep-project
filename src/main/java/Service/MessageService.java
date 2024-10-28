package Service;
import Model.Message;
import DAO.MessageDAO;
import java.util.List;
public class MessageService {
    MessageDAO messageDAO;
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    //creating a new message
    public Message creatMessage(Message message) throws Exception{
        return messageDAO.creatMessage(message);
    }
    //get all messages
    public List<Message> getAllMessages() throws Exception{
        return messageDAO.getAllMessages();
    }
    //get message by id
    public Message findMessageById(int id) throws Exception{
        return messageDAO.getMessageByID(id);
    }
    //delete message by id
    public void deleteMessage(int id)throws Exception{
        messageDAO.deleteMessage(id);
    }
    //update message by ID
    public Message updateMessage(String message, int id)throws Exception{
        return messageDAO.updateMessage(message, id);
    }
}
