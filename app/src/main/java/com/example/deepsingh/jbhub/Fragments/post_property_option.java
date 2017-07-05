package com.example.deepsingh.jbhub.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepsingh.jbhub.Location_api.PlaceAPI;
import com.example.deepsingh.jbhub.Login_screen;
import com.example.deepsingh.jbhub.MainActivity;
import com.example.deepsingh.jbhub.Multipart_enttity.AndroidMultiPartEntity;
import com.example.deepsingh.jbhub.Multipart_enttity.Utility;
import com.example.deepsingh.jbhub.Post_property_screen;
import com.example.deepsingh.jbhub.Property_detail_info;
import com.example.deepsingh.jbhub.Property_listing_screen;
import com.example.deepsingh.jbhub.R;
import com.example.deepsingh.jbhub.Service_handler.SERVER;
import com.example.deepsingh.jbhub.Service_handler.ServiceHandler;
import com.example.deepsingh.jbhub.success_screen;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link post_property_option.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link post_property_option#newInstance} factory method to
 * create an instance of this fragment.
 */
public class post_property_option extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressDialog pDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Spinner Spnr_Category_youfall;
    Spinner Spnr_Price;
    File f;
    long totalSize = 0;
    Spinner Spnr_Property_tpe;
    EditText Edt_txt_address, EdtTxt_property_description, Edt_txt_key_description, Edt_phone;
    RelativeLayout Rlv_upload_property_pics;
    Button Edt_txt_floor_plan;
    Button Btn_submit;
    Button Btn_rent;
    Bitmap bitmap;
    String selectedImagePath = "";
    String picturePath;
    String Str_image_one = "", Str_image_2 = "", str_image3 = "", Str_image4 = "", Str_imag5 = "";
    Cursor c;
    String Access_tocken = "";
    String Device_id = "";
    String status = "";
    String error = "";
    String Str_price = "", Str_category = "", Str_Property_type = "";
    String Str_result_code = "";
    AutoCompleteTextView autocompleteView;
    String Str_type_rent_or_sale = "1";

    String Message;
    String[] Price = {"Price", "£50,000", "£100,000", "£150,000", "£200,000", "£250,000", "£300,000"
            , "£350,000", "£400,000", "£450,000", "£500,000", "£550,000", "£600,000", "£650,000", "£700,000", "£750,000"
            , "£800,000", "£850,000", "£900,000", "£950,000", "£1,000,000", "£1,050,000", "£1,100,000", "£1,150,000", "£1,200,000", "£1,250,000"
            , "£1,300,000", "£1,350,000", "£1,400,000", "£1,450,000", "£1,500,000", "£1,550,000", "£1,600,000", "£1,650,000", "£1,700,000", "£1,750,000", "£1,800,000"
            , "£1,850,000", "£1,900,000", "£1,950,000", "£2,000,000"};
    String[] Price__forrent = {"Price", "£100", "£150", "£200", "£250", "£300", "£350"
            , "£400", "£450", "£500", "£600", "£700", "£800", "£900", "£1,000", "£1,100"
            , "£1,200", "£1,300", "£1,400", "£1,500", "£1,750", "£2,000", "£2,250", "£2,500", "£2,750", "£3,000"
            , "£3,500", "£4,000", "£4,500", "£5,000", "£5,500", "£6,000", "£6,500", "£7,000", "£8,000", "£9,000", "£10,000"
            , "£12,500", "£15,000", "£17,500", "£20,000", "£25,000", "£30,000", "£35,000", "£40,000"};

    String[] Property_type = {"Type of Property", "Buy to Let", "Flip", "Commercial Property", "Commercial to Residential", "Rent2Rent", "Lease Options", "Services Accommodation", "Services Offices", "Offices", "Retail", "Industrial", "Mixed Use"
            , "Flat/Apartments", "Houses", "Land", "HMO", "Others"};
    String[] Property_type_id = {"Type of Property", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "15"
            , "12", "13", "14", "16", "100"};

    String[] Cateory_you_fall = {"Category you fall under", "Developer", "Advisor", "Broker", "Owner Operator REIT", "Architect", "Engineer", "Contractor", "Surveyor", "Professional Services Provider", "Crowd Funder", "Insurance Provider", "Others"};
    String[] Cateory_you_fall_id = {"Category you fall under", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "100"};
    private OnFragmentInteractionListener mListener;
    ImageView Img_1, Img_2, img3, img4, img5, Img_cross1, Img_cross2, Img_cross3, Img_cross4, Img_cross_floor_plan;
    ImageView Img_Floor_plan;

    String Str_ng_floor_plan_imag_path = "";
    String Str_Check_status = "";

    RadioButton Rdio_button_sale, Radio_buttn_torent;

    public post_property_option() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment post_property_option.
     */
    // TODO: Rename and change types and number of parameters
    public static post_property_option newInstance(String param1, String param2) {
        post_property_option fragment = new post_property_option();
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spnr_Category_youfall = (Spinner) view.findViewById(R.id.spinner_Category);
        Spnr_Price = (Spinner) view.findViewById(R.id.spinner);
        Spnr_Property_tpe = (Spinner) view.findViewById(R.id.spinner_imgtype);
        Edt_txt_address = (EditText) view.findViewById(R.id.Edt_adress);
        EdtTxt_property_description = (EditText) view.findViewById(R.id.Edt_Description);
        Edt_txt_key_description = (EditText) view.findViewById(R.id.Edt_Key_features);
        Edt_txt_floor_plan = (Button) view.findViewById(R.id.Edt_Floor_plan);
        Edt_phone = (EditText) view.findViewById(R.id.Edt_Phone);
        Rlv_upload_property_pics = (RelativeLayout) view.findViewById(R.id.Rlv_upload_property_image);
        Btn_submit = (Button) view.findViewById(R.id.button6);
        Img_1 = (ImageView) view.findViewById(R.id.imageView17);
        Img_2 = (ImageView) view.findViewById(R.id.imageView14);
        img3 = (ImageView) view.findViewById(R.id.imageView22);
        img4 = (ImageView) view.findViewById(R.id.imageView19);
        Img_Floor_plan = (ImageView) view.findViewById(R.id.imageView27);
        Img_cross_floor_plan = (ImageView) view.findViewById(R.id.imageView29);
        //img5 = (ImageView) view.findViewById(R.id.imageView23);
        Img_cross1 = (ImageView) view.findViewById(R.id.imageView18);
        Img_cross2 = (ImageView) view.findViewById(R.id.imageView20);
        Img_cross3 = (ImageView) view.findViewById(R.id.imageView25);
        Img_cross4 = (ImageView) view.findViewById(R.id.imageView24);
        Rdio_button_sale = (RadioButton) view.findViewById(R.id.radioButton);
        Radio_buttn_torent = (RadioButton) view.findViewById(R.id.radioButton2);
        SharedPreferences shared = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
        Access_tocken = (shared.getString("Acess_tocken", "nodata"));
        Device_id = (shared.getString("device_id", "nodata"));
        // Img_cross5 = (ImageView) view.findViewById(R.id.imageView26);
        autocompleteView = (AutoCompleteTextView) view.findViewById(R.id.editText7);
        autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_txt_view));
        Rdio_button_sale.setChecked(true);

        //   ImageView Img_cross1, Img_cross2, Img_cross3, Img_cross4, Img_cross_floor_plan;
        Img_cross1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Str_image_one = "";
                Img_cross1.setVisibility(View.INVISIBLE);
                Img_1.setImageResource(android.R.drawable.ic_menu_camera);

            }
        });

        Img_cross2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Str_image_2 = "";
                Img_cross2.setVisibility(View.INVISIBLE);
                Img_2.setImageResource(android.R.drawable.ic_menu_camera);

            }
        });

        Img_cross3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_image3 = "";
                Img_cross3.setVisibility(View.INVISIBLE);
                img3.setImageResource(android.R.drawable.ic_menu_camera);

            }
        });

        Img_cross4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Str_image4 = "";
                Img_cross4.setVisibility(View.INVISIBLE);

                img4.setImageResource(android.R.drawable.ic_menu_camera);

            }
        });
        Img_cross_floor_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Str_Check_status = "";
                 Img_cross_floor_plan.setVisibility(View.INVISIBLE);

                Img_Floor_plan.setImageResource(android.R.drawable.ic_menu_camera);

            }
        });

        Rdio_button_sale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Radio_buttn_torent.setChecked(false);
                    Str_type_rent_or_sale = "1";

                    final ArrayAdapter<String> spinnerArrayAdapter_Price = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_dropdown_item_1line, Price) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                // Disable the first item from Spinner
                                // First item will be use for hint
                                return false;
                            } else {
                                return true;
                            }
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

                    Spnr_Price.setAdapter(spinnerArrayAdapter_Price);
                    //  Price = Price.clone();
                }

            }
        });
        Radio_buttn_torent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Rdio_button_sale.setChecked(false);
                    Str_type_rent_or_sale = "2";

                    final ArrayAdapter<String> spinnerArrayAdapter_Price = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_dropdown_item_1line, Price__forrent) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                // Disable the first item from Spinner
                                // First item will be use for hint
                                return false;
                            } else {
                                return true;
                            }
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

                    Spnr_Price.setAdapter(spinnerArrayAdapter_Price);
                    // Price = Price__forrent.clone();
                }
            }
        });
        Edt_txt_floor_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Str_Check_status = "floor_plan";
                boolean result = Utility.checkPermission(getActivity());

                if (result) {
                    selectImage_new();

                    //galleryIntent();


                }
            }
        });
        final ArrayAdapter<String> spinnerArrayAdapter_category = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, Cateory_you_fall) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
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

        Spnr_Category_youfall.setAdapter(spinnerArrayAdapter_category);
        Spnr_Category_youfall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Str_category = Cateory_you_fall_id[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final ArrayAdapter<String> spinnerArrayAdapter_Price = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, Price) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
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

        Spnr_Price.setAdapter(spinnerArrayAdapter_Price);
        Spnr_Price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (Str_type_rent_or_sale.contentEquals("1")) {
                    Str_price = Price[position];
                } else {
                    Str_price = Price__forrent[position];
                }

                Str_price = Str_price.replace("£", "");
                Str_price = Str_price.replace(",", "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final ArrayAdapter<String> spinnerArrayAdapter_Property_type = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, Property_type) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
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
        Spnr_Property_tpe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Str_Property_type = Property_type_id[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Str_price.contentEquals("Price")) {

                    Toast.makeText(getActivity(), "Select Price", Toast.LENGTH_LONG).show();
                } else if (Str_Property_type.contentEquals("Type of Property")) {

                    Toast.makeText(getActivity(), "Select Property Type", Toast.LENGTH_LONG).show();
                } else if (autocompleteView.getText().toString().contentEquals("")) {

                    Toast.makeText(getActivity(), "Select Location", Toast.LENGTH_LONG).show();
                } else if (Edt_txt_address.getText().toString().contentEquals("")) {

                    Toast.makeText(getActivity(), "Enter Address", Toast.LENGTH_LONG).show();
                } else if (EdtTxt_property_description.getText().toString().contentEquals("")) {

                    Toast.makeText(getActivity(), "Enter Property Description", Toast.LENGTH_LONG).show();
                } else if (Edt_txt_key_description.getText().toString().contentEquals("")) {

                    Toast.makeText(getActivity(), "Enter Key Description", Toast.LENGTH_LONG).show();
                } else if (Edt_txt_floor_plan.getText().toString().contentEquals("")) {
                    //  Edt_txt_floor_plan
                    Toast.makeText(getActivity(), "Enter Floor Plan", Toast.LENGTH_LONG).show();
                } else if (Edt_phone.getText().toString().contentEquals("")) {
                    //  Edt_txt_floor_plan
                    Toast.makeText(getActivity(), "Enter Phone", Toast.LENGTH_LONG).show();
                } else if (Str_image_one.contentEquals("")) {
                    //  Edt_txt_floor_plan
                    Toast.makeText(getActivity(), "Select atleast one image of property", Toast.LENGTH_LONG).show();
                } else {
                    new Upload_profile_pic().execute();
                }
            }
        });
        Rlv_upload_property_pics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = Utility.checkPermission(getActivity());

                if (result) {
                    selectImage_new();

                    //galleryIntent();


                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_post_property_screen, container, false);
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

    private void selectImage_new() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 1) {
                Str_result_code = "1";
                // onCaptureImageResult(data);
                f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                new Background().execute();

                // new UploadFileToServer().execute();
            } else if (requestCode == 2) {
                Str_result_code = "2";
                try {
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    picturePath = c.getString(columnIndex);

                    selectedImagePath = picturePath;

                    new Background().execute();
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {

            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

//            try {
//                bitmap.recycle();
//            }catch (java.lang.RuntimeException e){
//                e.printStackTrace();
//            }
            return bmRotated;

        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }

    }

    public String saveimage(Bitmap bmp, String file_name) {
// Find the SD Card path
        String path = null;
        File filepath = Environment.getExternalStorageDirectory();
        OutputStream output;
        // Create a new folder in SD Card

        File dir = new File(filepath.getAbsolutePath()
                + "/JVHUB/");
        if (dir.exists() && dir.isDirectory()) {
            // do something here


            // Create a name for the saved image
            String timeStamp = "JVHUB_pic";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
            String currentTimeStamp = dateFormat.format(new Date());
            File file = new File(dir, currentTimeStamp + ".png");

            // Show a toast message on successful save

            try {

                output = new FileOutputStream(file);
                path = file.getAbsolutePath();

                // Compress into png format image from 0% - 100%
                bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
                output.flush();
                output.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            dir.mkdirs();
            String timeStamp = "JVHUB_pic";
            File file = new File(dir, timeStamp + ".png");

            // Show a toast message on successful save

            try {

                output = new FileOutputStream(file);
                path = file.getAbsolutePath();

                // Compress into png format image from 0% - 100%
                bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
                output.flush();
                output.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return path;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public class Background extends AsyncTask<String, Integer, String> {
        Bitmap bmRotated = null;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
            if (Str_Check_status.contentEquals("floor_plan")) {
                Str_Check_status = selectedImagePath;
                Img_Floor_plan.setImageBitmap(bmRotated);

                Img_cross_floor_plan.setVisibility(View.VISIBLE);

            } else {

                if (Str_image_one.contentEquals("")) {
                    Img_1.setImageBitmap(bmRotated);
                    Str_image_one = selectedImagePath;
                    Img_cross1.setVisibility(View.VISIBLE);
                } else if (Str_image_2.contentEquals("")) {
                    Img_2.setImageBitmap(bmRotated);
                    Str_image_2 = selectedImagePath;
                    Img_cross2.setVisibility(View.VISIBLE);

                } else if (str_image3.contentEquals("")) {
                    img3.setImageBitmap(bmRotated);
                    str_image3 = selectedImagePath;
                    Img_cross3.setVisibility(View.VISIBLE);
                } else if (Str_image4.contentEquals("")) {
                    img4.setImageBitmap(bmRotated);
                    Str_image4 = selectedImagePath;
                    Img_cross4.setVisibility(View.VISIBLE);
                }
            }

            //str_image3,Str_image4,Str_imag5;
            if (isNetworkAvailable()) {
                //   new profile_fragment.Upload_profile_pic().execute();
            } else {
                Toast.makeText(getActivity(), "No Network Available", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            if (Str_result_code.contentEquals("2")) {


                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(picturePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                bitmapOptions.inSampleSize = 2;
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath, bitmapOptions));
                bmRotated = rotateBitmap(thumbnail, orientation);

                selectedImagePath = saveimage(bmRotated, "JVHUB");


                c.close();
            } else if (Str_result_code.contentEquals("1")) {
                try {

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmapOptions.inSampleSize = 2;

                    // selectedImagePath = selectedImageUri.getPath();
                    try {
                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);

                        //img_profile_pic.setImageBitmap(bitmap);
                        Uri tempUri = getImageUri(getActivity(), bitmap);
                        File finalFile = new File(getRealPathFromURI(tempUri));

                        selectedImagePath = finalFile.getAbsolutePath();
                        ExifInterface exif = null;
                        try {
                            exif = new ExifInterface(f.getAbsolutePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);

                        bmRotated = rotateBitmap(bitmap, orientation);
                        //BITMAP_RESIZER
                        //   Bitmap bit_big_image_crop = cropToSquare(bit_big_image);
                        // Bitmap bit_big_image_crop = BITMAP_RESIZER(bit_big_image,400,400);
                        selectedImagePath = saveimage(bmRotated, "jvhub");
                        //  arrPath[position] = squareimagepath;


                        //   Log.w("path of image from gallery......******************.........", picturePath+"");
                        //  Img_image.setImageBitmap(bmRotated);

                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

    }

    private class Upload_profile_pic extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.show();
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible


            // updating progress bar value
            pDialog.setProgress(progress[0]);

            // updating percentage value
            //txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(SERVER.CREATE_PROPERTY);
            httppost.addHeader("X-TOKEN", Access_tocken);
            httppost.addHeader("X-DEVICE", Device_id);
            try {

                try {
                    AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                            new AndroidMultiPartEntity.ProgressListener() {

                                @Override
                                public void transferred(long num) {
                                    publishProgress((int) ((num / (float) totalSize) * 100));
                                }
                            });
//

                    File sourceFile1 = new File(Str_image_one);
                    entity.addPart("propertyimages[]", new FileBody(sourceFile1));
                    if (!Str_image_2.contentEquals("")) {
                        File sourceFile2 = new File(Str_image_2);
                        entity.addPart("propertyimages[]", new FileBody(sourceFile2));
                    }
                    if (!str_image3.contentEquals("")) {
                        File sourceFile3 = new File(str_image3);
                        entity.addPart("propertyimages[]", new FileBody(sourceFile3));
                    }
                    if (!Str_image4.contentEquals("")) {
                        File sourceFile4 = new File(Str_image4);
                        entity.addPart("propertyimages[]", new FileBody(sourceFile4));
                    }


                    // Adding file data to http body

                    //entity.addPart("gender", new StringBody(gender));
                    File sourceFile_floor_plan = new File(Str_Check_status);

                    entity.addPart("property_type", new StringBody(Str_Property_type));
                    entity.addPart("price", new StringBody(Str_price));
                    //  entity.addPart("category", new StringBody(Str_category));
                    entity.addPart("address", new StringBody(Edt_txt_address.getText().toString()));
                    entity.addPart("location", new StringBody(autocompleteView.getText().toString()));
                    entity.addPart("description", new StringBody(EdtTxt_property_description.getText().toString()));
                    entity.addPart("floor_plan", new FileBody(sourceFile_floor_plan));
                    entity.addPart("specification", new StringBody(Edt_txt_key_description.getText().toString()));
                    entity.addPart("contact_phone", new StringBody(Edt_phone.getText().toString()));
                    entity.addPart("types", new StringBody(Str_type_rent_or_sale));

                    // entity.addPart("pid", new StringBody(""));
                    // Edt_phone.getText().toString(


                    // Extra parameters if you want to pass to server

                    totalSize = entity.getContentLength();
                    httppost.setEntity(entity);
//
                    // Making server call
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity r_entity = response.getEntity();

                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        // Server response
                        responseString = EntityUtils.toString(r_entity);
                    } else {
                        responseString = "Error occurred! Http Status Code: "
                                + statusCode;
                    }

                } catch (ClientProtocolException e) {
                    responseString = e.toString();
                } catch (IOException e) {
                    responseString = e.toString();
                }
                if (responseString != null) {
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = new JSONObject(responseString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Getting JSON Array node
                    // JSONArray array1 = null;
                    try {
                        status = jsonObj.getString("status");

                        if (status.contentEquals("true")) {
                            JSONObject jsonObj_data = null;
                            jsonObj_data = jsonObj.getJSONObject("data");
                            Message = jsonObj_data.getString("message");


                        }
                        // error = jsonObj.getString("errorCode");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (java.lang.NullPointerException e) {
                e.printStackTrace();
            }
            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Log.e(TAG, "Response from server: " + result);

            // showing the server response in an alert dialog
            //  showAlert(result);

            // new User_profile().execute();
            if (status.contentEquals("true")) {
                Intent i1 = new Intent(getActivity(), success_screen.class);
                startActivity(i1);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_left);

                // Toast.makeText(getActivity(), "Your Property Detail have been submitted successfully! It will publish shortly", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getActivity(), Message, Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();
            //progressBar.setVisibility(View.GONE);

        }

    }
}
