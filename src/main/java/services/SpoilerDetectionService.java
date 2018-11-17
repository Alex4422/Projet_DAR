package services;

import entities.Message;
import moviedb.Search;
import org.hibernate.SessionFactory;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class SpoilerDetectionService extends ServiceBase {
    private HashSet<String> frequentWords;
    private MessagesService messagesService;

    public SpoilerDetectionService(SessionFactory sessionFactory) {
        super(sessionFactory);
        frequentWords = loadFrequentWords();
        messagesService = new MessagesService(getSessionFactory());
    }

    private HashSet<String> loadFrequentWords() {
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get("src/main/resources/frequent_words.txt")));
            return frequentWords = new HashSet<>(Arrays.asList(fileContent.split("\\s")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println("Cannot load words list");
        System.exit(1);
        return null;
    }

    public void flagPotentialSpoilers() {
        Map<Integer, List<Message>> messagesByShow = new MessagesService(getSessionFactory())
                .getMessagesWithoutSpoilerProbability()
                .stream()
                .collect(groupingBy(Message::getShowId));
        for (Integer showId: messagesByShow.keySet()) {
            flagSpoilersForShow(showId, messagesByShow.get(showId));
        }
    }

    private void flagSpoilersForShow(Integer showId, List<Message> potentialSpoilers) {
        List<String> spoilerWords = getSpoilerWordsForShow(showId);
        for (Message msg: potentialSpoilers) {
            if (messageContrainsSpoiler(msg, spoilerWords)) {
                messagesService.flagPotentialSpoiler(msg);
            } else {
                messagesService.flagNonSpoiler(msg);
            }
        }
    }

    private boolean messageContrainsSpoiler(Message message, List<String> spoilerWords) {
        for (String s: spoilerWords) {
            if (message.getContent().matches(".*\\b" + s + "\\b.*")) {
                return true;
            }
        }
        return false;
    }

    public List<String> getSpoilerWordsForShow(Integer showId) {
        Map<String, Long> wordsFrequency =  getDescritpionsForShow(showId).stream()
                .flatMap(description -> Arrays.stream(description.split("\\s")))
                .map(String::toLowerCase)
                .collect(groupingBy(Function.identity(), counting()));
        return wordsFrequency.entrySet().stream()
                .filter(entry -> entry.getValue() >= 6 && entry.getKey().length() > 1)
                .filter(entry -> !frequentWords.contains(entry.getKey().toLowerCase()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> getDescritpionsForShow(Integer showId) {
        JSONObject showDetails = Search.showDetails(showId.toString());
        List<Integer> seasons = new ArrayList<>();
        for (Object o: showDetails.getJSONArray("seasons")) {
            seasons.add((int) o);
        }
        return seasons.parallelStream()
                .map(seasonNumber -> Search.seasonDetails(showId.toString(), seasonNumber.toString()))
                .flatMap(seasonDetails -> seasonDetails.getJSONArray("episodes").toList().stream())
                .map(o -> (String) ((HashMap) o).get("overview"))
                .map(description -> description.replaceAll(",", " ")
                                               .replaceAll("\\.", " ")
                                               .replaceAll("'", " "))
                .collect(Collectors.toList());
    }
}
