package com.example.deepsingh.jbhub.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deepsingh.jbhub.Login_screen;
import com.example.deepsingh.jbhub.MainActivity;
import com.example.deepsingh.jbhub.R;
import com.example.deepsingh.jbhub.Service_handler.SERVER;
import com.example.deepsingh.jbhub.Service_handler.ServiceHandler;
import com.example.deepsingh.jbhub.success_screen;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link contact_us.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link contact_us#newInstance} factory method to
 * create an instance of this fragment.
 */
public class contact_us extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText EdtTxt_Name, EdtText_phone;
    Button Btn_call_me;
    private ProgressDialog pDialog;
    String status, Message;
    String Access_tocken = "";
    String Device_id = "";
    String Str_name, Str_phone;

    private OnFragmentInteractionListener mListener;

    public contact_us() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment contact_us.
     */
    // TODO: Rename and change types and number of parameters
    public static contact_us newInstance(String param1, String param2) {
        contact_us fragment = new contact_us();
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
        return inflater.inflate(R.layout.fragment_contact_us, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EdtTxt_Name = (EditText) view.findViewById(R.id.editText1);
        EdtText_phone = (EditText) view.findViewById(R.id.editText2);
        Btn_call_me = (Button) view.findViewById(R.id.button2);
        SharedPreferences shared = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
        Access_tocken = (shared.getString("Acess_tocken", "nodata"));
        Device_id = (shared.getString("device_id", "nodata"));
        Btn_call_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EdtTxt_Name.getText().toString().contentEquals("")) {
                    Animation anm = Shake_Animation();
                    EdtTxt_Name.startAnimation(anm);
                } else if (EdtText_phone.getText().toString().contentEquals("")) {
                    Animation anm = Shake_Animation();
                    EdtText_phone.startAnimation(anm);
                } else {
                    Str_name = EdtTxt_Name.getText().toString();
                    Str_phone = EdtText_phone.getText().toString();
                    new Contact_us().execute();
                }
            }
        });


    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public Animation Shake_Animation() {
        Animation shake = new TranslateAnimation(0, 5, 0, 0);
        shake.setInterpolator(new CycleInterpolator(5));
        shake.setDuration(300);


        return shake;
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

    private class Contact_us extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        JSONObject jsonnode, json_User;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            //nameValuePairs.add(new BasicNameValuePair("email", email_fb));
            nameValuePairs.add(new BasicNameValuePair("contact_no", Str_phone));
            nameValuePairs.add(new BasicNameValuePair("Message", ""));
            nameValuePairs.add(new BasicNameValuePair("Name", Str_name));


            String jsonStr = sh.makeServiceCall_withHeader(SERVER.Contact_Us,
                    ServiceHandler.POST, nameValuePairs, Access_tocken, Device_id);
            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Getting JSON Array node
                // JSONArray array1 = null;
                try {
                    status = jsonObj.getString("status");

                    Message = jsonObj.getString("message");


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            pDialog.dismiss();
            if(status.contentEquals("true")){
                Intent i1 = new Intent(getActivity(), success_screen.class);
                startActivity(i1);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_left);
            }else{
                Toast.makeText(getActivity(),Message,Toast.LENGTH_LONG).show();
            }


        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }
}
