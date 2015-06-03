import processing.core.PImage;
import java.util.List;

public class Dragon 
   extends Miner
{
   public Dragon(String name, Point position, int rate, int animation_rate,
      int resource_limit, List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, resource_limit,
         0, Miner.class, imgs);
   }

   public String toString()
   {
      return String.format("dragon %s %d %d %d %d %d", getName(),
         getPosition().x, getPosition().y, getResourceLimit(),
         getRate(), getAnimationRate());
   }

   protected Miner transform(WorldModel world)
   {
      if (getResourceCount() < getResourceLimit())
      {
         return this;
      }
      else
      {
         this.remove(world);
         return null;
      }
   }

   protected boolean move(WorldModel world, WorldEntity miner)
   {
      if (miner == null)
      {
         return false;
      }

      if (adjacent(getPosition(), miner.getPosition()))
      {
         setResourceCount(getResourceCount() + 1);
         miner.remove(world);
         //put quake on point
         return true;
      }
      else
      {
         world.moveEntity(this, nextPosition(world, miner.getPosition()));
         return false;
      }
   }
}
