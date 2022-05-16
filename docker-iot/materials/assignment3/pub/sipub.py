# File: sipub.py
#
# The simplest MQTT producer.

import random
import time

import paho.mqtt.client as mqtt

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
CLIENT_ID = ""

# The callback for when the client receives a CONNACK response from the server.
def on_connect(client, userdata, flags, rc):
    print("Connected to ", client._host, "port: ", client._port)
    print("Flags: ", flags, "returned code: ", rc)

# The callback for when a message is published.
def on_publish(client, userdata, mid):
    print("sipub: msg published (mid={})".format(mid))


client = mqtt.Client(client_id=CLIENT_ID, 
                     clean_session=True, 
                     userdata=None, 
                     protocol=mqtt.MQTTv311, 
                     transport="tcp")

client.on_connect = on_connect
client.on_publish = on_publish

client.username_pw_set(None, password=None)
client.connect(THE_BROKER, port=THE_PORT, keepalive=60)

client.loop_start()

while True:

    msg_to_be_sent = f'Hello from {args.broker}: {random.randint(0, 100)}'
    print("Publishing: ", msg_to_be_sent)
    client.publish(THE_TOPIC, 
                   payload=str(msg_to_be_sent).encode('UTF-8'), 
                   qos=0, 
                   retain=False)

    time.sleep(5)

client.loop_stop()
