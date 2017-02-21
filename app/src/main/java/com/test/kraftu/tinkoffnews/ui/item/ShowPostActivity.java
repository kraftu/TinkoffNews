package com.test.kraftu.tinkoffnews.ui.item;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.test.kraftu.tinkoffnews.R;
import com.test.kraftu.tinkoffnews.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.PresenterFactory;
import nucleus.factory.RequiresPresenter;


@RequiresPresenter(ShowPostPresenter.class)
public class ShowPostActivity extends BaseActivity<ShowPostPresenter> {
    private static final String BUNDLE_POST_ID = "BUNDLE_POST_ID";
    private static final String BUNDLE_POST_TITLE = "BUNDLE_POST_TITLE";

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.content)
    TextView content;

    private String mPostId;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_post);
        ButterKnife.bind(this);

        mPostId = getIntent().getStringExtra(BUNDLE_POST_ID);
        mTitle = getIntent().getStringExtra(BUNDLE_POST_TITLE);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mTitle);

        if (savedInstanceState == null){
            getPresenter().loadPost(mPostId);
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, String postId,String title){
        Intent intent = new Intent(context,ShowPostActivity.class);
        intent.putExtra(BUNDLE_POST_ID, postId);
        intent.putExtra(BUNDLE_POST_TITLE, title);
        context.startActivity(intent);
    }

    void setTextContent(Spanned text){
        progressBar.setVisibility(View.GONE);
        if(text!=null){
            content.setText(text);
        }else {
            content.setText(R.string.post_not_load);
        }
    }

    void onNetworkError(Throwable throwable){
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this,throwable.toString(),Toast.LENGTH_LONG).show();
    }
}
