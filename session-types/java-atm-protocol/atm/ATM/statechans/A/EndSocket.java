package atm.ATM.statechans.A;

import atm.ATM.*;
import atm.ATM.roles.*;
import atm.ATM.statechans.A.ioifaces.*;

public final class EndSocket extends org.scribble.runtime.statechans.EndSocket<ATM, A> implements Succ_In_C_Quit {
	public static final EndSocket cast = null;

	protected EndSocket(org.scribble.runtime.session.SessionEndpoint<ATM, A> se, boolean dummy) {
		super(se);
	}

	@Override
	public EndSocket to(EndSocket cast) {
		return (EndSocket) this;
	}
}