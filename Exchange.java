import java.util.*;

public class Exchange
{
	int id;
	Exchange parent;
	ExchangeList children;
	MobilePhoneSet residentSet;
	
	public Exchange(int number)
	{
		id = number;
		parent = null;
		children = new ExchangeList();
		residentSet = new MobilePhoneSet();
	}
	
	public boolean equals(Exchange e)
	{
		if(this == e) return true;
		if(e == null) return false;
		return (id == e.id);
	}
	
	public Exchange parent()
	{
		return parent;
	}
	
	public int numChildren()
	{
		return children.size();
	}
	
	public Exchange child(int i)
	{
		try
		{
			return children.getMember(i);
		}
		catch(RuntimeException e)
		{
			throw e;
		}
	}
	
	public boolean isRoot()
	{
		return (parent == null);
	}
	
	public void addChild(Exchange e)
	{
		children.Insert(e);
	}
	
	public int[] getMobiles()
	{
		return residentSet.getMobiles();
	}
	
	public MobilePhone getMobile(int m)
	{
		return residentSet.getMobile(m);
	}
	
	public RoutingMapTree subtree(int i)
	{
		RoutingMapTree tree = new RoutingMapTree();
		try
		{
			tree.root = this.child(i);
			return tree;
		}
		catch(RuntimeException e)
		{
			throw e;
		}
	}
	
	public MobilePhoneSet residentSet()
	{
		return residentSet;
	}
}
