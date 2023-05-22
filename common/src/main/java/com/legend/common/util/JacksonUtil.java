package com.legend.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author legend xu
 * @date 2023/5/22
 */
public class JacksonUtil {
    public static void main(String[] args) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("userName", "username");
        objectNode.put("credential", "password");
    }
}
