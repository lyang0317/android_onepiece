package com.example.administrator.myapplication2.tools

import android.graphics.Bitmap
import android.graphics.BitmapFactory

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList
import java.util.HashMap
import java.util.regex.Matcher
import java.util.regex.Pattern

object ImageUtils {

    /*private static final String MAIN_URL = "http://op.hanhande.com/";
    private static final String URI = "http://www.hanhande.com/manhua/op/";
    private static final String URL_TYPE = "shtml";
    private static final String regex = "^海贼王(.*)话";*/

    fun getBitmapByUrl(picUrl: String): Bitmap? {
        try {
            val url = URL(picUrl)

            // 根据url 发送 http的请求.
            val conn = url
                    .openConnection() as HttpURLConnection
            // 设置请求的方式
            conn.requestMethod = "GET"
            conn.connectTimeout = 5000
            // conn.setReadTimeout(timeoutMillis);
            conn.setRequestProperty(
                    "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; Shuame)")

            // 得到服务器返回的响应码
            val code = conn.responseCode
            if (code == 200) {
                val `is` = conn.inputStream
                return BitmapFactory.decodeStream(`is`)
            } else {
                return null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /*public static List<Map> getSequenceList() {
        List<Map> sequenceList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(MAIN_URL).get();
            Elements elements = document.getElementsByClass("newsbox");
            for (Element element : elements) {
                Elements es = element.getElementsByTag("a");
                for (Element childElement : es) {
                    String text = childElement.text();
                    if(ImageUtils.isSequence(text)) {
                        String href = childElement.attr("href");
                        Map<String, String> data = new HashMap();
                        data.put("url", href);
                        data.put("title", text);
                        sequenceList.add(data);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return sequenceList;
        }
    }

    public static String getMainPicUrl(String url) {
        String src = "";
        try {
            Document document = Jsoup.connect(url).get();
            src = document.getElementById("pictureContent").getElementsByTag("img").attr("src");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return src;
        }
    }

    public static List<String> getContentPicUrls(String url)  {
        String[] split = url.split("." + URL_TYPE);
        List<String> srcList = new ArrayList<>();
        try {
            for (int index = 2;;index++) {
                String httpUrl = split[0] + "_" + index + "." + URL_TYPE;
                Document document = Jsoup.connect(httpUrl).get();
                String src = document.getElementById("pictureContent").getElementsByTag("img").attr("src");
                srcList.add(src);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return srcList;
        }
    }

    public static boolean isSequence(String str) {
        String s = "海贼王915a说的话：博罗镇";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.find();
    }*/

}
