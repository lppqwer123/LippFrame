package com.first.frame.utils;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashSet;

import androidx.annotation.NonNull;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class HotFixDexUtil {

    public static final String DEX_SUFFIX = ".dex";
    public static final String APK_SUFFIX = ".apk";
    public static final String JAR_SUFFIX = ".jar";
    public static final String ZIP_SUFFIX = ".zip";
    public static final String DEX_DIR = "odex"; //data/tata/com.first.frame/files/odex 指定文件夹,存放修复好的dex文件
    public static final String OPTIMIZE_DEX_DIR = "optimize_dex";
    public static HashSet<File> loadedDex = new HashSet<>();

    static {
        loadedDex.clear();
    }

    /**
     * 开启修复
     *
     * @param context
     */
    public static void startRepair(final Context context) {

        File fileDir = new File(context.getFilesDir().getAbsolutePath() +
                File.separator + DEX_DIR);

        if (!fileDir.exists()) {//如果目录不存在就创建所有目录，这里需要添加权限
            fileDir.mkdirs();
        }
        if (HotFixDexUtil.isGoingToFix(context,fileDir)) {
            doDexInject(context, loadedDex);
        }
    }

    /**
     * @author bthvi
     * @time 2019/10/10 11:42
     * @desc 验证是否需要热修复
     */
    public static boolean isGoingToFix(@NonNull Context context , File fileDir) {
        boolean canFix = false;

        File[] listFiles = fileDir.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.getName().endsWith(DEX_SUFFIX)
                        || file.getName().endsWith(APK_SUFFIX)
                        || file.getName().endsWith(JAR_SUFFIX)
                        || file.getName().endsWith(ZIP_SUFFIX)) {

                    loadedDex.add(file);// 存入集合,有目标dex文件, 需要修复
                    canFix = true;
                }
            }
        }
        return canFix;
    }

    /**
     * @param context
     */
    public static void loadFixedDex(Context context) {
        loadFixedDex(context, null);
    }

    /**
     * 加载补丁
     *
     * @param context       上下文
     * @param patchFilesDir 补丁所在目录
     */
    public static void loadFixedDex(Context context, File patchFilesDir) {
        // dex合并之前的dex
        doDexInject(context, loadedDex);
    }


    private static void doDexInject(Context appContext, HashSet<File> loadedDex) {
        String optimizeDir = appContext.getFilesDir().getAbsolutePath() +
                File.separator + OPTIMIZE_DEX_DIR;

        File fopt = new File(optimizeDir);
        if (!fopt.exists()) {
            fopt.mkdirs();
        }
        try {
            // 1.加载应用程序dex的Loader
            PathClassLoader pathLoader = (PathClassLoader) appContext.getClassLoader();
            for (File dex : loadedDex) {
                // 2.加载指定的修复的dex文件的Loader
                DexClassLoader dexLoader = new DexClassLoader(
                        dex.getAbsolutePath(),// 修复好的dex（补丁）所在目录
                        fopt.getAbsolutePath(),// 存放dex的解压目录（用于jar、zip、apk格式的补丁）
                        null,// 加载dex时需要的库
                        pathLoader// 父类加载器
                );
                // 3.开始合并,合并的目标是Element[],重新赋值它的值即可
                //3.1 准备好pathList的引用
                Object dexPathList = getPathList(dexLoader);
                Object pathPathList = getPathList(pathLoader);
                //3.2 从pathList中反射出element集合
                Object leftDexElements = getDexElements(dexPathList);
                Object rightDexElements = getDexElements(pathPathList);
                //3.3 合并两个dex数组
                Object dexElements = combineArray(leftDexElements, rightDexElements);

                // 重写给PathList里面的Element[] dexElements;赋值
                Object pathList = getPathList(pathLoader);// 一定要重新获取，不要用pathPathList，会报错
                setField(pathList, pathList.getClass(), "dexElements", dexElements);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射给对象中的属性重新赋值
     */
    private static void setField(Object obj, Class<?> cl, String field, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = cl.getDeclaredField(field);
        declaredField.setAccessible(true);
        declaredField.set(obj, value);
    }

    /**
     * 反射得到对象中的属性值
     */
    private static Object getField(Object obj, Class<?> cl, String field) throws NoSuchFieldException, IllegalAccessException {
        Field localField = cl.getDeclaredField(field);
        localField.setAccessible(true);
        return localField.get(obj);
    }

    /**
     * 反射得到类加载器中的pathList对象
     */
    private static Object getPathList(Object baseDexClassLoader) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return getField(baseDexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");
    }

    /**
     * 反射得到pathList中的dexElements
     */
    private static Object getDexElements(Object pathList) throws NoSuchFieldException, IllegalAccessException {
        return getField(pathList, pathList.getClass(), "dexElements");
    }

    /**
     * 数组合并
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> clazz = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);// 得到左数组长度（补丁数组）
        int j = Array.getLength(arrayRhs);// 得到原dex数组长度
        int k = i + j;// 得到总数组长度（补丁数组+原dex数组）
        Object result = Array.newInstance(clazz, k);// 创建一个类型为clazz，长度为k的新数组
        System.arraycopy(arrayLhs, 0, result, 0, i);
        System.arraycopy(arrayRhs, 0, result, i, j);
        return result;
    }

}
