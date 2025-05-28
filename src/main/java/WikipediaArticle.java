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

import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.safety.*;


public class WikipediaArticle
{

    private ArrayList<String> images = new ArrayList<String>();
    private ArrayList<String> externallinks = new ArrayList<String>();
    private String text = null;

    protected void addImage(String image)
    {
        this.images.add(image);
    }

    protected void addExternalLink(String externallink)
    {
        this.externallinks.add(externallink);
    }


    protected void setText(String text)
    {
        this.text = text;
    }

    public ArrayList<String> getImages()
    {
        return this.images;
    }

    public ArrayList<String> getExternalLinks()
    {
        return this.externallinks;
    }

    // getlinks (objects)

    public String getText()
    {
        return this.text;
    }

    public String getPlainText()
    {
        if(this.text != null) {
            return Jsoup.clean(this.text, "", Safelist.none(), new OutputSettings().prettyPrint(true));
        }
        return null;
    }

    public String getPrettyPrintText()
    {
        if(this.text != null) {
            return Jsoup.clean(this.text, "", Safelist.none(), new OutputSettings().prettyPrint(false));
        }
        return null;
    }

}
