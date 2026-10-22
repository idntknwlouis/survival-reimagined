package net.mcreator.survivalreimagined.procedures;

public class RadiationActiveTickConditionProcedure {
	public static boolean execute(double amplifier, double duration) {
		double rateWithAmplifier = 50 / Math.pow(2, amplifier);
		if (Math.floor(rateWithAmplifier) > 0) {
			return duration % Math.floor(rateWithAmplifier) == 0;
		}
		return true;
	}
}
