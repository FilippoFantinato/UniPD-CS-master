.assembly ATM
{
    .hash algorithm 0x00008004
    .ver 1:0:0:0
}
.class public ATM
{
    .field private int32 'balance'
    .method public hidebysig specialname rtspecialname instance default void '.ctor' () cil managed
    {
        .maxstack 1
        ldarg.0
        call instance void object::'.ctor'()
        ret
    }
    .method public hidebysig instance int32 'deposit'(int32 'money') cil managed
    {
        .maxstack 8
        .locals(int32 'money')
        ldarg.0
        ldarg.0
        ldfld int32 ATM::'balance'
        ldarg.1
        sub
        stfld int32 ATM::'balance'
        ldarg.0
        ldfld int32 ATM::'balance'
        ret
    }
    .method public hidebysig instance bool 'canIDeposit'(int32 'money') cil managed
    {
        .maxstack 8
        .locals(int32 'money')
        ldarg.1
        ldc.i4.0
        bgt TrueComp0
        ldc.i4.0
        br BrFalse0
    TrueComp0:
        ldc.i4.1
    BrFalse0:
        ret
    }
    .method public hidebysig instance void 'init'() cil managed
    {
        .maxstack 8
        ldarg.0
        ldc.i4 400
        stfld int32 ATM::'balance'
        ret
    }
}
