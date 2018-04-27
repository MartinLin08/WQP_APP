package com.example.a10609516.app.Basic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a10609516.app.DepartmentAndDIY.CustomerActivity;
import com.example.a10609516.app.DepartmentAndDIY.PictureActivity;
import com.example.a10609516.app.Element.SignView;
import com.example.a10609516.app.R;
import com.example.a10609516.app.Workers.CalendarActivity;
import com.example.a10609516.app.Workers.ScheduleActivity;
import com.example.a10609516.app.Workers.SearchActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignatureActivity extends AppCompatActivity {
    private SignView mView;
    private Button commit_btn,clear_btn;
    private Bitmap mSignBitmap;
    String signPath;
    String sign_name;

    private OkHttpClient client;

    /**
     * 創建Menu
     * @param menu
     * @return
     */
    /*public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    /**
     * 進入Menu各個頁面
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_item:
                Intent intent = new Intent(SignatureActivity.this, MenuActivity.class);
                startActivity(intent);
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                finish();
                break; //返回首頁
            case R.id.schedule_item:
                Intent intent7 = new Intent(SignatureActivity.this, ScheduleActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "行程資訊",Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent1 = new Intent(SignatureActivity.this, CalendarActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Intent intent2 = new Intent(SignatureActivity.this, SearchActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; //進入查詢派工資料頁面
            /*case R.id.signature_item:
                Toast.makeText(this, "客戶電子簽名", Toast.LENGTH_SHORT).show();
                break; //顯示客戶電子簽名*/
            /*case R.id.record_item:
                Intent intent8 = new Intent(SignatureActivity.this, RecordActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "上傳日報紀錄",Toast.LENGTH_SHORT).show();
                break; //進入上傳日報紀錄頁面*/
            case R.id.picture_item:
                Intent intent3 = new Intent(SignatureActivity.this, PictureActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳頁面
            case R.id.customer_item:
                Intent intent4 = new Intent(SignatureActivity.this, CustomerActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢頁面
            /*case R.id.upload_item:
                Intent intent5 = new Intent(SignatureActivity.this, UploadActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent6 = new Intent(SignatureActivity.this, CorrectActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面*/
            case R.id.QRCode_item:
                Intent intent10 = new Intent(SignatureActivity.this, QRCodeActivity.class);
                startActivity(intent10);
                Toast.makeText(this, "QRCode", Toast.LENGTH_SHORT).show();
                break; //進入QRCode頁面
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        //動態取得 View 物件
        InItFunction();
        client = new OkHttpClient();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        mView = (SignView) findViewById(R.id.signView);
        commit_btn = (Button) findViewById(R.id.commit_btn);
        clear_btn = (Button) findViewById(R.id.clear_btn);
        commit_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                saveSign(mView.getCachebBitmap());
                sendRequestWithOkHttpOfSignature();
                finish();
            }
        });
        clear_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mView.clear();
            }
        });
    }

    /**
     * signPath是圖片保存路徑
     * @param bit
     */
    public void saveSign(Bitmap bit){
        //儲存路徑
        mSignBitmap = bit;
        signPath = createFile();
    }

    /**
     * 生成Bitmap儲存簽名
     * @return
     */
    public String createFile() {
        //建立簽名檔儲存
        ByteArrayOutputStream byteArrayOutputStream = null;
        String _path = null;
        try {
            //接收LoginActivity傳過來的值
            SharedPreferences user_id = getSharedPreferences("user_id_data" , MODE_PRIVATE);
            String user_id_data = user_id.getString("ID" , "");
            Log.e("SignatureActivity",user_id_data);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String date = simpleDateFormat.format(new java.util.Date());
            Bundle bundle = getIntent().getExtras();
            String SN_NO = bundle.getString("ResponseText1");
            String sign_dir = Environment.getExternalStorageDirectory().getPath() + "/Pictures/";
            _path = sign_dir + "Sign_"  + SN_NO + "_" +user_id_data + "_" + date+".png";
            sign_name = "Sign_" + SN_NO + "_" +user_id_data + "_" + date;
            Log.e("TAG",_path);
            byteArrayOutputStream = new ByteArrayOutputStream();
            mSignBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] photoBytes = byteArrayOutputStream.toByteArray();
            if (photoBytes != null) {
                new FileOutputStream(new File(_path)).write(photoBytes);
                //與OKHttp連線上傳簽名到SEVER端
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("img_1", sign_name + ".png", RequestBody.create(MediaType.parse("image/png"),photoBytes));
                MultipartBody build = builder.build();
                okhttp3.Request bi = new okhttp3.Request.Builder()
                        .url("http://220.133.80.146/WQP/SignaturePicture.php")
                        .post(build)
                        .build();
                client.newCall(bi).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("SignatureActivity","onFailure : 失敗");
                    }
                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        Log.i("SignatureActivity","onResponse : "+response.body().string());
                        //提交成功處理結果
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (byteArrayOutputStream != null)
                    byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return _path;
    }

    /**
     * 與OkHttp建立連線(SignaturePicture)
     */
    private void sendRequestWithOkHttpOfSignature() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.i("SignatureActivity", user_id_data);

                Bundle bundle = getIntent().getExtras();
                String SN_NO = bundle.getString("ResponseText1");
                String SEQ_ID = bundle.getString("ResponseText2");
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .add("ESVD_SEQ_ID", SEQ_ID)
                            .add("ESVD_SERVICE_NO", SN_NO)
                            .add("SIGN_FILE_NAME", sign_name+".png")
                            .build();
                    Log.e("SignatureActivity", user_id_data);
                    Log.e("SignatureActivity", SEQ_ID);
                    Log.e("SignatureActivity", SN_NO);
                    Log.e("SignatureActivity", sign_name+".png");
                    Request request = new Request.Builder()
                            .url("http://220.133.80.146/WQP/SignatureLog.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("SignatureActivity", responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     *Destroy
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SignatureActivity", "onDestroy");
        if (mSignBitmap != null){
            mSignBitmap.recycle();
        }
    }
}
