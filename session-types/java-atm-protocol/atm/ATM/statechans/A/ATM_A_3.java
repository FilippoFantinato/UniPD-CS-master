package atm.ATM.statechans.A;

import java.io.IOException;
import atm.ATM.*;
import atm.ATM.roles.*;
import atm.ATM.ops.*;
import atm.ATM.statechans.A.ioifaces.*;

public final class ATM_A_3 extends org.scribble.runtime.statechans.OutputSocket<ATM, A> implements Select_A_C_SendMoney_Int<ATM_A_4> {
	public static final ATM_A_3 cast = null;

	protected ATM_A_3(org.scribble.runtime.session.SessionEndpoint<ATM, A> se, boolean dummy) {
		super(se);
	}

	public ATM_A_4 send(C role, SendMoney op, java.lang.Integer arg0) throws org.scribble.main.ScribRuntimeException, IOException {
		super.writeScribMessage(role, ATM.SendMoney, arg0);

		return new ATM_A_4(this.se, true);
	}
}