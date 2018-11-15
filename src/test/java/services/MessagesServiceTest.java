package services;

import entities.Message;
import entities.UserSession;
import org.junit.Test;
import services.errors.NonExistingMessage;
import services.errors.NonExistingUserException;
import services.errors.UnAuthenticatedUserException;
import services.errors.UserExistsException;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class MessagesServiceTest extends TestWithDb {
    @Test
    public void sendMessage() throws UserExistsException, NonExistingUserException, UnAuthenticatedUserException {
        UserSession userSession = registerAndLoginUser();
        MessagesService messagesService = new MessagesService(getSessionFactory());
        Message m = messagesService.postMessage(userSession.getUuid(), "Hello World", "1100");
        Message savedMessage = messagesService.getMessage(m.getId());

        assertEquals(userSession.getUser(), savedMessage.getUser());
        assertEquals("Hello World", savedMessage.getContent());
        assertEquals(1100, (int) savedMessage.getShowId());
    }

    @Test
    public void showMessages() throws UserExistsException, NonExistingUserException, UnAuthenticatedUserException {
        UserSession userSession = registerAndLoginUser();
        MessagesService messagesService = new MessagesService(getSessionFactory());
        Message m1 = messagesService.postMessage(userSession.getUuid(), "Hello World", "1100");
        Message m2 = messagesService.postMessage(userSession.getUuid(), "A rose by any other name", "1100");
        List<Message> showMessages = messagesService.getShowMessages("1100");

        assertEquals(m1, showMessages.get(0));
        assertEquals(m2, showMessages.get(1));
    }

    @Test
    public void flagPotentialSpoiler() throws UserExistsException, NonExistingUserException, UnAuthenticatedUserException {
        UserSession userSession = registerAndLoginUser();
        MessagesService messagesService = new MessagesService(getSessionFactory());
        Message m1 = messagesService.postMessage(userSession.getUuid(), "Hello World", "1100");
        messagesService.flagPotentialSpoiler(m1);

        assertEquals(0.5, messagesService.getMessage(m1.getId()).getSpoilerProbability());
    }

    @Test
    public void flagNonSpoiler() throws UserExistsException, NonExistingUserException, UnAuthenticatedUserException {
        UserSession userSession = registerAndLoginUser();
        MessagesService messagesService = new MessagesService(getSessionFactory());
        Message m1 = messagesService.postMessage(userSession.getUuid(), "Hello World", "1100");
        messagesService.flagNonSpoiler(m1);

        assertEquals(0.0, messagesService.getMessage(m1.getId()).getSpoilerProbability());
    }

    @Test
    public void registerUserSignaledSpoiler() throws UserExistsException, NonExistingUserException, UnAuthenticatedUserException, NonExistingMessage {
        UserSession userSession = registerAndLoginUser();
        MessagesService messagesService = new MessagesService(getSessionFactory());
        Message m1 = messagesService.postMessage(userSession.getUuid(), "Hello World", "1100");
        messagesService.flagNonSpoiler(m1);
        messagesService.registerUserSignaledSpoiler(m1.getId().toString());

        assertEquals(0.1, messagesService.getMessage(m1.getId()).getSpoilerProbability());
    }

    @Test
    public void registerUserSignaledNonSpoiler() throws UserExistsException, NonExistingUserException, UnAuthenticatedUserException, NonExistingMessage {
        UserSession userSession = registerAndLoginUser();
        MessagesService messagesService = new MessagesService(getSessionFactory());
        Message m1 = messagesService.postMessage(userSession.getUuid(), "Hello World", "1100");
        messagesService.flagPotentialSpoiler(m1);
        messagesService.registerUserSignaledNonSpoiler(m1.getId().toString());

        assertEquals(0.4, messagesService.getMessage(m1.getId()).getSpoilerProbability());
    }

    @Test
    public void getMessagesWithoutSpoilerProbability() throws UserExistsException, NonExistingUserException, UnAuthenticatedUserException {
        UserSession userSession = registerAndLoginUser();
        MessagesService messagesService = new MessagesService(getSessionFactory());
        Message m1 = messagesService.postMessage(userSession.getUuid(), "Hello World", "1100");
        Message m2 = messagesService.postMessage(userSession.getUuid(), "A rose by any other name", "1200");
        messagesService.flagPotentialSpoiler(m1);
        List<Message> withoutSpoilerProb = messagesService.getMessagesWithoutSpoilerProbability();

        assertEquals(1, withoutSpoilerProb.size());
        assertTrue(withoutSpoilerProb.contains(m2));
    }
}
