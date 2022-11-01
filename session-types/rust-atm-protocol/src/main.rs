extern crate session_types;

use session_types::*;
use std::thread;

type ATM = Recv<String, ATMOps>;
type ATMOps = Rec<Offer<ATMDeposit, Offer<ATMTransfer, Quit>>>;
type ATMDeposit = Recv<i64, Answer>;
type ATMTransfer = Recv<String, Recv<i64, Answer>>;
type Answer = Choose<Approved, Error>;
type Approved = Send<i64, Var<Z>>;
type Error = Send<String, Var<Z>>;
type Quit = Eps;

type Customer = <ATM as HasDual>::Dual;

fn atm(c: Chan<(), ATM>) {
    let (c, name) = c.recv();
    println!("Hi {}", name);
    let mut c = c.enter();
    let mut balance = 400;
    loop {
        c = offer !{
                c,
                Deposit => {
                    let (c, money) = c.recv();
                    println!("Depositing {}", money);
                    if money > 0 {
                        balance = balance + money;
                        c.sel1().send(balance).zero()
                    } else {
                        c.sel2().send("Error: the amount of money is negative or equal to 0".to_string()).zero()
                    }
                },
                Transfer => {
                    let (c, bank_address) = c.recv();
                    let (c, money) = c.recv();
                    println!("Transferring from {} to {} {}", name, bank_address, money);
                    if balance >= money && money > 0 {
                        balance = balance - money;
                        c.sel1().send(balance).zero()
                    } else {
                        c.sel2().send("Error: not enough money".to_string()).zero()
                    }
                },
                Quit => {
                    println!("Bye bye {}", name);
                    c.close();
                    break
                }
            }
    };
}

fn customer_deposit(c: Chan<(), Customer>, name: String) {
    let c = c.send(name);
    let c = c.enter().sel1().send(10000);
    let c = offer! {
        c,
        Approved => {
            let (c, new_balance) = c.recv();
            println!("Now my balance is {}", new_balance);
            c.zero()
        },
        Error => {
            let (c, e) = c.recv();
            println!("{}", e);
            c.zero()
        }
    };
    c.sel2().sel2().close()
}

fn customer_transfer(c: Chan<(), Customer>, name: String, dest: String) {
    let c = c.send(name);

    let c = c.enter().sel2().sel1().send(dest).send(10000);

    let c = offer! {
        c,
        Approved => {
            let (c, new_balance) = c.recv();
            println!("Now my balance is {}", new_balance);
            c.zero()
        },
        Error => {
            let (c, e) = c.recv();
            println!("{}", e);
            c.zero()
        }
    };
    c.sel2().sel2().close()
}

fn main() {
    let (server_chan, client_chan) = session_channel();
 
    let atm_t = thread::spawn(move || atm(server_chan));
    // let costumer_t = thread::spawn(move || customer_deposit(client_chan, "Filippo".to_string()));
    let costumer_t = thread::spawn(move || customer_transfer(client_chan, "Filippo".to_string(), "Alice".to_string()));

    let _ = (atm_t.join(), costumer_t.join());
}
