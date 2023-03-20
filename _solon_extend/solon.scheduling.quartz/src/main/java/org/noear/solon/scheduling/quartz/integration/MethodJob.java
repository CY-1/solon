package org.noear.solon.scheduling.quartz.integration;

import org.noear.solon.core.BeanWrap;
import org.noear.solon.scheduling.quartz.AbstractJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.Method;

/**
 * 方法运行器（支持非单例）
 *
 * @author noear
 * @since 1.11
 */
public class MethodJob extends AbstractJob {
    private final String jobId;
    private final BeanWrap target;
    private final Method method;
    private final boolean isRunnable;

    public MethodJob(BeanWrap target, Method method) {
        this.target = target;
        this.method = method;
        this.isRunnable = method.getParameterCount() == 0;
        this.jobId = target.getClass().getName() + "::" + method.getName();
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            if (isRunnable) {
                method.invoke(target.get());
            } else {
                method.invoke(target.get(), context);
            }
        } catch (Throwable e) {
            throw new JobExecutionException(e);
        }
    }

    @Override
    public String getJobId() {
        return jobId;
    }
}