package com.example.administrator.myapplication2

import android.app.Activity
import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import android.widget.TextView

class HomeActivity : Activity(), View.OnClickListener {

    private var topBar: TextView? = null
    private var tabPic: TextView? = null
    private var tabVid: TextView? = null

    private var ly_content: FrameLayout? = null

    private var mainFragment: MainFragment? = null
    private var videoFragment: VideoFragment? = null
    private val contentFragment: ContentFragment? = null
    private val fragmentManager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.home_main)

        bindView()

    }

    //UI组件初始化与事件绑定
    private fun bindView() {
        topBar = this.findViewById<View>(R.id.txt_top) as TextView
        tabPic = this.findViewById<View>(R.id.txt_pic) as TextView
        tabVid = this.findViewById<View>(R.id.txt_video) as TextView
        ly_content = findViewById<View>(R.id.fragment_container) as FrameLayout

        tabPic!!.setOnClickListener(this)
        tabVid!!.setOnClickListener(this)

    }

    //重置所有文本的选中状态
    fun selected() {
        tabPic!!.isSelected = false
        tabVid!!.isSelected = false
    }

    //隐藏所有Fragment
    fun hideAllFragment(transaction: FragmentTransaction) {
        if (mainFragment != null) {
            transaction.hide(mainFragment)
        }
        if (videoFragment != null) {
            transaction.hide(videoFragment)
        }
        if (contentFragment != null) {
            transaction.hide(contentFragment)
        }
    }

    override fun onClick(v: View) {
        val transaction = getFragmentManager().beginTransaction()
        hideAllFragment(transaction)
        when (v.id) {
            R.id.txt_pic -> {
                selected()
                tabPic!!.isSelected = true
                if (mainFragment == null) {
                    mainFragment = MainFragment()
                    transaction.add(R.id.fragment_container, mainFragment)
                } else {
                    transaction.show(mainFragment)
                }
            }

            R.id.txt_video -> {
                selected()
                tabVid!!.isSelected = true
                if (videoFragment == null) {
                    videoFragment = VideoFragment()
                    transaction.add(R.id.fragment_container, videoFragment)
                } else {
                    transaction.show(videoFragment)
                }
            }
        }

        transaction.commit()
    }

}
