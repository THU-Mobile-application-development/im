package com.tsinghua.course.Base.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

@Document

public class TimeFormat {



//    public void setTime(boolean user_read) {
//        this.user_read = user_read;
//    }

    public static String getTime() {
        SimpleDateFormat sDate2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return sDate2.format(new Date());
    }


}
