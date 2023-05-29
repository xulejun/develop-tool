package com.legend.common.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.Iterator;
import java.util.Map;

import static com.legend.common.util.UnicodeUtil.unicodeDecode;

/**
 * json 数据替换
 *
 * @author legend xu
 * @date 2023/5/29
 */
public class JsonReplaceUtil {
    /**
     * Json 数据转化
     *
     * @param data
     * @return
     */
    public static String jsonFilter(String data) {
        JSONObject oFilter = JSONUtil.parseObj(data);
        oFilter = jsonLoop(oFilter);
        return oFilter.toString();
    }

    /**
     * 递归过滤
     *
     * @param object
     * @return
     */
    private static JSONObject jsonLoop(Object object) {
        JSONObject jsonObject = null;
        if (object instanceof JSONObject) {
            jsonObject = (JSONObject) object;
            Iterator<Map.Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> en = iterator.next();
                String k = en.getKey();
                String v = String.valueOf(en.getValue());
                if (v.contains("\\u")) {
                    v = unicodeDecode(v);
                    jsonObject.put(k, v);
                } else {
                    jsonLoop(v);
                }
            }
        }
        if (object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) object;
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonLoop(jsonArray.get(i));
            }
        }
        return jsonObject;
    }
}
