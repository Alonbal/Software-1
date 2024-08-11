package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class StealthCruiser extends Fighter {
	
	private static int counter = 0;
	
	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
		counter++;
	}

	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers){
		this(name, commissionYear, maximalSpeed, crewMembers, Arrays.asList(new Weapon("Laser Cannons", 10, 100)));
	}

	@Override
	public int getAnnualMaintenanceCost() {
		return counter * 50 + super.getAnnualMaintenanceCost();
	}
	
	@Override
	public String toString() {
		String str = "StealthCruiser";
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
