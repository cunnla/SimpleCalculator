package cunnla.cunnla.mysimplecalculator;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static android.content.ContentValues.TAG;


    /**
     * Created by Kate on 7/12/2018.
     */

    public class MyCalculator {

        String tvResultText = "";

        BigDecimal num1;
        BigDecimal result;
        String sNum1 = "";               // this string will be converted to float num1
        String oper = "+";               // this is the operation button pressed

        int roundAfterDotDefault = 10;          // by default rounding is to 10 numbers after dot
        int roundAfterDot = roundAfterDotDefault; //this will change during the operations

        boolean dotPressed = false;      // will not allow to press a decimal point twice in a number
        int manyMinuses = 0;             // to prevent pressing more than two minuses
        boolean divisionByZero = false;  // will check if there is division by zero
        boolean operButtonHit = false;


        final static String divisionByZeroTxt = "Error: division by zero!";  // the text is a constant for convenience
        final static String ErrorMessageTxt = "Invalid combination";

        public void clear() {
            sNum1 = "";
            num1 = new BigDecimal("0");
            result = new BigDecimal("0");
            oper = "+";
            dotPressed = false;
            divisionByZero = false;
            operButtonHit = false;
            manyMinuses = 0;
            roundAfterDot = 10;
            tvResultText = "";
            Log.d(TAG, "Clear!");
        }



// here are the methods we use when a button is pressed in onClick

        public void numberButtonPressed(String appendNumber) {
            if ((oper!="=") && !divisionByZero){             //no number can be entered when the result is shown
                Log.d(TAG, appendNumber);
                tvResultText=appendNumber;
                sNum1+=appendNumber;
               // manyMinuses = 0;
            }
            else {
                tvResultText = "";
            }

            Log.d(TAG, "tvResultText: "+ tvResultText);
            Log.d(TAG, "sNum1: "+ sNum1);

        }

        public void btnDotPressed() {
            if ((oper!="=")&& !dotPressed && !divisionByZero){           //no number can be entered when the result is shown
                if (sNum1=="") {
                    tvResultText = "0";     //if we start with a dot we also add a zero in front
                }
                tvResultText = ".";
                sNum1+=".";
                dotPressed=true;
              //  manyMinuses = 0;   //we can enter minuses again
            }
            else {
                tvResultText = "";
            }
            Log.d(TAG, "tvResultText: "+ tvResultText);
            Log.d(TAG, "sNum1: "+ sNum1);
        }

        public void btnAddPressed() {
            if (!sNum1.equals(".") && !sNum1.equals("") &&!operButtonHit){            //if a number is entered
                parseNumber();
                calculation();
                oper="+";
                tvResultText = oper;
                operButtonHit = true;
                manyMinuses = 1;    //so that we cannot enter other operations with minuses
            }
            else {
                tvResultText = "";
            }
        }


        public void btnSubPressed() {   // the minus button is difficult because its combinations with other operators are allowed!

            manyMinuses ++;  // we count how many minuses were entered

            if (divisionByZero || sNum1.equals(".") || (manyMinuses>=3)) {  //cannot type minuses after the division by zero message
                tvResultText = "";      // checking how many minuses we can enter
                return;
            }

            operButtonHit = true;
            parseNumber();

            Log.d(TAG, "Oper: " + oper + ", manyMinuses: "+manyMinuses);
            Log.d(TAG, "divisionByZero: "+divisionByZero);

            if (((oper=="/")||(oper=="*")) && (sNum1 =="")) {              //this is to make such combinations as /- and *- possible

                num1=new BigDecimal("-1");                        // this combination is the same as if we multiply or delete by -1
                calculation();
                tvResultText = "-"; // "-" and not oper because of /- and *-. operator is / or *, but we need to write a minus too.
            }
            else if ((oper=="-") && (sNum1 =="")) {                    // this is to make such combinations as -- possible
                switch (manyMinuses) {
                    case 1:                        // if there is one minus, we just substract
                        Log.d(TAG, "manyMinuses: "+manyMinuses);
                        calculation();
                        oper="+";
                        tvResultText = "-";
                        break;
                    case 2:                       // if there are two minuses, we add
                        Log.d(TAG, "manyMinuses: "+manyMinuses);
                        oper="-";
                        calculation();
                        tvResultText = "-"; // "-" and not oper because of /- and *-. operator is / or *, but we need to write a minus too.;
                        break;
                    case 3:
                        Log.d(TAG, "manyMinuses: "+manyMinuses); // we do not allow three minuses
                        break;
                }
            }
            else {                        // this is for regular substraction, with no sign combinations
                calculation();
                oper="-";
                tvResultText = "-";
            }
        }


        public void btnMultPressed() {
            if (!sNum1.equals(".") && !sNum1.equals("") && !operButtonHit){           //if a number is entered
                parseNumber();
                calculation();
                oper="*";
                tvResultText =oper;
                operButtonHit = true;
                manyMinuses = 1;    //so that we cannot enter other operations with minuses
            }
            else {
                tvResultText = "";
            }
        }

        public void btnDivPressed() {
            if (!sNum1.equals(".") && !sNum1.equals("") && !operButtonHit ){           //if a number is entered
                parseNumber();
                calculation();
                oper="/";
                tvResultText =oper;
                operButtonHit = true;
                manyMinuses = 1;    //so that we cannot enter other operations with minuses
            }
            else {
                tvResultText = "";
            }
        }

        public void btnEqPressed() {
            Log.d(TAG, "sNum1: "+sNum1);
            Log.d(TAG, "manyMinuses: "+manyMinuses);
            Log.d(TAG, "oper: "+oper);

             if (!sNum1.equals(".") && !sNum1.equals("") && !divisionByZero){           // and if a number is entered
                parseNumber();
                operButtonHit = false;                     //we can enter new operations

                if ((num1.compareTo(BigDecimal.ZERO)==0)&& (oper == "/")) {          //avoiding division by zero
                    Log.d(TAG, "num1 is 0 and oper is / ");
                    divisionByZero = true;
                    tvResultText = divisionByZeroTxt;
                    sNum1 ="";
                }
                else {
                    calculation();
                    oper = "=";
                    tvResultText = "" + result;
                    sNum1 = "" + result;         //so that we can enter more operations after the result and calculate further
                    manyMinuses = 0;        //we can enter minuses again
                }
            }
            else {
                tvResultText = ErrorMessageTxt;
                 divisionByZero = true;
                }
        }



        public void parseNumber() {
            if ((sNum1=="") || (sNum1==".")) {
                sNum1="0";
            }

            num1=new BigDecimal(sNum1);
                Log.d(TAG, "String sNum1: "+ sNum1);
                Log.d(TAG, "num1: "+ num1);
                Log.d(TAG, "result: "+ result);

            sNum1="";
            dotPressed = false;   //we can now enter a new number with a dot
        }


        public void calculation(){     //scaling and calculating

            switch (oper){
                case "+":
                    if (num1.scale() > result.scale())       // we set the number
                        roundAfterDot = num1.scale();                // to which the result should be rounded
                    else                                         // in this case it is the bigger number
                        roundAfterDot = result.scale();
                    Log.d(TAG, "roundAfterDot: "+ roundAfterDot);
                    result = result.add(num1);
                    break;

                case "-":
                    if (num1.scale() > result.scale())       // we set the number
                        roundAfterDot = num1.scale();                // to which the result should be rounded
                    else                                          // in this case it is the bigger number
                        roundAfterDot = result.scale();
                    Log.d(TAG, "roundAfterDot: "+ roundAfterDot);
                    result=result.subtract(num1);
                    break;

                case "*":
                    roundAfterDot = num1.scale()+result.scale();      // we set the number to which the result should be rounded
                    Log.d(TAG, "roundAfterDot: "+ roundAfterDot);     // in this case it is the sum of numbers after dots in components
                    result=result.multiply(num1);
                    break;

                case "/":
                    Log.d(TAG, "oper is /");
                    roundAfterDot = roundAfterDotDefault;
                    Log.d(TAG, "roundAfterDot: "+ roundAfterDot);
                    Log.d(TAG, "num1: "+ num1);
                    result=result.divide(num1, roundAfterDot, RoundingMode.HALF_EVEN);
                    Log.d(TAG, "Result after / :"+result);
                    break;
            }

            // now we round the actual result using BigDecimal class and its convenient rounding functions
            result = result.setScale(roundAfterDot, RoundingMode.HALF_EVEN);
            Log.d(TAG, "Result after setScale :"+result);

            //now we strip the result from the zeroes in the end after the dot
            // the stripTrailingZeroes method does not suit here because it also strips zeroes from 10, 20, 100 etc.
            String sResult = result.toString();
            Log.d(TAG, "sResult: "+ sResult);

            if (sResult.contains(".")){
                Log.d(TAG, "Contains dot");
                for (int i=0; i<roundAfterDot+1; i++) {
                    if (sResult != null && sResult.length() > 0 && (sResult.charAt(sResult.length() - 1) == '0')||(sResult.charAt(sResult.length() - 1) == '.')) {
                        sResult = sResult.substring(0, sResult.length() - 1);
                        // Log.d(TAG, "new sResult: " + sResult);
                    } else break;
                }
                result= new BigDecimal(sResult);
            }

            if (result.compareTo(BigDecimal.ZERO)==0) {     // to avoid the error that 0 value is displayed as 0E-10
                result = new BigDecimal("0");
            }

            Log.d(TAG, "result: "+ result);

        }

    }




