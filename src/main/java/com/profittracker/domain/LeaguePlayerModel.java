package com.profittracker.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LeaguePlayerModel {
    public List<String> leaguePlayerIds =  new ArrayList<>();
    public Date lastUpdated = new Date();

    public String toJson() throws JsonProcessingException {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(this);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public LeaguePlayerModel fromJson(String json) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, LeaguePlayerModel.class);
        } catch (Exception e) {

        }
        return null;
    }
}
