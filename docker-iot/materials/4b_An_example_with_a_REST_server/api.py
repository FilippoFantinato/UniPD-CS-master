#!/usr/bin/python
import sqlite3
from flask import Flask, request, jsonify

def connect_to_db():
    conn = sqlite3.connect('database.db')
    return conn

def insert_sensor(sensor):
    inserted_sensor = {}
    try:
        conn = connect_to_db()
        conn.row_factory = sqlite3.Row
        cur = conn.cursor()
        cur.execute("INSERT INTO sensors (tstamp, label, value, unity) VALUES (?, ?, ?, ?)", 
                    (sensor['tstamp'], sensor['label'], sensor['value'], sensor['unity']) )
        conn.commit()
        inserted_sensor = get_sensor_by_id(cur.lastrowid)
    except:
        conn().rollback()
    finally:
        conn.close()
    return inserted_sensor

def get_sensors():
    sensors = []
    try:
        conn = connect_to_db()
        conn.row_factory = sqlite3.Row
        cur = conn.cursor()
        cur.execute("SELECT * FROM sensors")
        rows = cur.fetchall()

        # convert row objects to dictionary
        for i in rows:
            sensor = {}
            sensor["label"]  = i["label"]
            sensor["value"]  = i["value"]
            sensor["unity"]  = i["unity"]
            sensor["tstamp"] = i["tstamp"]
            sensors.append(sensor)
    except:
        sensors = []
    return sensors

def get_sensor_by_id(sensor_id):
    sensors = []
    try:
        conn = connect_to_db()
        conn.row_factory = sqlite3.Row
        cur = conn.cursor()
        cur.execute("SELECT * FROM sensors WHERE label = ?", (sensor_id,))
        rows = cur.fetchall()

        # convert row objects to dictionary
        for i in rows:
            sensor = {}
            sensor["label"]  = i["label"]
            sensor["value"]  = i["value"]
            sensor["unity"]  = i["unity"]
            sensor["tstamp"] = i["tstamp"]
            sensors.append(sensor)
    except:
        sensors = []
    return sensors

def create_db_table():
    initsensors = [
    {
        "tstamp": "2009-06-15T13:45:30",
        "label": "arduino_nano33",
        "value": 25,
        "unity": "Celsius degrees"
    },
    {
        "tstamp": "2010-06-15T13:45:30",
        "label": "portenta_h7",
        "value": 0.123,
        "unity": "mWatts"
    },
    {
        "tstamp": "2011-06-15T13:45:30",
        "label": "raspberry4",
        "value": 4,
        "unity": "roentgen"
    }
    ]

    try:
        conn = connect_to_db()
        conn.execute('''DROP TABLE sensors''')
        print("connected to db successfully")
    except:
        print("well... almost")
    try:
        conn.execute('''
            CREATE TABLE sensors (
                tstamp TEXT PRIMARY KEY NOT NULL,
                label  TEXT NOT NULL,
                value  REAL NOT NULL,
                unity  TEXT NOT NULL
            );
        ''')

        conn.commit()
        print("sensor table created successfully")
    except:
        print("sensor table creation failed - CREATE TABLE sensors")
    finally:
        conn.close()

    for i in initsensors:
        insert_sensor(i)



if __name__ == "__main__":

    app = Flask(__name__)

    @app.route('/api/initdb', methods=['GET'])
    def api_initdb():
        create_db_table()
        return jsonify(get_sensors())

    @app.route('/api/sensors', methods=['GET'])
    def api_get_sensors():
        return jsonify(get_sensors())

    @app.route('/api/sensors/<id>', methods=['GET'])
    def api_get_sensor(id):
        return jsonify(get_sensor_by_id(id))

    @app.route('/api/sensors/add',  methods = ['POST'])
    def api_add_sensor():
        sensor = request.get_json()
        return jsonify(insert_sensor(sensor))


    #app.debug = True
    app.run(host="0.0.0.0")
