package UMLRequestsPromo;


import java.util.List;

public class Client {

	private String name;
	private String mainContact;
	private Staff staffAssigned;
	List<Promotion> promos = null;
	
	public Client (String name, String mainContact, Staff staffAssigned) 
	{
		this.name = name;
		this.mainContact = mainContact;
		this.staffAssigned = staffAssigned;
	}
	
	//public boolean makePayment() 
	{
		//payment.makePayment (date,amount);
		
		//return payment.get_paymentMade();
	}

	public void AddPromo(Promotion p)
	{
		promos.add(p);
	}

	public String getName() {
		return name;
	}

	public Staff getStaffAssigned() {
		return staffAssigned;
	}

	public void setStaffAssigned(Staff staffAssigned) {
		this.staffAssigned = staffAssigned;
	}
	
}
