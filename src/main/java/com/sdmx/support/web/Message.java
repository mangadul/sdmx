package com.sdmx.support.web;

/**
 * A message to be displayed in web context. Depending on the type, different style will be applied.
 */
public class Message implements java.io.Serializable {

    /**
     * The type of the message to be displayed. The type is used to show message in a different style.
     */
	public static enum Type {
        DANGER, WARNING, INFO, SUCCESS;
	}

	private final String message;
	private final Type type;
	private final Object[] args;

	public Message(String message, Type type) {
		this.message = message;
		this.type = type;
		this.args = null;
	}
	
	public Message(String message, Type type, Object... args) {
		this.message = message;
		this.type = type;
		this.args = args;
	}

	public String getMessage() {
		return message;
	}

	public Type getType() {
		return type;
	}

	public String getTypeLabel() {
		switch (type) {
			case DANGER: return "danger";
			case WARNING: return "warning";
			case INFO: return "info";
			case SUCCESS: return "success";
			default: return "default";
		}
	}

	public Object[] getArgs() {
		return args;
	}
}
