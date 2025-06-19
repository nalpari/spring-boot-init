package net.devgrr.springbootinit.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PopupType {
    NOTICE("공지사항"),
    EVENT("이벤트"),
    ADVERTISEMENT("광고"),
    ALERT("알림"),
    TERMS("약관"),
    MAINTENANCE("점검");

    private final String description;
}