package services;

import entities.Message;
import entities.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;
import services.errors.NonExistingMessage;
import services.errors.UnAuthenticatedUserException;

import java.util.ArrayList;
import java.util.List;

public class MessagesService extends ServiceBase {
    private static Double POTENTIAL_SPOILER_THRESHOLD = 0.5;

    public MessagesService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void clear() {
        super.clearTable("Message");
    }

    public Message postMessage(String userToken, String content, String showIdStr) throws UnAuthenticatedUserException {
        User user = new UserSessionsService(getSessionFactory()).retrieveUser(userToken);
        Integer showId = Integer.parseInt(showIdStr);
        Message newMessage = new Message(user, showId, content);
        add(newMessage);
        return newMessage;
    }

    public List<JSONObject> getJSONShowMessages(String showIdStr) {
        List<JSONObject> result = new ArrayList<>();
        List<Message> messages = getShowMessages(showIdStr);
        if (messages.isEmpty()) {
            return result;
        }
        for (Message msg: messages) {
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("userName", msg.getUser().getUsername());
            jsonMessage.put("content", msg.getContent());
            jsonMessage.put("date", msg.getDate());
            jsonMessage.put("id", msg.getId());
            if (msg.getSpoilerProbability() != null) {
                jsonMessage.put("spoilerProbability", msg.getSpoilerProbability());
            }
            result.add(jsonMessage);
        }
        return result;
    }

    public List<Message> getShowMessages(String showIdStr) {
        Integer showId = Integer.parseInt(showIdStr);
        beginTransaction();
        Criteria criteria = getSession().createCriteria(Message.class);
        criteria.add(Restrictions.eq("showId", showId));
        criteria.addOrder(Order.asc("date"));
        List<Message> result = criteria.list();
        getSession().getTransaction().commit();
        return result;
    }

    public void flagPotentialSpoiler(Message message) {
        message.setSpoilerProbability(POTENTIAL_SPOILER_THRESHOLD);
        update(message);
    }

    public void flagNonSpoiler(Message message) {
        message.setSpoilerProbability(0.0);
        update(message);
    }

    public void registerUserSignaledSpoiler(String messageIdStr) throws NonExistingMessage {
        updateMessageSpoilerProbability(messageIdStr, 0.1);
    }

    public void registerUserSignaledNonSpoiler(String messageIdStr) throws NonExistingMessage {
        updateMessageSpoilerProbability(messageIdStr, -0.1);
    }

    private void updateMessageSpoilerProbability(String messageIdStr, Double offset) throws NonExistingMessage {
        Message message = getMessage(Integer.parseInt(messageIdStr));
        if (message == null) {
            throw new NonExistingMessage();
        }
        if (message.getSpoilerProbability() == -1.0) {
            return;
        }
        Double previousProbability = message.getSpoilerProbability();
        message.setSpoilerProbability(previousProbability + offset);
        update(message);
    }

    public List<Message> getMessagesWithoutSpoilerProbability() {
        beginTransaction();
        Criteria criteria = getSession().createCriteria(Message.class);
        criteria.add(Restrictions.eq("spoilerProbability", -1.0));
        List<Message> result = criteria.list();
        getSession().getTransaction().commit();
        return result;
    }

    public Message getMessage(Integer id) {
        beginTransaction();
        Criteria criteria = getSession().createCriteria(Message.class);
        criteria.add(Restrictions.eq("id", id));
        List<Message> result = criteria.list();
        getSession().getTransaction().commit();
        return first(result);
    }
}
