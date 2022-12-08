package com.transsion.framework.tango.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class Threads {
    private static final Logger logger = LoggerFactory.getLogger(Threads.class);

    private static volatile Manager manager = new Manager();

    public static ThreadGroupManager forGroup(String name) {
        return manager.getThreadGroupManager(name);
    }

    public static ThreadPoolManager forPool() {
        return manager.getThreadPoolManager();
    }

    static class Manager implements Thread.UncaughtExceptionHandler {
        private final Map<String, ThreadGroupManager> groupManagers = new LinkedHashMap<String, ThreadGroupManager>();

        private final ThreadPoolManager threadPoolManager;

        public Manager() {
            threadPoolManager = new ThreadPoolManager(this);
            ShutdownHookManager.get().addShutdownHook(() -> shutdownAll(), 100);
        }

        public void shutdownAll() {
            for (ThreadGroupManager manager : groupManagers.values()) {
                manager.shutdown();
            }

            threadPoolManager.shutdownAll();
        }

        @Override
        public void uncaughtException(Thread t, Throwable e) {

        }

        public ThreadPoolManager getThreadPoolManager() {
            return threadPoolManager;
        }

        public ThreadGroupManager getThreadGroupManager(String name) {
            ThreadGroupManager groupManager = groupManagers.get(name);

            if (groupManager == null) {
                synchronized (this) {
                    groupManager = groupManagers.get(name);

                    if (groupManager != null && !groupManager.isActive()) {
                        groupManagers.remove(name);
                        groupManager = null;
                    }

                    if (groupManager == null) {
                        groupManager = new ThreadGroupManager(this, name);
                        groupManagers.put(name, groupManager);
                    }
                }
            }

            return groupManager;
        }
    }

    public static class ThreadGroupManager {
        private DefaultThreadFactory factory;
        private ThreadGroup threadGroup;
        private boolean active;
        private boolean deamon;

        public ThreadGroupManager(Thread.UncaughtExceptionHandler handler, String name) {
            threadGroup = new ThreadGroup(name);
            factory = new DefaultThreadFactory(threadGroup);
            factory.setUncaughtExceptionHandler(handler);
            active = true;
            deamon = true;
        }

        public void awaitTermination(long time, TimeUnit unit) {
            long remaining = unit.toNanos(time);

            while (remaining > 0) {
                int len = threadGroup.activeCount();
                Thread[] activeThreads = new Thread[len];
                int num = threadGroup.enumerate(activeThreads);
                boolean anyAlive = false;

                for (int i = 0; i < num; i++) {
                    Thread thread = activeThreads[i];

                    if (thread.isAlive()) {
                        anyAlive = true;
                        break;
                    }
                }

                if (anyAlive) {
                    long slice = 1000 * 1000L;
                    // wait for 1 ms
                    LockSupport.parkNanos(slice);
                    remaining -= slice;
                } else {
                    break;
                }
            }
        }

        public ThreadGroup getThreadGroup() {
            return threadGroup;
        }

        public boolean isActive() {
            return active;
        }

        public ThreadGroupManager nonDaemon() {
            deamon = false;
            return this;
        }

        public void shutdown() {
            int len = threadGroup.activeCount();
            Thread[] activeThreads = new Thread[len];
            int num = threadGroup.enumerate(activeThreads);

            for (int i = 0; i < num; i++) {
                Thread thread = activeThreads[i];

                if (thread instanceof RunnableThread) {
                    ((RunnableThread) thread).shutdown();
                } else if (thread.isAlive()) {
                    thread.interrupt();
                }
            }

            active = false;
        }

        public Thread start(Runnable runnable) {
            return start(runnable, deamon);
        }

        public Thread start(Runnable runnable, boolean deamon) {
            Thread thread = factory.newThread(runnable);

            logger.info("start thread " + thread.getName());

            thread.setDaemon(deamon);
            thread.start();
            return thread;
        }
    }

    static class RunnableThread extends Thread {
        private Runnable target;

        public RunnableThread(ThreadGroup threadGroup, Runnable target, String name, UncaughtExceptionHandler handler) {
            super(threadGroup, target, name);

            this.target = target;

            setDaemon(true);
            setUncaughtExceptionHandler(handler);

            if (getPriority() != Thread.NORM_PRIORITY) {
                setPriority(Thread.NORM_PRIORITY);
            }
        }

        public Runnable getTarget() {
            return target;
        }

        @Override
        public void run() {
            super.run();
        }

        public void shutdown() {
            if (target instanceof Task) {
                ((Task) target).shutdown();
            } else {
                interrupt();
            }
        }
    }

    static class DefaultThreadFactory implements ThreadFactory {
        private ThreadGroup threadGroup;

        private String name;

        private AtomicInteger index = new AtomicInteger();

        private Thread.UncaughtExceptionHandler handler;

        public DefaultThreadFactory(String name) {
            threadGroup = new ThreadGroup(name);
            this.name = name;
        }

        public DefaultThreadFactory(ThreadGroup threadGroup) {
            this.threadGroup = threadGroup;
            name = threadGroup.getName();
        }

        public String getName() {
            return name;
        }

        @Override
        public Thread newThread(Runnable r) {
            int nextIndex = index.getAndIncrement(); // always increase by one
            String threadName;

            if (r instanceof Task) {
                threadName = name + "-" + ((Task) r).getName();
            } else {
                threadName = name + "-" + nextIndex;
            }

            return new RunnableThread(threadGroup, r, threadName, handler);
        }

        public void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
            this.handler = handler;
        }
    }

    public interface Task extends Runnable {
        String getName();

        void shutdown();
    }

    public static class ThreadPoolManager {
        private Thread.UncaughtExceptionHandler handler;
        private Map<String, ExecutorService> services = new LinkedHashMap<String, ExecutorService>();

        public ThreadPoolManager(Thread.UncaughtExceptionHandler handler) {
            this.handler = handler;
        }

        public ExecutorService getBoundedQueueThreadPool(String name, int maxPoolSize, int queueSize) {
            ExecutorService service = services.get(name);

            if (service != null && service.isShutdown()) {
                services.remove(name);
                service = null;
            }

            if (service == null) {
                synchronized (this) {
                    service = services.get(name);

                    if (service == null) {
                        DefaultThreadFactory factory = newThreadFactory(name);
                        service = new ThreadPoolExecutor(
                                1, maxPoolSize,
                                60L, TimeUnit.SECONDS,
                                new ArrayBlockingQueue<>(queueSize),
                                factory);
                        services.put(name, service);
                    }
                }
            }

            return service;
        }

        public ExecutorService getCachedThreadPool(String name) {
            ExecutorService service = services.get(name);

            if (service != null && service.isShutdown()) {
                services.remove(name);
                service = null;
            }

            if (service == null) {
                synchronized (this) {
                    service = services.get(name);

                    if (service == null) {
                        DefaultThreadFactory factory = newThreadFactory(name);
                        service = Executors.newCachedThreadPool(factory);

                        services.put(name, service);
                    }
                }
            }

            return service;
        }

        public ExecutorService getFixedThreadPool(String name, int nThreads) {
            ExecutorService service = services.get(name);

            if (service != null && service.isShutdown()) {
                services.remove(name);
                service = null;
            }

            if (service == null) {
                synchronized (this) {
                    service = services.get(name);

                    if (service == null) {
                        DefaultThreadFactory factory = newThreadFactory(name);
                        service = Executors.newFixedThreadPool(nThreads, factory);

                        services.put(name, service);
                    }
                }
            }

            return service;
        }

        public ScheduledExecutorService getScheduledThreadPool(String name, int nThreads) {
            ExecutorService service = services.get(name);

            if (service != null && service.isShutdown()) {
                services.remove(name);
                service = null;
            }

            if (service == null) {
                synchronized (this) {
                    service = services.get(name);

                    if (service == null) {
                        DefaultThreadFactory factory = newThreadFactory(name);
                        service = Executors.newScheduledThreadPool(nThreads, factory);

                        services.put(name, service);
                    }
                }
            }

            return (ScheduledExecutorService) service;
        }

        DefaultThreadFactory newThreadFactory(String name) {
            DefaultThreadFactory factory = new DefaultThreadFactory(name);

            factory.setUncaughtExceptionHandler(handler);
            return factory;
        }

        public void shutdownAll() {
            for (ExecutorService service : services.values()) {
                service.shutdown();
            }

            boolean allTerminated = false;
            int count = 100;

            while (!allTerminated && count-- > 0) {
                try {
                    for (ExecutorService service : services.values()) {
                        if (!service.isTerminated()) {
                            service.awaitTermination(10, TimeUnit.MILLISECONDS);
                        }
                    }

                    allTerminated = true;
                } catch (InterruptedException e) {
                    // ignore it
                }
            }
        }
    }
}
