package com.au.tupolevbase.authenticatedrestclient.rest;

import com.au.tupolevbase.authenticatedrestclient.exception.AuthenticateException;
import com.au.tupolevbase.authenticatedrestclient.exception.TupolevCallException;
import com.au.tupolevbase.authenticatedrestclient.service.Authenticate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;

@Component
public class AuthenticatedClient<T extends Object> {

    public static final String AUTHORIZATION = "Authorization";
    public static final String CACHED = "cached";
    public static final String REFRESH = "refresh";
    public static final int UNAUTHORIZED = 401;
    private static Logger LOG = LoggerFactory.getLogger(AuthenticatedClient.class);

    @Autowired
    Client client;

    @Autowired
    Authenticate authenticate;

    public Response post(String uri, T payload) throws TupolevCallException, AuthenticateException, IOException {

        return postCall(uri, payload);
    }

    public Response post(URI uri, T payload) throws TupolevCallException, AuthenticateException, IOException {
        return postCall(uri.toString(), payload);
    }

    private Response postCall(String uri, T payload) throws TupolevCallException, AuthenticateException, IOException {
        Response response;
        LOG.info("About to make the POST call for URI: [{}] with Payload: [{}]",uri,payload.toString());
        try {

            response = client.target(uri)
                    .request(MediaType.APPLICATION_JSON)
                    .header(AUTHORIZATION, authenticate.getApiToken())
                    .post(Entity.entity(payload, MediaType.APPLICATION_JSON));

        } catch(Exception e){
            LOG.error("There was error calling the Tupolev resource: [{}]",uri);
            throw new TupolevCallException("Failed to call Tupolev resource");

        }

        if (response.getStatus() == UNAUTHORIZED) {
             LOG.error("Unauthorized or Expired Token. Refreshing the token!");
             authenticate.resetToken();
             return client.target(uri)
                     .request(MediaType.APPLICATION_JSON)
                     .header(AUTHORIZATION, authenticate.getApiToken())
                     .post(Entity.entity(payload, MediaType.APPLICATION_JSON));

         }
        return response;
    }


}
