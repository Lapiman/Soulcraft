package SoundLogic.SoulCraft.HauntEvents.Fire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class FireTracker {
	public static List<TileEntitySpecialFire> fires;
	public static List<Integer> ids;
	public static void init()
	{
		fires=new ArrayList();
		ids=new ArrayList();
	}
	public static int getFreeID()
	{
		if(ids.size()==0)
			return 1;
		Collections.sort(ids);
		return ids.get(ids.size()-1)+1;
	}
	public static void remove(int id)
	{
		ids.remove(Integer.valueOf(id));
		List<TileEntitySpecialFire> cur=new ArrayList(fires);
		for(TileEntitySpecialFire fire : cur)
		{
			if(fire.removeIfMatches(id))
				fires.remove(fire);
		}
	}
}
