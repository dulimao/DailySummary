package com.example.myandroiddemo.view.viewpager2;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myandroiddemo.R;
import com.gala.video.component.group.Grid;
import com.gala.video.component.layout.BlockLayout;
import com.gala.video.component.layout.ListLayout;
import com.gala.video.component.widget.BlocksView;
import com.gala.video.component.widget.LayoutManager;

import java.util.ArrayList;
import java.util.List;

public class ListFragment2 extends Fragment {

    private BlocksView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blocklist,container,false);
        recyclerView = view.findViewById(R.id.blocksview);
        recyclerView.setOrientation(LayoutManager.Orientation.VERTICAL);
        recyclerView.setAdapter(new BlocksAdapter());
        List<BlockLayout> layouts = new ArrayList<BlockLayout>();

        ListLayout layout = new ListLayout();
        Grid.NumRowsController numRowsController = new Grid.NumRowsController();
        numRowsController.add(6, new Grid.CountCallback() { public int count() { return 40; } });
        layout.setItemCount(20);

        layout.setVerticalMargin(10);
        layout.setHorizontalMargin(10);
//        layout.setBackgroundColor(Color.GREEN);

        layouts.add(layout);

        recyclerView.getLayoutManager().setLayouts(layouts);
        return view;
    }

    class BlocksAdapter extends BlocksView.Adapter<BlocksViewHolder> {

        @Override
        public void onBindViewHolder(BlocksViewHolder blocksViewHolder, int position) {
            blocksViewHolder.textView.setText("AAAAAAAAAAAAAAA - " + position);
        }

        @Override
        public BlocksViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
            TextView textView = new TextView(viewGroup.getContext());
            textView.setLayoutParams(new BlocksView.LayoutParams(BlocksView.LayoutParams.MATCH_PARENT,200));
            textView.setGravity(Gravity.CENTER);
            return new BlocksViewHolder(textView);
        }

        @Override
        public int getCount() {
            return 20;
        }
    }


    class BlocksViewHolder extends BlocksView.ViewHolder {
        private TextView textView;
        public BlocksViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,200));
            textView.setGravity(Gravity.CENTER);
            return new MyViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.textView.setText("AAAAAAAAAAAAAAA - " + position);
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;

        }
    }
}
