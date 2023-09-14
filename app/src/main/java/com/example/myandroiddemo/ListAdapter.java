package com.example.myandroiddemo;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyHolder> {
    private List<Integer> colorsRes = Arrays.asList(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorAccent, R.color.color_red, R.color.bg_lunch_selected, R.color.bg_supper_selected);

    private Context context;
    public ListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vp_test, parent, false);

        return new MyHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        TextView itemView = ((TextView)holder.itemView);
        ((TextView) itemView).setText("第" + (position + 1) + "页面");
        int color = context.getResources().getColor(colorsRes.get(position));
        itemView.setBackgroundColor(color);
//        GradientDrawable drawable = (GradientDrawable) itemView.getBackground();
//        if (drawable == null) {
//            drawable = new GradientDrawable();
//            itemView.setBackground(drawable);
//        }
//        drawable.setCornerRadius(DisplayUtil.dp2px(5));
//        drawable.setStroke(DisplayUtil.dp2px(5), context.getResources().getColor(R.color.color_light_grey));
//        drawable.setColor(color);
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
