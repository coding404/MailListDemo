package com.example.ls.maillistdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ls.maillistdemo.maillist.DividerItemDecoration;
import com.example.ls.maillistdemo.maillist.MedicineAdapter;
import com.example.ls.maillistdemo.maillist.MedicineBean;
import com.example.ls.maillistdemo.maillist.PinyinComparator;
import com.example.ls.maillistdemo.maillist.SideBar;
import com.example.ls.maillistdemo.maillist.StickyItemDecoration;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.rv_content)
    RecyclerView mRvContent;
    @InjectView(R.id.sidrbar)
    SideBar mSidrbar;
    @InjectView(R.id.tv_letter)
    TextView mTvLetter;

    private StickyItemDecoration mDecoration;
    private PinyinComparator pinyinComparator;
    private MedicineAdapter medicineAdapter;
    private List<MedicineBean> mPorts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initMailList();

    }

    private void initMailList() {
        String s = FileUtil.readFromAssets(this, "content.json");
        ArrayList<PortDetailBean> portDetailBeans = parseNoHeaderJArray(s);
        mPorts = new ArrayList<>();
        mPorts.addAll(filledData(portDetailBeans));
        //按照字母排序
        pinyinComparator = new PinyinComparator();
        Collections.sort(mPorts, pinyinComparator);
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        medicineAdapter = new MedicineAdapter(this, mPorts);
        medicineAdapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = (String) view.getTag();
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_LONG).show();
            }
        });
        mRvContent.setAdapter(medicineAdapter);
        mDecoration = new StickyItemDecoration(medicineAdapter);
        mRvContent.addItemDecoration(mDecoration);
        //添加分割线
        mRvContent.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        mSidrbar.setTextView(mTvLetter);
        //设置右侧触摸监听
        mSidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = medicineAdapter.getPositionForSection(s);
                if (position != -1) {
                    mRvContent.scrollToPosition(position);
                }
            }
        });

    }

    /**
     * 填充数据
     *
     * @param data
     * @return
     */
    private List<MedicineBean> filledData(List<PortDetailBean> data) {
        List<MedicineBean> mSortList = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            MedicineBean medicineBean = new MedicineBean();
            PortDetailBean portDetailResponse = data.get(i);
            medicineBean.setName(portDetailResponse.getPortName());
            //汉字转换成拼音
            String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(portDetailResponse.getPortName().toCharArray()[0]);
            if (pinyin == null) {
                medicineBean.setLetter("#");
            } else {
                String sortString = pinyin[0].substring(0, 1).toUpperCase();
                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    medicineBean.setLetter(sortString.toUpperCase());
                } else {
                    medicineBean.setLetter("#");
                }
            }

            mSortList.add(medicineBean);
        }
        return mSortList;

    }

    private ArrayList<PortDetailBean> parseNoHeaderJArray(String strByJson) {
        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();
        Gson gson = new Gson();
        ArrayList<PortDetailBean> userBeanList = new ArrayList<>();
        //加强for循环遍历JsonArray
        for (JsonElement user : jsonArray) {
            //使用GSON，直接转成Bean对象
            PortDetailBean userBean = gson.fromJson(user, PortDetailBean.class);
            userBeanList.add(userBean);
        }
        return userBeanList;
    }
}
