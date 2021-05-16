package game.xmlhandler;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Class reads XML files into GameState objects.
 */
public class XmlToJava {
    /**
     * Reads a given sourceFile into a GameState object.
     *
     * @param sourceFile the file it tries to read
     * @return the GameState object created from the XML file
     * @throws Exception if an error occured while trying to read from xml
     */
    public static game.xmlhandler.GameState parseXml(String sourceFile) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(game.xmlhandler.GameState.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        if (sourceFile.equals("/SavedGameState.xml")) {
            try {
                String savedDataPath = System.getProperty("user.dir") + "/.helperFolder" + sourceFile;
                File saveFile = new File(savedDataPath);
                game.xmlhandler.GameState gameState = (game.xmlhandler.GameState) unmarshaller.unmarshal(saveFile);
                return gameState;
            } catch (Exception e){
                throw new FileNotFoundException();
            }

        } else if (sourceFile.equals("/DefaultGameState.xml") || sourceFile.equals("/TestGameState.xml")) {
            try {
                InputStream is = game.xmlhandler.XmlToJava.class.getResourceAsStream(sourceFile);
                game.xmlhandler.GameState gameState = (GameState) unmarshaller.unmarshal(is);
                return gameState;
            } catch (Exception e) {
                throw new FileNotFoundException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}

