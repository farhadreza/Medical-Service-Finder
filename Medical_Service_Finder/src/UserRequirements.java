
import java.util.HashMap;

public class UserRequirements {
	
	//String UserQuery;
	double UserDoctorPriority; // High value means user wants Doctor to as fast as possible
	double UserHealthCarePriority; // High value means user wants low price 
	double UserEmergencyPriority; // High value means user demands brand new products
	double UserMedicationStorePriority; // High value means user wants to get product from highly trustworthy MedicationStores
	
	HashMap<String, Double> UserSelectedMedicationStorePriority = new HashMap<String, Double>(); // Example: Key = Amazon, and Value = 10, User thinks Amazon is more trustworthy
	
	public UserRequirements(double _UserDoctorPriority, double _UserHealthCarePriority, double _UserEmergencyPriority, double _UserMedicationStorePriority,  HashMap <String, Double> _UserSelectedMedicationStorePriority){
		UserDoctorPriority = _UserDoctorPriority;
		UserHealthCarePriority = _UserHealthCarePriority;
		UserEmergencyPriority = _UserEmergencyPriority;
		UserMedicationStorePriority = _UserMedicationStorePriority;
		
		UserSelectedMedicationStorePriority = _UserSelectedMedicationStorePriority;
	}
	
	public UserRequirements(double _UserDoctorPriority, double _UserHealthCarePriority, double _UserEmergencyPriority, double _UserMedicationStorePriority){
		UserDoctorPriority = _UserDoctorPriority;
		UserHealthCarePriority = _UserHealthCarePriority;
		UserEmergencyPriority = _UserEmergencyPriority;
		UserMedicationStorePriority = _UserMedicationStorePriority;
		
		
	}
	
	public void setUserDoctorPriority( double _UserDoctorPriority){
		
		UserDoctorPriority = _UserDoctorPriority;
	}
	
	public void setUserHealthCarePriority(double _UserHealthCarePriority){
		
		UserHealthCarePriority = _UserHealthCarePriority;
	}
	
	public void setUserEmergencyPriority(double _UserEmergencyPriority){
	
		UserEmergencyPriority = _UserEmergencyPriority;
	}
	
	public void setUserMedicationStorePriority(double _UserMedicationStorePriority){
	
		UserMedicationStorePriority = _UserMedicationStorePriority;
	}
	
	public void setMedicationStoreInfo(HashMap<String, Double> _UserSelectedMedicationStorePriority){
		
		UserSelectedMedicationStorePriority = _UserSelectedMedicationStorePriority;
	}
	
	public double getUserDoctorPriority(){
		
		return UserDoctorPriority;
	}
	
	public double getUserHealthCarePriority(){
		
		return UserHealthCarePriority;
	}

	public double getUserEmergencyPriority(){
	
		return UserEmergencyPriority;
	}
	
	public double getUserMedicationStorePriority(){
	
		return UserMedicationStorePriority;
	}

	public HashMap<String, Double> getUserSelectedMedicationStorePriority(){
	
		return UserSelectedMedicationStorePriority;
	}
	
}