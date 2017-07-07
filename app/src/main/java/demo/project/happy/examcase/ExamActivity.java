package demo.project.happy.examcase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ExamActivity extends AppCompatActivity implements View.OnClickListener {

	private static final String TAG = "ExamActivity";
	private static final String KEY_INDEX = "index";
	private static final int REQUEST_CODE_CHEAT = 0;

	private TextView tvExit;
	private TextView tvQue;
	private RadioGroup rgOption;
	private RadioButton rbOption1,rbOption2,rbOption3,rbOption4;
	private TextView tvPre;
	private TextView tvNext;

	private int currentIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam);

		initView();

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

		rgOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

			}
		});

		tvPre = (TextView) findViewById(R.id.tv_exam_previous);
		tvPre.setOnClickListener(this);
		tvNext = (TextView) findViewById(R.id.tv_exam_next);
		tvNext.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.tv_exam_exit:
				finish();
				break;
			case R.id.tv_exam_question:
				break;
			case R.id.tv_exam_next:
				updateQuestion();
				break;
			case R.id.tv_exam_previous:
				break;
			default:
				break;
		}
	}

	private void updateQuestion() {

	}
}
