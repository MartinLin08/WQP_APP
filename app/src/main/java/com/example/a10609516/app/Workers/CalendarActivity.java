package com.example.a10609516.app.Workers;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import android.widget.TextView;

import com.example.a10609516.app.Basic.QRCodeActivity;
import com.example.a10609516.app.Clerk.QuotationActivity;
import com.example.a10609516.app.DepartmentAndDIY.CustomerActivity;
import com.example.a10609516.app.Tools.DatePickerFragment;
import com.example.a10609516.app.Basic.MenuActivity;
import com.example.a10609516.app.DepartmentAndDIY.PictureActivity;
import com.example.a10609516.app.R;
import com.example.a10609516.app.Basic.VersionActivity;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CalendarActivity extends AppCompatActivity {

    private LinearLayout search_llt, calendar_llt;
    private Spinner company_spinner;
    private TextView date_txt, search_up_txt, search_down_txt, dynamically_txt, dynamically_type,
            dynamically_customer, dynamically_phone, dynamically_address;
    private Button work_date_btn, last_btn, search_btn, next_btn;
    private ProgressBar dynamically_PGTime;

    int x, y;
    String date, last_date, next_date;
    SimpleDateFormat simpleDateFormat;

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
        if ((user_id_data.toString().equals("9706013")) || user_id_data.toString().equals("9908023") || user_id_data.toString().equals("10010039")
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
                Intent intent = new Intent(CalendarActivity.this, MenuActivity.class);
                startActivity(intent);
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                finish();
                break; //返回首頁
            case R.id.schedule_item:
                Intent intent7 = new Intent(CalendarActivity.this, ScheduleActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "行程資訊", Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //顯示派工行事曆
            case R.id.work_item:
                Intent intent1 = new Intent(CalendarActivity.this, SearchActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; //進入查詢派工資料頁面
            /*case R.id.signature_item:
                Intent intent2 = new Intent(CalendarActivity.this, SignatureActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "客戶電子簽名", Toast.LENGTH_SHORT).show();
                break; //進入客戶電子簽名頁面*/
            /*case R.id.record_item:
                Intent intent8 = new Intent(CalendarActivity.this, RecordActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "上傳日報紀錄", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報紀錄頁面*/
            case R.id.picture_item:
                Intent intent3 = new Intent(CalendarActivity.this, PictureActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳頁面
            case R.id.customer_item:
                Intent intent4 = new Intent(CalendarActivity.this, CustomerActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢頁面
            /*case R.id.upload_item:
                Intent intent5 = new Intent(CalendarActivity.this, UploadActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent6 = new Intent(CalendarActivity.this, CorrectActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面*/
            case R.id.about_item:
                Intent intent9 = new Intent(CalendarActivity.this, VersionActivity.class);
                startActivity(intent9);
                Toast.makeText(this, "版本資訊", Toast.LENGTH_SHORT).show();
                break; //進入版本資訊頁面
            case R.id.QRCode_item:
                Intent intent10 = new Intent(CalendarActivity.this, QRCodeActivity.class);
                startActivity(intent10);
                Toast.makeText(this, "QRCode", Toast.LENGTH_SHORT).show();
                break; //進入QRCode頁面
            case R.id.quotation_item:
                Intent intent11 = new Intent(CalendarActivity.this, QuotationActivity.class);
                startActivity(intent11);
                Toast.makeText(this, "報價單審核", Toast.LENGTH_SHORT).show();
                break; //進入報價單審核頁面
            case R.id.points_item:
                Intent intent12 = new Intent(CalendarActivity.this, PointsActivity.class);
                startActivity(intent12);
                Toast.makeText(this, "我的點數", Toast.LENGTH_SHORT).show();
                break; //進入查詢工務點數頁面
            case R.id.miss_item:
                Intent intent14 = new Intent(CalendarActivity.this, MissCountActivity.class);
                startActivity(intent14);
                Toast.makeText(this, "未回單數量", Toast.LENGTH_SHORT).show();
                break; //進入工務未回單數量頁面
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        //動態取得 View 物件
        InItFunction();
        //分公司的Spinner下拉選單
        WorkTypeSpinner();
        //獲取當天日期
        GetDate();
        //與OKHttp連線取得行事曆資料
        sendRequestWithOKHttpOfCalendar();
        //確認是否有最新版本，進行更新
        CheckFirebaseVersion();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        search_llt = (LinearLayout) findViewById(R.id.search_llt);
        calendar_llt = (LinearLayout) findViewById(R.id.calendar_llt);
        company_spinner = (Spinner) findViewById(R.id.company_spinner);
        date_txt = (TextView) findViewById(R.id.date_txt);
        search_up_txt = (TextView) findViewById(R.id.search_up_txt);
        search_down_txt = (TextView) findViewById(R.id.search_down_txt);
        work_date_btn = (Button) findViewById(R.id.work_date_btn);
        last_btn = (Button) findViewById(R.id.last_btn);
        search_btn = (Button) findViewById(R.id.search_btn);
        next_btn = (Button) findViewById(R.id.next_btn);

        //點查詢收起search_llt
        search_up_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_llt.setVisibility(View.GONE);
                search_up_txt.setVisibility(View.GONE);
                search_down_txt.setVisibility(View.VISIBLE);
            }
        });

        //點查詢打開search_llt
        search_down_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_llt.setVisibility(View.VISIBLE);
                search_up_txt.setVisibility(View.VISIBLE);
                search_down_txt.setVisibility(View.GONE);
            }
        });

        //查詢日期選擇器的日期
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_txt.setText(work_date_btn.getText().toString());
                calendar_llt.removeAllViews();
                sendRequestWithOKHttpOfSearch();
            }
        });

        //查詢往上一日
        last_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar_llt.removeAllViews();

                LastDate();
                date_txt.setText(last_date);
                work_date_btn.setText(last_date);

                sendRequestWithOKHttpOfLast();
            }
        });

        //查詢往下一日
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar_llt.removeAllViews();

                NextDate();
                date_txt.setText(next_date);
                work_date_btn.setText(next_date);

                sendRequestWithOKHttpOfNext();
            }
        });
    }

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
     * 分公司的Spinner下拉選單
     */
    private void WorkTypeSpinner() {
        final String[] select = {"請選擇  ", "台北拓亞鈦,快撥28868", "桃園分公司,快撥31338", "新竹分公司,快撥37888", "台中分公司,快撥42088", "高雄分公司,快撥73568"};
        ArrayAdapter<String> selectList = new ArrayAdapter<String>(CalendarActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                select);
        company_spinner.setAdapter(selectList);
    }

    /**
     * 獲取當天日期
     */
    private void GetDate() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormat.format(new java.util.Date());
        date_txt.setText(date);
        work_date_btn.setText(date);
    }

    /**
     * 獲取date_txt的前一日
     */
    private void LastDate() {
        String view_date = work_date_btn.getText().toString();
        Log.e("CalendarActivity2", view_date);
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(simpleDateFormat.parse(view_date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, -1);
        //SimpleDateFormat outLast = new SimpleDateFormat("yyyy-MM-dd");
        last_date = simpleDateFormat.format(c.getTime());
    }

    /**
     * 獲取date_txt的後一日
     */
    private void NextDate() {
        String view_date = work_date_btn.getText().toString();
        Log.e("CalendarActivity2", view_date);
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(simpleDateFormat.parse(view_date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);
        //SimpleDateFormat outNext = new SimpleDateFormat("yyyy-MM-dd");
        next_date = simpleDateFormat.format(c.getTime());
    }

    /**
     * 與OkHttp(CalendarData)建立連線
     */
    private void sendRequestWithOKHttpOfCalendar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.e("CalendarActivity", user_id_data);
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .add("Work_date", date)
                            .build();
                    Log.e("CalendarActivity", date);
                    Request request = new Request.Builder()
                            .url("http://220.133.80.146/WQP/CalendarData.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("CalendarActivity", responseData);
                    parseJSONWithJSONObjectOfCalendar(responseData);
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
    private void parseJSONWithJSONObjectOfCalendar(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String esvd_booking_period = jsonObject.getString("預約開始時間");
                String esvd_booking_period_end = jsonObject.getString("預約結束時間");
                String WORK_TYPE_NAME = jsonObject.getString("派工類別");
                String ESV_CONTACTOR = jsonObject.getString("聯絡人");
                String ESV_TEL_NO1 = jsonObject.getString("主要電話");
                String ESV_ADDRESS = jsonObject.getString("派工地址");
                String User_name = jsonObject.getString("員工姓名A");
                String Group_name = jsonObject.getString("公司別");

                //JSONArray加入SearchData資料
                ArrayList<String> JArrayList = new ArrayList<String>();
                JArrayList.add(esvd_booking_period);
                JArrayList.add(esvd_booking_period_end);
                JArrayList.add(WORK_TYPE_NAME);
                JArrayList.add(ESV_CONTACTOR);
                JArrayList.add(ESV_TEL_NO1);
                JArrayList.add(ESV_ADDRESS);
                JArrayList.add(User_name);
                JArrayList.add(Group_name);

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
     * 與OkHttp(CalendarData)建立連線
     */
    private void sendRequestWithOKHttpOfSearch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.e("CalendarActivity", user_id_data);

                String s_date = work_date_btn.getText().toString();
                String group_name = String.valueOf(company_spinner.getSelectedItem()).substring(0, 5);
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .add("Work_date", s_date)
                            .add("Group_name", group_name)
                            .build();
                    Log.e("CalendarActivity", group_name);
                    Request request = new Request.Builder()
                            .url("http://220.133.80.146/WQP/CalendarData.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("CalendarActivity", responseData);
                    parseJSONWithJSONObjectOfSearch(responseData);
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
    private void parseJSONWithJSONObjectOfSearch(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String esvd_booking_period = jsonObject.getString("預約開始時間");
                String esvd_booking_period_end = jsonObject.getString("預約結束時間");
                String WORK_TYPE_NAME = jsonObject.getString("派工類別");
                String ESV_CONTACTOR = jsonObject.getString("聯絡人");
                String ESV_TEL_NO1 = jsonObject.getString("主要電話");
                String ESV_ADDRESS = jsonObject.getString("派工地址");
                String User_name = jsonObject.getString("員工姓名A");
                String Group_name = jsonObject.getString("公司別");

                //JSONArray加入SearchData資料
                ArrayList<String> JArrayList = new ArrayList<String>();
                JArrayList.add(esvd_booking_period);
                JArrayList.add(esvd_booking_period_end);
                JArrayList.add(WORK_TYPE_NAME);
                JArrayList.add(ESV_CONTACTOR);
                JArrayList.add(ESV_TEL_NO1);
                JArrayList.add(ESV_ADDRESS);
                JArrayList.add(User_name);
                JArrayList.add(Group_name);

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
     * 與OkHttp(CalendarData)建立連線
     */
    private void sendRequestWithOKHttpOfLast() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String group_name = String.valueOf(company_spinner.getSelectedItem()).substring(0, 5);
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("Work_date", last_date)
                            .add("Group_name", group_name)
                            .build();
                    Log.e("CalendarActivity3", last_date);
                    Request request = new Request.Builder()
                            .url("http://220.133.80.146/WQP/CalendarData.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("CalendarActivity", responseData);
                    parseJSONWithJSONObjectOfLast(responseData);
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
    private void parseJSONWithJSONObjectOfLast(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String esvd_booking_period = jsonObject.getString("預約開始時間");
                String esvd_booking_period_end = jsonObject.getString("預約結束時間");
                String WORK_TYPE_NAME = jsonObject.getString("派工類別");
                String ESV_CONTACTOR = jsonObject.getString("聯絡人");
                String ESV_TEL_NO1 = jsonObject.getString("主要電話");
                String ESV_ADDRESS = jsonObject.getString("派工地址");
                String User_name = jsonObject.getString("員工姓名A");
                String Group_name = jsonObject.getString("公司別");

                //JSONArray加入SearchData資料
                ArrayList<String> JArrayList = new ArrayList<String>();
                JArrayList.add(esvd_booking_period);
                JArrayList.add(esvd_booking_period_end);
                JArrayList.add(WORK_TYPE_NAME);
                JArrayList.add(ESV_CONTACTOR);
                JArrayList.add(ESV_TEL_NO1);
                JArrayList.add(ESV_ADDRESS);
                JArrayList.add(User_name);
                JArrayList.add(Group_name);

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
     * 與OkHttp(CalendarData)建立連線
     */
    private void sendRequestWithOKHttpOfNext() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String group_name = String.valueOf(company_spinner.getSelectedItem()).substring(0, 5);
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("Work_date", next_date)
                            .add("Group_name", group_name)
                            .build();
                    Log.e("CalendarActivity3", next_date);
                    Request request = new Request.Builder()
                            .url("http://220.133.80.146/WQP/CalendarData.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("CalendarActivity", responseData);
                    parseJSONWithJSONObjectOfNext(responseData);
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
    private void parseJSONWithJSONObjectOfNext(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String esvd_booking_period = jsonObject.getString("預約開始時間");
                String esvd_booking_period_end = jsonObject.getString("預約結束時間");
                String WORK_TYPE_NAME = jsonObject.getString("派工類別");
                String ESV_CONTACTOR = jsonObject.getString("聯絡人");
                String ESV_TEL_NO1 = jsonObject.getString("主要電話");
                String ESV_ADDRESS = jsonObject.getString("派工地址");
                String User_name = jsonObject.getString("員工姓名A");
                String Group_name = jsonObject.getString("公司別");

                //JSONArray加入SearchData資料
                ArrayList<String> JArrayList = new ArrayList<String>();
                JArrayList.add(esvd_booking_period);
                JArrayList.add(esvd_booking_period_end);
                JArrayList.add(WORK_TYPE_NAME);
                JArrayList.add(ESV_CONTACTOR);
                JArrayList.add(ESV_TEL_NO1);
                JArrayList.add(ESV_ADDRESS);
                JArrayList.add(User_name);
                JArrayList.add(Group_name);

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
                    //製作Dip
                    Resources resources = getResources();
                    float name_Dip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, resources.getDisplayMetrics());
                    int name_dip = Math.round(name_Dip);
                    float type_Dip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 125, resources.getDisplayMetrics());
                    int type_dip = Math.round(type_Dip);
                    float pg_Dip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 26, resources.getDisplayMetrics());
                    int pg_dip = Math.round(pg_Dip);

                    LinearLayout big_llt = new LinearLayout(CalendarActivity.this);
                    big_llt.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout name_llt = new LinearLayout(CalendarActivity.this);
                    name_llt.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout small_llt1 = new LinearLayout(CalendarActivity.this);
                    small_llt1.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout small_llt2 = new LinearLayout(CalendarActivity.this);
                    small_llt2.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout dynamically_llt = new LinearLayout(CalendarActivity.this);
                    dynamically_llt.setBackgroundResource(R.drawable.calendar_h_divider);
                    HorizontalScrollView dynamically_hsv = new HorizontalScrollView(CalendarActivity.this);

                    Bundle jb = msg.getData();
                    ArrayList<String> JArrayList = new ArrayList<String>();
                    JArrayList = jb.getStringArrayList("JSON_data");
                    for (int i = 0; i < jb.getStringArrayList("JSON_data").size(); i++) {
                        dynamically_txt = new TextView(CalendarActivity.this);
                        dynamically_txt.setText(JArrayList.get(6).replace("A", ""));
                        dynamically_txt.setTextColor(Color.rgb(6, 102, 219));
                        dynamically_txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
                        dynamically_txt.setGravity(Gravity.LEFT);
                        dynamically_txt.setPadding(10, 2, 0, 0);
                        dynamically_txt.setWidth(name_dip);
                        dynamically_txt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                        dynamically_txt.getPaint().setAntiAlias(true);

                        name_llt.addView(dynamically_txt, LinearLayout.LayoutParams.MATCH_PARENT, name_dip);
                        if (dynamically_txt.getText().equals("")) {
                            dynamically_txt.setVisibility(View.GONE);
                        }

                        dynamically_type = new TextView(CalendarActivity.this);
                        dynamically_type.setText(JArrayList.get(0) + " " + JArrayList.get(2));
                        dynamically_type.setTextColor(Color.rgb(6, 102, 219));
                        dynamically_type.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                        dynamically_type.setGravity(Gravity.LEFT);
                        dynamically_type.setPadding(5, 0, 0, 4);
                        //dynamically_type.setWidth(type_dip);
                        if (JArrayList.get(0).equals("09:00")) {
                            x = 0;
                        }
                        if (JArrayList.get(0).equals("10:00")) {
                            x = 10;
                        }
                        if (JArrayList.get(0).equals("11:00")) {
                            x = 20;
                        }
                        if (JArrayList.get(0).equals("12:00")) {
                            x = 30;
                        }
                        if (JArrayList.get(0).equals("13:00")) {
                            x = 40;
                        }
                        if (JArrayList.get(0).equals("14:00")) {
                            x = 50;
                        }
                        if (JArrayList.get(0).equals("15:00")) {
                            x = 60;
                        }
                        if (JArrayList.get(0).equals("16:00")) {
                            x = 70;
                        }
                        if (JArrayList.get(0).equals("17:00")) {
                            x = 80;
                        }
                        if (JArrayList.get(0).equals("18:00")) {
                            x = 90;
                        }
                        if (JArrayList.get(0).equals("19:00")) {
                            x = 100;
                        }
                        if (JArrayList.get(0).equals("20:00")) {
                            x = 110;
                        }
                        if (JArrayList.get(0).equals("21:00")) {
                            x = 120;
                        }
                        if (JArrayList.get(1).equals("09:00")) {
                            y = 10;
                        }
                        if (JArrayList.get(1).equals("10:00")) {
                            y = 20;
                        }
                        if (JArrayList.get(1).equals("11:00")) {
                            y = 30;
                        }
                        if (JArrayList.get(1).equals("12:00")) {
                            y = 40;
                        }
                        if (JArrayList.get(1).equals("13:00")) {
                            y = 50;
                        }
                        if (JArrayList.get(1).equals("14:00")) {
                            y = 60;
                        }
                        if (JArrayList.get(1).equals("15:00")) {
                            y = 70;
                        }
                        if (JArrayList.get(1).equals("16:00")) {
                            y = 80;
                        }
                        if (JArrayList.get(1).equals("17:00")) {
                            y = 90;
                        }
                        if (JArrayList.get(1).equals("18:00")) {
                            y = 100;
                        }
                        if (JArrayList.get(1).equals("19:00")) {
                            y = 110;
                        }
                        if (JArrayList.get(1).equals("20:00")) {
                            y = 120;
                        }
                        if (JArrayList.get(1).equals("21:00")) {
                            y = 130;
                        }
                        dynamically_PGTime = new ProgressBar(CalendarActivity.this, null, android.R.attr.progressBarStyleHorizontal);
                        dynamically_PGTime.setMax(130);
                        dynamically_PGTime.setProgress(x);
                        dynamically_PGTime.setSecondaryProgress(y);
                        dynamically_PGTime.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.progressbar_bg, null));
                        //dynamically_PGTime.setBackgroundResource(R.drawable.progressbar_bg);

                        small_llt1.addView(dynamically_type, type_dip, pg_dip);
                        small_llt1.addView(dynamically_PGTime, LinearLayout.LayoutParams.MATCH_PARENT, pg_dip);

                        dynamically_customer = new TextView(CalendarActivity.this);
                        dynamically_customer.setText("客戶名稱 : " + JArrayList.get(3));
                        dynamically_customer.setTextColor(Color.rgb(6, 102, 219));
                        dynamically_customer.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                        dynamically_customer.setGravity(Gravity.LEFT);
                        dynamically_customer.setPadding(5, 0, 0, 0);

                        dynamically_phone = new TextView(CalendarActivity.this);
                        dynamically_phone.setText(" 聯絡電話 : " + JArrayList.get(4));
                        dynamically_phone.setTextColor(Color.rgb(6, 102, 219));
                        dynamically_phone.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                        dynamically_phone.setGravity(Gravity.LEFT);
                        dynamically_phone.setPadding(5, 0, 0, 0);

                        dynamically_address = new TextView(CalendarActivity.this);
                        dynamically_address.setText(" 派工地址 : " + JArrayList.get(5));
                        dynamically_address.setTextColor(Color.rgb(6, 102, 219));
                        dynamically_address.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                        dynamically_address.setGravity(Gravity.LEFT);
                        dynamically_address.setPadding(5, 0, 0, 0);
                        //dynamically_address.setTextIsSelectable(true);

                        small_llt2.addView(dynamically_customer);
                        small_llt2.addView(dynamically_phone);
                        small_llt2.addView(dynamically_address);
                    }

                    for (int a = 3; a <= 23; a++) {
                        small_llt2.getChildAt(a).setVisibility(View.GONE);
                    }
                    dynamically_hsv.addView(small_llt2);

                    big_llt.addView(name_llt, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    big_llt.addView(small_llt1, LinearLayout.LayoutParams.MATCH_PARENT, pg_dip);
                    big_llt.addView(dynamically_hsv, LinearLayout.LayoutParams.WRAP_CONTENT, pg_dip);
                    big_llt.addView(dynamically_llt);

                    calendar_llt.addView(big_llt, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
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
        Log.e("CalendarActivity", version);

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
                Log.e("CalendarActivity", "已讀取到值:" + data);
                if (version.equals(data)) {
                } else {
                    new AlertDialog.Builder(CalendarActivity.this)
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
                                                    CalendarActivity.this.Update();
                                                }
                                            }.start();
                                        }
                                    }).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("CalendarActivity", "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * 下載新版本APK
     */
    public void Update() {
        try {
            URL url = new URL("http://m.wqp-water.com.tw/wqp_1.8.apk");
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            //c.setRequestMethod("GET");
            //c.setDoOutput(true);
            c.connect();

            String PATH = Environment.getExternalStorageDirectory() + "/Download/";
            //String PATH = System.getenv("SECONDARY_STORAGE") + "/Download/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "wqp_1.8.apk");
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
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Download/" + "wqp_1.8.apk")), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            CalendarActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "開始安裝新版本", Toast.LENGTH_LONG).show();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("下載錯誤!", e.toString());
            CalendarActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "更新失敗!", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CalendarActivity", "onDestroy");
    }
}
