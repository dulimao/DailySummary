package com.gxa.car.splitscreen.view.ac;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class HeaderItemDecoration  extends RecyclerView.ItemDecoration {

    OnLayoutCallback onLayoutCallback;
    public void setOnLayoutCallback(OnLayoutCallback onLayoutCallback) {
        this.onLayoutCallback = onLayoutCallback;
    }
    interface OnLayoutCallback {
        void callback(boolean show);
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int topChildPosition = parent.getChildAdapterPosition(parent.getChildAt(0));
        this.onLayoutCallback.callback(topChildPosition > 0);
        if(topChildPosition > 0) {
//            Log.i("TAG", "draw header");
//            MyVeiw textView = new MyVeiw(parent.getContext());
//            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100));
////            textView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    Toast.makeText(parent.getContext(),"hello",Toast.LENGTH_SHORT).show();
////                }
////            });
//
//            textView.setText("bbdasdasd");
//            textView.setBackgroundColor(Color.BLACK);
//            textView.layout(0, 0, parent.getWidth(), 100);
//            drawText(c, textView);
        }
    }

    private void drawText(Canvas c, View header) {
        c.save();
        c.translate(0, 0);
        header.draw(c);
        c.restore();
    }
}
