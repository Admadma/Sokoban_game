package xmlhandler;

import game.xmlhandler.XmlToJava;
import game.xmlhandler.GameState;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class XmlToJavaTest {
    @Test
    void testParseXml(){
        String savedDataPath = System.getProperty("user.dir") + "/.helperFolder" + "/SavedGameState.xml";
        File saveFile = new File(savedDataPath);
        if(!saveFile.exists()) {
            assertThrows(FileNotFoundException.class, () -> {
                XmlToJava.parseXml("/SavedGameState.xml");
            });
        } else {
            try {
                assertEquals(GameState.class, XmlToJava.parseXml("/SavedGameState.xml").getClass());
            } catch (Exception e){}
        }

        try {
            assertEquals(GameState.class, XmlToJava.parseXml("DefaultGameState.xml"));
        } catch(Exception e){}

        assertThrows(IllegalArgumentException.class, () -> {
            XmlToJava.parseXml("/illegal_argument.xml");
        });
    }
}
