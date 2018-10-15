package com.irissz.quartz.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

/**
 * 具体任务类
 * @author Administrator
 *
 */
@PersistJobDataAfterExecution     //多次调用Job时，会对Job进行持久化，保存一个数据的信息
public class HelloJob implements Job{
	
	private int day;
	public void setDay(int day) {
		this.day=day;
	}
	public HelloJob() {
		System.out.println("我是HelloJob  构造方法");
	}
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("给当年暗恋女神发一封匿名贺卡  暗恋你的第"+day+"天"+new Date());
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
	}
}
