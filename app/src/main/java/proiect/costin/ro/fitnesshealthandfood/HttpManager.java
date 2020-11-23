package proiect.costin.ro.fitnesshealthandfood;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
*
* Clasa care se ocupa de paralelism si furnizeaza streamul de sir de caractere pentru
* aplicatie
*
* */

public class HttpManager extends AsyncTask<String, Void, String> {

    private HttpURLConnection connection;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();

            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(inputStreamReader != null){
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(connection != null){
                connection.disconnect();
            }
        }
        return result.toString();
    }
}
