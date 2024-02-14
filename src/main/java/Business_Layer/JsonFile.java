package Business_Layer;


import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import static java.nio.file.Files.newInputStream;
import static javax.json.Json.createReader;

public class JsonFile {

    public static JsonFile start() {
        JsonFile jsonFile = new JsonFile();
        return jsonFile;
    }

    public String data(String test, String value) {
        File jsonInputFile = new File("C:\\java\\ProductStore_Automation\\demoblaze_store-main\\ProductStore\\src\\main\\resources\\DataTests.json");
        InputStream is;
        JsonObject empObj = null;
        JsonObject addrObj = null;
        try {
            is = newInputStream(jsonInputFile.toPath());
            JsonReader reader = createReader(is);
            empObj = reader.readObject();
            reader.close();
            addrObj = empObj.getJsonObject(test);

        } catch (Exception e) {
            System.out.println("Exception in reading JSON file: " + e);
        } finally {
            return addrObj.getString(value);
        }
    }

    public javax.json.JsonArray dataArray(String test02, String value) {
        File jsonInputFile = new File("C:\\java\\ProductStore_Automation\\demoblaze_store-main\\ProductStore\\src\\main\\resources\\DataTests.json");
        Map<JsonObject, JsonArray> myMap = new HashMap<>();
        JsonObject addrObj = null;
        try (InputStream is = new FileInputStream(jsonInputFile)){
            JsonReader reader = Json.createReader(is);
            JsonObject empObj = reader.readObject();
            addrObj = empObj.getJsonObject(test02);
            if (addrObj != null && addrObj.containsKey(value)) {
                //list.add(addrObj.getJsonArray(value));
                myMap.put(empObj.getJsonObject(test02), addrObj.getJsonArray(value));
            }
            return addrObj.getJsonArray(value);
        } catch (Exception e) {
            System.out.println("Exception in reading JSON file: " + e);
        }
        return Json.createArrayBuilder().build(); // Return an empty array if the value does not exist
    }
}
