package com.softwareag.connectivity.actions;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient(baseUri = "https://api.zippopotam.us")
public interface ZipService {

    @GET
    @Path("/{country}/{postcode}")
    @Produces(MediaType.APPLICATION_JSON)
    String getZipData(@PathParam("country") String country, @PathParam("postcode") String postcode);
}
