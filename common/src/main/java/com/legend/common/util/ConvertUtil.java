package com.legend.common.util;

import com.beust.jcommander.internal.Lists;
import com.legend.common.entity.User;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 转换工具
 *
 * @author legend xu
 * @date 2021/12/2
 */
public class ConvertUtil {

    /**
     * .properties 配置文件转 hashMap
     *
     * @throws IOException
     */
    public static void propertiesConvertMap() throws IOException {
        String config = "data.input.cancelPolicy=入住前可免费取消,入住当天12:00前可免费取消,入住当天18:00前可免费取消,入住当天20:00前可免费取消,入住当天23:59前可免费取消,15分钟内可免费取消,不可取消,有条件取消,限时取消\n" +
                "data.input.payMethod=在线付,到店付\n" +
                "data.input.bedType=缺失,大床,双床,双人床,特大床,单人床,多床,上下铺,圆床,其他床,双床/大床,单人床/大床\n" +
                "data.input.window=缺失,无窗,有窗,落地窗\n" +
                "data.input.promotion.label=10亿补贴,新客专享红包,限时特惠,酒店天天神券,高星首单红包,高端酒店红包,今夜特价,惊喜津贴红包,叠加红包,出游特惠8折,牛气冲天红包,天天特价,随享红包,一口价208元,一口价328元,超值一口价,超值返现,特惠立减,限时5折,限时抢62折,6折放价红包,7折放价红包,8折放价红包,本店新客6折,本店新客6.1折,本店新客6.2折,本店新客6.3折,本店新客6.4折,本店新客6.5折,本店新客6.6折,本店新客6.7折,本店新客6.8折,本店新客6.9折,本店新客7折,本店新客7.1折,本店新客7.2折,本店新客7.3折,本店新客7.4折,本店新客7.5折,本店新客7.6折,本店新客7.6折,本店新客7.7折,本店新客7.8折,本店新客7.9折,本店新客8折,本店新客8.1折,本店新客8.2折,本店新客8.3折,本店新客8.4折,本店新客8.5折,本店新客8.6折,本店新客8.7折,本店新客8.8折,本店新客8.9折,本店新客9折,酒店随机红包,白银会员9.8折,黄金会员9.5折,铂金会员9折,铂金会员8.5折,钻石会员8.5折\n" +
                "data,input.source.channel=美团APP,美团微信APP小程序,美团微信PC小程序,大众点评APP,大众点评微信APP小程序,大众点评微信PC小程序";
        Properties properties = new Properties();
        properties.load(new StringReader(config));
        Map<Object, Object> hashMap = new HashMap<>(properties);
//        HashMap<String, String> hashMap = new HashMap<>((Map) properties);
        hashMap.keySet().forEach(System.out::println);
    }

    /**
     * map 转为 bean 对象
     */
    public static <T> T mapToObj(Map source, Class<T> target) throws Exception {
        Field[] fields = target.getDeclaredFields();
        T o = target.newInstance();
        for (Field field : fields) {
            Object val;
            if ((val = source.get(field.getName().toLowerCase())) != null) {
                field.setAccessible(true);
                field.set(o, val);
            }
        }
        return o;
    }

    /**
     * 数组转集合
     */
    public static void arrToList() {
        //注意这个List不是Collections包内的List,而是util包里面的List接口
        String[] arr = {"fgx", "lzy"};
        ArrayList<String> list = new ArrayList<>(arr.length);
        Collections.addAll(list, arr);
        System.out.println(list.toString());
    }

    /**
     * 集合转数组
     */
    public static void listToArr() {
        //注意这个List不是Collections包内的List,而是util包里面的List接口
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        String[] array = list.toArray(new String[0]);
        System.out.println(Arrays.toString(array));
    }

    /**
     * list转map
     */
    public static void listToMap() {
        List<User> list = Lists.newArrayList();
        User xlj1 = User.builder().id(1).name("xlj1").build();
        User xlj2 = User.builder().id(2).name("xlj2").build();
        User xlj3 = User.builder().id(3).name("xlj3").build();
        list.add(xlj1);
        list.add(xlj2);
        list.add(xlj3);
        Map<Integer, User> map = list.stream().collect(Collectors.toMap(User::getId, t -> t));
        System.out.println(map);
    }

    // 时间戳转LocalDateTime
    public static void timestampToLocalDateTime(long timestamp) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    // LocalDateTime转时间戳
    public static void localDateTimeToTimestamp() {
        long timestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }


    public static void main(String[] args) {
//        arrToList();
        listToMap();
    }
}
