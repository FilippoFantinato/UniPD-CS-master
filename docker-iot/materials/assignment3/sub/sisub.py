# File: sisub.py
#
# The simplest MQTT subscriber.

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

    client.subscribe(THE_TOPIC, qos=0)

# The callback for when a message is received from the server.
def on_message(client, userdata, msg):
    print("sisub: msg received with topic: {} and payload: {}".format(msg.topic, msg.payload.decode('UTF-8')))

client = mqtt.Client(client_id=CLIENT_ID, 
                     clean_session=True, 
                     userdata=None, 
                     protocol=mqtt.MQTTv311, 
                     transport="tcp")

client.on_connect = on_connect
client.on_message = on_message

client.username_pw_set(None, password=None)
client.connect(THE_BROKER, port=THE_PORT, keepalive=60)

# Blocking call that processes network traffic, dispatches callbacks and
# handles reconnecting.
client.loop_forever()

