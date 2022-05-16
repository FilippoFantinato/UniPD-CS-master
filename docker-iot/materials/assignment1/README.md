# Assignment 1 - Filippo Fantinato 2041620

> Exercise: “Dockerize” the python program written before (example2.py) to work in this scenario.

The steps i followed to complete the first assignment can be found here below:

1. Before creating and running the container, i need to **run the TIG stack** executing the following command where the file docker-compose.yml related is located:

   ```bash
   $ docker compose up
   ```

2. I find out the **network name** where the TIG stack is running with this command:

   ```bash
   $ docker network ls
   NETWORK ID     NAME                                     DRIVER    SCOPE
   58b8e357ad83   7c_orchestrating_using_compose_tig-net   bridge    local
   aa2aef1854fb   alpine-net                               bridge    local
   f39dba6b0992   bridge                                   bridge    local
   1d7984d1e25f   host                                     host      local
   0c2d97a1ddda   none                                     null      local
   ```

   The network name is **7c_orchestrating_using_compose_tig-net**

3. As the file example2.py is given, it can't communicate with the database because the hostname is set to localhost. Therefore, **I have got to change it to influxdb** in this way:

   ```python
   # Before
   client = InfluxDBClient(host='localhost', port=8086)
   # After
   client = InfluxDBClient(host='influxdb', port=8086)
   ```

4. I create the **Dockerfile**:

   ```dockerfile
   FROM alpine # Create the container starting from Alpine
   
   RUN apk add --update py3-pip # Install python3 and pip3 via apk
   RUN pip3 install -U influxdb # Install influxdb via pip3
   
   WORKDIR /home/ # Move to /home/ directory
   COPY example2.py . # Copy example2.py to /home/ into the container 
   
   CMD ["python3", "example2.py"] # Execute the file example2.py
   ```

5. I build an image named **filippofantinato/assignment1:v0.1**, from the previously created Dockerfile, thanks to this command:

   ```bash
   $ docker build -t filippofantinato/assignment1:v0.1 .
   ```

6. Finally, I'm ready to run the container I just created with this command:

   ```bash
   $ docker run --network 7c_orchestrating_using_compose_tig-net filippofantinato/assignment1:v0.1
   ```

   where I'm connecting it to the network of the TIG stack in order to be able to comunicate with influxdb

That's all.