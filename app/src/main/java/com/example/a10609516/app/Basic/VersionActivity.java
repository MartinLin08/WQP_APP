package com.example.a10609516.app.Basic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10609516.app.Clerk.QuotationActivity;
import com.example.a10609516.app.DepartmentAndDIY.CustomerActivity;
import com.example.a10609516.app.DepartmentAndDIY.PictureActivity;
import com.example.a10609516.app.R;
import com.example.a10609516.app.Workers.CalendarActivity;
import com.example.a10609516.app.Workers.ScheduleActivity;
import com.example.a10609516.app.Workers.SearchActivity;

public class VersionActivity extends AppCompatActivity {

    private TextView detail_txt1, detail_txt2;
    private LinearLayout detail_llt1, detail_llt2;
    private Button version_btn1, version_btn2;
    private Button version_up_btn1, version_up_btn2;


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
                Intent intent = new Intent(VersionActivity.this, MenuActivity.class);
                startActivity(intent);
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                finish();
                break; //返回首頁
            case R.id.schedule_item:
                Intent intent7 = new Intent(VersionActivity.this, ScheduleActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "行程資訊",Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent1 = new Intent(VersionActivity.this, CalendarActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Intent intent2 = new Intent(VersionActivity.this, SearchActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; //進入查詢派工資料頁面
            /*case R.id.signature_item:
                Toast.makeText(this, "客戶電子簽名", Toast.LENGTH_SHORT).show();
                break; //顯示客戶電子簽名*/
            /*case R.id.record_item:
                Intent intent8 = new Intent(VersionActivity.this, RecordActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "上傳日報紀錄",Toast.LENGTH_SHORT).show();
                break; //進入上傳日報紀錄頁面*/
            case R.id.picture_item:
                Intent intent3 = new Intent(VersionActivity.this, PictureActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳頁面
            case R.id.customer_item:
                Intent intent4 = new Intent(VersionActivity.this, CustomerActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢頁面
            /*case R.id.upload_item:
                Intent intent5 = new Intent(VersionActivity.this, UploadActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent6 = new Intent(VersionActivity.this, CorrectActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面*/
            case R.id.about_item:
                Toast.makeText(this, "版本資訊", Toast.LENGTH_SHORT).show();
                break; //顯示版本資訊
            case R.id.QRCode_item:
                Intent intent10 = new Intent(VersionActivity.this, QRCodeActivity.class);
                startActivity(intent10);
                Toast.makeText(this, "QRCode", Toast.LENGTH_SHORT).show();
                break; //進入QRCode頁面
            case R.id.quotation_item:
                Intent intent11 = new Intent(VersionActivity.this, QuotationActivity.class);
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
        setContentView(R.layout.activity_version);
        //動態取得 View 物件
        InItFunction();
        //版本詳細資訊
        DetailOfVersion();
        //查看版本
        CheckWhatDetail();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction(){
        detail_txt1 = (TextView)findViewById(R.id.detail_txt1);
        detail_txt2 = (TextView)findViewById(R.id.detail_txt2);
        detail_llt1 = (LinearLayout)findViewById(R.id.detail_llt1);
        detail_llt2 = (LinearLayout)findViewById(R.id.detail_llt2);
        version_btn1 = (Button)findViewById(R.id.version_btn1);
        version_btn2 = (Button)findViewById(R.id.version_btn2);
        version_up_btn1 = (Button)findViewById(R.id.version_up_btn1);
        version_up_btn2 = (Button)findViewById(R.id.version_up_btn2);
    }

    /**
     * 版本詳細資訊
     */
    private void DetailOfVersion(){
        detail_txt1.setText("1.新增工務部 - 行程資訊、派工行事曆、查詢派工資料 \n" +
                "2.新增工務部 - 出勤維護回報功能 \n" +
                "3.新增推播功能-工務(新派工、更新派工、取消派工) \n" +
                "4.新增客戶電子簽名、QRCode功能");
        detail_txt2.setText("1.新增業務部 - 報價單審核");
    }

    /**
     * 查看版本
     */
    private void CheckWhatDetail() {
        version_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt1.setVisibility(View.VISIBLE);
                version_up_btn1.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt1.setVisibility(View.GONE);
                version_up_btn1.setVisibility(View.GONE);
            }
        });
        version_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt2.setVisibility(View.VISIBLE);
                version_up_btn2.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt2.setVisibility(View.GONE);
                version_up_btn2.setVisibility(View.GONE);
            }
        });
    }
}
