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


public class FileInfo
{

    private HashMap<String, Object> info = new HashMap<String, Object>();

    protected void putInfo(String key, Object value)
    {
        this.info.put(key, value);
    }

    public String getDateTime()
    {
        return (String) this.info.get("DateTime");
    }


    public String getObjectName()
    {
        return (String) this.info.get("ObjectName");
    }

    public Double getCommonsMetadataExtension()
    {
        return (Double) this.info.get("CommonsMetadataExtension");
    }

    /**
            public String[] getCategories()
            {
                // needs to be split
            }
    **/

    public String getAssessments()
    {
        return (String) this.info.get("Assessments");
    }

    public String getImageDescription()
    {
        return (String) this.info.get("ImageDescription");
    }

    public String getDateTimeOriginal()
    {
        return (String) this.info.get("DateTimeOriginal");

    }

    public String getCredit()
    {
        return (String) this.info.get("Credit");
    }

    /**
            public String getCreditHref()
            {

            }

    **/
    public String getArtist()
    {
        return (String) this.info.get("getArtist");
    }

    public String getLicenseShortName()
    {
        return (String) this.info.get("LicenseShortName");

    }

    public String getUsageTerms()
    {
        return (String) this.info.get("UsageTerms");
    }

    /**
            public Boolean AttributionRequired()
            {

            }

            public Boolean Copyrighted()
            {

            }
    **/
    public String getRestrictions()
    {
        return (String) this.info.get("Restrictions");
    }

    public String getLicense()
    {
        return (String) this.info.get("License");
    }


}
