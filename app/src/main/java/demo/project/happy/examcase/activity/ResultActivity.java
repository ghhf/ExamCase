package demo.project.happy.examcase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import demo.project.happy.examcase.R;
import demo.project.happy.examcase.adapter.ResultGridAdapter;
import demo.project.happy.examcase.inter.OnRecyclerItemClickListener;

public class ResultActivity extends AppCompatActivity {

    private RecyclerView rvResultList;
    private TextView tvBack;
    private TextView tvRight, tvWrong;
    private TextView tvSummary;
    private boolean[] flagSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initUI();

    }

    private void initUI() {

        int listNum = getIntent().getIntExtra("totalNum", 0);
        boolean isSelected = false;
        flagSelected = getIntent().getBooleanArrayExtra("flag");

        int right = 0;
        int wrong = 0;

        for (int i = 0; i < flagSelected.length; i++) {
            isSelected = flagSelected[i];
            if (isSelected) {
                right++;
            } else {
                wrong++;
            }
            Log.e("ResultActivity -- flag", flagSelected[i] + "");
        }
        tvRight = (TextView) findViewById(R.id.tv_result_right);
        tvWrong = (TextView) findViewById(R.id.tv_result_wrong);
        tvSummary = (TextView) findViewById(R.id.tv_result_summary);

        tvRight.setText(getString(R.string.right) + right);
        tvWrong.setText(getString(R.string.wrong) + wrong);
        if (right > wrong && right < listNum) {
            tvSummary.setText("共" + listNum + "道题，回答正确：" + right
                    + ", 回答错误：" + wrong + ",总体表现不错，继续加油哦！");
        } else if (right < wrong && right != 0) {
            tvSummary.setText("共" + listNum + "道题，回答正确：" + right
                    + ", 回答错误：" + wrong + ",稍微有点落后了，不要灰心，一定要更努力。");
        } else if (right == 0) {
            tvSummary.setText(R.string.all_wrong);
        } else if (right == listNum) {
            tvSummary.setText(R.string.all_rignt);
        }

        rvResultList = (RecyclerView) findViewById(R.id.rv_result_list);
        tvBack = (TextView) findViewById(R.id.tv_result_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接返回首页 启动模式 singleTask
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 5);
        rvResultList.setLayoutManager(manager);

        ResultGridAdapter adapter = new ResultGridAdapter(this, listNum, flagSelected, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//				Toast.makeText(ResultActivity.this,"test",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        rvResultList.setAdapter(adapter);
    }

}
