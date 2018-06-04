package com.example.ls.maillistdemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import butterknife.ButterKnife
import com.example.ls.maillistdemo.maillist.*
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_main.*
import net.sourceforge.pinyin4j.PinyinHelper
import java.util.*

class MainActivity : AppCompatActivity() {


    private var mDecoration: StickyItemDecoration? = null
    private var pinyinComparator: PinyinComparator? = null
    private var medicineAdapter: MedicineAdapter? = null
    private var mPorts: MutableList<MedicineBean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        tv_about_me.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(this@MainActivity, AboutMeActivity::class.java)
                startActivity(intent)
            }

        })
        initMailList()

    }

    private fun initMailList() {
        val s = FileUtil.readFromAssets(this, "content.json")
        val portDetailBeans = parseNoHeaderJArray(s)
        mPorts = ArrayList()
        mPorts!!.addAll(filledData(portDetailBeans))
        //按照字母排序
        pinyinComparator = PinyinComparator()
        Collections.sort(mPorts!!, pinyinComparator)
        rv_content!!.layoutManager = LinearLayoutManager(this)
        medicineAdapter = MedicineAdapter(this, mPorts)
        medicineAdapter!!.setListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val name = p0!!.tag as String
                Toast.makeText(this@MainActivity, name, Toast.LENGTH_LONG).show()
            }

        })
        rv_content!!.adapter = medicineAdapter
        mDecoration = StickyItemDecoration(medicineAdapter!!)
        rv_content!!.addItemDecoration(mDecoration)
        //添加分割线
        rv_content!!.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST))

        sidrbar!!.setTextView(tv_letter!!)
        //设置右侧触摸监听
        sidrbar!!.setOnTouchingLetterChangedListener(object : SideBar.OnTouchingLetterChangedListener {
            override fun onTouchingLetterChanged(s: String) {

                //该字母首次出现的位置
                val position = medicineAdapter!!.getPositionForSection(s)
                if (position != -1) {
                    rv_content!!.scrollToPosition(position)
                }
            }
        })
    }

    /**
     * 填充数据
     *
     * @param data
     * @return
     */
    private fun filledData(data: List<PortDetailBean>): List<MedicineBean> {
        val mSortList = ArrayList<MedicineBean>()

        for (i in data.indices) {
            val medicineBean = MedicineBean()
            val portDetailResponse = data[i]
            medicineBean.name = portDetailResponse.portName
            //汉字转换成拼音
            val pinyin = PinyinHelper.toHanyuPinyinStringArray(portDetailResponse.portName!!.toCharArray()[0])
            if (pinyin == null) {
                medicineBean.letter = "#"
            } else {
                val sortString = pinyin[0].substring(0, 1).toUpperCase()
                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]".toRegex())) {
                    medicineBean.letter = sortString.toUpperCase()
                } else {
                    medicineBean.letter = "#"
                }
            }

            mSortList.add(medicineBean)
        }
        return mSortList

    }

    private fun parseNoHeaderJArray(strByJson: String): ArrayList<PortDetailBean> {
        //Json的解析类对象
        val parser = JsonParser()
        //将JSON的String 转成一个JsonArray对象
        val jsonArray = parser.parse(strByJson).asJsonArray
        val gson = Gson()
        val userBeanList = ArrayList<PortDetailBean>()
        //加强for循环遍历JsonArray
        for (user in jsonArray) {
            //使用GSON，直接转成Bean对象
            val userBean = gson.fromJson(user, PortDetailBean::class.java)
            userBeanList.add(userBean)
        }
        return userBeanList
    }
}
