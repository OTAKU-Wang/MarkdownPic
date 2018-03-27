package com.choice.cloud.olp.takeout.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @classDesc: 功能描述:
 * @Author: wangmaoshuai
 * @createTime: Created in 下午1:28 18-2-28
 * @version: v1.0
 * @copyright: 北京辰森
 * @email: wms@choicesoft.com.cn
 */

public class CommonSignComputer  {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonSignComputer.class);

    public static String createSign(String requestURI, Map<String, String> params,String secret) {
        String result;
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<>(params.entrySet());
            infoIds.sort(Comparator.comparing(o -> (o.getKey())));
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (!StringUtils.isEmpty(item.getKey())) {
                    if (!item.getKey().equals("sign")) {
                        String key = item.getKey();
                        String val = item.getValue();
                        if (!val.equals("")) {
                            if (JSONUtil.isJsonArray(val)) {
                                val = JSONUtil.sortJsonArray(JSON.parseArray(val)).toJSONString();
                            } else if (JSONUtil.isJsonObject(val)) {
                                val = JSONUtil.sortJsonObject(JSON.parseObject(val)).toJSONString();
                            }
                            sb.append(key).append("=").append(val).append("&");
                        }
                    }
                }
            }
            sb.append("secret=").append(secret);
            result = requestURI+"?"+sb.toString();
            LOGGER.info("生成的url {}", result);
            result = DigestUtils.md5Hex(result);
            LOGGER.debug("计算的sign {}", result);
        } catch (Exception e) {
            LOGGER.error("CommonSignComputer 获取异常 ,errMsg = {}, stack info = {}",e.getMessage(),e);
            e.printStackTrace();
            return null;
        }
        return result;
    }

}
