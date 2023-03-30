/*
Name: Melvin Carl Pote
Student ID: N01483399
Section A
 */
package melvincarl.pote.n01483399.melvinlayout8;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class MelvinFragment extends Fragment {

    public MelvinFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pote, container, false);

        Button readCourses = view.findViewById(R.id.readCButton);
        Button readFile = view.findViewById(R.id.readFButton);
        TextView dataField = view.findViewById(R.id.secondFragment);

        readCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String coursesJson = preferences.getString("courses",null);
                dataField.setText("");

                if(coursesJson != null){
                    Gson gson = new Gson();
                    String [] courses = gson.fromJson(coursesJson,String[].class);

                    String textCourse = "";
                    for (String course : courses){
                        textCourse += course;
                    }
                    dataField.setText(textCourse);
                }
                else {
                    dataField.setText(getResources().getString(R.string.no_data_courses));
                }

            }
        });

        readFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataField.setText("");
                File filePath = getContext().getFilesDir();
                File file = new File(filePath,"melvin.txt");

                try{
                    FileInputStream fileInputStream = new FileInputStream(file);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    fileInputStream.close();

                    String contents = stringBuilder.toString();

                    if (contents.length() == 0){
                        dataField.setText(getResources().getString(R.string.no_data_file));
                    }
                    else {
                        dataField.setText(contents);
                    }

                }
                catch (IOException exception){
                    exception.printStackTrace();
                }

            }
        });


        return view;
    }
}