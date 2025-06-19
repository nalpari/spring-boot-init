package net.devgrr.springbootinit.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PopupPosition {
    CENTER("중앙"),
    TOP_LEFT("상단 좌측"),
    TOP_RIGHT("상단 우측"),
    BOTTOM_LEFT("하단 좌측"),
    BOTTOM_RIGHT("하단 우측"),
    TOP_CENTER("상단 중앙"),
    BOTTOM_CENTER("하단 중앙");

    private final String description;
}