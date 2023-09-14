package com.gxa.car.splitscreen.view.ac;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoryFilterView<E extends TypeTag,T extends List<List<E>>> extends LinearLayout {
    private static final String TAG = "CategoryFilterView";

    private List<List<E>> categoryList = new ArrayList<>();
    public CategoryFilterView(Context context) {
        super(context);
        init();
    }

    public CategoryFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CategoryFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setCategoryList(List<List<E>> data) {
        this.categoryList = data;
        for (int i = 0; i < categoryList.size(); i++) {
            CategoryTagListView categoryGroup = new CategoryTagListView(getContext());
//            categoryGroup.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,200));
            List<E> tags = categoryList.get(i);
            for (int j = 0; j < tags.size(); j++) {
                E tag = tags.get(i);
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setId(j);
                radioButton.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,100));
                radioButton.setText(tag.getName());
//                categoryGroup.addTag(radioButton);
            }
//            categoryGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                    Log.i(TAG, "onCheckedChanged: i: " + i + " " + radioGroup.getCheckedRadioButtonId());
//                }
//            });
            addView(categoryGroup);
        }
    }

    private void init() {
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        setBackgroundColor(Color.BLUE);
        setOrientation(VERTICAL);

//        for (int i = 0; i < 3; i++) {
//            List<T> tagList = new ArrayList<>();
//            for (int j = 0; j < 20; j++) {
//                T tag = "TAG - " + j;
//                tagList.add(tag);
//            }
//            categoryList.add(tagList);
//        }

    }
}
