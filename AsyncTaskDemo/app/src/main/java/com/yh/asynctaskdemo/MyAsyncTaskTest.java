package com.yh.asynctaskdemo;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyAsyncTaskTest extends AsyncTaskTest<String, Integer, String> {
    private TextView textView;
    private ProgressBar progressBar;
    private Context context;
    public MyAsyncTaskTest(TextView textView, ProgressBar progressBar){
        this.textView = textView;
        this.progressBar = progressBar;
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
        textView.setText("开始下载");
        progressBar.setProgress(0);
    }

    @Override
    public String doInBackground(String... strings) {
        Log.e("doInBackground", strings[0]);
        String url = strings[0];
        downloadFile(url);
        return null;
    }

    @Override
    public void postExecute(String s) {
        super.postExecute(s);
        textView.setText("下载完成");
        progressBar.setProgress(0);

    }

    @Override
    public void processUpdate() {

    }
    private void downloadFile(String url) {
        Log.e("download", "begin");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest_interface request = retrofit.create(GetRequest_interface.class);
        Call<ResponseBody> call = request.getCall("SunloginClient_11.0.0.34335.dmg");
        InputStream is = null;
        FileOutputStream fileOutputStream = null;
        try {
            Response<ResponseBody> response = call.execute();
            if (response.code() == 200 && response.body() != null) {
                long total = response.body().contentLength();
                is = response.body().byteStream();
                byte[] bytes = new byte[2048];

                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SunloginClient_11.0.0.34335.dmg";
                path = "data/data/com.yh.asynctaskdemo/SunloginClient_11.0.0.34335.dmg";
                File file = new File(path);

                int len;
                long sum = 0;

                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();

                fileOutputStream = new FileOutputStream(file);
                while ((len = is.read(bytes)) != -1) {

                    fileOutputStream.write(bytes, 0, len);
                    sum += len;
                    int process = (int) (sum * 100.0 / total);
                    //publishProgress(process);
                }
                fileOutputStream.flush();
                Thread.sleep(5000);
                Log.e("download", "end");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (is != null) {
                    is.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
