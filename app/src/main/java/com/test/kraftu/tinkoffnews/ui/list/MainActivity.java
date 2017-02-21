package com.test.kraftu.tinkoffnews.ui.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.test.kraftu.tinkoffnews.R;
import com.test.kraftu.tinkoffnews.model.HeadPost;
import com.test.kraftu.tinkoffnews.source.network.ServerApi;
import com.test.kraftu.tinkoffnews.tools.HtmlUntil;
import com.test.kraftu.tinkoffnews.ui.item.ShowPostActivity;
import com.test.kraftu.tinkoffnews.ui.adapters.HeadersAdapter;
import com.test.kraftu.tinkoffnews.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.PresenterFactory;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends BaseActivity<MainPresenter> implements
        SwipeRefreshLayout.OnRefreshListener, HeadersAdapter.OnClickItemListener {

    @Inject
    ServerApi serverApi;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.emptyText)
    TextView emptyText;

    private HeadersAdapter mHeadersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();

        if(savedInstanceState == null)
            getPresenter().loadHeadsPost(true);
    }
    private void initView(){
        swipeRefreshLayout.setOnRefreshListener(this);

        mHeadersAdapter = new HeadersAdapter();
        mHeadersAdapter.setOnClickItemListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mHeadersAdapter);
    }


    void onNetworkError(Throwable throwable){
        swipeRefreshLayout.setRefreshing(false);
        emptyText.setVisibility(mHeadersAdapter.getItemCount()!=0? View.GONE:View.VISIBLE);
        Toast.makeText(this,throwable.toString(),Toast.LENGTH_LONG).show();
    }

    void setItems(List<HeadPost> lists){
        swipeRefreshLayout.setRefreshing(false);
        emptyText.setVisibility(lists!=null && lists.size()!=0? View.GONE:View.VISIBLE);
        mHeadersAdapter.setHeadPosts(lists);
    }
    void showProgress(boolean isShow){
        swipeRefreshLayout.setRefreshing(isShow);
    }

    @Override
    public void onRefresh() {
        getPresenter().loadHeadsPost(false);
    }

    @Override
    public void onClickItem(HeadersAdapter headersAdapter, View view, int position) {
        HeadPost headPost = headersAdapter.getObject(position);
        ShowPostActivity.start(this,headPost.getId(),
                HtmlUntil.fromHtml(headPost.getText()).toString());
    }
}
