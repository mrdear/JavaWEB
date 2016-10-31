package cn.mrdear.pay.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Niu Li
 * @date 2016/10/29
 */
public class Assert {
        public Assert() {
        }

        public static void isTrue(boolean expression, String message) {
            if(!expression) {
                throw new IllegalArgumentException(message);
            }
        }

        public static void isTrue(boolean expression) {
            isTrue(expression, "[Assertion failed] - this expression must be true");
        }

        public static void isNull(Object object, String message) {
            if(object != null) {
                throw new IllegalArgumentException(message);
            }
        }

        public static void isNull(Object object) {
            isNull(object, "[Assertion failed] - the object argument must be null");
        }

        public static void notNull(Object object, String message) {
            if(object == null) {
                throw new IllegalArgumentException(message);
            }
        }

        public static void notNull(Object object) {
            notNull(object, "[Assertion failed] - this argument is required; it must not be null");
        }



        public static void isNotEmpty(String text) {
            isNotEmpty(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
        }

        public static void isNotEmpty(String text, String message) {
            if(!StringUtils.isNotEmpty(text)) {
                throw new IllegalArgumentException(message);
            }
        }

        public static void doesNotContain(String textToSearch, String substring, String message) {
            if(StringUtils.isNotEmpty(textToSearch) && StringUtils.isNotEmpty(substring) && textToSearch.contains(substring)) {
                throw new IllegalArgumentException(message);
            }
        }

        public static void doesNotContain(String textToSearch, String substring) {
            doesNotContain(textToSearch, substring, "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
        }

        public static void noNullElements(Object[] array, String message) {
            if(array != null) {
                Object[] var2 = array;
                int var3 = array.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    Object element = var2[var4];
                    if(element == null) {
                        throw new IllegalArgumentException(message);
                    }
                }
            }

        }

        public static void noNullElements(Object[] array) {
            noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
        }

        public static void isInstanceOf(Class<?> clazz, Object obj) {
            isInstanceOf(clazz, obj, "");
        }

        public static void isInstanceOf(Class<?> type, Object obj, String message) {
            notNull(type, "Type to check against must not be null");
            if(!type.isInstance(obj)) {
                throw new IllegalArgumentException((StringUtils.isNotEmpty(message)?message + " ":"") + "Object of class [" + (obj != null?obj.getClass().getName():"null") + "] must be an instance of " + type);
            }
        }

        public static void isAssignable(Class<?> superType, Class<?> subType) {
            isAssignable(superType, subType, "");
        }

        public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
            notNull(superType, "Type to check against must not be null");
            if(subType == null || !superType.isAssignableFrom(subType)) {
                throw new IllegalArgumentException(message + subType + " is not assignable to " + superType);
            }
        }

        public static void state(boolean expression, String message) {
            if(!expression) {
                throw new IllegalStateException(message);
            }
        }

        public static void state(boolean expression) {
            state(expression, "[Assertion failed] - this state invariant must be true");
        }
}
