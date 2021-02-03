package model;

public class HelloMessage {

	private String name;
    private String command;

	public HelloMessage() {
	}

	public HelloMessage(String name,String command) {
		this.name = name;
		this.command=command;
	}

	public void setCommand(String command){
		this.command=command;
	}

	public String getCommand(){
	   return command;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
