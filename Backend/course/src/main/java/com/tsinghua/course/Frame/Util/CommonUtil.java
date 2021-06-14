package com.tsinghua.course.Frame.Util;

import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import org.apache.commons.lang3.RandomStringUtils;

public class CommonUtil {
    //고유 아이디 만들기 (yyyyMMddHHmmssSSS_랜덤문자6개)
    public static String getUniqueId() {
        String uniqueId = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Calendar dateTime = Calendar.getInstance();
        uniqueId = sdf.format(dateTime.getTime());

        //yyyymmddhh24missSSS_랜덤문자6개
        uniqueId = uniqueId+"_"+RandomStringUtils.randomAlphanumeric(6);

        return uniqueId;
    }
}
