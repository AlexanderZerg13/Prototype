package ru.infocom_s.propotype;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
        View v = inflater.inflate(R.layout.item_news_describe, container, false);

        ImageView newsCardImage = (ImageView) v.findViewById(R.id.news_card_image);
        TextView newsCardText = (TextView) v.findViewById(R.id.news_card_text);
        TextView newsCardDescribe = (TextView) v.findViewById(R.id.news_card_describe);

        newsCardText.setText(news.getTitle());
        newsCardDescribe.setText(news.getDescription());
        newsCardImage.setImageResource(news.getImageViewResource());

        return v;
    }
}
