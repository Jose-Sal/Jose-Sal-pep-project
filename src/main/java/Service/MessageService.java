package Service;
import Model.Message;
import DAO.MessageDAO;
import java.util.List;
public class MessageService {
    MessageDAO messageDAO;
    public MessageService(){}

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    //creating a new message
    public Message creatMessage(Message message) throws Exception{
        return messageDAO.creatMessage(message);
    }
}
