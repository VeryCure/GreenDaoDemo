package com.jeff.ttxs.greendaodemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    private List<String> mTextStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inntData();
    }

    private void inntData()
    {
        for (int i = 0; i < 20; i++)
        {
            mTextStrings.add("我是第"+i+"条-----"+"年龄："+String.valueOf(20+i));
        }
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(new DaoAdapter(this,mTextStrings));
    }

    @OnClick({R.id.insert, R.id.delete, R.id.updata, R.id.check})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.insert:
                break;
            case R.id.delete:
                break;
            case R.id.updata:
                break;
            case R.id.check:
                break;
        }
    }

    class DaoAdapter extends RecyclerView.Adapter<DaoHolder>
    {

        private Context mContext;
        private List<String> mDatas;

        public DaoAdapter(Context context, List<String> items)
        {
            mContext = context;
            mDatas = items;
        }

        @Override
        public DaoHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View contentView = LayoutInflater.from(mContext).inflate(R.layout
                            .item_layout,
                    parent, false);
            DaoHolder daoHolder = new DaoHolder(contentView);
            return daoHolder;
        }

        @Override
        public void onBindViewHolder(DaoHolder holder, int position)
        {
            DaoHolder daoHolder = holder;
            daoHolder.mItemText.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }
    }

    class DaoHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.list_item)
        TextView mItemText;

        public DaoHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
