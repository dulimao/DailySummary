package com.gxa.car.splitscreen.view.ac;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myandroiddemo.MainActivity;
import com.example.myandroiddemo.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class NewActivity extends AppCompatActivity {
    private static final String TAG = "ActivityTest";

    List<String> list = new ArrayList<>();
    MyListView recyclerView;
    TextView textview;
    RecyclerDomeAdapter adapter;

    static class TagListAdapter extends CategoryTagListView.Adapter<TypeTag> {
        private List<TypeTag> datas;
        public void setDatas(List<TypeTag> list) {
            datas = list;
        }

        @Override
        public RadioButton getItemView(View parent,int position) {
            RadioButton radioButton = new RadioButton(parent.getContext());
            radioButton.setButtonDrawable(0);
//            radioButton.setTextColor(R.drawable.type_tag_selector);
//            radioButton.setTextColor(parent.getContext().getResources().getColor(R.color.iv_home_category_tag_colors));
             ColorStateList colorStateList = parent.getContext().getColorStateList(R.color.iv_home_category_tag_colors);
//            radioButton.setBackground(parent.getContext().getDrawable(R.drawable.type_tag_selector));
            radioButton.setTextColor(colorStateList);
            return radioButton;
        }

        @Override
        public void bindData(RadioButton itemView,int position) {
            TypeTag typeTag = datas.get(position);
            itemView.setText(typeTag.getName());
        }

        @Override
        public int getItemCount() {
            return datas == null ? 0 : datas.size();
        }

        @Override
        public void onCheckChanged(int position) {
            checkChangedListener.onCheckChanged(datas.get(position));
        }


    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        Log.i(TAG, "B onCreate: ");
        Log.i(TAG, "B onCreate: taskId: " + getTaskId());
        View viewById = findViewById(R.id.btn_finish);
        recyclerView = findViewById(R.id.listview);
        textview = findViewById(R.id.textview);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewActivity.this,"hello",Toast.LENGTH_SHORT).show();
            }
        });
        initText();
        //初始化适配器（adapter）
        adapter = new RecyclerDomeAdapter(this, list);
        adapter.notifyDataSetChanged();
//        View header = LayoutInflater.from(this).inflate(R.layout.header_item,null,false);
//        List<List<TypeTag>> categoryList = new ArrayList<>();
//        CategoryFilterView<TypeTag, List<List<TypeTag>>> header = new CategoryFilterView<>(this);
//        for (int i = 0; i < 3; i++) {
//            List<TypeTag> tagList = new ArrayList<>();
//            for (int j = 0; j < 20; j++) {
//                TypeTag tag = new TypeTag();
//                tag.setId(i);
//                tag.setName("TAG-" + j);
//                tag.setType(2);
//                tagList.add(tag);
//            }
//            categoryList.add(tagList);
//        }
//        header.setCategoryList(categoryList);
            List<TypeTag> tagList = new ArrayList<>();
            for (int j = 0; j < 40; j++) {
                TypeTag tag = new TypeTag();
                tag.setId(j);
                tag.setName("TAG-" + j);
                tag.setType(2);
                tagList.add(tag);
            }
        CategoryTagListView tagListView = new CategoryTagListView(this);
        TagListAdapter mAdapter = new TagListAdapter();
        mAdapter.setDatas(tagList);
        tagListView.setAdapter(mAdapter);
        mAdapter.setCheckChangedListener(new CategoryTagListView.Adapter.CheckChangedListener<TypeTag>() {
            @Override
            public void onCheckChanged(TypeTag typeTag) {
                Log.i(TAG, "onCheckChanged: typeTag: " + typeTag);
            }
        });
        mAdapter.notifyDataSetChanged();
        adapter.setHeaderView(tagListView);
        //配置Layoutmanger
        /**
         * recyclerView的花样全在这里，横向、纵向、各种花式布局，都是从这里来的
         * 我下面，就是做简单的纵向列表，根据自己需要选择不同layoutManager
         * 具体有哪些layoutManager
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//加上这句，就可以设置方向
        recyclerView.setLayoutManager(layoutManager);
//        HeaderItemDecoration headerItemDecoration = new HeaderItemDecoration();
//        headerItemDecoration.setOnLayoutCallback(new HeaderItemDecoration.OnLayoutCallback() {
//            @Override
//            public void callback(boolean show) {
//                if (show) {
//                    textview.setVisibility(View.VISIBLE);
//
//                } else {
//                    textview.setVisibility(View.GONE);
//                }
//            }
//        });
//        recyclerView.addItemDecoration(headerItemDecoration);
        //配置adapter
        recyclerView.setAdapter(adapter);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initText() {
        for (int i = 0; i < 2; i++) {
            list.add("zhang");
            list.add("li");
            list.add("wu");
            list.add("liu");
            list.add("sun");
            list.add("zheng");
            list.add("ming");
            list.add("ming");
            list.add("ming");
            list.add("ming");
            list.add("ming");
            list.add("ming");
            list.add("ming");
            list.add("ming");
            list.add("qi");
            list.add("ji");
            list.add("gao");
            list.add("hao");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("age", 11);
        Log.i(TAG, "B onSaveInstanceState: ");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int age = savedInstanceState.getInt("age");
        Log.i(TAG, "B onRestoreInstanceState: age: " + age);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "B onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "B onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "B onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "B onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "B onStop: ");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "B onCreate: ");
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "B onConfigurationChanged: width:" + newConfig.screenWidthDp);
    }


    class RecyclerDomeAdapter extends RecyclerView.Adapter<RecyclerDomeAdapter.MyHolder> {
        public static final int TYPE_HEADER = 0;
        public static final int TYPE_NORMAL = 1;
        Context context;
        List<String> list;
        private View mHeaderView;

        public void setHeaderView(View headerView) {
            mHeaderView = headerView;
            notifyItemInserted(0);
        }

        @Override
        public int getItemViewType(int position) {
            if (mHeaderView == null) return TYPE_NORMAL;
            if (position == 0) return TYPE_HEADER;
            return TYPE_NORMAL;
        }

        public RecyclerDomeAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        public void updata(List<String> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        /**
         * onCreateViewHolder 为注册 item
         * 这个方法是用来创建viewholder的
         * 就是引入xml传送给viewholder的
         * ViewGroup parent：可以简单理解为item的根ViewGroup，item的子控件加载在其中
         * int viewType：item的类型，可以根据viewType来创建不同的ViewHolder，来加载不同的类型的item
         */
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_item,parent,false);
//            MyHolder myHolder = new MyHolder(view);

            if (mHeaderView != null && viewType == TYPE_HEADER) return new MyHolder(mHeaderView);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_item, parent, false);
            return new MyHolder(view);
        }

        /**
         * 这里，是操作item的地方
         * onBindViewHolder 给Item元素赋值
         */
        @Override
        public void onBindViewHolder(@NonNull MyHolder viewHolder, int position) {
//            String s = list.get(position);
//            holder.textView.setText(s);
//            //对控件进行监听
//            holder.textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
//
//            //对整个item进行监听
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });

            if (getItemViewType(position) == TYPE_HEADER) return;

            final int pos = getRealPosition(viewHolder);
            final String data = list.get(pos);
            if (viewHolder instanceof MyHolder) {
                viewHolder.textView.setText(data);
//                if(mListener == null) return;
//                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mListener.onItemClick(pos, data);
//                    }
//                });
            }
        }

        public int getRealPosition(RecyclerView.ViewHolder holder) {
            int position = holder.getLayoutPosition();
            return mHeaderView == null ? position : position - 1;
        }

        /**
         * getItemCount 返回Item个数
         */
        @Override
        public int getItemCount() {
            return list.size();
        }

        /**
         * 这个ViewHolder是用来初始化控件的
         */

        class MyHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public MyHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.text);
            }
        }
    }

}