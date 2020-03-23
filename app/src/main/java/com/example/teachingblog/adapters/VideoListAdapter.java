package com.example.teachingblog.adapters;

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

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.InnerHolder> {

    private static final String TAG = "VideoListAdapter";
    private List<Video> mDatas = new ArrayList<>();
    private OnDetailVideoItemClickListener mDtailVideoItemClickListener = null;

    @Override
    public VideoListAdapter.InnerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_video, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoListAdapter.InnerHolder holder, int position) {
        //设置数据
        Video video = mDatas.get(position);

        holder.mDetailVideoTitleTv.setText(video.getTitle());
        holder.mDetailVideoAuthorTv.setText(video.getAuthor());
        holder.mDetailVideoTypeTv.setText(video.getType());

        String addTimeText = null;
        try {
            addTimeText = Utils.getDateFormat(video.getAddtime());
        } catch (ParseException e) {
            LogUtil.d(TAG, "ParseException --- > " + e.toString());
        }
        if (addTimeText != null) {
            holder.mDetailVideoAddTimeTv.setText(addTimeText);
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.default_image);
        requestOptions.error(R.mipmap.default_image);
        Glide.with(holder.itemView.getContext())
                .load(video.getImgsrc())
                .apply(requestOptions)
                .into(holder.mDetailVideoCoverImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDtailVideoItemClickListener != null) {
                    mDtailVideoItemClickListener.onItemClick(mDatas.get(position), mDatas);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView mDetailVideoTitleTv;
        private final TextView mDetailVideoAuthorTv;
        private final TextView mDetailVideoTypeTv;
        private final TextView mDetailVideoAddTimeTv;
        private final ImageView mDetailVideoCoverImg;

        public InnerHolder(View itemView) {
            super(itemView);

            //视频标题
            mDetailVideoTitleTv = itemView.findViewById(R.id.detail_video_title_tv);
            //视频作者
            mDetailVideoAuthorTv = itemView.findViewById(R.id.detail_video_author_tv);
            //视频类型
            mDetailVideoTypeTv = itemView.findViewById(R.id.detail_video_type_tv);
            //上传时间
            mDetailVideoAddTimeTv = itemView.findViewById(R.id.detail_video_add_time_tv);
            //视频图片
            mDetailVideoCoverImg = itemView.findViewById(R.id.detail_video_cover);
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

    public void setOnDetailVideoItemClickListener(OnDetailVideoItemClickListener listener) {
        this.mDtailVideoItemClickListener = listener;
    }

    public interface OnDetailVideoItemClickListener {
        void onItemClick(Video video, List<Video> videoList);
    }

}
