package com.irissz.quartz.main;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.irissz.quartz.job.HelloJob2;

public class HelloScheduler2 {

	public static void main(String[] args) throws Exception {
		//工厂获取Scheduler对象
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		//创建任务实例 与任务类完成绑定
		JobDetail jobDetail = JobBuilder.newJob(HelloJob2.class)
				.withIdentity("job1", "group1")
				.usingJobData("message", "ss")
				.usingJobData("hh", 1)
				.build();
		
		//创建触发器
		Trigger trigger=TriggerBuilder.newTrigger()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatSecondlyForever(1))
				.usingJobData("message", "你好")
				.startNow()
				.build();
		//绑定任务实例 与 触发器
		scheduler.scheduleJob(jobDetail, trigger);
		//开始
		scheduler.start();
	}
}
