package com.prancingdonkey.reconnect;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.mule.api.retry.RetryPolicy;
import org.mule.retry.PolicyStatus;

//<start id="lis_09_working-hours-aware-exhaustible-retry-policy"/>
public class WorkingHoursAwareExhaustibleRetryPolicy 
  implements RetryPolicy
{
    int companyWorkStartHour;
    int companyWorkEndHour;
    int intervalInWorkingHours;
    TimeZone timeZone;
		
    public WorkingHoursAwareExhaustibleRetryPolicy(
      int companyWorkStartHour,
      int companyWorkEndHour, TimeZone timeZone, 
      int intervalInWorkingHours) 
    {
    	this.companyWorkStartHour = companyWorkStartHour; 
    	this.companyWorkEndHour = companyWorkEndHour;
    	this.intervalInWorkingHours = intervalInWorkingHours;
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
    	
    	if (!withinCompanyHours) {
    		return PolicyStatus.policyExhausted(cause);//<co id="lis_09_working-hours-aware-exhaustible-retry-policy_1"/> 
    	}

    	try 
    	{
    		Thread.sleep(intervalInWorkingHours);
    	} catch (InterruptedException e) {
    		throw new RuntimeException(e);
    	}
    	
    	return PolicyStatus.policyOk();
    }
    
}
//<end id="lis_09_working-hours-aware-exhaustible-retry-policy"/>
