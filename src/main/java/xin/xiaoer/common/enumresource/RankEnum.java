package xin.xiaoer.common.enumresource;

import xin.xiaoer.common.utils.EnumMessage;

public enum RankEnum implements EnumMessage {

    ONE("1","1"),
    TOW("2","2"),
    THREE("3","3"),
    FOUR("4","4"),
    FIVE("5","5");
    private final String code;
    private final String value;
    private RankEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
    @Override
    public String getCode() { return code;}
    @Override
    public String getValue() { return value; }
}
