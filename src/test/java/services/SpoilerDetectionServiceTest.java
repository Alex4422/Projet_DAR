package services;

import launch.Main;
import org.junit.Test;

import java.util.List;

public class SpoilerDetectionServiceTest extends TestWithDb {
    @Test
    public void spoilerWordsForShow() {
        SpoilerDetectionService spoilerDetectionService = new SpoilerDetectionService(Main.getFactory());
        List<String> words = spoilerDetectionService.getSpoilerWordsForShow(1408);
        System.out.println(words);
    }
}
