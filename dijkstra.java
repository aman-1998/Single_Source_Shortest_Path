import java.util.*;
import java.lang.*;

class node
{
	int v,dis;
	node(int v,int dis)
	{
		this.v=v;
		this.dis=dis;
	}
}
class dij
{
	int heap_size;
	node min=null;
	dij(int i)
	{
		heap_size=i;
	}
	node[] build_heap(node[] A)
	{
		int i;
		for(i=heap_size/2;i>=1;i--)
			A=min_heapify(A,i);
		return A;
	}
	node[] min_heapify(node[] A,int i)
	{
		int l,r,smallest;
		node temp=null;
		l=2*i;
		r=2*i+1;
		if(l<=heap_size && A[l].dis<A[i].dis)
			smallest=l;
		else
			smallest=i;
		if(r<=heap_size && A[r].dis<A[smallest].dis)
			smallest=r;
		if(i!=smallest)
		{
			temp=A[i];
			A[i]=A[smallest];
			A[smallest]=temp;
			
			A=min_heapify(A,smallest);
		}
		return A;
	}
	node[] delete_min(node[] A)
	{
		min=A[1];
		A[1]=A[heap_size];
		heap_size--;
		A=min_heapify(A,1);
		return A;
	}
	node[] decrease_key(node[] A,int i,int key)
	{
		node temp=null;
		if(i<1 || i>heap_size || key>A[i].dis)
		{
			System.out.println("\nError");
			System.exit(0);
		}
		A[i].dis=key;
		while(i>1 && A[i].dis<A[i/2].dis)
		{
			temp=A[i/2];
			A[i/2]=A[i];
			A[i]=temp;
			i=i/2;
		}
		return A;
	}
	void dijkstra(int[][] cost,int n,int s)
	{
		int i,j;
		node[] A=new node[n+1];
		for(i=1;i<=n;i++)
			A[i]=new node(i,999);
		A[s].dis=0;
		A=build_heap(A);
		for(i=1;i<=n;i++)
		{
			A=delete_min(A);
			System.out.println("The shortest distance from "+s+" to "+min.v+" is "+min.dis);
			for(j=1;j<=heap_size;j++)
			{
				if(cost[min.v][A[j].v]!=0 && cost[min.v][A[j].v]!=999)
				{
					if(min.dis+cost[min.v][A[j].v]<A[j].dis)
						A=decrease_key(A,j,min.dis+cost[min.v][A[j].v]);
				}
			}
		}
	}
	public static void main(String args[])
	{
		int i,j;
		Scanner in=new Scanner(System.in);
		System.out.print("Enter no. of vertices: ");
		int n=in.nextInt();
		int[][] cost=new int[n+1][n+1];
		for(i=1;i<=n;i++)
		{
			for(j=1;j<=n;j++)
			{
				System.out.print("Enter cost["+i+"]["+j+"] : ");
				cost[i][j]=in.nextInt();
			}
		}
		System.out.print("Directed weight Matrix: \n");
		for(i=1;i<=n;i++)
		{
			for(j=1;j<=n;j++)
			{
				if(cost[i][j]==999)
					System.out.print((char)8734+" ");
				else
					System.out.print(cost[i][j]+" ");
			}
			System.out.println();
		}
		System.out.print("Enter the source vertex: ");
		int s=in.nextInt();
		dij x=new dij(n);
		x.dijkstra(cost,n,s);
	}
}
