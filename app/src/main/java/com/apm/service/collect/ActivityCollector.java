package com.apm.service.collect;

import com.apm.model.ActivityInfo;
import com.apm.util.Config;
import com.apm.util.Converter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Activity信息收集器
 *
 * @author 王俊超
 */
public class ActivityCollector {

    /**
     * 开始收集的时间，当类中有静态的方法，或者属性被使用时会初始化这个字段
     */
    private volatile static long START = System.currentTimeMillis();
    private volatile static long lastResumeTime;

    /**
     * 标记统计的Activity数目是否超过限定
     */
    private static volatile boolean isNumberOut = false;
    /**
     * 标记统计的Activity时间是否超时
     */
    private static volatile boolean isTimeOut = false;

    /**
     * Activity收集计数器
     */
    private volatile static int counter = 0;

    /**
     * 存储ActivityInfo信息的队队
     */
    private final static BlockingDeque<ActivityInfo> //
            ACTIVITIES = new LinkedBlockingDeque<ActivityInfo>(64);

    /**
     * Activity跳转路径队列
     */
    private final static BlockingDeque<JSONObject>//
            ROUTES = new LinkedBlockingDeque<JSONObject>(64);

    /**
     * 保存前显示的activity信息
     */
    private static ActivityInfo current = null;

    /**
     * 最近onCreate的Activity名字
     */
    private static String nowActivityName;

    // 防止new方式进行初始化
    private ActivityCollector() {
        // 不能初始化
    }

    /**
     * 重置收集器
     */
    public static void reset() {
        isNumberOut = false;
        isTimeOut = false;
        START = System.currentTimeMillis();
        ACTIVITIES.clear();
        ROUTES.clear();
    }

    /**
     * 获取当前的Activity信息
     *
     * @return 当前的Activity信息
     */
    public static ActivityInfo getCurrentActivityInfo() {
        return current;
    }

    /**
     * 设置Activity信息
     *
     * @param ai 新的Activity信息
     */
    public static void setActivityInfo(ActivityInfo ai) {

        if (current == null || !current.getName().equals(ai.getName())) {
            current = ai;
            // 在执行流中记录当前的Activity简要信息
            ROUTES.addLast(Converter.getSimpleActivity(current));
        } else {
            // 如果有相同名字的Activity就取较小的创建时间
            if (ai.getBeforeCreate() <= current.getBeforeCreate()) {
                current.setBeforeCreate(ai.getBeforeCreate());

                // 如果完成时间比已经记录的还后，就记录最后的创建时间
                if (ai.getAfterCreate() >= current.getAfterCreate()) {
                    current.setAfterCreate(ai.getAfterCreate());
                }
            }
            // 如果两个连接相同的Activity后一个的创建时间在前一个结束之后，说明这是一个新的Activity
            else if (current.getAfterPause() != null //
                    && current.getAfterPause().longValue() <= ai.getBeforeCreate().longValue()) {
                // 两个activity中间的间隔
                long du = ai.getBeforeCreate() - current.getAfterPause();

                // 从尾向前遍历
                Iterator<ActivityInfo> iterator = ACTIVITIES.descendingIterator();
                ActivityInfo tmp;
                while (iterator.hasNext()) {
                    tmp = iterator.next();
                    // 找第一个与ai不同名的ActivityInfo对象（设为tmp），也就是tmp之后就跳转到了
                    // 与ai同名的Activity上，面tmp调用了onRestart方法，这个没有被捕获，所以就没有记录tmp，
                    // 本来要记录了，这里采用了迂回的方法
                    if (!tmp.equals(ai)) {
                        // du这段时时间其实在使用名为tmp的activity
                        ROUTES.addLast(Converter.getSimpleActivity(tmp.getName(), tmp.getAfterPause()));
                        counter++;
                        tmp.setAfterPause(tmp.getAfterPause() + du);
                        break;
                    }
                }

                // 在执行流中记录当前的Activity简要信息
                ROUTES.addLast(Converter.getSimpleActivity(ai));
                current = ai;
            }
        }
    }

    /**
     * 从队列头部取队首元素，并且删除
     *
     * @return
     */
    public static synchronized ActivityInfo take() {
        try {
            return ACTIVITIES.takeFirst(); // 从队首取元素
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("获取ActivityInfo对象失败");
        }
    }

    /**
     * 获取队首元素，并且在队列中删除此元素，并且队首元素不是最后一个元素
     *
     * @return
     */
    public static synchronized ActivityInfo takeNonLast() {
        try {
            // 有4条以上的数据时才发送
            if (ACTIVITIES.size() > 3) {
                return ACTIVITIES.takeFirst(); // 从队首取元素
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("获取ActivityInfo对象失败");
        }

        return null;
    }

    /**
     * 在队列的末尾放入元素
     *
     * @param ai 待放入的元素
     */
    public static synchronized void put(ActivityInfo ai) {
        //ActivityInfo tmp = ACTIVITIES.peekLast(); // 检查队尾元素

        // 如果没有对象，或者队首元素与当前的activity不同名就添加到队列中,
        // 或者两个相同的Activity后一个的创建时间在前一个的结束之后
	/*	if (tmp == null || !tmp.getName().equalsIgnoreCase(ai.getName())
				|| tmp.getAfterPause().longValue() <= ai.getBeforeCreate().longValue()) {*/
        // 记录多添加了一个
        counter++;

        ACTIVITIES.addLast(Converter.copy(ai)); // 添加到队尾
//			ApmLogger.info(ai); // 记录日志
        //	}
    }

    /**
     * 获取执行流中的所有对象
     *
     * @return 执行流的对象队列
     */
    public static synchronized List<JSONObject> getRoute() {
        List<JSONObject> saiList = new ArrayList<JSONObject>();
        while (!ROUTES.isEmpty()) {
            saiList.add(ROUTES.removeFirst());
        }
        return saiList;
    }

    /**
     * 获取Route大小
     *
     * @return Route大小
     */
    public static synchronized int getRouteSize() {
        return ROUTES.size();
    }

    /**
     * 获取所有的ActivtiyInfo对象
     *
     * @return 所有的ActivtiyInfo对象
     */
    public static synchronized Deque<ActivityInfo> getAll() {

        //System.out.println("put get all start=="+ACTIVITIES.size());
        Deque<ActivityInfo> aiList = new LinkedList<ActivityInfo>();
        while (!ACTIVITIES.isEmpty()) {
            aiList.add(ACTIVITIES.removeFirst());

        }
        //System.out.println("put getall=="+aiList.size());

        return aiList;
    }

    // /**
    // * 收集是否结束
    // *
    // * @return true：结束，false：不在进行
    // */
    // public static boolean isFinshed() {
    // return checkAndSet();
    // }

    // /**
    // * 设置Activity收集结束
    // */
    // public static void finished() {
    // isCollectionFinised = true;
    // }

    /**
     * 检查是否已经完成了收集任务，如果是就返回，如果没有就先进行检查，查完后再返回结果
     *
     * @return true：停止收集，false：未停止收集
     */
    public static boolean isFinished() {
        return testNumberOutAndSet() || testTimeOutAndSet();
    }

    /**
     * 测试统计Activity数目是否超过规定
     *
     * @return true是，false否
     */
    public static boolean testNumberOutAndSet() {

        if (counter > Config.maxActivityDepth) {
            isNumberOut = true;
        }

        return isNumberOut;
    }

    /**
     * 判断Activity统计是超时
     *
     * @return true是，false否
     */
    public static boolean testTimeOutAndSet() {
        long current = System.currentTimeMillis();
        if (current - START > Config.maxCollectTime) {
            isTimeOut = true;
        }

        return isTimeOut;
    }

    public static void setLastResumeTime() {
        lastResumeTime = System.currentTimeMillis();
        ;
    }

    public static boolean isRoutetimeout() {
        long current = System.currentTimeMillis();
        long cha = current - lastResumeTime;
        return current - lastResumeTime > Config.maxNoOpTime;
    }

    public static void setCurrent(ActivityInfo currentinfo) {
        current = currentinfo;
    }

    public static void addtoRoute(ActivityInfo activityInfo) {
        ROUTES.addLast(Converter.getSimpleActivity(activityInfo));
    }

    public static void setNowActivityName(String activityName) {
        nowActivityName = activityName;
    }

    public static String getNowActivityName() {
        return nowActivityName;
    }
}
