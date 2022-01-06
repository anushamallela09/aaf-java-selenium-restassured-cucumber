package com.go.rest.utils;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.parser.JSONParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.util.*;

public class Services {
    public Logger log = LogManager.getLogger(this.getClass());

    public String getResponseFromGetMethod(String endpoint) {

        try {
            String baseURL = Config.properties.getProperty("GorestBaseURL");
            RestAssured.baseURI= baseURL;
            RequestSpecification specification = RestAssured.given();
            String header = Config.properties.getProperty("GetUserHeaders");
            String[] headerValues = header.split(";");
                for(int i = 0; i < headerValues.length; ++i) {
                    String[] values = headerValues[i].split(":");
                    specification.header(values[0],values[1]);
                }

            Response response = specification.get(endpoint);
            int statusCode = response.getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Failed : HTTP error code :" +  statusCode);
            }
           String responseString = response.body().asString();
            return responseString;
        }catch(Exception var1) {
            log.info("response failed " + var1 + System.lineSeparator());
            throw new RuntimeException("error in getResponseFromGetMethodRestAssured");
        }
    }
    public String postRequest(String jsonRequest, String endPoint) {

        try {
            String baseURL = Config.properties.getProperty("GorestBaseURL");


            RequestSpecification specification = new RequestSpecBuilder().setRelaxedHTTPSValidation().build();

            String header = Config.properties.getProperty("CreateUserHeaders");
                String[] headerValues = header.split(";");
                for(int i = 0; i < headerValues.length; ++i) {
                    String[] values = headerValues[i].split(":");
                    specification.header(values[0],values[1]);
                }
            }
            int statusCode = given().spec(specification).body(jsonRequest).when().post(url+endPoint).getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Failed : HTTP error code :" +  statusCode);
            }
            String responseString = given().spec(specification).body(jsonRequest).when().post(url).asString();

            return responseString;
        }catch(Exception var1) {
            log.info("response failed " + var1.getMessage() + System.lineSeparator());
        }
    }
    public String getValueFromJsonString(String response, String jsonPath) {
        try {
            JSONParser parser = new JSONParser();
            Object jsonObject = parser.parse(response);
            String result = JsonPath.read(jsonObject, jsonPath).toString();
            return result;
        } catch (Exception var5) {
            log.info("Exception - " + var5);
            throw new RuntimeException("error in getValueFromJsonString");
        }
    }
    public HashMap<String, String> convertDataTableValuesToList(DataTable dt){
        HashMap<String, String> val = new HashMap<String, String>();
        List<List<String>> list  = dt.asLists(String.class);
        List<Map<String, String>> map  = dt.asMaps(String.class, String.class);
        if(list.get(0).size() != 2){
            throw new RuntimeException("Failed data load");
        }
        for(int i=0; i<list.size();i++){
            val.put(list.get(i).get(0), list.get(i).get(1));
        }
        return val;
    }

    public String createRestRequest(String fileName, Map<String, String> testData) {

        String val = this.loadRequest(fileName);
       // Map<String, String> testData = (Map<String, String>)this.context.getVar("testData");
        Set<String> keyList = testData.keySet();
        String data = "empty";
        String keyAttribute = null;
        Iterator var6 = keyList.iterator();

        while(var6.hasNext()) {

            String key = (String)var6.next();
            try {
                Map<String, String> testData1 = testData;

                data = (String)testData1.get(key);
            }catch(Exception var18) {
                log.info(var18.getMessage());
            }

            if (data != null && data.equals("*")) {
                val = val.replace("${" + key + "}", "");
            }else if(data != null) {
                if(data.equals("pending TransactionID")) {
                    //val = val.replace("${" + key + "}", this.context.pendingTransactionID_resubmit);
                }else {
                    val = val.replace("${" + key + "}", data);
                }
            }else {

                if (key.contains("_")) {
                    String [] actKey = key.split("_");
                    keyAttribute = actKey[1];
                }

                val = val.replace("\"name\":\"" + key + "\",", "");
                val = val.replace("\"name\":\"" + key + "\"", "");

                val = val.replace("\"" + keyAttribute + "\":\"${" + key + "}\",", "");
                val = val.replace("\"" + keyAttribute + "\":\"${" + key + "}\"", "");

                val = val.replace("\"value\":\"${" + key + "}\",", "");
                val = val.replace("\"value\":\"${" + key + "}\"", "");

                val = val.replace("\"" + key + "\":\"${" + key + "}\",", "");
                val = val.replace("\"" + key + "\":\"${" + key + "}\"", "");

                int fromIndex = 0;
                int lastComma;
                for (lastComma = 0; lastComma < val.length(); ++lastComma) {
                    int openIndex = val.indexOf("{", fromIndex);
                    int closeIndex = val.indexOf("}", openIndex);

                    if (closeIndex >= val.length() || closeIndex == -1 || openIndex == -1) {
                        break;
                    }
                    if (val.substring(openIndex +1,  closeIndex -1).trim().isEmpty()) {
                        if (val.substring(closeIndex + 1, closeIndex + 2).equals(",")) {
                            val = val.replace(val.substring(openIndex, closeIndex + 2), "");
                            fromIndex = closeIndex + 2;
                        }else {
                            val = val.replace(val.substring(openIndex, closeIndex + 1), "");
                            fromIndex = closeIndex = 1;
                        }
                    }else {
                        fromIndex = closeIndex = 1;
                    }
                }
                int lastCamma = 0;
                int firstCamma;
                for (firstCamma = 0; firstCamma < val.length(); ++firstCamma) {
                    int lastSqBracket = val.indexOf("]", lastCamma);
                    if (firstCamma >= val.length() || lastSqBracket == -1 || lastCamma == -1) {
                        break;
                    }

                    String subString = val.substring(0, lastSqBracket);
                    lastCamma = subString.lastIndexOf(",");
                    if (val.substring(lastCamma + 1, lastSqBracket).trim().equalsIgnoreCase("")) {
                        val = val.substring(0,lastCamma) + val.substring(lastCamma + 1);
                    }
                    lastCamma = lastSqBracket +1;
                    firstCamma = lastSqBracket +1;
                }
                firstCamma = 0;
                for (int i = 0; i < val.length(); ++i) {
                    int firstSqBracket = val.indexOf("[", firstCamma);
                    if (i >= val.length() || firstSqBracket == -1 || firstCamma == -1) {
                        break;
                    }
                    String firstSubString = val.substring(firstSqBracket, val.length());
                    firstCamma = firstSubString.indexOf(",");
                    if(firstCamma >= 0 && firstSubString.substring(firstSubString.indexOf("[") + 1, firstCamma).trim().equalsIgnoreCase("")); {
                        val = val.substring(0, val.indexOf(",", firstSqBracket)) + val.substring(val.indexOf(",", firstSqBracket) +1);
                    }
                    firstCamma = firstSqBracket + 1;
                    i = firstSqBracket + 1;
                }
            }
        }
        log.info(val);
       return val;
    }
    public String loadRequest(String requestName) {

        String template = "";
        InputStream is = null;
        BufferedReader bReader = null;
        FileReader reader = null;
        try {
            String sourcePath =  System.getProperty("user.dir") +  "\\src\\test\\resources\\templates\\" + requestName + ".txt";
            reader = new FileReader(sourcePath);
            bReader = new BufferedReader(reader);
            String newLine = System.lineSeparator();
            String temp = "";
            while ((temp = bReader.readLine()) != null) {
                template = template + temp + newLine;
            }

        } catch (Exception var29) {
            log.info("exception - " + var29);
        } finally {
            try {
                if (bReader != null) {
                    bReader.close();
                }
            } catch(Exception var30) {
                log.info(var30.getMessage());
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch(Exception var31) {
                log.info(var31.getMessage());
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch(Exception var32) {
                log.info(var32.getMessage());
            }
        }
        return template;
    }
}
