package com.example.android.mcq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int marks;
    String resultOutput;
    QuestionType RQ = QuestionType.RADIO_QUESTIONS;
    QuestionType CQ = QuestionType.CHECKBOX_QUESTIONS;
    QuestionType EQ = QuestionType.EDITTEXT_QUESTIONS;
    QuestionType COQ = QuestionType.COMBINED_QUESTIONS;

    private RadioGroup radioGroupQuestion;

    public static boolean validateEditTextAnswer(EditText editText) {
        String input = editText.getText().toString().trim();
        return input.length() == 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Result(View view) {

//        Here we Clear the TextView For Result
        resultOutput = "";
        marks = 0;
        TextView outputTextView = (TextView) findViewById(R.id.Result);
        outputTextView.setText(resultOutput);
/*
        Every question has a certain type i.e. Radio, CheckBox, EditText and CombinedType
                first parameter is Question Type (Enum)
                second parameter is Question No (String).
                third parameter is answer of that Question (String).
                */
        calculateResult(RQ, getText(R.string.qNo1).toString(), getText(R.string.opt1_2).toString());
        calculateResult(CQ, getText(R.string.qNo2).toString(), getText(R.string.opt2_1).toString() + getText(R.string.opt2_2).toString());
        calculateResult(EQ, getText(R.string.qNo3).toString(), getText(R.string.opt3_1).toString());
        calculateResult(COQ, getText(R.string.qNo4).toString(), getText(R.string.opt4_4).toString());

//        here we will display result along with the total marks on the screen.
        displayResult(resultOutput + "\n" + getText(R.string.totalMarks) + " " + marks);
    }

    //    each method will follow the appropriate case
    private void calculateResult(QuestionType Type, String qNo, String answer) {
        switch (Type) {
            case RADIO_QUESTIONS:
                //Radio Questions answers are validated and checked here
                radioQuestions(qNo, answer);
                break;

            case CHECKBOX_QUESTIONS:
                //CheckBox Questions answers are validated and checked here
                checkBoxQuestions(qNo, answer);
                break;

            case EDITTEXT_QUESTIONS:
                //EditText Questions answers are validated and checked here
                editTextQuestions(qNo, answer);
                break;

            case COMBINED_QUESTIONS:
                //CombinedType Questions answers are validated and checked here
                combinedQuestions(qNo, answer);
                break;
        }
    }

    //This method will Display Result in TextView And Make Toast
    void displayResult(String result) {
        TextView outputTextView = (TextView) findViewById(R.id.Result);
        outputTextView.setText(result);
        Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
    }

    //This method will warn about un-attempted questions
    void warningToast(String qNo) {
        Toast.makeText(MainActivity.this, getText(R.string.attemptWarn).toString() + " " + qNo, Toast.LENGTH_SHORT).show();
        marks = 0;
        TextView outputTextView = (TextView) findViewById(R.id.Result);
        resultOutput = "";
        outputTextView.setText(resultOutput);
    }

    //This method will increment marks and add correct answer string to result output
    void correctAnswer(String qNo) {
        marks += 1;
        resultOutput += "\nQuestion No." + qNo + " " + getText(R.string.correct).toString();
    }

    //This method will add incorrect answer string to result output
    void incorrectAnswer(String qNo) {
        resultOutput += "\nQuestion No." + qNo + " " + getText(R.string.incorrect).toString();
    }

    //This method handles Radio Question Answers
    void radioQuestions(String questionNo, String radioAnswer) {

        radioGroupQuestion = (RadioGroup) findViewById(R.id.firstQuestionRadioGroup);
        //First Validate RadioGroup is Checked or Not
        if (radioGroupQuestion.getCheckedRadioButtonId() == -1) {
            //no buttons are checked
            warningToast(questionNo);
        } else {
            //button checked
            int selectedRadioAnswer = radioGroupQuestion.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioAnswer);

            //verify selected RadioButton and match answer
            if (selectedRadioButton.getText().toString() == radioAnswer) {
                //correct answer
                correctAnswer(questionNo);
            } else {
                //incorrect answer
                incorrectAnswer(questionNo);
            }
        }
    }

    //This method handles CheckBox Question Answers
    void checkBoxQuestions(String questionNo, String checkBoxAnswer) {

        CheckBox optCheckBox1 = (CheckBox) findViewById(R.id.opt2_1);
        CheckBox optCheckBox2 = (CheckBox) findViewById(R.id.opt2_2);
        CheckBox optCheckBox3 = (CheckBox) findViewById(R.id.opt2_3);
        CheckBox optCheckBox4 = (CheckBox) findViewById(R.id.opt2_4);

        //First Validate any of the CheckBox is Checked or Not
        if (optCheckBox1.isChecked() || optCheckBox2.isChecked() || optCheckBox3.isChecked() || optCheckBox4.isChecked()) {
            //button checked
            StringBuilder resultCheckBox = new StringBuilder();

            //append whole Answer
            if (optCheckBox1.isChecked()) {
                resultCheckBox.append(optCheckBox1.getText().toString());
            }
            if (optCheckBox2.isChecked()) {
                resultCheckBox.append(optCheckBox2.getText().toString());
            }
            if (optCheckBox3.isChecked()) {
                resultCheckBox.append(optCheckBox3.getText().toString());
            }
            if (optCheckBox4.isChecked()) {
                resultCheckBox.append(optCheckBox4.getText().toString());
            }

            //match Answer
            if (resultCheckBox.toString().matches(checkBoxAnswer)) {
                correctAnswer(questionNo);
            } else {
                incorrectAnswer(questionNo);
            }
        } else {
            //no buttons are checked
            warningToast(questionNo);
        }
    }

    //This method handles EditText Question Answers
    void editTextQuestions(String questionNo, String editTextAnswer) {

        EditText answerEditText = (EditText) findViewById(R.id.answerEditText);
        //First Validate EditText contains answer
        if (validateEditTextAnswer(answerEditText)) {
            //no buttons are checked
            warningToast(questionNo);
        } else {
            if (answerEditText.getText().toString().matches(editTextAnswer)) {
                correctAnswer(questionNo);
            } else {
                incorrectAnswer(questionNo);
            }
        }
    }

    //This method handles Combined Question Answers
    void combinedQuestions(String questionNo, String combinedTextAnswer) {

        CheckBox optCheckBox1 = (CheckBox) findViewById(R.id.opt4_1);
        CheckBox optCheckBox2 = (CheckBox) findViewById(R.id.opt4_2);
        CheckBox optCheckBox3 = (CheckBox) findViewById(R.id.opt4_3);

        EditText answerEditText = (EditText) findViewById(R.id.answerEditText2);

        //First Validate any of the CheckBox is Checked or Not
        if (optCheckBox1.isChecked() || optCheckBox2.isChecked() || optCheckBox3.isChecked()) {
            //button checked
            StringBuilder resultCheckBox = new StringBuilder();

            //append whole Answer
            if (optCheckBox1.isChecked()) {
                resultCheckBox.append(optCheckBox1.getText().toString());
            }
            if (optCheckBox2.isChecked()) {
                resultCheckBox.append(optCheckBox2.getText().toString());
            }
            if (optCheckBox3.isChecked()) {
                resultCheckBox.append(optCheckBox3.getText().toString());
            }

            //Second Validate EditText contains answer
            if (validateEditTextAnswer(answerEditText)) {
                //no buttons are checked
                warningToast(questionNo);
            } else {
                String answerCombined = resultCheckBox.toString() + answerEditText.getText().toString();
                if (answerCombined.matches(combinedTextAnswer)) {
                    correctAnswer(questionNo);
                } else {
                    incorrectAnswer(questionNo);
                }
            }
        } else {
            //no buttons are checked
            warningToast(questionNo);
        }

    }

    //Declaring enum Constants for QuestionTypes
    public enum QuestionType {
        RADIO_QUESTIONS,
        CHECKBOX_QUESTIONS,
        EDITTEXT_QUESTIONS,
        COMBINED_QUESTIONS
    }
}
