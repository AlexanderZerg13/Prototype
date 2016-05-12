package ru.infocom_s.propotype;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import ru.infocom_s.propotype.data.StaticData;

public class FragmentCampaign extends Fragment{
    private int mStatus;
    private ArrayList<String> mDirectionsList;
    private ArrayList<EGE> mEge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Приемная кампания");

        mDirectionsList = new ArrayList<>();
        mEge = new ArrayList<>();

        Random random = new Random();
        mStatus = random.nextInt(3);

        if (mStatus != 0) {
            return;
        }
        int number = 4 + random.nextInt(3);
        HashSet<Integer> hs = new HashSet<>();
        while (hs.size() < number) {
            hs.add(random.nextInt(StaticData.directions.length));
        }
        Iterator<Integer> iterator = hs.iterator();
        while (iterator.hasNext()) {
            mDirectionsList.add(StaticData.directions[((int) iterator.next())]);
        }

        mEge.add(new EGE(StaticData.subjects[0], 50 + random.nextInt(50)));
        mEge.add(new EGE(StaticData.subjects[1], 50 + random.nextInt(50)));
        mEge.add(new EGE(StaticData.subjects[2 + random.nextInt(StaticData.subjects.length - 2)], 50 + random.nextInt(50)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_campaign, container, false);

        TextView cTVStatus = (TextView) v.findViewById(R.id.cTVStatus);
        TextView cTVDirections = (TextView) v.findViewById(R.id.cTVDirections);
        TextView cTVResult = (TextView) v.findViewById(R.id.cTVResult);
        LinearLayout cLLDirections = (LinearLayout) v.findViewById(R.id.cLLDirections);
        LinearLayout cLLEge = (LinearLayout) v.findViewById(R.id.cLLEge);

        SpannableString spannableString = new SpannableString(StaticData.status[mStatus]);
        int color;
        switch (mStatus){
            case 0:
                color = Color.GREEN;
                break;
            default:
                color = Color.RED;
                break;
        }
        spannableString.setSpan(new ForegroundColorSpan(color), 0, spannableString.length(), 0);
        cTVStatus.setText(spannableString, TextView.BufferType.SPANNABLE);

        if (mStatus == 0) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMarginStart((int) getResources().getDimension(R.dimen.activity_horizontal_margin));
            for (int i = 0; i < mDirectionsList.size(); i++) {
                TextView textView = new TextView(getActivity());
                textView.setText(mDirectionsList.get(i));

                textView.setTextAppearance(getActivity(), R.style.normalText);

                cLLDirections.addView(textView, layoutParams);
            }

            for (int i = 0; i < mEge.size(); i++) {
                TextView textView = new TextView(getActivity());
                EGE ege = mEge.get(i);
                textView.setText(ege.getSubject() + " (" + ege.getMark() + ")");

                textView.setTextAppearance(getActivity(), R.style.normalText);

                cLLEge.addView(textView, layoutParams);
            }
        } else if (mStatus == 1) {
            cTVDirections.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) cTVResult.getLayoutParams();
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            SpannableString sp = new SpannableString("Отсутствуют серия и номер паспорта");
            sp.setSpan(new ForegroundColorSpan(Color.RED), 0, sp.length(), 0);
            cTVResult.setText(sp);

            ((LinearLayout.LayoutParams)cTVStatus.getLayoutParams()).setMargins(0, 0, 0, 0);

        } else {
            cTVDirections.setVisibility(View.GONE);
            cTVResult.setVisibility(View.GONE);
        }


        return v;
    }

    private class EGE {
        private String subject;
        private int mark;

        public EGE(String subject, int mark) {
            this.subject = subject;
            this.mark = mark;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }
    }
}
