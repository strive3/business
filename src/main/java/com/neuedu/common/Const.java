package com.neuedu.common;

/**
 * @author 杜晓鹏
 * @create 2019-01-04 19:56
 */
public class Const {
    public static final String CURRENTUSER = "current_user";

    /**
     * 定义一个枚举   0是管理员   1是普通用户
     */
    public enum RoleEnum{
        ROLE_ADMIN(0,"管理员"),
        ROLE_CUSTOMER(1,"普通用户")
        ;

        private int code;
        private String descr;
        private RoleEnum(int code, String descr){
            this.code = code;
            this.descr = descr;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }
    }
}
