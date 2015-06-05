import processing.core.PImage;
import java.util.List;

public class Dragon 
   extends Miner
{
   public Dragon(String name, Point position, int rate, int animation_rate,
      int resource_limit, List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, resource_limit,
         0, MinerNotFull.class, imgs);
   }

   protected Miner transform(WorldModel world)
   {
      if (getResourceCount() < getResourceLimit())
      {
         return this;
      }
      else if(getResourceCount() == getResourceLimit())
      {
         this.remove(world);
         return null;
      }
      else
      {
         return this;
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
         return true;
      }
      else
      {
         world.moveEntity(this, nextPosition(world, miner.getPosition()));
         return false;
      }
   }

}
