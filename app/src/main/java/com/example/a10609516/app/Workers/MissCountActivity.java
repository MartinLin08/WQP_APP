package com.example.a10609516.app.Workers;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10609516.app.Basic.MenuActivity;
import com.example.a10609516.app.Basic.QRCodeActivity;
import com.example.a10609516.app.Basic.SignatureActivity;
import com.example.a10609516.app.Basic.VersionActivity;
import com.example.a10609516.app.Clerk.QuotationActivity;
import com.example.a10609516.app.DepartmentAndDIY.CorrectActivity;
import com.example.a10609516.app.DepartmentAndDIY.CustomerActivity;
import com.example.a10609516.app.DepartmentAndDIY.PictureActivity;
import com.example.a10609516.app.DepartmentAndDIY.RecordActivity;
import com.example.a10609516.app.DepartmentAndDIY.UploadActivity;
import com.example.a10609516.app.Manager.InventoryActivity;
import com.example.a10609516.app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MissCountActivity extends AppCompatActivity {

    private LinearLayout miss_llt;

    /**
     * 創建Menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //接收LoginActivity傳過來的值
        SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
        String user_id_data = user_id.getString("ID", "");
        SharedPreferences department_id = getSharedPreferences("department_id", MODE_PRIVATE);
        String department_id_data = department_id.getString("D_ID", "");
        if ((user_id_data.toString().equals("09706013")) || user_id_data.toString().equals("09908023") || user_id_data.toString().equals("10010039")
                || user_id_data.toString().equals("10012043") || user_id_data.toString().equals("10101046") || user_id_data.toString().equals("10405235")) {
            getMenuInflater().inflate(R.menu.workers_manager_menu, menu);
            return true;
        }else if (department_id_data.toString().equals("2100")) {
            getMenuInflater().inflate(R.menu.clerk_menu, menu);
            return true;
        } else if (department_id_data.toString().equals("2200")) {
            getMenuInflater().inflate(R.menu.diy_menu, menu);
            return true;
        } else if (department_id_data.toString().equals("5200")) {
            getMenuInflater().inflate(R.menu.workers_menu, menu);
            return true;
        } else {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
    }

    /**
     * 進入Menu各個頁面
     *
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_item:
                Intent intent = new Intent(MissCountActivity.this, MenuActivity.class);
                startActivity(intent);
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                finish();
                break; //返回首頁
            case R.id.schedule_item:
                Intent intent7 = new Intent(MissCountActivity.this, ScheduleActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "行程資訊", Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent1 = new Intent(MissCountActivity.this, CalendarActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Intent intent13 = new Intent(MissCountActivity.this, SearchActivity.class);
                startActivity(intent13);
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; //進入查詢派工資料
            /*case R.id.signature_item:
                Intent intent2 = new Intent(MissCountActivity.this, SignatureActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "客戶電子簽名", Toast.LENGTH_SHORT).show();
                break; //進入客戶電子簽名頁面
            case R.id.record_item:
                Intent intent8 = new Intent(MissCountActivity.this, RecordActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "上傳日報紀錄", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報紀錄頁面*/
            case R.id.picture_item:
                Intent intent3 = new Intent(MissCountActivity.this, PictureActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳頁面
            case R.id.customer_item:
                Intent intent4 = new Intent(MissCountActivity.this, CustomerActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢頁面
            /*case R.id.upload_item:
                Intent intent5 = new Intent(MissCountActivity.this, UploadActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent6 = new Intent(MissCountActivity.this, CorrectActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面*/
            case R.id.about_item:
                Intent intent9 = new Intent(MissCountActivity.this, VersionActivity.class);
                startActivity(intent9);
                Toast.makeText(this, "版本資訊", Toast.LENGTH_SHORT).show();
                break; //進入版本資訊頁面
            case R.id.QRCode_item:
                Intent intent10 = new Intent(MissCountActivity.this, QRCodeActivity.class);
                startActivity(intent10);
                Toast.makeText(this, "QRCode", Toast.LENGTH_SHORT).show();
                break; //進入QRCode頁面
            case R.id.quotation_item:
                Intent intent11 = new Intent(MissCountActivity.this, QuotationActivity.class);
                startActivity(intent11);
                Toast.makeText(this, "報價單審核", Toast.LENGTH_SHORT).show();
                break; //進入報價單審核頁面
            case R.id.points_item:
                Intent intent12 = new Intent(MissCountActivity.this, PointsActivity.class);
                startActivity(intent12);
                Toast.makeText(this, "我的點數", Toast.LENGTH_SHORT).show();
                break; //進入查詢工務點數頁面
            case R.id.miss_item:
                Toast.makeText(this, "未回單數量", Toast.LENGTH_SHORT).show();
                break; //顯示未回單數量
            case R.id.inventory_item:
                Intent intent15 = new Intent(MissCountActivity.this, InventoryActivity.class);
                startActivity(intent15);
                Toast.makeText(this, "倉庫盤點", Toast.LENGTH_SHORT).show();
                break; //進入倉庫盤點管理頁面
            case R.id.map_item:
                Intent intent17 = new Intent(MissCountActivity.this, GPSActivity.class);
                startActivity(intent17);
                Toast.makeText(this, "工務打卡GPS", Toast.LENGTH_SHORT).show();
                break; //進入GPS地圖頁面
            case R.id.eng_points_item:
                Intent intent18 = new Intent(MissCountActivity.this, EngPointsActivity.class);
                startActivity(intent18);
                Toast.makeText(this, "工務點數明細", Toast.LENGTH_SHORT).show();
                break; //進入工務點數明細頁面
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miss_count);
        //動態取得 View 物件
        InItFunction();
        //與OKHttp連線(GroupMissCount)工務未回報派工數量
        sendRequestWithOkHttp();
        //確認是否有最新版本，進行更新
        CheckFirebaseVersion();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        miss_llt = (LinearLayout) findViewById(R.id.miss_llt);
    }

    /**
     * 與OkHttp建立連線
     */
    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.i("MissCountActivity", user_id_data);
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .build();
                    Log.i("MissCountActivity", user_id_data);
                    Request request = new Request.Builder()
                            .url("http://220.133.80.146/WQP/GroupMissCount.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("MissCountActivity", responseData);
                    parseJSONWithJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 獲得JSON字串並解析成String字串
     *
     * @param jsonData
     */
    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String eng_local = jsonObject.getString("地區");
                String eng_name = jsonObject.getString("工務");
                String miss_count = jsonObject.getString("未回單數量");

                Log.e("MissCountActivity", eng_name);

                //JSONArray加入SearchData資料
                ArrayList<String> JArrayList = new ArrayList<String>();
                JArrayList.add(eng_local);
                JArrayList.add(eng_name);
                JArrayList.add(miss_count);

                //HandlerMessage更新UI
                Bundle b = new Bundle();
                b.putStringArrayList("JSON_data", JArrayList);
                Message msg = mHandler.obtainMessage();
                msg.setData(b);
                msg.what = 1;
                msg.sendToTarget();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新UI
     */
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    LinearLayout small_llt = new LinearLayout(MissCountActivity.this);
                    small_llt.setOrientation(LinearLayout.HORIZONTAL);

                    Bundle jb = msg.getData();
                    ArrayList<String> JArrayList = new ArrayList<String>();
                    //int i = b.getStringArrayList("JSON_data").size();
                    JArrayList = jb.getStringArrayList("JSON_data");

                    //顯示每筆LinearLayout的地區
                    TextView dynamically_local;
                    dynamically_local = new TextView(MissCountActivity.this);
                    dynamically_local.setText(JArrayList.get(0).substring(0, 2));
                    dynamically_local.setGravity(Gravity.CENTER);
                    //dynamically_local.setWidth(50);
                    dynamically_local.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

                    //顯示每筆LinearLayout的工務
                    TextView dynamically_name;
                    dynamically_name = new TextView(MissCountActivity.this);
                    dynamically_name.setText(JArrayList.get(1));
                    dynamically_name.setGravity(Gravity.CENTER);
                    //dynamically_name.setWidth(50);
                    dynamically_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

                    //顯示每筆LinearLayout的數量
                    TextView dynamically_count;
                    dynamically_count = new TextView(MissCountActivity.this);
                    dynamically_count.setText(JArrayList.get(2));
                    dynamically_count.setGravity(Gravity.CENTER);
                    dynamically_count.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

                    //設置每筆TableLayout的分隔線
                    TextView dynamically_txt = new TextView(MissCountActivity.this);
                    dynamically_txt.setBackgroundColor(Color.rgb(220, 220, 220));

                    LinearLayout.LayoutParams small_pm1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f);
                    LinearLayout.LayoutParams small_pm2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                    small_llt.addView(dynamically_local, small_pm1);
                    small_llt.addView(dynamically_name, small_pm2);
                    small_llt.addView(dynamically_count, small_pm2);

                    miss_llt.addView(dynamically_txt, LinearLayout.LayoutParams.MATCH_PARENT, 3);
                    miss_llt.addView(small_llt);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 確認是否有最新版本，進行更新
     */
    private void CheckFirebaseVersion() {
        SharedPreferences fb_version = getSharedPreferences("fb_version", MODE_PRIVATE);
        final String version = fb_version.getString("FB_VER", "");
        Log.e("MissCountActivity", version);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("WQP");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //Log.d("現在在根結點上的資料是:", "Value is: " + value);
                Map<String, String> map = (Map) dataSnapshot.getValue();
                String data = map.toString().substring(9, 12);
                Log.e("MissCountActivity", "已讀取到值:" + data);
                if (version.equals(data)) {
                } else {
                    new AlertDialog.Builder(MissCountActivity.this)
                            .setTitle("更新通知")
                            .setMessage("檢測到軟體重大更新\n請更新最新版本")
                            .setIcon(R.drawable.bwt_icon)
                            .setNegativeButton("確定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            new Thread() {
                                                @Override
                                                public void run() {
                                                    super.run();
                                                    MissCountActivity.this.Update();
                                                }
                                            }.start();
                                        }
                                    }).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("MissCountActivity", "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * 下載新版本APK
     */
    public void Update() {
        try {
            URL url = new URL("http://192.168.0.201/wqp_1.9.apk");
            //URL url = new URL("http://m.wqp-water.com.tw/wqp_1.9.apk");
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            //c.setRequestMethod("GET");
            //c.setDoOutput(true);
            c.connect();

            String PATH = Environment.getExternalStorageDirectory() + "/Download/";
            //String PATH2 = Environment.getExternalStorageDirectory().getPath() + "/Download/";
            //String PATH = System.getenv("SECONDARY_STORAGE") + "/Download/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "wqp_1.9.apk");
            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();//till here, it works fine - .apk is download to my sdcard in download file

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Download/" + "wqp_1.9.apk")), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            MissCountActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "開始安裝新版本", Toast.LENGTH_LONG).show();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("下載錯誤!", e.toString());
            MissCountActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "更新失敗!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}