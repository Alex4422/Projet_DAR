package services;

import entities.Message;
import entities.UserSession;
import org.junit.Test;
import services.errors.NonExistingUserException;
import services.errors.UnAuthenticatedUserException;
import services.errors.UserExistsException;

import static junit.framework.TestCase.assertEquals;

public class SpoilerDetectionServiceTest extends TestWithDb {
    @Test
    public void spoilerWordsForShow() throws UserExistsException, NonExistingUserException, UnAuthenticatedUserException {
        UserSession userSession = registerAndLoginUser();
        MessagesService messagesService = new MessagesService(getSessionFactory());

        Integer spoilerid = messagesService.postMessage(userSession.getUuid(),
                                              "Ted gets married in the end", "1100").getId();
        Integer nonSpoilerId = messagesService.postMessage(userSession.getUuid(),
                                                "I cant wait for the new season to be released !", "1100").getId();

        SpoilerDetectionService spoilerDetectionService = new SpoilerDetectionService(getSessionFactory());
        spoilerDetectionService.flagPotentialSpoilers();

        Message spoiler = messagesService.getMessage(spoilerid);
        Message nonSpoiler = messagesService.getMessage(nonSpoilerId);

        assertEquals(0.5, spoiler.getSpoilerProbability());
        assertEquals(0.0, nonSpoiler.getSpoilerProbability());
    }
}
