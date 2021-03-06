package com.example.rodoggx.deltachallenge.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.rodoggx.deltachallenge.R;
import com.example.rodoggx.deltachallenge.di.DaggerPostComponent;
import com.example.rodoggx.deltachallenge.model.Post;

import java.util.List;

import javax.inject.Inject;

public class PostActivity extends AppCompatActivity implements PostContract.View{

    @Inject
    PostPresenter postPresenter;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private PostRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDaggerComponent();

        postPresenter.attachView(this);
        setupRecyclerView();

    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rvPosts);
        layoutManager = new LinearLayoutManager(this);
    }

    private void setupDaggerComponent() {
        DaggerPostComponent.create().inject(this);
    }

    @Override
    public void onPostReceived(List<Post> postList) {

        adapter = new PostRecyclerAdapter(postList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String s) {

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void onPostRequest(View view) {

        postPresenter.getPosts();
    }
}
