package game.model.xmlhandler;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class XmlToJava {
    public static GameState parseXml(String loadingType) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(GameState.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        if (loadingType.equals("/SavedGameState.xml")) {
            try {
            String savedDataPath = System.getProperty("user.dir") + "/helperFolder" + loadingType;
            File saveFile = new File(savedDataPath);
            GameState gameState = (GameState) unmarshaller.unmarshal(saveFile);
            return gameState;
            } catch (Exception e){
                throw new FileNotFoundException();
            }

        } else if (loadingType.equals("/DefaultGameState.xml") || loadingType.equals("/TestGameState.xml")) {
            try {
                InputStream is = XmlToJava.class.getResourceAsStream(loadingType);
                GameState gameState = (GameState) unmarshaller.unmarshal(is);
                return gameState;
            } catch (Exception e) {
                throw new FileNotFoundException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}
