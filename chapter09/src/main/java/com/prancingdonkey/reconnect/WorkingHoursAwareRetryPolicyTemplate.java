package com.prancingdonkey.reconnect;

import java.util.TimeZone;

import org.mule.api.retry.RetryPolicy;
import org.mule.retry.policies.AbstractPolicyTemplate;

//<start id="lis_09_working-hours-aware-retry-policy-template"/>
public class WorkingHoursAwareRetryPolicyTemplate 
  extends AbstractPolicyTemplate 
{
	int companyWorkStartHour;
	int companyWorkEndHour;
	int intervalInWorkingHours;
	int intervalInNonWorkingHours;
	TimeZone timeZone;

	public RetryPolicy createRetryInstance() 
	{
		return new WorkingHoursAwareRetryPolicy(
				companyWorkStartHour, companyWorkEndHour, timeZone, 
				intervalInWorkingHours, intervalInNonWorkingHours);
	}

	public void setCompanyWorkStartHour(int companyWorkStartHour) 
	{
		this.companyWorkStartHour = companyWorkStartHour;
	}

	public void setCompanyWorkEndHour(int companyWorkEndHour) 
	{
		this.companyWorkEndHour = companyWorkEndHour;
	}

	public void setIntervalInWorkingHours(int intervalInWorkingHours) 
	{
		this.intervalInWorkingHours = intervalInWorkingHours;
	}

	public void setIntervalInNonWorkingHours
		(int intervalInNonWorkingHours) 
	{
		this.intervalInNonWorkingHours = intervalInNonWorkingHours;
	}

	public void setTimeZone(TimeZone timeZone) 
	{
		this.timeZone = timeZone;
	}

}
//<end id="lis_09_working-hours-aware-retry-policy-template"/>
