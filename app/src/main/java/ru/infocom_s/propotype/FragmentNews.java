package ru.infocom_s.propotype;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.infocom_s.propotype.data.News;
import ru.infocom_s.propotype.data.NewsLab;

public class FragmentNews extends ListFragment {
    private ArrayList<News> mNews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.title_news);
        mNews = NewsLab.get(getActivity()).getNews();

        setListAdapter(new NewsAdapter(mNews));
    }

    private class NewsAdapter extends ArrayAdapter<News> {

        public NewsAdapter(ArrayList<News> news) {
            super(getActivity(), 0, news);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.news_item, null);
            }
            News news = getItem(position);

            TextView tvNewsTitle = (TextView) convertView.findViewById(R.id.newsTitle);
            TextView tvNewsDescription = (TextView) convertView.findViewById(R.id.newsDescription);
//            ImageView ivNewsImage = (ImageView) convertView.findViewById(R.id.newsImage);
            tvNewsTitle.setText(news.getTitle());
            tvNewsDescription.setText(news.getDescription());
//            ivNewsImage.setImageResource(R.mipmap.ic_launcher);

            return convertView;
        }
    }
}
