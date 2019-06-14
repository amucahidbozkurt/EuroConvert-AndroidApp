package com.ahmetmb.currencyconvert;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView chfText, usdText, jpyText, tryText, cadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chfText = findViewById(R.id.chfText);
        usdText = findViewById(R.id.usdText);
        jpyText = findViewById(R.id.jpyText);
        tryText = findViewById(R.id.tryText);
        cadText = findViewById(R.id.cadText);

    }

    public void getRates (View view) {

        DownloadData downloadData = new DownloadData();

        try {

            String url = "http://data.fixer.io/api/latest?access_key=df1141e9a9fab33d13bc8140c1f6b930&format=1";

            downloadData.execute(url);

        } catch (Exception e) {

        }

    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {

                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data > 0) {

                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();

                }

                return result;

            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                // JSON'dan veriyi getirmek
                JSONObject jsonObject = new JSONObject(s);

                String rates = jsonObject.getString("rates");

                // rates alt elemanlari cekmek icin baska bir jsonObject olusturuyoruz
                JSONObject jsonObject1 = new JSONObject(rates);

                String chf = jsonObject1.getString("CHF");
                chfText.setText("CHF: " + chf);

                String usd = jsonObject1.getString("USD");
                usdText.setText("USD: " + usd);

                String jpy = jsonObject1.getString("JPY");
                jpyText.setText("JPY: " + jpy);

                String turkishLira = jsonObject1.getString("TRY");
                tryText.setText("TRY: " + turkishLira);

                String cad = jsonObject1.getString("CAD");
                cadText.setText("CAD: " + cad);

            } catch (Exception e) {

            }

        }
    }

}
