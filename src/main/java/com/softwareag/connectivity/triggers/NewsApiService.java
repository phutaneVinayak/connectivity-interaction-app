package com.softwareag.connectivity.triggers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("v2/everything")
@RegisterRestClient(baseUri = "https://newsapi.org")
public interface NewsApiService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    String getNews(@QueryParam("q") String query, @QueryParam("from") String fromTime, @QueryParam("apiKey") String apiKey);
}
