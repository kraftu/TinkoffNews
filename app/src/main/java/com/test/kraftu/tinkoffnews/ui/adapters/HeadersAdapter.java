package com.test.kraftu.tinkoffnews.ui.adapters;


import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.kraftu.tinkoffnews.R;
import com.test.kraftu.tinkoffnews.model.HeadPost;
import com.test.kraftu.tinkoffnews.tools.HtmlUntil;
import com.test.kraftu.tinkoffnews.tools.TimeUntil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeadersAdapter extends RecyclerView.Adapter<HeadersAdapter.HeaderHolder> {

    private List<HeadPost> mHeadPosts;
    private OnClickItemListener mOnClickItemListener;

    @Override
    public HeaderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HeaderHolder(
                LayoutInflater.from(
                        parent.getContext()).
                        inflate(R.layout.list_item_header,parent,false));
    }

    @Override
    public void onBindViewHolder(HeaderHolder holder, int position) {
        HeadPost headPost = getObject(position);
        holder.bind(headPost);
    }

    public HeadPost getObject(int position){
        return mHeadPosts.get(position);
    }

    @Override
    public int getItemCount() {
        return mHeadPosts !=null? mHeadPosts.size():0;
    }

    public void setHeadPosts(List<HeadPost> mHeadPosts) {
        this.mHeadPosts = mHeadPosts;
        notifyDataSetChanged();
    }
    protected void onClickItem(RecyclerView.ViewHolder viewHolder){
        if(mOnClickItemListener!=null)
            mOnClickItemListener.onClickItem(this
                    ,viewHolder.itemView
                    ,viewHolder.getAdapterPosition());
    }

    public void setOnClickItemListener(OnClickItemListener mOnClickItemListener) {
        this.mOnClickItemListener = mOnClickItemListener;
    }

    public interface OnClickItemListener{
        void onClickItem(HeadersAdapter headersAdapter,View view,int position);
    }

    public class HeaderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvTitle)
        TextView title;
        @BindView(R.id.tvTime)
        TextView time;

        public HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(HeadPost headPost){
            title.setText(HtmlUntil.fromHtml(headPost.getText()));
            if(headPost.getDatePost() != null)
                time.setText(TimeUntil.getStringDate(headPost.getDatePost().getMilliseconds()));
        }

        @Override
        public void onClick(View view) {
            onClickItem(this);
        }
    }
}
