package com.novel.core.enums;

/**
 * @author 昴星
 * @date 2023-09-02 22:12
 * @explain
 */

public enum ErrorStatusEnums {

    RES_OK("200","成功执行"),
    RES_SYSTEM_ERROR("201","系统异常，请重新尝试"),

    RES_USER_INFO_SHORT("202","注册信息未填完毕,请重新尝试"),

    RES_VERIFY_CODE_NOT_MATCH("203","验证码不匹配"),

    RES_REGISTER_USERANAME_EXIT("204","注册用户名已存在"),

    RES_TOKEN_EMPTY("205","未登录，请返回登录界面"),

    RES_TOKEN_ERR("206","验证信息异常,请重新登陆"),

    RES_LOGIN_NOT_MATCH("207","账号密码不匹配，请重新登陆"),
    RES_ES_ERR("208","系统繁忙，请稍后尝试"),

    RES_DATABASE_ERROR("209","服务器故障，请稍后重试"),

    RES_NOT_EXIT_CHAPTER("300","不存在该章节，请重新操作"),

    RES_BOOK_RELEASE_FAIL("301","小说发布异常，请尝试"),

    RES_BOOK_CHAPTER_RELEASE_FAILE("302","小说章节发布异常，请重新尝试"),
    RES_BOOKS_GET_FAILED("303","查询书籍列表失败"),
    RES_BOOKS_CHAPTER_GET_FAILED("304","查询章节列表失败"),

    RES_USER_UID_FAILED("305","用户信息丢失，请尝试"),

    RES_FILE_UPLOAD_FAILED("306","文件数据有缺损，请重新尝试"),
    RES_FILE_UPLOAD_NAME_BLANK("307","文件名为空，请再次上传"),
    RES_FILE_UPLOAD_FAILD("308","文件名为空，请再次上传"),
    RES_PAY_COUPON_NUM_LIMIT("309","折扣卷数量以抢完"),

    RES_PAY_COUPON_USER_COUPON_AFTER_VALID("310","使用的卷超过有效期"),

    RES_PAY_COUPON_USER_COUPON_HAD_USER("311","卷已经使用过了"),

    RES_PAY_COUPON_CHAPTER_NOT_ALLOW_REDUCE("312","该章节不允许使用优惠卷"),

    RES_PAY_COUPON_SMALL_THAN_MIN_COST_OF_COIN("313","使用的币未达到最小卷使用额度"),
    RES_ERR_PARAM_IMP_LACK("314","参数缺失");


    public String code;
    public String message;

    ErrorStatusEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
