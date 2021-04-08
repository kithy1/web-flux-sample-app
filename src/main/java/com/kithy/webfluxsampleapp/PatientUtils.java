package com.kithy.webfluxsampleapp;

import com.kithy.webfluxsampleapp.controller.MathUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PatientUtils {
    public static Long getChecksum(String pesel) {

        // Numer PESEL
        long num = Long.parseLong(pesel);
        // Numer PESEL w postaci tablicy
        long tab[] = new long[11];
        // Tablica z wagami do obliczenia sumy kontrolnej
        long weights[] = { 1, 3, 7, 9, 1, 3, 7, 9, 1, 3, 1 };

        // Tworzenie tablicy z numerem
        for (int i = 0; i < 11; i++) {
            tab[i] = MathUtils.getDigitFromPos(num, i);
        }

        // Obliczanie sumy kontrolnej
        long sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += weights[i] * tab[i];
        }
        sum = 10 - (sum % 10);
        return sum % 10;
    }

    /**
     * Zwracanie roku urodzenia na podstawie numeru PESEL
     *
     * @date 15-06-2013
     *
     * @param pesel
     *            Numer PESEL
     * @return rok urodzenia
     */
    public static Integer getBirthYear(String pesel) {
        long year;
        long month;

        if (!isPeselValid(pesel))
            return null;

        Long num = Long.parseLong(pesel);

        year = 10 * MathUtils.getDigitFromPos(num, 0);
        year += MathUtils.getDigitFromPos(num, 1);
        month = 10 * MathUtils.getDigitFromPos(num, 2);
        month += MathUtils.getDigitFromPos(num, 3);
        // Określanie stulecia na podstawie danych miesiąca
        if (month > 80 && month < 93) {
            year += 1800;
        } else if (month > 0 && month < 13) {
            year += 1900;
        } else if (month > 20 && month < 33) {
            year += 2000;
        } else if (month > 40 && month < 53) {
            year += 2100;
        } else if (month > 60 && month < 73) {
            year += 2200;
        }
        return (int)year;
    }

    /**
     * Pobranie miesiąca urodzenia z numeru pesel
     *
     * @date 15-06-2013
     *
     * @return
     */
    public static Integer getBirthMonth(String pesel) {
        long month;

        if (!isPeselValid(pesel))
            return null;

        Long num = Long.parseLong(pesel);

        month = 10 * MathUtils.getDigitFromPos(num, 2);
        month += MathUtils.getDigitFromPos(num, 3);
        if (month > 80 && month < 93) {
            month -= 80;
        } else if (month > 20 && month < 33) {
            month -= 20;
        } else if (month > 40 && month < 53) {
            month -= 40;
        } else if (month > 60 && month < 73) {
            month -= 60;
        }
        return (int) month;
    }

    /**
     * Pobranie dnia urodzenia na podstawie numeru pesel
     *
     * @date 15-06-2013
     *
     * @return
     */
    public static Integer getBirthDay(String pesel) {
        long day;

        if (!isPeselValid(pesel))
            return null;

        Long num = Long.parseLong(pesel);

        day = 10 * MathUtils.getDigitFromPos(num, 4);
        day += MathUtils.getDigitFromPos(num, 5);

        return (int) day;
    }

    /**
     * Pobranie daty urodzenia na podstawie numeru pesel (data w formacie
     * gotowego stringu)
     *
     * @date 15-06-2013
     *
     * @param pesel
     * @return
     */
    public static String getBirthDayString(String pesel) {

        if (!isPeselValid(pesel))
            return "BRAK DANYCH";

        return (getBirthYear(pesel) + "-"
                + (getBirthMonth(pesel).toString().length() == 1 ? "0" : "")
                +  getBirthMonth(pesel) + "-"
                + (getBirthDay(pesel).toString().length() == 1 ? "0" : "")
                + getBirthDay(pesel));

    }

    public static LocalDate getBirthDate(String pesel) {
        return LocalDate.of(getBirthYear(pesel), getBirthMonth(pesel), getBirthDay(pesel));
    }

    public static boolean isPeselValid(String pesel) {

        if (pesel == null)
            return false;

        if (pesel.length() != 11)
            return false;

        if (!MathUtils.getDigitFromPos(Long.parseLong(pesel), 10).equals(
                getChecksum(pesel)))
            return false;

        return true;
    }

    public static void main(String[] args) {
        System.out.println("65060585139 date: "+ getBirthDayString("02211523158"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        LocalDate date = LocalDate.of(2012,5,1);
        System.out.println(date);
        System.out.println(getBirthDate(""));
    }

}
