import processing.core.PImage;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.lang.Math;
import static java.lang.Math.abs;

public abstract class MobileAnimatedActor
   extends AnimatedActor
{
   public MobileAnimatedActor(String name, Point position, int rate,
      int animation_rate, List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, imgs);
   }

   protected Point nextPosition(WorldModel world, Point dest_pt)
   {
      /*
      int horiz = Integer.signum(dest_pt.x - getPosition().x);
      Point new_pt = new Point(getPosition().x + horiz, getPosition().y);

      if (horiz == 0 || !canPassThrough(world, new_pt))
      {
         int vert = Integer.signum(dest_pt.y - getPosition().y);
         new_pt = new Point(getPosition().x, getPosition().y + vert);

         if (vert == 0 || !canPassThrough(world, new_pt))
         {
            new_pt = getPosition();
         }
      }
      */
      Point this_pt = new Point(getPosition().x, getPosition().y);
      AStar Next = new AStar();
      Point new_pt = Next.AStar(this_pt, dest_pt, world);

      if(new_pt != null)
      { 
         return new_pt;
      }
      
      return dest_pt;
   }

   protected static boolean adjacent(Point p1, Point p2)
   {
      return (p1.x == p2.x && abs(p1.y - p2.y) == 1) ||
         (p1.y == p2.y && abs(p1.x - p2.x) == 1);
   }

   protected abstract boolean canPassThrough(WorldModel world, Point new_pt);
}
