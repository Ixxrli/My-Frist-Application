package com.jnu.student.total;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.R;
import com.jnu.student.TargetAdapter;
import com.jnu.student.data.WorkStorage;
import com.jnu.student.data.WorkStorageItem;
import com.jnu.student.target.MyTarget;

import java.util.ArrayList;

public class StagetotalFragment extends Fragment {
    private  String Type;
    private RecyclerView targetR;
    private WorkStorage workStorage;
    private TargetAdapter TAdapter;
    private ArrayList<MyTarget> list;
    private  String File_Target_Name ="TData";

    public StagetotalFragment(){
        // Required empty public constructor
    }
    public  StagetotalFragment(String Type){
        this.Type=Type;
    }

    public StagetotalFragment newInstance(){
        StagetotalFragment fragment =new StagetotalFragment();
        Bundle T =new Bundle();
        fragment.setArguments(T);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_stage, container, false);
        workStorage =new WorkStorageItem();
        System.out.println(Type);
        list =workStorage.LoadTargetItems(requireActivity().getApplicationContext(),File_Target_Name,Type);
        System.out.println(list);
        targetR =rootView.findViewById(R.id.task_recyclerView);
        TAdapter = new TargetAdapter(requireActivity(),list);
        targetR.setAdapter(TAdapter);
        targetR.setLayoutManager(new LinearLayoutManager(requireActivity()));
        registerForContextMenu(targetR);

        return rootView;
    }
}
