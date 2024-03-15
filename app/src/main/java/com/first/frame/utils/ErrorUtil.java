package com.first.frame.utils;

import android.content.Context;
import android.text.TextUtils;

import com.first.frame.R;

public class ErrorUtil {

    public static void handleError(Context context, String code, String error) {
        String errorInfo = context.getString(R.string.error_internet);
        if (!TextUtils.isEmpty(error)) {
            errorInfo = error;
        }
        if (!"The Request is Forbidden".equals(errorInfo)){
            ToastUtils.showTop(context, errorInfo);
        } else {
            //TODO toast what?
        }

//        switch (code) {
//            case "-100":
//                /**
//                 * @see HttpUtils#STATE_FAILED
//                 */
//                break;
//            case "-200":
//                /**
//                 * @see HttpUtils#STATE_JSON_ERROR
//                 */
//                break;
//            case "-300":
//                /**
//                 * @see HttpUtils#STATE_NET_ERROR
//                 */
//                break;
//            case "-1":
//                //代码运行错误
//                break;
//            case "-2":
//                //header-language参数传值错误
//                break;
//            case "-3":
//                //header的access-token参数缺失
//                //TODO 提示登录
//                break;
//            case "-4":
//                //header-sign参数缺失
//                break;
//            case "-5":
//                //请求签名错误
//                break;
//            case "-6":
//                //请求device_type错误
//                break;
//            case "-7":
//                //请求timezone格式错误，格式：+8000或-8000
//                break;
//            case "-8":
//                //来自非法的bundle id
//                break;
//            case "700":
//                //用户行为异常，禁止访问（暂未启用）
//                break;
//            case "701":
//                //用户信息不存在，需要填写用户信息
//                context.startActivity(new Intent(context, UserInfoActivity.class));
//                break;
//            case "702":
//                //用户信息存在，不用重复再次填写基本信息（注册后需要填写的基本信息）
//                break;
//            case "1000":
//                //参数缺失不全
//                break;
//            case "1001":
//                //重复请求（一个请求只能有一次有效期）
//                break;
//            case "1002":
//                //请求的时间与服务器时间相差超过5分钟
//                break;
//            case "1003":
//                //access-token的格式不对
//                break;
//            case "1004":
//                //access-token错误，不存在
//                //TODO 提示用户重新登录
//                ToastUtils.showTop(MyApplication.getContext(), R.string.login_time_out);
//                context.startActivity(ThirdLoginActivity.actionToView(context)
//                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                break;
//            case "1005":
//                //post的rawbody格式不对
//                break;
//            case "1006":
//                //官方注册登录的用户不存在
//                break;
//            case "1007":
//                //官方登录的密码错误
//                break;
//            case "1008":
//                //两次输入的密码不一致
//                break;
//            case "1009":
//                //注册提示用户已经存在
//                break;
//            case "1010":
//                //当天点击发送邮件太过频繁了
//                break;
//            case "1011":
//                //忘记密码重置密码，验证码过期
//                break;
//            case "1012":
//                //忘记密码重置密码，验证码错误
//                break;
//            case "1013":
//                //修改密码，旧密码错误
//                break;
//            case "2000":
//                //操作的记录不存在
//                break;
//            case "2001":
//                //记录的所属者与当前登录用户不一致
//                break;
//            case "2002":
//                //feed发布时，image参数格式不对
//                break;
//            case "2003":
//                //发布feed时，如图片参数传值，则须至少有一个图片地址
//                break;
//            case "2004":
//                //发布feed时，如图片参数传值，不能大于9张图片
//                break;
//            case "2005":
//                //当前feed设置私密，其他用户不可查看
//                break;
//            case "2020":
//                //评论回复内容过长，目前设置200个字符
//                break;
//            case "2021":
//                //回复某位用户不存在
//                break;
//            case "2022":
//                //不能评论自己
//                break;
//            case "2023":
//                //不能删除非自己的评论
//                break;
//            case "3000":
//                //好友请求已发送，等待审核
//                break;
//            case "3001":
//                //双方已经是好友
//                break;
//            case "3002":
//                //好友请求已经过期
//                break;
//            case "3003":
//                //通过、拒绝、删除好友时，操作与用户本身无关的操作或者非法操作
//                break;
//            case "4000":
//                //当前用户创建的群聊数量超过限制
//                break;
//            case "4001":
//                //群组当前人数大于群组最大人数
//                break;
//            case "4002":
//                //加入群组时，当前人数达到上限
//                break;
//            case "4003":
//                //加入群组失败
//                break;
//            case "4004":
//                //非创建者操作
//                break;
//            case "4005":
//                //退出群聊或者踢出成员失败
//                break;
//            default:
//                break;
//        }
    }
}
