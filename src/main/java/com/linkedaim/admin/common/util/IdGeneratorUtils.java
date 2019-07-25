package com.linkedaim.admin.common.util;

import java.util.Random;

/**
 * 唯一id生成器
 * @author xiongwanqiang
 * @date 2017-08-23 11:10
 */
public class IdGeneratorUtils {
    private static Random random = new Random();
    private static final Sequence sequence = new Sequence();

    /**
     * 生成平台流水号, 0-99+P+19位的id
     * @return
     */
    public static String getPlatformId() {
        return new StringBuilder().append(getRandow(10, 100)).append("P").append(getId()).toString();
    }

    /**
     * 生成模块流水号，0-99+M+19位的id
     * @return
     */
    public static String getModuleId() {
        return new StringBuilder().append(getRandow(10, 100)).append("M").append(getId()).toString();
    }

    /**
     * 生成字符型流水号，共19位长度
     * @return
     */
    public static String getId() {
        return String.valueOf(getIdLong());
    }

    /**
     * 生成Long型序列号
     * @return
     */
    public static long getIdLong() {
        return sequence.nextId();
    }

    private static int getRandow(int start, int end) {
        return random.nextInt(end - start) + start;
    }


}
