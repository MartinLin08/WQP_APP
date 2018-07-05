package com.example.a10609516.app.Workers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.a10609516.app.Tools.CirclePgBar;
import com.example.a10609516.app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PointsActivity extends AppCompatActivity {

    private CirclePgBar money_pgr, a_points_pgr, b_points_pgr, d_points_pgr, ab_points_pgr;

    private TextView local_txt, id_txt, user_txt;
    private String user_id_data;
    private String a_points, b_points, d_points, ab_points, money;

    //private int a_points_count, b_points_count, d_points_count, ab_points_count, money_count;
    private Float a_points_count, b_points_count, d_points_count, ab_points_count, money_count;

    /**
     * 創建Menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences department_id = getSharedPreferences("department_id" , MODE_PRIVATE);
        String department_id_data = department_id.getString("D_ID" , "");
        if (department_id_data.toString().equals("2100")) {
            getMenuInflater().inflate(R.menu.clerk_menu, menu);
            return true;
        }else if (department_id_data.toString().equals("2200")) {
            getMenuInflater().inflate(R.menu.diy_menu, menu);
            return true;
        }else if (department_id_data.toString().equals("5200")) {
            getMenuInflater().inflate(R.menu.workers_menu, menu);
            return true;
        }else{
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
                Intent intent = new Intent(PointsActivity.this, MenuActivity.class);
                startActivity(intent);
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                finish();
                break; //返回首頁
            case R.id.schedule_item:
                Intent intent7 = new Intent(PointsActivity.this, ScheduleActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "行程資訊", Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent12 = new Intent(PointsActivity.this, ScheduleActivity.class);
                startActivity(intent12);
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Intent intent1 = new Intent(PointsActivity.this, SearchActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; //進入查詢派工資料頁面
            case R.id.signature_item:
                Intent intent2 = new Intent(PointsActivity.this, SignatureActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "客戶電子簽名", Toast.LENGTH_SHORT).show();
                break; //進入客戶電子簽名頁面
            case R.id.record_item:
                Intent intent8 = new Intent(PointsActivity.this, RecordActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "上傳日報紀錄", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報紀錄頁面
            case R.id.picture_item:
                Intent intent3 = new Intent(PointsActivity.this, PictureActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳頁面
            case R.id.customer_item:
                Intent intent4 = new Intent(PointsActivity.this, CustomerActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢頁面
            case R.id.upload_item:
                Intent intent5 = new Intent(PointsActivity.this, UploadActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent6 = new Intent(PointsActivity.this, CorrectActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面
            case R.id.about_item:
                Intent intent9 = new Intent(PointsActivity.this, VersionActivity.class);
                startActivity(intent9);
                Toast.makeText(this, "版本資訊", Toast.LENGTH_SHORT).show();
                break; //進入版本資訊頁面
            case R.id.QRCode_item:
                Intent intent10 = new Intent(PointsActivity.this, QRCodeActivity.class);
                startActivity(intent10);
                Toast.makeText(this, "QRCode", Toast.LENGTH_SHORT).show();
                break; //進入QRCode頁面
            case R.id.quotation_item:
                Intent intent11 = new Intent(PointsActivity.this, QuotationActivity.class);
                startActivity(intent11);
                Toast.makeText(this, "報價單審核", Toast.LENGTH_SHORT).show();
                break; //進入報價單審核頁面
            case R.id.points_item:
                Toast.makeText(this, "我的點數", Toast.LENGTH_SHORT).show();
                break; //顯示工務點數金額
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        //動態取得 View 物件
        InItFunction();
        //與OKHttp連線(UserName.php)
        sendRequestWithOkHttpForUserName();
        //與OKHttp連線(UserLocal.php)
        sendRequestWithOkHttpForuserLocal();
        //與OKHttp連線(WorkAllPoints.php)
        sendRequestWithOkHttpForWorkAllPoints();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        local_txt = (TextView) findViewById(R.id.local_txt);
        id_txt = (TextView) findViewById(R.id.id_txt);
        user_txt = (TextView) findViewById(R.id.user_txt);
        money_pgr = (CirclePgBar) findViewById(R.id.money_pgr);
        a_points_pgr = (CirclePgBar) findViewById(R.id.a_points_pgr);
        b_points_pgr = (CirclePgBar) findViewById(R.id.b_points_pgr);
        d_points_pgr = (CirclePgBar) findViewById(R.id.d_points_pgr);
        ab_points_pgr = (CirclePgBar) findViewById(R.id.ab_points_pgr);

        //接收LoginActivity傳過來的值
        SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
        user_id_data = user_id.getString("ID", "");
        Log.i("PointsActivity", user_id_data);
        id_txt.setText("員編 : " + user_id_data);
    }

    /**
     * 與資料庫連線 藉由登入輸入的員工ID取得員工姓名
     */
    private void sendRequestWithOkHttpForUserName() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://220.133.80.146/WQP/UserName.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("PointsActivity", responseData);
                    showResponseForUserName(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 在TextView上SHOW出回傳的員工姓名
     *
     * @param response
     */
    private void showResponseForUserName(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                user_txt.setText("工務 : " + response);
            }
        });
    }

    /**
     * 與資料庫連線 藉由登入輸入的員工ID取得員工所在地區
     */
    private void sendRequestWithOkHttpForuserLocal() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://220.133.80.146/WQP/UserLocal.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("PointsActivity", responseData);
                    showResponseForUserLocal(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 在TextView上SHOW出回傳的員工所在區域
     *
     * @param response
     */
    private void showResponseForUserLocal(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                local_txt.setText("地區別 : " + response.substring(0,2));
            }
        });
    }

    /**
     * 與OkHttp建立連線
     */
    private void sendRequestWithOkHttpForWorkAllPoints() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.e("PointsActivity1", user_id_data);

                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .build();
                    Log.e("PointsActivity", user_id_data);
                    Request request = new Request.Builder()
                            .url("http://220.133.80.146/WQP/WorkAllPoints.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("PointsActivity1", responseData);
                    parseJSONWithJSONObjectForWorkAllPoints(responseData);
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
    private void parseJSONWithJSONObjectForWorkAllPoints(String jsonData) {
        Log.e("ENTER", "YES");
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                a_points = jsonObject.getString("A點數");
                b_points = jsonObject.getString("B點數");
                d_points = jsonObject.getString("D點數");
                ab_points = jsonObject.getString("AB點數");
                money = jsonObject.getString("分配金額");

                Log.e("PointsActivity", a_points);
                Log.e("PointsActivity", b_points);
                Log.e("PointsActivity", d_points);
                Log.e("PointsActivity", ab_points);
                Log.e("PointsActivity", money);

                a_points_count = Float.parseFloat(a_points);
                b_points_count = Float.parseFloat(b_points);
                d_points_count = Float.parseFloat(d_points.trim());
                ab_points_count = Float.parseFloat(ab_points);
                money_count = Float.parseFloat(money.trim());

                money_pgr.setmCirclePgBar(0, money_count, 6000, Color.rgb(244, 164, 96));
                money_pgr.invalidate();
                a_points_pgr.setmCirclePgBar(0, a_points_count, 5000, Color.rgb(227, 38, 54));
                a_points_pgr.invalidate();
                b_points_pgr.setmCirclePgBar(0, b_points_count, 5000, Color.rgb(115, 230, 140));
                b_points_pgr.invalidate();
                d_points_pgr.setmCirclePgBar(0, d_points_count, 200, Color.rgb(30, 144, 255));
                d_points_pgr.invalidate();
                ab_points_pgr.setmCirclePgBar(0, ab_points_count, 8000, Color.rgb(255, 255, 0));
                ab_points_pgr.invalidate();
                Log.e("OVER", "YES");
            }
        } catch (Exception e) {
            Log.e("CATCH", "YES");
            e.printStackTrace();
        }
    }
}