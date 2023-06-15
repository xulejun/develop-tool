import cn.hutool.core.codec.Base64;
//import com.alibaba.fastjson2.JSONArray;
import cn.hutool.json.JSONArray;
//import com.alibaba.fastjson2.JSONObject;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.legend.common.compress.CompressUtil;
import com.legend.common.entity.User;
import com.legend.common.util.UnicodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.platform.commons.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.legend.common.util.JsonReplaceUtil.jsonFilter;


/**
 * @author legend xu
 * @date 2023/4/21
 */
@Slf4j
public class CommonTests {

    public static void main(String[] args) throws Exception {
    }

}
