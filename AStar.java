import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class AStar
{
   public static Point AStar(Point start, Point goal, WorldModel world)
   {
      List<Node> closed = new ArrayList<Node>();
      OrderedList<Node> open = new OrderedList<Node>();

      Node startingNode = new Node(start, 0, h_cost(start, goal), null);
      int start_g_score = startingNode.g;
      int start_f_score = startingNode.g + startingNode.h;
      open.insert(startingNode, start_f_score);

      while(open.size() > 0)
      {
         Node current = open.head().item;
	 if(current.point.x == goal.x && current.point.y == goal.y)
   	 {
	    return reconstruct_path(current);
	 }
   	 closed.add(current);
 	 open.remove(current);

  	 for(Node neighbor : neighbor_nodes(current, goal, world))
	 {
	    if(closed.contains(neighbor)) { continue; }

            int temp_g_score = current.g + 1;
	    if(!open.contains(neighbor) || temp_g_score < neighbor.g)
	    {               
	       neighbor.setG(temp_g_score);
	       int temp_f_score = neighbor.g + h_cost(neighbor.point, goal);
	       
	       if(!open.contains(neighbor))
	       {
                  neighbor.previous = current;
		  open.insert(neighbor, temp_f_score);
	       }
	    }
	 }
      }
      return null;
   }

   public static Point reconstruct_path(Node current)
   {
      List<Node> total_path = new LinkedList<Node>();
      while(current.previous.previous != null)
      {
	 current = current.previous;
         total_path.add(0, current);
      }

      return total_path.get(0).point;
   }

   public static int h_cost(Point p1, Point p2)
   {
      return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
   }

   public static List<Node> neighbor_nodes(Node node, Point goal, WorldModel 
      world)
   {
      List<Node> neighbors = new ArrayList<Node>();
      
      Point p1 = new Point(node.point.x - 1, node.point.y);
      if((world.withinBounds(p1) && !world.isOccupied(p1)) || isGoal(p1, goal))
      {
         Node n1 = new Node(p1, node.g + 1, h_cost(p1, goal), node);
         neighbors.add(n1);
      }

      Point p2 = new Point(node.point.x + 1, node.point.y);
      if((world.withinBounds(p2) && !world.isOccupied(p2)) || isGoal(p2, goal))
      {      
         Node n2 = new Node(p2, node.g + 1, h_cost(p2, goal), node);
         neighbors.add(n2);
      }

      Point p3 = new Point(node.point.x, node.point.y - 1);
      if((world.withinBounds(p3) && !world.isOccupied(p3)) || isGoal(p3, goal))
      {      
         Node n3 = new Node(p3, node.g + 1, h_cost(p3, goal), node);
         neighbors.add(n3);
      }

      Point p4 = new Point(node.point.x, node.point.y + 1);
      if((world.withinBounds(p4) && !world.isOccupied(p4)) || isGoal(p4, goal))
      {      
         Node n4 = new Node(p4, node.g + 1, h_cost(p4, goal), node);
         neighbors.add(n4);
      }
      return neighbors;
   }

   public static boolean isGoal(Point pt, Point goal)
   {
      return (pt.x == goal.x && pt.y == goal.y);
   }

   public static class Node
   {
      public Point point;
      public int g;
      public int h;
      public Node previous;
   
      public Node(Point pt, int g_value, int h_value, Node previous)
      {
         this.point = pt;
         this.g = g_value;
         this.h = h_value;
         this.previous = previous;
      }

      public void setG(int g)
      {
         this.g = g;
      }

      public void setH(int h)
      {
         this.h = h;
      }

      public boolean equals(Object that)
      {
         if(this == that)
         {
            return true;
         }
         else if(!(that instanceof Node))
         {
            return false;
         }
         else
         {
            return ((this.point.x == ((Node) that).point.x) && (this.point.y == 
               ((Node) that).point.y));
         }
      }
   }
}
