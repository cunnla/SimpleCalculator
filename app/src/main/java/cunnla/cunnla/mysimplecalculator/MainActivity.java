package cunnla.cunnla.mysimplecalculator;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static android.content.ContentValues.TAG;
import static java.math.MathContext.DECIMAL128;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "myLogs";

    Button btnAdd;
    Button btnSub;
    Button btnMult;
    Button btnDiv;
    Button btnEq;
    Button btnC;

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btn0;
    Button btnDot;

    TextView tvResult;
    int tvResultColorTxt=R.color.colorBtnText1;   //dark grey
    int tvResultColorEq=R.color.colorEquals;      // green
    int tvResultColorC=R.color.colorC;            // red

    MyCalculator myCalculator = new MyCalculator();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnSub.setOnClickListener(this);
        btnMult = (Button) findViewById(R.id.btnMult);
        btnMult.setOnClickListener(this);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnDiv.setOnClickListener(this);
        btnEq = (Button) findViewById(R.id.btnEq);
        btnEq.setOnClickListener(this);
        btnC = (Button) findViewById(R.id.btnC);
        btnC.setOnClickListener(this);

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        btn7 = (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        btn8 = (Button) findViewById(R.id.btn8);
        btn8.setOnClickListener(this);
        btn9 = (Button) findViewById(R.id.btn9);
        btn9.setOnClickListener(this);
        btn0 = (Button) findViewById(R.id.btn0);
        btn0.setOnClickListener(this);
        btnDot = (Button) findViewById(R.id.btnDot);
        btnDot.setOnClickListener(this);

        tvResult = (TextView) findViewById(R.id.tvResult);
        tvResult.setTextColor(getResources().getColor(tvResultColorTxt));
        tvResult.setText("");

        myCalculator.num1 = new BigDecimal("0");
        myCalculator.result = new BigDecimal("0");

    }



    @Override
    public void onClick(View v) {

        tvResult.setTextColor(getResources().getColor(tvResultColorTxt));

        switch (v.getId()){
            case R.id.btn1:
                myCalculator.numberButtonPressed("1");
                break;
            case R.id.btn2:
                myCalculator.numberButtonPressed("2");
                break;
            case R.id.btn3:
                myCalculator.numberButtonPressed("3");
                break;
            case R.id.btn4:
                myCalculator.numberButtonPressed("4");
                break;
            case R.id.btn5:
                myCalculator.numberButtonPressed("5");
                break;
            case R.id.btn6:
                myCalculator.numberButtonPressed("6");
                break;
            case R.id.btn7:
                myCalculator.numberButtonPressed("7");
                break;
            case R.id.btn8:
                myCalculator.numberButtonPressed("8");
                break;
            case R.id.btn9:
                myCalculator.numberButtonPressed("9");
                break;
            case R.id.btn0:
                myCalculator.numberButtonPressed("0");
                break;
            case R.id.btnDot:
                myCalculator.btnDotPressed();
                break;
            case R.id.btnAdd:
                myCalculator.btnAddPressed();
                break;
            case R.id.btnSub:   // the minus button is difficult because its combinations with other operators are allowed!
                myCalculator.btnSubPressed();
                break;
            case R.id.btnMult:
                myCalculator.btnMultPressed();
                break;
            case R.id.btnDiv:
                myCalculator.btnDivPressed();
                break;
            case R.id.btnEq:
                myCalculator.btnEqPressed();
                tvResult.setTextColor(getResources().getColor(tvResultColorEq));
                tvResult.setText(myCalculator.tvResultText);
                break;
            case R.id.btnC:
                myCalculator.clear();
                tvResult.setText("");
                break;
        }


            if (v.getId()!=R.id.btnEq) {      // if Eq button was pressed, we do not append the text, we set it
                tvResult.append(myCalculator.tvResultText);
            }
    }


}
