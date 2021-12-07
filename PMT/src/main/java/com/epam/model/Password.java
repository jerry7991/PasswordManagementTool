package com.epam.model;

public class Password {
	private int length;
	private boolean isAlphabetAllowed;
	private boolean isNumericAllowed;
	private boolean isSpeacialCharAllowed;

	public Password() {
		length = 6;
		isAlphabetAllowed = true;
		isSpeacialCharAllowed = true;
		isNumericAllowed = true;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isAlphabetAllowed() {
		return isAlphabetAllowed;
	}

	public void setAlphabetAllowed(boolean isAlphabetAllowed) {
		this.isAlphabetAllowed = isAlphabetAllowed;
	}

	public boolean isNumericAllowed() {
		return isNumericAllowed;
	}

	public void setNumericAllowed(boolean isNumericAllowed) {
		this.isNumericAllowed = isNumericAllowed;
	}

	public boolean isSpeacialCharAllowed() {
		return isSpeacialCharAllowed;
	}

	public void setSpeacialCharAllowed(boolean isSpeacialCharAllowed) {
		this.isSpeacialCharAllowed = isSpeacialCharAllowed;
	}

	@Override
	public String toString() {
		StringBuilder criteria = new StringBuilder();
		criteria.append("\n Is numeric :: " + this.isNumericAllowed());
		criteria.append("\n Is alphabate :: " + this.isAlphabetAllowed());
		criteria.append("\n Is special chars :: " + this.isSpeacialCharAllowed());
		criteria.append("\n min Length :: " + this.getLength());
		return criteria.toString();
	}
}
