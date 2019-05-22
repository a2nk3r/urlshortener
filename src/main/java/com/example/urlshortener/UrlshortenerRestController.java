package com.example.urlshortener;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UrlshortenerRestController {

    private Map<String, ShortenUrl> shortenUrlList = new HashMap<>();

    @RequestMapping(value="/shortenurl", method = RequestMethod.POST)
    public ResponseEntity<Object> getShortenUrl(@RequestBody ShortenUrl shortenUrl) throws MalformedURLException {
        String randomChars = getRandomChars();
        setShortUrl(randomChars, shortenUrl);
        return new ResponseEntity<Object>(shortenUrl, HttpStatus.OK);
    }

    private void setShortUrl(String randomChars, ShortenUrl shortenUrl) throws MalformedURLException {
        shortenUrl.setShort_url("http://localhost:8080/s/"+randomChars);
        shortenUrlList.put(randomChars, shortenUrl);
    }


    @RequestMapping(value = "/s/{randomstring}", method = RequestMethod.GET)
    public void getFullUrl(HttpServletResponse response, @PathVariable("randomstring") String randomString) throws IOException{
        response.sendRedirect(shortenUrlList.get(randomString).getFull_url());
    }

    private String getRandomChars(){
        String randomStr = "";
        String pool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for(int i = 0;i < 5;i++)
            randomStr += pool.charAt((int)Math.floor(Math.random()*pool.length()));
        return randomStr;
    }
}
