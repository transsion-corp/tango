package com.transsion.framework.tango.common;

import com.transsion.framework.tango.common.exception.InitializeException;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * @Author mengqi.lv
 * @Date 2022/1/12
 * @Version 1.0
 **/
public class Utility {
    private static final Logger logger = LoggerFactory.getLogger(Utility.class);

    private static volatile String ip = null;
    private static volatile long lastUpdateIpTime = 0;
    private static volatile long updateIpInterval = 60000;

    private static SecureRandom random;

    static {
        try {
            SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new InitializeException("missing random algorithm", e);
        }
    }

    public static boolean isEmptyStr(String s) {
        return s == null || s.isEmpty();
    }

    public static <T> Set<Class<? extends T>> scannerSubTypeFromPackage(String packageName, Class<T> subType) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf(subType);
    }

    public static String joinStrings(String sep, String[] parts) {
        if (parts == null || parts.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(String part : parts) {
            sb.append(part);
            sb.append(sep);
        }
        // remove the last sep
        return sb.substring(0, sb.lastIndexOf(sep));
    }

    public static String joinStrings(String sep, List<String> parts) {
        return joinStrings(sep, parts.toArray(new String[0]));
    }

    public static int nextRandom(int bound) {
        return random.nextInt(bound);
    }

    public static List<String> splitStrings(String regex, String data) {
        List<String> result = new ArrayList<String>();
        if (isEmptyStr(data)) {
            return result;
        }
        String[] parts = data.split(regex);
        if (parts == null) {
            return result;
        }
        for(String p : parts) {
            if (p == null) {
                continue;
            }
            p = p.trim();
            if (isEmptyStr(p)) {
                continue;
            }
            result.add(p);
        }
        return result;
    }

    public static boolean isEmpty(final Collection c) {
        if (c == null) {
            return true;
        }
        return c.size() == 0;
    }
    public static boolean isEmpty(final Map m) {
        if (m == null) {
            return true;
        }
        return m.size() == 0;
    }

    public static boolean isEmpty(final CharSequence s) {
        if (s == null) {
            return true;
        }
        return s.length() == 0;
    }

    public static boolean isBlank(final CharSequence s) {
        if (s == null) {
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public final static String getIp() {
        if (System.currentTimeMillis() - lastUpdateIpTime < updateIpInterval) {
            return ip;
        }
        String newIp = _getIp();
        if (ip == null) {
            ip = newIp;
        } else if (!ip.equals(newIp)) {
            ip = newIp;
        }
        lastUpdateIpTime = System.currentTimeMillis();
        return ip;
    }

    private static String _getIp() {
        String ip = null;
        try {
            Enumeration<NetworkInterface> er = NetworkInterface.getNetworkInterfaces();
            while (er.hasMoreElements()) {
                NetworkInterface ni = er.nextElement();
                if (ni.getName().startsWith("eth") || ni.getName().startsWith("bond") || ni.getName().startsWith("en")) {
                    List<InterfaceAddress> list = ni.getInterfaceAddresses();
                    for (InterfaceAddress interfaceAddress : list) {
                        InetAddress address = interfaceAddress.getAddress();
                        if (address instanceof Inet4Address) {
                            ip = address.getHostAddress();
                            break;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            logger.error("Get ip error.", e);
        }
        if (ip == null) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                logger.error("Parse ip error.", e);
            }
        }
        return ip;
    }

    public static int ipToInt(String ipStr) {
        if (Utility.isEmpty(ipStr)) {
            return 0;
        }
        String[] split = ipStr.split("\\.");
        if (split.length != 4) {
            return 0;
        }
        int[] ip = new int[4];
        for (int i=0; i< split.length; i++) {
            ip[i] = Integer.parseInt(split[i]);
        }
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    public static String intToIp(int ipv4_int) {
        String[] ipString = new String[4];

        for (int i = 0; i < 4; i++) {
            int pos = i * 8;

            int and = ipv4_int & (255 << pos);

            ipString[3-i] = String.valueOf(and >>> pos);
        }

        return String.join(".", ipString);
    }
}
