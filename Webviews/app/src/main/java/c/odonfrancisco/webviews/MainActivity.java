package c.odonfrancisco.webviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

//        webView.loadUrl("http://arkadia.xyz");

        webView.loadData("<html><body><h1>Chreast</h1><h3>Yeast</h3><p>You bitches aint got nothing on me</p></body></html>", "text/html", "UTF-8");

    }
}
