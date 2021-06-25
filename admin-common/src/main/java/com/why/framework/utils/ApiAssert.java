package com.why.framework.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.why.common.exception.admin.AdminException;
import com.why.common.exception.admin.AdminResultCode;
import com.why.common.exception.core.ErrorStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.MapUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * API 断言
 * </p>
 *
 * @author Caratacus
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiAssert {

    /**
     * obj1 eq obj2
     *
     * @param obj1
     * @param obj2
     * @param resultCode
     */
    public static void equals(AdminResultCode resultCode, Object obj1, Object obj2) {
        if (!Objects.equals(obj1, obj2)) {
            failure(resultCode);
        }
    }

    public static void isTrue(AdminResultCode resultCode, boolean condition) {
        if (!condition) {
            failure(resultCode);
        }
    }

    public static void isFalse(AdminResultCode resultCode, boolean condition) {
        if (condition) {
            failure(resultCode);
        }
    }

    public static void isNull(AdminResultCode resultCode, Object... conditions) {
        if (ObjectUtils.isNotNull(conditions)) {
            failure(resultCode);
        }
    }

    public static void notNull(AdminResultCode resultCode, Object... conditions) {
        if (ObjectUtils.isNull(conditions)) {
            failure(resultCode);
        }
    }

    /**
     * <p>
     * 失败结果
     * </p>
     *
     * @param resultCode 异常错误码
     */
    public static void failure(AdminResultCode resultCode) {
        throw new AdminException(resultCode);
    }

    public static void notEmpty(AdminResultCode resultCode, Object[] array) {
        if (ObjectUtils.isEmpty(array)) {
            failure(resultCode);
        }
    }

    /**
     * Assert that an array has no null elements. Note: Does not complain if the
     * array is empty!
     * <p>
     * <pre class="code">
     * Assert.noNullElements(array, &quot;The array must have non-null elements&quot;);
     * </pre>
     *
     * @param array         the array to check
     * @param resultCode the exception message to use if the assertion fails
     * @throws AdminException if the object array contains a {@code null} element
     */
    public static void noNullElements(AdminResultCode resultCode, Object[] array) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    failure(resultCode);
                }
            }
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection    the collection to check
     * @param resultCode the exception message to use if the assertion fails
     * @throws AdminException if the collection is {@code null} or has no elements
     */
    public static void notEmpty(AdminResultCode resultCode, Collection<?> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            failure(resultCode);
        }
    }

    /**
     * Assert that a Map has entries; that is, it must not be {@code null} and
     * must have at least one entry.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(map, &quot;Map must have entries&quot;);
     * </pre>
     *
     * @param map           the map to check
     * @param resultCode the exception message to use if the assertion fails
     * @throws AdminException if the map is {@code null} or has no entries
     */
    public static void notEmpty(AdminResultCode resultCode, Map<?, ?> map) {
        if (MapUtils.isEmpty(map)) {
            failure(resultCode);
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection    the collection to check
     * @param resultCode the exception message to use if the assertion fails
     * @throws AdminException if the collection is {@code null} or has no elements
     */
    public static void isEmpty(AdminResultCode resultCode, Collection<?> collection) {
        if (CollectionUtils.isNotEmpty(collection)) {
            failure(resultCode);
        }
    }

    /**
     * Assert that a Map has entries; that is, it must not be {@code null} and
     * must have at least one entry.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(map, &quot;Map must have entries&quot;);
     * </pre>
     *
     * @param map           the map to check
     * @param resultCode the exception message to use if the assertion fails
     * @throws AdminException if the map is {@code null} or has no entries
     */
    public static void isEmpty(AdminResultCode resultCode, Map<?, ?> map) {
        if (MapUtils.isNotEmpty(map)) {
            failure(resultCode);
        }
    }

    /**
     * obj1 eq obj2
     *
     * @param obj1
     * @param obj2
     * @param errorStatus
     */
    public static void equals(ErrorStatus errorStatus, Object obj1, Object obj2) {
        if (!Objects.equals(obj1, obj2)) {
            failure(errorStatus);
        }
    }

    public static void isTrue(ErrorStatus errorStatus, boolean condition) {
        if (!condition) {
            failure(errorStatus);
        }
    }

    public static void isFalse(ErrorStatus errorStatus, boolean condition) {
        if (condition) {
            failure(errorStatus);
        }
    }

    public static void isNull(ErrorStatus errorStatus, Object... conditions) {
        if (ObjectUtils.isNotNull(conditions)) {
            failure(errorStatus);
        }
    }

    public static void notNull(ErrorStatus errorStatus, Object... conditions) {
        if (ObjectUtils.isNull(conditions)) {
            failure(errorStatus);
        }
    }

    /**
     * <p>
     * 失败结果
     * </p>
     *
     * @param errorStatus 异常错误码
     */
    public static void failure(ErrorStatus errorStatus) {
        throw new AdminException(errorStatus);
    }

    public static void notEmpty(ErrorStatus errorStatus, Object[] array) {
        if (ObjectUtils.isEmpty(array)) {
            failure(errorStatus);
        }
    }

    /**
     * Assert that an array has no null elements. Note: Does not complain if the
     * array is empty!
     * <p>
     * <pre class="code">
     * Assert.noNullElements(array, &quot;The array must have non-null elements&quot;);
     * </pre>
     *
     * @param array     the array to check
     * @param errorStatus the exception message to use if the assertion fails
     * @throws AdminException if the object array contains a {@code null} element
     */
    public static void noNullElements(ErrorStatus errorStatus, Object[] array) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    failure(errorStatus);
                }
            }
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection the collection to check
     * @param errorStatus  the exception message to use if the assertion fails
     * @throws AdminException if the collection is {@code null} or has no elements
     */
    public static void notEmpty(ErrorStatus errorStatus, Collection<?> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            failure(errorStatus);
        }
    }

    /**
     * Assert that a Map has entries; that is, it must not be {@code null} and
     * must have at least one entry.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(map, &quot;Map must have entries&quot;);
     * </pre>
     *
     * @param map       the map to check
     * @param errorStatus the exception message to use if the assertion fails
     * @throws AdminException if the map is {@code null} or has no entries
     */
    public static void notEmpty(ErrorStatus errorStatus, Map<?, ?> map) {
        if (MapUtils.isEmpty(map)) {
            failure(errorStatus);
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection the collection to check
     * @param errorStatus  the exception message to use if the assertion fails
     * @throws AdminException if the collection is {@code null} or has no elements
     */
    public static void isEmpty(ErrorStatus errorStatus, Collection<?> collection) {
        if (CollectionUtils.isNotEmpty(collection)) {
            failure(errorStatus);
        }
    }

    /**
     * Assert that a Map has entries; that is, it must not be {@code null} and
     * must have at least one entry.
     * <p>
     * <pre class="code">
     * Assert.notEmpty(map, &quot;Map must have entries&quot;);
     * </pre>
     *
     * @param map       the map to check
     * @param errorStatus the exception message to use if the assertion fails
     * @throws AdminException if the map is {@code null} or has no entries
     */
    public static void isEmpty(ErrorStatus errorStatus, Map<?, ?> map) {
        if (MapUtils.isNotEmpty(map)) {
            failure(errorStatus);
        }
    }

}
