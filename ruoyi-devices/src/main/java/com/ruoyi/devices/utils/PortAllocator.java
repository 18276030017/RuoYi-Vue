package com.ruoyi.devices.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lfx
 * @program ruoyi
 * @description 动态端口分配器
 * @date 2024/06/12
 */
public class PortAllocator {
    /**
     * TCP/IP端口共有65535个，分为两类：已知端口、动态/私有端口。
     * 已知端口是指已经被IANA（互联网编号分配局）分配的端口，用于特定的服务，如HTTP（80端口）、HTTPS（443端口）、FTP（21端口）等。
     * 动态/私有端口是指1024-49151之间的端口，可以由任何应用程序使用。
     */
    private static final int MAX_PORT = 49151;
    private static final int MIN_PORT = 1024;
    private static final Set<Integer> RESERVED_PORTS = Collections.unmodifiableSet(
            IntStream.of(
                    80, 443, 21, 22, 23, 25, 53, 67, 68, 69, 80, 110, 143, 161, 162, 179, 389, 443, 445, 465, 587, 636, 993, 995, 1099, 1433, 1521, 1883, 1884, 1885, 1886, 1887, 1888, 1889, 3306, 3389, 5432, 5555, 5900, 5901, 5902, 5903, 5904, 5905, 5906,5907, 5908, 5909, 5910,
                    8080, 8081, 8082, 8083, 8084, 8085, 8090, 8443, 8444, 8445, 8446, 27017
                    ) // 这里添加其他固定使用的端口
                    .boxed()
                    .collect(Collectors.toSet()));

    private final Set<Integer> usedPorts = Collections.synchronizedSet(new HashSet<>());
    private final Random rand = new Random();
    public int allocatePort() {
        while (true) {
            int port = rand.nextInt(MAX_PORT - MIN_PORT + 1) + MIN_PORT;
            if (!RESERVED_PORTS.contains(port)) {
                usedPorts.add(port);
                return port;
            }
        }
    }
}
