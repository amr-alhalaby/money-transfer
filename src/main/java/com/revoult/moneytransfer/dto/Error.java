

package com.revoult.moneytransfer.dto;


public class Error {

    private String code;
    private String message;
    private String techMessage;

  
    public Error(String code, String message, String teckMessage) {
		super();
		
	    this.code = code;
		this.message = message;
		this.techMessage = teckMessage;

    }

    

	public Error() {
		super();
	}



	public String getTechMessage() {
		return techMessage;
	}



	public void setTechMessage(String techMessage) {
		this.techMessage = techMessage;
	}





    public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getMessage() {
        return message;
    }


    public void setMessage(String value) {
        this.message = value;
    }

}
