package ru.infocom_s.propotype.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

public class PublicationLab {
    private static String sName = "Иванов И.И.";

    private static PublicationLab sPublicationLab;
    private ArrayList<Publication> mPublications;
    private Context mAppContext;

    private PublicationLab(Context appContext) {
        mAppContext = appContext;
        mPublications = new ArrayList<>();

        Random random = new Random();
        int numberPublications = random.nextInt(4) + 3;
        for(int i = 0; i < numberPublications; i++) {
            StringBuilder sb = new StringBuilder(sName);
            if (random.nextBoolean()) {
                sb.append(" ");
                sb.append(StaticData.author[random.nextInt(StaticData.author.length)]);
            }
            mPublications.add(new Publication(
                    sb.toString(),
                    StaticData.publications[random.nextInt(StaticData.publications.length)]));
        }
    }

    public static PublicationLab get(Context c) {
        if (sPublicationLab == null) {
            sPublicationLab = new PublicationLab(c);
        }
        return sPublicationLab;
    }

    public ArrayList<Publication> getPublications() {
        return mPublications;
    }
}
