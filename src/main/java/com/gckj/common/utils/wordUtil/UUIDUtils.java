package com.gckj.common.utils.wordUtil;

import java.util.UUID;

/**
 * @Description：uuid
 * @author：ldc
 * date：2020-06-23
 */
public class UUIDUtils {

    /**
     * 创建一个32位的UUID
     * @return
     */
    public static String get32UUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
