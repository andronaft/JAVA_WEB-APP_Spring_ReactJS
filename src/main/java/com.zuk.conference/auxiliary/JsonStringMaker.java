package com.zuk.conference.auxiliary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonStringMaker {
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonInString = "";

    public String objectToJson(Object object){
        jsonInString="";
        System.out.println(object.toString() + "object");
        Boolean isCreateJson = false;
        try {
            jsonInString = objectMapper.writeValueAsString(object);
            isCreateJson = true;
        } catch (JsonProcessingException e) {
            System.out.println("error with json");
            e.printStackTrace();
        }
        finally {
            if(!isCreateJson) {
                ArrayList<String> error = new ArrayList<String>();
                error.add("Woooh");
                error.add("try later");
                try {
                    jsonInString = objectMapper.writeValueAsString(error);
                    return jsonInString;
                } catch (JsonProcessingException e) {
                    System.out.println("error with error json");
                    e.printStackTrace();
                }
            }
        }
        return jsonInString;
    }

    public String listToJson(ArrayList list) {
        jsonInString = "";
        Boolean isCreateJson = false;
        try {
            jsonInString = objectMapper.writeValueAsString(list);
            isCreateJson = true;
        } catch (JsonProcessingException e) {
            System.out.println("error with json");
            e.printStackTrace();
        }
        finally {
            if(!isCreateJson) {
                System.out.println("finally listToJson");
                ArrayList<String> error = new ArrayList<String>();
                error.add("Woooh");
                error.add("try later");
                try {
                    jsonInString = objectMapper.writeValueAsString(error);
                    return jsonInString;
                } catch (JsonProcessingException e) {
                    System.out.println("error with error json");
                    e.printStackTrace();
                }
            }
        }
        return jsonInString;
    }

}