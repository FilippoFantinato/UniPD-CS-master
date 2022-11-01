package atm.ATM.statechans.A.ioifaces;

import java.io.IOException;
import atm.ATM.*;
import atm.ATM.roles.*;
import atm.ATM.ops.*;

public interface In_C_Res_Boolean<__Succ extends Succ_In_C_Res_Boolean> {

	abstract public __Succ receive(C role, Res op, org.scribble.runtime.util.Buf<? super java.lang.Boolean> arg1) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException;
}