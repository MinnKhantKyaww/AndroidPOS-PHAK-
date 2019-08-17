package com.example.andoridpos.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andoridpos.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public abstract class ListFragment extends Fragment {

    protected abstract RecyclerView.Adapter<? extends  RecyclerView.ViewHolder> adapter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter());

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(this::onFabClick);

        // TODO hide/show on recyclerview scroll
    }

    protected void onFabClick(View view) {

    }
}
