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
    public static String[] typeOfClasses = {"Лекция", "Практическое занятие", "Лабораторная работа"};
    public static String[] audiences = {"9-402А", "9-405", "9-310", "9-410"};
    public static String[] teachers = {"Кириллов Сергей Владимирович", "Спичкин Василий Дмитриевич"};
    public static String[] author = {"Кириллов С.В.", "Спичкин В.Д.", "Сидоров И.И."};
    public static String[] publications = {"К проблеме информатизации общества в XXI веке",
            "Математические алгоритмы на JavaScript",
            "Анализ угроз информации систем электронного документооборота",
            "Хранилища и витрины данных в системах поддержки принятия решений",
            "Поиск инноваций в сфере технологий",
            "Быстродействующий алгоритм фрактального сжатия изображений",
            "Актуальность современных систем удаленного мониторинга вычислительных ресурсов",
            "Система вечного и надежного хранения персональной информации и ДНК",
            "Подготовка специалистов в области компьютерных наук, техники и технологий",
            "Создание интегрированной информационной среды на предприятии"};

    public static String[] status = {"Принято", "Отклонено", "Заявление не подано"};
    public static String[] directions = {"Биология", "История", "Экономика", "Химия",
            "Информационная безопасность автоматизированных систем",
            "Информационные системы и технологии",
            "Математика и компьютерные науки",
            "Прикладная математика и информатика",
            "Юриспруденция"};
    public static String[] subjects = {"Русский язык", "Математика", "Биология", "Химия", "Физика"};

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
