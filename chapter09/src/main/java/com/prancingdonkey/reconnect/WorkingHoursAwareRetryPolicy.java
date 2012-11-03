package com.prancingdonkey.reconnect;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.mule.api.retry.RetryPolicy;
import org.mule.retry.PolicyStatus;

//<start id="lis_09_working-hours-aware-retry-policy"/>
public class WorkingHoursAwareRetryPolicy implements RetryPolicy
{
    int companyWorkStartHour;
    int companyWorkEndHour;
    int intervalInWorkingHours;
    int intervalInNonWorkingHours;
    TimeZone timeZone;
		
    public WorkingHoursAwareRetryPolicy(int companyWorkStartHour,
      int companyWorkEndHour, TimeZone timeZone, 
      int intervalInWorkingHours, int intervalInNonWorkingHours) 
    {
    	this.companyWorkStartHour = companyWorkStartHour; 
    	this.companyWorkEndHour = companyWorkEndHour;
    	this.intervalInWorkingHours = intervalInWorkingHours;
    	this.intervalInNonWorkingHours = intervalInNonWorkingHours;
    	this.timeZone = timeZone;
    }

    public PolicyStatus applyPolicy(Throwable cause) 
    {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(new Date());
    	cal.setTimeZone(timeZone);

    	int hour = cal.get(Calendar.HOUR_OF_DAY);
    	boolean withinCompanyHours = 
          (hour >= companyWorkStartHour && hour < companyWorkEndHour);
    	
    	try 
    	{
    	  Thread.sleep(withinCompanyHours ?
    	    intervalInWorkingHours : intervalInNonWorkingHours);//<co id="lis_09_working-hours-aware-retry-policy_1"/>
    	} catch (InterruptedException e) {
    		throw new RuntimeException(e);//<co id="lis_09_working-hours-aware-retry-policy_2"/>
    	}
    	
    	return PolicyStatus.policyOk();
    }
    
}
//<end id="lis_09_working-hours-aware-retry-policy"/>

