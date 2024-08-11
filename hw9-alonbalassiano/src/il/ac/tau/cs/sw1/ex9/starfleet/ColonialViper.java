package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class ColonialViper extends Fighter {

	public ColonialViper(String name, int commissionYear, float maximalSpeed, Set<CrewWoman> crewMembers,
			List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
	}
	
	@Override
	public int getAnnualMaintenanceCost() {
		int s = 4000;
		for (Weapon weapon : this.getWeapon()) s += weapon.getAnnualMaintenanceCost();
		s += 500 * this.getCrewMembers().size();
		s += (int)(500 * this.getMaximalSpeed());
		return s;	
	}
	
	@Override
	public String toString() {
		String str = "ColonialViper";
		str += "\n\tName=" + this.getName();
		str += "\n\tCommissionYear=" + this.getCommissionYear();
		str += "\n\tMaximalSpeed=" + this.getMaximalSpeed();
		str += "\n\tFirePower=" + this.getFirePower();
		str += "\n\tCrewMembers=" + this.getCrewMembers().size();
		str += "\n\tAnnualMaintenanceCost=" + this.getAnnualMaintenanceCost();
		str += "\n\tWeaponArray=" + this.getWeapon().toString();
		return str;
	}

}
