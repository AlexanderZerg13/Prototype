package ru.infocom_s.propotype.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

import ru.infocom_s.propotype.R;

public class NewsLab {
    private ArrayList<News> mNews;

    private static NewsLab sNewsLab;
    private Context mAppContext;

    private NewsLab(Context appContext) {
        mAppContext = appContext;
        mNews = new ArrayList<News>();
        mNews.add(new News("Конкурс на получение стипендии Правительства РФ", "Студентов и аспирантов очной формы обучения  приглашают принять участие в конкурсе на получение стипендии Правительства Российской Федерации по приоритетным направлениям модернизации и технологического развития российской экономики.", R.drawable.n1));
        mNews.add(new News("Открыт тестовый доступ к Электронно-библиотечной системе ibooks", "Электронная библиотечная система ibooks создана ведущими российскими издательствами учебной, научной и деловой литературы «Питер» и «БХВ-Петербург» в сотрудничестве с Ассоциацией региональных библиотечных консорциумов (АРБИКОН).", R.drawable.n2));
        mNews.add(new News("Открытый форум научных идей", "На базе Научной библиотеки прошел первый Открытый форум научных идей. Его цель – показать, что даже стандартные научные проблемы можно представить нестандартно и креативно. Организаторами мероприятия выступили: Институт математики и естественных наук, Совет молодых ученых и специалистов ИМЕН и малое инновационное предприятие ООО «Центр стратегического территориального проектирования». Участниками стали студенты, аспиранты и молодые ученые, кадеты Ставропольского президентского кадетского училища, специалисты ИТ-компании Ставропольского края.", R.drawable.n3));

    }

    public static NewsLab get(Context c) {
        if (sNewsLab == null) {
            sNewsLab = new NewsLab(c.getApplicationContext());
        }
        return sNewsLab;
    }

    public ArrayList<News> getNews() {
        return mNews;
    }

    public News getNewsByUUID(UUID id) {
        for (News n : mNews) {
            if (n.getId().equals(id)) {
                return n;
            }
        }
        return null;
    }
}
