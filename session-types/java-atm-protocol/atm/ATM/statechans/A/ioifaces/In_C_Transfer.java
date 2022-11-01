package atm.ATM.statechans.A.ioifaces;

import java.io.IOException;
import atm.ATM.*;
import atm.ATM.roles.*;
import atm.ATM.ops.*;

public interface In_C_Transfer<__Succ extends Succ_In_C_Transfer> {

	abstract public __Succ receive(C role, Transfer op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException;
}