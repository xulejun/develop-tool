package com.legend.common.util;

import com.google.common.collect.Lists;
import com.legend.common.entity.User;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author legend xu
 * @date 2023/10/8
 */
public class PageUtil {
    /**
     * 手动分页
     *
     * @param originalList 数据集合
     * @param groupSize 每页多少个元素
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> groupListBySize(List<T> originalList, int groupSize) {
        List<List<T>> groupedLists = new ArrayList<>();
        int total = originalList.size();
        int totalPages = (int) Math.ceil((double) total / groupSize);

        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            int startIndex = (currentPage - 1) * groupSize;
            int endIndex = Math.min(startIndex + groupSize, total);
            List<T> groupList = originalList.subList(startIndex, endIndex);
            groupedLists.add(groupList);
        }
        return groupedLists;
    }

    public static void main(String[] args) {
        ArrayList<User> list = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            User user = User.builder().id(i).name("zhangsan" + i).build();
            list.add(user);
        }
        List<List<User>> listBySize = groupListBySize(list, 10);
        List<User> users = listBySize.get(0);
        System.out.println(users.size());
    }
}
