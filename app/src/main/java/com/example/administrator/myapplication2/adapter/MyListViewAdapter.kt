package com.example.administrator.myapplication2.adapter

import android.app.Activity
import android.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import com.example.administrator.myapplication2.ContentActivity
import com.example.administrator.myapplication2.ContentFragment
import com.example.administrator.myapplication2.MainActivity
import com.example.administrator.myapplication2.R

class MyListViewAdapter(private val datas: List<Map<String, Any>>, private val context: Context) : BaseAdapter() {

    override fun getCount(): Int {
        return datas.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val data = datas[position]
        val view = View.inflate(context, R.layout.view0, null)
        val image1 = view.findViewById<View>(R.id.image1) as ImageView
        val url = data["url"] as String
        image1.setOnClickListener {
            val activity = context as Activity
            val transaction = activity.fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, ContentFragment.newInstance(url))
            transaction.commit()
            /*Intent intent = new Intent(context, ContentFragment.class);
                intent.putExtra("url", url);
                context.startActivity(intent);*/
        }
        image1.setImageBitmap(data["bitmap"] as Bitmap)
        val text1 = view.findViewById<View>(R.id.text1) as TextView
        text1.text = data["title"] as String
        return view
    }

}
