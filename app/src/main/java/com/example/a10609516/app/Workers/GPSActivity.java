package com.example.a10609516.app.Workers;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.example.a10609516.app.Tools.DatePickerFragment;
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

public class GPSActivity extends AppCompatActivity {

    private ScrollView gps_scv;
    private LinearLayout gps_llt;
    private Button[] dynamically_btn;
    private Button search_btn, time_start_btn, time_end_btn, clean_start_btn, clean_end_btn;
    private EditText eng_edt;

    private String d_id;

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;

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
        d_id = department_id.getString("D_ID", "");
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
                Intent intent17 = new Intent(GPSActivity.this, MenuActivity.class);
                startActivity(intent17);
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                break;  //進入首頁
            case R.id.schedule_item:
                Intent intent7 = new Intent(GPSActivity.this, ScheduleActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "行程資訊", Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent = new Intent(GPSActivity.this, CalendarActivity.class);
                startActivity(intent);
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Intent intent1 = new Intent(GPSActivity.this, SearchActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; //進入查詢派工資料頁面
            /*case R.id.signature_item:
                Intent intent2 = new Intent(GPSActivity.this, SignatureActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "客戶電子簽名", Toast.LENGTH_SHORT).show();
                break; //進入客戶電子簽名頁面
            case R.id.record_item:
                Intent intent8 = new Intent(GPSActivity.this, RecordActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "上傳日報紀錄", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報紀錄頁面
            case R.id.picture_item:
                Intent intent3 = new Intent(GPSActivity.this, PictureActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳頁面
            case R.id.customer_item:
                Intent intent4 = new Intent(GPSActivity.this, CustomerActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢
            case R.id.upload_item:
                Intent intent5 = new Intent(GPSActivity.this, UploadActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent6 = new Intent(GPSActivity.this, CorrectActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面*/
            case R.id.about_item:
                Intent intent9 = new Intent(GPSActivity.this, VersionActivity.class);
                startActivity(intent9);
                Toast.makeText(this, "版本資訊", Toast.LENGTH_SHORT).show();
                break; //進入版本資訊頁面
            case R.id.QRCode_item:
                Intent intent10 = new Intent(GPSActivity.this, QRCodeActivity.class);
                startActivity(intent10);
                Toast.makeText(this, "QRCode", Toast.LENGTH_SHORT).show();
                break; //進入QRCode頁面
            case R.id.quotation_item:
                Intent intent11 = new Intent(GPSActivity.this, QuotationActivity.class);
                startActivity(intent11);
                Toast.makeText(this, "報價單審核", Toast.LENGTH_SHORT).show();
                break; //進入報價單審核頁面
            case R.id.points_item:
                Intent intent12 = new Intent(GPSActivity.this, PointsActivity.class);
                startActivity(intent12);
                Toast.makeText(this, "我的點數", Toast.LENGTH_SHORT).show();
                break; //進入查詢工務點數頁面
            case R.id.miss_item:
                Intent intent13 = new Intent(GPSActivity.this, MissCountActivity.class);
                startActivity(intent13);
                Toast.makeText(this, "未回單數量", Toast.LENGTH_SHORT).show();
                break; //進入工務未回單數量頁面
            case R.id.inventory_item:
                Intent intent18 = new Intent(GPSActivity.this, InventoryActivity.class);
                startActivity(intent18);
                Toast.makeText(this, "倉庫盤點", Toast.LENGTH_SHORT).show();
                break; //進入倉庫盤點頁面
            /*case R.id.purchase_item:
                Intent intent15 = new Intent(GPSActivity.this, QRCodeActivity.class);
                startActivity(intent15);
                Toast.makeText(this, "倉庫進貨", Toast.LENGTH_SHORT).show();
                break; //進入倉庫進貨管理頁面
            case R.id.return_item:
                Intent intent16 = new Intent(GPSActivity.this, QRCodeActivity.class);
                startActivity(intent16);
                Toast.makeText(this, "倉庫調撥", Toast.LENGTH_SHORT).show();
                break; //進入倉庫調撥管理頁面*/
            case R.id.map_item:
                Toast.makeText(this, "工務打卡GPS", Toast.LENGTH_SHORT).show();
                break; //顯示GPS打卡查詢
            case R.id.eng_points_item:
                Intent intent19 = new Intent(GPSActivity.this, EngPointsActivity.class);
                startActivity(intent19);
                Toast.makeText(this, "工務點數明細", Toast.LENGTH_SHORT).show();
                break; //進入工務點數明細頁面
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        //動態取得 View 物件
        InItFunction();
        //確認是否有最新版本，進行更新
        CheckFirebaseVersion();
        //請求開啟權限
        UsesPermission();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        gps_scv = (ScrollView) findViewById(R.id.gps_scv);
        gps_llt = (LinearLayout) findViewById(R.id.gps_llt);
        search_btn = (Button) findViewById(R.id.search_btn);
        time_start_btn = (Button) findViewById(R.id.start_btn);
        time_end_btn = (Button) findViewById(R.id.end_btn);
        clean_start_btn = (Button) findViewById(R.id.clean_btn1);
        clean_end_btn = (Button) findViewById(R.id.clean_btn2);
        eng_edt = (EditText) findViewById(R.id.eng_edt);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps_llt.removeAllViews();
                //取得工務打卡的GPS位置
                sendRequestWithOkHttpForGPS();
                gps_llt.setVisibility(View.VISIBLE);
            }
        });

        //Clean_Start_Button.setOnClickListener監聽器  //清空time_start_btn內的文字
        clean_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_start_btn.setText("");
            }
        });//end setOnItemClickListener
        //Clean_End_Button.setOnClickListener監聽器  //清空time_end_btn內的文字
        clean_end_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_end_btn.setText("");
            }
        });//end setOnItemClickListener
    }

    /**
     * 與OkHttp建立連線 取得工務打卡的GPS位置
     */
    private void sendRequestWithOkHttpForGPS() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //獲取日期挑選器的數據
                String time_start = time_start_btn.getText().toString();
                String time_end = time_end_btn.getText().toString();
                String eng_name = eng_edt.getText().toString();
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("START_DATE", time_start)
                            .add("END_DATE", time_end)
                            .add("ENG_NAME", eng_name)
                            .build();
                    Log.e("GPSActivity1", time_start);
                    Log.e("GPSActivity1", time_end);
                    Log.e("GPSActivity1", eng_name);
                    Request request = new Request.Builder()
                            .url("http://220.133.80.146/WQP/WorkerGPSHR.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("GPSActivity4", responseData);
                    parseJSONWithJSONObjectForGPS(responseData);
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
    private void parseJSONWithJSONObjectForGPS(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            dynamically_btn = new Button[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String eng_name = jsonObject.getString("工務姓名");
                String hr_date = jsonObject.getString("打卡日期");
                String hr_time = jsonObject.getString("打卡時間");
                String gps_location = jsonObject.getString("GPS位置");

                Log.e("GPSActivity5", eng_name);
                Log.e("GPSActivity6", hr_date);
                Log.e("GPSActivity7", hr_time);
                Log.e("GPSActivity8", gps_location);

                //JSONArray加入SearchData資料
                ArrayList<String> JArrayList = new ArrayList<String>();
                JArrayList.add(eng_name);
                JArrayList.add(hr_date);
                JArrayList.add(hr_time);
                JArrayList.add(gps_location);

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
                    LinearLayout small_llt0 = new LinearLayout(GPSActivity.this);
                    small_llt0.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout small_llt1 = new LinearLayout(GPSActivity.this);
                    small_llt1.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout small_llt2 = new LinearLayout(GPSActivity.this);
                    small_llt2.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout small_llt3 = new LinearLayout(GPSActivity.this);
                    small_llt3.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout small_llt4 = new LinearLayout(GPSActivity.this);
                    small_llt4.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout small_llt5 = new LinearLayout(GPSActivity.this);
                    small_llt5.setOrientation(LinearLayout.HORIZONTAL);
                    small_llt5.setGravity(Gravity.CENTER);
                    LinearLayout big_llt = new LinearLayout(GPSActivity.this);
                    big_llt.setOrientation(LinearLayout.VERTICAL);

                    Bundle jb = msg.getData();
                    ArrayList<String> JArrayList = new ArrayList<String>();
                    //int i = b.getStringArrayList("JSON_data").size();
                    JArrayList = jb.getStringArrayList("JSON_data");

                    //顯示每筆LinearLayout的工務
                    TextView dynamically_name;
                    dynamically_name = new TextView(GPSActivity.this);
                    dynamically_name.setText("工務姓名 : " + JArrayList.get(0));
                    dynamically_name.setGravity(Gravity.CENTER);
                    //dynamically_name.setWidth(50);
                    dynamically_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

                    //顯示每筆LinearLayout的打卡日期
                    TextView dynamically_date;
                    dynamically_date = new TextView(GPSActivity.this);
                    dynamically_date.setText("打卡日期 : " + JArrayList.get(1).substring(0, 10));
                    dynamically_date.setGravity(Gravity.CENTER);
                    dynamically_date.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

                    //顯示每筆LinearLayout的打卡時間
                    TextView dynamically_time;
                    dynamically_time = new TextView(GPSActivity.this);
                    dynamically_time.setText("打卡時間 : " + JArrayList.get(2));
                    dynamically_time.setGravity(Gravity.CENTER);
                    dynamically_time.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

                    //顯示每筆LinearLayout的GPS位置
                    TextView dynamically_gps;
                    dynamically_gps = new TextView(GPSActivity.this);
                    dynamically_gps.setText("GPS位置 : " + JArrayList.get(3));
                    dynamically_gps.setGravity(Gravity.CENTER);
                    //dynamically_gps.setWidth(50);
                    dynamically_gps.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

                    //設置每筆TableLayout的分隔線
                    TextView dynamically_txt = new TextView(GPSActivity.this);
                    dynamically_txt.setBackgroundColor(Color.rgb(220, 220, 220));

                    //設置每筆TableLayout的Button監聽器、與動態新增Button的ID
                    int loc = 0;
                    for (int i = 0; i < dynamically_btn.length; i++) {
                        if (dynamically_btn[i] == null) {
                            loc = i;
                            break;
                        }
                    }
                    dynamically_btn[loc] = new Button(GPSActivity.this);
                    dynamically_btn[loc].setBackgroundResource(R.drawable.googlemap);
                    //dynamically_btn[loc].setText("Google Map");
                    //dynamically_btn[loc].setPadding(10, 0, 10, 0);
                    dynamically_btn[loc].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    dynamically_btn[loc].setTextColor(Color.rgb(6, 102, 219));
                    dynamically_btn[loc].setId(loc);
                    dynamically_btn[loc].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int a = 0; a < dynamically_btn.length; a++) {
                                if (v.getId() == dynamically_btn[a].getId()) {
                                    Intent intent_gps = new Intent(GPSActivity.this, WorkersMapsActivity.class);
                                    LinearLayout location_llt = (LinearLayout) gps_llt.getChildAt(a);
                                    for (int x = 1; x < 5; x++) {
                                        LinearLayout gps_hr_llt = (LinearLayout) location_llt.getChildAt(x);
                                        TextView gps_txt = (TextView) gps_hr_llt.getChildAt(0);
                                        String gps = gps_txt.getText().toString();
                                        //將SQL裡的資料傳到MapsActivity
                                        Bundle bundle = new Bundle();
                                        bundle.putString("gps_location" + x, gps);

                                        //intent_gps.putExtra("TitleText", TitleText);//可放所有基本類別
                                        intent_gps.putExtras(bundle);//可放所有基本類別
                                    }
                                    startActivity(intent_gps);
                                    //進入MapsActivity後 清空gps_llt的資料
                                    gps_llt.removeAllViews();
                                }
                            }
                        }
                    });

                    LinearLayout.LayoutParams small_pm = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                    LinearLayout.LayoutParams btn_pm = new LinearLayout.LayoutParams(350, 80);

                    small_llt0.addView(dynamically_txt, LinearLayout.LayoutParams.MATCH_PARENT, 3);
                    small_llt1.addView(dynamically_name, small_pm);
                    small_llt2.addView(dynamically_date, small_pm);
                    small_llt3.addView(dynamically_time, small_pm);
                    small_llt4.addView(dynamically_gps, small_pm);
                    small_llt5.addView(dynamically_btn[loc], btn_pm);

                    big_llt.addView(small_llt0);
                    big_llt.addView(small_llt1);
                    big_llt.addView(small_llt2);
                    big_llt.addView(small_llt3);
                    big_llt.addView(small_llt4);
                    big_llt.addView(small_llt5);

                    gps_llt.addView(big_llt);
                    LinearLayout first_llt = (LinearLayout) gps_llt.getChildAt(0);
                    first_llt.getChildAt(0).setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 日期挑選器
     *
     * @param v
     */
    public void showDatePickerDialog(View v) {
        //日期挑選器
        DialogFragment newFragment = new DatePickerFragment();
        Bundle bData = new Bundle();
        bData.putInt("view", v.getId());
        Button button = (Button) v;
        bData.putString("date", button.getText().toString());
        newFragment.setArguments(bData);
        newFragment.show(getSupportFragmentManager(), "日期挑選器");
    }

    /**
     * 實現畫面置頂按鈕
     *
     * @param view
     */
    public void GoTopBtn(View view) {
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            public void run() {
                //實現畫面置頂按鈕
                gps_scv.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    /**
     * 確認是否有最新版本，進行更新
     */
    private void CheckFirebaseVersion() {
        SharedPreferences fb_version = getSharedPreferences("fb_version", MODE_PRIVATE);
        final String version = fb_version.getString("FB_VER", "");
        Log.e("GPSActivity", version);

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
                Log.e("GPSActivity", "已讀取到值:" + data);
                if (version.equals(data)) {
                } else {
                    new AlertDialog.Builder(GPSActivity.this)
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
                                                    GPSActivity.this.Update();
                                                }
                                            }.start();
                                        }
                                    }).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("GPSActivity", "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * 下載新版本APK
     */
    public void Update() {
        try {
            URL url = new URL("http://m.wqp-water.com.tw/wqp_1.9.apk");
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

            GPSActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "開始安裝新版本", Toast.LENGTH_LONG).show();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("下載錯誤!", e.toString());
            GPSActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "更新失敗!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * 請求開啟儲存、相機權限、GPS
     */
    private void UsesPermission() {
        // Here, thisActivity is the current activity、GPS
        if (ContextCompat.checkSelfPermission(GPSActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(GPSActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(GPSActivity.this)
                        .setMessage("我真的沒有要做壞事, 給我權限吧?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(GPSActivity.this,
                                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                                                , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(GPSActivity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                                , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("GPSActivity", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("GPSActivity", "onDestroy");
    }
}

