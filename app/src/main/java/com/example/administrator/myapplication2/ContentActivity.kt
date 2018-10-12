package com.example.administrator.myapplication2

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast

import com.alibaba.fastjson.JSON
import com.example.administrator.myapplication2.adapter.MyPageAdapter
import com.example.administrator.myapplication2.tools.ImageUtils
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import com.zyao89.view.zloading.ZLoadingDialog
import com.zyao89.view.zloading.Z_TYPE

import java.util.ArrayList

import cz.msebera.android.httpclient.Header

class ContentActivity : Activity() {

    private var viewPager: ViewPager? = null

    private val viewList = ArrayList<View>()

    /*private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(msg.what == 1){
                List<Bitmap> bitmapList = (List<Bitmap>) msg.obj;
                for (Bitmap bitmap : bitmapList) {
                    View view = LayoutInflater.from(ContentActivity.this).inflate(R.layout.view1,null);
                    ImageView image2 = view.findViewById(R.id.image2);
                    image2.setImageBitmap(bitmap);
                    viewList.add(view);
                }
                viewPager.setAdapter(new MyPageAdapter(viewList));
            }else if(msg.what == 2){
                Toast.makeText(ContentActivity.this, "显示图片错误", Toast.LENGTH_SHORT).show();
            }
        };
    };*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)
        val dialog = ZLoadingDialog(this@ContentActivity)
        dialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE)//设置类型
                .setLoadingColor(Color.BLACK)//颜色
                .setHintText("Loading...")
                .show()

        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        viewPager = findViewById(R.id.view_pager)
        val url = intent.getStringExtra("url")

        val client = AsyncHttpClient()//实例化AsyncHttpCli
        try {
            val rp = RequestParams()
            rp.put("url", url)
            client.post("http://45.32.24.209:8080/android/picture/contentUrlList", rp, object : AsyncHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                    val strs = JSON.parseArray(String(responseBody), String::class.java)
                    if (strs.size != 0) {
                        val bitmapList = ArrayList<Bitmap>()
                        for (str in strs) {
                            val bitmap = ImageUtils.getBitmapByUrl(str)
                            if (bitmap != null) {
                                bitmapList.add(bitmap)
                            }
                        }
                        for (bitmap in bitmapList) {
                            val view = LayoutInflater.from(this@ContentActivity).inflate(R.layout.view1, null)
                            val image2 = view.findViewById<ImageView>(R.id.image2)
                            image2.setImageBitmap(bitmap)
                            viewList.add(view)
                        }
                        viewPager!!.adapter = MyPageAdapter(viewList)
                        /*Message msg = new Message();
                        msg.what = 1;
                        msg.obj = bitmapList;
                        handler.sendMessage(msg);*/
                    } else {
                        Toast.makeText(this@ContentActivity, "显示图片错误", Toast.LENGTH_SHORT).show()
                        /*Message msg = new Message();
                        msg.what = 2;
                        handler.sendMessage(msg);*/
                    }
                    dialog.dismiss()
                }

                override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                    Toast.makeText(this@ContentActivity, "显示图片错误", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    /*Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);*/
                }
            })
            /*List<Bitmap> bitmapList = new ArrayList<>();
            List<String> contentPicUrls = ImageUtils.getContentPicUrls(url);
            if(contentPicUrls.size() != 0) {
                for (String contentPicUrl : contentPicUrls) {
                    Bitmap bitmap = ImageUtils.getBitmapBySequence(contentPicUrl);
                    if (bitmap != null) {
                        bitmapList.add(bitmap);
                    }
                }
                if (bitmapList.size() != 0) {
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = bitmapList;
                    handler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                }
            }*/
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this@ContentActivity, "显示图片错误", Toast.LENGTH_SHORT).show()
            /*Message msg = new Message();
            msg.what = 2;
            handler.sendMessage(msg);*/
        }

        /*new Thread() {
            public void run() {


            };
        }.start();
*/
    }

}
