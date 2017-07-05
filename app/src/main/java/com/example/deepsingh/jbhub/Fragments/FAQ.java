package com.example.deepsingh.jbhub.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deepsingh.jbhub.R;

import java.util.ArrayList;
import java.util.HashMap;

import adapter.Detail_adapter;
import adapter.adapter_for_FAQ;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FAQ.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FAQ#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FAQ extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView RCLVW;
    ArrayList<HashMap<String, String>> listdata = new ArrayList<HashMap<String, String>>();
    private OnFragmentInteractionListener mListener;
    private adapter_for_FAQ mAdapter;
    public FAQ() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FAQ.
     */
    // TODO: Rename and change types and number of parameters
    public static FAQ newInstance(String param1, String param2) {
        FAQ fragment = new FAQ();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faq, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RCLVW = (RecyclerView) view.findViewById(R.id.RCL_VW_FAQ);
        HashMap<String, String> prodHashMap = new HashMap<String, String>();

        prodHashMap.put("FAQ", "Do I need to pay to contact the seller?");
        prodHashMap.put("ans", "No you do not need to pay to contact the seller.");

        listdata.add(prodHashMap);
        HashMap<String, String> prodHashMap2 = new HashMap<String, String>();
        prodHashMap2.put("ans", "Yes, there is a listing fee of £250 inclusive of VAT for a trade property.");
        prodHashMap2.put("FAQ", "Do I need to pay to list a property as a Trader?");
        listdata.add(prodHashMap2);
        HashMap<String, String> prodHashMap3 = new HashMap<String, String>();
        prodHashMap3.put("ans", "There are no fees to be paid should you wish to re market a previously listed property by you.");
        prodHashMap3.put("FAQ", "Do I need to pay again if i withdraw the property and would like to re market it?");
        listdata.add(prodHashMap3);

        HashMap<String, String> prodHashMap4 = new HashMap<String, String>();
        prodHashMap4.put("ans", "The property can be advertised for as long as it takes to sell it.");
        prodHashMap4.put("FAQ", "How long can i advertise my property for?");
        listdata.add(prodHashMap4);

        HashMap<String, String> prodHashMap5 = new HashMap<String, String>();
        prodHashMap5.put("ans", "There are no limits to the number of properties that you can list.");
        prodHashMap5.put("FAQ", "Is there a limit to the number of properties i can list?");
        listdata.add(prodHashMap5);
        HashMap<String, String> prodHashMap6 = new HashMap<String, String>();
        prodHashMap6.put("ans", "JV Hub works alongside a number of industry compliant property brokers throughout the UK to source the very best investment opportunities ");
        prodHashMap6.put("FAQ", "How does JV Hub acquire property deals?");
        listdata.add(prodHashMap6);

        HashMap<String, String> prodHashMap7 = new HashMap<String, String>();
        prodHashMap7.put("ans", "This will depend on your long term strategy, we would recommend you talk to an investment consultant to talk through your available options.");
        prodHashMap7.put("FAQ", "Should i be looking for rental yield or capital growth?");
        listdata.add(prodHashMap7);

        HashMap<String, String> prodHashMap8 = new HashMap<String, String>();
        prodHashMap8.put("ans", "There are many benefits to both, we would recommend you discuss all the options with our experienced property investment advisors.");
        prodHashMap8.put("FAQ", "Should i buy the property outright with cash or get a mortgage?");
        listdata.add(prodHashMap8);

        HashMap<String, String> prodHashMap9 = new HashMap<String, String>();
        prodHashMap9.put("ans", "A mortgage broker will source the very best mortgage products from a large panel of providers across the UK.");
        prodHashMap9.put("FAQ", "What does a mortgage broker do?");
        listdata.add(prodHashMap9);

        HashMap<String, String> prodHashMap10 = new HashMap<String, String>();
        prodHashMap10.put("ans", "we would recommend you discuss all the options with our experienced property investment advisors.");
        prodHashMap10.put("FAQ", "Which mortgage provider is the best for my needs?");
        listdata.add(prodHashMap10);

        HashMap<String, String> prodHashMap11 = new HashMap<String, String>();
        prodHashMap11.put("ans", "Yes, JV Hub works alongside premier property lawyers. Speak to one of our property investment consultants for more information.");
        prodHashMap11.put("FAQ", "Does JV Hub have a recommended solicitors firm?");
        listdata.add(prodHashMap11);

        HashMap<String, String> prodHashMap12 = new HashMap<String, String>();
        prodHashMap12.put("ans", "All guarantees will be clearly outlined within the properties description, speak to one of our property consultants for more information.");
        prodHashMap12.put("FAQ", "Do the properties that JV Hub offer have building guarantees?");
        listdata.add(prodHashMap12);


        mAdapter = new adapter_for_FAQ(listdata);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        RCLVW.setLayoutManager(mLayoutManager);
        RCLVW.setItemAnimator(new DefaultItemAnimator());
        RCLVW.setAdapter(mAdapter);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
