package com.example.administrator.myapplication2

import android.annotation.SuppressLint
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

class VideoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        return inflater.inflate(R.layout.video_fragment, container, false)
    }

}
