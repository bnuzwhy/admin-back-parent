package com.why.common.utils;

import com.why.common.exception.admin.AdminException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

/**
 * @description: 类型转换工具 ｜ https://github.com/alibaba/fastjson/blob/master/src/main/java/com/alibaba/fastjson/util/TypeUtils.java
 * @author: Stan | stan.wang@paytm.com
 * @create: 2021/1/22 4:48 下午
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TypeUtils {
    private static final int[] IA = new int[256];
    private static boolean oracleTimestampMethodInited = false;
    private static Method oracleTimestampMethod;
    private static boolean oracleDateMethodInited = false;
    private static Method oracleDateMethod;

    /**
     * Object转换String
     * @param value Object类型数据
     * @return String 类型
     */
    public static String castToString(Object value){
        if(value == null){
            return null;
        }
        return value.toString();
    }

    /**
     * Object转换String，如果传入的Object是null，则返回保底的String类型数据
     * @param value     需要转换的数据
     * @param defaults  如果是null，保底返回的数据
     * @return
     */
    public static String castToString(Object value, String defaults){
        String castString = castToString(value);
        return Objects.isNull(castString) ? defaults : castString;
    }

    /**
     * Object转换Byte
     * @param value
     * @return
     */
    public static Byte castToByte(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Number){
            return ((Number) value).byteValue();
        }
        if(value instanceof String){
            String strval = (String) value;
            if(strval.length() == 0 || "null".equals(strval.toLowerCase())){
                return null;
            }
            return Byte.parseByte(strval);
        }
        throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换为byte, 值为 : " + value);
    }

    /**
     * Object转换Byte，如果要转换的数据为null，则返回保底的byte数据
     * @param value     要转换的数据
     * @param defaults  如果要转换的数据为null，保底数据
     * @return
     */
    public static Byte castToByte(Object value, Byte defaults){
        Byte aByte = castToByte(value);
        return Objects.isNull(aByte) ? defaults : aByte;
    }

    /**
     * Object转换char
     * @param value
     * @return
     */
    public static Character castToChar(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Character) {
            return (Character) value;
        }

        if (value instanceof String) {
            String strVal = (String) value;

            if (strVal.length() == 0) {
                return null;
            }

            if (strVal.length() != 1) {
                throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换为char, 值为 : " + value);
            }

            return strVal.charAt(0);
        }
        throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换为char, 值为 : " + value);
    }

    /**
     * Object转换char，如果要转换的数据为null，则返回保底的byte数据
     * @param value     要转换的数据
     * @param defaults  如果要转换的数据为null，保底数据
     * @return
     */
    public static Character castToChar(Object value, Character defaults) {
        Character castToChar = castToChar(value);
        return Objects.isNull(castToChar) ? defaults : castToChar;
    }

    /**
     * Object转换Short
     * @param value
     * @return
     */
    public static Short castToShort(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).shortValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;

            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            return Short.parseShort(strVal);
        }
        throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换为short, 值为 : " + value);
    }

    /**
     * Object转换short，如果要转换的数据为null，则返回保底的byte数据
     * @param value     要转换的数据
     * @param defaults  如果要转换的数据为null，保底数据
     * @return
     */
    public static Short castToShort(Object value, Short defaults) {
        Short castToShort = castToShort(value);
        return Objects.isNull(castToShort) ? defaults : castToShort;
    }

    /**
     * Object转换BigDecimal
     * @param value
     * @return
     */
    public static BigDecimal castToBigDecimal(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        if (value instanceof BigInteger) {
            return new BigDecimal((BigInteger) value);
        }

        String strVal = value.toString();
        if (strVal.length() == 0) {
            return null;
        }

        return new BigDecimal(strVal);
    }

    /**
     * Object转换BigDecimal，如果要转换的数据为null，则返回保底的byte数据
     * @param value     要转换的数据
     * @param defaults  如果要转换的数据为null，保底数据
     * @return
     */
    public static BigDecimal castToBigDecimal(Object value, BigDecimal defaults) {
        BigDecimal bigDecimal = castToBigDecimal(value);
        return Objects.isNull(bigDecimal) ? defaults : bigDecimal;

    }

    /**
     * Object转换BigInteger
     * @param value
     * @return
     */
    public static BigInteger castToBigInteger(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }

        if (value instanceof Float || value instanceof Double) {
            return BigInteger.valueOf(((Number) value).longValue());
        }

        String strVal = value.toString();
        if (strVal.length() == 0
                || "null".equals(strVal)
                || "NULL".equals(strVal)) {
            return null;
        }

        return new BigInteger(strVal);
    }

    /**
     * Object转换BigInteger，如果要转换的数据为null，则返回保底的byte数据
     * @param value     要转换的数据
     * @param defaults  如果要转换的数据为null，保底数据
     * @return
     */
    public static BigInteger castToBigInteger(Object value, BigInteger defaults) {

        BigInteger bigInteger = castToBigInteger(value);
        return Objects.isNull(bigInteger) ? defaults : bigInteger;

    }

    /**
     * Object转换Float
     * @param value
     * @return
     */
    public static Float castToFloat(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }

        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            if (strVal.indexOf(',') != 0) {
                strVal = strVal.replaceAll(",", "");
            }

            return Float.parseFloat(strVal);
        }

        throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换为float, 值为 : " + value);
    }

    /**
     * Object转换Float，如果要转换的数据为null，则返回保底的byte数据
     * @param value     要转换的数据
     * @param defaults  如果要转换的数据为null，保底数据
     * @return
     */
    public static Float castToFloat(Object value, Float defaults) {
        Float castToFloat = castToFloat(value);
        return Objects.isNull(castToFloat) ? defaults : castToFloat;

    }

    /**
     * Object转换Double
     * @param value
     * @return
     */
    public static Double castToDouble(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }

        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            if (strVal.indexOf(',') != 0) {
                strVal = strVal.replaceAll(",", "");
            }

            return Double.parseDouble(strVal);
        }

        throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换为double, 值为 : " + value);
    }

    /**
     * Object转换Double，如果要转换的数据为null，则返回保底的byte数据
     * @param value     要转换的数据
     * @param defaults  如果要转换的数据为null，保底数据
     * @return
     */
    public static Double castToDouble(Object value, Double defaults) {
        Double castToDouble = castToDouble(value);
        return Objects.isNull(castToDouble) ? defaults : castToDouble;

    }
    /**
     * Object转换Date
     * @param value
     * @return
     */
    public static Date castToDate(Object value) {
        if (value == null) {
            return null;
        }
        // 使用频率最高的，应优先处理
        if (value instanceof Date) {
            return (Date) value;
        }

        if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        }

        long longValue = -1;

        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
            return new Date(longValue);
        }

        if (value instanceof String) {
            String strVal = (String) value;

            if (strVal.startsWith("/Date(") && strVal.endsWith(")/")) {
                strVal = strVal.substring(6, strVal.length() - 2);
            }

            if (strVal.indexOf('-') != -1) {
                String format;
                if (strVal.length() == 10) {
                    format = "yyyy-MM-dd";
                } else if (strVal.length() == "yyyy-MM-dd HH:mm:ss".length()) {
                    format = "yyyy-MM-dd HH:mm:ss";
                } else {
                    format = "yyyy-MM-dd HH:mm:ss.SSS";
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                dateFormat.setTimeZone(TimeZone.getDefault());
                try {
                    return dateFormat.parse(strVal);
                } catch (ParseException e) {
                    throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换为Date, 值为 : " + value);
                }
            }

            if (strVal.length() == 0) {
                return null;
            }

            longValue = Long.parseLong(strVal);
        }

        if (longValue < 0) {
            Class<?> clazz = value.getClass();
            if ("oracle.sql.TIMESTAMP".equals(clazz.getName())) {
                if (oracleTimestampMethod == null && !oracleTimestampMethodInited) {
                    try {
                        oracleTimestampMethod = clazz.getMethod("toJdbc");
                    } catch (NoSuchMethodException e) {
                        // skip
                    } finally {
                        oracleTimestampMethodInited = true;
                    }
                }

                Object result;
                try {
                    assert oracleTimestampMethod != null;
                    result = oracleTimestampMethod.invoke(value);
                } catch (Exception e) {
                    throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换oracle.sql.TIMESTAMP to Date", e);

                }
                return (Date) result;
            }

            if ("oracle.sql.DATE".equals(clazz.getName())) {
                if (oracleDateMethod == null && !oracleDateMethodInited) {
                    try {
                        oracleDateMethod = clazz.getMethod("toJdbc");
                    } catch (NoSuchMethodException e) {
                        // skip
                    } finally {
                        oracleDateMethodInited = true;
                    }
                }

                Object result;
                try {
                    assert oracleDateMethod != null;
                    result = oracleDateMethod.invoke(value);
                } catch (Exception e) {
                    throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换oracle.sql.DATE to Date", e);
                }
                return (Date) result;
            }

            throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换为Date, 值为 : " + value);
        }

        return new Date(longValue);
    }

    /**
     * Object转换Date，如果要转换的数据为null，则返回保底的byte数据
     * @param value     要转换的数据
     * @param defaults  如果要转换的数据为null，保底数据
     * @return
     */
    public static Date castToDate(Object value, Date defaults) {
        Date castToDate = castToDate(value);
        return Objects.isNull(castToDate) ? defaults : castToDate;

    }
    /**
     * Object转换SqlDate
     * @param value
     * @return
     */
    @SuppressWarnings("Duplicates")
    public static java.sql.Date castToSqlDate(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof java.sql.Date) {
            return (java.sql.Date) value;
        }

        if (value instanceof Date) {
            return new java.sql.Date(((Date) value).getTime());
        }

        if (value instanceof Calendar) {
            return new java.sql.Date(((Calendar) value).getTimeInMillis());
        }

        long longValue = 0;

        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            longValue = Long.parseLong(strVal);
        }

        if (longValue <= 0) {
            // 忽略 1970-01-01 之前的时间处理？
            throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换为Date, 值为 : " + value);
        }

        return new java.sql.Date(longValue);
    }

    /**
     * Object转换SqlDate，如果要转换的数据为null，则返回保底的byte数据
     * @param value     要转换的数据
     * @param defaults  如果要转换的数据为null，保底数据
     * @return
     */
    public static java.sql.Date castToSqlDate(Object value, java.sql.Date defaults) {
        java.sql.Date castToSqlDate = castToSqlDate(value);
        return Objects.isNull(castToSqlDate) ? defaults : castToSqlDate;

    }
    /**
     * Object转换Timestamp
     * @param value
     * @return
     */
    @SuppressWarnings("Duplicates")
    public static java.sql.Timestamp castToTimestamp(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Calendar) {
            return new java.sql.Timestamp(((Calendar) value).getTimeInMillis());
        }

        if (value instanceof java.sql.Timestamp) {
            return (java.sql.Timestamp) value;
        }

        if (value instanceof Date) {
            return new java.sql.Timestamp(((Date) value).getTime());
        }

        long longValue = 0;

        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            longValue = Long.parseLong(strVal);
        }

        if (longValue <= 0) {
            throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换为Date, 值为 : " + value);
        }

        return new java.sql.Timestamp(longValue);
    }

    /**
     * Object转换Timestamp，如果要转换的数据为null，则返回保底的byte数据
     * @param value     要转换的数据
     * @param defaults  如果要转换的数据为null，保底数据
     * @return
     */
    public static java.sql.Timestamp castToTimestamp(Object value, java.sql.Timestamp defaults) {
        java.sql.Timestamp castToTimestamp = castToTimestamp(value);
        return Objects.isNull(castToTimestamp) ? defaults : castToTimestamp;

    }
    /**
     * Object转换Long
     * @param value
     * @return
     */
    public static Long castToLong(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).longValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            if (strVal.indexOf(',') != 0) {
                strVal = strVal.replaceAll(",", "");
            }

            try {
                return Long.parseLong(strVal);
            } catch (NumberFormatException ex) {
                //
            }

        }
        throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换为long, 值为 : " + value);
    }

    /**
     * Object转换Long，如果要转换的数据为null，则返回保底的byte数据
     * @param value     要转换的数据
     * @param defaults  如果要转换的数据为null，保底数据
     * @return
     */
    public static Long castToLong(Object value, Long defaults) {
        Long castToLong = castToLong(value);
        return Objects.isNull(castToLong) ? defaults : castToLong;

    }
    /**
     * Object转换Int
     * @param value
     * @return
     */
    public static Integer castToInt(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Integer) {
            return (Integer) value;
        }

        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;

            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            if (strVal.indexOf(',') != 0) {
                strVal = strVal.replaceAll(",", "");
            }

            return Integer.parseInt(strVal);
        }

        if (value instanceof Boolean) {
            return (Boolean) value ? 1 : 0;
        }
        throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换为int, 值为 : " + value);
    }

    /**
     * Object转换Int，如果要转换的数据为null，则返回保底的byte数据
     * @param value     要转换的数据
     * @param defaults  如果要转换的数据为null，保底数据
     * @return
     */
    public static Integer castToInt(Object value, Integer defaults) {
        Integer castToInt = castToInt(value);
        return Objects.isNull(castToInt) ? defaults : castToInt;

    }
    /**
     * Object转换Bytes
     * @param value
     * @return
     */
    public static byte[] castToBytes(Object value) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        }

        if (value instanceof String) {
            return decodeBase64((String) value);
        }
        throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换为int, 值为 : " + value);
    }

    /**
     * Decodes a BASE64 encoded string that is known to be resonably well formatted. The method is about twice as fast
     * as decode(String). The preconditions are:<br>
     * + The array must have a line length of 76 chars OR no line separators at all (one line).<br>
     * + Line separator must be "\r\n", as specified in RFC 2045 + The array must not contain illegal characters within
     * the encoded string<br>
     * + The array CAN have illegal characters at the beginning and end, those will be dealt with appropriately.<br>
     *
     * @param s The source string. Length 0 will return an empty array. <code>null</code> will throw an exception.
     * @return The decoded array of bytes. May be of length 0.
     */
    private static byte[] decodeBase64(String s) {
        // Check special case
        int sLen = s.length();
        if (sLen == 0) {
            return new byte[0];
        }

        int sIx = 0, eIx = sLen - 1; // Start and end index after trimming.

        // Trim illegal chars from start
        while (sIx < eIx && IA[s.charAt(sIx) & 0xff] < 0) {
            sIx++;
        }
        // Trim illegal chars from end
        while (eIx > 0 && IA[s.charAt(eIx) & 0xff] < 0) {
            eIx--;
        }
        // get the padding count (=) (0, 1 or 2)
        int pad = s.charAt(eIx) == '=' ? (s.charAt(eIx - 1) == '=' ? 2 : 1) : 0; // Count '=' at end.
        int cCnt = eIx - sIx + 1; // Content count including possible separators
        int sepCnt = sLen > 76 ? (s.charAt(76) == '\r' ? cCnt / 78 : 0) << 1 : 0;

        int len = ((cCnt - sepCnt) * 6 >> 3) - pad; // The number of decoded bytes
        byte[] dArr = new byte[len]; // Preallocate byte[] of exact length

        // Decode all but the last 0 - 2 bytes.
        int d = 0;
        for (int cc = 0, eLen = (len / 3) * 3; d < eLen; ) {
            // Assemble three bytes into an int from four "valid" characters.
            int i = IA[s.charAt(sIx++)] << 18 | IA[s.charAt(sIx++)] << 12 | IA[s.charAt(sIx++)] << 6
                    | IA[s.charAt(sIx++)];

            // Add the bytes
            dArr[d++] = (byte) (i >> 16);
            dArr[d++] = (byte) (i >> 8);
            dArr[d++] = (byte) i;

            // If line separator, jump over it.
            if (sepCnt > 0 && ++cc == 19) {
                sIx += 2;
                cc = 0;
            }
        }

        if (d < len) {
            // Decode last 1-3 bytes (incl '=') into 1-3 bytes
            int i = 0;
            for (int j = 0; sIx <= eIx - pad; j++) {
                i |= IA[s.charAt(sIx++)] << (18 - j * 6);
            }
            for (int r = 16; d < len; r -= 8) {
                dArr[d++] = (byte) (i >> r);
            }
        }

        return dArr;
    }

    /**
     * Object转换Bytes，如果要转换的数据为null，则返回保底的byte数据
     * @param value     要转换的数据
     * @param defaults  如果要转换的数据为null，保底数据
     * @return
     */
    public static byte[] castToBytes(Object value, byte[] defaults) {
        byte[] castToBytes = castToBytes(value);
        return Objects.isNull(castToBytes) ? defaults : castToBytes;

    }
    /**
     * Object转换Boolean
     * @param value
     * @return
     */
    public static Boolean castToBoolean(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Boolean) {
            return (Boolean) value;
        }

        if (value instanceof Number) {
            return ((Number) value).intValue() == 1;
        }

        if (value instanceof String) {
            String strVal = (String) value;

            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            if ("true".equalsIgnoreCase(strVal)
                    || "1".equals(strVal)) {
                return Boolean.TRUE;
            }

            if ("false".equalsIgnoreCase(strVal)
                    || "0".equals(strVal)) {
                return Boolean.FALSE;
            }
        }
        throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换为boolean, 值为 : " + value);
    }

    /**
     * Object转换Boolean，如果要转换的数据为null，则返回保底的byte数据
     * @param value     要转换的数据
     * @param defaults  如果要转换的数据为null，保底数据
     * @return
     */
    public static Boolean castToBoolean(Object value, Boolean defaults) {
        Boolean castToBoolean = castToBoolean(value);
        return Objects.isNull(castToBoolean) ? defaults : castToBoolean;

    }

    /**
     * Object转换Enum
     * @param obj
     * @param clazz
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T castToEnum(Object obj, Class<T> clazz) {
        try {
            if (obj instanceof String) {
                String name = (String) obj;
                if (name.length() == 0) {
                    return null;
                }

                return (T) Enum.valueOf((Class<? extends Enum>) clazz, name);
            }

            if (obj instanceof Number) {
                int ordinal = ((Number) obj).intValue();
                Object[] values = clazz.getEnumConstants();
                if (ordinal < values.length) {
                    return (T) values[ordinal];
                }
            }
        } catch (Exception ex) {
            throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换为 : " + clazz.getName(), ex);
        }

        throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法转换为 : " + clazz.getName());
    }

    /**
     * Object转换Enum，如果要转换的数据为null，则返回保底的byte数据
     * @param value     要转换的数据
     * @param clazz
     * @param defaults  如果要转换的数据为null，保底数据
     * @return
     */
    public static <T> T castToEnum(Object value, Class<T> clazz, T defaults) {
        T castToEnum = castToEnum(value, clazz);
        return Objects.isNull(castToEnum) ? defaults : castToEnum;

    }
}
