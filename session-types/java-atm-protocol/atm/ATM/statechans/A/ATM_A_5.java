package atm.ATM.statechans.A;

import java.io.IOException;
import atm.ATM.*;
import atm.ATM.roles.*;
import atm.ATM.ops.*;
import atm.ATM.statechans.A.ioifaces.*;

public final class ATM_A_5 extends org.scribble.runtime.statechans.OutputSocket<ATM, A> implements Select_A_C_SendBankAddress_String<ATM_A_6> {
	public static final ATM_A_5 cast = null;

	protected ATM_A_5(org.scribble.runtime.session.SessionEndpoint<ATM, A> se, boolean dummy) {
		super(se);
	}

	public ATM_A_6 send(C role, SendBankAddress op, java.lang.String arg0) throws org.scribble.main.ScribRuntimeException, IOException {
		super.writeScribMessage(role, ATM.SendBankAddress, arg0);

		return new ATM_A_6(this.se, true);
	}
}