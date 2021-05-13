package game.model.xmlhandler;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileInputStream;

public class XmlToJava {
    public static void parseXml() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(GameState.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        String resourceName = "gameOut.xml";
        ClassLoader classLoader = XmlToJava.class.getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        String absoulePath = file.getAbsolutePath();
        System.out.println(absoulePath);
        GameState gameState = (GameState) unmarshaller.unmarshal(file);

        //GameState gameState = (GameState) unmarshaller.unmarshal(new FileInputStream("gameOut.xml"));


        System.out.println(gameState.getNumberOfMoves());
        System.out.println(gameState.getWallXValues()[0] + "" + gameState.getWallYValues()[0]);
    }
}
