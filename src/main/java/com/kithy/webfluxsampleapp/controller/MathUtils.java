package com.kithy.webfluxsampleapp.controller;

public class MathUtils {

    /**
     * Pobranie cyfry z konkretnej pozycji danej liczby
     *
     * @date 15-06-2013
     *
     * @param number
     * @param pos
     * @return
     */
    public static Long getDigitFromPos(Long number, Integer pos) {
        Long result = Long.parseLong(number.toString().substring(pos, pos + 1));
        return result;
    }

    /**
     * Pobranie cyfry z konkretnej pozycji danej liczby
     *
     * @date 15-06-2013
     *
     * @param number
     * @param pos
     * @return
     */
    public static Integer getDigitFromPos(Integer number, Integer pos) {
        Integer result = Integer.parseInt(number.toString().substring(pos,
                pos + 1));
        return result;
    }

}