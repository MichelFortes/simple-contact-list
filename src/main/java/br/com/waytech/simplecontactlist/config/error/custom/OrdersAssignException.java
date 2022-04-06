package br.com.waytech.simplecontactlist.config.error.custom;

import java.util.List;
import java.util.UUID;

public class OrdersAssignException extends RuntimeException {

	private static final long serialVersionUID = 1377549258882718980L;
	public final List<UUID> rejectedOrdersIds;

	public OrdersAssignException(List<UUID> rejectedOrdersIds) {
		this.rejectedOrdersIds = rejectedOrdersIds;
	}

}
