package com.ganjiangps.huochetou.job.jobDetail;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.ganjiangps.huochetou.job.BaseJob;
import com.ganjiangps.huochetou.model.UserSpreadQuery;
import com.ganjiangps.huochetou.service.UserSpreadQueryService;

@Component
public class UserSpreadQueryApplicationJob implements BaseJob{
	
	
	  @Resource
	  UserSpreadQueryService  userSpreadQueryService;
		
	  @Override
	  public void execute(JobExecutionContext context) throws JobExecutionException {
		  Date date = new Date();
		  List<UserSpreadQuery>  lists = userSpreadQueryService.selectList(null);
		  if(lists.size()>0){
			  for (UserSpreadQuery userSpreadQuery : lists) {
				  if(compareDate(date,userSpreadQuery.getExpirytime())){
					  userSpreadQuery.setRecommendstatus(0);
					  userSpreadQueryService.updateById(userSpreadQuery);
					  System.out.println("执行定时器");
				  }else{
					  userSpreadQuery.setRecommendstatus(1);
					  userSpreadQueryService.updateById(userSpreadQuery);
					  System.out.println("执行定时器");
				  }
			}
		  }
	  }
	  
	  
	  /**
	   * 比较两个时间的大小
	   * @param @param dt1  当前时间
	   * @param @param dt2 数据库的有效时间
	   * @param @return
	   * @return int
	   * @author jiangxueyou
	   */
	  public static boolean compareDate(Date dt1,Date dt2){
          if (dt2.getTime() > dt1.getTime()) {
              return false;
          }
          if (dt2.getTime() <= dt1.getTime()) {
        	  return true;
          }
          return true;
	  }

}
