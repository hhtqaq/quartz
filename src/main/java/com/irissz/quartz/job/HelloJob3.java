package com.irissz.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 具体任务类
 * @author Administrator
 *
 */
@PersistJobDataAfterExecution     //多次调用Job时，会对Job进行持久化，保存一个数据的信息
public class HelloJob3 implements Job{
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//获取JobKey
		JobKey key = context.getJobDetail().getKey();
		//获取触发器开始时间
		System.out.println("触发器开始时间"+context.getTrigger().getStartTime());
		System.out.println("触发器结束时间"+context.getTrigger().getEndTime());
		
	}
	public static void main(String[] args) throws Exception {
		//创建任务调度器
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		//创建Job实例
		JobDetail jobDetail = JobBuilder.newJob(HelloJob3.class)
				.withIdentity("job1", "group1")
				.build();
		//创建触发器
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.endAt(new Date())
				.build();
		//关联
		scheduler.scheduleJob(jobDetail, trigger);
		//开始
		scheduler.start();
		
	}  
}
