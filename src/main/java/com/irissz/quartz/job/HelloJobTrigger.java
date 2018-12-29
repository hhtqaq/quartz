package com.irissz.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.KeyMatcher;

import com.irissz.quartz.listener.MyJobListener;
import com.irissz.quartz.listener.MyTriggerListener;

public class HelloJobTrigger implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
			System.out.println("正在执行任务"+new Date());
	}

	public static void main(String[] args) throws Exception{
		//创建调度器
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		//绑定任务
		JobDetail jobDetail = JobBuilder.newJob(HelloJobTrigger.class)
		.withIdentity("job1", "group1")
		.build();
		// 创建触发器
		SimpleTrigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("trigger1")
		.startNow()
		.withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatSecondlyForever(2))
		.build();
		//创建并注册一个全局的触发器TriggerListener：
		//1创建一个全局的监听管理器
		scheduler.getListenerManager().addTriggerListener(new MyTriggerListener(),EverythingMatcher.allTriggers());
		//创建并注册一个局部的触发器TriggerListener：
		scheduler.getListenerManager().addTriggerListener(new MyTriggerListener(),KeyMatcher.keyEquals(TriggerKey.triggerKey("trigger1")));
		//绑定触发器任务
		scheduler.scheduleJob(jobDetail, trigger);
		//开始
		scheduler.start();
	}
	
}
