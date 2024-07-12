package AbortLock;

import java.lang.reflect.Field;
import java.util.concurrent.locks.ReentrantLock;

import java.util.HashMap;
import java.util.Map;

public class AbortLock extends ReentrantLock {
    Map<Field, Object> state = new HashMap<>();
    private Class<?> holderClass;

    public AbortLock(Class<?> holderClass) {
        this.holderClass = holderClass;
    }

    public void lock(Object instance) {
        super.lock();

        try {
            for (Field field : holderClass.getDeclaredFields()) {
                field.setAccessible(true);
                state.put(field, field.get(instance));
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

    public void abort(AbortLock lock, Object instance) {
        System.out.println("Aborted");
        
        for (Field field : state.keySet()) {    
            Class<?> fieldType = field.getType();
            try {
                if (fieldType == int.class) {
                    field.setInt(instance, (int) state.get(field));
                } else if (fieldType == long.class) {
                    field.setLong(instance, (long) state.get(field));
                } else if (fieldType == double.class) {
                    field.setDouble(instance, (double) state.get(field));
                } else if (fieldType == float.class) {
                    field.setFloat(instance, (float) state.get(field));
                } else if (fieldType == short.class) {
                    field.setShort(instance, (short) state.get(field));
                } else if (fieldType == byte.class) {
                    field.setByte(instance, (byte) state.get(field));
                } else if (fieldType == char.class) {
                    field.setChar(instance, (char) state.get(field));
                } else if (fieldType == boolean.class) {
                    field.setBoolean(instance, (boolean) state.get(field));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void unlock(){
        super.unlock();
    }
}