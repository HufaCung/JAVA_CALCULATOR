package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private double firstValue = Double.NaN;
    private double secondValue;
    private String currentAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);

        // thiet lap su kien cho may cai button
        setNumberButtonEvents();

        // + - * / thiet lap su kien phep tinh
        setOperationButtonEvents();

        // AC
        findViewById(R.id.btnAC).setOnClickListener(view -> {
            firstValue = Double.NaN;
            secondValue = Double.NaN;
            tvResult.setText("0");
        });

        // +/-
        findViewById(R.id.btnPlusMinus).setOnClickListener(view -> {
            String val = tvResult.getText().toString();
            if (!val.equals("0") && !val.equals("Error")) {
                if (val.startsWith("-")) {
                    tvResult.setText(val.substring(1));
                } else {
                    tvResult.setText("-" + val);
                }
            }
        });

        // %
        findViewById(R.id.btnPercent).setOnClickListener(view -> {
            String val = tvResult.getText().toString().replace(",", ".");
            try {
                double num = Double.parseDouble(val) / 100;
                // dau phay
                tvResult.setText(String.valueOf(num).replace(".", ","));
            } catch (Exception e) {
                tvResult.setText("Error");
            }
        });

        // =
        findViewById(R.id.btnEqual).setOnClickListener(view -> calculate());
    }

    private void setNumberButtonEvents() {
        View.OnClickListener listener = view -> {
            Button b = (Button) view;
            String buttonText = b.getText().toString();
            String currentText = tvResult.getText().toString();

            if (currentText.equals("0") || currentText.equals("Error")) {
                if (buttonText.equals(",")) {
                    tvResult.setText("0,");
                } else {
                    tvResult.setText(buttonText);
                }
            } else {
                // 1 so kh dc co 2 dau phay
                if (buttonText.equals(",") && currentText.contains(",")) return;
                tvResult.append(buttonText);
            }
        };

        int[] numberIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot};
        for (int id : numberIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperationButtonEvents() {
        View.OnClickListener actionListener = view -> {
            Button b = (Button) view;
            try {
                // dau phay thanh dau .
                String val = tvResult.getText().toString().replace(",", ".");
                firstValue = Double.parseDouble(val);
                currentAction = b.getText().toString().trim();
                tvResult.setText("0");
            } catch (Exception e) {
                firstValue = Double.NaN;
            }
        };

        findViewById(R.id.btnPlus).setOnClickListener(actionListener);
        findViewById(R.id.btnMinus).setOnClickListener(actionListener);
        findViewById(R.id.btnMultiply).setOnClickListener(actionListener);
        findViewById(R.id.btnDivide).setOnClickListener(actionListener);
    }

    private void calculate() {
        if (!Double.isNaN(firstValue)) {
            try {
                String val = tvResult.getText().toString().replace(",", ".");
                secondValue = Double.parseDouble(val);
                double result = 0;

                if (currentAction.contains("+")) result = firstValue + secondValue;
                else if (currentAction.contains("-")) result = firstValue - secondValue;
                else if (currentAction.contains("×")) result = firstValue * secondValue;
                else if (currentAction.contains("÷")) {
                    if (secondValue != 0) result = firstValue / secondValue;
                    else { tvResult.setText("Error"); return; }
                }

                // hien thi kqua
                String finalResult;
                if (result == (long) result)
                    finalResult = String.valueOf((long) result); // so nguyen
                else
                    finalResult = String.valueOf(result); // thap phan

                // hien thi ra man hinh, dau cham thanh phay
                tvResult.setText(finalResult.replace(".", ","));

                // phep tinh moi
                firstValue = Double.NaN;
                currentAction = null;

            } catch (Exception e) {
                tvResult.setText("Error");
            }
        }
    }
}