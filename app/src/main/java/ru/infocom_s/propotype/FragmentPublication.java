package ru.infocom_s.propotype;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.infocom_s.propotype.data.Publication;
import ru.infocom_s.propotype.data.PublicationLab;

public class FragmentPublication extends ListFragment{

    private ArrayList<Publication> mPublication;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Публикации");
        mPublication = PublicationLab.get(getActivity()).getPublications();
        setListAdapter(new PublicationAdapter(mPublication));
    }

    private class PublicationAdapter extends ArrayAdapter<Publication> {

        public PublicationAdapter(ArrayList<Publication> publications) {
            super(getActivity(), 0, publications);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.publication_item, null);
            }

            TextView pTVId = (TextView) convertView.findViewById(R.id.pTVId);
            TextView pTVAuthor = (TextView) convertView.findViewById(R.id.pTVAuthor);
            TextView pTVName = (TextView) convertView.findViewById(R.id.pTVName);

            Publication publication = getItem(position);

            pTVId.setText(String.valueOf(position + 1));
            pTVAuthor.setText(publication.getAuthor());
            pTVName.setText(publication.getName());

            return convertView;
        }
    }
}
