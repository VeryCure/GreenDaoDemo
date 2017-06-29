package com.jeff.ttxs.greendaodemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jeff.ttxs.greendaodemo.db.GreenDaoHelper;
import com.jeff.ttxs.greendaodemo.db.User;
import com.jeff.ttxs.greendaodemo.db.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.name)
    EditText mNameEt;
    @BindView(R.id.age)
    EditText mAgeEt;
    @BindView(R.id.key)
    EditText mKeyEt;


    private List<User> mUsers = new ArrayList<>();
    private DaoAdapter mDaoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }

    private void initData()
    {
        //这是我测试是否已经关联
        mUsers = GreenDaoHelper.getDaoSession().getUserDao().loadAll();
        if (mUsers.size()==0){
            for (int i = 0; i < 20; i++)
            {
                User user = new User();
                user.setName("我是第"+i+"条-----");
                user.setAge(20+i);
                mUsers.add(user);
                insertData(user);
            }
        }
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mDaoAdapter = new DaoAdapter(this, mUsers);
        mRecycler.setAdapter(mDaoAdapter);
    }

    @OnClick({R.id.insert, R.id.delete, R.id.updata, R.id.check})
    public void onClick(View view)
    {
        User user = new User();
        user.setName(mNameEt.getText().toString());
        String ageTx = mAgeEt.getText().toString().trim();
        if (TextUtils.isEmpty(ageTx)){
            user.setAge(-1);
        }else {
            user.setAge(Integer.parseInt(ageTx));
        }
        switch (view.getId())
        {
            case R.id.insert:
                insertData(user);
                break;
            case R.id.delete:
                deleteData();
                break;
            case R.id.updata:
                updateData(user);
                break;
            case R.id.check:
                queryData(user);
                break;
        }
    }

    /**
     * 插入数据
     * @param user
     */
    private void insertData(User user){
        GreenDaoHelper.getDaoSession().getUserDao().insert(user);
        mUsers.add(user);
        mDaoAdapter.notifyDataSetChanged();
    }

    /**
     * 删除数据
     * 目前只是根据key删除
     */
    private void deleteData(){
        UserDao userDao = GreenDaoHelper.getDaoSession().getUserDao();
        String keyTx = mKeyEt.getText().toString().trim();
        long key = -1;
        if (!TextUtils.isEmpty(keyTx)){
            key = Long.parseLong(keyTx);
        }
        if (key==-1){
            userDao.deleteAll();
            mUsers.clear();
        }else {
            userDao.deleteByKey(key);
            for (int i=0;i<mUsers.size();i++){
                if (mUsers.get(i).getId() == key){
                    mUsers.remove(mUsers.get(i));
                }
            }
        }
        mDaoAdapter.notifyDataSetChanged();
    }

    /**
     * 更新数据
     * @param user
     */
    private void updateData(User user){
        UserDao userDao = GreenDaoHelper.getDaoSession().getUserDao();
        QueryBuilder<User> where = userDao.queryBuilder().where(UserDao
                .Properties.Age.eq(user.getAge()));
        List<User> list = where.list();
        //更新数据id必须有值，暂时我是这么查到得
        if (list == null || list.isEmpty()){
            insertData(user);
        }else {
            for (User u : list){
                user.setId(u.getId());
                userDao.update(user);
            }
        }
        for (int i=0;i<mUsers.size();i++){
            if (mUsers.get(i).getAge() == user.getAge()){
                mUsers.get(i).setName(user.getName());
            }
        }
        if (mDaoAdapter!= null){
            mDaoAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 查询数据
     * @param user
     */
    private void queryData(User user){
        UserDao userDao = GreenDaoHelper.getDaoSession().getUserDao();
        List<User> list = new ArrayList<>();
        if (TextUtils.isEmpty(user.getName())&& user.getAge()!=-1){
            list = userDao.queryBuilder().where(UserDao.Properties.Age.eq
                    (String.valueOf(user.getAge()))).list();
        }else if (!TextUtils.isEmpty(user.getName())){
            list = userDao.queryBuilder().where(UserDao.Properties.Name
                    .eq(user.getName())).list();
        }else {
            Toast.makeText(this,"没有查到数据",Toast.LENGTH_SHORT).show();
        }
        mUsers.clear();
        mUsers.addAll(list);
        mDaoAdapter.notifyDataSetChanged();
    }
    class DaoAdapter extends RecyclerView.Adapter<DaoHolder>
    {

        private Context mContext;
        private List<User> mDatas;

        public DaoAdapter(Context context, List<User> items)
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
            User user = mDatas.get(position);
            daoHolder.mItemText.setText(user.getId()+"~~~~~~"+BaseUtils
                    .combinString
                    (user.getName(),String.valueOf(user.getAge())));
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
