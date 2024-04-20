package cn.lirui.rmi.message;

import java.util.HashMap;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/4/20-17:35:00
 */
public class Message extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;


    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String REMAIN = "remain";

    /**操作成功标记符*/
    public static final String CONSUME_FLAG = "success";

    public Message(boolean flag, double remain, String msg)
    {
        super.put(CONSUME_FLAG, flag);
        super.put(REMAIN, remain);
        super.put(MSG_TAG, msg);
    }

    public Message(boolean flag, double remain)
    {
        super.put(CONSUME_FLAG, flag);
        super.put(REMAIN, remain);
    }


    public static Message success(double remain){
        return new Message(true, remain);
    }

    public static Message success(double remain, String msg){
        return new Message(true, remain, msg);
    }


    public static Message error(double remain, String msg){
        return new Message(false, remain, msg);
    }


    /**
     * 方便链式调用
     *
     * @param key 键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public Message put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
}
