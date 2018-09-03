import java.util.*;

public class Myset
{
	Node head;
	static class Node
	{
		Object obj;
		Node next, prev;
		Node(Object o)
		{
			obj = o;
			next = prev = null;
		}
	}
	
	public boolean IsEmpty()
	{
		return (head==null);
	}
	
	public boolean IsMember(Object o)
	{
		Node temp = head;
		while(temp != null)
		{
			if(temp.obj.equals(o))
				return true;
			temp = temp.next;
		}
		return false;
	}
	
	public void Insert(Object o)
	{
		if(IsMember(o)) return;
		Node tmp = new Node(o);
		if(head==null)
		{
			head = tmp;
			return;
		}
		Node n = head;
		while(n.next != null)
			n = n.next;
		tmp.prev = n;
		n.next = tmp;
	}
	
	public void Delete(Object o)
	{
		Node temp = head;
		while(temp != null)
		{
			if(temp.obj.equals(o))
			{
				Node t = temp.next;
				if(temp.prev != null) temp.prev.next = t;
				else head = t;
				if(t != null) t.prev = temp.prev;
				return;
			}
			temp = temp.next;
		}
		throw new RuntimeException("Error - Given object is not present in set");
	}
	
	public Myset Union(Myset a)
	{
		Myset union = new Myset();
		Node temp = head;
		while(temp != null)
		{
			union.Insert(temp.obj);
			temp = temp.next;
		}
		temp = a.head;
		while(temp != null)
		{
			if(!union.IsMember(temp.obj)) union.Insert(temp.obj);
			temp = temp.next;
		}
		return union;
	}
	
	public Myset Intersection(Myset a)
	{
		Myset intersection = new Myset();
		Node temp = head;
		while(temp != null)
		{
			if(a.IsMember(temp.obj)) intersection.Insert(temp.obj);
			temp = temp.next;
		}
		return intersection;
	}
	
	public int size()
	{
		Node tmp = head;
		int count = 0;
		while(tmp != null)
		{
			count++;
			tmp = tmp.next;
		}
		return count;
	}
	
	public Object[] getItems()
	{
		int n = this.size();
		Object[] objs = new Object[n];
		Node tmp = head;
		for(int i=0; i<n; i++)
		{
			objs[i] = tmp.obj;
			tmp = tmp.next;
		}
		return objs;
	}

	/*public static void main(String[] args)
	{
		Myset a = new Myset();
		System.out.println(a.IsEmpty());
		a.Insert(1529); a.Insert(99); a.Insert(455); a.Insert(99);
		System.out.println(a.IsEmpty());
		System.out.println(a.size());
		System.out.println(a.IsMember(455));
		System.out.println(a.getItems()[1]);
		//a.Delete(15);
		System.out.println(a.getItems()[1]);
		Myset b = new Myset(); //b.Insert(99); b.Insert(656); b.Insert(34); b.Insert(1529);
		Myset c = b.Union(a);
		c = b.Intersection(a);
		System.out.println(c.size());
	}*/
}
