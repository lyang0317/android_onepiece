package com.example.administrator.myapplication2.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

class MyPageAdapter(private val viewList: List<View>) : PagerAdapter() {


    override fun getCount(): Int {
        return viewList.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        container.addView(viewList[position % viewList.size])
        return viewList[position % viewList.size]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(viewList[position % viewList.size])
    }

}
