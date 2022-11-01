package atm.ATM.statechans.A;

import atm.ATM.*;
import atm.ATM.roles.*;
import atm.ATM.ops.*;
import atm.ATM.statechans.A.ioifaces.*;

public final class ATM_A_4 extends org.scribble.runtime.statechans.ReceiveSocket<ATM, A> implements Receive_A_C_Res_Boolean<ATM_A_2> {
	public static final ATM_A_4 cast = null;

	protected ATM_A_4(org.scribble.runtime.session.SessionEndpoint<ATM, A> se, boolean dummy) {
		super(se);
	}

	public ATM_A_2 receive(C role, Res op, org.scribble.runtime.util.Buf<? super java.lang.Boolean> arg1) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException {
		org.scribble.runtime.message.ScribMessage m = super.readScribMessage(ATM.C);
		arg1.val = (java.lang.Boolean) m.payload[0];
		return new ATM_A_2(this.se, true);
	}

	public ATM_A_2 async(C role, Res op, org.scribble.runtime.util.Buf<ATM_A_4_Future> arg) throws org.scribble.main.ScribRuntimeException {
		arg.val = new ATM_A_4_Future(super.getFuture(ATM.C));
		return new ATM_A_2(this.se, true);
	}

	public boolean isDone() {
		return super.isDone(ATM.C);
	}

	@SuppressWarnings("unchecked")
	public ATM_A_2 async(C role, Res op) throws org.scribble.main.ScribRuntimeException {
		return async(ATM.C, op, (org.scribble.runtime.util.Buf<ATM_A_4_Future>) this.se.gc);
	}
}