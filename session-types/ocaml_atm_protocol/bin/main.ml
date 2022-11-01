module BP = Binary_session_lwt.Make

let rec atm () = BP.(
  recv () >>= 
  fun (name: string) -> 
    lift_io (Lwt_io.printlf "Hi : %s" name) >>= 
  fun () ->
    offer
      (* Quit *)
    (stop ())
    (
      offer
        (* Deposit *)
        (
          recv () >>= 
          fun (money: int) ->
            lift_io (Lwt_io.printlf "Depositing %d" money) >>= fun () ->
              if money > 0
              then choose_left (send (Printf.sprintf "Your new balance is %d" money) >>= atm)
              else choose_right (send (Printf.sprintf "Error: The amount of money is negative or equal to 0") >>= atm)
        )
        (* Transfer *)
        (
          stop()
        )
    )
)

let rec customer (name: string) = BP.(
  send name >>= fun () ->
    choose_right (
      choose_left (
        send (-12) >>= 
        fun () ->
          offer
            (
              recv() >>= fun (msg: string) -> lift_io (Lwt_io.printlf "%s" msg) >>= fun () -> customer name
            )
            (
              recv() >>= fun (err: string) -> lift_io (Lwt_io.printlf "%s" err) >>= fun () -> customer name
            )
      )
    )
)

let () = Lwt_main.run (
  Lwt.(   
    BP.run_processes (atm ()) (customer "Filippo") >>= fun (server_fn,client_fn) ->
    async server_fn ;
    client_fn () >>= fun _ ->
    return ()
  )
)