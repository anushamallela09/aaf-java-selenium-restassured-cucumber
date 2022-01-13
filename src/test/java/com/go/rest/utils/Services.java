package com.go.rest.utils;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import org.json.simple.parser.JSONParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Services {
    public Logger log = LogManager.getLogger(this.getClass());

    public String getValueFromJsonString(String response, String jsonPath) {
        try {
            JSONParser parser = new JSONParser();
            Object jsonObject = parser.parse(response);
            String result = JsonPath.read(jsonObject, jsonPath).toString();
            return result;
        } catch (Exception var1) {
            log.info("Exception - " + var1);
            throw new RuntimeException("error in getValueFromJsonString");
        }
    }
    public HashMap<String, String> convertDataTableToHashMap(DataTable dt){
        HashMap<String, String> val = new HashMap<String, String>();
        try{
            List<List<String>> list  = dt.asLists(String.class);
            List<Map<String, String>> map  = dt.asMaps(String.class, String.class);
            if(list.get(0).size() != 2){
                throw new RuntimeException("Failed data load");
            }
            for(int i=0; i<list.size();i++){
                val.put(list.get(i).get(0), list.get(i).get(1));
            }
        } catch (Exception var2) {
            log.info("Exception - " + var2);
            throw new RuntimeException("error in getValueFromJsonString");
        }
        return val;
    }
    public String createPayload(String requestFileName, Map<String, String> testData) {

        String val = "";
        try {
            //Path file = Path.of("src/test/resources/templates/" + requestFileName);
            //val = Files.readString(file);
            String sourcePath = System.getProperty("user.dir") + "\\src\\test\\resources\\templates\\" + requestFileName + ".txt";
            FileReader reader = new FileReader(sourcePath);
            BufferedReader bReader = new BufferedReader(reader);
            String newLine = System.lineSeparator();
            String temp = "";
            while ((temp = bReader.readLine()) != null) {
                val = val + temp + newLine;
            }

            Set<String> keyList = testData.keySet();
            String data = "empty";
            Iterator var6 = keyList.iterator();

            while(var6.hasNext()) {
                String key = (String) var6.next();
                data = (String) testData.get(key);
                val = val.replace("${" + key + "}", data);
            }
        } catch (Exception var29) {
            log.info("exception - " + var29);
            throw new RuntimeException("Issue in reading and replacing values in Request Payload");
        }
        return val;
    }
}
