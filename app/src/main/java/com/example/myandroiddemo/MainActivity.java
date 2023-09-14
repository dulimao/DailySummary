package com.example.myandroiddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gxa.car.splitscreen.view.ac.NewActivity;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;


public class MainActivity extends FragmentActivity {//C9EC96C48945AA08BCBF6D2415F87AAB C9EC96C48945AA08BCBF6D2415F87AAB
    private List<Integer> colorsRes = Arrays.asList(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorAccent, R.color.color_red, R.color.bg_lunch_selected, R.color.bg_supper_selected);

    private static final String TAG = "ActivityTest";
    private static final int NUM = 1024;
    private static Person person;
    List<byte[]> list;
    String url = "https://iface2.iqiyi.com/control/3.0/init_login?app_vm=1&app_s=1dddca34440ef1a4afdbbd88ed31c48c&usr_new=0&usr_type=-1&ir_id=&ir_v=2.3.3&init_crash=0&init_sid=wksrpeiwb5s93t93&init_type=0&init_first_ts=&push_id_qiyi=&init_push_id=&dev_model=AGM3-W09HN&dev_manufacturer=HONOR&scrn_size=7.08&dev_break=false&msg_ctl=2&msg_t=2,3,4,5,6,7,8&msg_s=0,5,9,12,15,25&install_first_ts=1678328439319&up_ts=1678330379218&spt_1080p=1&spt_h265=1&init_sub_type=&start_page=0&push_sw=1&fkey=&sdk_v=1556&abiFilter=2&sdk_build=1556.0.12120&cpu_platform=MT6769V%2FCT&android_api_level=29&qyidv2=57484B94747F8AC95D79C491817123C3&oris=&referer=&hdfit=%7B%22ua%22%3A%22AGM3-W09HN%22%2C%22h%22%3A1200%2C%22w%22%3A1920%2C%22h_dpi%22%3A320%2C%22w_dpi%22%3A320%2C%22density%22%3A2%2C%22app_v%22%3A%223.1.2%22%2C%22dev_os%22%3A%2210%22%2C%22platform_id%22%3A%221108%22%2C%22is_pkg_pad%22%3Afalse%7D&iqid=&identifying=70ad7b3fd5874ae982636c5759a380561105&qyctxv=1&qyctx=bf1fe71a024681f8ecb9d1ea8b8c06a89a0783855b39c5f5eec5c11b51487e45&qylct=&oaid=6f5d42a9-d645-4a64-9afd-b86bd8f3eae1&device_name=AGM3-W09HN&device_type=AGM3-W09HN&ptid=11025132630000000000&verifyPhone=1&dfp=14431abb50da734fa395140b12453b8862a851c04754490e6538afa6cec08f8713&dev_brand=HONOR&isdcdu=0&ext=";
    String uri = "http://iface2.iqiyi.com:443/control/3.0/init_login?app_vm=1&app_s=1dddca34440ef1a4afdbbd88ed31c48c&usr_new=0&usr_type=-1&ir_id=&ir_v=2.3.3&init_crash=0&init_sid=wksrpeiwb5s93t93&init_type=0&init_first_ts=&push_id_qiyi=&init_push_id=&dev_model=AGM3-W09HN&dev_manufacturer=HONOR&scrn_size=7.08&dev_break=false&msg_ctl=2&msg_t=2,3,4,5,6,7,8&msg_s=0,5,9,12,15,25&install_first_ts=1678328439319&up_ts=1678330379218&spt_1080p=1&spt_h265=1&init_sub_type=&start_page=0&push_sw=1&fkey=&sdk_v=1556&abiFilter=2&sdk_build=1556.0.12120&cpu_platform=MT6769V/CT&android_api_level=29&qyidv2=57484B94747F8AC95D79C491817123C3&oris=&referer=&hdfit=%7B%22ua%22:%22AGM3-W09HN%22,%22h%22:1200,%22w%22:1920,%22h_dpi%22:320,%22w_dpi%22:320,%22density%22:2,%22app_v%22:%223.1.2%22,%22dev_os%22:%2210%22,%22platform_id%22:%221108%22,%22is_pkg_pad%22:false%7D&iqid=&identifying=70ad7b3fd5874ae982636c5759a380561105&qyctxv=1&qyctx=bf1fe71a024681f8ecb9d1ea8b8c06a89a0783855b39c5f5eec5c11b51487e45&qylct=&oaid=6f5d42a9-d645-4a64-9afd-b86bd8f3eae1&device_name=AGM3-W09HN&device_type=AGM3-W09HN&ptid=11025132630000000000&verifyPhone=1&dfp=14431abb50da734fa395140b12453b8862a851c04754490e6538afa6cec08f8713&dev_brand=HONOR&isdcdu=0&ext=";
    private TextView mSignTV;
    private TextView mGoBtn;
    private ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "A onCreate: ");

        Log.i(TAG, "A onCreate: taskId: " + getTaskId());
        setContentView(R.layout.activity_main);
        mSignTV = findViewById(R.id.signTV);
        mGoBtn = findViewById(R.id.toNewActivityBtn);
        mGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        getAPKSign("com.qiyi.video.iv");
//        ImageView imageView = findViewById(R.id.imageview);
//
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.back);
////        mColArr = new int[bitmap.getWidth()][bitmap.getHeight()];
//        for (int i = 0; i < bitmap.getWidth(); i++) {
//            for (int j = 0; j < 2; j++) {
////                mColArr[i][j] = bitmap.getPixel(i, j);
////                Log.i(TAG, "onCreate: " + bitmap.getPixel(i, j));;
//                printColor("color: ", bitmap.getPixel(i, j));
//            }
//        }
//        imageView.setImageBitmap(changeAlpha(bitmap));


//        File file = new File(getCacheDir() + "/back.jpg");
//        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//        Log.i(TAG, "onCreate: bitmap: " + bitmap.hashCode());
//        imageView.setImageBitmap(bitmap);
//        Log.d(TAG, "run: config: " + bitmap.getConfig().name());
//        Log.d(TAG, " bitmap width = " + bitmap.getWidth() + " bitmap height = " + bitmap.getHeight());
//        Log.d(TAG, " memory usage = " + bitmap.getAllocationByteCount());/**bitmap.getByteCount()方法不再使用*/

//        testA();
//        UrlConvertor.convertUrl(url);

        viewPager = findViewById(R.id.vp_vertical_stack);
//        viewPager.setClipChildren(false);
//        viewPager.setClipToPadding(false);
//        viewPager.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        viewPager.setOffscreenPageLimit(6);

        viewPager.setPageTransformer( new VerticalStackPageTransformerWithRotation());
        viewPager.setAdapter(new ListAdapter(MainActivity.this));
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        Log.i(TAG, "onGenericMotionEvent: ");
        return super.onGenericMotionEvent(event);
    }

    /**
     * 将颜色从int 拆分成argb,并打印出来
     * @param msg
     * @param color
     */
    private void printColor(String msg, int color) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        Log.d(TAG,msg + "----a:" + a + ", r:" + r + ", g:" + g + ", b:" + b);
    }
    public Bitmap changeAlpha(Bitmap bitmap){
        int w = bitmap.getWidth();
        int h = bitmap .getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);

        int ratio = w >  h ?  h * 32768 / w : w * 32768 /  h;
        int r,g,b,a,color;

        int[] oldPx = new int[w * h];
        int[] newPx = new int[w * h];

        float Size =0.5f;
        int cx = w >> 1;
        int cy = h >> 1;
        int max = cx * cx + cy * cy;
        int min = (int)(max * (2 - Size));
        int diff = max - min;
        bitmap.getPixels(oldPx, 0, w, 0, 0, w, h);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {

                color = oldPx[x * h + y];
                r = Color.red(color);
                g = Color.green(color);
                b = Color.blue(color);
                a = Color.alpha(color);

                int dx = cx - x;
                int dy = cy - y;
                if (w > h){
                    dx = (dx * ratio) >> 15;
                }
                else{
                    dy = (dy * ratio) >> 15;
                }

                int distSq = dx * dx + dy * dy;
                float v =  ((float)distSq / diff) * 255;
                a = (int)(a+(v));
                a = (a > 255 ? 255 : (a < 0 ? 0 : a));
                newPx[x * h + y] = Color.argb(a,r, g, b);
            }
        }
        result.setPixels(newPx, 0, w, 0, 0, w, h);
        return result;
    }


    private <T> List<T> deepCopyList(List<T> src) {
        List<T> dest = new ArrayList<>();
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            dest = (List<T>) in.readObject();
        } catch (Exception e) {
        }
        return dest;
    }

    private void testA() {
        Person person1 = new Person("张三",22);
        person = person1;
        Log.i(TAG, "testA: person1:" + person1);
        Log.i(TAG, "testA: person:" + person);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "A onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "A onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "A onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "A onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "A onStop: ");
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        person = null;

        Log.i(TAG, "A onCreate: ");
    }

    private String md5;
    private X509Certificate x509Cert;

    private String getMD5(String str) {
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] bytes = str.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            byte[] digest = messageDigest.digest();
            char[] cArr2 = new char[digest.length * 2];
            int i = 0;
            for (byte b : digest) {
                int i2 = i + 1;
                cArr2[i] = cArr[(b >>> 4) & 15];
                i = i2 + 1;
                cArr2[i2] = cArr[b & 15];
            }
            return new String(cArr2);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getSign(Context context) {
        try {
            String charsString = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures[0].toCharsString();
            System.out.println(charsString);
            return getMD5(charsString);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void getAPKSign(String str) {
        this.md5 = null;
        this.x509Cert = null;
        try {
            PackageManager packageManager = getPackageManager();
            Signature signature = packageManager.getPackageInfo(str, 64).signatures[0];
            this.md5 = getMD5(signature.toCharsString());
            Log.i(TAG, "getAPKSign: md5: " + md5);
            mSignTV.setText(md5);
            try {
                this.x509Cert = (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(signature.toByteArray()));
            } catch (CertificateException e) {
                e.printStackTrace();
            }
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    class Person {
        private String name;
        private int age;
        private List<byte[]> list;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
//            list = new ArrayList<>();
//            for (int i = 0; i < 100; i++) {
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                list.add(new byte[1024 * 1024]);
//            }
        }
    }

}