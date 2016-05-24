package ru.infocom_s.propotype;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.infocom_s.propotype.data.Publication;
import ru.infocom_s.propotype.data.PublicationLab;

public class FragmentPublication extends Fragment{

    private ArrayList<Publication> mPublication;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Публикации");
        mPublication = PublicationLab.get(getActivity()).getPublications();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view, container, false);

        PublicationAdapter publicationAdapter = new PublicationAdapter();

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(publicationAdapter);

        return v;
    }

    private class PublicationViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        TextView pTVId;
        TextView pTVAuthor;
        TextView pTVName;

        public PublicationViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.publication_item, parent, false));

            itemView.setOnClickListener(this);

            pTVId = (TextView) itemView.findViewById(R.id.pTVId);
            pTVAuthor = (TextView) itemView.findViewById(R.id.pTVAuthor);
            pTVName = (TextView) itemView.findViewById(R.id.pTVName);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class PublicationAdapter extends RecyclerView.Adapter<PublicationViewHolder> {

        @Override
        public PublicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PublicationViewHolder(LayoutInflater.from(getActivity()), parent);
        }

        @Override
        public void onBindViewHolder(PublicationViewHolder holder, int position) {
            Publication publication = mPublication.get(position);

            holder.pTVId.setText(String.valueOf(position + 1));
            holder.pTVAuthor.setText(publication.getAuthor());
            holder.pTVName.setText(publication.getName());
        }

        @Override
        public int getItemCount() {
            return mPublication.size();
        }
    }
}
