# Assignment 3 - Filippo Fantinato 2041620

> Modify the **docker-compose.yml** file to start 2 brokers locally that perform full bridging among them

I created two directories (**mosquitto1** and **mosquitto2**) where I declared 1 configuration file for each of them. Here below the two files:

```bash
$ cat ./mosquitto1/config/mosquitto.conf
allow_anonymous true
listener 1883
```

```bash
$ cat./mosquitto2/config/mosquitto.conf
allow_anonymous true
listener 1884

connection broker1
address broker1:1883

topic # both 0

try_private false
```

Basically, I wrote that one broker will listen on the port 1883 and the second one on the port 1884. The second broker will be exploited as a bridge connecting to the broker1 and redirecting anything that will be received and sent.

Afterwards, I changed the **docker-compose.yml** to start the brokers declared before.

```yaml
version: '3'

networks: 
    pubsub-net:
        driver: bridge

services:
    mosquitto1:
        image: eclipse-mosquitto:latest
        container_name: broker1
        ports:
            - 1883:1883
        volumes:
            - ./mosquitto1:/mosquitto/
        networks: 
            - pubsub-net

    mosquitto2:
        image: eclipse-mosquitto:latest
        container_name: broker2
        ports:
            - 1884:1884
        volumes:
            - ./mosquitto2:/mosquitto/
        networks: 
            - pubsub-net
```
## Custom publisher and subscriber to test if everything works

I created a generic publisher and  a generic subscriber. They are generic because you can connect them to any broker and send/receive messages from/to any topic, specifying them via CLI arguments.

To do that, i started from the previous version of the publisher and subscriber given by the professor and modified them to be able to receive the broker, the port and the topic from the command line in this way: 

```python
import argparse

def init_argparse():
	parser = argparse.ArgumentParser()
	parser.add_argument("--broker", type=str, required=True)
	parser.add_argument("--port", type=int, required=True)
	parser.add_argument("--topic", type=str, required=True)
	
	return parser

args = init_argparse().parse_args()
THE_BROKER = args.broker
THE_TOPIC = args.topic
THE_PORT = args.port
```

Afterwards, i **containerized** them (sipub.py and sisub.py) using an appropriate Dockerfile. The interesting part of these Dockerfiles is the use of **ENTRYPOINT in place of CMD**, because the CMD command it's used for giving some default arguments to the entrypoint command and, for this reason, they're going to be replaced by the new ones, while the entrypoint cannot be affected in any way.

## Example of execution

CLI executing the docker compose:

```bash
$ docker compose up
Attaching to broker1, broker2
broker1  | 1649336649: mosquitto version 2.0.14 starting
broker1  | 1649336649: Config loaded from /mosquitto/config/mosquitto.conf.
broker1  | 1649336649: Opening ipv4 listen socket on port 1883.
broker1  | 1649336649: Opening ipv6 listen socket on port 1883.
broker1  | 1649336649: mosquitto version 2.0.14 running
broker2  | 1649336649: mosquitto version 2.0.14 starting
broker2  | 1649336649: Config loaded from /mosquitto/config/mosquitto.conf.
broker2  | 1649336649: Opening ipv4 listen socket on port 1884.
broker2  | 1649336649: Opening ipv6 listen socket on port 1884.
broker2  | 1649336649: Connecting bridge broker1 (broker1:1883)
broker2  | 1649336649: mosquitto version 2.0.14 running
broker1  | 1649336649: New connection from 172.21.0.3:57656 on port 1883.
broker1  | 1649336649: New client connected from 172.21.0.3:57656 as f6c867740aaf.broker1 (p2, c0, k60)
```

CLI executing the **publisher connected to the broker1:**

```bash
$ docker run -it --rm --network=assignment3_pubsub-net sipub:v0.1 --port 1883 --broker broker1 --topic 'test'
sipub: msg published (mid=1)
Publishing:  Hello from broker1: 63
sipub: msg published (mid=2)
Publishing:  Hello from broker1: 33
sipub: msg published (mid=3)
Publishing:  Hello from broker1: 24
sipub: msg published (mid=4)
Publishing:  Hello from broker1: 24
sipub: msg published (mid=5)
Publishing:  Hello from broker1: 17
sipub: msg published (mid=6)
Publishing:  Hello from broker1: 70
sipub: msg published (mid=7)
Publishing:  Hello from broker1: 76
sipub: msg published (mid=8)
Publishing:  Hello from broker1: 43
sipub: msg published (mid=9)
Publishing:  Hello from broker1: 5
...
```

CLI executing the **publisher connected to the broker2**:

```bash
$ docker run -it --rm --network=assignment3_pubsub-net sipub:v0.1 --port 1884 --broker broker2 --topic 'test'
sipub: msg published (mid=1)
Publishing:  Hello from broker2: 74
sipub: msg published (mid=2)
Publishing:  Hello from broker2: 39
sipub: msg published (mid=3)
Publishing:  Hello from broker2: 80
sipub: msg published (mid=4)
Publishing:  Hello from broker2: 27
sipub: msg published (mid=5)
Publishing:  Hello from broker2: 90
sipub: msg published (mid=6)
Publishing:  Hello from broker2: 75
sipub: msg published (mid=7)
Publishing:  Hello from broker2: 82
sipub: msg published (mid=8)
Publishing:  Hello from broker2: 24
sipub: msg published (mid=9)
Publishing:  Hello from broker2: 98
,,,
```

CLI executing the **subscriber connected to the broker1**:

```bash
$ docker run -it --rm --network=assignment3_pubsub-net sisub:v0.1 --port 1883 --broker broker1 --topic '#'
sisub: msg received with topic: test and payload: Hello from broker1: 70
sisub: msg received with topic: test and payload: Hello from broker2: 27
sisub: msg received with topic: test and payload: Hello from broker1: 76
sisub: msg received with topic: test and payload: Hello from broker2: 90
sisub: msg received with topic: test and payload: Hello from broker1: 43
sisub: msg received with topic: test and payload: Hello from broker2: 75
sisub: msg received with topic: test and payload: Hello from broker1: 5
sisub: msg received with topic: test and payload: Hello from broker2: 82
sisub: msg received with topic: test and payload: Hello from broker1: 50
sisub: msg received with topic: test and payload: Hello from broker2: 24
sisub: msg received with topic: test and payload: Hello from broker1: 38
sisub: msg received with topic: test and payload: Hello from broker2: 98
sisub: msg received with topic: test and payload: Hello from broker1: 93
sisub: msg received with topic: test and payload: Hello from broker2: 19
sisub: msg received with topic: test and payload: Hello from broker1: 69
sisub: msg received with topic: test and payload: Hello from broker2: 54
sisub: msg received with topic: test and payload: Hello from broker1: 28
sisub: msg received with topic: test and payload: Hello from broker2: 70
sisub: msg received with topic: test and payload: Hello from broker1: 98
...
```

CLI executing the **subscriber connected to the broker2**:

```bash
$ docker run -it --rm --network=assignment3_pubsub-net sisub:v0.1 --port 1884 --broker broker2 --topic '#'
sisub: msg received with topic: test and payload: Hello from broker2: 75
sisub: msg received with topic: test and payload: Hello from broker2: 75
sisub: msg received with topic: test and payload: Hello from broker1: 5
sisub: msg received with topic: test and payload: Hello from broker2: 82
sisub: msg received with topic: test and payload: Hello from broker2: 82
sisub: msg received with topic: test and payload: Hello from broker1: 50
sisub: msg received with topic: test and payload: Hello from broker2: 24
sisub: msg received with topic: test and payload: Hello from broker2: 24
sisub: msg received with topic: test and payload: Hello from broker1: 38
sisub: msg received with topic: test and payload: Hello from broker2: 98
sisub: msg received with topic: test and payload: Hello from broker2: 98
sisub: msg received with topic: test and payload: Hello from broker1: 93
sisub: msg received with topic: test and payload: Hello from broker2: 19
sisub: msg received with topic: test and payload: Hello from broker2: 19
sisub: msg received with topic: test and payload: Hello from broker1: 69
sisub: msg received with topic: test and payload: Hello from broker2: 54
sisub: msg received with topic: test and payload: Hello from broker2: 54
sisub: msg received with topic: test and payload: Hello from broker1: 28
sisub: msg received with topic: test and payload: Hello from broker2: 70
sisub: msg received with topic: test and payload: Hello from broker2: 70
sisub: msg received with topic: test and payload: Hello from broker1: 98
sisub: msg received with topic: test and payload: Hello from broker2: 71
sisub: msg received with topic: test and payload: Hello from broker2: 71
sisub: msg received with topic: test and payload: Hello from broker1: 16
sisub: msg received with topic: test and payload: Hello from broker2: 74
sisub: msg received with topic: test and payload: Hello from broker2: 74
sisub: msg received with topic: test and payload: Hello from broker1: 92
sisub: msg received with topic: test and payload: Hello from broker2: 81
sisub: msg received with topic: test and payload: Hello from broker2: 81
```

**Note:** the subscriber to the broker2 received the message from the publisher on the same broker twice, because the set direction is BOTH.