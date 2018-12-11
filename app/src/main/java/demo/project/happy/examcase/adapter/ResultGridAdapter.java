package demo.project.happy.examcase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import demo.project.happy.examcase.R;
import demo.project.happy.examcase.inter.OnRecyclerItemClickListener;

/**
 * Created by Happy on 2017/7/11.
 */

public class ResultGridAdapter extends RecyclerView.Adapter<ResultGridAdapter.ViewHolder> {

	private Context context;
	private boolean[] selected;
	private boolean isSelected = false;
	private int list = 0;
	OnRecyclerItemClickListener onRecyclerItemClickListener;

	public ResultGridAdapter(Context context, int list, boolean[] selected, OnRecyclerItemClickListener onRecyclerItemClickListener) {
		this.context = context;
		this.list = list;
		this.selected = selected;
		this.onRecyclerItemClickListener = onRecyclerItemClickListener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final View view = LayoutInflater.from(context).inflate(R.layout.recycler_grid_item, parent, false);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onRecyclerItemClickListener != null){
					onRecyclerItemClickListener.onItemClick(view, (int) view.getTag());
				}
			}
		});

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		ViewHolder viewHolder = holder;
		viewHolder.itemView.setTag(position);
		viewHolder.tvResult.setText(String.valueOf(position + 1));
		/**
		 * 在布局文件中设置后，在这里添加属性，无效果。。
		 */
//		viewHolder.tvResult.setSelected(isSelected);

		isSelected = selected[position];

		Log.e("Adapter",isSelected + "");
		if(isSelected){
			viewHolder.tvResult.setBackgroundResource(R.drawable.bg_result_selected);
		}else {
			viewHolder.tvResult.setBackgroundResource(R.drawable.bg_result_normal);
		}

	}

	@Override
	public int getItemCount() {
		return list;
	}

	static class ViewHolder extends RecyclerView.ViewHolder{
		private TextView tvResult;

		public ViewHolder(View itemView) {
			super(itemView);
			tvResult = (TextView) itemView.findViewById(R.id.tv_result_grid_item);
		}
	}
}
