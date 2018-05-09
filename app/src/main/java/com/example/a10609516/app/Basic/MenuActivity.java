package com.example.a10609516.app.Basic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10609516.app.Clerk.QuotationActivity;
import com.example.a10609516.app.DepartmentAndDIY.CustomerActivity;
import com.example.a10609516.app.DepartmentAndDIY.PictureActivity;
import com.example.a10609516.app.R;
import com.example.a10609516.app.Workers.CalendarActivity;
import com.example.a10609516.app.Workers.ScheduleActivity;
import com.example.a10609516.app.Workers.SearchActivity;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MenuActivity extends AppCompatActivity {

    private ListView announcement_ListView;
    private String[] show_text = {"","","","","","",""};
    private ArrayAdapter listAdapter;
    private Spinner announcement_spinner;
    private TextView name_textView;

    /**
     * 創建Menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences user_id = getSharedPreferences("department_id" , MODE_PRIVATE);
        String department_id_data = user_id.getString("D_ID" , "");
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
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_item:
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                break;  //顯示HOME
            case R.id.schedule_item:
                Intent intent7 = new Intent(MenuActivity.this, ScheduleActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "行程資訊", Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent = new Intent(MenuActivity.this, CalendarActivity.class);
                startActivity(intent);
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Intent intent1 = new Intent(MenuActivity.this, SearchActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; //進入查詢派工資料頁面
            /*case R.id.signature_item:
                Intent intent2 = new Intent(MenuActivity.this, SignatureActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "客戶電子簽名", Toast.LENGTH_SHORT).show();
                break; //進入客戶電子簽名頁面
            case R.id.record_item:
                Intent intent8 = new Intent(MenuActivity.this, RecordActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "上傳日報紀錄", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報紀錄頁面*/
            case R.id.picture_item:
                Intent intent3 = new Intent(MenuActivity.this, PictureActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳頁面
            case R.id.customer_item:
                Intent intent4 = new Intent(MenuActivity.this, CustomerActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢
            /*case R.id.upload_item:
                Intent intent5 = new Intent(MenuActivity.this, UploadActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent6 = new Intent(MenuActivity.this, CorrectActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面*/
            case R.id.about_item:
                Intent intent9 = new Intent(MenuActivity.this, VersionActivity.class);
                startActivity(intent9);
                Toast.makeText(this, "版本資訊", Toast.LENGTH_SHORT).show();
                break; //進入版本資訊頁面
            case R.id.QRCode_item:
                Intent intent10 = new Intent(MenuActivity.this, QRCodeActivity.class);
                startActivity(intent10);
                Toast.makeText(this, "QRCode", Toast.LENGTH_SHORT).show();
                break; //進入QRCode頁面
            case R.id.quotation_item:
                Intent intent11 = new Intent(MenuActivity.this, QuotationActivity.class);
                startActivity(intent11);
                Toast.makeText(this, "報價單審核", Toast.LENGTH_SHORT).show();
                break; //進入報價單審核頁面
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //動態取得 View 物件
        InItFunction();
        //ListView監聽器
        ListViewOnClick();
        //與MenuUserName.php 建立OKHttp連線
        sendRequestWithOkHttp();
        //公告區的各部門下拉選單
        AnnouncementSpinner();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        name_textView = (TextView) findViewById(R.id.name_textView);
        announcement_ListView = (ListView) findViewById(R.id.announcement_listView);
        announcement_spinner = (Spinner) findViewById(R.id.announcement_spinner);
    }

    /**
     * ListView監聽器
     */
    private void ListViewOnClick() {
        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, show_text);
        announcement_ListView.setAdapter(listAdapter);
    }

    /**
     * 與資料庫連線 藉由登入輸入的員工ID取得員工姓名
     */
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data" , MODE_PRIVATE);
                String user_id_data = user_id.getString("ID" , "");
                Log.i("MenuActivity",user_id_data);
                try{
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id",user_id_data)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://220.133.80.146/WQP/MenuUserName.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("MenuActivity",responseData);
                    showResponse(responseData);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 在TextView上SHOW出回傳的員工姓名
     * @param response
     */
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                name_textView.setText(response);
            }
        });
    }

    /**
     * 公告區的各部門下拉選單
     */
    private void AnnouncementSpinner() {
        final String[] announcement = {"全 部 分 類"/*, "內 部 公 告 區", "管 理 部", "財 會 部",
                "水 資 部", "管 財 部", "設 計/經 銷 部", "電 商 部", "技 術 部",
                "行 銷 部", "建 設 部", "D I Y 部", "百 貨 部", "客 服 工 程 部"*/};
        ArrayAdapter<String> announcementList = new ArrayAdapter<>(MenuActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                announcement);
        listAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        announcement_spinner.setAdapter(announcementList);
        announcement_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MenuActivity.this, "你選的是" + announcement[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MenuActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MenuActivity", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MenuActivity", "omRestart");
    }
}


