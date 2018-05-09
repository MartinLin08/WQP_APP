package com.example.a10609516.app.Basic;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10609516.app.Clerk.QuotationActivity;
import com.example.a10609516.app.DepartmentAndDIY.CustomerActivity;
import com.example.a10609516.app.DepartmentAndDIY.PictureActivity;
import com.example.a10609516.app.R;
import com.example.a10609516.app.Workers.CalendarActivity;
import com.example.a10609516.app.Element.ScannerActivity;
import com.example.a10609516.app.Workers.ScheduleActivity;
import com.example.a10609516.app.Workers.SearchActivity;

import java.text.SimpleDateFormat;

public class QRCodeActivity extends AppCompatActivity {

    private Button QRCode_btn;
    private TextView date_txt, result_txt;
    private WebView mWebView;

    //轉畫面的Activity參數
    private Class<?> mClss;
    //ZXING_CAMERA權限
    private static final int ZXING_CAMERA_PERMISSION = 1;

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
     *
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_item:
                Intent intent = new Intent(QRCodeActivity.this, MenuActivity.class);
                startActivity(intent);
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                finish();
                break; //返回首頁
            case R.id.schedule_item:
                Intent intent7 = new Intent(QRCodeActivity.this, ScheduleActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "行程資訊", Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent11 = new Intent(QRCodeActivity.this, CalendarActivity.class);
                startActivity(intent11);
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //顯示派工行事曆
            case R.id.work_item:
                Intent intent1 = new Intent(QRCodeActivity.this, SearchActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; //進入查詢派工資料頁面
            /*case R.id.signature_item:
                Intent intent2 = new Intent(CalendarActivity.this, SignatureActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "客戶電子簽名", Toast.LENGTH_SHORT).show();
                break; //進入客戶電子簽名頁面*/
            /*case R.id.record_item:
                Intent intent8 = new Intent(QRCodeActivity.this, RecordActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "上傳日報紀錄", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報紀錄頁面*/
            case R.id.picture_item:
                Intent intent3 = new Intent(QRCodeActivity.this, PictureActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳頁面
            case R.id.customer_item:
                Intent intent4 = new Intent(QRCodeActivity.this, CustomerActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢頁面
            /*case R.id.upload_item:
                Intent intent5 = new Intent(QRCodeActivity.this, UploadActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent6 = new Intent(QRCodeActivity.this, CorrectActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面*/
            case R.id.about_item:
                Intent intent9 = new Intent(QRCodeActivity.this, VersionActivity.class);
                startActivity(intent9);
                Toast.makeText(this, "版本資訊", Toast.LENGTH_SHORT).show();
                break; //進入版本資訊頁面
            case R.id.QRCode_item:
                Intent intent10 = new Intent(QRCodeActivity.this, QRCodeActivity.class);
                startActivity(intent10);
                Toast.makeText(this, "QRCode", Toast.LENGTH_SHORT).show();
                break; //進入QRCode頁面
            case R.id.quotation_item:
                Intent intent12 = new Intent(QRCodeActivity.this, QuotationActivity.class);
                startActivity(intent12);
                Toast.makeText(this, "報價單審核", Toast.LENGTH_SHORT).show();
                break; //進入報價單審核頁面
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        //動態取得 View 物件
        InItFunction();
        //獲取當天日期
        GetDate();
        //初始畫面設置
        initSet();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        QRCode_btn = (Button) findViewById(R.id.QRCode_btn);
        date_txt = (TextView) findViewById(R.id.date_txt);
        result_txt = (TextView) findViewById(R.id.result_txt);
        mWebView = (WebView) findViewById(R.id.wv);

        /*QRCode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                if(getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size()==0)
                {
                    //未安裝
                    Toast.makeText(QRCodeActivity.this, "請至 Play商店 安裝 ZXing 條碼掃描器", Toast.LENGTH_LONG).show();
                }else{
                    //SCAN_MODE, 可判別所有支援條碼
                    //QR_CODE_MODE, 只判別QR_CODE
                    //PRODUCT_MODE, UPC and EAN條碼
                    //ONE_D_MODE, 1維條碼
                    intent.putExtra("SCAN_MODE","SCAN_MODE");

                    //呼叫ZXing Scanner,完成動作後回傳1給onActivityResult的requestCode參數
                    startActivityForResult(intent,1);
                }
            }
        });*/
    }

    /**
     * 取回掃描回傳值或取消掃描
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    /*public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //ZXing回傳的內容
                String contents = intent.getStringExtra("SCAN_RESULT");
                result_txt.setText(contents.toString());
            }else{
                if(resultCode==RESULT_CANCELED){
                    Toast.makeText(QRCodeActivity.this, "取消掃描", Toast.LENGTH_LONG).show();
                }
            }
        }
    }*/

    /**
     * 獲取當天日期
     */
    private void GetDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new java.util.Date());
        date_txt.setText(date);
    }

    /**
     * 起始畫面設置
     */
    private void initSet() {
        /**
         * 以下都是WebView的設定
         */
        WebSettings websettings = mWebView.getSettings();
        websettings.setSupportZoom(true); //啟用內置的縮放功能
        websettings.setBuiltInZoomControls(true);//啟用內置的縮放功能
        websettings.setDisplayZoomControls(false);//讓縮放功能的Button消失
        websettings.setJavaScriptEnabled(true);//使用JavaScript
        websettings.setAppCacheEnabled(true);//設置啟動緩存
        websettings.setSaveFormData(true);//設置儲存
        websettings.setAllowFileAccess(true);//啟用webview訪問文件數據
        websettings.setDomStorageEnabled(true);//啟用儲存數據
        mWebView.setWebViewClient(new WebViewClient());
    }

    //Button的設置
    public void scanCode(View view) {
        //startActivityForResult(new Intent(this, ScannerActivity.class), 1);
        launchActivity(ScannerActivity.class);
    }

    //轉畫面的封包，兼具權限和Intent跳轉化面
    public void launchActivity(Class<?> clss) {
        //假如還「未獲取」權限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            //startActivity(intent);
            startActivityForResult(intent, ZXING_CAMERA_PERMISSION);
        }
    }

    //當ScannerActivity結束後的回調資訊
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.d("checkpoint", "CheckPoint");
            result_txt.setText(data.getStringExtra("result_text"));
            mWebView.loadUrl(data.getStringExtra("result_text"));
        }
    }
}