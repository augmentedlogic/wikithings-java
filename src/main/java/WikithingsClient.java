/**
  Copyright (c) 2020 Wolfgang Hauptfleisch <dev@augmentedlogic.com>

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in all
  copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  SOFTWARE.
 **/
package com.augmentedlogic.wikithings;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.*;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;

import com.google.gson.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.safety.*;

public class WikithingsClient
{

    private String language = "en";
    public static final String WIKIPEDIA_API = "wikipedia.org/w/api.php";
    public static final String ENDPOINT_CATEGORIES = "?action=query&prop=categories&format=json";
    public static final String ENDPOINT_IMAGE = "?action=query&prop=pageimages&format=json&piprop=original";

    // TODO ADD MORE ENDPOINTS HERE
    private String user_agent = "wikipedia-api-client-java";
    private int timeout = 8000;
    private int connect_timeout = 8000;
    private int limit = 10;
    private Integer http_status = null;


    // TODO status code
    private String fetchData(String endpoint) throws Exception
    {
        StringBuffer response = new StringBuffer();
        URL obj = new URL(endpoint);
        String inputLine;
        try {

            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setConnectTimeout(8000);
            this.http_status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch(Exception e) {
            throw e;
        }
        return response.toString();
    }


    /**
     *
     **/
    public WikipediaArticle getArticle(String lemma) throws Exception
    {
        String data = null;
        WikipediaArticle wa = new WikipediaArticle();

        try {
            String endpoint = "https://" + this.language + "." + WIKIPEDIA_API;
            String params = "?action=parse&format=json" + "&page=" + URLEncoder.encode(lemma, "UTF-8");
            data = this.fetchData(endpoint + params);
            JsonObject rootObject = JsonParser.parseString(data).getAsJsonObject();
            JsonObject parseObject = rootObject.get("parse").getAsJsonObject();
            JsonObject text_e = parseObject.get("text").getAsJsonObject();
            String text = text_e.get("*").getAsString();

            JsonArray images = parseObject.get("images").getAsJsonArray();
            JsonArray externallinks = parseObject.get("externallinks").getAsJsonArray();
            JsonArray links = parseObject.get("links").getAsJsonArray();

            // add the text to the article object
            wa.setText(text);


            // TODO: user String[] instead ?
            //
            for(JsonElement image : images) {
                wa.addImage(image.getAsString());
            }

            //
            for(JsonElement externallink : externallinks) {
                wa.addExternalLink(externallink.getAsString());
            }


            //
            Link[] internal_links = null;
            int i =0;
            try {

                // TODO
                // for(JsonElement link : links) {
                //    JsonObject link_obj = link.getAsJsonObject();
                //    Link link_o = new Link();
                //         link_o.setTitle(link_obj.get("*").getAsString());
                //         link_o.setNamespace(link_obj.get("ns").getAsInt());
                //    internal_links[i] = link_o;
                //    i++;
                // }

            } catch(Exception e) {
                throw e;
            }

            // TODO/FUTURE set LINKS
            // Category
            // strings
            // TODO: langlinks (objects)
            // JsonArray iwlinks = parseObject.get("externallinks").getAsJsonArray();
            // links (complex) SKIP
            // section (maybe)

        } catch(Exception e) {
            throw e;
        }
        return wa;
    }


    /**
     *
     **/
    public WikipediaExtract getExtract(String lemma) throws Exception
    {
        WikipediaExtract extract = new WikipediaExtract();
        String data = null;
        try {
            String endpoint = "https://" + this.language + "." + WIKIPEDIA_API;
            String params = "?format=json&action=query&prop=extracts&exintro&noexcerpt&explaintext&redirects=1&explaintext&exintro" + "&titles=" + URLEncoder.encode(lemma, "UTF-8");
            data = this.fetchData(endpoint + params);

            JsonObject pages = JsonParser.parseString(data)
                               .getAsJsonObject().get("query")
                               .getAsJsonObject().get("pages")
                               .getAsJsonObject();
            String pageid = null;
            Set<Map.Entry<String, JsonElement>> entries = pages.entrySet();//will return members of your object
            for (Map.Entry<String, JsonElement> entry: entries) {
                pageid = (String) entry.getKey();
            }
            JsonObject page = pages.get(pageid).getAsJsonObject();
            extract.setText(page.get("extract").getAsString());
        } catch(Exception e) {
            throw e;
        }
        return extract;
    }


    /**
     *
     **/
    public ArticleImage getArticleImage(String lemma) throws Exception
    {
        ArticleImage ai = null;
        String data = null;
        String endpoint = "https://" + this.language + "."+ WIKIPEDIA_API + ENDPOINT_IMAGE + "&titles=" + URLEncoder.encode(lemma, "UTF-8");

        try {
            data = this.fetchData(endpoint);
            JsonObject pages = JsonParser.parseString(data)
                               .getAsJsonObject().get("query")
                               .getAsJsonObject().get("pages")
                               .getAsJsonObject();


            String pageid = null;
            Set<Map.Entry<String, JsonElement>> entries = pages.entrySet();
            for (Map.Entry<String, JsonElement> entry: entries) {
                pageid = (String) entry.getKey();
            }
            JsonObject page = pages.get(pageid).getAsJsonObject();
            JsonObject original = page.get("original").getAsJsonObject();
            Gson gson = new Gson();
            ai = gson.fromJson(original, ArticleImage.class);
        } catch(Exception e) {
            throw e;
        }
        return ai;
    }


    /**
     *
     **/
    public Category[] getPageCategories(String lemma) throws Exception
    {
        // TODO limit is missing
        Category[] userArray = null;
        String data = null;
        String endpoint = "https://" + this.language + "." + WIKIPEDIA_API +  ENDPOINT_CATEGORIES + "&titles=" + URLEncoder.encode(lemma, "UTF-8");
        try {
            data = this.fetchData(endpoint);
            JsonObject pages = JsonParser.parseString(data)
                               .getAsJsonObject().get("query")
                               .getAsJsonObject().get("pages")
                               .getAsJsonObject();

            String pageid = null;
            Set<Map.Entry<String, JsonElement>> entries = pages.entrySet();//will return members of your object
            for (Map.Entry<String, JsonElement> entry: entries) {
                pageid = (String) entry.getKey();
            }
            // if pageid is -1 then not found
            JsonObject zero = pages.get(pageid).getAsJsonObject();
            JsonArray categories = zero.get("categories").getAsJsonArray();

            Gson gson = new Gson();
            userArray = gson.fromJson(categories, Category[].class);
            //for(Category user : userArray) {
                //System.out.println(user.getTitle());
            //}

        } catch(Exception e) {
            throw e;
        }
        return userArray;
    }


    /**
     *
     **/
    public CategoryMember[] getCategoryMembers(String category) throws Exception
    {
        CategoryMember[] userArray = null;
        String data = null;
        String endpoint = "https://" + this.language + "." + WIKIPEDIA_API + "?action=query&list=categorymembers&cmtitle=Category:" + category + "&cmlimit=" + this.limit + "&format=json";
        try {
            data = this.fetchData(endpoint);
            JsonArray categorymembers = JsonParser.parseString(data)
                                        .getAsJsonObject().get("query")
                                        .getAsJsonObject().get("categorymembers")
                                        .getAsJsonArray();


            Gson gson = new Gson();
            userArray = gson.fromJson(categorymembers, CategoryMember[].class);

        } catch(Exception e) {
            throw e;
        }
        return userArray;
    }


    // SEARCH
    public SearchResult[] searchArticles(String searchterm) throws Exception
    {
        SearchResult[] userArray = null;
        String data = null;
        String endpoint = "https://" + this.language + "." + WIKIPEDIA_API + "?action=query&list=search&srsearch=" + searchterm + "&format=json&srlimit=" + this.limit + "&format=json";
        try {
            data = this.fetchData(endpoint);

            JsonArray search = JsonParser.parseString(data)
                               .getAsJsonObject().get("query")
                               .getAsJsonObject().get("search")
                               .getAsJsonArray();

            Gson gson = new Gson();
            userArray = gson.fromJson(search, SearchResult[].class);
            //for(SearchResult user : userArray) {
            //    System.out.println(user.getTitle());
            //    System.out.println(user.getPageId());
            //}
        } catch(Exception e) {
            throw e;
        }
        return userArray;
    }

    // TODO
    ///https://en.wikipedia.org/w/api.php?action=query&prop=imageinfo&iiprop=extmetadata&titles=File%3aBrad_Pitt_at_Incirlik2.jpg&format=json
    public FileInfo getFileInfo(String filename) throws Exception
    {
        FileInfo fileInfo = new FileInfo();
        String data = null;
        String endpoint = "https://" + this.language + ".wikipedia.org/w/api.php?action=query&prop=imageinfo&iiprop=extmetadata&titles=File%3a" + filename + "&format=json";
        try {
            data = this.fetchData(endpoint);

            JsonObject rootObject = JsonParser.parseString(data).getAsJsonObject();
            JsonObject queryObject = rootObject.get("query").getAsJsonObject();
            JsonObject pages = queryObject.get("pages").getAsJsonObject();
            JsonObject page = pages.get("-1").getAsJsonObject();
            //TODO: chain the above

            JsonArray imageinfo = page.get("imageinfo").getAsJsonArray();
            JsonObject zero = imageinfo.get(0).getAsJsonObject();
            JsonObject extmetadata = zero.get("extmetadata").getAsJsonObject();

            Set<Map.Entry<String, JsonElement>> entries = extmetadata.entrySet();
            for (Map.Entry<String, JsonElement> entry: entries) {
                String k = (String) entry.getKey();
                JsonObject e = entry.getValue().getAsJsonObject();
                String v = e.get("value").getAsString();
                fileInfo.putInfo(k, v);
            }

        } catch(Exception e) {
            throw e;
        }
        return fileInfo;
    }


    public void setUserAgent(String user_agent)
    {
        this.user_agent = user_agent;
    }

    public Integer getHttpStatus()
    {
        return this.http_status;
    }

    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }

    public void setConnectTimeout(int connect_timeout)
    {
        this.connect_timeout = connect_timeout;
    }

    public void setLanguage(String lang)
    {
        this.language = lang;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }


}
