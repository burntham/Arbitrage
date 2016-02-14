package com.danInterview.io;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Request handler
 */
public class RequestHandler {

    HttpClient client;
    HttpGet httpGet;
    String baseURL = "http://api.fixer.io/latest";

    public RequestHandler()
    {
        client = HttpClientBuilder.create().build();
    }

    public String fetchCurrencyJson(String base) throws UnsupportedEncodingException, MalformedURLException, InterruptedException
    {
        System.out.print(base+",");
        String url= (new URL(String.format("%s?base=%s",baseURL,base))).toString();
        HttpGet httpGet = new HttpGet(url);

        httpGet.setHeader("Accept", "text/json,application/json");

        String json="";

        boolean success = false;

        do {
            try{

                HttpResponse httpResponse = client.execute(httpGet);
                json = buildJson(httpResponse.getEntity().getContent());
                success = true;
            }
            catch (Exception e)
            {
                //System.out.println("failed to connect, retrying in 1 second");
                Thread.sleep(1000);
            }
        }while(!success);

        return json;
    }


    private String buildJson(InputStream in) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in)))
        {
            for ( int c; (c=reader.read())!=-1;)
            {
                sb.append((char)c);
            }
        }
        return sb.toString();
    }
}
