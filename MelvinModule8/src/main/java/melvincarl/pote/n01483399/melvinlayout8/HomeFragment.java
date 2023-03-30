package melvincarl.pote.n01483399.melvinlayout8;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private SharedPreferences.Editor editor;
    final String textLink = "https://www.google.ca/";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);
        Button button3 = view.findViewById(R.id.button3);

        Context context = requireActivity().getApplicationContext();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String jsoncourses = gson.toJson(getResources().getStringArray(R.array.courses));
                editor.putString("courses",jsoncourses);
                editor.apply();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File pathway = getContext().getFilesDir();
                File file = new File(pathway,"melvin.txt");
                FileOutputStream outputStream = null;
                try{
                    outputStream = new FileOutputStream(file);
                }catch (FileNotFoundException exception){
                    exception.printStackTrace();
                }

                try {
                   outputStream.write(getResources().getString(R.string.fullName).getBytes());
                   outputStream.write(getResources().getString(R.string.studentID).getBytes());
                   outputStream.write(getResources().getString(R.string.programmingLanguages).getBytes());
                    outputStream.write(getResources().getString(R.string.languages).getBytes());
                   outputStream.close();
                } catch (IOException ioException){
                    ioException.printStackTrace();
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            URL url;
                            HttpURLConnection urlConnection;

                            try {
                                url = new URL(textLink);
                                urlConnection = (HttpURLConnection) url.openConnection();
                                int responseCode = urlConnection.getResponseCode();
                                if (responseCode == HttpURLConnection.HTTP_OK) {
                                    BufferedReader bufferReader = new BufferedReader(new InputStreamReader(url.openStream()));
                                    String oneline;
                                    String dataFromInternet = "";

                                    while ((oneline = bufferReader.readLine()) != null) {
                                        dataFromInternet += oneline + "\n";
                                    }
                                    bufferReader.close();
                                    final String finalDataFromInternet = dataFromInternet;
                                    requireActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), finalDataFromInternet, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                } else {
                                    requireActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), String.valueOf(responseCode), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                requireActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), getResources().getString(R.string.error1), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }).start();
                } catch (Exception e) {
                    Toast.makeText(context, getResources().getString(R.string.error2) + e, Toast.LENGTH_LONG).show();
                }
            }
        });




        return view;
    }
}