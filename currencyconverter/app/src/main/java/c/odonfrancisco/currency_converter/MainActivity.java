package c.odonfrancisco.currency_converter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {

    public void convertCurrency(View view) {
        EditText quantityInput = findViewById(R.id.quantityInput);
        String quantityIn = quantityInput.getText().toString();

        Boolean yuh = quantityIn.length()>0;

        Log.i("yuh", yuh.toString());
        switch(yuh.toString()) {
            case "true":
                int quantity = Integer.parseInt(quantityIn.toString());

                Double convertedQuantity;
                convertedQuantity = quantity*27.91;

                String message = "$" + quantity + " is equivalent to  "
                        + "ብር" + convertedQuantity + " Ethiopian Birr";


                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                break;
            case "false":
                Toast.makeText(MainActivity.this,
                        "Can't convert 0 ya dingus", Toast.LENGTH_LONG).show();
                break;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
