package ru.infocom_s.propotype.data;

import java.util.Calendar;
import java.util.Date;

public abstract class StaticData {
    public static String[] classes = {"Защита информации в банковской системе и в электронном бизнесе" ,
            "Компьютерная и инженерная графика компьютерных систем",
            "Защита в операционных системах",
            "Физическая культура",
            "Компьютерные сети",
            "Криптографические методы защиты информации",
            "Защита программ и данных"};
    public static String[] audiences = {"9-402А", "9-405", "9-310", "9-410"};
    public static String[] teachers = {"Кирилов Сергей Владимирович", "Спичкин Василий Дмитриевич"};
    public static Date[] date;
    static {
        date = new Date[10];
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 10; i++) {
            calendar.set(2016, 5, 1 + (i * 2));
            date[i] = calendar.getTime();
        }
    }
}
