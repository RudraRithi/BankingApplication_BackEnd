package com.Banking._Application.helper;

import lombok.Data;

@Data
public class ResponseStructure<T> {

	int code;
	String message;
	T data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public ResponseStructure(int code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public ResponseStructure() {
		super();
	}
	@Override
	public String toString() {
		return "ResponseStructure [code=" + code + ", message=" + message + ", data=" + data + "]";
	}
	
	
}
