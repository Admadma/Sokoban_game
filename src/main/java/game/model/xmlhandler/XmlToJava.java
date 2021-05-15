package game.model.xmlhandler;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.InputStream;


public class XmlToJava {
    public static GameState parseXml(String loadingType) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(GameState.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        if (loadingType.equals("save")) {
            String savedDataPath = System.getProperty("user.dir") + "/helperFolder" + "/SavedGameState.xml";
            System.out.println(savedDataPath);
            File saveFile = new File(savedDataPath);
            GameState gameState = (GameState) unmarshaller.unmarshal(saveFile);
            System.out.println("marshal kesz");
            return gameState;
        } else if (loadingType.equals("default")) {
            try {
                String resource = "/" + "DefaultGameState.xml";
                InputStream is = XmlToJava.class.getResourceAsStream(resource);
                GameState gameState = (GameState) unmarshaller.unmarshal(is);
                return gameState;
            } catch (Exception e) {}
        } else {
            throw new IllegalArgumentException();
        }
        return null;
    }
}
