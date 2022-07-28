package com.example.myapplication.record_fragment;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.Utils.KeyBoardUtils;
import com.example.myapplication.Utils.SelectTimeDialog;
import com.example.myapplication.adapter.TypeBaseAdapter;
import com.example.myapplication.db.AccountBean;
import com.example.myapplication.db.TypeBean;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecordFragment extends Fragment implements View.OnClickListener{


    KeyboardView keyboardView;
    EditText moneyEt;
    ImageView typeIv;
    TextView typeTv, beizhuTv, timeTv;
    GridView typeGv;
    List<TypeBean> typeList;
    TypeBaseAdapter typeBaseAdapter;
    AccountBean accountBean;   //将需要插入到记账本当中的数据保存成对象的形式
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_base_record, container, false);
        initView(view);
        loadDataToGV();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_record_tv_time:
                showTimeDialog();
                break;
            case R.id.frag_record_tv_beizhu:
                showBZDialog();
                break;
        }
    }

    /**
     * 给Gridview填充数据的方法
     */
    public void loadDataToGV(){
        typeList = new ArrayList<>();
        typeBaseAdapter = new TypeBaseAdapter(getContext(), typeList);
        typeGv.setAdapter(typeBaseAdapter);
    }

    /**
     * 初始化视图
     * @param view
     */
    private void initView(View view) {
        keyboardView = view.findViewById(R.id.frag_record_keyboard);
        moneyEt = view.findViewById(R.id.frag_record_et_money);
        typeIv = view.findViewById(R.id.frag_record_iv);
        typeGv = view.findViewById(R.id.frag_record_gv);
        typeTv = view.findViewById(R.id.frag_record_tv_type);
        beizhuTv = view.findViewById(R.id.frag_record_tv_beizhu);
        timeTv = view.findViewById(R.id.frag_record_tv_time);
        beizhuTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);
        //让自定义软键盘显示出来
        KeyBoardUtils boardUtils = new KeyBoardUtils(keyboardView, moneyEt);
        boardUtils.showKeyboard();
        //设置接口，监听确定按钮按钮被点击了
        boardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                //获取输入钱数
                String moneyStr = moneyEt.getText().toString();
                if (TextUtils.isEmpty(moneyStr)||moneyStr.equals("0")) {
                    getActivity().finish();
                    return;
                }
                float money = Float.parseFloat(moneyStr);
                accountBean.setMoney(money);
                //获取记录的信息，保存在数据库当中
//                saveAccountToDB();
                // 返回上一级页面
                getActivity().finish();
            }
        });
    }
    /* 让子类一定要重写这个方法*/
    public abstract void saveAccountToDB();

    public void showTimeDialog(){
//        new SelectTimeDialog()
    }
    public void showBZDialog(){

    }
}