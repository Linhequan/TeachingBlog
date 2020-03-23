package com.example.teachingblog.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.teachingblog.R;
import com.example.teachingblog.models.Article;
import com.example.teachingblog.utils.LogUtil;
import com.example.teachingblog.utils.Utils;
import com.stx.xhb.xbanner.XBanner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class HomeArticleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "HomeArticleListAdapter";
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;

    private int mHeaderCount = 1;//头部View个数
    //文章列表的内容
    private List<Article> mDatas = new ArrayList<>();
    //banner的数据
    private List<Article> mBannerDatas = new ArrayList<>();
    private OnArticleItemClickListener mArticleItemClickListener = null;

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    //内容长度
    public int getContentItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    //内容 ViewHolder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {

        private final TextView mArticleTitleTv;
        private final TextView mArticleDesTv;
        private final TextView mArticleTypeTv;
        private final TextView mArticleAddTimeTv;
        private final ImageView mArticleCoverImg;

        public ContentViewHolder(View itemView) {
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

    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final XBanner mArticleBanner;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            //XBanner
            mArticleBanner = itemView.findViewById(R.id.xbanner);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //这里是加载View
        if (viewType == ITEM_TYPE_HEADER) {
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_header, parent, false);
            return new HeaderViewHolder(headerView);
        } else if (viewType == ITEM_TYPE_CONTENT) {
            View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
            return new ContentViewHolder(contentView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {
            //Banner条目点击事件
            ((HeaderViewHolder) holder).mArticleBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                @Override
                public void onItemClick(XBanner banner, Object model, View view, int position) {
                    Article article = (Article) model;
                    if (mArticleItemClickListener != null) {
                        mArticleItemClickListener.onItemClick(article);
                    }
                }
            });
            //设置banner加载图片的方式
            ((HeaderViewHolder) holder).mArticleBanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Article article = (Article) model;
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.mipmap.default_image);
                    requestOptions.error(R.mipmap.default_image);
                    requestOptions.transform(new RoundedCorners(20));
                    Glide.with(((HeaderViewHolder) holder).itemView.getContext())
                            .load(article.getImg_path())
                            .apply(requestOptions)
                            .into((ImageView) view);
                }
            });
            //刷新数据之后，需要重新设置是否支持自动轮播
            ((HeaderViewHolder) holder).mArticleBanner.setAutoPlayAble(mBannerDatas.size() > 1);
            ((HeaderViewHolder) holder).mArticleBanner.setIsClipChildrenMode(true);
            ((HeaderViewHolder) holder).mArticleBanner.setBannerData(mBannerDatas);
        } else if (holder instanceof ContentViewHolder) {
            //设置数据
            Article article = mDatas.get(position - mHeaderCount);
            ((ContentViewHolder) holder).mArticleTitleTv.setText(article.getTitle());
            ((ContentViewHolder) holder).mArticleDesTv.setText(article.getBody());
            ((ContentViewHolder) holder).mArticleTypeTv.setText(article.getType());

            Glide.with(((ContentViewHolder) holder).itemView.getContext()).load(article.getImg_path()).into(((ContentViewHolder) holder).mArticleCoverImg);

            String addTimeText = null;
            try {
                addTimeText = Utils.getDateFormat(article.getAddtime());
            } catch (ParseException e) {
                LogUtil.d(TAG, "ParseException --- > " + e.toString());
            }
            if (addTimeText != null) {
                ((ContentViewHolder) holder).mArticleAddTimeTv.setText(addTimeText);
            }

            //设置Item的点击事件
            ((ContentViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mArticleItemClickListener != null) {
                        mArticleItemClickListener.onItemClick(mDatas.get(position - mHeaderCount));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //返回要显示的个数
        return mHeaderCount + getContentItemCount();
    }

    //设置适配器数据
    public void setData(List<Article> data) {
        //清除原来的数据
        mDatas.clear();
        //添加新的数据
        mDatas.addAll(data);
        //更新UI
        notifyDataSetChanged();
    }

    //设置轮播图数据
    public void setBannerData(List<Article> bannerData) {
        //清除原来的数据
        mBannerDatas.clear();
        //添加新的数据
        mBannerDatas.addAll(bannerData);
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
