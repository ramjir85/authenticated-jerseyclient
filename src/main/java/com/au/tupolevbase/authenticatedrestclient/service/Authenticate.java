package com.au.tupolevbase.authenticatedrestclient.service;

import com.au.tupolevbase.authenticatedrestclient.AuthenticatedRestClientConfiguration;
import com.au.tupolevbase.authenticatedrestclient.domain.Token;
import com.au.tupolevbase.authenticatedrestclient.domain.TokenRequest;
import com.au.tupolevbase.authenticatedrestclient.exception.AuthenticateException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Service
public class Authenticate {

    private static Logger LOG = LoggerFactory.getLogger(Authenticate.class);

    @Autowired
    Client client;

    @Autowired
    AuthenticatedRestClientConfiguration authenticatedRestClientConfiguration;

    @Cacheable(value = "token")
    public String getApiToken() throws AuthenticateException, IOException {

        LOG.info("Auth Token end point : [{}]", authenticatedRestClientConfiguration.getAuthUrl());


        String authToken = null;
        Response response;
        TokenRequest tokenRequest = new TokenRequest(authenticatedRestClientConfiguration.getAppId(),
                authenticatedRestClientConfiguration.getSecret());

        try {

            response = client.target(authenticatedRestClientConfiguration.getAuthUrl())
                    .request(MediaType.APPLICATION_JSON)
                    .header("Content-Type"," application/json")
                    .post(Entity.entity(tokenRequest, MediaType.APPLICATION_JSON));
        } catch(Exception e) {
            LOG.error("There was an error calling the Tupolev Authenticate end point: [{}]", e.getMessage());
            throw new AuthenticateException("Failed Getting Auth token!!");
        }

        if(response.getStatus() == 200) {
            String tokenString = response.readEntity(String.class);
            authToken = new ObjectMapper().readValue(tokenString, Token.class).getToken();
        }
        return authToken;
    }

    @CacheEvict(value = "token", allEntries = true)
    public void resetToken() {

    }


}
