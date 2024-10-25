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

    public List<Message> getAllMessages() throws Exception{
        return messageDAO.getAllMessages();
    }
}
