package com.softwareag.connectivity.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareag.connectivity.InteractionInput;
import com.softwareag.connectivity.InteractionOutput;
import com.softwareag.connectivity.actions.v1.ZipData;
import com.softwareag.connectivity.actions.v2.ZipDataV2;
import com.softwareag.connectivity.interaction.extension.runtime.Interaction;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Interaction(name="getLocationInfo")
public class ZippopotamAction {

    @RestClient
    ZipService zipService;

    public void execute(InteractionInput input, InteractionOutput output) throws JsonProcessingException {
        JsonNode inputPayload = input.getInputPayload();
        String version = input.getVersion();

        String country = inputPayload.get("country").asText();
        String postcode = inputPayload.get("postcode").asText();

        String jsonResponse = zipService.getZipData(country, postcode);
        if("v2".equals(version)) {
            output.setEntity(new ObjectMapper().readValue(jsonResponse, ZipDataV2.class));
        } else {
            output.setEntity(new ObjectMapper().readValue(jsonResponse, ZipData.class));
        }
        output.setStatus(Response.Status.OK);
    }
}
