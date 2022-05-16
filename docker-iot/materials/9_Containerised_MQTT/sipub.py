# File: sipub.py
#
# The simplest MQTT producer.

import random
import time

import paho.mqtt.client as mqtt

THE_BROKER = "localhost:1884"
THE_TOPIC = "PMtest/rndvalue"
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
client.connect(THE_BROKER, port=1884, keepalive=60)

client.loop_start()

while True:

    msg_to_be_sent = random.randint(0, 100)
    print("publishing: ", msg_to_be_sent)
    client.publish(THE_TOPIC, 
                   payload=str(msg_to_be_sent).encode('UTF-8'), 
                   qos=0, 
                   retain=False)

    time.sleep(5)

client.loop_stop()
