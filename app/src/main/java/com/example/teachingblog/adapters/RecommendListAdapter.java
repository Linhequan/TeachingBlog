package com.example.teachingblog.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teachingblog.R;
import com.example.teachingblog.models.Article;
import com.example.teachingblog.utils.LogUtil;
import com.example.teachingblog.utils.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.InnerHolder> {

    private static final String TAG = "RecommendListAdapter";
    private List<Article> mDatas = new ArrayList<>();

    @Override
    public InnerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //这里是加载View
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InnerHolder holder, int position) {
        //这里是设置数据
        View itemView = holder.itemView;
        //文章标题
        TextView articleTitleTv = itemView.findViewById(R.id.article_title_tv);
        //文章描述
        TextView articleDesTv = itemView.findViewById(R.id.article_description_tv);
        //文章类型
        TextView articleTypeTv = itemView.findViewById(R.id.article_type_tv);
        //上传时间
        TextView articleAddTimeTv = itemView.findViewById(R.id.article_add_time_tv);

        //设置数据
        Article article = mDatas.get(position);
        articleTitleTv.setText(article.getTitle());
        articleDesTv.setText(article.getBody());
        articleTypeTv.setText(article.getType());

        String addTimeText = null;
        try {
            addTimeText = Utils.getDateFormat(article.getAddtime());
        } catch (ParseException e) {
            LogUtil.d(TAG, "ParseException --- > " + e.toString());
        }
        if (addTimeText != null) {
            articleAddTimeTv.setText(addTimeText);
        }

    }

    @Override
    public int getItemCount() {
        //返回要显示的个数
        return mDatas.size();
    }

    public void setData(List<Article> result) {
        //清除原来的数据
        mDatas.clear();
        //添加新的数据
        mDatas.addAll(result);
        //更新UI
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(View itemView) {
            super(itemView);
        }
    }
}
