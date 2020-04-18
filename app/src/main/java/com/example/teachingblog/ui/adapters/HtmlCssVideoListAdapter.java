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
import com.example.teachingblog.models.Video;
import com.example.teachingblog.utils.LogUtil;
import com.example.teachingblog.utils.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class HtmlCssVideoListAdapter extends RecyclerView.Adapter<HtmlCssVideoListAdapter.InnerHolder> {

    private static final String TAG = "HtmlCssVideoListAdapter";
    //视频列表的内容
    private List<Video> mDatas = new ArrayList<>();
    private OnVideoItemClickListener mVideoItemClickListener = null;

    @Override
    public HtmlCssVideoListAdapter.InnerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HtmlCssVideoListAdapter.InnerHolder holder, final int position) {
        //设置数据
        Video video = mDatas.get(position);

        holder.mVideoTitleTv.setText(video.getTitle());
        holder.mVideoAuthorTv.setText(video.getAuthor());
        holder.mVideoTypeTv.setText(video.getType());

        String addTimeText = null;
        try {
            addTimeText = Utils.getDateFormat(video.getAddtime());
        } catch (ParseException e) {
            LogUtil.d(TAG, "ParseException --- > " + e.toString());
        }
        if (addTimeText != null) {
            holder.mVideoAddTimeTv.setText(addTimeText);
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.default_image);
        requestOptions.error(R.mipmap.default_image);
        Glide.with(holder.itemView.getContext())
                .load(video.getImgsrc())
                .apply(requestOptions)
                .into(holder.mVideoCoverImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoItemClickListener != null) {
                    mVideoItemClickListener.onItemClick(mDatas.get(position), mDatas);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView mVideoTitleTv;
        private final TextView mVideoAuthorTv;
        private final TextView mVideoTypeTv;
        private final TextView mVideoAddTimeTv;
        private final ImageView mVideoCoverImg;

        public InnerHolder(View itemView) {
            super(itemView);

            //视频标题
            mVideoTitleTv = itemView.findViewById(R.id.video_title_tv);
            //视频作者
            mVideoAuthorTv = itemView.findViewById(R.id.video_author_tv);
            //视频类型
            mVideoTypeTv = itemView.findViewById(R.id.video_type_tv);
            //上传时间
            mVideoAddTimeTv = itemView.findViewById(R.id.video_add_time_tv);
            //视频图片
            mVideoCoverImg = itemView.findViewById(R.id.video_cover);

        }
    }

    public void setData(List<Video> data) {
        //清除原来的数据
        mDatas.clear();
        //添加新的数据
        mDatas.addAll(data);
        //更新UI
        notifyDataSetChanged();
    }

    public void setOnVideoItemClickListener(OnVideoItemClickListener listener) {
        this.mVideoItemClickListener = listener;
    }

    public interface OnVideoItemClickListener {
        void onItemClick(Video video, List<Video> videoList);
    }

}
