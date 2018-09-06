import java.util.*;

public class MobilePhone
{
	int id;
	boolean status;
	Exchange exchange;
	
	public MobilePhone(int number)
	{
		id = number;
		status = false;
		exchange = null;
	}
	
	public boolean equals(MobilePhone m)
	{
		if(this == m) return true;
		if(m == null) return false;
		return (id == m.id);
	}
	
	public int number()
	{
		return id;
	}
	
	public boolean status()
	{
		return status;
	}
	
	public void switchOn()
	{
		status = true;
	}
	
	public void switchOff()
	{
		status = false;
	}
	
	public Exchange location()
	{
		if(status) return exchange;
		throw new RuntimeException("Error - Mobile phone with identifier " + number() + " is currently switched off");
	}
}
