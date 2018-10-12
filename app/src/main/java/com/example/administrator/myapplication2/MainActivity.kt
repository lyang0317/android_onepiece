package com.example.administrator.myapplication2

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.StrictMode
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

import com.alibaba.fastjson.JSON
import com.example.administrator.myapplication2.adapter.MyListViewAdapter
import com.example.administrator.myapplication2.tools.ImageUtils
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.zyao89.view.zloading.ZLoadingDialog
import com.zyao89.view.zloading.Z_TYPE

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import cz.msebera.android.httpclient.Header

class MainActivity : Activity() {

    private var lv: ListView? = null
    private val text1: TextView? = null
    private val image1: ImageView? = null

    /* private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(msg.what == 1){
                List<Map<String, Object>> datas = (List<Map<String, Object>>) msg.obj;
                lv.setAdapter(new MyListViewAdapter(datas, MainActivity.this));
            }else if(msg.what == 2){
                Toast.makeText(MainActivity.this, "显示图片错误", Toast.LENGTH_SHORT).show();
            }
        };
    };*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dialog = ZLoadingDialog(this@MainActivity)
        dialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE)//设置类型
                .setLoadingColor(Color.BLACK)//颜色
                .setHintText("Loading...")
                .show()

        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        lv = findViewById<View>(R.id.lv) as ListView

        val client = AsyncHttpClient()//实例化AsyncHttpCli
        try {
            client.get("http://45.32.24.209:8080/android/picture/mainUrlList", object : AsyncHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                    val maps = JSON.parseArray(String(responseBody), Map::class.java) as List<Map<String, Any>>
                    if (maps.size != 0) {
                        for (obj in maps) {
                            val map = obj as MutableMap<String, Bitmap>
                            println(map["picUrl"] as String)
                            val bitmap = ImageUtils.getBitmapByUrl(map["picUrl"] as String) as Bitmap
                            map.put("bitmap", bitmap)
                        }
                        lv!!.adapter = MyListViewAdapter(maps, this@MainActivity)
                        /*Message msg = new Message();
                        msg.what = 1;
                        msg.obj = maps;
                        handler.sendMessage(msg);*/
                    } else {
                        Toast.makeText(this@MainActivity, "显示图片错误", Toast.LENGTH_SHORT).show()
                        /* Message msg = new Message();
                        msg.what = 2;
                        handler.sendMessage(msg);*/
                    }
                    dialog.dismiss()
                }

                override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                    Toast.makeText(this@MainActivity, "显示图片错误", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    /*Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);*/
                }
            })
            /*List<Map> sequenceList = ImageUtils.getSequenceList();
            if (sequenceList.size() != 0) {
                for (Map map : sequenceList) {
                    String url = (String) map.get("url");
                    String title = (String) map.get("title");
                    Bitmap bitmapBySequence = ImageUtils.getBitmapBySequence(ImageUtils.getMainPicUrl(url));
                    map.put("bitmap", bitmapBySequence);
                }
                Message msg = new Message();
                msg.what = 1;
                msg.obj = sequenceList;
                handler.sendMessage(msg);

            } else {
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
            }*/

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this@MainActivity, "显示图片错误", Toast.LENGTH_SHORT).show()
        }

        /* new Thread() {
            public void run() {

                // 连接服务器 get 请求 获取图片.

            };
        }.start();
*/

    }

}
