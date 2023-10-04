package com.softwareag.connectivity.triggers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareag.connectivity.InteractionInput;
import com.softwareag.connectivity.InteractionOutput;
import com.softwareag.connectivity.interaction.extension.runtime.Trigger;
import com.softwareag.connectivity.triggers.v1.NewsData;
import com.softwareag.connectivity.triggers.v2.NewsDataV2;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Trigger(name="getLatestNews")
public class NewsApiTrigger {
    @RestClient
    NewsApiService newsApiService;

    public void execute(InteractionInput input, InteractionOutput output) throws JsonProcessingException {
        JsonNode inputPayload = input.getInputPayload();
        String version = input.getVersion();

        String metadata =  inputPayload.get("PrevState").get("Metadata").asText();
        JsonNode jsonNode = new ObjectMapper().readTree(metadata);

        String query = jsonNode.get("query").asText();
        String apiKey = jsonNode.get("apiKey").asText();
        String fromTime = inputPayload.get("PrevState").get("UnixTime").asText();

        String news = newsApiService.getNews(query, fromTime, apiKey);

        if("v2".equals(version)) {
            output.setEntity(new ObjectMapper().readValue(news, NewsDataV2.class));
        } else {
            output.setEntity(new ObjectMapper().readValue(news, NewsData.class));
        }
        output.setStatus(Response.Status.OK);
    }
}
