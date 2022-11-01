open Mpst.BasicCombinators
open Mpst.Unicast

[%%declare_roles_prefixed customer, atm]
[%%declare_labels deposit, transfer, approved, denied, quit]

let protocol = (customer ==> atm) @@ 
fix (fun ops ->
  [%choice_at customer (
    (customer --> atm) deposit @@ (customer ==> atm) @@ [%choice_at atm (
      (atm --> customer) approved @@ (atm ==> customer) @@ ops,
      (atm --> customer) denied @@ (atm ==> customer) @@ ops
      )],
    (customer --> atm) transfer @@ (customer ==> atm) @@ (customer ==> atm) @@ [%choice_at atm (
      (atm --> customer) approved @@ (atm ==> customer) @@ ops,
      (atm --> customer) denied @@ (atm ==> customer) @@ ops
      )],
    (customer --> atm) quit @@ finish
  )]
)

let (`cons (s_customer, `cons(s_atm, _))) = extract protocol

let t_customer_deposit () = 
  let s_customer = send s_customer#role_Atm "Filippo" in
  let s_customer = select s_customer#role_Atm#deposit in
  let s_customer = send s_customer#role_Atm 400 in
  let s_customer = match branch s_customer#role_Atm with
  | `approved s_customer ->
      let new_balance, s_customer = receive s_customer#role_Atm in
      Printf.printf "Now my balance is %d\n" new_balance;
      s_customer
  | `denied s_customer ->
      let e, s_customer = receive s_customer#role_Atm in
      print_string e;
      s_customer
  in
  let s_customer = select s_customer#role_Atm#quit in
  close s_customer

let t_customer_transfer () = 
  let s_customer = send s_customer#role_Atm "Filippo" in
  let s_customer = select s_customer#role_Atm#transfer in
  let s_customer = send s_customer#role_Atm "Alice" in
  let s_customer = send s_customer#role_Atm 100 in
  let s_customer = match branch s_customer#role_Atm with
  | `approved s_customer ->
      let new_balance, s_customer = receive s_customer#role_Atm in
      Printf.printf "Now my balance is %d\n" new_balance;
      s_customer
  | `denied s_customer ->
      let e, s_customer = receive s_customer#role_Atm in
      print_string e;
      s_customer
  in
  let s_customer = select s_customer#role_Atm#quit in
  close s_customer

let t_atm () = 
  let name, s_atm = receive s_atm#role_Customer in
  Printf.printf "Hi %s\n" name;
  let rec loop bal s_atm =
    match branch s_atm#role_Customer with
      | `deposit s_atm -> 
          let money, s_atm = receive s_atm#role_Customer in
          if money > 0 then
            let s_atm = select s_atm#role_Customer#approved in
            Printf.printf "Depositing %d\n" money;
            let new_bal = (bal + money) in
            let s_atm = send s_atm#role_Customer new_bal in
            loop new_bal s_atm
          else
            let s_atm = select s_atm#role_Customer#denied in
            let s_atm = send s_atm#role_Customer (Printf.sprintf "Error: the amount of money is negative or equal to 0\n") in
            loop bal s_atm
      | `transfer s_atm ->
          let bank_address, s_atm = receive s_atm#role_Customer in
          let money, s_atm = receive s_atm#role_Customer in
          if (bal >= money && money > 0) then
            let s_atm = select s_atm#role_Customer#approved in
            Printf.printf "Transferring from %s to %s %d\n" name bank_address money;
            let new_bal = (bal - money) in
            let s_atm = send s_atm#role_Customer new_bal in
            loop new_bal s_atm
          else
            let s_atm = select s_atm#role_Customer#denied in
            let s_atm = send s_atm#role_Customer (Printf.sprintf "Error: not enough money\n") in
            loop bal s_atm
      | `quit s_atm -> Printf.printf "Bye bye %s" name; close s_atm
  in
  loop 400 s_atm

let () =
  let ts = List.map (fun f -> Thread.create f ()) [ t_atm; t_customer_deposit ] in
  List.iter Thread.join ts
