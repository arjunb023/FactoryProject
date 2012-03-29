package shared;

import java.util.ArrayList;

import shared.enums.PartType;

public class KitConfig {

	public ArrayList <PartType> kitConfig; //list of parts that go into nest
	
	public KitConfig(ArrayList<PartType> spec)
	{
		kitConfig	= spec;
	}

	public ArrayList <PartType> getKitConfig() {
		return kitConfig;
	}
}