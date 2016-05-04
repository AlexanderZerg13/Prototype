package ru.infocom_s.propotype;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

import ru.infocom_s.propotype.data.News;
import ru.infocom_s.propotype.data.NewsLab;


public class FragmentNewsDescribe extends Fragment {

    private static final String KEY_ID = "key_id";

    private News news;

    public static FragmentNewsDescribe newInstance(UUID id) {

        Bundle args = new Bundle();
        args.putSerializable(KEY_ID, id);

        FragmentNewsDescribe fragment = new FragmentNewsDescribe();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        news = NewsLab.get(
                getActivity()).getNewsByUUID((UUID)getArguments().getSerializable(KEY_ID));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.news_item, container, false);

        TextView newsTitle = (TextView) v.findViewById(R.id.newsTitle);
        TextView newsDescription = (TextView) v.findViewById(R.id.newsDescription);
        ImageView newsImage = (ImageView) v.findViewById(R.id.newsImage);

        newsTitle.setText(news.getTitle());
        newsDescription.setText(news.getDescription());
        newsImage.setImageResource(news.getImageViewResource());

        return v;
    }
}
