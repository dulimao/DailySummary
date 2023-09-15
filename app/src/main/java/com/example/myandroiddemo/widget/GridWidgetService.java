package com.example.myandroiddemo.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.myandroiddemo.R;

import java.util.ArrayList;
import java.util.HashMap;

public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this,intent);
    }

    private class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private static final String TAG = "GridRemoteViewsFactory";

        private Context mContext;
        private int mAppWidgetId;

        private String IMAGE_ITEM = "imgage_item";
        private String TEXT_ITEM = "text_item";
        private ArrayList<HashMap<String, Object>> data ;

        private String[] arrText = new String[]{
                "Picture 1", "Picture 2", "Picture 3",
                "Picture 4", "Picture 5", "Picture 6",
                "Picture 7", "Picture 8", "Picture 9"
        };
        private int[] arrImages=new int[]{
                R.drawable.aa, R.drawable.aa, R.drawable.aa,
                R.drawable.aa, R.drawable.aa, R.drawable.aa,
                R.drawable.aa, R.drawable.aa, R.drawable.aa
        };

        public GridRemoteViewsFactory(Context context,Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
            Log.i(TAG, "GridRemoteViewsFactory: mAppWidgetId: " + mAppWidgetId);
        }

        @Override
        public void onCreate() {
            data = new ArrayList<HashMap<String,Object>>();

            for (int i = 0; i < 9; i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put(IMAGE_ITEM, arrImages[i]);
                map.put(TEXT_ITEM, arrText[i]);
                data.add(map);
            }
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            data.clear();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            HashMap<String,Object> maps;
            Log.d(TAG, "getViewAt: position: " + position);
            //item view对应的RemoteViews
            RemoteViews rv = new RemoteViews(mContext.getPackageName(),R.layout.gridview_item_layout);
            //设置第position为的“视图”的数据
            maps = data.get(position);
            rv.setImageViewResource(R.id.itemImage, ((Integer) maps.get(IMAGE_ITEM)).intValue());
            rv.setTextViewText(R.id.itemText,(String) maps.get(TEXT_ITEM));

            //设置第position位的“视图”对应的响应事件
            Intent fillInIntent = new Intent();
            fillInIntent.putExtra(GridWidgetProvider.COLLECTION_VIEW_EXTRA,position);
            rv.setOnClickFillInIntent(R.id.itemLayout,fillInIntent);

            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
