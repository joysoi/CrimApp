//package com.example.nikola.criminal.database;
//
//import android.animation.TypeConverter;
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//
//import java.util.UUID;
//
//
//@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//public class UUIDConverter extends TypeConverter<Integer, UUID> {
//
//
//    public UUIDConverter(Class<Integer> fromClass, Class<UUID> toClass) {
//        super(fromClass, toClass);
//    }
//
//    @Override
//    public UUID convert(Integer integer) {
//       return integer == null ? null : UUID.fromString(integer);
//    }
//}
