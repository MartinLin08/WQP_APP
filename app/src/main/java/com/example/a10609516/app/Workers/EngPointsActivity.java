package com.example.a10609516.app.Workers;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class EngPointsActivity extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ScrollView points_scv;
    private LinearLayout points_llt;

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
        } else if (department_id_data.toString().equals("2100")) {
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
                Intent intent18 = new Intent(EngPointsActivity.this, MenuActivity.class);
                startActivity(intent18);
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                break;  //進入HOME頁面
            case R.id.schedule_item:
                Intent intent7 = new Intent(EngPointsActivity.this, ScheduleActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "行程資訊", Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent = new Intent(EngPointsActivity.this, CalendarActivity.class);
                startActivity(intent);
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Intent intent1 = new Intent(EngPointsActivity.this, SearchActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; //進入查詢派工資料頁面
            /*case R.id.signature_item:
                Intent intent2 = new Intent(EngPointsActivity.this, SignatureActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "客戶電子簽名", Toast.LENGTH_SHORT).show();
                break; //進入客戶電子簽名頁面
            case R.id.record_item:
                Intent intent8 = new Intent(EngPointsActivity.this, RecordActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "上傳日報紀錄", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報紀錄頁面
            case R.id.picture_item:
                Intent intent3 = new Intent(EngPointsActivity.this, PictureActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳頁面
            case R.id.customer_item:
                Intent intent4 = new Intent(EngPointsActivity.this, CustomerActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢
            case R.id.upload_item:
                Intent intent5 = new Intent(EngPointsActivity.this, UploadActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent6 = new Intent(EngPointsActivity.this, CorrectActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面*/
            case R.id.about_item:
                Intent intent9 = new Intent(EngPointsActivity.this, VersionActivity.class);
                startActivity(intent9);
                Toast.makeText(this, "版本資訊", Toast.LENGTH_SHORT).show();
                break; //進入版本資訊頁面
            case R.id.QRCode_item:
                Intent intent10 = new Intent(EngPointsActivity.this, QRCodeActivity.class);
                startActivity(intent10);
                Toast.makeText(this, "QRCode", Toast.LENGTH_SHORT).show();
                break; //進入QRCode頁面
            case R.id.quotation_item:
                Intent intent11 = new Intent(EngPointsActivity.this, QuotationActivity.class);
                startActivity(intent11);
                Toast.makeText(this, "報價單審核", Toast.LENGTH_SHORT).show();
                break; //進入報價單審核頁面
            case R.id.points_item:
                Intent intent12 = new Intent(EngPointsActivity.this, PointsActivity.class);
                startActivity(intent12);
                Toast.makeText(this, "我的點數", Toast.LENGTH_SHORT).show();
                break; //進入查詢工務點數頁面
            case R.id.miss_item:
                Intent intent13 = new Intent(EngPointsActivity.this, MissCountActivity.class);
                startActivity(intent13);
                Toast.makeText(this, "未回單數量", Toast.LENGTH_SHORT).show();
                break; //進入工務未回單數量頁面
            case R.id.inventory_item:
                Intent intent14 = new Intent(EngPointsActivity.this, InventoryActivity.class);
                startActivity(intent14);
                Toast.makeText(this, "倉庫盤點", Toast.LENGTH_SHORT).show();
                break; //進入倉庫盤點管理頁面
            /*case R.id.purchase_item:
                Intent intent15 = new Intent(EngPointsActivity.this, QRCodeActivity.class);
                startActivity(intent15);
                Toast.makeText(this, "倉庫進貨", Toast.LENGTH_SHORT).show();
                break; //進入倉庫進貨管理頁面
            case R.id.return_item:
                Intent intent16 = new Intent(EngPointsActivity.this, QRCodeActivity.class);
                startActivity(intent16);
                Toast.makeText(this, "倉庫調撥", Toast.LENGTH_SHORT).show();
                break; //進入倉庫調撥管理頁面*/
            case R.id.map_item:
                Intent intent17 = new Intent(EngPointsActivity.this, GPSActivity.class);
                startActivity(intent17);
                Toast.makeText(this, "工務打卡GPS", Toast.LENGTH_SHORT).show();
                break; //進入GPS地圖頁面
            case R.id.eng_points_item:
                Toast.makeText(this, "工務點數明細", Toast.LENGTH_SHORT).show();
                break; //顯示工務點數明細
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eng_points);
        //動態取得 View 物件
        InItFunction();
        //與OKHttp連線(工務點數明細 WorkPointsDetail.php)
        sendRequestWithOkHttpForWorkPointsDetail();
        //確認是否有最新版本，進行更新
        CheckFirebaseVersion();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        points_scv = (ScrollView) findViewById(R.id.points_scv);
        points_llt = (LinearLayout) findViewById(R.id.points_llt);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);

        //UI介面下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                points_llt.removeAllViews();
                //與OKHttp連線(工務點數明細 WorkPointsDetail.php)
                sendRequestWithOkHttpForWorkPointsDetail();
            }
        });
    }

    /**
     * 與OKHttp連線(工務點數明細)
     */
    private void sendRequestWithOkHttpForWorkPointsDetail() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.e("EngPointsActivity", user_id_data);

                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://220.133.80.146/WQP/WorkPointsDetail.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("EngPointsActivity2", responseData);
                    parseJSONWithJSONObjectForWorkPointsDetail(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 取得工務點數(A、B)
     *
     * @param jsonData
     */
    private void parseJSONWithJSONObjectForWorkPointsDetail(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String service_date = jsonObject.getString("派工日期");
                String esvd_service_no = jsonObject.getString("派工單號");
                String work_type_name = jsonObject.getString("派工類別");
                String esvd_a_point = jsonObject.getString("A點數");
                String esvd_b_point = jsonObject.getString("B點數");
                String esvd_d_point = jsonObject.getString("D點數");
                String esvd_eng_money = jsonObject.getString("點數獎金");

                Log.e("EngPointsActivity", work_type_name);
                Log.e("EngPointsActivity", esvd_a_point);
                Log.e("EngPointsActivity", esvd_b_point);
                Log.e("EngPointsActivity", esvd_d_point);
                Log.e("EngPointsActivity", esvd_eng_money);

                //JSONArray加入SearchData資料
                ArrayList<String> JArrayList = new ArrayList<String>();
                JArrayList.add(service_date);
                JArrayList.add(esvd_service_no);
                JArrayList.add(work_type_name);
                JArrayList.add(esvd_a_point);
                JArrayList.add(esvd_b_point);
                JArrayList.add(esvd_d_point);
                JArrayList.add(esvd_eng_money);

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
                    LinearLayout small_llt0 = new LinearLayout(EngPointsActivity.this);
                    small_llt0.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout small_llt1 = new LinearLayout(EngPointsActivity.this);
                    small_llt1.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout small_llt2 = new LinearLayout(EngPointsActivity.this);
                    small_llt2.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout small_llt3 = new LinearLayout(EngPointsActivity.this);
                    small_llt3.setOrientation(LinearLayout.HORIZONTAL);
                    small_llt3.setBackgroundResource(R.drawable.part_line3);
                    LinearLayout small_llt4 = new LinearLayout(EngPointsActivity.this);
                    small_llt4.setOrientation(LinearLayout.HORIZONTAL);
                    small_llt4.setBackgroundResource(R.drawable.part_line3);
                    LinearLayout line_llt1 = new LinearLayout(EngPointsActivity.this);
                    line_llt1.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout line_llt2 = new LinearLayout(EngPointsActivity.this);
                    line_llt2.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout line_llt3 = new LinearLayout(EngPointsActivity.this);
                    line_llt3.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout line_llt4 = new LinearLayout(EngPointsActivity.this);
                    line_llt4.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout big_llt = new LinearLayout(EngPointsActivity.this);
                    big_llt.setOrientation(LinearLayout.VERTICAL);
                    big_llt.setBackgroundResource(R.drawable.part_line1);
                    LinearLayout empty_llt1 = new LinearLayout(EngPointsActivity.this);
                    empty_llt1.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout empty_llt2 = new LinearLayout(EngPointsActivity.this);
                    empty_llt2.setOrientation(LinearLayout.VERTICAL);

                    Bundle jb = msg.getData();
                    ArrayList<String> JArrayList = new ArrayList<String>();
                    //int i = b.getStringArrayList("JSON_data").size();
                    JArrayList = jb.getStringArrayList("JSON_data");

                    //顯示每筆LinearLayout的日期
                    TextView dynamically_date;
                    dynamically_date = new TextView(EngPointsActivity.this);
                    dynamically_date.setText("派工日期 : " + JArrayList.get(0).substring(0, 16));
                    dynamically_date.setGravity(Gravity.CENTER);
                    dynamically_date.setPadding(0, 10, 0, 10);
                    //dynamically_date.setWidth(50);
                    dynamically_date.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

                    //顯示每筆LinearLayout的派工類別
                    TextView dynamically_type;
                    dynamically_type = new TextView(EngPointsActivity.this);
                    dynamically_type.setText("派工類別 : " + JArrayList.get(2));
                    dynamically_type.setGravity(Gravity.CENTER);
                    dynamically_type.setPadding(0, 10, 0, 10);
                    //dynamically_type.setWidth(50);
                    dynamically_type.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

                    //顯示每筆LinearLayout的派工單號
                    TextView dynamically_no;
                    dynamically_no = new TextView(EngPointsActivity.this);
                    dynamically_no.setText("派工單號 : " + JArrayList.get(1));
                    dynamically_no.setGravity(Gravity.CENTER);
                    dynamically_no.setPadding(0, 10, 0, 10);
                    //dynamically_no.setWidth(50);
                    dynamically_no.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

                    //顯示每筆LinearLayout的A點數
                    TextView dynamically_a_points;
                    dynamically_a_points = new TextView(EngPointsActivity.this);
                    dynamically_a_points.setText("A點數 : " + JArrayList.get(3));
                    dynamically_a_points.setGravity(Gravity.CENTER);
                    dynamically_a_points.setPadding(0, 10, 0, 10);
                    //dynamically_a_points.setWidth(50);
                    dynamically_a_points.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

                    //顯示每筆LinearLayout的B點數
                    TextView dynamically_b_points;
                    dynamically_b_points = new TextView(EngPointsActivity.this);
                    dynamically_b_points.setText("B點數 : " + JArrayList.get(4));
                    dynamically_b_points.setGravity(Gravity.CENTER);
                    dynamically_b_points.setPadding(0, 10, 0, 10);
                    //dynamically_b_points.setWidth(50);
                    dynamically_b_points.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

                    //顯示每筆LinearLayout的D點數
                    TextView dynamically_d_points;
                    dynamically_d_points = new TextView(EngPointsActivity.this);
                    dynamically_d_points.setText("D點數 : " + JArrayList.get(5));
                    dynamically_d_points.setGravity(Gravity.CENTER);
                    dynamically_d_points.setPadding(0, 10, 0, 10);
                    //dynamically_d_points.setWidth(50);
                    dynamically_d_points.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

                    //顯示每筆LinearLayout的點數獎金
                    TextView dynamically_eng_money;
                    dynamically_eng_money = new TextView(EngPointsActivity.this);
                    dynamically_eng_money.setText("點數獎金 : " + JArrayList.get(6));
                    dynamically_eng_money.setGravity(Gravity.CENTER);
                    dynamically_eng_money.setPadding(0, 10, 0, 10);
                    //dynamically_eng_money.setWidth(50);
                    dynamically_eng_money.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

                    //設置每筆TableLayout的分隔線
                    TextView dynamically_txt1 = new TextView(EngPointsActivity.this);
                    dynamically_txt1.setBackgroundColor(Color.rgb(224, 255, 255));
                    //設置每筆TableLayout的分隔線
                    TextView dynamically_txt2 = new TextView(EngPointsActivity.this);
                    dynamically_txt2.setBackgroundColor(Color.rgb(224, 255, 255));
                    //設置每筆TableLayout的分隔線
                    TextView dynamically_txt3 = new TextView(EngPointsActivity.this);
                    dynamically_txt3.setBackgroundColor(Color.rgb(224, 255, 255));
                    //設置每筆TableLayout的分隔線
                    TextView dynamically_txt4 = new TextView(EngPointsActivity.this);
                    dynamically_txt4.setBackgroundColor(Color.rgb(224, 255, 255));
                    //設置每筆TableLayout的分隔線
                    TextView dynamically_txt5 = new TextView(EngPointsActivity.this);
                    dynamically_txt5.setBackgroundColor(Color.rgb(224, 255, 255));
                    //設置每筆TableLayout的分隔線
                    TextView dynamically_txt6 = new TextView(EngPointsActivity.this);
                    dynamically_txt6.setBackgroundColor(Color.rgb(224, 255, 255));

                    LinearLayout.LayoutParams small_pm = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                    small_llt0.addView(dynamically_date, small_pm);
                    line_llt1.addView(dynamically_txt1, LinearLayout.LayoutParams.MATCH_PARENT, 3);
                    small_llt1.addView(dynamically_no, small_pm);
                    line_llt2.addView(dynamically_txt2, LinearLayout.LayoutParams.MATCH_PARENT, 3);
                    small_llt2.addView(dynamically_type, small_pm);
                    line_llt3.addView(dynamically_txt3, LinearLayout.LayoutParams.MATCH_PARENT, 3);
                    small_llt3.addView(dynamically_a_points, small_pm);
                    small_llt3.addView(dynamically_txt5, 3, LinearLayout.LayoutParams.MATCH_PARENT);
                    small_llt3.addView(dynamically_b_points, small_pm);
                    line_llt4.addView(dynamically_txt4, LinearLayout.LayoutParams.MATCH_PARENT, 3);
                    small_llt4.addView(dynamically_d_points, small_pm);
                    small_llt4.addView(dynamically_txt6, 3, LinearLayout.LayoutParams.MATCH_PARENT);
                    small_llt4.addView(dynamically_eng_money, small_pm);

                    big_llt.addView(small_llt0);
                    big_llt.addView(line_llt1);
                    big_llt.addView(small_llt1);
                    big_llt.addView(line_llt2);
                    big_llt.addView(small_llt2);
                    big_llt.addView(line_llt3);
                    big_llt.addView(small_llt3);
                    big_llt.addView(line_llt4);
                    big_llt.addView(small_llt4);
                    big_llt.addView(empty_llt2, LinearLayout.LayoutParams.MATCH_PARENT, 12);

                    points_llt.addView(big_llt);
                    points_llt.addView(empty_llt1, LinearLayout.LayoutParams.MATCH_PARENT, 10);

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
                points_scv.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    /**
     * 確認是否有最新版本，進行更新
     */
    private void CheckFirebaseVersion() {
        SharedPreferences fb_version = getSharedPreferences("fb_version", MODE_PRIVATE);
        final String version = fb_version.getString("FB_VER", "");
        Log.e("EngPointsActivity", version);

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
                Log.e("EngPointsActivity", "已讀取到值:" + data);
                if (version.equals(data)) {
                } else {
                    new AlertDialog.Builder(EngPointsActivity.this)
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
                                                    EngPointsActivity.this.Update();
                                                }
                                            }.start();
                                        }
                                    }).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("EngPointsActivity", "Failed to read value.", error.toException());
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

            EngPointsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "開始安裝新版本", Toast.LENGTH_LONG).show();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("下載錯誤!", e.toString());
            EngPointsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "更新失敗!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("EngPointsActivity", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("EngPointsActivity", "onDestroy");
    }
}

