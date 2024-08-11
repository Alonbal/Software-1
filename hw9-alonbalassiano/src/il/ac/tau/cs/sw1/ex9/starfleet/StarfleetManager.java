package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class StarfleetManager {

	/**
	 * Returns a list containing string representation of all fleet ships, sorted in descending order by
	 * fire power, and then in descending order by commission year, and finally in ascending order by
	 * name
	 */
	public static List<String> getShipDescriptionsSortedByFirePowerAndCommissionYear (Collection<Spaceship> fleet) {
		List<Spaceship> lst = new ArrayList<>();
		for (Spaceship ship : fleet) lst.add(ship);
		lst.sort(new Comparator<Spaceship>() {
			public int compare(Spaceship ship1, Spaceship ship2) {
				if (ship1.getFirePower() != ship2.getFirePower()) return Integer.compare(ship2.getFirePower(), ship1.getFirePower());
				if (ship1.getCommissionYear() != ship2.getCommissionYear()) return Integer.compare(ship2.getCommissionYear(), ship1.getCommissionYear());
				return ship1.getName().compareTo(ship2.getName());
			}
		});
		List<String> res = new ArrayList<>();
		for (Spaceship ship : lst) res.add(ship.toString());
		return res;
	}

	/**
	 * Returns a map containing ship type names as keys (the class name) and the number of instances created for each type as values
	 */
	public static Map<String, Integer> getInstanceNumberPerClass(Collection<Spaceship> fleet) {
		Map<String, Integer> map = new HashMap<>();
		for (Spaceship ship : fleet) {
			String name = ship.getClass().getSimpleName();
			if (map.containsKey(name)) map.put(name, map.get(name) + 1);
			else map.put(name, 1);
		}
		return map;

	}


	/**
	 * Returns the total annual maintenance cost of the fleet (which is the sum of maintenance costs of all the fleet's ships)
	 */
	public static int getTotalMaintenanceCost (Collection<Spaceship> fleet) {
		int sum = 0;
		for (Spaceship ship : fleet) sum += ship.getAnnualMaintenanceCost();
		return sum;

	}


	/**
	 * Returns a set containing the names of all the fleet's weapons installed on any ship
	 */
	public static Set<String> getFleetWeaponNames(Collection<Spaceship> fleet) {
		Set<String> set = new HashSet<>();
		for (Spaceship ship : fleet) {
			if (ship instanceof Fighter) {
				for (Weapon weapon : ((Fighter) ship).getWeapon()) set.add(weapon.getName());
			}
			else if (ship instanceof Bomber) {
				for (Weapon weapon : ((Bomber) ship).getWeapon()) set.add(weapon.getName());
			}
		}
		return set;

	}

	/*
	 * Returns the total number of crew-members serving on board of the given fleet's ships.
	 */
	public static int getTotalNumberOfFleetCrewMembers(Collection<Spaceship> fleet) {
		int sum = 0;
		for (Spaceship ship : fleet) sum += ship.getCrewMembers().size();
		return sum;

	}

	/*
	 * Returns the average age of all officers serving on board of the given fleet's ships. 
	 */
	public static float getAverageAgeOfFleetOfficers(Collection<Spaceship> fleet) {
		int totalAge = 0;
		int numOfOfficers = 0;
		for (Spaceship ship : fleet) {
			for (CrewMember member : ship.getCrewMembers()) {
				if (member instanceof Officer) {
					totalAge += member.getAge();
					numOfOfficers++;
				}
			}
		}
		return ((float)totalAge)/((float)numOfOfficers);
	}

	/*
	 * Returns a map mapping the highest ranking officer on each ship (as keys), to his ship (as values).
	 */
	public static Map<Officer, Spaceship> getHighestRankingOfficerPerShip(Collection<Spaceship> fleet) {
		Map<Officer, Spaceship> map = new HashMap<>();
		for (Spaceship ship : fleet) {
			List<Officer> lst = new ArrayList<>();
			for (CrewMember member : ship.getCrewMembers()) {
				if (member instanceof Officer) lst.add((Officer)member);
			}
			lst.sort((o1, o2) -> o2.getRank().compareTo(o1.getRank()));
			if (lst.size() > 0) map.put(lst.get(0), ship);
		}
		return map;

	}

	/*
	 * Returns a List of entries representing ranks and their occurrences.
	 * Each entry represents a pair composed of an officer rank, and the number of its occurrences among starfleet personnel.
	 * The returned list is sorted ascendingly based on the number of occurrences.
	 */
	public static List<Map.Entry<OfficerRank, Integer>> getOfficerRanksSortedByPopularity(Collection<Spaceship> fleet) {
		Map<OfficerRank, Integer> map = new HashMap<>();
		for (Spaceship ship : fleet) {
			for (CrewMember member : ship.getCrewMembers()) {
				if (member instanceof Officer) {
					OfficerRank rank = ((Officer)member).getRank();
					if (map.containsKey(rank)) map.put(rank, map.get(rank) + 1);
					else map.put(rank, 1);
				}
			}
		}
		List<Map.Entry<OfficerRank, Integer>> lst = new ArrayList<>();
		for (OfficerRank rank : map.keySet()) lst.add(Map.entry(rank, map.get(rank)));
		lst.sort(new Comparator<Map.Entry<OfficerRank, Integer>>() {
			public int compare(Entry<OfficerRank, Integer> o1, Entry<OfficerRank, Integer> o2) {
				if (o1.getValue() != o2.getValue()) return Integer.compare(o1.getValue(), o2.getValue());
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		return lst;
	}

}
