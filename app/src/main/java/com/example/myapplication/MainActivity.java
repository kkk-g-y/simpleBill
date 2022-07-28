package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.adapter.AccountAdapter;
import com.example.myapplication.db.AccountBean;
import com.example.myapplication.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //展示今日收支情况的ListView
    ListView todayLv;
    ImageView searchIv;
    Button editBtn;
    ImageButton moreBtn;
    //声明数据源
    List<AccountBean> mDatas;
    AccountAdapter adapter;
    int year,month,day;
//    头布局相关文件
    View headerView;
    TextView topOutTv, topInTv, topbudgetTv, topConTv;
    ImageView topShowIv;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //添加ListView的头布局
        addLVHeaderView();
        mDatas = new ArrayList<>();
        //设置适配器：加载每一行数据到列表当中
        adapter = new AccountAdapter(this, mDatas);
        todayLv.setAdapter(adapter);
    }
    /** 给ListView添加头布局的方法*/
    private void addLVHeaderView() {
        //将布局转换成View对象
        headerView = getLayoutInflater().inflate(R.layout.item_mainlv_top, null);
        todayLv.addHeaderView(headerView);
        //查找头布局可用控件
        topOutTv = headerView.findViewById(R.id.item_mainlv_top_tv_out);
        topInTv = headerView.findViewById(R.id.item_mainlv_top_tv_in);
        topbudgetTv = headerView.findViewById(R.id.item_mainlv_top_tv_budget);
        topConTv = headerView.findViewById(R.id.item_mainlv_top_tv_day);
        topShowIv = headerView.findViewById(R.id.item_mainlv_top_iv_hide);

        topbudgetTv.setOnClickListener(this);
        headerView.setOnClickListener(this);
        topShowIv.setOnClickListener(this);

    }

    /**
     * 初始化自带的view方法
     */
    private void initView(){
        todayLv = findViewById(R.id.main_lv);
        searchIv = findViewById(R.id.main_iv_search);
        editBtn = findViewById(R.id.main_btn_edit);
        moreBtn = findViewById(R.id.main_btn_more);
        //设置监听
        editBtn.setOnClickListener(this);
        moreBtn.setOnClickListener(this);
        searchIv.setOnClickListener(this);
        setLVLongClickListener();
    }

    /**
     * 设置listview的长按事件
     */
    private void setLVLongClickListener(){
        todayLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    return false;//点击了头布局
                }
                int pos = i - 1;
                AccountBean clickBean = mDatas.get(i);
                //弹出提示用户是否删除的对话框
                showDeleteItemDialog(clickBean);
                return false;
            }
        });
    }
    /* 弹出是否删除某一条记录的对话框*/
    private void showDeleteItemDialog(final  AccountBean clickBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("您确定要删除这条记录么？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int click_id = clickBean.getId();
                        //执行删除的操作
//                        DBManager.deleteItemFromAccounttbById(click_id);
                        mDatas.remove(clickBean);   //实时刷新，移除集合当中的对象
//                        adapter.notifyDataSetChanged();   //提示适配器更新数据
//                        setTopTvShow();   //改变头布局TextView显示的内容
                    }
                });
        builder.create().show();   //显示对话框
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_btn_edit:
                Intent it1 = new Intent(this, RecordActivity.class);
                startActivity(it1);
                break;
            case R.id.item_mainlv_top_iv_hide:
                moneyHide();
                break;
        }
    }


    /**
     * 点击头布局眼睛按钮时，如果原来是明文就加密，如果是密文就显示出来
     */
    boolean isShow = true;
    private void moneyHide(){
        if(isShow){//原来是显示的，现在要改为不显示
            PasswordTransformationMethod passwordMethod = PasswordTransformationMethod.getInstance();
            topInTv.setTransformationMethod(passwordMethod);
            topOutTv.setTransformationMethod(passwordMethod);
            topbudgetTv.setTransformationMethod(passwordMethod);
            topShowIv.setImageResource(R.mipmap.ih_hide);
            isShow = false;//设置标志位

        }else{//原来是不显示，现在改为显示
            HideReturnsTransformationMethod hideMethod = HideReturnsTransformationMethod.getInstance();
            topInTv.setTransformationMethod(hideMethod);
            topOutTv.setTransformationMethod(hideMethod);
            topbudgetTv.setTransformationMethod(hideMethod);
            topShowIv.setImageResource(R.mipmap.ih_show);
            isShow = true;
        }
    }
}