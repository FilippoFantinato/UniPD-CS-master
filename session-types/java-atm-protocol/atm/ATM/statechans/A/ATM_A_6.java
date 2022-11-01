package atm.ATM.statechans.A;

import java.io.IOException;
import atm.ATM.*;
import atm.ATM.roles.*;
import atm.ATM.ops.*;
import atm.ATM.statechans.A.ioifaces.*;

public final class ATM_A_6 extends org.scribble.runtime.statechans.OutputSocket<ATM, A> implements Select_A_C_SendMoney_Int<ATM_A_7> {
	public static final ATM_A_6 cast = null;

	protected ATM_A_6(org.scribble.runtime.session.SessionEndpoint<ATM, A> se, boolean dummy) {
		super(se);
	}

	public ATM_A_7 send(C role, SendMoney op, java.lang.Integer arg0) throws org.scribble.main.ScribRuntimeException, IOException {
		super.writeScribMessage(role, ATM.SendMoney, arg0);

		return new ATM_A_7(this.se, true);
	}
}