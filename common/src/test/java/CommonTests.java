import cn.hutool.json.JSONArray;

import java.util.ArrayList;

/**
 * @author legend xu
 * @date 2023/4/21
 */

public class CommonTests {

    public static void main(String[] args) {
        String a = "[{'a':1},{'b':2}]";
        JSONArray objects = new JSONArray(a);
        System.out.println(objects);

    }
}
