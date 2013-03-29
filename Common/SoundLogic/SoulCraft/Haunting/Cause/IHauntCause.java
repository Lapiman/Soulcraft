package SoundLogic.SoulCraft.Haunting.Cause;

import java.util.Map;

import SoundLogic.SoulCraft.HauntEvents.HauntEvent;

public interface IHauntCause {
	public boolean valid();
	public HauntEvent protectEvent();
	public boolean underAttack();
	public Map<String,String> writeToMap();
	public void loadFromMap(Map<String,String> map);
}
