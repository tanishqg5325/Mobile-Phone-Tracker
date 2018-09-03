import java.util.*;

public class ExchangeList
{
	Node head;
	static class Node
	{
		Exchange exchange;
		Node next, prev;
		Node(Exchange e)
		{
			exchange = e;
			next = prev = null;
		}
	}
	
	public boolean IsEmpty()
	{
		return (head==null);
	}
	
	public boolean IsMember(Exchange e)
	{
		Node temp = head;
		while(temp != null)
		{
			if(temp.exchange.equals(e))
				return true;
			temp = temp.next;
		}
		return false;
	}
	
	public void Insert(Exchange e)
	{
		Node tmp = new Node(e);
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
	
	public void InsertHead(Exchange e)
	{
		Node tmp = new Node(e);
		tmp.next = head;
		if(head != null)
			head.prev = tmp;
		head = tmp;
	}
	
	public void Delete(Exchange e)
	{
		Node temp = head;
		while(temp != null)
		{
			if(temp.exchange.equals(e))
			{
				Node t = temp.next;
				if(temp.prev != null) temp.prev.next = t;
				else head = t;
				if(t != null) t.prev = temp.prev;
				return;
			}
			temp = temp.next;
		}
		throw new RuntimeException("Error - Exchange " + e.id + " is not present in list");
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
	
	public Exchange getMember(int i)
	{
		if(this.size() > i && i>=0)
		{
			Node temp = head;
			for(int j=0; j<i; j++)
				temp = temp.next;
			return temp.exchange;
		}
		throw new RuntimeException("Error - Index out of bound");
	}
}
