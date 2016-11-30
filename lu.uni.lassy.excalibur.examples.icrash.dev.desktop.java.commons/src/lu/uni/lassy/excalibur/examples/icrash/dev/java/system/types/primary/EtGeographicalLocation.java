package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

public enum EtGeographicalLocation implements JIntIs{
	central,
	
	south_eastern,
	
	south_western,
	
	north_eastern,
	
	north_western;
	
	
	@Override
	public PtBoolean is(){
		return new PtBoolean(this.name() == EtGeographicalLocation.central.name() ||
			this.name() == EtGeographicalLocation.south_eastern.name()|| 
			this.name() == EtGeographicalLocation.south_western.name()||
			this.name() == EtGeographicalLocation.north_eastern.name()||
			this.name() == EtGeographicalLocation.north_western.name());
	}

}

