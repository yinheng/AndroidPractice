package com.yh.asynctaskdemo;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyAsyncTask extends AsyncTask<String, Integer, String> {
    private final TextView textView;
    private final ProgressBar progressBar;

    public MyAsyncTask(TextView textView, ProgressBar progressBar) {
        this.textView = textView;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        textView.setText("");
        progressBar.setProgress(0);
        Log.e("MyAsyncTask", "onPreExecute");
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.e("doInBackground", strings[0]);
        String url = strings[0];
        downloadFile(url);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        textView.setText(values[0] + "%");
        progressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        textView.setText("下载完毕");
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        textView.setText("");
        progressBar.setProgress(0);
    }

    private void downloadFile(String url) {
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

                String path = Environment.getDataDirectory().getAbsolutePath() + "/SunloginClient_11.0.0.34335.dmg";
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
                    publishProgress(process);
                }
                fileOutputStream.flush();
            }

        } catch (IOException e) {
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
