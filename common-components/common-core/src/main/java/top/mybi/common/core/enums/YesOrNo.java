package top.mybi.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 是否
 *
 * @Author mustang
 * @version 1.0
 * @date 2022年6月24日11:18:14
 */
@Getter
@AllArgsConstructor
public enum YesOrNo {

    /**
     * 是
     */
    YES(1,"是"),

    /**
     * 否
     */
    NO(2,"否"),

    ;

    private Integer value;

    private String name;

    /**
     * 获取名称
     * @param value
     * @return
     */
    public static String getName(Integer value) {
        for (YesOrNo yesOrNo : YesOrNo.values()) {
            if (Objects.equals(value,yesOrNo.value)) {
                return yesOrNo.name;
            }
        }
        return "";
    }
}
