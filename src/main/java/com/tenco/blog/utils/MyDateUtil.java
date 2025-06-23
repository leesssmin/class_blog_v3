package com.tenco.blog.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

/**
 * 날짜/ 시간 관련 유틸리티 클래스
 * static 메서드로 구성해서 객체 생성 없이 바로 사용 가능하게 설계
 */
public class MyDateUtil {

    public static String timestampFormat(Timestamp time){
        // Boaed 엔티티에 선언된 Timestamp를 Date 객체로 반환
        // getTime() 매서드를 호출해서 밀리초 단위로 시간을 받아 --> Date 객체 생성
        Date currentDate = new Date(time.getTime());

        // 아파치 Commons 라이브러리
        return DateFormatUtils.format(currentDate, "yyyy-MM-dd HH:mm");
    }
}
