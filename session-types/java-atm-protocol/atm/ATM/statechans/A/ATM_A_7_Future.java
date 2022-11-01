package atm.ATM.statechans.A;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ATM_A_7_Future extends org.scribble.runtime.util.ScribFuture {
	public java.lang.Boolean pay1;

	protected ATM_A_7_Future(CompletableFuture<org.scribble.runtime.message.ScribMessage> fut) {
		super(fut);
	}

	public ATM_A_7_Future sync() throws IOException {
		org.scribble.runtime.message.ScribMessage m = super.get();
		this.pay1 = (java.lang.Boolean) m.payload[0];
		return this;
	}
}