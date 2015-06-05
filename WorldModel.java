import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

public class WorldModel
{
   private Background[][] background;
   private WorldEntity[][] occupancy;
   private List<WorldEntity> entities;
   private int numRows;
   private int numCols;
   private OrderedList<Action> actionQueue;

   public WorldModel(int numRows, int numCols, Background background)
   {
      this.background = new Background[numRows][numCols];
      this.occupancy = new WorldEntity[numRows][numCols];
      this.numRows = numRows;
      this.numCols = numCols;
      this.entities = new LinkedList<>();
      this.actionQueue = new OrderedList<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], background);
      }
   }

   public boolean withinBounds(Point pt)
   {
      return pt.x >= 0 && pt.x < numCols && pt.y >= 0 && pt.y < numRows;
   }

   public int getNumRows()
   {
      return numRows;
   }

   public int getNumCols()
   {
      return numCols;
   }

   public List<WorldEntity> getEntities()
   {
      return entities;
   }

   public boolean isOccupied(Point pt)
   {
      return withinBounds(pt) && getCell(occupancy, pt) != null;
   }

   public WorldEntity findNearest(Point pt, Class type)
   {
      List<WorldEntity> ofType = new LinkedList<>();
      for (WorldEntity entity : entities)
      {
         if (type.isInstance(entity))
         {
            ofType.add(entity);
         }
      }

      return nearestEntity(ofType, pt);
   }

   public void addEntity(WorldEntity entity)
   {
      Point pt = entity.getPosition();
      if (withinBounds(pt))
      {
         WorldEntity old = getCell(occupancy, pt);
         if (old != null)
         {
            old.remove(this);
         }
         setCell(occupancy, pt, entity);
         entities.add(entity);
      }
   }

   public void moveEntity(WorldEntity entity, Point pt)
   {
      if (withinBounds(pt))
      {
         Point oldPt = entity.getPosition();
         setCell(occupancy, oldPt, null);
         removeEntityAt(pt);
         setCell(occupancy, pt, entity);
         entity.setPosition(pt);
      }
   }

   public void removeEntity(WorldEntity entity)
   {
      removeEntityAt(entity.getPosition());
   }

   public void removeEntityAt(Point pt)
   {
      if (withinBounds(pt) && getCell(occupancy, pt) != null)
      {
         WorldEntity entity = getCell(occupancy, pt);
         entity.setPosition(new Point(-1, -1));
         entities.remove(entity);
         setCell(occupancy, pt, null);
      }
   }

   public Background getBackground(Point pt)
   {
      return withinBounds(pt) ? getCell(background, pt) : null;
   }

   public void setBackground(Point pt, Background bgnd)
   {
      if (withinBounds(pt))
      {
         setCell(background, pt, bgnd);
      }
   }

   public WorldEntity getTileOccupant(Point pt)
   {
      return withinBounds(pt) ? getCell(occupancy, pt) : null;
   }

   public void scheduleAction(Action action, long time)
   {
      actionQueue.insert(action, time);
   }

   public void unscheduleAction(Action action)
   {
      actionQueue.remove(action);
   }

   public void updateOnTime(long time)
   {
      OrderedList.ListItem<Action> next = actionQueue.head();
      while (next != null && next.ord < time)
      {
         actionQueue.pop();
         next.item.execute(time);
         next = actionQueue.head();
      }
   }

   private static WorldEntity nearestEntity(List<WorldEntity> entities,
      Point pt)
   {
      if (entities.size() == 0)
      {
         return null;
      }
      WorldEntity nearest = entities.get(0);
      double nearest_dist = distance_sq(nearest.getPosition(), pt);

      for (WorldEntity entity : entities)
      {
         double dist = distance_sq(entity.getPosition(), pt);
         if (dist < nearest_dist)
         {
            nearest = entity;
            nearest_dist = dist;
         }
      }

      return nearest;
   }

   private static double distance_sq(Point p1, Point p2)
   {
      double dx = p1.x - p2.x;
      double dy = p1.y - p2.y;
      return dx * dx + dy * dy;
   }

   private static <T> T getCell(T[][] grid, Point pt)
   {
      return grid[pt.y][pt.x];
   }

   private static <T> void setCell(T[][] grid, Point pt, T v)
   {
      grid[pt.y][pt.x] = v;
   }

      public boolean isMouseAreaOccupied(Point pt)
   {
      Point checkpt1 = new Point(pt.x-2, pt.y + 2);
      Point checkpt2 = new Point(pt.x-1, pt.y + 2);
      Point checkpt3 = new Point(pt.x, pt.y + 2);
      Point checkpt4 = new Point(pt.x+1, pt.y + 2);
      Point checkpt5 = new Point(pt.x+2, pt.y + 2);
      Point checkpt6 = new Point(pt.x-2, pt.y + 1);
      Point checkpt7 = new Point(pt.x-1, pt.y + 1);
      Point checkpt8 = new Point(pt.x, pt.y + 1);
      Point checkpt9 = new Point(pt.x+1, pt.y + 1);
      Point checkpt10 = new Point(pt.x+2, pt.y + 1);
      Point checkpt11 = new Point(pt.x-2, pt.y);
      Point checkpt12 = new Point(pt.x-1, pt.y);
      Point checkpt13 = new Point(pt.x+1, pt.y);
      Point checkpt14 = new Point(pt.x+2, pt.y);
      Point checkpt15 = new Point(pt.x-2, pt.y-1);
      Point checkpt16 = new Point(pt.x-1, pt.y-1);
      Point checkpt17 = new Point(pt.x, pt.y-1);
      Point checkpt18 = new Point(pt.x+1, pt.y-1);
      Point checkpt19 = new Point(pt.x+2, pt.y-1);
      Point checkpt20 = new Point(pt.x-2, pt.y-2);
      Point checkpt21 = new Point(pt.x-1, pt.y-2);
      Point checkpt22 = new Point(pt.x, pt.y-2);
      Point checkpt23 = new Point(pt.x+1, pt.y-2);
      Point checkpt24 = new Point(pt.x+2, pt.y-2);
      Point checkpt25 = pt;
      
      if(isOccupied(checkpt1) || isOccupied(checkpt2) || isOccupied(checkpt3) 
         || isOccupied(checkpt4) || isOccupied(checkpt5) || 
         isOccupied(checkpt6) || isOccupied(checkpt7) || isOccupied(checkpt8) 
         || isOccupied(checkpt9) || isOccupied(checkpt10) || 
         isOccupied(checkpt11) || isOccupied(checkpt12) || 
         isOccupied(checkpt13) || isOccupied(checkpt14) || 
         isOccupied(checkpt15) || isOccupied(checkpt16) || 
         isOccupied(checkpt17) || isOccupied(checkpt18) || 
         isOccupied(checkpt19) || isOccupied(checkpt20) || 
         isOccupied(checkpt21) || isOccupied(checkpt22) || 
         isOccupied(checkpt23) || isOccupied(checkpt24) || 
         isOccupied(checkpt25))
      {
         return true;
      }
      return false; 
   }
}
