//package com.cdyhrj.fastorm.entity.queryablex.from;
//
//import com.cdyhrj.fastorm.entity.queryablex.with.With;
//
//public class WithFrom implements From {
//    public static WithFrom of(With with) {
//        return new WithFrom(with);
//    }
//
//    private final With with;
//
//    public WithFrom(With with) {
//        this.with = with;
//    }
//
//    @Override
//    public String getContent() {
//        return with.getName();
//    }
//}
