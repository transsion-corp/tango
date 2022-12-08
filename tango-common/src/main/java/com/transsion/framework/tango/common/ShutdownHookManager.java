package com.transsion.framework.tango.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author mengqi.lv
 * @Date 2022/4/7
 * @Version 1.0
 **/
public class ShutdownHookManager {
    private static final Logger logger = LoggerFactory.getLogger(ShutdownHookManager.class);

    private static final ShutdownHookManager MGR = new ShutdownHookManager();
    private Set<HookEntry> hooks = Collections.synchronizedSet(new HashSet());
    private AtomicBoolean shutdownInProgress = new AtomicBoolean(false);

    public static ShutdownHookManager get() {
        return MGR;
    }

    private ShutdownHookManager() {
    }

    List<Runnable> getShutdownHooksInOrder() {
        ArrayList list;
        synchronized(MGR.hooks) {
            list = new ArrayList(MGR.hooks);
        }

        Collections.sort(list, (Comparator<HookEntry>) (o1, o2) -> o2.priority - o1.priority);
        List<Runnable> ordered = new ArrayList();
        Iterator iter = list.iterator();

        while(iter.hasNext()) {
            HookEntry entry = (HookEntry)iter.next();
            ordered.add(entry.hook);
        }

        return ordered;
    }

    public void addShutdownHook(Runnable shutdownHook, int priority) {
        if (shutdownHook == null) {
            throw new IllegalArgumentException("shutdownHook cannot be NULL");
        } else if (this.shutdownInProgress.get()) {
            throw new IllegalStateException("Shutdown in progress, cannot add a shutdownHook");
        } else {
            this.hooks.add(new HookEntry(shutdownHook, priority));
        }
    }

    public boolean removeShutdownHook(Runnable shutdownHook) {
        if (this.shutdownInProgress.get()) {
            throw new IllegalStateException("Shutdown in progress, cannot remove a shutdownHook");
        } else {
            return this.hooks.remove(new HookEntry(shutdownHook, 0));
        }
    }

    public boolean hasShutdownHook(Runnable shutdownHook) {
        return this.hooks.contains(new HookEntry(shutdownHook, 0));
    }

    public boolean isShutdownInProgress() {
        return this.shutdownInProgress.get();
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ShutdownHookManager.MGR.shutdownInProgress.set(true);
            Iterator iter = ShutdownHookManager.MGR.getShutdownHooksInOrder().iterator();

            while(iter.hasNext()) {
                Runnable hook = (Runnable)iter.next();

                try {
                    hook.run();
                } catch (Throwable throwable) {
                    logger.error("Shutdown hook error.", throwable);
                }
            }

        }));
    }

    private static class HookEntry {
        Runnable hook;
        int priority;

        public HookEntry(Runnable hook, int priority) {
            this.hook = hook;
            this.priority = priority;
        }

        @Override
        public int hashCode() {
            return this.hook.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            boolean eq = false;
            if (obj != null && obj instanceof HookEntry) {
                eq = this.hook == ((HookEntry)obj).hook;
            }

            return eq;
        }
    }
}
