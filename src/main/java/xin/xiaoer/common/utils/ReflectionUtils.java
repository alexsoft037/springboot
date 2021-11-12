package xin.xiaoer.common.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import xin.xiaoer.common.annotation.Serializable;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReflectionUtils {
    public static final String TAG = ReflectionUtils.class.getSimpleName();
    private static Map<String, ClassInfo> lP = new HashMap();

    public ReflectionUtils() {
    }

    public static ReflectionUtils.ClassInfo getClassInfo(Class<?> clazz) {
        ReflectionUtils.ClassInfo classInfo = (ReflectionUtils.ClassInfo)lP.get(clazz.getName());
        if (classInfo == null) {
            classInfo = new ReflectionUtils.ClassInfo();
            classInfo.fields = a(clazz);
            lP.put(clazz.getName(), classInfo);
        }

        return classInfo;
    }

    public static void convJSONToObject(JSONObject jsonObject, Object object) {
        Iterator var2 = getClassInfo(object.getClass()).fields.iterator();

        while(true) {
            Field field;
            Serializable annotation;
            String jsonName;
            do {
                do {
                    do {
                        do {
                            if (!var2.hasNext()) {
                                return;
                            }

                            field = (Field)var2.next();
                            annotation = (Serializable)field.getAnnotation(Serializable.class);
                        } while(annotation == null);
                    } while(StringUtility.isEmpty(annotation.name()));

                    jsonName = annotation.name();
                } while(!jsonObject.has(jsonName));
            } while(StringUtility.isNull(jsonName));

            field.setAccessible(true);

            try {
                if (field.getType() == String.class) {
                    field.set(object, jsonObject.optString(jsonName));
                } else if (field.getType() != Integer.TYPE && field.getType() != Integer.class) {
                    if (field.getType() != Long.TYPE && field.getType() != Long.class) {
                        if (field.getType() != Double.TYPE && field.getType() != Double.class) {
                            if (field.getType() != Boolean.TYPE && field.getType() != Boolean.class) {
                                if (field.getType() != Short.TYPE && field.getType() != Short.class) {
                                    annotation = (Serializable)field.getType().getAnnotation(Serializable.class);
                                    if (annotation != null) {
                                        Object subObject = field.getType().newInstance();
                                        convJSONToObject(jsonObject.optJSONObject(jsonName), subObject);
                                        field.set(object, subObject);
                                    } else {
                                        field.set(object, jsonObject.opt(jsonName));
                                    }
                                } else {
                                    field.set(object, (short)jsonObject.optInt(jsonName));
                                }
                            } else {
                                field.set(object, jsonObject.optBoolean(jsonName));
                            }
                        } else {
                            field.set(object, jsonObject.optDouble(jsonName));
                        }
                    } else {
                        field.set(object, jsonObject.optLong(jsonName));
                    }
                } else {
                    field.set(object, jsonObject.optInt(jsonName));
                }
            } catch (Exception var7) {
            }
        }
    }

    public static JSONObject convObjectToJSON(Object object) {
        JSONObject result = new JSONObject();
        Iterator var2 = getClassInfo(object.getClass()).fields.iterator();

        while(true) {
            Field field;
            Serializable annotation;
            do {
                do {
                    if (!var2.hasNext()) {
                        return result;
                    }

                    field = (Field)var2.next();
                    annotation = (Serializable)field.getAnnotation(Serializable.class);
                } while(annotation == null);
            } while(StringUtility.isEmpty(annotation.name()));

            String paramName = annotation.name();
            field.setAccessible(true);

            try {
                Object value = field.get(object);
                if (value != null) {
                    if (!(value instanceof Iterable)) {
                        annotation = (Serializable)field.getType().getAnnotation(Serializable.class);
                        if (annotation != null) {
                            result.put(paramName, convObjectToJSON(value));
                        } else {
                            result.put(paramName, value);
                        }
                    } else {
                        Iterable<?> iterable = (Iterable)value;
                        JSONArray array = new JSONArray();
                        Iterator var9 = iterable.iterator();

                        while(var9.hasNext()) {
                            Object obj = var9.next();
                            annotation = (Serializable)obj.getClass().getAnnotation(Serializable.class);
                            if (annotation != null) {
                                array.add(convObjectToJSON(obj));
                            } else {
                                array.add(obj);
                            }
                        }

                        result.put(paramName, array);
                    }
                }
            } catch (Exception var11) {
            }
        }
    }

    private static final List<Field> a(Class<?> cls) {
        List<Field> list = new ArrayList();
        Field[] fields = cls.getDeclaredFields();
        Field[] var3 = fields;
        int var4 = fields.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Field field = var3[var5];
            list.add(field);
        }

        Class<?> superClass = (Class)cls.getGenericSuperclass();
        if (superClass != null) {
            list.addAll(a(superClass));
        }

        return list;
    }

    public static class NameObjectParam {
        public String name;
        public Object object;

        public NameObjectParam(String name, Object object) {
            this.name = name;
            if (object instanceof Date) {
                Date date = (Date)object;
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                this.object = df.format(date);
            } else {
                this.object = object;
            }

        }
    }

    public static class ClassInfo {
        public List<Field> fields;

        public ClassInfo() {
        }
    }
}
