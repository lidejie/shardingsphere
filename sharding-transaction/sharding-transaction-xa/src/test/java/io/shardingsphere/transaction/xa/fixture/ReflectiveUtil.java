/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.transaction.xa.fixture;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflective utility.
 * 
 * @author zhaojun 
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectiveUtil {
    
    /**
     * Get property.
     * 
     * @param target target
     * @param fieldName field name
     * @return property
     * @throws IllegalAccessException illegal access exception
     */
    public static Object getProperty(final Object target, final String fieldName) throws IllegalAccessException {
        Field field = getField(target, fieldName);
        Preconditions.checkNotNull(field);
        field.setAccessible(true);
        return field.get(target);
    }
    
    /**
     * Get field.
     * @param target target
     * @param fieldName field name
     * @return field
     */
    public static Field getField(final Object target, final String fieldName) {
        Class clazz = target.getClass();
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
                // CHECKSTYLE:OFF
            } catch (Exception ignored) {
            }
            // CHECKSTYLE:ON
            clazz = clazz.getSuperclass();
        }
        return null;
    }
    
    /**
     * Set value to specified field.
     * @param target target
     * @param fieldName field name
     * @param value value
     */
    @SneakyThrows
    public static void setProperty(final Object target, final String fieldName, final Object value) {
        Field field = getField(target, fieldName);
        Preconditions.checkNotNull(field);
        field.setAccessible(true);
        field.set(target, value);
    }
    
    /**
     * Invoke target method.
     *
     * @param target target object
     * @param methodName method name
     */
    @SneakyThrows
    public static void methodInvoke(final Object target, final String methodName) {
        Method method = target.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        try {
            method.invoke(target);
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }
}
