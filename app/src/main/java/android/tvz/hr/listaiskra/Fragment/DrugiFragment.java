package android.tvz.hr.listaiskra.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.tvz.hr.listaiskra.Database.ListItemDB;
import android.tvz.hr.listaiskra.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class DrugiFragment extends Fragment {

    ListItemDB listItemDB;


    public DrugiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=this.getArguments();
        if (bundle != null) {
            listItemDB=bundle.getParcelable("List item");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drugi, container, false);
        ImageView imageView=view.findViewById(R.id.image_detail_fragment);
        imageView.setImageResource(listItemDB.getItemImage());
        TextView textView=view.findViewById(R.id.text_detail_fragment);
        textView.setText(listItemDB.getText());
        TextView descriptionView=view.findViewById(R.id.description_detail_fragment);
        descriptionView.setText(listItemDB.getDescription());
        return view;
    }
}