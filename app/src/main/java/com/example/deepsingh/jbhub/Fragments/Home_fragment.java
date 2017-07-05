package com.example.deepsingh.jbhub.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepsingh.jbhub.Location_api.PlaceAPI;
import com.example.deepsingh.jbhub.Login_screen;
import com.example.deepsingh.jbhub.MainActivity;
import com.example.deepsingh.jbhub.Property_detail_info;
import com.example.deepsingh.jbhub.Property_listing_screen;
import com.example.deepsingh.jbhub.R;
import com.example.deepsingh.jbhub.Service_handler.SERVER;
import com.example.deepsingh.jbhub.Service_handler.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String status = "";
    String Message = "";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button Btn_Search;
    private ProgressDialog pDialog;
    Spinner Spnr_Experienced;
    Spinner Spnr_FND_THIS_p;
    Spinner Spnr_Category_youfall;
    Spinner Spnr_Desired;
    Spinner Spnr_Price_min;
    Spinner Spnr_Price_max;
    Spinner Spnr_Property_tpe;
    Spinner Spnr_Plan_to_purchase;
    Spinner Spnr_Prefed_time;
    Spinner Spnr_Looking_for;
    Spinner Spnr_How_much_invest;
    Spinner Spnr_Risk;
    String Str_experienced = "", Str_fund = "", Str_category = "", StrDesired = "", Str_priceminimu = "", Str_price_maximum = "",
            Str_property_type = "", Str_plan_to_purchase = "", Str_preffer_time = "", Str_looking = "", Str_invest = "", Str_risk = "", Str_location = "";
    HandlerThread mHandlerThread;
    Handler mThreadHandler;
    private PlacesAutoCompleteAdapter mAdapter;
    String[] experienced_array = {"Show All", "New Investor", "Existing investor"};
    String[] experienced_array_id = {"Show All", "0", "1"};

    String[] Fund_this_purchase = {"Show All", "Cash", "Mortgage", "Other"};
    String[] Fund_this_purchase_id = {"Show All", "1", "2", "100"};

    String[] Cateory_you_fall = {"Show All", "Developer", "Advisor", "Broker", "Owner Operator REIT", "Architect", "Engineer", "Contractor", "Surveyor", "Professional Services Provider", "Crowd Funder", "Insurance Provider", "Others"};
    String[] Cateory_you_fall_id = {"Show All", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "100"};
    String[] Desired = {"Show All", "up to 5%", "6-10%", "11-15%", "15%+"};
    String[] Desired_id = {"Show All", "1", "2", "3", "4"};
    RadioButton Rdio_button_sale, Radio_buttn_torent;
    String Str_type_rent_or_sale = "1";


    String[] Price_minimum = {"Minimum", "No min", "£50,000", "£100,000", "£150,000", "£200,000", "£250,000", "£300,000"
            , "£350,000", "£400,000", "£450,000", "£500,000", "£550,000", "£600,000", "£650,000", "£700,000", "£750,000"
            , "£800,000", "£850,000", "£900,000", "£950,000", "£1,000,000", "£1,050,000", "£1,100,000", "£1,150,000", "£1,200,000", "£1,250,000"
            , "£1,300,000", "£1,350,000", "£1,400,000", "£1,450,000", "£1,500,000", "£1,550,000", "£1,600,000", "£1,650,000", "£1,700,000", "£1,750,000", "£1,800,000"
            , "£1,850,000", "£1,900,000", "£1,950,000", "£2,000,000"};
    //    String[] Price_Maximu = {"Maximum", "No max", "£50,000", "£75,000", "£100,000", "£125,000", "£150,000", "£175,000"
//            , "£200,000", "£2,25,000", "£2,50,000", "£2,75,000", "£3,00,000", "£3,25,000", "£3,50,000", "£3,75,000", "£4,00,000"
//            , "£4,25,000", "£4,50,000", "£4,75,000", "£5,00,000", "£5,25,000", "£5,50,000", "£5,75,000", "£6,00,000", "£6,25,000", "£6,50,000"
//            , "£6,75,000", "£7,00,000", "£7,25,000", "£7,50,000", "£7,75,000", "£8,00,000", "£8,25,000", "£8,50,000", "£8,75,000", "£9,00,000", "£9,25,000"
//            , "£9,50,000", "£9,75,000", "£10,00,000", "£11,00,000", "£12,00,000", "£13,00,000", "£14,00,000", "£15,00,000", "£16,00,000"
//            , "£17,00,000", "£18,00,000", "£19,00,000", "£20,00,000", "£21,00,000", "£22,00,000", "£23,00,000", "£24,00,000", "£25,00,000", "£26,00,000"
//            , "£27,00,000", "£28,00,000", "£29,00,000", "£30,00,000", "£31,00,000", "£32,00,000", "£33,00,000", "£34,00,000", "£35,00,000", "£36,00,000", "£37,00,000", "£38,00,000", "£39,00,000"
//            , "£40,00,000", "£41,00,000", "£42,00,000", "£43,00,000", "£44,00,000", "£45,00,000", "£46,00,000", "£47,00,000", "£48,00,000", "£49,00,000"
//            , "£50,00,000"};
    String[] Price_Maximu = {"Maximum", "No max", "£50,000", "£100,000", "£150,000", "£200,000", "£250,000", "£300,000"
            , "£350,000", "£400,000", "£450,000", "£500,000", "£550,000", "£600,000", "£650,000", "£700,000", "£750,000"
            , "£800,000", "£850,000", "£900,000", "£950,000", "£1,000,000", "£1,050,000", "£1,100,000", "£1,150,000", "£1,200,000", "£1,250,000"
            , "£1,300,000", "£1,350,000", "£1,400,000", "£1,450,000", "£1,500,000", "£1,550,000", "£1,600,000", "£1,650,000", "£1,700,000", "£1,750,000", "£1,800,000"
            , "£1,850,000", "£1,900,000", "£1,950,000", "£2,000,000"};
    String[] Price_Maximu_forrent = {"Maximum", "No max", "£100", "£150", "£200", "£250", "£300", "£350"
            , "£400", "£450", "£500", "£600", "£700", "£800", "£900", "£1,000", "£1,100"
            , "£1,200", "£1,300", "£1,400", "£1,500", "£1,750", "£2,000", "£2,250", "£2,500", "£2,750", "£3,000"
            , "£3,500", "£4,000", "£4,500", "£5,000", "£5,500", "£6,000", "£6,500", "£7,000", "£8,000", "£9,000", "£10,000"
            , "£12,500", "£15,000", "£17,500", "£20,000", "£25,000", "£30,000", "£35,000", "£40,000"};
    String[] Price_minimum_for_rent = {"Minimum", "No min", "£100", "£150", "£200", "£250", "£300", "£350"
            , "£400", "£450", "£500", "£600", "£700", "£800", "£900", "£1,000", "£1,100"
            , "£1,200", "£1,300", "£1,400", "£1,500", "£1,750", "£2,000", "£2,250", "£2,500", "£2,750", "£3,000"
            , "£3,500", "£4,000", "£4,500", "£5,000", "£5,500", "£6,000", "£6,500", "£7,000", "£8,000", "£9,000", "£10,000"
            , "£12,500", "£15,000", "£17,500", "£20,000", "£25,000", "£30,000", "£35,000", "£40,000"};
    String[] Property_type = {"Show All", "Buy to Let", "Flip", "Commercial Property", "Commercial to Residential", "Rent2Rent", "Lease Options", "Services Accommodation", "Services Offices", "Offices", "Retail", "Industrial", "Mixed Use"
            , "Flat/Apartments", "Houses", "Land", "HMO", "Others"};
    String[] Property_type_id = {"Show All", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "15"
            , "12", "13", "14", "16", "100"};

    String[] Plan_to_purchase = {"Show All", "0-3 months", "3-6 months", "6+ months"};
    String[] Plan_to_purchase_id = {"Show All", "1", "2", "3"};

    String[] Prefferd_time_scale = {"Show All", "1-3 years", "3-5 years", "5+years"};
    String[] Prefferd_time_scale_id = {"Show All", "1", "2", "3"};

    String[] Looking_for = {"Show All", "Income", "Capital Growth", "Both"};
    String[] Looking_for_id = {"Show All", "1", "2", "100"};

    String[] How_much_invest = {"Show All", "£5-50,000", "£50-100,000", "£100-200,000", "£200,000 +", "£500,000 +", "£1,000,000+", "£2,000,000+"};
    String[] How_much_invest_id = {"Show All", "1", "2", "3", "4", "5", "6", "7"};

    String[] Risk = {"Show All", "Low", "Medium", "High"};
    String[] Risk_id = {"Show All", "1", "2", "3"};
    String Access_tocken, Device_id;
    SharedPreferences shared;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Btn_Search = (Button) view.findViewById(R.id.button);
        Spnr_Experienced = (Spinner) view.findViewById(R.id.experiencedinvestor);
        Spnr_FND_THIS_p = (Spinner) view.findViewById(R.id.fund);
        Spnr_Category_youfall = (Spinner) view.findViewById(R.id.ROI);
        Spnr_Desired = (Spinner) view.findViewById(R.id.desire);
        Spnr_Price_min = (Spinner) view.findViewById(R.id.price);
        Spnr_Price_max = (Spinner) view.findViewById(R.id.price2);
        Spnr_Property_tpe = (Spinner) view.findViewById(R.id.invest);
        Spnr_Plan_to_purchase = (Spinner) view.findViewById(R.id.purchaseplan);
        Spnr_Prefed_time = (Spinner) view.findViewById(R.id.timescale);
        Spnr_Looking_for = (Spinner) view.findViewById(R.id.lookingfor);
        Spnr_How_much_invest = (Spinner) view.findViewById(R.id.asideinvest);
        Spnr_Risk = (Spinner) view.findViewById(R.id.riskappetite);
        Rdio_button_sale = (RadioButton) view.findViewById(R.id.radioButton);
        Radio_buttn_torent = (RadioButton) view.findViewById(R.id.radioButton2);
        shared = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
        final AutoCompleteTextView autocompleteView = (AutoCompleteTextView) view.findViewById(R.id.editText7);

        autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_txt_view));
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
//                (getActivity(), android.R.layout.simple_spinner_item,list);

        String Str_locationn = (shared.getString("Location", "nodata"));

        if (!Str_locationn.contentEquals("nodata")) {
            autocompleteView.setText(Str_locationn);
        }
        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String description = (String) parent.getItemAtPosition(position);
                Str_location = description;
                SharedPreferences.Editor editor = shared.edit();

                editor.putString("Location", Str_location);

                editor.commit();
                //Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }
        });
//        Str_type_rent_or_sale = (shared.getString("Radio_button_status", "nodata"));
//        if (Str_type_rent_or_sale.contentEquals("nodata")) {
//            Str_type_rent_or_sale = "1";
//        }
//        if (Str_type_rent_or_sale.contentEquals("1")) {
//            //  Rdio_button_sale.setChecked(true);
//            //Radio_buttn_torent.setChecked(false);
////            SharedPreferences.Editor editor = shared.edit();
////
////            editor.putString("Radio_button_status", Str_type_rent_or_sale);
////
////            editor.commit();
//
////                    Price_minimum = Price_minimum.clone();
////                    Price_Maximu = Price_Maximu.clone();
//            final ArrayAdapter<String> spinnerArrayAdapter_price = new ArrayAdapter<String>(
//                    getActivity(), android.R.layout.simple_dropdown_item_1line, Price_minimum) {
//                @Override
//                public boolean isEnabled(int position) {
//
//                    return true;
//                }
//
//                @Override
//                public View getDropDownView(int position, View convertView,
//                                            ViewGroup parent) {
//                    View view = super.getDropDownView(position, convertView, parent);
//                    TextView tv = (TextView) view;
//                    if (position == 0) {
//                        // Set the hint text color gray
//                        tv.setTextColor(Color.GRAY);
//                    } else {
//                        tv.setTextColor(Color.BLACK);
//                    }
//                    return view;
//                }
//            };
//
//            Spnr_Price_min.setAdapter(spinnerArrayAdapter_price);
//            final ArrayAdapter<String> spinnerArrayAdapter_price_max = new ArrayAdapter<String>(
//                    getActivity(), android.R.layout.simple_dropdown_item_1line, Price_Maximu) {
//                @Override
//                public boolean isEnabled(int position) {
////
//                    return true;
//                }
//
//                @Override
//                public View getDropDownView(int position, View convertView,
//                                            ViewGroup parent) {
//                    View view = super.getDropDownView(position, convertView, parent);
//                    TextView tv = (TextView) view;
//                    if (position == 0) {
//                        // Set the hint text color gray
//                        tv.setTextColor(Color.GRAY);
//                    } else {
//                        tv.setTextColor(Color.BLACK);
//                    }
//                    return view;
//                }
//            };
//
//
//            Spnr_Price_max.setAdapter(spinnerArrayAdapter_price_max);
//        } else if (Str_type_rent_or_sale.contentEquals("2")) {
//            Radio_buttn_torent.setChecked(true);
//            Str_type_rent_or_sale = "2";
//            SharedPreferences.Editor editor = shared.edit();
//
//            editor.putString("Radio_button_status", Str_type_rent_or_sale);
//
//            editor.commit();
////                    Price_minimum = Price_minimum_for_rent.clone();
////                    Price_Maximu = Price_Maximu_forrent.clone();
//
//            final ArrayAdapter<String> spinnerArrayAdapter_price = new ArrayAdapter<String>(
//                    getActivity(), android.R.layout.simple_dropdown_item_1line, Price_minimum_for_rent) {
//                @Override
//                public boolean isEnabled(int position) {
//
//                    return true;
//                }
//
//                @Override
//                public View getDropDownView(int position, View convertView,
//                                            ViewGroup parent) {
//                    View view = super.getDropDownView(position, convertView, parent);
//                    TextView tv = (TextView) view;
//                    if (position == 0) {
//                        // Set the hint text color gray
//                        tv.setTextColor(Color.GRAY);
//                    } else {
//                        tv.setTextColor(Color.BLACK);
//                    }
//                    return view;
//                }
//            };
//
//            Spnr_Price_min.setAdapter(spinnerArrayAdapter_price);
//            final ArrayAdapter<String> spinnerArrayAdapter_price_max = new ArrayAdapter<String>(
//                    getActivity(), android.R.layout.simple_dropdown_item_1line, Price_Maximu_forrent) {
//                @Override
//                public boolean isEnabled(int position) {
////
//                    return true;
//                }
//
//                @Override
//                public View getDropDownView(int position, View convertView,
//                                            ViewGroup parent) {
//                    View view = super.getDropDownView(position, convertView, parent);
//                    TextView tv = (TextView) view;
//                    if (position == 0) {
//                        // Set the hint text color gray
//                        tv.setTextColor(Color.GRAY);
//                    } else {
//                        tv.setTextColor(Color.BLACK);
//                    }
//                    return view;
//                }
//            };
//
//
//            Spnr_Price_max.setAdapter(spinnerArrayAdapter_price_max);
//
//
//        }
//
//        Rdio_button_sale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    Radio_buttn_torent.setChecked(false);
//                    Str_type_rent_or_sale = "1";
//                    SharedPreferences.Editor editor = shared.edit();
//
//                    editor.putString("Radio_button_status", Str_type_rent_or_sale);
//
//                    editor.commit();
//
////                    Price_minimum = Price_minimum.clone();
////                    Price_Maximu = Price_Maximu.clone();
//                    final ArrayAdapter<String> spinnerArrayAdapter_price = new ArrayAdapter<String>(
//                            getActivity(), android.R.layout.simple_dropdown_item_1line, Price_minimum) {
//                        @Override
//                        public boolean isEnabled(int position) {
//
//                            return true;
//                        }
//
//                        @Override
//                        public View getDropDownView(int position, View convertView,
//                                                    ViewGroup parent) {
//                            View view = super.getDropDownView(position, convertView, parent);
//                            TextView tv = (TextView) view;
//                            if (position == 0) {
//                                // Set the hint text color gray
//                                tv.setTextColor(Color.GRAY);
//                            } else {
//                                tv.setTextColor(Color.BLACK);
//                            }
//                            return view;
//                        }
//                    };
//
//                    Spnr_Price_min.setAdapter(spinnerArrayAdapter_price);
//                    Spnr_Price_min.setSelection(0);
//                    final ArrayAdapter<String> spinnerArrayAdapter_price_max = new ArrayAdapter<String>(
//                            getActivity(), android.R.layout.simple_dropdown_item_1line, Price_Maximu) {
//                        @Override
//                        public boolean isEnabled(int position) {
////
//                            return true;
//                        }
//
//                        @Override
//                        public View getDropDownView(int position, View convertView,
//                                                    ViewGroup parent) {
//                            View view = super.getDropDownView(position, convertView, parent);
//                            TextView tv = (TextView) view;
//                            if (position == 0) {
//                                // Set the hint text color gray
//                                tv.setTextColor(Color.GRAY);
//                            } else {
//                                tv.setTextColor(Color.BLACK);
//                            }
//                            return view;
//                        }
//                    };
//
//
//                    Spnr_Price_max.setAdapter(spinnerArrayAdapter_price_max);
//                    Spnr_Price_max.setSelection(0);
//                }
//
//            }
//        });
//        Radio_buttn_torent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    Rdio_button_sale.setChecked(false);
//                    Str_type_rent_or_sale = "2";
//                    SharedPreferences.Editor editor = shared.edit();
//
//                    editor.putString("Radio_button_status", Str_type_rent_or_sale);
//
//                    editor.commit();
////                    Price_minimum = Price_minimum_for_rent.clone();
////                    Price_Maximu = Price_Maximu_forrent.clone();
//
//                    final ArrayAdapter<String> spinnerArrayAdapter_price = new ArrayAdapter<String>(
//                            getActivity(), android.R.layout.simple_dropdown_item_1line, Price_minimum_for_rent) {
//                        @Override
//                        public boolean isEnabled(int position) {
//
//                            return true;
//                        }
//
//                        @Override
//                        public View getDropDownView(int position, View convertView,
//                                                    ViewGroup parent) {
//                            View view = super.getDropDownView(position, convertView, parent);
//                            TextView tv = (TextView) view;
//                            if (position == 0) {
//                                // Set the hint text color gray
//                                tv.setTextColor(Color.GRAY);
//                            } else {
//                                tv.setTextColor(Color.BLACK);
//                            }
//                            return view;
//                        }
//                    };
//
//                    Spnr_Price_min.setAdapter(spinnerArrayAdapter_price);
//                    Spnr_Price_min.setSelection(0);
//                    final ArrayAdapter<String> spinnerArrayAdapter_price_max = new ArrayAdapter<String>(
//                            getActivity(), android.R.layout.simple_dropdown_item_1line, Price_Maximu_forrent) {
//                        @Override
//                        public boolean isEnabled(int position) {
////
//                            return true;
//                        }
//
//                        @Override
//                        public View getDropDownView(int position, View convertView,
//                                                    ViewGroup parent) {
//                            View view = super.getDropDownView(position, convertView, parent);
//                            TextView tv = (TextView) view;
//                            if (position == 0) {
//                                // Set the hint text color gray
//                                tv.setTextColor(Color.GRAY);
//                            } else {
//                                tv.setTextColor(Color.BLACK);
//                            }
//                            return view;
//                        }
//                    };
//
//
//                    Spnr_Price_max.setAdapter(spinnerArrayAdapter_price_max);
//                    Spnr_Price_max.setSelection(0);
//                }
//            }
//        });

        Access_tocken = (shared.getString("Acess_tocken", "nodata"));
        Device_id = (shared.getString("device_id", "nodata"));
        Btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i1 = new Intent(getActivity(), Property_listing_screen.class);
//                startActivity(i1);
//                getActivity().finish();
//                getActivity().overridePendingTransition(R.anim.slide_in_left,
//                        R.anim.slide_out_left);
                if (Str_experienced.contentEquals("Show All")) {
                    Str_experienced = "";
//                    Animation anm = Shake_Animation();
//                    Spnr_Experienced.startAnimation(anm);
//                    Toast.makeText(getActivity(), "Select your Experience", Toast.LENGTH_LONG).show();
                }
                if (Str_fund.contentEquals("Show All")) {
                    Str_fund = "";
//                    Animation anm = Shake_Animation();
//                    Spnr_FND_THIS_p.startAnimation(anm);
//                    Toast.makeText(getActivity(), "Select fund this purchase", Toast.LENGTH_LONG).show();
                }
                if (Str_category.contentEquals("Show All")) {
//                    Animation anm = Shake_Animation();
//                    Spnr_Category_youfall.startAnimation(anm);
//                    Toast.makeText(getActivity(), "Select Category", Toast.LENGTH_LONG).show();
                    Str_category = "";
                }
                if (StrDesired.contentEquals("Show All")) {
//                    Animation anm = Shake_Animation();
//                    Spnr_Desired.startAnimation(anm);
//                    Toast.makeText(getActivity(), "Select Desired ROI", Toast.LENGTH_LONG).show();
                    StrDesired = "";
                }
                if (Str_priceminimu.contentEquals("Show All")) {
//                    Animation anm = Shake_Animation();
//                    Spnr_Price_min.startAnimation(anm);
//                    Toast.makeText(getActivity(), "Select minimum price", Toast.LENGTH_LONG).show();
                    Str_priceminimu = "";
                }
                if (Str_price_maximum.contentEquals("Show All")) {
//                    Animation anm = Shake_Animation();
//                    Spnr_Price_max.startAnimation(anm);
//                    Toast.makeText(getActivity(), "Select maximum price", Toast.LENGTH_LONG).show();
                    Str_price_maximum = "";
                }
                if (Str_property_type.contentEquals("Show All")) {
//                    Animation anm = Shake_Animation();
//                    Spnr_Property_tpe.startAnimation(anm);
                    Str_property_type = "";
                    //Toast.makeText(getActivity(), "Select Type of investment Sought", Toast.LENGTH_LONG).show();
                }
                if (Str_plan_to_purchase.contentEquals("Show All")) {
//                    Animation anm = Shake_Animation();
//                    Spnr_Plan_to_purchase.startAnimation(anm);
//                    Toast.makeText(getActivity(), "Select Plan to purchase", Toast.LENGTH_LONG).show();
                    Str_plan_to_purchase = "";
                }
                if (Str_preffer_time.contentEquals("Show All")) {
//                    Animation anm = Shake_Animation();
//                    Spnr_Prefed_time.startAnimation(anm);
//                    Toast.makeText(getActivity(), "Select preferred timescale", Toast.LENGTH_LONG).show();
                    Str_preffer_time = "";
                }
                if (Str_looking.contentEquals("Show All")) {
//                    Animation anm = Shake_Animation();
//                    Spnr_Looking_for.startAnimation(anm);
//                    Toast.makeText(getActivity(), "Select Looking for", Toast.LENGTH_LONG).show();
                    Str_preffer_time = "";
                }
                if (Str_invest.contentEquals("Show All")) {
//                    Animation anm = Shake_Animation();
//                    Spnr_How_much_invest.startAnimation(anm);
//                    Toast.makeText(getActivity(), "Select set aside to invest", Toast.LENGTH_LONG).show();
                    Str_invest = "";
                }
                if (Str_risk.contentEquals("Show All")) {
//                    Animation anm = Shake_Animation();
//                    Spnr_Risk.startAnimation(anm);
//                    Toast.makeText(getActivity(), "Select risk appetite", Toast.LENGTH_LONG).show();
                    Str_risk = "";
                }
                if (Str_location.contentEquals("")) {
//                    Animation anm = Shake_Animation();
//                    autocompleteView.startAnimation(anm);
//                    Toast.makeText(getActivity(), "Select Location", Toast.LENGTH_LONG).show();
                    Str_location = "";
                    // Cateory_you_fall_id="";
                }
//                else {
                new Search_result().execute();
                // }
            }
        });
        //Spnr_Experienced.setPrompt("Select an item");
        final ArrayAdapter<String> spinnerArrayAdapter_Risk = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, Risk) {
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                } else {
//
//                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        Spnr_Risk.setAdapter(spinnerArrayAdapter_Risk);

        // Spnr_Risk.setSelection(3);
        Spnr_Risk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Str_risk = Risk_id[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final ArrayAdapter<String> spinnerArrayAdapter_HOw_invest = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, How_much_invest) {
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                } else {
//                    return true;
//                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        Spnr_How_much_invest.setAdapter(spinnerArrayAdapter_HOw_invest);
        String Str_position = (shared.getString("How_much_invest_postion", "nodata"));
        String Str_invest_value = (shared.getString("How_much_invest_", "nodata"));
        if (!Str_position.contentEquals("nodata")) {
            int int_postion = Integer.parseInt(Str_position);
            Str_invest = Str_invest_value;
            Spnr_How_much_invest.setSelection(int_postion);
        }
        Spnr_How_much_invest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Str_invest = How_much_invest_id[position];
                String Str_postion = String.valueOf(position);
                SharedPreferences.Editor editor = shared.edit();

                editor.putString("How_much_invest_postion", Str_postion);
                editor.putString("How_much_invest_", Str_invest);
                editor.putString("Str_invest", Str_invest);
                editor.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final ArrayAdapter<String> spinnerArrayAdapter_Looking_for = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, Looking_for) {
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                } else {
//                    return true;
//                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        Spnr_Looking_for.setAdapter(spinnerArrayAdapter_Looking_for);
        String Str_position_lo = (shared.getString("Looking_for_postion", "nodata"));
        String Str_invest_value_lo = (shared.getString("Looking_for_", "nodata"));
        if (!Str_position_lo.contentEquals("nodata")) {
            int int_postion = Integer.parseInt(Str_position_lo);
            Str_looking = Str_invest_value_lo;
            Spnr_Looking_for.setSelection(int_postion);
        }
        Spnr_Looking_for.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Str_looking = Looking_for_id[position];
                String Str_postion = String.valueOf(position);
                SharedPreferences.Editor editor = shared.edit();

                editor.putString("Looking_for_postion", Str_postion);
                editor.putString("Looking_for_", Str_looking);
                editor.putString("Str_looking", Str_looking);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final ArrayAdapter<String> spinnerArrayAdapter_Prefferd = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, Prefferd_time_scale) {
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                } else {
//                    return true;
//                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        Spnr_Prefed_time.setAdapter(spinnerArrayAdapter_Prefferd);
        String Str_position_P_time = (shared.getString("Prefered_time_postion", "nodata"));
        String Str_invest_value_Pr_time = (shared.getString("Prefered_time_", "nodata"));
        if (!Str_position_P_time.contentEquals("nodata")) {
            int int_postion = Integer.parseInt(Str_position_P_time);
            Str_preffer_time = Str_invest_value_Pr_time;
            Spnr_Prefed_time.setSelection(int_postion);
        }
        Spnr_Prefed_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Str_preffer_time = Prefferd_time_scale_id[position];

                String Str_postion = String.valueOf(position);
                SharedPreferences.Editor editor = shared.edit();

                editor.putString("Prefered_time_postion", Str_postion);
                editor.putString("Prefered_time_", Str_preffer_time);
                editor.putString("Str_preffer_time", Str_preffer_time);
                editor.commit();
                // Toast.makeText(getActivity(), "added", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final ArrayAdapter<String> spinnerArrayAdapter_Plan_to_purchase = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, Plan_to_purchase) {
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                } else {
//                    return true;
//                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        Spnr_Plan_to_purchase.setAdapter(spinnerArrayAdapter_Plan_to_purchase);
        String Str_position_Plan_pos = (shared.getString("Spnr_Plan_to_postion", "nodata"));
        String Str_invest_value_Plan = (shared.getString("Spnr_Plan_to_", "nodata"));
        if (!Str_position_Plan_pos.contentEquals("nodata")) {
            int int_postion = Integer.parseInt(Str_position_Plan_pos);
            Str_plan_to_purchase = Str_invest_value_Plan;
            Spnr_Plan_to_purchase.setSelection(int_postion);
        }
        //  Spnr_Plan_to_purchase.setSelection(3);
        Spnr_Plan_to_purchase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Str_plan_to_purchase = Plan_to_purchase_id[position];
                String Str_postion = String.valueOf(position);
                SharedPreferences.Editor editor = shared.edit();

                editor.putString("Spnr_Plan_to_postion", Str_postion);
                editor.putString("Spnr_Plan_to_", Str_plan_to_purchase);
                editor.putString("Str_plan_to_purchase", Str_plan_to_purchase);

                editor.commit();
                // Toast.makeText(getActivity(), "added", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayAdapter<String> spinnerArrayAdapter_Property_type = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, Property_type) {
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                } else {
//                    return true;
//                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        Spnr_Property_tpe.setAdapter(spinnerArrayAdapter_Property_type);
        String Str_position_Property_pos = (shared.getString("Spnr_Property_tpe_postion", "nodata"));
        String Str_invest_value_Property = (shared.getString("Spnr_Property_tpe__", "nodata"));
        if (!Str_position_Property_pos.contentEquals("nodata")) {
            int int_postion = Integer.parseInt(Str_position_Property_pos);
            Str_property_type = Str_invest_value_Property;
            Spnr_Property_tpe.setSelection(int_postion);
        }
        Spnr_Property_tpe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Str_property_type = Property_type_id[position];
                String Str_postion = String.valueOf(position);
                SharedPreferences.Editor editor = shared.edit();

                editor.putString("Spnr_Property_tpe_postion", Str_postion);
                editor.putString("Spnr_Property_tpe__", Str_property_type);
                editor.putString("Str_property_type", Str_property_type);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayAdapter<String> spinnerArrayAdapter_price = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, Price_minimum) {
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                } else {
//                    return true;
//                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
//
        Spnr_Price_min.setAdapter(spinnerArrayAdapter_price);
        String Str_position_Price_min_pos = (shared.getString("Spnr_Price_postion", "nodata"));
        String Str_invest_value_Price_min = (shared.getString("Spnr_Price_tpe__", "nodata"));
        if (!Str_position_Price_min_pos.contentEquals("nodata")) {
            int int_postion = Integer.parseInt(Str_position_Price_min_pos);
            Str_priceminimu = Str_invest_value_Price_min;
            Spnr_Price_min.setSelection(int_postion);
        }
        Spnr_Price_min.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (Str_type_rent_or_sale.contentEquals("1")) {
                    Str_priceminimu = Price_minimum[position];
                } else if (Str_type_rent_or_sale.contentEquals("2")) {
                    Str_priceminimu = Price_minimum_for_rent[position];
                }

                // Str_priceminimu = Price_minimum[position];
                Str_priceminimu = Str_priceminimu.replace("£", "");
                String Str_postion = String.valueOf(position);
                SharedPreferences.Editor editor = shared.edit();

                editor.putString("Spnr_Price_postion", Str_postion);
                editor.putString("Spnr_Price_tpe__", Str_priceminimu);
                editor.putString("Str_priceminimu", Str_priceminimu);
                editor.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final ArrayAdapter<String> spinnerArrayAdapter_price_max = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, Price_Maximu) {
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                } else {
//                    return true;
//                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
//
//
        Spnr_Price_max.setAdapter(spinnerArrayAdapter_price_max);

        String Str_position_Price_max_pos = (shared.getString("Spnr_Price_max_postion", "nodata"));
        String Str_invest_value_Price_max = (shared.getString("Spnr_Price_max_tpe__", "nodata"));
        if (!Str_position_Price_max_pos.contentEquals("nodata")) {
            int int_postion = Integer.parseInt(Str_position_Price_max_pos);
            Str_price_maximum = Str_invest_value_Price_max;
            Spnr_Price_max.setSelection(int_postion);
        }

        Spnr_Price_max.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (Str_type_rent_or_sale.contentEquals("1")) {
                    // Str_priceminimu = Price_minimum[position];
                    Str_price_maximum = Price_Maximu[position];
                } else if (Str_type_rent_or_sale.contentEquals("2")) {
                    Str_price_maximum = Price_Maximu_forrent[position];
                    // Str_priceminimu = Price_minimum_for_rent[position];
                }
                // Str_price_maximum = Price_Maximu[position];
                Str_price_maximum = Str_price_maximum.replace("£", "");
                Str_price_maximum = Str_price_maximum.replace(",", "");
                Str_priceminimu = Str_priceminimu.replace(",", "");
                try {
//                    Double Doble_minimum = Double.valueOf(Str_price_maximum);
//                    Double Doble_maximum = Double.valueOf(Str_price_maximum);
//                    int priceminimu = Integer.parseInt(Str_priceminimu);
//                    int pricemaximum = Integer.parseInt(Str_price_maximum);
//                    if (priceminimu > pricemaximum) {
//                        Toast.makeText(getActivity(), "Price should be greater than minimum", Toast.LENGTH_LONG).show();
//                        Str_price_maximum = "";
//                    }
                    String Str_postion = String.valueOf(position);
                    SharedPreferences.Editor editor = shared.edit();

                    editor.putString("Spnr_Price_max_postion", Str_postion);
                    editor.putString("Spnr_Price_max_tpe__", Str_price_maximum);
                    editor.putString("Str_price_maximum", Str_price_maximum);
                    editor.commit();
                } catch (java.lang.NumberFormatException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ///////////////////////////////////////
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, experienced_array) {
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                } else {
//                    return true;
//                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        Spnr_Experienced.setAdapter(spinnerArrayAdapter);
        String Str_position_Price_Experience_pos = (shared.getString("Spnr_Experienced_postion", "nodata"));
        String Str_invest_value_Experience = (shared.getString("Spnr_Experienced__", "nodata"));
        if (!Str_position_Price_Experience_pos.contentEquals("nodata")) {
            int int_postion = Integer.parseInt(Str_position_Price_Experience_pos);
            Str_experienced = Str_invest_value_Experience;
            Spnr_Experienced.setSelection(int_postion);
        }
        Spnr_Experienced.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Str_experienced = experienced_array_id[position];
                String Str_postion = String.valueOf(position);
                SharedPreferences.Editor editor = shared.edit();

                editor.putString("Spnr_Experienced_postion", Str_postion);
                editor.putString("Spnr_Experienced__", Str_experienced);
                editor.putString("Str_experienced", Str_experienced);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////////////////////////////////////////////

        final ArrayAdapter<String> spinner_fund = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, Fund_this_purchase) {
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                } else {
//                    return true;
//                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinner_fund.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        Spnr_FND_THIS_p.setAdapter(spinner_fund);
        String Str_position_Fund_pos = (shared.getString("Spnr_FND_THIS_postion", "nodata"));
        String Str_Fund_ = (shared.getString("Spnr_FND_THIS__", "nodata"));
        if (!Str_position_Fund_pos.contentEquals("nodata")) {
            int int_postion = Integer.parseInt(Str_position_Fund_pos);
            Str_fund = Str_Fund_;
            Spnr_FND_THIS_p.setSelection(int_postion);
        }
        Spnr_FND_THIS_p.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Str_fund = Fund_this_purchase_id[position];
                String Str_postion = String.valueOf(position);
                SharedPreferences.Editor editor = shared.edit();

                editor.putString("Spnr_FND_THIS_postion", Str_postion);
                editor.putString("Spnr_FND_THIS__", Str_fund);
                editor.putString("Str_fund", Str_fund);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final ArrayAdapter<String> spinner_Category = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, Cateory_you_fall) {
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                } else {
//                    return true;
//                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
//        spinner_Category.setDropDownViewResource
//                (android.R.layout.simple_spinner_dropdown_item);
        Spnr_Category_youfall.setAdapter(spinner_Category);

        Spnr_Category_youfall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Str_category = Cateory_you_fall_id[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


/////////////////////////////////////////////////////////////////////////////
        final ArrayAdapter<String> spinner_desire = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, Desired) {
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                } else {
//                    return true;
//                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
//        spinner_Category.setDropDownViewResource
//                (android.R.layout.simple_spinner_dropdown_item);
        Spnr_Desired.setAdapter(spinner_desire);
        String Str_Desired_pos = (shared.getString("Spnr_Desired_postion", "nodata"));
        String Str_Desired_ = (shared.getString("Spnr_Desired__", "nodata"));
        if (!Str_Desired_pos.contentEquals("nodata")) {
            int int_postion = Integer.parseInt(Str_Desired_pos);
            StrDesired = Str_Desired_;
            Spnr_Desired.setSelection(int_postion);
        }
        Spnr_Desired.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StrDesired = Desired_id[position];
                String Str_postion = String.valueOf(position);
                SharedPreferences.Editor editor = shared.edit();

                editor.putString("Spnr_Desired_postion", Str_postion);
                editor.putString("Spnr_Desired__", StrDesired);
                editor.putString("StrDesired", StrDesired);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Spnr_Experienced.
        // Spinner item selection Listener
        //addListenerOnSpinnerItemSelection();

        ///new SELECT_OPTION().execute();

    }


    private OnFragmentInteractionListener mListener;

    public Home_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Home_fragment newInstance(String param1, String param2) {
        Home_fragment fragment = new Home_fragment();
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
        return inflater.inflate(R.layout.content_main, container, false);
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

    private class SELECT_OPTION extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {
        String android_id;

        JSONObject jsonnode, json_User;

        String str = "nostatus";
        String Name, access_tocken, Ostype;


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
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            //nameValuePairs.add(new BasicNameValuePair("email", email_fb));
            //nameValuePairs.add(new BasicNameValuePair("userID", userID));


            String jsonStr = sh.makeServiceCall(SERVER.APP_SETUP_API,
                    ServiceHandler.POST, nameValuePairs);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                //                    JSONObject jsonObj = null;
//                    try {
//                        jsonObj = new JSONObject(jsonStr);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                // Getting JSON Array node
                // JSONArray array1 = null;
                JSONArray jArr = null;
                try {
                    jArr = new JSONArray(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int count = 0; count < jArr.length(); count++) {
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = jArr.getJSONObject(count);
                        String quantity = jsonObj.getString("quantity");
                        String start_date = jsonObj.getString("startDate");
                        JSONObject pakage = jsonObj.getJSONObject("subscriptionPackage");
                        String str_tittle = pakage.getString("title");
                        String subscriptionPackageID = pakage.getString("subscriptionPackageID");
                        String description = pakage.getString("description");
                        String amount = pakage.getString("amount");
                        String type = pakage.getString("type");
                        String position = pakage.getString("position");
                        HashMap<String, String> Explorer = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        Explorer.put("quantity", quantity);
                        Explorer.put("str_tittle", str_tittle);
                        Explorer.put("subscriptionPackageID", subscriptionPackageID);
                        Explorer.put("description", start_date);
                        Explorer.put("amount", amount);
                        Explorer.put("type", type);
                        Explorer.put("position", position);


                        // adding contact to contact list
                        // List_Subscription.add(Explorer);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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


        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }

    class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

        ArrayList<String> resultList;

        Context mContext;
        int mResource;

        PlaceAPI mPlaceAPI = new PlaceAPI();

        public PlacesAutoCompleteAdapter(Context context, int resource) {
            super(context, resource);

            mContext = context;
            mResource = resource;
        }

        @Override
        public int getCount() {
            // Last item will be the footer
            return resultList.size();
        }

        @Override
        public String getItem(int position) {
            return resultList.get(position);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        resultList = mPlaceAPI.autocomplete(constraint.toString());

                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };

            return filter;
        }
    }

    public Animation Shake_Animation() {
        Animation shake = new TranslateAnimation(0, 5, 0, 0);
        shake.setInterpolator(new CycleInterpolator(5));
        shake.setDuration(300);


        return shake;
    }

    private class Search_result extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


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
            nameValuePairs.add(new BasicNameValuePair("experienced", Str_experienced));
            nameValuePairs.add(new BasicNameValuePair("purchase_type", Str_fund));
            //    nameValuePairs.add(new BasicNameValuePair("purchase_type_other", ""));
            // nameValuePairs.add(new BasicNameValuePair("categories", Str_category));
            nameValuePairs.add(new BasicNameValuePair("price_from", Str_price_maximum));
            nameValuePairs.add(new BasicNameValuePair("price_to", Str_priceminimu));
            nameValuePairs.add(new BasicNameValuePair("clocation", Str_location));
            nameValuePairs.add(new BasicNameValuePair("lat", "1"));
            nameValuePairs.add(new BasicNameValuePair("lng", "1"));
            nameValuePairs.add(new BasicNameValuePair("property_type", Str_property_type));
            nameValuePairs.add(new BasicNameValuePair("desired_roi", StrDesired));
            nameValuePairs.add(new BasicNameValuePair("purchase_plan", Str_plan_to_purchase));
            nameValuePairs.add(new BasicNameValuePair("preferred_timescale", Str_preffer_time));
            nameValuePairs.add(new BasicNameValuePair("looking_for", Str_looking));
            nameValuePairs.add(new BasicNameValuePair("set_invests", Str_invest));
            nameValuePairs.add(new BasicNameValuePair("types", Str_type_rent_or_sale));
            nameValuePairs.add(new BasicNameValuePair("radius", ""));


            String jsonStr = sh.makeServiceCall_withHeader(SERVER.SET_USER_SEARCH_CRITERIA,
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
//                    if (status.contentEquals("true")) {
//                        JSONObject jsonObj_data = null;
//                        jsonObj_data = jsonObj.getJSONObject("data");
//
//                    }

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
            if (status.contentEquals("true")) {
                Intent i1 = new Intent(getActivity(), Property_listing_screen.class);
                startActivity(i1);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_left);
            } else {
                Toast.makeText(getActivity(), Message, Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }
}
