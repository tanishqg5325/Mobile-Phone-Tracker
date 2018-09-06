import java.util.*;

public class MobilePhoneSet
{
	Myset mobileSet;
	
	public MobilePhoneSet()
	{
		mobileSet = new Myset();
	}
	
	public void Insert(MobilePhone mobile)
	{
		try { mobileSet.Insert(mobile); }
		catch(RuntimeException ex)
		{
			throw new RuntimeException("Error - Mobile phone with identifier " + mobile.number() + " is already present in this resident set");
		}
	}
	
	public void Delete(MobilePhone mobile)
	{
		try { mobileSet.Delete(mobile); }
		catch(RuntimeException e)
		{
			throw new RuntimeException("Error - Mobile phone with identifier " + mobile.number() + " is not present in this resident set");
		}
	}
	
	public MobilePhone getMobile(int m)
	{
		int n = mobileSet.size();
		Object[] objs =  mobileSet.getItems();
		MobilePhone mobile;
		for(int i=0; i<n; i++)
		{
			mobile = (MobilePhone)objs[i];
			if(mobile.number() == m) return mobile;
		}
		return null;
	}
	
	public int[] getMobiles()
	{
		int n = mobileSet.size();
		Vector<Integer> v = new Vector<Integer>();
		MobilePhone m;
		Object[] objs =  mobileSet.getItems();
		for(int i=0; i<n; i++)
		{
			m = (MobilePhone)objs[i];
			if(m.status) v.add(m.number());
		}
		n = v.size();
		int[] mobiles = new int[n];
		for(int i=0; i<n; i++)
			mobiles[i] = v.get(i);
		return mobiles;
	}
}
