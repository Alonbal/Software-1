package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Fighter implements Spaceship {
	
	private String name;
	private int commissionYear;
	private float maximalSpeed;
	private Set<? extends CrewMember> crewMembers;
	private List<Weapon> weapons;
	
	public Fighter(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers, List<Weapon> weapons){
		this.name = name;
		this.commissionYear = commissionYear;
		this.maximalSpeed = maximalSpeed;
		this.crewMembers = crewMembers;
		this.weapons = weapons;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getCommissionYear() {
		return commissionYear;
	}

	@Override
	public float getMaximalSpeed() {
		return maximalSpeed;
	}

	@Override
	public int getFirePower() {
		int s = 10;
		for (Weapon weapon : weapons) s += weapon.getFirePower();
		return s;
	}

	@Override
	public Set<? extends CrewMember> getCrewMembers() {
		return crewMembers;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		int s = 2500;
		for (Weapon weapon : weapons) s += weapon.getAnnualMaintenanceCost();
		s += (int)(1000 * maximalSpeed);
		return s;
	}

	public List<Weapon> getWeapon() {
		return weapons;
	}
	
	@Override
	public String toString() {
		String str = "Fighter";
		str += "\n\tName=" + this.getName();
		str += "\n\tCommissionYear=" + this.getCommissionYear();
		str += "\n\tMaximalSpeed=" + this.getMaximalSpeed();
		str += "\n\tFirePower=" + this.getFirePower();
		str += "\n\tCrewMembers=" + this.getCrewMembers().size();
		str += "\n\tAnnualMaintenanceCost=" + this.getAnnualMaintenanceCost();
		str += "\n\tWeaponArray=" + this.getWeapon().toString();
		return str;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fighter other = (Fighter) obj;
		return this.getName().equals(other.getName());
	}
}
