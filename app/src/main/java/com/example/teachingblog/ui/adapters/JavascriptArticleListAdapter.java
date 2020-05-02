package com.example.teachingblog.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.teachingblog.R;
import com.example.teachingblog.models.Article;
import com.example.teachingblog.utils.LogUtil;
import com.example.teachingblog.utils.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class JavascriptArticleListAdapter extends RecyclerView.Adapter<JavascriptArticleListAdapter.InnerHolder> {

    private static final String TAG = "JavascriptArticleListAd";
    //文章列表的内容
    private List<Article> mDatas = new ArrayList<>();
    private OnArticleItemClickListener mArticleItemClickListener = null;

    @Override
    public JavascriptArticleListAdapter.InnerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(JavascriptArticleListAdapter.InnerHolder holder, int position) {
        //设置数据
        Article article = mDatas.get(position);

        holder.mArticleTitleTv.setText(article.getTitle());
        holder.mArticleDesTv.setText(article.getBody());
        holder.mArticleTypeTv.setText(article.getType());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.default_image);
        requestOptions.error(R.mipmap.default_image);
        Glide.with(holder.itemView.getContext())
                .load(article.getImg_path())
                .apply(requestOptions)
                .into(holder.mArticleCoverImg);

        String addTimeText = null;
        try {
            addTimeText = Utils.getDateFormat(article.getAddtime());
        } catch (ParseException e) {
            LogUtil.d(TAG, "ParseException --- > " + e.toString());
        }
        if (addTimeText != null) {
            holder.mArticleAddTimeTv.setText(addTimeText);
        }

        //设置Item的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mArticleItemClickListener != null) {
                    mArticleItemClickListener.onItemClick(mDatas.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView mArticleTitleTv;
        private final TextView mArticleDesTv;
        private final TextView mArticleTypeTv;
        private final TextView mArticleAddTimeTv;
        private final ImageView mArticleCoverImg;

        public InnerHolder(View itemView) {
            super(itemView);

            //文章标题
            mArticleTitleTv = itemView.findViewById(R.id.article_title_tv);
            //文章描述
            mArticleDesTv = itemView.findViewById(R.id.article_description_tv);
            //文章类型
            mArticleTypeTv = itemView.findViewById(R.id.article_type_tv);
            //上传时间
            mArticleAddTimeTv = itemView.findViewById(R.id.article_add_time_tv);
            //文章图标
            mArticleCoverImg = itemView.findViewById(R.id.article_cover);
        }
    }

    public void setData(List<Article> data) {
        //清除原来的数据
        mDatas.clear();
        //添加新的数据
        mDatas.addAll(data);
        //更新UI
        notifyDataSetChanged();
    }

    public void setOnArticleItemClickListener(OnArticleItemClickListener listener) {
        this.mArticleItemClickListener = listener;
    }

    public interface OnArticleItemClickListener {
        void onItemClick(Article article);
    }

}
