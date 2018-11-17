package servlet;

import launch.Main;
import org.json.JSONObject;
import services.MessagesService;
import services.ServiceBase;

import javax.servlet.annotation.WebServlet;

@WebServlet (
        name = "SpoilerVotesServlet",
        urlPatterns = {"/api/v1/auth/userVote"}
)
public class SpoilerVotes extends ServletBase {
    @Override
    public JSONObject processPost() throws Exception {
        Integer messageId = getIntegerParameter("messageId");
        boolean spoiler = getBoolParameter("spoiler");
        MessagesService messagesService = new MessagesService(Main.getFactory());
        if (spoiler) {
            messagesService.registerUserSignaledSpoiler(messageId.toString());
        } else {
            messagesService.registerUserSignaledNonSpoiler(messageId.toString());
        }
        return null;
    }
}
