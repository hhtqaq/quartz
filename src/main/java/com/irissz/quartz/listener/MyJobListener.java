package com.irissz.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class MyJobListener implements JobListener{

	@Override
	public String getName() {
		String name = getClass().getSimpleName();
		System.out.println("获取任务名称："+name);
		return name;//这里必须返回一个值，否则会报出JobListener name cannot be empty.异常
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		String jobname=context.getJobDetail().getKey().getName();
		System.out.println(jobname+"     Scheduler在jobDetail将要被执行的方法。");
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		String jobname=context.getJobDetail().getKey().getName();
		System.out.println(jobname+"    Scheduler在JobDetail即将被执行，但又被TriggerListener否决时会调用该方法。	");
		
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		String jobname=context.getJobDetail().getKey().getName();
		System.out.println(jobname+"    Scheduler在JobDetail被执行之后调用的方法");
		
	}

}
