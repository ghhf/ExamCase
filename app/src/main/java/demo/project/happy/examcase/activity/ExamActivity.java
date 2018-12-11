package demo.project.happy.examcase.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import demo.project.happy.examcase.R;
import demo.project.happy.examcase.model.Examination;

/**
 * 涉及内容：
 * <p>
 * <p>
 * 逻辑分析：
 * 问题，选项，知道用户是否选择了正确的选项，并把值保存，传递给ResultActivity，
 * 点击上一个、下一个 可以正确的反馈
 * <p>
 * 遇到的问题：
 * 问题数组到最后一个时，直接跳转到下一个Activity，最后一道题没有显示。  在点击事件中设置currentIndex的改变，而不是在updateQuestion()中
 * 由于用到的是同一个布局，所以在下一题会重复得到上一题的选项，默认选择， clearCheck
 * 结果页面点击返回按钮，直接返回首页，而不是测试页面。
 * <p>
 * 记录选择状态，必须用一个集合或者数组，Intent传值无法传集合，用数组实现
 * 记录选择状态的事件，用数组记录，数组无法动态添加数据，只能一个一个被赋值，无法确定赋值的时间
 */
public class ExamActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ExamActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_RESULT = 0;

    private TextView tvExit;
    private TextView tvQue;
    private RadioGroup rgOption;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private TextView tvPre;
    private TextView tvNext;
    private TextView tvAnswer;
    private String answer;

    private Examination[] questionArr = new Examination[]{
            new Examination("question1 ?", "A:option1", "B:option2", "C:option3", "D:option4", "A"),
            new Examination("question2 ?", "A:option1", "B:option2", "C:option3", "D:option4", "A"),
            new Examination("question3 ?", "A:option1", "B:option2", "C:option3", "D:option4", "B"),
            new Examination("question4 ?", "A:option1", "B:option2", "C:option3", "D:option4", "A"),
            new Examination("question5 ?", "A:option1", "B:option2", "C:option3", "D:option4", "B"),
            new Examination("question6 ?", "A:option1", "B:option2", "C:option3", "D:option4", "A"),
            new Examination("question7 ?", "A:option1", "B:option2", "C:option3", "D:option4", "A"),
    };

    private String[] explain = {
            "解析 1 ",
            "解析 2 ",
            "解析 3 ",
            "解析 4 ",
            "解析 5 ",
            "解析 6 ",
            "解析 7 ",
    };

    private int currentIndex = 0;
    private boolean flag;
    private boolean[] flagArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        // 取出销毁状态前 保存的数据
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        initView();
        updateQuestion();

    }

    //保存销毁前的状态
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(KEY_INDEX, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, currentIndex);
    }

    private void initView() {
        tvExit = (TextView) findViewById(R.id.tv_exam_exit);
        tvExit.setOnClickListener(this);

        tvQue = (TextView) findViewById(R.id.tv_exam_question);
        tvQue.setOnClickListener(this);

        rgOption = (RadioGroup) findViewById(R.id.rg_exam_choose);
        rbOption1 = (RadioButton) findViewById(R.id.rb_exam_choose1);
        rbOption2 = (RadioButton) findViewById(R.id.rb_exam_choose2);
        rbOption3 = (RadioButton) findViewById(R.id.rb_exam_choose3);
        rbOption4 = (RadioButton) findViewById(R.id.rb_exam_choose4);

        answer = questionArr[currentIndex].getAnswer();

        flagArr = new boolean[questionArr.length];

        rgOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // RadioGroup的监听
                switch (checkedId) {
                    case R.id.rb_exam_choose1:
                        if (answer.equals("A")) {
                            flag = true;
                        }
                        break;
                    case R.id.rb_exam_choose2:
                        if (answer.equals("B")) {
                            flag = true;
                        }
                        break;
                    case R.id.rb_exam_choose3:
                        if (answer.equals("C")) {
                            flag = true;
                        }
                        break;
                    case R.id.rb_exam_choose4:
                        if (answer.equals("D")) {
                            flag = true;
                        }
                        break;
                }
            }
        });


        tvPre = (TextView) findViewById(R.id.tv_exam_previous);
        tvPre.setOnClickListener(this);

        tvNext = (TextView) findViewById(R.id.tv_exam_next);
        tvNext.setOnClickListener(this);

        tvAnswer = (TextView) findViewById(R.id.tv_exam_answer);
        tvAnswer.setText(answer);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exam_exit:
                if (tvAnswer.getVisibility() == View.GONE) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                            .setTitle("确定要退出测试吗")
                            .setPositiveButton("确定退出", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).setNegativeButton("继续测试", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    dialog.create().show();
                }else {
                    // todo 查看过答案后直接返回应用首页
                    finish();
                }

                break;

            case R.id.tv_exam_question:

                // 为数组中元素赋值
                flagArr[currentIndex] = flag;
                Log.e("ExamActivity", flagArr[currentIndex] + "");

                if (currentIndex == questionArr.length - 1) {
                    Intent intent = new Intent(ExamActivity.this, ResultActivity.class);
                    intent.putExtra("totalNum", questionArr.length);
//					intent.putExtra("flag",flag);// 答案是否正确,,,这样仅仅传递过去 最后一个值得状态，所以应该传一个数组
                    intent.putExtra("flag", flagArr);
                    startActivityForResult(intent, REQUEST_CODE_RESULT);
                } else {
                    currentIndex++;
                    updateQuestion();
                }
                break;

            case R.id.tv_exam_next:
                // 为数组中元素赋值
                flagArr[currentIndex] = flag;
                Log.e("ExamActivity", flagArr[currentIndex] + "num" + currentIndex);

                if (currentIndex == questionArr.length - 1) {
                    Intent intent = new Intent(ExamActivity.this, ResultActivity.class);
                    intent.putExtra("totalNum", questionArr.length);
                    intent.putExtra("flag", flagArr);
                    startActivityForResult(intent, REQUEST_CODE_RESULT);
                } else {
                    currentIndex++;
                    answer = questionArr[currentIndex].getAnswer();
                    updateQuestion();
                }
                break;

            case R.id.tv_exam_previous:
                // 为数组中元素赋值
                flagArr[currentIndex] = flag;
                Log.e("ExamActivity", flagArr[currentIndex] + "");

                currentIndex--;
                answer = questionArr[currentIndex].getAnswer();

                updateQuestion();
                break;

            default:
                break;
        }
    }

    private void updateQuestion() {

        Log.d(TAG, "Updating question text for question and option#" + currentIndex, new Exception());
        String question = questionArr[currentIndex].getQuestion();
        tvQue.setText(question);
        String option1 = questionArr[currentIndex].getOption1();
        rbOption1.setText(option1);
        String option2 = questionArr[currentIndex].getOption2();
        rbOption2.setText(option2);
        String option3 = questionArr[currentIndex].getOption3();
        rbOption3.setText(option3);
        String option4 = questionArr[currentIndex].getOption4();
        rbOption4.setText(option4);

        rgOption.clearCheck(); // 清除选中状态
        flag = false;
        tvAnswer.setVisibility(View.GONE);
        tvAnswer.setText(getString(R.string.answer) + questionArr[currentIndex].getAnswer() + explain[currentIndex]);

        if (currentIndex == 0) {
            tvPre.setText(R.string.no);
            tvPre.setEnabled(false);
        } else {
            tvPre.setText(R.string.previous);
            tvPre.setEnabled(true);
        }

        if (currentIndex == questionArr.length -1){
            tvNext.setText(R.string.done);
        }
        tvNext.setText(R.string.next);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            currentIndex = data.getIntExtra("position", 0);
            answer = questionArr[currentIndex].getAnswer();
            updateQuestion();
        }
        if (requestCode == REQUEST_CODE_RESULT) {
            tvAnswer.setVisibility(View.VISIBLE);
            tvExit.setText("返回");
            tvNext.setVisibility(View.GONE);
            tvPre.setVisibility(View.GONE);

        }

    }

    @Override
    public void onBackPressed() {
        if (tvAnswer.getVisibility() == View.GONE) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("确定要退出测试吗")
                    .setPositiveButton("确定退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("继续测试", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            dialog.create().show();
        } else {
            finish();
        }
    }
}
