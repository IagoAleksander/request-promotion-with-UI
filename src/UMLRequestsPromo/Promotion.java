package UMLRequestsPromo;


import java.util.Date;

public abstract class Promotion {
	
	int identifier;
	Client client;
	PromDescription description;
	private int ponumber;
	private boolean pending;
	private boolean confirmed;
	private boolean completed;
	Invoice payment;

	private Promotion(int identifier, Client client, String type, String i, String endDate,String details) {
		description = new PromDescription(type, i, endDate, details);
		this.identifier = identifier;
		this.client = client;
		this.ponumber = -1;
		this.pending = true;
		this.confirmed = false;
		this.completed = false;
		Frame1.index++;
		Frame1.clientsList.add(client);
	}
	
	public Promotion(int identifier, Client client, String type) {
		this(identifier, client, type, "0.00", "", "");
	}

	public int getidentifier(){
		return identifier;
	}
	public String getCost()
	{
		return description.getCost();
	}
	
	public String getEndDate()
	{
		return description.getEndDate();
	}
	
	public void setPonumber(int ponumber)
	{
		this.ponumber = ponumber;
	}
	
	public int getPonumber()
	{
		return ponumber;
	}
	
	public void set_description(String type, String cost, String endDate, String details)
	{
		this.description = new PromDescription(type, cost, endDate, details);	
	}
	
	
	public boolean isPending() {
		return pending;
	}
	
	public void setPending(boolean pending) {
		this.pending = pending;
	}
	
	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	
	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public void setInvoice(String amount) {
	   double money;
	   money = Double.parseDouble(amount);
		payment = new Invoice (money, client);
	}

}
