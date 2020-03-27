package com.hydra.context;

import com.hydra.anno.MyAutowired;
import com.hydra.anno.MyService;
import com.hydra.exception.AutowireNotFoundException;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
public class AnnotationConfigApplicationContext {
    HashMap<String, Object> map = new HashMap<>();
    public void scan(String basePackage) {
        //获取class运行时的路径
        String rootPath = this.getClass().getResource("/").getPath();
        //将包的.替换为文件系统的\
        String basePackagePath = basePackage.replaceAll("\\.", "\\\\");
        File file = new File(rootPath + "//" + basePackagePath);
        String[] names = file.list();
        //暂时不考虑依赖顺序、循环依赖的问题
        for (String name : names) {
            name = name.replace(".class", "");
            Object object = null;
            try {
                Class clazz = Class.forName(basePackage + "." + name);
                //判断是否属于@Service @Component等注解,以我们自己写的注解为例
                if (clazz.isAnnotationPresent(MyService.class)) {
                    MyService myService = (MyService) clazz.getAnnotation(MyService.class); //获取注解
                    String beanName = myService.value();
                    //System.out.println(beanName);
                    object = clazz.newInstance();
                    //拿到所有属性，判断是否需要依赖注入（需补充，还有byType，byName）
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(MyAutowired.class)) {
                            MyAutowired autowired = field.getAnnotation(MyAutowired.class);
                            //Autowired应该先按照byType查找，当数量大于1个时，按照byName查找
                            int count = 0;
                            String findName = "";
                            Class fieldClass = field.getType(); //获取field的Class
                            boolean isInterface = fieldClass.isInterface();//判断是否接口
                            //接口,可以用实现类
                            if (isInterface) {
                                for (String key : map.keySet()) {
                                    //暂时先判断一个接口作为例子
                                    if (map.get(key).getClass().getInterfaces()[0].getName().equals(fieldClass.getName())) {
                                        findName = key;
                                        count++;
                                    }
                                }
                            } else {//类,应该严格使用类来判断
                                for (String key : map.keySet()) {
                                    //使用全限定名确定同一个类
                                    if (map.get(key).getClass().getName().equals(fieldClass.getName())) {
                                        findName = key;
                                        count++;
                                    }
                                }
                            }
                            if (count == 0) {
                                throw new AutowireNotFoundException("没有找到对应类型的bean");
                            }
                            if (count == 1) {
                                field.setAccessible(true);
                                field.set(object, getBean(findName));
                            }
                            if (count > 1) {
                                String injectName = autowired.value();
                                field.setAccessible(true);
                                field.set(object, getBean(injectName));
                            }
                        }
                    }
                    map.put(beanName, object);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public Object getBean(String beanName) {
        return map.get(beanName);
    }
    public HashMap<String, Object> getContext() {
        return map;
    }
}