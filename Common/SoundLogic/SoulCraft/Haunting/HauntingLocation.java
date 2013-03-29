package SoundLogic.SoulCraft.Haunting;

import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import cpw.mods.fml.common.*;

import SoundLogic.SoulCraft.LocationField;
import SoundLogic.SoulCraft.Registry.FileRegistry;

public abstract class HauntingLocation extends Haunting{
	public LocationField location;

	@Override
	public Map<String, String> writeToMap() {
		Map<String,String> map=super.writeToMap();
		map.put("Location.A.X", String.valueOf(location.a[0]));
		map.put("Location.A.Y", String.valueOf(location.a[1]));
		map.put("Location.A.Z", String.valueOf(location.a[2]));
		map.put("Location.B.X", String.valueOf(location.b[0]));
		map.put("Location.B.Y", String.valueOf(location.b[1]));
		map.put("Location.B.Z", String.valueOf(location.b[2]));
		map.put("Location.Dim",String.valueOf(location.dim));
		map.put("Location.R",String.valueOf(location.r));
		return map;
	}

	@Override
	public void loadFromMap(Map<String, String> map) {
		super.loadFromMap(map);
		float[] a=new float[]{Float.valueOf(map.get("Location.A.X")),
					  Float.valueOf(map.get("Location.A.Y")),
					  Float.valueOf(map.get("Location.A.Z"))};
		float[] b=new float[]{Float.valueOf(map.get("Location.B.X")),
				  Float.valueOf(map.get("Location.B.Y")),
				  Float.valueOf(map.get("Location.B.Z"))};
		float r=Float.valueOf(map.get("Location.R"));
		int dim=Integer.valueOf(map.get("Location.Dim"));
		location=new LocationField(a,b,r,dim);
	}
}
