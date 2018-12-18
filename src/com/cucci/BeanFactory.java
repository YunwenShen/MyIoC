package com.cucci;


import com.cucci.annations.AutoWired;
import com.cucci.annations.Bean;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 注册Bean、注入Bean
 *
 * @author shenyunwen
 **/
public class BeanFactory {

    /**
     * 用于存放Bean
     * Key为Bean类型
     * Value为对应Bean的实例
     */
    private Map<String, Object> repository = new HashMap<String, Object>();

    /**
     * 初始化Bean
     *
     * @param classSet Class集合
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public void init(Set<Class> classSet) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // 初始化bean
        for (Class clazz : classSet) {
            Bean bean = (Bean) clazz.getAnnotation(Bean.class);
            if (bean != null) {
                String key = clazz.getName();
                repository.put(key, clazz.newInstance());
            }
        }
        // 注入bean
        for (Class clazz : classSet) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(AutoWired.class)) {
                    Class<?> type = field.getType();
                    Object object = repository.get(clazz.getName());
                    Object value = repository.get(type.getName());
                    field.set(object, value);
                    Method method = clazz.getMethod("say");
                    method.invoke(object);
                }
            }

        }
    }

    /**
     * 获取对应包名下的所有类
     *
     * @param packageName 包名
     * @return Class 集合
     * @throws ClassNotFoundException
     */
    public Set<Class> findClassSet(String packageName) throws ClassNotFoundException {
        Set<Class> classSet = new HashSet<Class>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        // 该文件路径不存在
        if (url == null) {
            return null;
        }
        File directory = new File(url.getPath());
        File[] files = directory.listFiles();
        // 该文件下不存在子目录
        if (files == null) {
            return null;
        }
        for (File file : files) {
            String filePath = file.getPath();
            if (filePath.endsWith(".class")) {
                String filePath2 = filePath.replace("\\", ".");
                int from = filePath2.indexOf(packageName);
                int end = filePath.lastIndexOf(".");
                String className = filePath2.substring(from, end);
                classSet.add(Class.forName(className));
            }
        }
        return classSet;
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        BeanFactory beanFactory = new BeanFactory();
        Set<Class> classSet = beanFactory.findClassSet("com.cucci.service");
        beanFactory.init(classSet);
        System.out.println(beanFactory.repository);
    }
}
