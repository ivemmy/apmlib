package com.apm.service.collect;

import com.apm.model.FragmentInfo;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Fragment信息收集器
 *
 * @author 王俊超
 */
public class FragmentCollector {
    /**
     * 保存当前的Fragment
     */
    private static FragmentInfo current = null;
    /**
     * 存储FragmentInfo信息的队列
     */
    private final static BlockingDeque<FragmentInfo> //
            DATA = new LinkedBlockingDeque<FragmentInfo>(64);

    /**
     * 向收集队列中添加数据
     *
     * @param fi
     */
    public static synchronized void put(FragmentInfo fi) {
        //System.out.println("Fragment.....put=="+fi);
        DATA.addLast(fi);
/*		if (current == null || !current.getName().equals(fi.getName())) {
			System.out.println("fragment put if......");
			current = fi;
			System.out.println("fragment put=="+fi.toString());
			DATA.addLast(fi);
		} else {
			if (fi.getBeforeCreate() < current.getBeforeCreate()) {
				current.setBeforeCreate(fi.getBeforeCreate());
			}

			if (fi.getAfterCreate() > current.getAfterCreate()) {
				current.setAfterCreate(fi.getAfterCreate());
			}
		}*/
    }

    /**
     * 获取一个FragmentInfo对象
     *
     * @return FragmentInfo对象
     */
    public static synchronized FragmentInfo get() {
        if (!DATA.isEmpty()) {
            return DATA.removeFirst();
        }
        return null;
    }

    /**
     * 获取所有的FragmentInfo对象
     *
     * @return 所有的FragmentInfo对象
     */
    public static synchronized Deque<FragmentInfo> getAll() {
        Deque<FragmentInfo> aiList = new LinkedList<FragmentInfo>();

        while (!DATA.isEmpty()) {
            aiList.add(DATA.removeFirst());
        }

        return aiList;
    }

    /**
     * 清空数据
     */
    public static synchronized void clear() {
        DATA.clear();
    }

    public static void setCurrent(FragmentInfo currentinfo) {
        current = currentinfo;
    }

    public static FragmentInfo getCurrent() {
        return current;
    }
}
