package com.example.myapplication.adapter;

import android.content.Context;
import android.nfc.cardemulation.OffHostApduService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.db.TypeBean;

import java.util.List;

public class TypeBaseAdapter extends BaseAdapter {

    Context context;
    List<TypeBean> mDatas;
    int selectPos = 0;  //选中位置
    public TypeBaseAdapter(Context context, List<TypeBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_recordfrag_gv, viewGroup, false);
        //添加布局中的控件
        ImageView imageView = view.findViewById(R.id.item_recordfrag_iv);
        TextView textView = view.findViewById(R.id.item_recordfrag_tv);
        //获取指定位置的数据源
        TypeBean typeBean = mDatas.get(i);
        textView.setText(typeBean.getTypename());
        //判断当前位置是否为选中位置，如果是选中位置就设置为带颜色的图片， 否则为灰色图片
        if(selectPos == i){
            imageView.setImageResource(typeBean.getSimageId());
        }else {
            imageView.setImageResource(typeBean.getImageId());
        }

        return view;
    }
}
