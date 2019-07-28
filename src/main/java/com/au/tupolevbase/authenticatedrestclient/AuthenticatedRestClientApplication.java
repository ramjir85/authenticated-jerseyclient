package com.au.tupolevbase.authenticatedrestclient;

import org.glassfish.jersey.client.ClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import javax.net.ssl.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Configuration;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@SpringBootApplication
@EnableCaching
public class AuthenticatedRestClientApplication {

	@Autowired
	AuthenticatedRestClientConfiguration authenticatedRestClientConfiguration;

	@Bean
	public Client createClient() throws KeyManagementException, NoSuchAlgorithmException {
		Client client = createHttpsClient();
		return client;
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthenticatedRestClientApplication.class, args);
	}

	public Client createHttpsClient() throws NoSuchAlgorithmException, KeyManagementException {
		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		}};
		SSLContext sslContext = SSLContext.getInstance(authenticatedRestClientConfiguration.getTlsVersion());
		sslContext.init((KeyManager[])null, trustAllCerts, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		ClientBuilder.newBuilder().sslContext(sslContext);
		Client client = ClientBuilder.newClient();
		client.property(ClientProperties.CONNECT_TIMEOUT,900000);
		client.property(ClientProperties.READ_TIMEOUT, 900000);
		return client;
	}

}
