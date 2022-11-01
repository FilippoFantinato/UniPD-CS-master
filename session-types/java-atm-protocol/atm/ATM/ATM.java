package atm.ATM;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.scribble.core.type.name.Role;
import atm.ATM.roles.*;
import atm.ATM.ops.*;

public final class ATM extends org.scribble.runtime.session.Session {
	public static final List<String> IMPATH = new LinkedList<>();
	public static final String SOURCE = "getSource";
	public static final org.scribble.core.type.name.GProtoName PROTO = org.scribble.core.type.session.STypeFactory.parseGlobalProtocolName("atm.ATM");
	public static final A A = atm.ATM.roles.A.A;
	public static final C C = atm.ATM.roles.C.C;
	public static final SendBankAddress SendBankAddress = atm.ATM.ops.SendBankAddress.SendBankAddress;
	public static final Quit Quit = atm.ATM.ops.Quit.Quit;
	public static final Transfer Transfer = atm.ATM.ops.Transfer.Transfer;
	public static final SendMoney SendMoney = atm.ATM.ops.SendMoney.SendMoney;
	public static final SendName SendName = atm.ATM.ops.SendName.SendName;
	public static final Res Res = atm.ATM.ops.Res.Res;
	public static final Deposit Deposit = atm.ATM.ops.Deposit.Deposit;
	public static final List<Role> ROLES = Collections.unmodifiableList(Arrays.asList(new Role[] {A, C}));

	public ATM() {
		super(ATM.IMPATH, ATM.SOURCE, ATM.PROTO);
	}

	@Override
	public List<Role> getRoles() {
		return ATM.ROLES;
	}
}