package com.example.administrator.myapplication2

import android.app.Fragment
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

import cz.msebera.android.httpclient.Header

class MainFragment : Fragment() {

    private var lv: ListView? = null
    private val text1: TextView? = null
    private val image1: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_main, container, false)

        val dialog = ZLoadingDialog(activity)
        dialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE)//设置类型
                .setLoadingColor(Color.BLACK)//颜色
                .setHintText("Loading...")
                .show()

        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        lv = view.findViewById<View>(R.id.lv) as ListView

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
                        lv!!.adapter = MyListViewAdapter(maps, activity)
                        /*Message msg = new Message();
                        msg.what = 1;
                        msg.obj = maps;
                        handler.sendMessage(msg);*/
                    } else {
                        Toast.makeText(activity, "显示图片错误", Toast.LENGTH_SHORT).show()
                        /* Message msg = new Message();
                        msg.what = 2;
                        handler.sendMessage(msg);*/
                    }
                    dialog.dismiss()
                }

                override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                    Toast.makeText(activity, "显示图片错误", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(activity, "显示图片错误", Toast.LENGTH_SHORT).show()
        }

        return view
    }


}
