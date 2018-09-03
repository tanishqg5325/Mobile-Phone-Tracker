import java.util.*;

public class RoutingMapTree
{
	Exchange root;
	int depthBaseStation, maxDepth;
	
	public RoutingMapTree()
	{
		root = new Exchange(0);
		depthBaseStation = -1;
		maxDepth = 0;
	}
	
	public boolean containsNode(Exchange root, Exchange e)
	{
		if(root != null)
		{
			if (root.equals(e)) return true;
			int n = root.numChildren();
			for(int i=0; i < n; i++)
			{
				if(containsNode(root.child(i), e))
					return true;
			}
		}
		return false;
	}
	
	public int depth(Exchange e)
	{
		int ans = -1;
		Exchange p = e;
		while(p != null)
		{
			ans++;
			p = p.parent();
		}
		return ans;
	}
	
	public MobilePhone getMobile(int m)
	{
		return root.getMobile(m);
	}
	
	public void switchOn(MobilePhone m, Exchange e)
	{
		if(m == null) throw new RuntimeException("Error - MobilePhone does not exist");
		if(e == null) throw new RuntimeException("Error - Exchange does not exist");
		if(depth(e) != maxDepth)
			throw new RuntimeException("Error - Exchange " + e.id + " is not a base station");
		if(!m.status())
		{
			Exchange ex = m.exchange, p;
			while(ex != null)
			{
				ex.residentSet().Delete(m);
				ex = ex.parent();
			}
			if(depthBaseStation == -1) depthBaseStation = maxDepth;
			m.switchOn();
			m.exchange = e;
			e.residentSet().Insert(m);
			p = e.parent();
			while(p != null)
			{
				p.residentSet().Insert(m);
				p = p.parent();
			}
		}
		else
			throw new RuntimeException("Error - Mobile " + m.number() + " is already on");
	}
	
	public void switchOff(MobilePhone m)
	{
		if(m == null) throw new RuntimeException("Error - MobilePhone does not exist");
		if(m.status)
			m.switchOff();
		else
			throw new RuntimeException("Error - Mobile " + m.number() + " is already off");
	}
	
	public void switchOnMobile(int a, int b)
	{
		MobilePhone m = getMobile(a);
		Exchange e = getExchange(b);
		if(e != null)
		{
			if(m == null)
				m = new MobilePhone(a);
			try
			{
				switchOn(m, e);
				return;
			}
			catch(RuntimeException ex)
			{
				throw ex;
			}
		}
		throw new RuntimeException("Error - Exchange " + b + " does not exist");
	}
	
	public void switchOffMobile(int a)
	{
		MobilePhone mobile = getMobile(a);
		if(mobile != null)
		{
			try
			{
				switchOff(mobile);
			}
			catch(RuntimeException e)
			{
				throw e;
			}
		}
		else
			throw new RuntimeException("Error - Mobile " + a + " does not exist");
	}
	
	public Exchange getExchange(Exchange root, int e)
	{
		if(root != null)
		{
			if(root.id == e) return root;
			int n = root.numChildren();
			Exchange tmp;
			for(int i=0; i < n; i++)
			{
				tmp = getExchange(root.child(i), e);
				if(tmp != null) return tmp;
			}
		}
		return null;
	}
	
	public void addExchange(int a, int b)
	{
		Exchange p = getExchange(a);
		Exchange e = getExchange(b);
		if(e != null)
			throw new RuntimeException("Error - Exchange " + b + " already exists");
		if(p != null)
		{
			if(depthBaseStation == -1 || depth(p) < depthBaseStation)
			{
				e = new Exchange(b);
				e.parent = p;
				p.addChild(e);
				maxDepth = Math.max(maxDepth, depth(p) + 1);
				return;
			}
			throw new RuntimeException("Error - Can't add an exchange to a base station");
		}
		throw new RuntimeException("Error - Parent Exchange " + a + " not found");
	}
	
	public int queryNthChild(int a, int b)
	{
		Exchange e = getExchange(a);
		if(e != null)
		{
			try
			{
				return e.child(b).id;
			}
			catch(RuntimeException ex)
			{
				throw ex;
			}
		}
		throw new RuntimeException("Error - Parent Exchange " + a + " not found");
	}
	
	public int[] queryMobilePhoneSet(int a)
	{
		Exchange e = getExchange(a);
		if(e != null)
		{
			return e.getMobiles();	
		}
		throw new RuntimeException("Error - Parent Exchange " + a + " not found");
	}
	
	public Exchange findPhone(MobilePhone m)
	{
		if(m == null) throw new RuntimeException("Error - No such mobile exists in the network");
		if(!m.status()) throw new RuntimeException("Error - Mobile " + m.number() + " is switched off");
		return m.location();
	}
	
	public Exchange lowestRouter(Exchange a, Exchange b)
	{
		if(a == null || b == null) throw new RuntimeException("Error - Either exchange does not exist");
		if(depth(a) != maxDepth) throw new RuntimeException("Error - Exchange " + a.id + " is not a base station");
		if(depth(b) != maxDepth) throw new RuntimeException("Error - Exchange " + b.id + " is not a base station");
		Exchange t1 = a, t2 = b;
		while(t1 != null && !t1.equals(t2))
		{
			t1 = t1.parent();
			t2 = t2.parent();
		}
		return t1;
	}
	
	public ExchangeList routeCall(MobilePhone a, MobilePhone b)
	{
		if(a == null || b == null) throw new RuntimeException("Error - Either mobile phone does not exist");
		ExchangeList route = new ExchangeList();
		Exchange e1,e2;
		try
		{
			e1 = a.location();
			e2 = b.location();
		}
		catch(RuntimeException e)
		{
			throw e;
		}
		Exchange lca = lowestRouter(e1, e2);
		while(e1 != lca)
		{
			route.Insert(e1);
			e1 = e1.parent();
		}
		route.Insert(lca);
		ExchangeList tmp = new ExchangeList();
		while(e2 != lca)
		{
			tmp.InsertHead(e2);
			e2 = e2.parent();
		}
		for(int i=0; i<tmp.size(); i++)
			route.Insert(tmp.getMember(i));
		return route;
	}
	
	public void movePhone(MobilePhone a, Exchange b)
	{
		if(a == null) throw new RuntimeException("Error - Mobile does not exist");
		if(b == null) throw new RuntimeException("Errror - Exchange does not exist");
		if(depth(b) != maxDepth) throw new RuntimeException("Error - Exchange " + b.id + " is not a base station");
		if(!a.status())	throw new RuntimeException("Error - Mobile " + a.number() + " is switched off");
		switchOff(a);
		switchOn(a,b);
	}
	
	public boolean containsNode(Exchange e)	{	return containsNode(root, e); }
	public Exchange getExchange(int e)	{	return getExchange(root, e);	}
	
	public static boolean isInteger(String s)
	{
	      boolean isValidInteger = false;
	      try
	      {
	         Integer.parseInt(s);
	         isValidInteger = true;
	      }
	      catch (NumberFormatException ex)
	      {
	         // s is not an integer
	      }
	      return isValidInteger;
	}

	public void performAction(String actionMessage)
	{
		String[] query = actionMessage.split("\\s");
		if(query.length == 3 && query[0].equals("addExchange") && isInteger(query[1]) && isInteger(query[2]))
		{
			int a = Integer.parseInt(query[1]);
			int b = Integer.parseInt(query[2]);
			try
			{
				addExchange(a,b);
			}
			catch(RuntimeException e)
			{
				System.out.println(actionMessage + ": " + e.getMessage());
			}
		}
		
		else if(query.length == 3 && query[0].equals("switchOnMobile") && isInteger(query[1]) && isInteger(query[2]))
		{
			int a = Integer.parseInt(query[1]);
			int b = Integer.parseInt(query[2]);
			try
			{
				switchOnMobile(a,b);
			}
			catch(RuntimeException e)
			{
				System.out.println(actionMessage + ": " + e.getMessage());
			}
		}
		
		else if(query.length == 2 && query[0].equals("switchOffMobile") && isInteger(query[1]))
		{
			int a = Integer.parseInt(query[1]);
			try
			{
				switchOffMobile(a);
			}
			catch(RuntimeException e)
			{
				System.out.println(actionMessage + ": " + e.getMessage());
			}
		}
		
		else if(query.length == 3 && query[0].equals("queryNthChild") && isInteger(query[1]) && isInteger(query[2]))
		{
			System.out.print(actionMessage + ": ");
			int a = Integer.parseInt(query[1]);
			int b = Integer.parseInt(query[2]);
			try
			{
				System.out.println(queryNthChild(a,b));
			}
			catch(RuntimeException e)
			{
				System.out.println(e.getMessage());
			}
		}
		
		else if(query.length == 2 && query[0].equals("queryMobilePhoneSet") && isInteger(query[1]))
		{
			System.out.print(actionMessage + ": ");
			int a = Integer.parseInt(query[1]);
			try
			{
				int[] mobileSet = queryMobilePhoneSet(a);
				for(int i=0; i<mobileSet.length-1; i++)
					System.out.print(mobileSet[i] + ", ");
				if(mobileSet.length > 0)
					System.out.print(mobileSet[mobileSet.length-1]);
				System.out.println("");
			}
			catch(RuntimeException e)
			{
				System.out.println(e.getMessage());
			}
		}
		
		else if(query.length == 2 && (query[0].equals("findPhone") || query[0].equals("queryFindPhone")) && isInteger(query[1]))
		{
			int a = Integer.parseInt(query[1]);
			System.out.print("queryFindPhone " + a + ": ");
			MobilePhone m = getMobile(a);
			try
			{
				System.out.println(findPhone(m).id);
			}
			catch(RuntimeException e)
			{
				System.out.println(e.getMessage());
			}
		}
		
		else if(query.length == 3 && (query[0].equals("lowestRouter") || query[0].equals("queryLowestRouter")) && isInteger(query[1]) && isInteger(query[2]))
		{
			int a = Integer.parseInt(query[1]);
			int b = Integer.parseInt(query[2]);
			System.out.print("queryLowestRouter " + a + " " + b + ": ");
			Exchange e1 = getExchange(a), e2 = getExchange(b);
			try
			{
				System.out.println(lowestRouter(e1,e2).id);
			}
			catch(RuntimeException e)
			{
				System.out.println(e.getMessage());
			}
		}
		
		else if(query.length == 3 && (query[0].equals("findCallPath") || query[0].equals("queryFindCallPath")) && isInteger(query[1]) && isInteger(query[2]))
		{
			int a = Integer.parseInt(query[1]);
			int b = Integer.parseInt(query[2]);
			System.out.print("queryFindCallPath " + a + " " + b + ": ");
			MobilePhone m1 = getMobile(a), m2 = getMobile(b);
			try
			{
				ExchangeList route = routeCall(m1,m2);
				int n = route.size();
				for(int i=0; i<n-1; i++)
					System.out.print(route.getMember(i).id + ", ");
				System.out.println(route.getMember(n-1).id);
			}
			catch(RuntimeException e)
			{
				System.out.println(e.getMessage());
			}
		}
		
		else if(query.length == 3 && query[0].equals("movePhone") && isInteger(query[1]) && isInteger(query[2]))
		{
			int a = Integer.parseInt(query[1]);
			int b = Integer.parseInt(query[2]);
			MobilePhone m = getMobile(a);
			Exchange e = getExchange(b);
			try
			{
				movePhone(m,e);
			}
			catch(RuntimeException ex)
			{
				System.out.println(actionMessage + ": " + ex.getMessage());
			}
		}
		
		else
			System.out.println(actionMessage + ": Error - Invalid Input");
	}
}
