package com.yh.mycalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/*
 * 1+1=2
 * */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvScreen;
    private List<Button> btnList = new ArrayList<>();
    private List<ItemData> itemDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScreen = findViewById(R.id.tvScreen);
        btnList.add(findViewById(R.id.button1));
        btnList.add(findViewById(R.id.button2));
        btnList.add(findViewById(R.id.button3));
        btnList.add(findViewById(R.id.button4));
        btnList.add(findViewById(R.id.button5));
        btnList.add(findViewById(R.id.button6));
        btnList.add(findViewById(R.id.button7));
        btnList.add(findViewById(R.id.button8));
        btnList.add(findViewById(R.id.button9));
        btnList.add(findViewById(R.id.button0));
        btnList.add(findViewById(R.id.buttonAdd));
        btnList.add(findViewById(R.id.buttonSubtract));
        btnList.add(findViewById(R.id.buttonMultiply));
        btnList.add(findViewById(R.id.buttonDivide));
        btnList.add(findViewById(R.id.buttonClear));
        btnList.add(findViewById(R.id.buttonEqual));
        for (Button button : btnList) {
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                whenClickNum("1");
                break;
            case R.id.button2:
                whenClickNum("2");
                break;
            case R.id.button3:
                whenClickNum("3");
                break;
            case R.id.button4:
                whenClickNum("4");
                break;
            case R.id.button5:
                whenClickNum("5");
                break;
            case R.id.button6:
                whenClickNum("6");
                break;
            case R.id.button7:
                whenClickNum("7");
                break;
            case R.id.button8:
                whenClickNum("8");
                break;
            case R.id.button9:
                whenClickNum("9");
                break;
            case R.id.button0:
                whenClickNum("0");
                break;
            case R.id.buttonAdd:
                checkAndCompute();
                if (!itemDataList.isEmpty() && (itemDataList.get(itemDataList.size() - 1).getType() == DataType.NUM_TYPE)) {
                    itemDataList.add(new ItemData("+", DataType.ADD_TYPE));
                } else {
                    itemDataList.clear();
                }

                break;
            case R.id.buttonSubtract:
                checkAndCompute();
                if (!itemDataList.isEmpty() && (itemDataList.get(itemDataList.size() - 1).getType() == DataType.NUM_TYPE)) {
                    itemDataList.add(new ItemData("-", DataType.SUBTRACT_TYPE));
                } else {
                    itemDataList.clear();
                }

                break;
            case R.id.buttonMultiply:
                checkAndCompute();
                if (!itemDataList.isEmpty() && (itemDataList.get(itemDataList.size() - 1).getType() == DataType.NUM_TYPE)) {
                    itemDataList.add(new ItemData("*", DataType.MULTIPLY_TYPE));
                } else {
                    itemDataList.clear();
                }
                break;
            case R.id.buttonDivide:
                checkAndCompute();
                if (!itemDataList.isEmpty() && (itemDataList.get(itemDataList.size() - 1).getType() == DataType.NUM_TYPE)) {
                    itemDataList.add(new ItemData("/", DataType.DIVIDE_TYPE));
                } else {
                    itemDataList.clear();
                }
                break;
            case R.id.buttonEqual:
                checkAndCompute();
                break;
            case R.id.buttonClear:
                itemDataList.clear();
                tvScreen.setText("");
                break;
        }

        StringBuilder sb = new StringBuilder();
        for (ItemData itemData : itemDataList) {
            sb.append(itemData.getData());
        }
        tvScreen.setText(sb.toString());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void whenClickNum(String num) {
        if (null != itemDataList && !itemDataList.isEmpty()) {
            ItemData itemData = itemDataList.get(itemDataList.size() - 1);
            if (itemData.getType() != DataType.NUM_TYPE) {
                itemDataList.add(new ItemData(num, DataType.NUM_TYPE));
            } else {
                String newNum = itemData.getData() + num;
                itemDataList.remove(itemDataList.size() - 1);
                itemDataList.add(new ItemData(newNum, DataType.NUM_TYPE));
            }
        } else {
            itemDataList.add(new ItemData(num, DataType.NUM_TYPE));
        }
    }

    private void checkAndCompute() {
        String result = "";
        if (null != itemDataList && !itemDataList.isEmpty()) {
            if (itemDataList.size() == 3) {
                long item1 = Long.parseLong(itemDataList.get(0).getData());
                long item2 = Long.parseLong(itemDataList.get(2).getData());
                int dataType = itemDataList.get(1).getType();
                switch (dataType) {
                    case DataType.ADD_TYPE:
                        itemDataList.clear();
                        itemDataList.add(new ItemData(String.valueOf(item1 + item2), DataType.NUM_TYPE));
                        break;
                    case DataType.SUBTRACT_TYPE:
                        itemDataList.clear();
                        itemDataList.add(new ItemData(String.valueOf(item1 - item2), DataType.NUM_TYPE));
                        break;
                    case DataType.MULTIPLY_TYPE:
                        itemDataList.clear();
                        itemDataList.add(new ItemData(String.valueOf(item1 * item2), DataType.NUM_TYPE));
                        break;
                    case DataType.DIVIDE_TYPE:
                        itemDataList.clear();
                        itemDataList.add(new ItemData(String.valueOf(item1 / item2), DataType.NUM_TYPE));
                        break;
                }
            }
        }
    }
}