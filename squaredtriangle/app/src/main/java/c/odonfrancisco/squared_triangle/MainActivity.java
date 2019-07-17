package c.odonfrancisco.squared_triangle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private void toaster(String content) {
        Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
    }

    private double getDecimal(double num){
        return Math.abs(Math.floor(num)) - Math.abs(num);
    }

    class Num {
        int num;

        private boolean isSquare(){

            double squareRoot = Math.pow(num, 0.5);
            double squareDecimal = getDecimal(squareRoot);

            Log.i("Num: ", String.valueOf(num));

            if(squareDecimal == 0){
//                toaster("true!");
                 return true;
            }
//            toaster("false :(");
            return false;
        }

        private boolean isTriangle(){
             double quadSquare = 1 - 4*(num*(-2));

             double quadRoot = Math.pow(quadSquare, 0.5);
             double quadDecimal = getDecimal(quadRoot);

             if(quadDecimal == 0){
//                 toaster("true!");
                 return true;
             }
//             toaster("false :(");
             return false;
        }



    }


    public void squareNum(View view){

        EditText numInput = findViewById(R.id.numberInput);
        String numString = numInput.getText().toString();

        if(numString.length() > 0) {

            Num number = new Num();

            number.num = Integer.parseInt(numString);

            if(number.isTriangle()){
                if(number.isSquare()){
                    toaster("This is both a Triangle && Square");
                } else {
                    toaster("This is a Triangle");
                }
            } else {
                if(number.isSquare()){
                    toaster("This is a Square!");
                } else {
                    toaster("This is neither a Square nor a Triangle");
                }

            }


        } else {
            toaster("Ya dingus, put a number in there!");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
}
