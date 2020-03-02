package com.example.a10609516.app.Tools;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.a10609516.app.Basic.MenuActivity;
import com.example.a10609516.app.Basic.QRCodeActivity;
import com.example.a10609516.app.Basic.RequisitionActivity;
import com.example.a10609516.app.Basic.RequisitionSearchActivity;
import com.example.a10609516.app.Workers.Worker_SignatureActivity;
import com.example.a10609516.app.Basic.VersionActivity;
import com.example.a10609516.app.Clerk.QuotationActivity;
import com.example.a10609516.app.DepartmentAndDIY.CorrectActivity;
import com.example.a10609516.app.DepartmentAndDIY.CustomerActivity;
import com.example.a10609516.app.DepartmentAndDIY.PictureActivity;
import com.example.a10609516.app.DepartmentAndDIY.RecordActivity;
import com.example.a10609516.app.DepartmentAndDIY.UploadActivity;
import com.example.a10609516.app.Manager.InventoryActivity;
import com.example.a10609516.app.Manager.OrderSearchActivity;
import com.example.a10609516.app.R;
import com.example.a10609516.app.Workers.CalendarActivity;
import com.example.a10609516.app.Workers.EngPointsActivity;
import com.example.a10609516.app.Workers.GPSActivity;
import com.example.a10609516.app.Workers.MissCountActivity;
import com.example.a10609516.app.Workers.PointsActivity;
import com.example.a10609516.app.Workers.ScheduleActivity;
import com.example.a10609516.app.Workers.SearchActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class WQPServiceActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //確認是否有最新版本，進行更新
        CheckFirebaseVersion();
    }

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
                || user_id_data.toString().equals("10012043") || user_id_data.toString().equals("10304116") || user_id_data.toString().equals("10405235")) {
            //工務主管  //09706013 周威廷  //09908023 劉欣亨  //10010039 劉英任  //10012043 洪寬耀  //10304116 陳信華  //10405235 郭哲毓
            getMenuInflater().inflate(R.menu.workers_manager_menu, menu);
            return true;
        }else if (department_id_data.toString().equals("1100")) {
            //內勤
            getMenuInflater().inflate(R.menu.logistics_menu, menu);
            return true;
        }else if (department_id_data.toString().equals("2100")) {
            //業務主管
            getMenuInflater().inflate(R.menu.clerk_menu, menu);
            return true;
        } else if (department_id_data.toString().equals("2200")) {
            //外點業務
            getMenuInflater().inflate(R.menu.diy_menu, menu);
            return true;
        } else if (department_id_data.toString().equals("5200")) {
            //工務
            getMenuInflater().inflate(R.menu.workers_menu, menu);
            return true;
        } else {
            //超級使用者
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
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home_item:
                Intent intent0 = new Intent(this, MenuActivity.class);
                startActivity(intent0);
                Toast.makeText(this, "HOME",Toast.LENGTH_SHORT).show();
                finish();
                break; //返回首頁
            /**工務部Menu*/
            case R.id.schedule_item:
                Intent intent1 = new Intent(this, ScheduleActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "行程資訊",Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent2 = new Intent(this, CalendarActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "派工行事曆",Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Intent intent3 = new Intent(this, SearchActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "查詢派工資料",Toast.LENGTH_SHORT).show();
                break; //進入查詢派工資料頁面
            case R.id.points_item:
                Intent intent4 = new Intent(this, PointsActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "我的點數", Toast.LENGTH_SHORT).show();
                break; //進入查詢工務點數頁面
            case R.id.eng_points_item:
                Intent intent5 = new Intent(this, EngPointsActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "工務點數明細", Toast.LENGTH_SHORT).show();
                break; //進入工務點數明細頁面
            case R.id.miss_item:
                Intent intent6 = new Intent(this, MissCountActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "未回單數量", Toast.LENGTH_SHORT).show();
                break; //進入工務未回單數量頁面
            case R.id.map_item:
                Intent intent7 = new Intent(this, GPSActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "工務打卡GPS", Toast.LENGTH_SHORT).show();
                break; //進入GPS地圖頁面
            case R.id.signature_item:
                Intent intent42 = new Intent(this, Worker_SignatureActivity.class);
                startActivity(intent42);
                Toast.makeText(this, "客戶電子簽名", Toast.LENGTH_SHORT).show();
                break; //進入客戶電子簽名頁面
            /**業務部Menu*/
            case R.id.quotation_item:
                Intent intent11 = new Intent(this, QuotationActivity.class);
                startActivity(intent11);
                Toast.makeText(this, "報價單審核", Toast.LENGTH_SHORT).show();
                break; //進入報價單審核頁面
            /**百貨/DIY部Menu*/
            case R.id.record_item:
                Intent intent21 = new Intent(this, RecordActivity.class);
                startActivity(intent21);
                Toast.makeText(this, "上傳日報紀錄",Toast.LENGTH_SHORT).show();
                break; //進入上傳日報紀錄頁面
            case R.id.picture_item:
                Intent intent22 = new Intent(this, PictureActivity.class);
                startActivity(intent22);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳頁面
            case R.id.customer_item:
                Intent intent23 = new Intent(this, CustomerActivity.class);
                startActivity(intent23);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢頁面
            case R.id.upload_item:
                Intent intent24 = new Intent(this, UploadActivity.class);
                startActivity(intent24);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent25 = new Intent(this, CorrectActivity.class);
                startActivity(intent25);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面
            /**管理部Menu*/
            case R.id.inventory_item:
                Intent intent31 = new Intent(this, InventoryActivity.class);
                startActivity(intent31);
                Toast.makeText(this, "倉庫盤點", Toast.LENGTH_SHORT).show();
                break; //進入倉庫盤點管理頁面
            case R.id.picking_item:
                Intent intent32 = new Intent(this, OrderSearchActivity.class);
                startActivity(intent32);
                Toast.makeText(this, "撿料單", Toast.LENGTH_SHORT).show();
                break; //進入撿料單頁面
            /**共用Menu*/
            case R.id.QRCode_item:
                Intent intent41 = new Intent(this, QRCodeActivity.class);
                startActivity(intent41);
                Toast.makeText(this, "QRCode", Toast.LENGTH_SHORT).show();
                break; //進入QRCode頁面
            case R.id.requisition_item:
                Intent intent44 = new Intent(this, RequisitionActivity.class);
                startActivity(intent44);
                Toast.makeText(this, "需求申請單", Toast.LENGTH_SHORT).show();
                break; //進入需求單頁面
            case R.id.progress_item:
                Intent intent45 = new Intent(this, RequisitionSearchActivity.class);
                startActivity(intent45);
                Toast.makeText(this, "需求單進度查詢", Toast.LENGTH_SHORT).show();
                break; //進入需求單頁面
            case R.id.about_item:
                Intent intent43 = new Intent(this, VersionActivity.class);
                startActivity(intent43);
                Toast.makeText(this, "版本資訊", Toast.LENGTH_SHORT).show();
                break; //進入版本資訊頁面
            default:
        }
        return true;
    }

    /**
     * 日期挑選器
     * @param v
     */
    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        Bundle bData = new Bundle();
        bData.putInt("view", v.getId());
        Button button = (Button) v;
        bData.putString("date", button.getText().toString());
        newFragment.setArguments(bData);
        newFragment.show(getSupportFragmentManager(), "日期挑選器");
    }

    /**
     * 點擊空白區域，隐藏虛擬鍵盤
     */
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);
    }

    /**
     * 確認是否有最新版本，進行更新
     */
    private void CheckFirebaseVersion() {
        SharedPreferences fb_version = getSharedPreferences("fb_version", MODE_PRIVATE);
        final String version = fb_version.getString("FB_VER", "");
        Log.e("WQPServiceActivity", version);

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
                Log.e("WQPServiceActivity", "已讀取到值:" + data);
                if (version.equals(data)) {
                } else {
                    new AlertDialog.Builder(WQPServiceActivity.this)
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
                                                    Log.e("WQPServiceActivity", "11111");
                                                    WQPServiceActivity.this.Update();
                                                    Log.e("WQPServiceActivity", "22222");
                                                }
                                            }.start();
                                        }
                                    }).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("WQPServiceActivity", "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * 下載新版本APK
     */
    public void Update() {
        try {
            //URL url = new URL("http://192.168.0.201/wqp_2.5.apk");
            URL url = new URL("http://m.wqp-water.com.tw/wqp_2.5.apk");
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            //c.setRequestMethod("GET");
            //c.setDoOutput(true);
            c.connect();

            String PATH = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/";
            //String PATH2 = Environment.getExternalStorageDirectory().getPath() + "/Download/";
            //String PATH = System.getenv("SECONDARY_STORAGE") + "/Download/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "wqp_2.5.apk");
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
            intent.setDataAndType(Uri.fromFile(new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + "wqp_2.5.apk")), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "開始安裝新版本", Toast.LENGTH_LONG).show();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("下載錯誤!", e.toString());
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "更新失敗!", Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}

