from influxdb import InfluxDBClient

client = InfluxDBClient(host='localhost', port=8086)
client.switch_database('telegraf')

results = client.query('select * from TTN WHERE time > now() - 1h')

points=results.get_points()
for item in points:  
	if (item['uplink_message_decoded_payload_temperature'] != None):
		print(item['time'], " -> ", item['uplink_message_decoded_payload_temperature'])


