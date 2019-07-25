package com.linkedaim.admin.system.constants;

/**
 * @author zhaoyangyang
 * @title 业务枚举
 * @date 2019-07-18
 */
public class BuinessEnum {

    /**
     * 状态
     */
    public enum statusEnum {
        DISABLE("0", "锁定"),
        ENABLE("1", "启用"),
        ;
        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        statusEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 账号状态
     */
    public enum userLockStatusEnum {
        LOCK("0", "锁定"),
        ENABLE("1", "启用"),
        ;
        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        userLockStatusEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }


}
