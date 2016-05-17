package ru.infocom_s.propotype;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

import ru.infocom_s.propotype.data.News;
import ru.infocom_s.propotype.data.NewsLab;

public class FragmentCardNews extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Новости");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        NewsAdapter adapter = new NewsAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView newsCardImage;
        TextView newsCardText;
        TextView newsCardDescribe;
        UUID id;

        public NewsViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_news, parent, false));

            newsCardImage = (ImageView) itemView.findViewById(R.id.news_card_image);
            newsCardText = (TextView) itemView.findViewById(R.id.news_card_text);
            newsCardDescribe = (TextView) itemView.findViewById(R.id.news_card_describe);


            itemView.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();

                    Fragment fragment = FragmentNewsDescribe.newInstance(id);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fragment.setSharedElementEnterTransition(new DetailsTransition());
                        fragment.setEnterTransition(new Fade());
                        setExitTransition(new Fade());
                        fragment.setSharedElementReturnTransition(new DetailsTransition());
                    }

                    fm.beginTransaction()
                            .addSharedElement(newsCardImage, "image")
                            .replace(R.id.fragmentContainer, fragment)
                            .addToBackStack("")
                            .commit();
                }
            });
        }
    }

    public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
        private Context mContext;
        private ArrayList<News> mListNews;

        public NewsAdapter(Context context) {
            mContext = context;
            mListNews = NewsLab.get(mContext).getNews();
        }

        @Override
        public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NewsViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onBindViewHolder(NewsViewHolder holder, int position) {
            News n = mListNews.get(position);
            holder.newsCardText.setText(n.getTitle());
            holder.newsCardDescribe.setText(n.getDescription());
            holder.newsCardImage.setImageResource(n.getImageViewResource());
            holder.id = n.getId();
            holder.newsCardImage.setTransitionName(position + "_position");
        }

        @Override
        public int getItemCount() {
            return mListNews.size();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public class DetailsTransition extends TransitionSet {
        public DetailsTransition() {
            setOrdering(ORDERING_TOGETHER);
            addTransition(new ChangeBounds()).
                    addTransition(new ChangeTransform()).
                    addTransition(new ChangeImageTransform());
        }
    }
}
